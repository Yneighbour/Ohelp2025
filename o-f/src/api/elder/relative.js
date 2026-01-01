import request from '../../utils/request'

// Elderly relatives management (qinshu module)
export const getRelativeList = () => {
  return request({
    url: '/api/qinshu',
    method: 'GET'
  })
}

export const createRelative = (data) => {
  return request({
    url: '/api/qinshu',
    method: 'POST',
    data
  })
}

export const updateRelative = (id, data) => {
  return request({
    url: `/api/qinshu/${id}`,
    method: 'PUT',
    data
  })
}

export const deleteRelative = (id) => {
  return request({
    url: `/api/qinshu/${id}`,
    method: 'DELETE'
  })
}

export const getRelativeById = (id) => {
  return request({
    url: `/api/qinshu/${id}`,
    method: 'GET'
  })
}

export const getRelativeByElder = (elderId) => {
  return request({
    url: `/api/qinshu/laoren/${elderId}`,
    method: 'GET'
  })
}