import axios from 'axios'
import { ElMessage } from 'element-plus'

const request = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 10000
})

// attach token
request.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = token
  }
  return config
})

let isRefreshing = false
let refreshSubscribers = []
function subscribeTokenRefresh(cb) { refreshSubscribers.push(cb) }
function onRefreshed(token) { refreshSubscribers.forEach(cb => cb(token)); refreshSubscribers = [] }

request.interceptors.response.use(
  res => res,
  async err => {
    const { response, config } = err || {}
    if (!response) {
      ElMessage.error('网络异常，请稍后重试')
      return Promise.reject(err)
    }

    if (response.status === 401) {
      // try refresh token
      const refreshToken = localStorage.getItem('refreshToken')
      if (!refreshToken) {
        localStorage.removeItem('token')
        location.href = '/index.html'
        return Promise.reject(err)
      }

      if (!isRefreshing) {
        isRefreshing = true
        try {
          const r = await axios.post('/auth/refresh', { refreshToken })
          const newToken = r?.data?.token
          if (newToken) {
            localStorage.setItem('token', newToken)
            onRefreshed(newToken)
          } else {
            localStorage.removeItem('token')
            location.href = '/index.html'
          }
        } catch (e) {
          localStorage.removeItem('token')
          location.href = '/index.html'
          return Promise.reject(e)
        } finally {
          isRefreshing = false
        }
      }

      // queue the request until refreshed
      const retryOrig = new Promise((resolve) => {
        subscribeTokenRefresh(token => {
          config.headers.Authorization = token
          resolve(axios(config))
        })
      })
      return retryOrig
    }

    // other errors
    const msg = response.data?.message || response.statusText || '请求出错'
    ElMessage.error(msg)
    return Promise.reject(err)
  }
)

export default request
