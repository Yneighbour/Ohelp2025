import request from '../utils/request'

export const laogongLogin = (data) => {
  return request({
    url: '/api/laogong/login',
    method: 'POST',
    data
  })
}

export const getLaogongList = () => {
  return request({
    url: '/api/laogong',
    method: 'GET'
  })
}

export const getLaogongById = (id) => {
  return request({
    url: `/api/laogong/${id}`,
    method: 'GET'
  })
}

export const createLaogong = (data) => {
  return request({
    url: '/api/laogong',
    method: 'POST',
    data
  })
}

export const updateLaogong = (id, data) => {
  return request({
    url: `/api/laogong/${id}`,
    method: 'PUT',
    data
  })
}

export const deleteLaogong = (id) => {
  return request({
    url: `/api/laogong/${id}`,
    method: 'DELETE'
  })
}

export const updateLaogongPassword = (id, data) => {
  return request({
    url: `/api/laogong/${id}/password`,
    method: 'PUT',
    data
  })
}