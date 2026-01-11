<template>
  <div class="login-page">
    <BaseCard class="login-card" shadow="xl">
      <template #header>
        <div class="login-header">
          <div class="login-logo" aria-hidden="true">OH</div>
          <div class="product-title">Ohelp2025</div>
          <div class="product-subtitle">智慧养老服务管理系统</div>
          <h2 class="login-title">登录</h2>
          <p class="login-subtitle">请使用账号密码登录系统</p>
        </div>
      </template>

      <form class="login-form" @submit.prevent="handleLogin">
        <BaseInput
          v-model="form.username"
          label="用户名"
          placeholder="例如：admin"
          autocomplete="username"
          required
          size="lg"
        >
          <template #prefix>
            <svg class="field-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2" />
              <circle cx="12" cy="7" r="4" />
            </svg>
          </template>
        </BaseInput>

        <BaseInput
          v-model="form.password"
          type="password"
          label="密码"
          placeholder="请输入密码"
          autocomplete="current-password"
          required
          size="lg"
        >
          <template #prefix>
            <svg class="field-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <rect x="3" y="11" width="18" height="11" rx="2" ry="2" />
              <path d="M7 11V7a5 5 0 0 1 10 0v4" />
            </svg>
          </template>
        </BaseInput>

        <div class="actions">
          <BaseButton
            type="submit"
            variant="primary"
            size="lg"
            block
            :loading="state.loading"
          >
            登录
          </BaseButton>
        </div>

        <p v-if="state.error" class="error" role="alert">{{ state.error }}</p>
      </form>

      <div class="login-footer">
        <span>默认接口：</span>
        <span class="mono">http://localhost:8080/api</span>
      </div>
    </BaseCard>
  </div>
</template>

<script>
import BaseCard from '../../components/base/BaseCard.vue'
import BaseButton from '../../components/base/BaseButton.vue'
import BaseInput from '../../components/base/BaseInput.vue'

import { login } from './auth.api.js'
import { getUser } from '../user/user.api.js'
import { ROLE_ROUTE_MAP } from '../../router/roleMap.js'

export default {
  name: 'Login',
  components: { BaseCard, BaseButton, BaseInput },
  data() {
    return {
      form: {
        username: '',
        password: ''
      },
      state: {
        loading: false,
        error: null
      }
    }
  },
  mounted() {
    // 已登录则直接分发（避免重复登录）
    const token = localStorage.getItem('token')
    const role = localStorage.getItem('role')
    if (token && role) {
      const targetPath = ROLE_ROUTE_MAP[role]
      if (targetPath) {
        this.$router.replace(targetPath)
      }
    }
  },
  methods: {
    async handleLogin() {
      this.state.loading = true
      this.state.error = null
      try {
        // 1) 登录：拿 token + userId
        const loginRes = await login({
          username: this.form.username,
          password: this.form.password
        })
        const auth = loginRes?.data

        if (!auth?.token || !auth?.userId) {
          throw new Error('登录返回数据不完整')
        }

        // 2) 取用户：拿 role（角色只来源于后端）
        const userRes = await getUser(auth.userId)
        const user = userRes?.data

        if (!user?.role) {
          throw new Error('用户角色缺失')
        }

        // 3) 最小持久化（token / userId / role）
        localStorage.setItem('token', auth.token)
        localStorage.setItem('userId', String(auth.userId))
        localStorage.setItem('role', String(user.role))

        // 4) 角色 -> 入口路由（唯一分发点）
        const targetPath = ROLE_ROUTE_MAP[user.role]
        if (!targetPath) {
          // 兜底：非法角色
          localStorage.removeItem('token')
          localStorage.removeItem('userId')
          localStorage.removeItem('role')
          this.$router.replace('/login')
          return
        }

        this.$router.replace(targetPath)
      } catch (e) {
        const message = e?.message || '登录失败'
        this.state.error = message
        if (this.$message) this.$message.error(message)
      } finally {
        this.state.loading = false
      }
    }
  }
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 24px;
  background: linear-gradient(135deg, #7C3AED 0%, #A78BFA 50%, #C084FC 100%);
  position: relative;
  overflow: hidden;
}

/* 背景装饰（参考静态演示 login.css） */
.login-page::before {
  content: '';
  position: absolute;
  top: -50%;
  right: -20%;
  width: 600px;
  height: 600px;
  background: radial-gradient(circle, rgba(255, 255, 255, 0.12) 0%, transparent 70%);
  border-radius: 50%;
  animation: float 6s ease-in-out infinite;
}

.login-page::after {
  content: '';
  position: absolute;
  bottom: -30%;
  left: -10%;
  width: 420px;
  height: 420px;
  background: radial-gradient(circle, rgba(255, 255, 255, 0.10) 0%, transparent 70%);
  border-radius: 50%;
  animation: float 8s ease-in-out infinite reverse;
}

@keyframes float {
  0%,
  100% {
    transform: translateY(0) scale(1);
  }
  50% {
    transform: translateY(-20px) scale(1.05);
  }
}

.login-card {
  width: min(520px, 100%);
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(20px);
  border-radius: 24px;
  border: 1px solid rgba(255, 255, 255, 0.22);
  box-shadow: 0 20px 60px rgba(124, 58, 237, 0.30), 0 0 0 1px rgba(255, 255, 255, 0.10);
  position: relative;
  z-index: 1;
  animation: slideUp 0.6s ease-out;
}

@keyframes slideUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.login-logo {
  width: 96px;
  height: 96px;
  margin: 0 auto var(--spacing-md);
  background: var(--primary-gradient);
  border-radius: var(--border-radius-circle);
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--white);
  font-size: 36px;
  font-weight: 800;
  box-shadow: 0 8px 24px rgba(124, 58, 237, 0.40);
  position: relative;
}

.login-logo::before {
  content: '';
  position: absolute;
  inset: -4px;
  background: var(--primary-gradient-reverse);
  border-radius: var(--border-radius-circle);
  z-index: -1;
  opacity: 0.3;
  filter: blur(10px);
}

.product-title {
  text-align: center;
  font-size: 28px;
  font-weight: 800;
  background: var(--primary-gradient);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  letter-spacing: 0.5px;
}

.product-subtitle {
  text-align: center;
  margin-top: 6px;
  color: var(--text-secondary);
  font-size: var(--font-size-base);
}

.login-header {
  display: flex;
  flex-direction: column;
  gap: 6px;
  text-align: center;
}

.login-title {
  margin: var(--spacing-lg) 0 0;
  font-size: 24px;
  font-weight: 700;
  color: var(--text-primary);
}

.login-subtitle {
  margin: 0;
  color: var(--text-secondary);
  font-size: var(--font-size-base);
}

.field-icon {
  width: 18px;
  height: 18px;
}

.login-form {
  display: grid;
  gap: var(--spacing-md);
  margin-top: var(--spacing-lg);
}

/* 登录页输入框：更接近静态演示的 form-input 风格 */
.login-page :deep(.input-container) {
  background-color: #F9FAFB;
  border: 2px solid #E5E7EB;
  border-radius: 12px;
}

.login-page :deep(.input-container:focus-within) {
  background-color: var(--white);
  border-color: var(--primary-color);
  box-shadow: 0 0 0 4px rgba(124, 58, 237, 0.10);
}

.login-page :deep(.base-input.input-lg) {
  min-height: 52px;
}

.login-page :deep(.input-prefix) {
  border-right: none;
  padding-left: var(--spacing-md);
  padding-right: var(--spacing-sm);
  color: var(--text-secondary);
}

.login-page :deep(.base-input) {
  padding-left: 0;
}

.actions {
  margin-top: 4px;
}

/* 登录按钮：更接近静态演示 login-btn */
.actions :deep(.base-btn.btn-lg) {
  min-height: 52px;
  font-size: var(--font-size-lg);
  border-radius: 12px;
  box-shadow: 0 4px 16px rgba(124, 58, 237, 0.30);
}

.actions :deep(.base-btn.btn-primary:hover) {
  box-shadow: 0 8px 24px rgba(124, 58, 237, 0.40);
}

.error {
  margin-top: 6px;
  color: var(--error-color);
  font-size: var(--font-size-sm);
}

.login-footer {
  margin-top: var(--spacing-lg);
  padding-top: var(--spacing-md);
  border-top: 1px solid var(--border-color);
  color: var(--text-secondary);
  font-size: var(--font-size-sm);
  text-align: center;
}

.mono {
  font-family: ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas, 'Liberation Mono', 'Courier New', monospace;
}

@media (max-width: 480px) {
  .login-page {
    padding: 16px;
  }

  .login-logo {
    width: 82px;
    height: 82px;
    font-size: 32px;
  }

  .product-title {
    font-size: 24px;
  }
}
</style>
