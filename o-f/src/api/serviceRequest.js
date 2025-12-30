import request from '../utils/request'

export const getServiceRequests = () => {
  return request({
    url: '/api/service-requests',
    method: 'GET'
  })
}

export const getServiceRequestById = (id) => {
  return request({
    url: `/api/service-requests/${id}`,
    method: 'GET'
  })
}

export const createServiceRequest = (data) => {
  return request({
    url: '/api/service-requests',
    method: 'POST',
    data
  })
}

export const updateServiceRequest = (id, data) => {
  return request({
    url: `/api/service-requests/${id}`,
    method: 'PUT',
    data
  })
}

export const deleteServiceRequest = (id) => {
  return request({
    url: `/api/service-requests/${id}`,
    method: 'DELETE'
  })
}

export const getServiceRequestsByStatus = (status) => {
  return request({
    url: `/api/service-requests/status/${status}`,
    method: 'GET'
  })
}

export const getServiceRequestsByPriority = (priority) => {
  return request({
    url: `/api/service-requests/priority/${priority}`,
    method: 'GET'
  })
}