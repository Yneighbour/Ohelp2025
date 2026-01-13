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
