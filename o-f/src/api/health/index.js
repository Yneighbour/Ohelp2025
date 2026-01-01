import request from '../../utils/request'

// Point management (jifenzengjia module)
export const getPointList = () => {
  return request({
    url: '/api/jifenzengjia',
    method: 'GET'
  })
}

export const createPoint = (data) => {
  return request({
    url: '/api/jifenzengjia',
    method: 'POST',
    data
  })
}

export const updatePoint = (id, data) => {
  return request({
    url: `/api/jifenzengjia/${id}`,
    method: 'PUT',
    data
  })
}

export const deletePoint = (id) => {
  return request({
    url: `/api/jifenzengjia/${id}`,
    method: 'DELETE'
  })
}

export const getPointById = (id) => {
  return request({
    url: `/api/jifenzengjia/${id}`,
    method: 'GET'
  })
}

export const getPointByElder = (elderId) => {
  return request({
    url: `/api/jifenzengjia/laoren/${elderId}`,
    method: 'GET'
  })
}

// Medical history management (jiwangbingshi module)
export const getMedicalHistoryList = () => {
  return request({
    url: '/api/jiwangbingshi',
    method: 'GET'
  })
}

export const createMedicalHistory = (data) => {
  return request({
    url: '/api/jiwangbingshi',
    method: 'POST',
    data
  })
}

export const updateMedicalHistory = (id, data) => {
  return request({
    url: `/api/jiwangbingshi/${id}`,
    method: 'PUT',
    data
  })
}

export const deleteMedicalHistory = (id) => {
  return request({
    url: `/api/jiwangbingshi/${id}`,
    method: 'DELETE'
  })
}

export const getMedicalHistoryById = (id) => {
  return request({
    url: `/api/jiwangbingshi/${id}`,
    method: 'GET'
  })
}

export const getMedicalHistoryByElder = (elderId) => {
  return request({
    url: `/api/jiwangbingshi/laoren/${elderId}`,
    method: 'GET'
  })
}

// Health record management (meirijiankang module)
export const getHealthRecordList = () => {
  return request({
    url: '/api/meirijiankang',
    method: 'GET'
  })
}

export const createHealthRecord = (data) => {
  return request({
    url: '/api/meirijiankang',
    method: 'POST',
    data
  })
}

export const updateHealthRecord = (id, data) => {
  return request({
    url: `/api/meirijiankang/${id}`,
    method: 'PUT',
    data
  })
}

export const deleteHealthRecord = (id) => {
  return request({
    url: `/api/meirijiankang/${id}`,
    method: 'DELETE'
  })
}

export const getHealthRecordById = (id) => {
  return request({
    url: `/api/meirijiankang/${id}`,
    method: 'GET'
  })
}

export const getHealthRecordByElder = (elderId) => {
  return request({
    url: `/api/meirijiankang/laoren/${elderId}`,
    method: 'GET'
  })
}