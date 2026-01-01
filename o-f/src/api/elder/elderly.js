import request from '../../utils/request'

// Elderly management (elderly module)
export const getElderlyList = () => {
  return request({
    url: '/api/elderly',
    method: 'GET'
  })
}

export const getActiveElderly = () => {
  return request({
    url: '/api/elderly/active',
    method: 'GET'
  })
}

export const getElderlyById = (id) => {
  return request({
    url: `/api/elderly/${id}`,
    method: 'GET'
  })
}

export const createElderly = (data) => {
  return request({
    url: '/api/elderly',
    method: 'POST',
    data
  })
}

export const updateElderly = (id, data) => {
  return request({
    url: `/api/elderly/${id}`,
    method: 'PUT',
    data
  })
}

export const deleteElderly = (id) => {
  return request({
    url: `/api/elderly/${id}`,
    method: 'DELETE'
  })
}

export const searchElderly = (params) => {
  return request({
    url: '/api/elderly/search',
    method: 'GET',
    params
  })
}