import request from '../../utils/request'

// Create activity
export const createActivity = (data) => {
  return request({
    url: '/api/activity/',
    method: 'POST',
    data
  })
}

// Get activity by id
export const getActivityById = (id) => {
  return request({
    url: `/api/activity/${id}`,
    method: 'GET'
  })
}

// Get all activities
export const getActivityList = () => {
  return request({
    url: '/api/activity/',
    method: 'GET'
  })
}

// Get activities by category
export const getActivityByCategory = (category) => {
  return request({
    url: `/api/activity/category/${category}`,
    method: 'GET'
  })
}

// Get activities by status
export const getActivityByStatus = (status) => {
  return request({
    url: `/api/activity/status/${status}`,
    method: 'GET'
  })
}

// Update activity
export const updateActivity = (id, data) => {
  return request({
    url: `/api/activity/${id}`,
    method: 'PUT',
    data
  })
}

// Delete activity
export const deleteActivity = (id) => {
  return request({
    url: `/api/activity/${id}`,
    method: 'DELETE'
  })
}

// Activate activity
export const activateActivity = (id) => {
  return request({
    url: `/api/activity/${id}/activate`,
    method: 'PUT'
  })
}

// Deactivate activity
export const deactivateActivity = (id) => {
  return request({
    url: `/api/activity/${id}/deactivate`,
    method: 'PUT'
  })
}