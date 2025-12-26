<template>
  <header class="site-header">
    <div class="header-content">
      <!-- Logo 和品牌 -->
      <div class="brand">
        <el-icon size="28" class="brand-icon">
          <House />
        </el-icon>
        <div class="brand-text">
          <h1 class="brand-title">OHelp</h1>
          <span class="brand-subtitle">智慧养老服务平台</span>
        </div>
      </div>

      <!-- 导航菜单 -->
      <nav class="nav">
        <router-link to="/" class="nav-item">
          <el-icon><HomeFilled /></el-icon>
          <span>首页</span>
        </router-link>
        <router-link to="/dashboard" class="nav-item">
          <el-icon><DataAnalysis /></el-icon>
          <span>仪表盘</span>
        </router-link>
        <router-link to="/laoren" class="nav-item">
          <el-icon><User /></el-icon>
          <span>老人档案</span>
        </router-link>
        <router-link to="/requests" class="nav-item">
          <el-icon><DocumentAdd /></el-icon>
          <span>服务请求</span>
        </router-link>
      </nav>

      <!-- 右侧功能区 -->
      <div class="header-actions">
        <!-- 老人模式切换 -->
        <div class="mode-switch">
          <el-icon class="mode-icon">
            <Sunny v-if="!isElder" />
            <Moon v-else />
          </el-icon>
          <span class="mode-label">{{ isElder ? '老人模式' : '标准模式' }}</span>
          <el-switch
            v-model="isElder"
            active-color="var(--success)"
            inactive-color="var(--primary)"
            size="small"
          />
        </div>

        <!-- 用户信息 -->
        <div class="user-info">
          <el-avatar size="32" :src="userAvatar" class="user-avatar">
            <el-icon><User /></el-icon>
          </el-avatar>
          <span class="user-name">{{ userName }}</span>
        </div>
      </div>
    </div>
  </header>
</template>

<script>
import { defineComponent, computed } from 'vue'
import { useUserStore } from '../store/user'
import {
  ElSwitch,
  ElIcon,
  ElAvatar
} from 'element-plus'
import {
  House,
  HomeFilled,
  DataAnalysis,
  User,
  DocumentAdd,
  Sunny,
  Moon
} from '@element-plus/icons-vue'

export default defineComponent({
  name: 'SiteHeader',
  components: {
    ElSwitch,
    ElIcon,
    ElAvatar
  },
  setup() {
    const store = useUserStore()

    // 确保body类同步加载
    if (store.uiMode === 'elder') document.body.classList.add('elder-mode')

    const isElder = computed({
      get: () => store.uiMode === 'elder',
      set: v => store.setUIMode(v ? 'elder' : 'admin')
    })

    // 用户信息（暂时使用模拟数据）
    const userName = computed(() => store.user?.name || '管理员')
    const userAvatar = computed(() => store.user?.avatar || '')

    return {
      isElder,
      userName,
      userAvatar,
      // 图标组件
      House,
      HomeFilled,
      DataAnalysis,
      User,
      DocumentAdd,
      Sunny,
      Moon
    }
  }
})
</script>

<style scoped>
.header-content {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 var(--space-4);
  height: 100%;
}

/* 品牌区域 */
.brand {
  display: flex;
  align-items: center;
  gap: var(--space-3);
}

.brand-icon {
  color: var(--text-inverse);
  filter: drop-shadow(0 2px 4px rgba(0, 0, 0, 0.1));
}

.brand-text {
  display: flex;
  flex-direction: column;
}

.brand-title {
  font-size: var(--font-size-xl);
  font-weight: var(--font-weight-bold);
  margin: 0;
  color: var(--text-inverse);
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
}

.brand-subtitle {
  font-size: var(--font-size-xs);
  color: rgba(255, 255, 255, 0.8);
  margin: 0;
  font-weight: var(--font-weight-normal);
}

/* 导航菜单 */
.nav {
  display: flex;
  gap: var(--space-2);
  align-items: center;
}

.nav-item {
  display: flex;
  align-items: center;
  gap: var(--space-2);
  padding: var(--space-2) var(--space-4);
  color: var(--text-inverse);
  text-decoration: none;
  font-weight: var(--font-weight-medium);
  border-radius: var(--radius);
  transition: all 0.2s ease;
  position: relative;
  overflow: hidden;
}

.nav-item:hover {
  background: rgba(255, 255, 255, 0.1);
  transform: translateY(-1px);
}

.nav-item.router-link-active {
  background: rgba(255, 255, 255, 0.15);
  font-weight: var(--font-weight-semibold);
}

.nav-item .el-icon {
  font-size: 18px;
}

.nav-item span {
  font-size: var(--font-size-sm);
}

/* 右侧功能区 */
.header-actions {
  display: flex;
  align-items: center;
  gap: var(--space-6);
}

/* 模式切换 */
.mode-switch {
  display: flex;
  align-items: center;
  gap: var(--space-2);
  padding: var(--space-2) var(--space-3);
  background: rgba(255, 255, 255, 0.1);
  border-radius: var(--radius);
  backdrop-filter: blur(10px);
}

.mode-icon {
  color: var(--text-inverse);
}

.mode-label {
  font-size: var(--font-size-sm);
  color: var(--text-inverse);
  font-weight: var(--font-weight-medium);
  white-space: nowrap;
}

/* 用户信息 */
.user-info {
  display: flex;
  align-items: center;
  gap: var(--space-2);
  padding: var(--space-2) var(--space-3);
  background: rgba(255, 255, 255, 0.1);
  border-radius: var(--radius);
  backdrop-filter: blur(10px);
}

.user-avatar {
  border: 2px solid rgba(255, 255, 255, 0.3);
}

.user-name {
  font-size: var(--font-size-sm);
  color: var(--text-inverse);
  font-weight: var(--font-weight-medium);
}

/* 老人模式特殊样式 */
.elder-mode .brand-title {
  font-size: var(--font-size-2xl);
}

.elder-mode .brand-subtitle {
  font-size: var(--font-size-sm);
}

.elder-mode .nav-item {
  padding: var(--space-3) var(--space-6);
  font-size: var(--font-size-base);
}

.elder-mode .nav-item .el-icon {
  font-size: 20px;
}

.elder-mode .mode-label,
.elder-mode .user-name {
  font-size: var(--font-size-base);
}

/* 响应式设计 */
@media (max-width: 768px) {
  .header-content {
    flex-direction: column;
    gap: var(--space-3);
    padding: var(--space-3);
  }

  .brand {
    order: -1;
  }

  .nav {
    order: 1;
    flex-wrap: wrap;
    justify-content: center;
  }

  .header-actions {
    order: 2;
    gap: var(--space-3);
  }

  .brand-subtitle {
    display: none;
  }

  .mode-label {
    display: none;
  }

  .user-name {
    display: none;
  }
}

@media (max-width: 480px) {
  .nav-item span {
    display: none;
  }

  .nav-item .el-icon {
    font-size: 20px;
  }
}
</style>
