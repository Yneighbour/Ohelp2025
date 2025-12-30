import request from '../utils/request'

export const login = (data) => {
  return request({
    url: '/api/user/login',
    method: 'POST',
    data
  })
}

export const register = (data) => {
  return request({
    url: '/api/user/register',
    method: 'POST',
    data
  })
}

export const logout = () => {
  return request({
    url: '/api/user/logout',
    method: 'POST'
  })
}

export const getUserInfo = () => {
  return request({
    url: '/api/user',
    method: 'GET'
  })
}

export const getUserById = (id) => {
  return request({
    url: `/api/user/${id}`,
    method: 'GET'
  })
}

export const updateUser = (id, data) => {
  return request({
    url: `/api/user/${id}`,
    method: 'PUT',
    data
  })
}

export const deleteUser = (id) => {
  return request({
    url: `/api/user/${id}`,
    method: 'DELETE'
  })
}

export const updatePassword = (id, data) => {
  return request({
    url: `/api/user/${id}/password`,
    method: 'PUT',
    data
  })
}

export const updateProfile = (id, data) => {
  return request({
    url: `/api/user/${id}/profile`,
    method: 'PUT',
    data
  })
}