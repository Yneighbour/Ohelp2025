<script setup>
import { computed, onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';

import '../../styles/profile.css';

import { socialPosts } from '../data/social';
import * as healthApi from '../api/health';
import * as activityApi from '../api/activity';

const router = useRouter();

const loading = ref(false);
const error = ref('');

const statsPosts = ref(Array.isArray(socialPosts) ? socialPosts.length : 0);
const statsActivities = ref(0);
const statsHealthDays = ref(0);

const nickname = computed(() => localStorage.getItem('username') || '张大爷');
const bio = computed(() => '热爱生活，享受健康');
const avatarText = computed(() => String(nickname.value || '我').charAt(0));

const elderlyId = computed(() => Number(localStorage.getItem('elderlyId') || localStorage.getItem('userId') || 1));

async function loadStats() {
  loading.value = true;
  error.value = '';
  try {
    const [activities, records] = await Promise.all([
      activityApi.listAll().catch(() => []),
      healthApi.listByElderlyId(elderlyId.value).catch(() => []),
    ]);

    statsActivities.value = Array.isArray(activities) ? activities.length : 0;
    statsHealthDays.value = Array.isArray(records) ? records.length : 0;
  } catch (e) {
    error.value = e?.message || '加载失败';
  } finally {
    loading.value = false;
  }
}

function go(path) {
  router.push(path);
}

function logout() {
  const ok = window.confirm('确定要退出登录吗？');
  if (!ok) return;

  // 最小化清理：只清当前会话相关
  localStorage.removeItem('token');
  localStorage.removeItem('username');
  localStorage.removeItem('role');
  localStorage.removeItem('userId');
  localStorage.removeItem('elderlyId');

  router.replace('/login');
}

onMounted(loadStats);
</script>

<template>
  <div class="profile-page">
    <div class="profile-content">
      <div class="profile-header">
        <div class="profile-avatar-container">
          <div class="profile-avatar">{{ avatarText }}</div>
          <div class="edit-avatar-btn" role="button" tabindex="0" @click="() => window.alert('演示版本，该功能暂不可用')">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <path d="M12 20h9" />
              <path d="M16.5 3.5a2.121 2.121 0 0 1 3 3L7 19l-4 1 1-4 12.5-12.5z" />
            </svg>
          </div>
        </div>

        <div class="profile-nickname">{{ nickname }}</div>
        <div class="profile-bio">{{ bio }}</div>
      </div>

      <div class="stats-cards">
        <div class="stat-card">
          <div class="stat-value">{{ statsPosts }}</div>
          <div class="stat-label">动态</div>
        </div>
        <div class="stat-card">
          <div class="stat-value">{{ statsActivities }}</div>
          <div class="stat-label">活动</div>
        </div>
        <div class="stat-card">
          <div class="stat-value">{{ statsHealthDays }}</div>
          <div class="stat-label">健康天数</div>
        </div>
      </div>

      <div v-if="loading" style="padding: 8px 0; color: var(--text-secondary)">正在加载...</div>
      <div v-else-if="error" style="padding: 8px 0; color: var(--danger-color)">{{ error }}</div>

      <div class="menu-section">
        <div class="menu-item" role="button" tabindex="0" @click="go('/profile/info')">
          <div class="menu-icon">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2" />
              <circle cx="12" cy="7" r="4" />
            </svg>
          </div>
          <div class="menu-content"><div class="menu-title">个人资料</div></div>
          <svg class="menu-arrow" viewBox="0 0 24 24" fill="none" stroke="currentColor"><path d="M9 18l6-6-6-6" /></svg>
        </div>

        <div class="menu-item" role="button" tabindex="0" @click="go('/profile/health-records')">
          <div class="menu-icon">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <path d="M9 12h6" />
              <path d="M12 9v6" />
              <path d="M5 4h14v16H5z" />
            </svg>
          </div>
          <div class="menu-content"><div class="menu-title">健康档案</div></div>
          <svg class="menu-arrow" viewBox="0 0 24 24" fill="none" stroke="currentColor"><path d="M9 18l6-6-6-6" /></svg>
        </div>

        <div class="menu-item" role="button" tabindex="0" @click="go('/profile/my-activities')">
          <div class="menu-icon">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <path d="M22 12h-4l-3 9L9 3l-3 9H2" />
            </svg>
          </div>
          <div class="menu-content"><div class="menu-title">我的活动</div></div>
          <svg class="menu-arrow" viewBox="0 0 24 24" fill="none" stroke="currentColor"><path d="M9 18l6-6-6-6" /></svg>
        </div>

        <div class="menu-item" role="button" tabindex="0" @click="go('/profile/contacts')">
          <div class="menu-icon">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <path d="M22 16.92V19a2 2 0 0 1-2.18 2 19.79 19.79 0 0 1-8.63-3.07A19.5 19.5 0 0 1 3.07 9.81 19.79 19.79 0 0 1 0 1.18 2 2 0 0 1 2 0h2.09a2 2 0 0 1 2 1.72c.12.86.3 1.7.54 2.51a2 2 0 0 1-.45 2.11L5.09 7.91a16 16 0 0 0 7 7l1.57-1.09a2 2 0 0 1 2.11-.45c.81.24 1.65.42 2.51.54a2 2 0 0 1 1.72 2z" />
            </svg>
          </div>
          <div class="menu-content"><div class="menu-title">紧急联系人</div></div>
          <svg class="menu-arrow" viewBox="0 0 24 24" fill="none" stroke="currentColor"><path d="M9 18l6-6-6-6" /></svg>
        </div>

        <div class="menu-item" role="button" tabindex="0" @click="go('/profile/settings')">
          <div class="menu-icon">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <circle cx="12" cy="12" r="3" />
              <path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1-2.83 2.83l-.06-.06A1.65 1.65 0 0 0 15 19.4a1.65 1.65 0 0 0-1 .6 1.65 1.65 0 0 0-.33 1.82V22a2 2 0 0 1-4 0v-.08a1.65 1.65 0 0 0-.33-1.82 1.65 1.65 0 0 0-1-.6 1.65 1.65 0 0 0-1.82.33l-.06.06A2 2 0 1 1 2.46 19l.06-.06A1.65 1.65 0 0 0 3.4 17a1.65 1.65 0 0 0-.6-1 1.65 1.65 0 0 0-1.82-.33H1a2 2 0 0 1 0-4h.08a1.65 1.65 0 0 0 1.82-.33 1.65 1.65 0 0 0 .6-1 1.65 1.65 0 0 0-.33-1.82l-.06-.06A2 2 0 1 1 5.94 2.46l.06.06A1.65 1.65 0 0 0 8 3.4a1.65 1.65 0 0 0 1-.6 1.65 1.65 0 0 0 .33-1.82V1a2 2 0 0 1 4 0v.08a1.65 1.65 0 0 0 .33 1.82 1.65 1.65 0 0 0 1 .6 1.65 1.65 0 0 0 1.82-.33l.06-.06A2 2 0 1 1 21.54 5l-.06.06A1.65 1.65 0 0 0 20.6 7c.14.32.2.67.2 1s-.06.68-.2 1a1.65 1.65 0 0 0 .88 1.94c.32.14.68.2 1 .2s.68-.06 1-.2a2 2 0 0 1 0 4c-.32-.14-.68-.2-1-.2s-.68.06-1 .2A1.65 1.65 0 0 0 19.4 15z" />
            </svg>
          </div>
          <div class="menu-content"><div class="menu-title">系统设置</div></div>
          <svg class="menu-arrow" viewBox="0 0 24 24" fill="none" stroke="currentColor"><path d="M9 18l6-6-6-6" /></svg>
        </div>
      </div>

      <button class="logout-btn" type="button" @click="logout">退出登录</button>
    </div>
  </div>
</template>
