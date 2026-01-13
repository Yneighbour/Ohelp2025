<script setup>
import { ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import * as authApi from '../api/auth';

import '../../styles/login.css';

const router = useRouter();
const route = useRoute();

const username = ref('admin');
const password = ref('admin');
const role = ref('user');
const loading = ref(false);
const error = ref('');

async function submit() {
  error.value = '';
  loading.value = true;
  try {
    const data = await authApi.login({ username: username.value, password: password.value });
    localStorage.setItem('token', data.token);
    localStorage.setItem('username', data.username || username.value);
    // 后端若未返回 role，则使用页面选择的登录身份（最小返工，便于切换用户端/管理端）
    localStorage.setItem('role', data.role || role.value);
    if (data.userId != null) localStorage.setItem('userId', String(data.userId));

    const next = typeof route.query.next === 'string'
      ? route.query.next
      : (role.value === 'admin' ? '/admin-user-list' : '/health');
    router.replace(next);
  } catch (e) {
    const msg = e?.message || '';
    if (msg.includes('timeout')) {
      error.value = '登录请求超时，请确认后端服务已启动（默认 http://localhost:8080/api）';
    } else {
      error.value = msg || '登录失败';
    }
  } finally {
    loading.value = false;
  }
}
</script>

<template>
  <div class="login-page">
    <div class="login-container">
      <div class="login-logo">智</div>
      <h1 class="login-title">智慧养老系统</h1>

      <form class="login-form" @submit.prevent="submit">
        <div class="form-group">
          <label class="form-label" for="username">用户名</label>
          <input
            id="username"
            v-model="username"
            class="form-input"
            placeholder="请输入用户名"
            autocomplete="username"
            required
          />
        </div>

        <div class="form-group">
          <label class="form-label" for="password">密码</label>
          <input
            id="password"
            v-model="password"
            class="form-input"
            type="password"
            placeholder="请输入密码"
            autocomplete="current-password"
            required
          />
        </div>

        <div class="form-group">
          <label class="form-label">登录身份</label>
          <div class="role-selector">
            <label class="role-option">
              <input type="radio" name="role" value="user" v-model="role" />
              <span class="role-card">
                <span class="role-icon">👴</span>
                <span class="role-name">用户端</span>
                <span class="role-desc">老人/家属使用</span>
              </span>
            </label>
            <label class="role-option">
              <input type="radio" name="role" value="admin" v-model="role" />
              <span class="role-card">
                <span class="role-icon">👨‍💼</span>
                <span class="role-name">管理端</span>
                <span class="role-desc">管理员使用</span>
              </span>
            </label>
          </div>
        </div>

        <div v-if="error" class="error" style="margin-bottom: 10px;">{{ error }}</div>

        <button type="submit" class="login-btn" :disabled="loading">
          {{ loading ? '登录中…' : '登录' }}
        </button>

        <div class="register-link" style="margin-top: 10px;">
          后端演示数据：管理员账号密码通常为 admin/admin123（以数据库初始化脚本为准）
        </div>
      </form>
    </div>
  </div>
</template>
