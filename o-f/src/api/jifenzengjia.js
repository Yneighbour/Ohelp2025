import request from '../utils/request'

export const getJifenzengjiaList = () => {
  return request({
    url: '/api/jifenzengjia',
    method: 'GET'
  })
}

export const createJifenzengjia = (data) => {
  return request({
    url: '/api/jifenzengjia',
    method: 'POST',
    data
  })
}

export const updateJifenzengjia = (id, data) => {
  return request({
    url: `/api/jifenzengjia/${id}`,
    method: 'PUT',
    data
  })
}

export const deleteJifenzengjia = (id) => {
  return request({
    url: `/api/jifenzengjia/${id}`,
    method: 'DELETE'
  })
}

export const getJifenzengjiaById = (id) => {
  return request({
    url: `/api/jifenzengjia/${id}`,
    method: 'GET'
  })
}

export const getJifenzengjiaByLaoren = (laorenId) => {
  return request({
    url: `/api/jifenzengjia/laoren/${laorenId}`,
    method: 'GET'
  })
}