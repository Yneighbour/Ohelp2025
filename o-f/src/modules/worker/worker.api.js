import http from '../../shared/http.js'

// Worker API 接口
export function createWorker(worker) { return http.post('/worker/', worker) }
export function getWorker(id) { return http.get(`/worker/${id}`) }
export function getWorkerByEmail(email) { return http.get(`/worker/email/${email}`) }
export function getWorkerByPhone(phone) { return http.get(`/worker/phone/${phone}`) }
export function getAllWorkers() { return http.get('/worker/') }
export function getWorkersByDepartment(department) { return http.get(`/worker/department/${encodeURIComponent(department)}`) }
export function getWorkersByPosition(position) { return http.get(`/worker/position/${encodeURIComponent(position)}`) }
export function getAvailableWorkers() { return http.get('/worker/available') }
export function updateWorker(id, worker) { return http.put(`/worker/${id}`, worker) }
export function deleteWorker(id) { return http.delete(`/worker/${id}`) }
export function activateWorker(id) { return http.put(`/worker/${id}/activate`) }
export function deactivateWorker(id) { return http.put(`/worker/${id}/deactivate`) }
export function setWorkerAvailability(id) { return http.put(`/worker/${id}/availability`) }