import http from '../../shared/http.js'

// File API 接口
export function uploadFile(fileData) { return http.post('/file/upload', fileData) }
export function getFileRecord(id) { return http.get(`/file/${id}`) }
export function getAllFileRecords() { return http.get('/file/') }
export function getFilesByUploader(uploaderId) { return http.get(`/file/uploader/${uploaderId}`) }
export function getFilesByEntity(entityType, entityId) { return http.get(`/file/entity/${encodeURIComponent(entityType)}/${entityId}`) }
export function getFilesByType(fileType) { return http.get(`/file/type/${encodeURIComponent(fileType)}`) }
export function updateFileRecord(id, record) { return http.put(`/file/${id}`, record) }
export function deleteFileRecord(id) { return http.delete(`/file/${id}`) }
export function activateFileRecord(id) { return http.put(`/file/${id}/activate`) }
export function deactivateFileRecord(id) { return http.put(`/file/${id}/deactivate`) }