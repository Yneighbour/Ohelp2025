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
