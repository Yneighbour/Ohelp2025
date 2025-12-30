import request from '../utils/request'

export const getJinjiqiuzhuList = () => {
  return request({
    url: '/api/jinjiqiuzhu',
    method: 'GET'
  })
}

export const createJinjiqiuzhu = (data) => {
  return request({
    url: '/api/jinjiqiuzhu',
    method: 'POST',
    data
  })
}

export const updateJinjiqiuzhu = (id, data) => {
  return request({
    url: `/api/jinjiqiuzhu/${id}`,
    method: 'PUT',
    data
  })
}

export const deleteJinjiqiuzhu = (id) => {
  return request({
    url: `/api/jinjiqiuzhu/${id}`,
    method: 'DELETE'
  })
}

export const getJinjiqiuzhuById = (id) => {
  return request({
    url: `/api/jinjiqiuzhu/${id}`,
    method: 'GET'
  })
}

export const getJinjiqiuzhuByLaoren = (laorenId) => {
  return request({
    url: `/api/jinjiqiuzhu/laoren/${laorenId}`,
    method: 'GET'
  })
}