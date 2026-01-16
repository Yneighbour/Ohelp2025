import { requestData } from './http';

export async function listAll() {
  return await requestData({ method: 'GET', url: '/role/' });
}

export async function getById(id) {
  return await requestData({ method: 'GET', url: `/role/${id}` });
}

export async function getByCode(code) {
  return await requestData({ method: 'GET', url: `/role/code/${encodeURIComponent(code)}` });
}

export async function getRolePermissions(id) {
  return await requestData({ method: 'GET', url: `/role/${id}/permissions` });
}

export async function createRole(payload) {
  return await requestData({ method: 'POST', url: '/role/', data: payload });
}

export async function updateRole(id, payload) {
  return await requestData({ method: 'PUT', url: `/role/${id}`, data: payload });
}

export async function assignPermissions(roleId, permissionIds) {
  return await requestData({ 
    method: 'PUT', 
    url: `/role/${roleId}/permissions`, 
    data: { permissionIds } 
  });
}

export async function deleteRole(id) {
  return await requestData({ method: 'DELETE', url: `/role/${id}` });
}

export async function activateRole(id) {
  return await requestData({ method: 'PUT', url: `/role/${id}/activate` });
}

export async function deactivateRole(id) {
  return await requestData({ method: 'PUT', url: `/role/${id}/deactivate` });
}
