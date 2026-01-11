<template>
  <div id="app">
    <!-- 顶部导航栏 -->
    <header v-if="showShell" class="top-nav">
      <div class="logo">Ohelp2025</div>
      <div class="nav-actions">
        <div class="user-menu">
          <svg class="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
            <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
            <circle cx="12" cy="7" r="4"></circle>
          </svg>
          <span>{{ getCurrentRoleLabel() }}</span>
        </div>

        <button type="button" class="logout-btn" @click="handleLogout">
          退出
        </button>
      </div>
    </header>

    <!-- 侧边栏导航 -->
    <aside v-if="showSidebar" class="sidebar" v-keyboard-navigation="{ selector: '.nav-item' }">
      <nav class="sidebar-nav">
        <div v-for="section in backendMenu" :key="section.title" class="nav-section">
          <div class="nav-section-title">{{ section.title }}</div>
          <router-link
            v-for="item in section.items"
            :key="item.to"
            :to="item.to"
            class="nav-item"
          >
            <svg class="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <rect x="4" y="4" width="16" height="16" rx="3" ry="3"></rect>
              <path d="M8 12h8"></path>
            </svg>
            {{ item.label }}
          </router-link>
        </div>
      </nav>
    </aside>

    <!-- 主内容区域 -->
    <main :class="showShell ? 'main-content' : 'main-content login-main'">
      <div class="content-wrapper">
        <PageTransition name="router-view">
          <router-view v-slot="{ Component }">
            <Transition
              name="page"
              mode="out-in"
              :duration="300"
              @enter="onPageEnter"
              @leave="onPageLeave"
            >
              <component :is="Component" :key="$route.path" />
            </Transition>
          </router-view>
        </PageTransition>
      </div>
    </main>

    <!-- 全局加载指示器 -->
    <LoadingIndicator
      :visible="globalLoading"
      text="加载中..."
      :fullscreen="true"
      blur
    />

    <!-- 全局消息提示 -->
    <MessageToast ref="messageToast" />

    <!-- 移动端底部导航栏 -->
    <nav v-if="!isLoginPage && !isUserApp && currentRole !== 'USER'" id="mobile-nav" class="mobile-nav" v-keyboard-navigation="{ selector: '.mobile-nav-item' }">
      <router-link v-for="it in mobileNavItems" :key="it.to" :to="it.to" class="mobile-nav-item">
        <svg class="mobile-nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
          <rect x="3" y="3" width="18" height="18" rx="2" ry="2"></rect>
          <line x1="8" y1="12" x2="16" y2="12"></line>
        </svg>
        <span class="mobile-nav-label">{{ it.label }}</span>
      </router-link>
    </nav>
  </div>
</template>

<script>
import PageTransition from './components/common/PageTransition.vue'
import LoadingIndicator from './components/common/LoadingIndicator.vue'
import MessageToast from './components/common/MessageToast.vue'
import { MessageService } from './shared/message'

import { getMenuByRole } from './layouts/menu.config.js'

export default {
  name: 'App',
  components: {
    PageTransition,
    LoadingIndicator,
    MessageToast
  },
  data() {
    return {
      globalLoading: false
    }
  },
  computed: {
    isLoginPage() {
      return this.$route?.meta?.public === true && this.$route?.path === '/login'
    },

    isUserApp() {
      return String(this.$route?.path || '').startsWith('/app')
    },

    showShell() {
      return !this.isLoginPage && !this.isUserApp
    },

    currentRole() {
      return localStorage.getItem('role') || ''
    },

    backendMenu() {
      return getMenuByRole(this.currentRole)
    },

    showSidebar() {
      return this.showShell && this.currentRole !== 'USER'
    },

    mobileNavItems() {
      if (this.currentRole === 'WORKER') {
        return [
          { to: '/worker/tasks', label: '任务' },
          { to: '/worker/records', label: '记录' },
          { to: '/emergency', label: '求助' },
          { to: '/serviceorder', label: '订单' }
        ]
      }
      return [
        { to: '/admin/dashboard', label: '概览' },
        { to: '/user', label: '用户' },
        { to: '/elder', label: '老人' },
        { to: '/emergency', label: '求助' },
        { to: '/activity', label: '活动' }
      ]
    }
  },
  mounted() {
    // 初始化全局消息服务
    MessageService.init(this.$refs.messageToast)

    // 监听全局加载状态
    this.setupGlobalLoading()
  },
  methods: {
    getCurrentRoleLabel() {
      const role = localStorage.getItem('role')
      if (!role) return '未登录'
      const map = {
        ADMIN: '管理员',
        WORKER: '工作人员',
        USER: '用户'
      }
      return map[role] || role
    },

    handleLogout() {
      // 只清理本项目登录态（避免误删其他本地数据）
      localStorage.removeItem('token')
      localStorage.removeItem('role')
      localStorage.removeItem('userId')

      if (this.$message) this.$message.success('已退出登录')
      this.$router.replace('/login')
    },

    onPageEnter(el, done) {
      // 页面进入时的回调
      // 可以在这里添加页面加载完成后的逻辑
      done()
    },

    onPageLeave(el, done) {
      // 页面离开时的回调
      // 可以在这里添加页面离开前的清理逻辑
      done()
    },

    setupGlobalLoading() {
      // 可以通过全局状态管理来控制加载状态
      // 这里提供一个简单的示例
      window.showGlobalLoading = (show = true) => {
        this.globalLoading = show
      }

      // 自动隐藏加载状态（可选）
      this.$watch('globalLoading', (newVal) => {
        if (newVal) {
          setTimeout(() => {
            this.globalLoading = false
          }, 3000) // 3秒后自动隐藏
        }
      })
    }
  }
}
</script>

<style scoped>
/* App.vue 特定样式 */
#app {
  background-color: var(--background-color);
}

.logout-btn {
  height: 34px;
  padding: 0 12px;
  border-radius: var(--border-radius-md);
  border: 1px solid var(--border-color);
  background: var(--white);
  color: var(--text-secondary);
  font-weight: 600;
  cursor: pointer;
  transition: all var(--transition-fast);
}

.logout-btn:hover {
  color: var(--primary-color);
  border-color: rgba(124, 58, 237, 0.35);
  background: rgba(124, 58, 237, 0.06);
}

.login-main {
  margin-left: 0;
  padding: 0;
}

/* 自定义侧边栏导航项样式 */
.sidebar-nav .nav-item {
  position: relative;
}

.sidebar-nav .nav-item.router-link-active {
  background-color: var(--nav-active-bg);
  color: var(--primary-color);
  border-right: 3px solid var(--primary-color);
}

.sidebar-nav .nav-item.router-link-active::before {
  content: '';
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 4px;
  height: 60%;
  background: var(--primary-gradient);
  border-radius: 0 2px 2px 0;
}

/* 移动端底部导航 */
.mobile-nav {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  height: 60px;
  background: var(--white);
  border-top: 1px solid var(--border-color);
  box-shadow: 0 -2px 12px rgba(124, 58, 237, 0.08);
  display: none;
  flex-direction: row;
  z-index: 1000;
}

.mobile-nav-item {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 60px;
  color: var(--text-secondary);
  text-decoration: none;
  transition: all var(--transition-fast);
  position: relative;
}

.mobile-nav-item:hover {
  color: var(--primary-color);
}

.mobile-nav-item.router-link-active {
  color: var(--primary-color);
}

.mobile-nav-item.router-link-active::before {
  content: '';
  position: absolute;
  top: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 30px;
  height: 3px;
  background: var(--primary-gradient);
  border-radius: 0 0 3px 3px;
}

.mobile-nav-icon {
  width: 20px;
  height: 20px;
  margin-bottom: 2px;
  stroke-width: 2;
}

.mobile-nav-label {
  font-size: 11px;
  font-weight: 500;
}

/* 响应式适配 */
@media (max-width: 1024px) {
  .sidebar {
    transform: translateX(-100%);
  }

  .main-content {
    margin-left: 0;
  }

  /* 添加移动端菜单按钮 */
  .mobile-menu-btn {
    display: block;
    background: none;
    border: none;
    color: var(--text-secondary);
    font-size: 24px;
    cursor: pointer;
    padding: 8px;
    border-radius: 4px;
    transition: background-color var(--transition-fast);
  }

  .mobile-menu-btn:hover {
    background-color: var(--nav-hover-bg);
  }

  .sidebar.open {
    transform: translateX(0);
  }

  /* 遮罩层 */
  .sidebar-overlay {
    position: fixed;
    top: var(--header-height);
    left: 0;
    right: 0;
    bottom: 0;
    background-color: rgba(0, 0, 0, 0.5);
    z-index: 1049;
    opacity: 0;
    visibility: hidden;
    transition: all var(--transition-base);
  }

  .sidebar-overlay.open {
    opacity: 1;
    visibility: visible;
  }
}

@media (max-width: 768px) {
  /* 在移动端显示底部导航 */
  .mobile-nav {
    display: flex !important;
  }

  /* 调整主内容区域，为底部导航留出空间 */
  .main-content {
    padding-bottom: 60px;
  }

  .content-wrapper {
    min-height: calc(100vh - var(--header-height) - 60px);
  }

  /* 隐藏侧边栏，只显示顶部导航 */
  .sidebar {
    display: none;
  }

  /* 调整顶部导航，添加移动端菜单按钮 */
  .top-nav {
    position: relative;
  }

  .top-nav .logo {
    position: absolute;
    left: 50%;
    transform: translateX(-50%);
  }

  .top-nav .nav-actions {
    margin-left: auto;
  }
}
</style>