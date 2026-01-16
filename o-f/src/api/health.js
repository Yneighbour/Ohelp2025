import { requestData } from './http';

export async function listAllHealthRecords() {
  return await requestData({ method: 'GET', url: '/health/' });
}

export async function listHealthRecordsByElderlyId(elderlyId) {
  return await requestData({ method: 'GET', url: `/health/elderly/${elderlyId}` });
}

// Alias for naming consistency with other API modules (e.g., emergency.listByElderlyId)
export async function listByElderlyId(elderlyId) {
  return await listHealthRecordsByElderlyId(elderlyId);
}

export async function getHealthRecordById(id) {
  return await requestData({ method: 'GET', url: `/health/${id}` });
}

export async function createHealthRecord(data) {
  return await requestData({ method: 'POST', url: '/health/', data });
}

export async function updateHealthRecord(id, data) {
  return await requestData({ method: 'PUT', url: `/health/${id}`, data });
}

export async function deleteHealthRecord(id) {
  return await requestData({ method: 'DELETE', url: `/health/${id}` });
}

export async function activateHealthRecord(id) {
  return await requestData({ method: 'PUT', url: `/health/${id}/activate` });
}

export async function deactivateHealthRecord(id) {
  return await requestData({ method: 'PUT', url: `/health/${id}/deactivate` });
}
