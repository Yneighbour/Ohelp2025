import { requestData } from './http';

export async function listAll() {
  return await requestData({ method: 'GET', url: '/elder/relative/' });
}

export async function getById(id) {
  return await requestData({ method: 'GET', url: `/elder/relative/${id}` });
}

export async function listByElderlyId(elderlyId) {
  return await requestData({ method: 'GET', url: `/elder/relative/elderly/${elderlyId}` });
}

export async function createRelative(payload) {
  return await requestData({ method: 'POST', url: '/elder/relative/', data: payload });
}

export async function updateRelative(id, payload) {
  return await requestData({ method: 'PUT', url: `/elder/relative/${id}`, data: payload });
}

export async function deleteRelative(id) {
  return await requestData({ method: 'DELETE', url: `/elder/relative/${id}` });
}
