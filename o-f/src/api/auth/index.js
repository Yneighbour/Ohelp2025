import request from '../../utils/request'

// User authentication
export const login = (username, password) => {
  return request({
    url: '/api/auth/login',
    method: 'POST',
    data: {
      username,
      password
    }
  })
}

// Token management (token module)
export const refreshToken = () => {
  return request({
    url: '/api/token/refresh',
    method: 'POST'
  })
}

// User logout
export const logout = () => {
  return request({
    url: '/api/auth/logout',
    method: 'POST'
  })
}