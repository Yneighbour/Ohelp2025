import request from '../utils/request'

export const refreshToken = () => {
  return request({
    url: '/api/token/refresh',
    method: 'POST'
  })
}