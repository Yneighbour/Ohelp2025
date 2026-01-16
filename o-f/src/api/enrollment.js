import { requestData } from './http';

export async function listAll() {
  return await requestData({ method: 'GET', url: '/enrollment' });
}

export async function listByActivity(activityId) {
  return await requestData({ method: 'GET', url: `/enrollment/activity/${activityId}` });
}

export async function listByElderly(elderlyId) {
  return await requestData({ method: 'GET', url: `/enrollment/elderly/${elderlyId}` });
}

export async function listByStatus(status) {
  return await requestData({ method: 'GET', url: `/enrollment/status/${status}` });
}

export async function getById(id) {
  return await requestData({ method: 'GET', url: `/enrollment/${id}` });
}

export async function create(data) {
  return await requestData({ method: 'POST', url: '/enrollment', data });
}

export async function update(id, data) {
  return await requestData({ method: 'PUT', url: `/enrollment/${id}`, data });
}

export async function deleteEnrollment(id) {
  return await requestData({ method: 'DELETE', url: `/enrollment/${id}` });
}

export async function confirm(id) {
  return await requestData({ method: 'PUT', url: `/enrollment/${id}/confirm` });
}

export async function checkIn(id) {
  return await requestData({ method: 'PUT', url: `/enrollment/${id}/checkin` });
}

export async function cancel(id) {
  return await requestData({ method: 'PUT', url: `/enrollment/${id}/cancel` });
}

export async function markAbsent(id) {
  return await requestData({ method: 'PUT', url: `/enrollment/${id}/absent` });
}
