import request from '../utils/request'

export const getTanwangList = () => {
  return request({
    url: '/api/tanwang',
    method: 'GET'
  })
}

export const createTanwang = (data) => {
  return request({
    url: '/api/tanwang',
    method: 'POST',
    data
  })
}

export const updateTanwang = (id, data) => {
  return request({
    url: `/api/tanwang/${id}`,
    method: 'PUT',
    data
  })
}

export const deleteTanwang = (id) => {
  return request({
    url: `/api/tanwang/${id}`,
    method: 'DELETE'
  })
}

export const getTanwangById = (id) => {
  return request({
    url: `/api/tanwang/${id}`,
    method: 'GET'
  })
}

export const getTanwangByLaoren = (laorenId) => {
  return request({
    url: `/api/tanwang/laoren/${laorenId}`,
    method: 'GET'
  })
}