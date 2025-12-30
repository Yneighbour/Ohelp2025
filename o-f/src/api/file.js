import request from '../utils/request'

export const uploadFile = (file) => {
  const formData = new FormData()
  formData.append('file', file)
  
  return request({
    url: '/api/file/upload',
    method: 'POST',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

export const uploadMultipleFiles = (files) => {
  const formData = new FormData()
  files.forEach(file => {
    formData.append('files', file)
  })
  
  return request({
    url: '/api/file/upload/multiple',
    method: 'POST',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

export const deleteFile = (filename) => {
  return request({
    url: `/api/file/delete/${filename}`,
    method: 'DELETE'
  })
}