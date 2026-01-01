import { defineStore } from 'pinia'
import { getUserInfo } from '../api/user'

export const useUserStore = defineStore('user', {
  state: () => ({
    userInfo: null,
    loading: false,
    error: null
  }),
  
  getters: {
    isAuthenticated: (state) => !!state.userInfo
  },
  
  actions: {
    async fetchUserInfo() {
      this.loading = true
      this.error = null
      try {
        const response = await getUserInfo()
        // 使用统一的API响应结构
        if (response.success) {
          this.userInfo = response.data
          return response.data
        } else {
          throw new Error(response.message || '获取用户信息失败')
        }
      } catch (error) {
        this.error = error
        throw error
      } finally {
        this.loading = false
      }
    },
    
    setUserInfo(userInfo) {
      this.userInfo = userInfo
    },
    
    clearUserInfo() {
      this.userInfo = null
    }
  }
})