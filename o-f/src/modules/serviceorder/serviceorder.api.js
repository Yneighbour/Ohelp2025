import http from '../../shared/http.js'

// ServiceOrder API 接口
export function createServiceOrder(order) { return http.post('/serviceorder/', order) }
export function getServiceOrder(id) { return http.get(`/serviceorder/${id}`) }
export function getAllServiceOrders() { return http.get('/serviceorder/') }
export function getServiceOrdersByElderlyId(elderlyId) { return http.get(`/serviceorder/elderly/${elderlyId}`) }
export function getServiceOrdersByType(serviceType) { return http.get(`/serviceorder/service-type/${encodeURIComponent(serviceType)}`) }
export function getServiceOrdersByProvider(serviceProviderId) { return http.get(`/serviceorder/provider/${serviceProviderId}`) }
export function getServiceOrdersByStatus(status) { return http.get(`/serviceorder/status/${encodeURIComponent(status)}`) }
export function updateServiceOrder(id, order) { return http.put(`/serviceorder/${id}`, order) }
export function deleteServiceOrder(id) { return http.delete(`/serviceorder/${id}`) }
export function cancelServiceOrder(id) { return http.put(`/serviceorder/${id}/cancel`) }
export function completeServiceOrder(id) { return http.put(`/serviceorder/${id}/complete`) }