import axios from 'axios'

// 创建axios实例
const http = axios.create({
  baseURL: 'http://localhost:8080/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
})

// 请求拦截器
http.interceptors.request.use(
  config => {
    // 仅携带后端返回的 token（不在前端推断权限/角色）
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = token
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器
http.interceptors.response.use(
  response => {
    // 统一处理响应
    const { data } = response
    if (data.code !== 200 && data.code !== 201) {
      // 处理业务错误
      console.error('API Error:', data.message)
      throw new Error(data.message || '请求失败')
    }
    return data
  },
  error => {
    // 处理HTTP错误
    if (error.response) {
      const { status, data } = error.response
      switch (status) {
        case 400:
          console.error('请求错误:', data.message)
          break
        case 401:
          console.error('未授权:', data.message)
          break
        case 404:
          console.error('资源不存在:', data.message)
          break
        case 500:
          console.error('服务器错误:', data.message)
          break
        default:
          console.error('未知错误:', data.message)
      }
    } else if (error.request) {
      console.error('网络错误:', error.message)
    } else {
      console.error('请求配置错误:', error.message)
    }
    return Promise.reject(error)
  }
)

export default http