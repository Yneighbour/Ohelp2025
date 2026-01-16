import { requestData } from './http';

export async function listAll() {
  return await requestData({ method: 'GET', url: '/worker/' });
}

export async function getById(id) {
  return await requestData({ method: 'GET', url: `/worker/${id}` });
}

export async function getByEmail(email) {
  return await requestData({ method: 'GET', url: `/worker/email/${email}` });
}

export async function getByPhone(phone) {
  return await requestData({ method: 'GET', url: `/worker/phone/${phone}` });
}

export async function getByDepartment(department) {
  return await requestData({ method: 'GET', url: `/worker/department/${department}` });
}

export async function getByPosition(position) {
  return await requestData({ method: 'GET', url: `/worker/position/${position}` });
}

export async function getAvailable() {
  return await requestData({ method: 'GET', url: '/worker/available' });
}

export async function createWorker(data) {
  return await requestData({ method: 'POST', url: '/worker/', data });
}

export async function updateWorker(id, data) {
  return await requestData({ method: 'PUT', url: `/worker/${id}`, data });
}

export async function deleteWorker(id) {
  return await requestData({ method: 'DELETE', url: `/worker/${id}` });
}

export async function activateWorker(id) {
  return await requestData({ method: 'PUT', url: `/worker/${id}/activate` });
}

export async function deactivateWorker(id) {
  return await requestData({ method: 'PUT', url: `/worker/${id}/deactivate` });
}

export async function setAvailability(id, available) {
  return await requestData({ 
    method: 'PUT', 
    url: `/worker/${id}/availability`,
    params: { available }
  });
}
