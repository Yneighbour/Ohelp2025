import request from '../utils/request'

export const getFuwuleixingList = () => {
  return request({
    url: '/api/fuwuleixing',
    method: 'GET'
  })
}

export const createFuwuleixing = (data) => {
  return request({
    url: '/api/fuwuleixing',
    method: 'POST',
    data
  })
}

export const updateFuwuleixing = (id, data) => {
  return request({
    url: `/api/fuwuleixing/${id}`,
    method: 'PUT',
    data
  })
}

export const deleteFuwuleixing = (id) => {
  return request({
    url: `/api/fuwuleixing/${id}`,
    method: 'DELETE'
  })
}