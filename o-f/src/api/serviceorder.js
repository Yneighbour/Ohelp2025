import { requestData } from './http';

export async function listAll() {
  return await requestData({ method: 'GET', url: '/serviceorder/' });
}

export async function listByStatus(status) {
  return await requestData({ method: 'GET', url: `/serviceorder/status/${encodeURIComponent(status)}` });
}

export async function cancel(id) {
  return await requestData({ method: 'PUT', url: `/serviceorder/${id}/cancel` });
}

export async function complete(id) {
  return await requestData({ method: 'PUT', url: `/serviceorder/${id}/complete` });
}
