import request from '../../utils/request'

// Create emergency
export const createEmergency = (data) => {
  return request({
    url: '/api/emergency/',
    method: 'POST',
    data
  })
}

// Get emergency by id
export const getEmergencyById = (id) => {
  return request({
    url: `/api/emergency/${id}`,
    method: 'GET'
  })
}

// Get all emergencies
export const getEmergencyList = () => {
  return request({
    url: '/api/emergency/',
    method: 'GET'
  })
}

// Get emergencies by elderly id
export const getEmergencyByElderly = (elderlyId) => {
  return request({
    url: `/api/emergency/elderly/${elderlyId}`,
    method: 'GET'
  })
}

// Get emergencies by status
export const getEmergencyByStatus = (status) => {
  return request({
    url: `/api/emergency/status/${status}`,
    method: 'GET'
  })
}

// Get emergencies by priority
export const getEmergencyByPriority = (priority) => {
  return request({
    url: `/api/emergency/priority/${priority}`,
    method: 'GET'
  })
}

// Update emergency
export const updateEmergency = (id, data) => {
  return request({
    url: `/api/emergency/${id}`,
    method: 'PUT',
    data
  })
}

// Delete emergency
export const deleteEmergency = (id) => {
  return request({
    url: `/api/emergency/${id}`,
    method: 'DELETE'
  })
}

// Respond to emergency
export const respondEmergency = (id, data) => {
  return request({
    url: `/api/emergency/${id}/respond`,
    method: 'PUT',
    data
  })
}

// Resolve emergency
export const resolveEmergency = (id, data) => {
  return request({
    url: `/api/emergency/${id}/resolve`,
    method: 'PUT',
    data
  })
}