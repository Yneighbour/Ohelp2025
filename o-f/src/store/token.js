import { defineStore } from 'pinia'
import { refreshToken as refreshTokenAPI } from '../api/token'

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
        const data = await refreshTokenAPI()
        this.setToken(data.token, data.refreshToken, data.expires)
        return data
      } catch (error) {
        this.clearToken()
        throw error
      }
    }
  }
})