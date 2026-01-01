import request from '../../utils/request'

// Elder account management (laoren module)
export const elderRegister = (data) => {
  return request({
    url: '/api/laoren/register',
    method: 'POST',
    data
  })
}

export const elderLogin = (data) => {
  return request({
    url: '/api/laoren/login',
    method: 'POST',
    data
  })
}

export const getElderList = () => {
  return request({
    url: '/api/laoren',
    method: 'GET'
  })
}

export const getElderById = (id) => {
  return request({
    url: `/api/laoren/${id}`,
    method: 'GET'
  })
}

export const updateElder = (id, data) => {
  return request({
    url: `/api/laoren/${id}`,
    method: 'PUT',
    data
  })
}

export const deleteElder = (id) => {
  return request({
    url: `/api/laoren/${id}`,
    method: 'DELETE'
  })
}

export const updateElderPassword = (id, data) => {
  return request({
    url: `/api/laoren/${id}/password`,
    method: 'PUT',
    data
  })
}

export const updateElderProfile = (id, data) => {
  return request({
    url: `/api/laoren/${id}/profile`,
    method: 'PUT',
    data
  })
}