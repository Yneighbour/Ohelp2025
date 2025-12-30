import request from '../utils/request'

export const getZhaoliaoList = () => {
  return request({
    url: '/api/zhaoliao',
    method: 'GET'
  })
}

export const createZhaoliao = (data) => {
  return request({
    url: '/api/zhaoliao',
    method: 'POST',
    data
  })
}

export const updateZhaoliao = (id, data) => {
  return request({
    url: `/api/zhaoliao/${id}`,
    method: 'PUT',
    data
  })
}

export const deleteZhaoliao = (id) => {
  return request({
    url: `/api/zhaoliao/${id}`,
    method: 'DELETE'
  })
}

export const getZhaoliaoById = (id) => {
  return request({
    url: `/api/zhaoliao/${id}`,
    method: 'GET'
  })
}

export const getZhaoliaoByLaoren = (laorenId) => {
  return request({
    url: `/api/zhaoliao/laoren/${laorenId}`,
    method: 'GET'
  })
}

export const getZhaoliaoByLaogong = (laogongId) => {
  return request({
    url: `/api/zhaoliao/laogong/${laogongId}`,
    method: 'GET'
  })
}