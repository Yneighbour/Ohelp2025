<template>
  <div id="app">
    <!-- 顶部导航栏 -->
    <header v-if="!isLoginPage" class="top-nav">
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
    <aside v-if="!isLoginPage" class="sidebar" v-keyboard-navigation="{ selector: '.nav-item' }">
      <nav class="sidebar-nav">
        <div class="nav-section">
          <div class="nav-section-title">概览</div>
          <router-link v-if="canAccess('/dashboard')" to="/dashboard" class="nav-item">
            <svg class="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <rect x="3" y="3" width="18" height="18" rx="2" ry="2"></rect>
              <line x1="9" y1="9" x2="15" y2="9"></line>
              <line x1="9" y1="15" x2="15" y2="15"></line>
              <line x1="9" y1="9" x2="9" y2="15"></line>
              <line x1="15" y1="9" x2="15" y2="15"></line>
            </svg>
            系统概览
          </router-link>
        </div>

        <div class="nav-section">
          <div class="nav-section-title">核心功能</div>
          <router-link v-if="canAccess('/auth')" to="/auth" class="nav-item">
            <svg class="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <rect x="3" y="11" width="18" height="11" rx="2" ry="2"></rect>
              <circle cx="12" cy="16" r="1"></circle>
              <path d="M7 11V7a5 5 0 0 1 10 0v4"></path>
            </svg>
            认证管理
          </router-link>
          <router-link v-if="canAccess('/user')" to="/user" class="nav-item">
            <svg class="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
              <circle cx="9" cy="7" r="4"></circle>
              <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
              <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
            </svg>
            用户管理
          </router-link>
          <router-link v-if="canAccess('/elder')" to="/elder" class="nav-item">
            <svg class="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2"></path>
              <circle cx="9" cy="7" r="4"></circle>
              <text x="9" y="12" text-anchor="middle" font-size="8" fill="currentColor">👴</text>
            </svg>
            老人信息
          </router-link>
        </div>

        <div class="nav-section">
          <div class="nav-section-title">业务管理</div>
          <router-link v-if="canAccess('/activity')" to="/activity" class="nav-item">
            <svg class="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <path d="M14.828 14.828a4 4 0 0 1-5.656 0"></path>
              <path d="M9 10h1.586a1 1 0 0 1 .707.293l.707.707A1 1 0 0 0 13.414 11H15"></path>
              <path d="M15 15a6 6 0 1 1-12 0 6 6 0 0 1 12 0z"></path>
            </svg>
            活动管理
          </router-link>
          <router-link v-if="canAccess('/emergency')" to="/emergency" class="nav-item">
            <svg class="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <circle cx="12" cy="12" r="10"></circle>
              <line x1="12" y1="8" x2="12" y2="12"></line>
              <line x1="12" y1="16" x2="12.01" y2="16"></line>
            </svg>
            紧急求助
          </router-link>
          <router-link v-if="canAccess('/health')" to="/health" class="nav-item">
            <svg class="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"></path>
            </svg>
            健康管理
          </router-link>
          <router-link v-if="canAccess('/serviceorder')" to="/serviceorder" class="nav-item">
            <svg class="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <path d="M9 12l2 2 4-4"></path>
              <path d="M21 12c.552 0 1 .448 1 1v6c0 .552-.448 1-1 1H3c-.552 0-1-.448-1-1v-6c0-.552.448-1 1-1h18z"></path>
              <path d="M3 6h18"></path>
              <path d="M3 6c0-1.105.895-2 2-2h14c1.105 0 2 .895 2 2v0c0 1.105-.895 2-2 2H5c-1.105 0-2-.895-2-2v0z"></path>
            </svg>
            服务订单
          </router-link>
        </div>

        <div class="nav-section">
          <div class="nav-section-title">系统管理</div>
          <router-link v-if="canAccess('/worker')" to="/worker" class="nav-item">
            <svg class="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <path d="M14.7 6.3a1 1 0 0 0 0 1.4l1.6 1.6a1 1 0 0 0 1.4 0l3.77-3.77a6 6 0 0 1-7.94 7.94l-6.91 6.91a2.12 2.12 0 0 1-3-3l6.91-6.91a6 6 0 0 1 7.94-7.94l-3.76 3.76z"></path>
            </svg>
            工作人员
          </router-link>
          <router-link v-if="canAccess('/file')" to="/file" class="nav-item">
            <svg class="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
              <polyline points="14,2 14,8 20,8"></polyline>
            </svg>
            文件管理
          </router-link>
        </div>
      </nav>
    </aside>

    <!-- 主内容区域 -->
    <main :class="isLoginPage ? 'main-content login-main' : 'main-content'">
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
    <nav v-if="!isLoginPage" id="mobile-nav" class="mobile-nav" v-keyboard-navigation="{ selector: '.mobile-nav-item' }">
      <router-link v-if="canAccess('/dashboard')" to="/dashboard" class="mobile-nav-item">
        <svg class="mobile-nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
          <rect x="3" y="3" width="18" height="18" rx="2" ry="2"></rect>
          <line x1="9" y1="9" x2="15" y2="9"></line>
          <line x1="9" y1="15" x2="15" y2="15"></line>
        </svg>
        <span class="mobile-nav-label">概览</span>
      </router-link>

      <router-link v-if="canAccess('/user')" to="/user" class="mobile-nav-item">
        <svg class="mobile-nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
          <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
          <circle cx="9" cy="7" r="4"></circle>
          <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
          <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
        </svg>
        <span class="mobile-nav-label">用户</span>
      </router-link>

      <router-link v-if="canAccess('/elder')" to="/elder" class="mobile-nav-item">
        <svg class="mobile-nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
          <circle cx="12" cy="8" r="4"></circle>
          <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
        </svg>
        <span class="mobile-nav-label">老人</span>
      </router-link>

      <router-link v-if="canAccess('/emergency')" to="/emergency" class="mobile-nav-item">
        <svg class="mobile-nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
          <circle cx="12" cy="12" r="10"></circle>
          <line x1="12" y1="8" x2="12" y2="12"></line>
          <line x1="12" y1="16" x2="12.01" y2="16"></line>
        </svg>
        <span class="mobile-nav-label">求助</span>
      </router-link>

      <router-link v-if="canAccess('/activity')" to="/activity" class="mobile-nav-item">
        <svg class="mobile-nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
          <circle cx="12" cy="12" r="10"></circle>
          <path d="M9.09 9a3 3 0 0 1 5.83 1c0 2-3 3-3 3"></path>
          <path d="M12 17h.01"></path>
        </svg>
        <span class="mobile-nav-label">活动</span>
      </router-link>
    </nav>
  </div>
</template>

<script>
import PageTransition from './components/common/PageTransition.vue'
import LoadingIndicator from './components/common/LoadingIndicator.vue'
import MessageToast from './components/common/MessageToast.vue'
import { MessageService } from './shared/message'
import { isPathAllowed } from './router/roleAccess.js'

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

    currentRole() {
      return localStorage.getItem('role') || ''
    }
  },
  mounted() {
    // 初始化全局消息服务
    MessageService.init(this.$refs.messageToast)

    // 监听全局加载状态
    this.setupGlobalLoading()
  },
  methods: {
    canAccess(path) {
      return isPathAllowed(this.currentRole, path)
    },

    getCurrentRoleLabel() {
      const role = localStorage.getItem('role')
      if (!role) return '未登录'
      const map = {
        admin: '管理员',
        manager: '经理',
        staff: '员工',
        worker: '工作人员',
        doctor: '医生',
        relative: '家属',
        user: '用户'
      }
      return map[role] || role
    },

    handleLogout() {
      // 只清理本项目登录态（避免误删其他本地数据）
      localStorage.removeItem('token')
      localStorage.removeItem('userId')
      localStorage.removeItem('role')

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