import { requestData } from './http';

// Elderly APIs
export async function listAll() {
  return await requestData({ method: 'GET', url: '/elder/elderly/' });
}

export async function getById(id) {
  return await requestData({ method: 'GET', url: `/elder/elderly/${id}` });
}

export async function searchByName(name) {
  return await requestData({ method: 'GET', url: `/elder/elderly/search/${encodeURIComponent(name)}` });
}

export async function createElderly(payload) {
  return await requestData({ method: 'POST', url: '/elder/elderly/', data: payload });
}

export async function updateElderly(id, payload) {
  return await requestData({ method: 'PUT', url: `/elder/elderly/${id}`, data: payload });
}

export async function deleteElderly(id) {
  return await requestData({ method: 'DELETE', url: `/elder/elderly/${id}` });
}

// Relative APIs
export async function listAllRelatives() {
  return await requestData({ method: 'GET', url: '/elder/relative/' });
}

export async function getRelativeById(id) {
  return await requestData({ method: 'GET', url: `/elder/relative/${id}` });
}

export async function getRelativesByElderlyId(elderlyId) {
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
