<template>
  <header class="site-header">
    <div class="header-container">
      <div class="logo">
        <h1>OHelp</h1>
      </div>
      <nav class="nav">
        <router-link to="/home" :class="{ active: $route.path === '/home' }">主页</router-link>
        <router-link to="/dashboard" :class="{ active: $route.path === '/dashboard' }">仪表盘</router-link>
        <router-link to="/elder" :class="{ active: $route.path === '/elder' }">老人档案</router-link>
        <router-link to="/service-request" :class="{ active: $route.path === '/service-request' }">服务请求</router-link>
      </nav>
      <div class="user-info" v-if="userStore.userInfo">
        <div class="user-details">
          <span class="user-name">{{ userStore.userInfo.username }}</span>
          <span class="user-role">{{ getRoleLabel(userStore.userInfo.role) }}</span>
        </div>
        <div class="user-actions">
          <button class="btn btn-secondary btn-sm" @click="handleLogout">退出登录</button>
        </div>
      </div>
    </div>
  </header>
</template>

<script>
import { useUserStore } from '../store/user'
import { useTokenStore } from '../store/token'
import { logout } from '../api/auth'
import { useRouter } from 'vue-router'

export default {
  name: 'SiteHeader',
  setup() {
    const userStore = useUserStore()
    const tokenStore = useTokenStore()
    const router = useRouter()
    
    // 获取角色中文标签
    const getRoleLabel = (role) => {
      const roleMap = {
        admin: '管理员',
        worker: '护理人员',
        relative: '家属',
        elder: '老人'
      }
      return roleMap[role] || role
    }
    
    // 退出登录
    const handleLogout = async () => {
      try {
        await logout()
      } catch (error) {
        console.error('Logout API error:', error)
      } finally {
        // 清除本地存储的用户信息和token
        userStore.clearUserInfo()
        tokenStore.clearToken()
        // 重定向到登录页
        router.push('/login')
      }
    }
    
    return {
      userStore,
      getRoleLabel,
      handleLogout
    }
  }
}
</script>

<style scoped>
.header-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1.5rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.logo h1 {
  margin: 0;
  font-size: 1.75rem;
  font-weight: 700;
  color: white;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.nav {
  display: flex;
  gap: 2rem;
  align-items: center;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.user-details {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
}

.user-name {
  color: white;
  font-weight: 600;
  font-size: 0.9rem;
}

.user-role {
  color: rgba(255, 255, 255, 0.8);
  font-size: 0.8rem;
  background-color: rgba(255, 255, 255, 0.2);
  padding: 0.1rem 0.5rem;
  border-radius: 10px;
}

.user-actions {
  display: flex;
  gap: 0.5rem;
}

@media (max-width: 768px) {
  .header-container {
    flex-direction: column;
    gap: 1rem;
  }
  
  .logo h1 {
    font-size: 1.5rem;
  }
  
  .nav {
    flex-direction: column;
    gap: 0.5rem;
  }
  
  .user-info {
    flex-direction: column;
    gap: 0.5rem;
  }
  
  .user-details {
    align-items: center;
  }
}
</style>
