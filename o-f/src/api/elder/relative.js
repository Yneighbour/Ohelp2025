import request from '../../utils/request'

// Create relative
export const createRelative = (data) => {
  return request({
    url: '/api/elder/relative/',
    method: 'POST',
    data
  })
}

// Get relative by id
export const getRelativeById = (id) => {
  return request({
    url: `/api/elder/relative/${id}`,
    method: 'GET'
  })
}

// Get relatives by elderly id
export const getRelativeByElderly = (elderlyId) => {
  return request({
    url: `/api/elder/relative/elderly/${elderlyId}`,
    method: 'GET'
  })
}

// Get all relatives
export const getRelativeList = () => {
  return request({
    url: '/api/elder/relative/',
    method: 'GET'
  })
}

// Update relative
export const updateRelative = (id, data) => {
  return request({
    url: `/api/elder/relative/${id}`,
    method: 'PUT',
    data
  })
}

// Delete relative
export const deleteRelative = (id) => {
  return request({
    url: `/api/elder/relative/${id}`,
    method: 'DELETE'
  })
}

// Activate relative
export const activateRelative = (id) => {
  return request({
    url: `/api/elder/relative/${id}/activate`,
    method: 'PUT'
  })
}

// Deactivate relative
export const deactivateRelative = (id) => {
  return request({
    url: `/api/elder/relative/${id}/deactivate`,
    method: 'PUT'
  })
}