import request from '../../utils/request'

// Create health record
export const createHealthRecord = (data) => {
  return request({
    url: '/api/health/',
    method: 'POST',
    data
  })
}

// Get health record by id
export const getHealthRecordById = (id) => {
  return request({
    url: `/api/health/${id}`,
    method: 'GET'
  })
}

// Get all health records
export const getHealthRecordList = () => {
  return request({
    url: '/api/health/',
    method: 'GET'
  })
}

// Get health records by elderly id
export const getHealthRecordByElderly = (elderlyId) => {
  return request({
    url: `/api/health/elderly/${elderlyId}`,
    method: 'GET'
  })
}

// Get health records by date
export const getHealthRecordByDate = (recordDate) => {
  return request({
    url: `/api/health/date/${recordDate}`,
    method: 'GET'
  })
}

// Get health records by doctor id
export const getHealthRecordByDoctor = (doctorId) => {
  return request({
    url: `/api/health/doctor/${doctorId}`,
    method: 'GET'
  })
}

// Update health record
export const updateHealthRecord = (id, data) => {
  return request({
    url: `/api/health/${id}`,
    method: 'PUT',
    data
  })
}

// Delete health record
export const deleteHealthRecord = (id) => {
  return request({
    url: `/api/health/${id}`,
    method: 'DELETE'
  })
}

// Activate health record
export const activateHealthRecord = (id) => {
  return request({
    url: `/api/health/${id}/activate`,
    method: 'PUT'
  })
}

// Deactivate health record
export const deactivateHealthRecord = (id) => {
  return request({
    url: `/api/health/${id}/deactivate`,
    method: 'PUT'
  })
}