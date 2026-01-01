import request from '../../utils/request'

// Emergency assistance management (jinjiqiuzhu module)
export const getEmergencyAssistanceList = () => {
  return request({
    url: '/api/jinjiqiuzhu',
    method: 'GET'
  })
}

export const createEmergencyAssistance = (data) => {
  return request({
    url: '/api/jinjiqiuzhu',
    method: 'POST',
    data
  })
}

export const updateEmergencyAssistance = (id, data) => {
  return request({
    url: `/api/jinjiqiuzhu/${id}`,
    method: 'PUT',
    data
  })
}

export const deleteEmergencyAssistance = (id) => {
  return request({
    url: `/api/jinjiqiuzhu/${id}`,
    method: 'DELETE'
  })
}

export const getEmergencyAssistanceById = (id) => {
  return request({
    url: `/api/jinjiqiuzhu/${id}`,
    method: 'GET'
  })
}

export const getEmergencyAssistanceByElder = (elderId) => {
  return request({
    url: `/api/jinjiqiuzhu/laoren/${elderId}`,
    method: 'GET'
  })
}