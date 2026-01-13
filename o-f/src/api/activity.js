import { requestData } from './http';

export async function listAll() {
  return requestData({ method: 'GET', url: '/activity/' });
}

export async function getById(id) {
  return requestData({ method: 'GET', url: `/activity/${id}` });
}

export async function listByCategory(category) {
  return requestData({ method: 'GET', url: `/activity/category/${encodeURIComponent(category)}` });
}

export async function listByStatus(status) {
  return requestData({ method: 'GET', url: `/activity/status/${encodeURIComponent(status)}` });
}

export async function createActivity(payload) {
  return requestData({ method: 'POST', url: '/activity/', data: payload });
}

export async function updateActivity(id, payload) {
  return requestData({ method: 'PUT', url: `/activity/${id}` , data: payload });
}

export async function deleteActivity(id) {
  return requestData({ method: 'DELETE', url: `/activity/${id}` });
}

export async function activateActivity(id) {
  return requestData({ method: 'PUT', url: `/activity/${id}/activate` });
}

export async function deactivateActivity(id) {
  return requestData({ method: 'PUT', url: `/activity/${id}/deactivate` });
}
