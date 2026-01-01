import { defineStore } from 'pinia'
import { refreshToken as refreshTokenAPI } from '../api/auth'

export const useTokenStore = defineStore('token', {
  state: () => ({
    token: localStorage.getItem('token') || null,
    refreshToken: localStorage.getItem('refreshToken') || null,
    tokenExpires: localStorage.getItem('tokenExpires') || null
  }),
  
  actions: {
    setToken(token, refreshToken, expires) {
      this.token = token
      this.refreshToken = refreshToken
      this.tokenExpires = expires
      
      localStorage.setItem('token', token)
      localStorage.setItem('refreshToken', refreshToken)
      localStorage.setItem('tokenExpires', expires)
    },
    
    clearToken() {
      this.token = null
      this.refreshToken = null
      this.tokenExpires = null
      
      localStorage.removeItem('token')
      localStorage.removeItem('refreshToken')
      localStorage.removeItem('tokenExpires')
    },
    
    async refreshToken() {
      if (!this.refreshToken) {
        throw new Error('No refresh token available')
      }
      
      try {
        const response = await refreshTokenAPI()
        // 使用统一的API响应结构
        if (response.success) {
          this.setToken(response.data.token, response.data.refreshToken, response.data.expires)
          return response.data
        } else {
          throw new Error(response.message || '刷新令牌失败')
        }
      } catch (error) {
        this.clearToken()
        throw error
      }
    }
  }
})