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

// User logout
export const logout = () => {
  return request({
    url: '/api/auth/logout',
    method: 'POST'
  })
}

// User register
export const register = (data) => {
  return request({
    url: '/api/auth/register',
    method: 'POST',
    data
  })
}

// Validate token
export const validateToken = (token) => {
  return request({
    url: `/api/auth/validate/${token}`,
    method: 'GET'
  })
}

// Get auth record
export const getAuthById = (id) => {
  return request({
    url: `/api/auth/${id}`,
    method: 'GET'
  })
}

// Get all auth records
export const getAuthList = () => {
  return request({
    url: '/api/auth/',
    method: 'GET'
  })
}

// Delete auth record
export const deleteAuth = (id) => {
  return request({
    url: `/api/auth/${id}`,
    method: 'DELETE'
  })
}