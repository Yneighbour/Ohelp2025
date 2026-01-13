<script setup>
import { computed } from 'vue';
import { useRouter } from 'vue-router';

import '../../../styles/admin.css';

const props = defineProps({
  activePage: { type: String, required: true },
  title: { type: String, required: true },
});

const router = useRouter();

const current = computed(() => props.activePage);

function go(path) {
  router.push(path);
}

function backToFront() {
  router.push('/profile');
}
</script>

<template>
  <div class="admin-layout">
    <div class="admin-sidebar">
      <div class="admin-sidebar-header">
        <div class="admin-sidebar-logo">🏠</div>
        <div>
          <div class="admin-sidebar-title">智慧养老</div>
          <div class="admin-sidebar-subtitle">后台管理系统</div>
        </div>
      </div>

      <nav class="admin-nav">
        <div class="admin-nav-group">
          <div class="admin-nav-group-title">👥 用户管理</div>
          <div class="admin-nav-item" :class="{ active: current === 'admin-user-list' }" @click="go('/admin-user-list')">
            <span class="icon">📋</span>
            <span class="text">用户列表</span>
          </div>
          <div class="admin-nav-item" :class="{ active: current === 'admin-role-manage' }" @click="go('/admin-role-manage')">
            <span class="icon">🎭</span>
            <span class="text">角色管理</span>
          </div>
          <div class="admin-nav-item" :class="{ active: current === 'admin-permission' }" @click="go('/admin-permission')">
            <span class="icon">🔐</span>
            <span class="text">权限设置</span>
          </div>
        </div>

        <div class="admin-nav-group">
          <div class="admin-nav-group-title">👴 老人管理</div>
          <div class="admin-nav-item" :class="{ active: current === 'admin-elder-list' }" @click="go('/admin-elder-list')">
            <span class="icon">📁</span>
            <span class="text">老人档案</span>
          </div>
          <div class="admin-nav-item" :class="{ active: current === 'admin-health-record' }" @click="go('/admin-health-record')">
            <span class="icon">💊</span>
            <span class="text">健康记录</span>
          </div>
          <div class="admin-nav-item" :class="{ active: current === 'admin-family-bindng' }" @click="go('/admin-family-bindng')">
            <span class="icon">👨‍👩‍👧</span>
            <span class="text">家属绑定</span>
          </div>
        </div>

        <div class="admin-nav-group">
          <div class="admin-nav-group-title">🛎️ 服务管理</div>
          <div class="admin-nav-item" :class="{ active: current === 'admin-service-list' }" @click="go('/admin-service-list')">
            <span class="icon">📦</span>
            <span class="text">服务项目</span>
          </div>
          <div class="admin-nav-item" :class="{ active: current === 'admin-service-order' }" @click="go('/admin-service-order')">
            <span class="icon">📝</span>
            <span class="text">服务预约</span>
          </div>
        </div>

        <div class="admin-nav-group">
          <div class="admin-nav-group-title">🎉 活动管理</div>
          <div class="admin-nav-item" :class="{ active: current === 'admin-activity-list' }" @click="go('/admin-activity-list')">
            <span class="icon">🎯</span>
            <span class="text">活动列表</span>
          </div>
          <div class="admin-nav-item" :class="{ active: current === 'admin-enrollment' }" @click="go('/admin-enrollment')">
            <span class="icon">✅</span>
            <span class="text">报名管理</span>
          </div>
        </div>
      </nav>

      <button class="admin-back-btn" type="button" @click="backToFront">← 返回前台</button>
    </div>

    <div class="admin-main">
      <div class="admin-topbar">
        <h1>{{ title }}</h1>
        <div class="admin-topbar-actions">
          <span style="color: var(--text-secondary)">管理员</span>
        </div>
      </div>

      <div class="admin-content">
        <slot />
      </div>
    </div>
  </div>
</template>
