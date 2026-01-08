import request from '../../utils/request'

// Upload file
export const uploadFile = (data) => {
  return request({
    url: '/api/file/upload',
    method: 'POST',
    data
  })
}

// Get file record by id
export const getFileRecordById = (id) => {
  return request({
    url: `/api/file/${id}`,
    method: 'GET'
  })
}

// Get all file records
export const getFileRecordList = () => {
  return request({
    url: '/api/file/',
    method: 'GET'
  })
}

// Get file records by uploader id
export const getFileRecordByUploader = (uploaderId) => {
  return request({
    url: `/api/file/uploader/${uploaderId}`,
    method: 'GET'
  })
}

// Get file records by entity
export const getFileRecordByEntity = (entityType, entityId) => {
  return request({
    url: `/api/file/entity/${entityType}/${entityId}`,
    method: 'GET'
  })
}

// Get file records by file type
export const getFileRecordByFileType = (fileType) => {
  return request({
    url: `/api/file/type/${fileType}`,
    method: 'GET'
  })
}

// Update file record
export const updateFileRecord = (id, data) => {
  return request({
    url: `/api/file/${id}`,
    method: 'PUT',
    data
  })
}

// Delete file record
export const deleteFileRecord = (id) => {
  return request({
    url: `/api/file/${id}`,
    method: 'DELETE'
  })
}

// Activate file record
export const activateFileRecord = (id) => {
  return request({
    url: `/api/file/${id}/activate`,
    method: 'PUT'
  })
}

// Deactivate file record
export const deactivateFileRecord = (id) => {
  return request({
    url: `/api/file/${id}/deactivate`,
    method: 'PUT'
  })
}