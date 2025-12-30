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
        const data = await getUserInfo()
        this.userInfo = data
        return data
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