import request from '../utils/request'

export const getQinshuList = () => {
  return request({
    url: '/api/qinshu',
    method: 'GET'
  })
}

export const createQinshu = (data) => {
  return request({
    url: '/api/qinshu',
    method: 'POST',
    data
  })
}

export const updateQinshu = (id, data) => {
  return request({
    url: `/api/qinshu/${id}`,
    method: 'PUT',
    data
  })
}

export const deleteQinshu = (id) => {
  return request({
    url: `/api/qinshu/${id}`,
    method: 'DELETE'
  })
}

export const getQinshuById = (id) => {
  return request({
    url: `/api/qinshu/${id}`,
    method: 'GET'
  })
}

export const getQinshuByLaoren = (laorenId) => {
  return request({
    url: `/api/qinshu/laoren/${laorenId}`,
    method: 'GET'
  })
}