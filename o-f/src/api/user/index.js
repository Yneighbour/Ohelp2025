import request from '../../utils/request'

// Create user
export const createUser = (data) => {
  return request({
    url: '/api/user/',
    method: 'POST',
    data
  })
}

// Get user by id
export const getUserById = (id) => {
  return request({
    url: `/api/user/${id}`,
    method: 'GET'
  })
}

// Get user by email
export const getUserByEmail = (email) => {
  return request({
    url: `/api/user/email/${email}`,
    method: 'GET'
  })
}

// Get user by phone
export const getUserByPhone = (phone) => {
  return request({
    url: `/api/user/phone/${phone}`,
    method: 'GET'
  })
}

// Get all users
export const getUserList = () => {
  return request({
    url: '/api/user/',
    method: 'GET'
  })
}

// Update user
export const updateUser = (id, data) => {
  return request({
    url: `/api/user/${id}`,
    method: 'PUT',
    data
  })
}

// Delete user
export const deleteUser = (id) => {
  return request({
    url: `/api/user/${id}`,
    method: 'DELETE'
  })
}

// Activate user
export const activateUser = (id) => {
  return request({
    url: `/api/user/${id}/activate`,
    method: 'PUT'
  })
}

// Deactivate user
export const deactivateUser = (id) => {
  return request({
    url: `/api/user/${id}/deactivate`,
    method: 'PUT'
  })
}