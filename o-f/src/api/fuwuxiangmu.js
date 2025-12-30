import request from '../utils/request'

export const getFuwuxiangmuList = () => {
  return request({
    url: '/api/fuwuxiangmu',
    method: 'GET'
  })
}

export const getFuwuxiangmuByType = (typeId) => {
  return request({
    url: `/api/fuwuxiangmu/type/${typeId}`,
    method: 'GET'
  })
}

export const getFuwuxiangmuByStatus = (status) => {
  return request({
    url: `/api/fuwuxiangmu/shangxia/${status}`,
    method: 'GET'
  })
}

export const createFuwuxiangmu = (data) => {
  return request({
    url: '/api/fuwuxiangmu',
    method: 'POST',
    data
  })
}

export const updateFuwuxiangmu = (id, data) => {
  return request({
    url: `/api/fuwuxiangmu/${id}`,
    method: 'PUT',
    data
  })
}

export const deleteFuwuxiangmu = (id) => {
  return request({
    url: `/api/fuwuxiangmu/${id}`,
    method: 'DELETE'
  })
}