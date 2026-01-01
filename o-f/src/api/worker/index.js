import request from '../../utils/request'

// Worker management (laogong module)
export const workerLogin = (data) => {
  return request({
    url: '/api/laogong/login',
    method: 'POST',
    data
  })
}

export const getWorkerList = () => {
  return request({
    url: '/api/laogong',
    method: 'GET'
  })
}

export const getWorkerById = (id) => {
  return request({
    url: `/api/laogong/${id}`,
    method: 'GET'
  })
}

export const createWorker = (data) => {
  return request({
    url: '/api/laogong',
    method: 'POST',
    data
  })
}

export const updateWorker = (id, data) => {
  return request({
    url: `/api/laogong/${id}`,
    method: 'PUT',
    data
  })
}

export const deleteWorker = (id) => {
  return request({
    url: `/api/laogong/${id}`,
    method: 'DELETE'
  })
}

export const updateWorkerPassword = (id, data) => {
  return request({
    url: `/api/laogong/${id}/password`,
    method: 'PUT',
    data
  })
}