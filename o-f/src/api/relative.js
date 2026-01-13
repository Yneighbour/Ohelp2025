import { requestData } from './http';

export async function listAll() {
  return await requestData({ method: 'GET', url: '/elder/relative/' });
}

export async function listByElderlyId(elderlyId) {
  return await requestData({ method: 'GET', url: `/elder/relative/elderly/${elderlyId}` });
}
