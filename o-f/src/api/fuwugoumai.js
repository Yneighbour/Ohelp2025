import request from '../utils/request'

export const getFuwugoumaiList = () => {
  return request({
    url: '/api/fuwugoumai',
    method: 'GET'
  })
}

export const getFuwugoumaiById = (id) => {
  return request({
    url: `/api/fuwugoumai/${id}`,
    method: 'GET'
  })
}

export const createFuwugoumai = (data) => {
  return request({
    url: '/api/fuwugoumai',
    method: 'POST',
    data
  })
}

export const updateFuwugoumai = (id, data) => {
  return request({
    url: `/api/fuwugoumai/${id}`,
    method: 'PUT',
    data
  })
}

export const deleteFuwugoumai = (id) => {
  return request({
    url: `/api/fuwugoumai/${id}`,
    method: 'DELETE'
  })
}

export const getFuwugoumaiByLaoren = (uuid) => {
  return request({
    url: `/api/fuwugoumai/laoren/${uuid}`,
    method: 'GET'
  })
}

export const getFuwugoumaiByStatus = (status) => {
  return request({
    url: `/api/fuwugoumai/status/${status}`,
    method: 'GET'
  })
}