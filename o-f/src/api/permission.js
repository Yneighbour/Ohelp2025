import { requestData } from './http';

export async function listAll() {
  return await requestData({ method: 'GET', url: '/permission/' });
}

export async function getById(id) {
  return await requestData({ method: 'GET', url: `/permission/${id}` });
}

export async function listByModule(module) {
  return await requestData({ method: 'GET', url: `/permission/module/${encodeURIComponent(module)}` });
}

export async function createPermission(payload) {
  return await requestData({ method: 'POST', url: '/permission/', data: payload });
}

export async function updatePermission(id, payload) {
  return await requestData({ method: 'PUT', url: `/permission/${id}`, data: payload });
}

export async function deletePermission(id) {
  return await requestData({ method: 'DELETE', url: `/permission/${id}` });
}
