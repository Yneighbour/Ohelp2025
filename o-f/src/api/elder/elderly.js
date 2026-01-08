import request from '../../utils/request'

// Create elderly
export const createElderly = (data) => {
  return request({
    url: '/api/elder/elderly/',
    method: 'POST',
    data
  })
}

// Get elderly by id
export const getElderlyById = (id) => {
  return request({
    url: `/api/elder/elderly/${id}`,
    method: 'GET'
  })
}

// Get all elderly
export const getElderlyList = () => {
  return request({
    url: '/api/elder/elderly/',
    method: 'GET'
  })
}

// Search elderly by name
export const searchElderly = (name) => {
  return request({
    url: `/api/elder/elderly/search/${name}`,
    method: 'GET'
  })
}

// Update elderly
export const updateElderly = (id, data) => {
  return request({
    url: `/api/elder/elderly/${id}`,
    method: 'PUT',
    data
  })
}

// Delete elderly
export const deleteElderly = (id) => {
  return request({
    url: `/api/elder/elderly/${id}`,
    method: 'DELETE'
  })
}

// Activate elderly
export const activateElderly = (id) => {
  return request({
    url: `/api/elder/elderly/${id}/activate`,
    method: 'PUT'
  })
}

// Deactivate elderly
export const deactivateElderly = (id) => {
  return request({
    url: `/api/elder/elderly/${id}/deactivate`,
    method: 'PUT'
  })
}