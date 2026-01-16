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

export async function getById(id) {
  return await requestData({ method: 'GET', url: `/serviceorder/${id}` });
}

export async function createServiceOrder(data) {
  return await requestData({ method: 'POST', url: '/serviceorder/', data });
}

export async function updateServiceOrder(id, data) {
  return await requestData({ method: 'PUT', url: `/serviceorder/${id}`, data });
}

export async function deleteServiceOrder(id) {
  return await requestData({ method: 'DELETE', url: `/serviceorder/${id}` });
}

export async function confirm(id) {
  // 通过更新状态为confirmed来确认预约
  return await requestData({ 
    method: 'PUT', 
    url: `/serviceorder/${id}`,
    data: { status: 'confirmed' }
  });
}
