import axios from 'axios'

const request = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 10000
})

request.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = token
  }
  return config
}, error => {
  console.error('Request error:', error)
  return Promise.reject(error)
})

request.interceptors.response.use(
  response => {
    // 返回统一格式的数据结构
    return {
      success: true,
      data: response.data,
      status: response.status,
      message: response.statusText || 'Success'
    }
  },
  error => {
    // 统一错误处理
    const errorResponse = {
      success: false,
      data: null,
      status: error.response?.status || 500,
      message: error.response?.data?.message || error.message || 'Request failed'
    }
    
    console.error('Response error:', errorResponse)
    return Promise.reject(errorResponse)
  }
)

export default request