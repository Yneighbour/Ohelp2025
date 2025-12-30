import request from '../utils/request'

export const getPingjiaList = () => {
  return request({
    url: '/api/pingjia',
    method: 'GET'
  })
}

export const createPingjia = (data) => {
  return request({
    url: '/api/pingjia',
    method: 'POST',
    data
  })
}

export const updatePingjia = (id, data) => {
  return request({
    url: `/api/pingjia/${id}`,
    method: 'PUT',
    data
  })
}

export const deletePingjia = (id) => {
  return request({
    url: `/api/pingjia/${id}`,
    method: 'DELETE'
  })
}

export const getPingjiaById = (id) => {
  return request({
    url: `/api/pingjia/${id}`,
    method: 'GET'
  })
}

export const getPingjiaByLaoren = (laorenId) => {
  return request({
    url: `/api/pingjia/laoren/${laorenId}`,
    method: 'GET'
  })
}

export const getPingjiaByLaogong = (laogongId) => {
  return request({
    url: `/api/pingjia/laogong/${laogongId}`,
    method: 'GET'
  })
}