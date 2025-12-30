import request from '../utils/request'

export const laorenRegister = (data) => {
  return request({
    url: '/api/laoren/register',
    method: 'POST',
    data
  })
}

export const laorenLogin = (data) => {
  return request({
    url: '/api/laoren/login',
    method: 'POST',
    data
  })
}

export const getLaorenList = () => {
  return request({
    url: '/api/laoren',
    method: 'GET'
  })
}

export const getLaorenById = (id) => {
  return request({
    url: `/api/laoren/${id}`,
    method: 'GET'
  })
}

export const updateLaoren = (id, data) => {
  return request({
    url: `/api/laoren/${id}`,
    method: 'PUT',
    data
  })
}

export const deleteLaoren = (id) => {
  return request({
    url: `/api/laoren/${id}`,
    method: 'DELETE'
  })
}

export const updateLaorenPassword = (id, data) => {
  return request({
    url: `/api/laoren/${id}/password`,
    method: 'PUT',
    data
  })
}

export const updateLaorenProfile = (id, data) => {
  return request({
    url: `/api/laoren/${id}/profile`,
    method: 'PUT',
    data
  })
}