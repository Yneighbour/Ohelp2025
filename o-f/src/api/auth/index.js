import request from '../../utils/request'

// Token management (token module)
export const refreshToken = () => {
  return request({
    url: '/api/token/refresh',
    method: 'POST'
  })
}