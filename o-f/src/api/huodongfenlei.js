import request from '../utils/request'

export const getHuodongfenleiList = () => {
  return request({
    url: '/api/huodongfenlei',
    method: 'GET'
  })
}

export const createHuodongfenlei = (data) => {
  return request({
    url: '/api/huodongfenlei',
    method: 'POST',
    data
  })
}

export const updateHuodongfenlei = (id, data) => {
  return request({
    url: `/api/huodongfenlei/${id}`,
    method: 'PUT',
    data
  })
}

export const deleteHuodongfenlei = (id) => {
  return request({
    url: `/api/huodongfenlei/${id}`,
    method: 'DELETE'
  })
}

export const getHuodongfenleiById = (id) => {
  return request({
    url: `/api/huodongfenlei/${id}`,
    method: 'GET'
  })
}