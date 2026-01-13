import { requestData } from './http';

export async function create(payload) {
  return requestData({ method: 'POST', url: '/emergency/', data: payload });
}

export async function listAll() {
  return requestData({ method: 'GET', url: '/emergency/' });
}

export async function listByElderlyId(elderlyId) {
  return requestData({ method: 'GET', url: `/emergency/elderly/${elderlyId}` });
}

export async function getById(id) {
  return requestData({ method: 'GET', url: `/emergency/${id}` });
}

export async function listByStatus(status) {
  return requestData({ method: 'GET', url: `/emergency/status/${encodeURIComponent(status)}` });
}

export async function listByPriority(priority) {
  return requestData({ method: 'GET', url: `/emergency/priority/${encodeURIComponent(priority)}` });
}

export async function respond(id, responderId) {
  return requestData({ method: 'PUT', url: `/emergency/${id}/respond`, params: { responderId } });
}

export async function resolve(id) {
  return requestData({ method: 'PUT', url: `/emergency/${id}/resolve` });
}
