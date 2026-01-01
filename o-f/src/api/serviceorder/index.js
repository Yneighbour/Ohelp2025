import request from '../../utils/request'

// Service category management (fuwuleixing module)
export const getServiceCategoryList = () => {
  return request({
    url: '/api/fuwuleixing',
    method: 'GET'
  })
}

export const createServiceCategory = (data) => {
  return request({
    url: '/api/fuwuleixing',
    method: 'POST',
    data
  })
}

export const updateServiceCategory = (id, data) => {
  return request({
    url: `/api/fuwuleixing/${id}`,
    method: 'PUT',
    data
  })
}

export const deleteServiceCategory = (id) => {
  return request({
    url: `/api/fuwuleixing/${id}`,
    method: 'DELETE'
  })
}

// Service item management (fuwuxiangmu module)
export const getServiceItemList = () => {
  return request({
    url: '/api/fuwuxiangmu',
    method: 'GET'
  })
}

export const getServiceItemByType = (typeId) => {
  return request({
    url: `/api/fuwuxiangmu/type/${typeId}`,
    method: 'GET'
  })
}

export const getServiceItemByStatus = (status) => {
  return request({
    url: `/api/fuwuxiangmu/shangxia/${status}`,
    method: 'GET'
  })
}

export const createServiceItem = (data) => {
  return request({
    url: '/api/fuwuxiangmu',
    method: 'POST',
    data
  })
}

export const updateServiceItem = (id, data) => {
  return request({
    url: `/api/fuwuxiangmu/${id}`,
    method: 'PUT',
    data
  })
}

export const deleteServiceItem = (id) => {
  return request({
    url: `/api/fuwuxiangmu/${id}`,
    method: 'DELETE'
  })
}

// Service purchase management (fuwugoumai module)
export const getServicePurchaseList = () => {
  return request({
    url: '/api/fuwugoumai',
    method: 'GET'
  })
}

export const getServicePurchaseById = (id) => {
  return request({
    url: `/api/fuwugoumai/${id}`,
    method: 'GET'
  })
}

export const createServicePurchase = (data) => {
  return request({
    url: '/api/fuwugoumai',
    method: 'POST',
    data
  })
}

export const updateServicePurchase = (id, data) => {
  return request({
    url: `/api/fuwugoumai/${id}`,
    method: 'PUT',
    data
  })
}

export const deleteServicePurchase = (id) => {
  return request({
    url: `/api/fuwugoumai/${id}`,
    method: 'DELETE'
  })
}

export const getServicePurchaseByElder = (uuid) => {
  return request({
    url: `/api/fuwugoumai/laoren/${uuid}`,
    method: 'GET'
  })
}

export const getServicePurchaseByStatus = (status) => {
  return request({
    url: `/api/fuwugoumai/status/${status}`,
    method: 'GET'
  })
}

// Service request management (service-requests module)
export const getServiceRequests = () => {
  return request({
    url: '/api/service-requests',
    method: 'GET'
  })
}

export const getServiceRequestById = (id) => {
  return request({
    url: `/api/service-requests/${id}`,
    method: 'GET'
  })
}

export const createServiceRequest = (data) => {
  return request({
    url: '/api/service-requests',
    method: 'POST',
    data
  })
}

export const updateServiceRequest = (id, data) => {
  return request({
    url: `/api/service-requests/${id}`,
    method: 'PUT',
    data
  })
}

export const deleteServiceRequest = (id) => {
  return request({
    url: `/api/service-requests/${id}`,
    method: 'DELETE'
  })
}

export const getServiceRequestsByStatus = (status) => {
  return request({
    url: `/api/service-requests/status/${status}`,
    method: 'GET'
  })
}

export const getServiceRequestsByPriority = (priority) => {
  return request({
    url: `/api/service-requests/priority/${priority}`,
    method: 'GET'
  })
}

// Get service requests by elder ID
export const getServiceRequestsByElder = (laorenId) => {
  return request({
    url: `/api/service-requests/laoren/${laorenId}`,
    method: 'GET'
  })
}

// Review management (pingjia module)
export const getReviewList = () => {
  return request({
    url: '/api/pingjia',
    method: 'GET'
  })
}

export const createReview = (data) => {
  return request({
    url: '/api/pingjia',
    method: 'POST',
    data
  })
}

export const updateReview = (id, data) => {
  return request({
    url: `/api/pingjia/${id}`,
    method: 'PUT',
    data
  })
}

export const deleteReview = (id) => {
  return request({
    url: `/api/pingjia/${id}`,
    method: 'DELETE'
  })
}

export const getReviewById = (id) => {
  return request({
    url: `/api/pingjia/${id}`,
    method: 'GET'
  })
}

export const getReviewByElder = (elderId) => {
  return request({
    url: `/api/pingjia/laoren/${elderId}`,
    method: 'GET'
  })
}

export const getReviewByWorker = (workerId) => {
  return request({
    url: `/api/pingjia/laogong/${workerId}`,
    method: 'GET'
  })
}

// Care management (zhaoliao module)
export const getCareList = () => {
  return request({
    url: '/api/zhaoliao',
    method: 'GET'
  })
}

export const createCare = (data) => {
  return request({
    url: '/api/zhaoliao',
    method: 'POST',
    data
  })
}

export const updateCare = (id, data) => {
  return request({
    url: `/api/zhaoliao/${id}`,
    method: 'PUT',
    data
  })
}

export const deleteCare = (id) => {
  return request({
    url: `/api/zhaoliao/${id}`,
    method: 'DELETE'
  })
}

export const getCareById = (id) => {
  return request({
    url: `/api/zhaoliao/${id}`,
    method: 'GET'
  })
}

export const getCareByElder = (elderId) => {
  return request({
    url: `/api/zhaoliao/laoren/${elderId}`,
    method: 'GET'
  })
}

export const getCareByWorker = (workerId) => {
  return request({
    url: `/api/zhaoliao/laogong/${workerId}`,
    method: 'GET'
  })
}