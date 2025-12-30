import request from '../utils/request'

export const getJiwangbingshiList = () => {
  return request({
    url: '/api/jiwangbingshi',
    method: 'GET'
  })
}

export const createJiwangbingshi = (data) => {
  return request({
    url: '/api/jiwangbingshi',
    method: 'POST',
    data
  })
}

export const updateJiwangbingshi = (id, data) => {
  return request({
    url: `/api/jiwangbingshi/${id}`,
    method: 'PUT',
    data
  })
}

export const deleteJiwangbingshi = (id) => {
  return request({
    url: `/api/jiwangbingshi/${id}`,
    method: 'DELETE'
  })
}

export const getJiwangbingshiById = (id) => {
  return request({
    url: `/api/jiwangbingshi/${id}`,
    method: 'GET'
  })
}

export const getJiwangbingshiByLaoren = (laorenId) => {
  return request({
    url: `/api/jiwangbingshi/laoren/${laorenId}`,
    method: 'GET'
  })
}