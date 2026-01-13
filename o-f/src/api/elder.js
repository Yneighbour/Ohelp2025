import { requestData } from './http';

export async function listAll() {
  return await requestData({ method: 'GET', url: '/elder/elderly/' });
}

export async function getById(id) {
  return await requestData({ method: 'GET', url: `/elder/elderly/${id}` });
}

export async function searchByName(name) {
  return await requestData({ method: 'GET', url: `/elder/elderly/search/${encodeURIComponent(name)}` });
}
