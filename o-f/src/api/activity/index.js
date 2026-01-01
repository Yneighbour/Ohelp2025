import request from '../../utils/request'

// Activity category management (huodongfenlei module)
export const getActivityCategoryList = () => {
  return request({
    url: '/api/huodongfenlei',
    method: 'GET'
  })
}

export const createActivityCategory = (data) => {
  return request({
    url: '/api/huodongfenlei',
    method: 'POST',
    data
  })
}

export const updateActivityCategory = (id, data) => {
  return request({
    url: `/api/huodongfenlei/${id}`,
    method: 'PUT',
    data
  })
}

export const deleteActivityCategory = (id) => {
  return request({
    url: `/api/huodongfenlei/${id}`,
    method: 'DELETE'
  })
}

export const getActivityCategoryById = (id) => {
  return request({
    url: `/api/huodongfenlei/${id}`,
    method: 'GET'
  })
}

// Activity information management (huodongxinxi module)
export const getActivityInfoList = () => {
  return request({
    url: '/api/huodongxinxi',
    method: 'GET'
  })
}

export const createActivityInfo = (data) => {
  return request({
    url: '/api/huodongxinxi',
    method: 'POST',
    data
  })
}

export const updateActivityInfo = (id, data) => {
  return request({
    url: `/api/huodongxinxi/${id}`,
    method: 'PUT',
    data
  })
}

export const deleteActivityInfo = (id) => {
  return request({
    url: `/api/huodongxinxi/${id}`,
    method: 'DELETE'
  })
}

export const getActivityInfoById = (id) => {
  return request({
    url: `/api/huodongxinxi/${id}`,
    method: 'GET'
  })
}

export const getActivityInfoByCategory = (categoryId) => {
  return request({
    url: `/api/huodongxinxi/category/${categoryId}`,
    method: 'GET'
  })
}

// Visit management (tanwang module)
export const getVisitList = () => {
  return request({
    url: '/api/tanwang',
    method: 'GET'
  })
}

export const createVisit = (data) => {
  return request({
    url: '/api/tanwang',
    method: 'POST',
    data
  })
}

export const updateVisit = (id, data) => {
  return request({
    url: `/api/tanwang/${id}`,
    method: 'PUT',
    data
  })
}

export const deleteVisit = (id) => {
  return request({
    url: `/api/tanwang/${id}`,
    method: 'DELETE'
  })
}

export const getVisitById = (id) => {
  return request({
    url: `/api/tanwang/${id}`,
    method: 'GET'
  })
}

export const getVisitByElder = (elderId) => {
  return request({
    url: `/api/tanwang/laoren/${elderId}`,
    method: 'GET'
  })
}