import request from '../../utils/request'

// Create worker
export const createWorker = (data) => {
  return request({
    url: '/api/worker/',
    method: 'POST',
    data
  })
}

// Get worker by id
export const getWorkerById = (id) => {
  return request({
    url: `/api/worker/${id}`,
    method: 'GET'
  })
}

// Get worker by email
export const getWorkerByEmail = (email) => {
  return request({
    url: `/api/worker/email/${email}`,
    method: 'GET'
  })
}

// Get worker by phone
export const getWorkerByPhone = (phone) => {
  return request({
    url: `/api/worker/phone/${phone}`,
    method: 'GET'
  })
}

// Get all workers
export const getWorkerList = () => {
  return request({
    url: '/api/worker/',
    method: 'GET'
  })
}

// Get workers by department
export const getWorkerByDepartment = (department) => {
  return request({
    url: `/api/worker/department/${department}`,
    method: 'GET'
  })
}

// Get workers by position
export const getWorkerByPosition = (position) => {
  return request({
    url: `/api/worker/position/${position}`,
    method: 'GET'
  })
}

// Get available workers
export const getAvailableWorker = () => {
  return request({
    url: '/api/worker/available',
    method: 'GET'
  })
}

// Update worker
export const updateWorker = (id, data) => {
  return request({
    url: `/api/worker/${id}`,
    method: 'PUT',
    data
  })
}

// Delete worker
export const deleteWorker = (id) => {
  return request({
    url: `/api/worker/${id}`,
    method: 'DELETE'
  })
}

// Activate worker
export const activateWorker = (id) => {
  return request({
    url: `/api/worker/${id}/activate`,
    method: 'PUT'
  })
}

// Deactivate worker
export const deactivateWorker = (id) => {
  return request({
    url: `/api/worker/${id}/deactivate`,
    method: 'PUT'
  })
}

// Set worker availability
export const setWorkerAvailability = (id, data) => {
  return request({
    url: `/api/worker/${id}/availability`,
    method: 'PUT',
    data
  })
}