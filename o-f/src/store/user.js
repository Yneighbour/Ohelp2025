import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
  state: () => ({
    token: localStorage.getItem('token') || null,
    user: null,
    // uiMode: 'admin' | 'elder'
    uiMode: localStorage.getItem('uiMode') || 'admin'
  }),
  actions: {
    setToken(t) {
      this.token = t
      localStorage.setItem('token', t)
    },
    setUIMode(mode) {
      this.uiMode = mode
      localStorage.setItem('uiMode', mode)
      // apply body class for elder mode
      if (mode === 'elder') document.body.classList.add('elder-mode')
      else document.body.classList.remove('elder-mode')
    },
    clear() {
      this.token = null
      this.user = null
      localStorage.removeItem('token')
    }
  }
})
