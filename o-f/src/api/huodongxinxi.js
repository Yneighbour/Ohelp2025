import request from '../utils/request'

export const getHuodongxinxiList = () => {
  return request({
    url: '/api/huodongxinxi',
    method: 'GET'
  })
}

export const createHuodongxinxi = (data) => {
  return request({
    url: '/api/huodongxinxi',
    method: 'POST',
    data
  })
}

export const updateHuodongxinxi = (id, data) => {
  return request({
    url: `/api/huodongxinxi/${id}`,
    method: 'PUT',
    data
  })
}

export const deleteHuodongxinxi = (id) => {
  return request({
    url: `/api/huodongxinxi/${id}`,
    method: 'DELETE'
  })
}

export const getHuodongxinxiById = (id) => {
  return request({
    url: `/api/huodongxinxi/${id}`,
    method: 'GET'
  })
}

export const getHuodongxinxiByCategory = (categoryId) => {
  return request({
    url: `/api/huodongxinxi/category/${categoryId}`,
    method: 'GET'
  })
}