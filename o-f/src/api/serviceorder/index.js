import request from '../../utils/request'

// Create service order
export const createServiceOrder = (data) => {
  return request({
    url: '/api/serviceorder/',
    method: 'POST',
    data
  })
}

// Get service order by id
export const getServiceOrderById = (id) => {
  return request({
    url: `/api/serviceorder/${id}`,
    method: 'GET'
  })
}

// Get all service orders
export const getServiceOrderList = () => {
  return request({
    url: '/api/serviceorder/',
    method: 'GET'
  })
}

// Get service orders by elderly id
export const getServiceOrderByElderly = (elderlyId) => {
  return request({
    url: `/api/serviceorder/elderly/${elderlyId}`,
    method: 'GET'
  })
}

// Get service orders by service type
export const getServiceOrderByServiceType = (serviceType) => {
  return request({
    url: `/api/serviceorder/service-type/${serviceType}`,
    method: 'GET'
  })
}

// Get service orders by provider id
export const getServiceOrderByProvider = (serviceProviderId) => {
  return request({
    url: `/api/serviceorder/provider/${serviceProviderId}`,
    method: 'GET'
  })
}

// Get service orders by status
export const getServiceOrderByStatus = (status) => {
  return request({
    url: `/api/serviceorder/status/${status}`,
    method: 'GET'
  })
}

// Update service order
export const updateServiceOrder = (id, data) => {
  return request({
    url: `/api/serviceorder/${id}`,
    method: 'PUT',
    data
  })
}

// Delete service order
export const deleteServiceOrder = (id) => {
  return request({
    url: `/api/serviceorder/${id}`,
    method: 'DELETE'
  })
}

// Cancel service order
export const cancelServiceOrder = (id, data) => {
  return request({
    url: `/api/serviceorder/${id}/cancel`,
    method: 'PUT',
    data
  })
}

// Complete service order
export const completeServiceOrder = (id, data) => {
  return request({
    url: `/api/serviceorder/${id}/complete`,
    method: 'PUT',
    data
  })
}