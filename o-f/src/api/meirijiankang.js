import request from '../utils/request'

export const getMeirijiankangList = () => {
  return request({
    url: '/api/meirijiankang',
    method: 'GET'
  })
}

export const createMeirijiankang = (data) => {
  return request({
    url: '/api/meirijiankang',
    method: 'POST',
    data
  })
}

export const updateMeirijiankang = (id, data) => {
  return request({
    url: `/api/meirijiankang/${id}`,
    method: 'PUT',
    data
  })
}

export const deleteMeirijiankang = (id) => {
  return request({
    url: `/api/meirijiankang/${id}`,
    method: 'DELETE'
  })
}

export const getMeirijiankangById = (id) => {
  return request({
    url: `/api/meirijiankang/${id}`,
    method: 'GET'
  })
}

export const getMeirijiankangByLaoren = (laorenId) => {
  return request({
    url: `/api/meirijiankang/laoren/${laorenId}`,
    method: 'GET'
  })
}