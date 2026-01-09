import http from '../../shared/http.js'

// Health API 接口
export function createHealthRecord(record) { return http.post('/health/', record) }
export function getHealthRecord(id) { return http.get(`/health/${id}`) }
export function getAllHealthRecords() { return http.get('/health/') }
export function getHealthRecordsByElderlyId(elderlyId) { return http.get(`/health/elderly/${elderlyId}`) }
export function getHealthRecordsByDate(recordDate) { return http.get(`/health/date/${recordDate}`) }
export function getHealthRecordsByDoctor(doctorId) { return http.get(`/health/doctor/${doctorId}`) }
export function updateHealthRecord(id, record) { return http.put(`/health/${id}`, record) }
export function deleteHealthRecord(id) { return http.delete(`/health/${id}`) }
export function activateHealthRecord(id) { return http.put(`/health/${id}/activate`) }
export function deactivateHealthRecord(id) { return http.put(`/health/${id}/deactivate`) }