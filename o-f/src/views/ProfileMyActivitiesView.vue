<script setup>
import { computed, onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';

import * as activityApi from '../api/activity';

const router = useRouter();

const loading = ref(false);
const error = ref('');
const activities = ref([]);

function toDate(value) {
  if (!value) return null;
  if (value instanceof Date) return value;
  const d1 = new Date(value);
  if (!Number.isNaN(d1.getTime())) return d1;
  const d2 = new Date(String(value).replace(' ', 'T'));
  return Number.isNaN(d2.getTime()) ? null : d2;
}

function pad2(n) {
  return String(n).padStart(2, '0');
}

function formatYMD(value) {
  const d = toDate(value);
  if (!d) return '';
  return `${d.getFullYear()}-${pad2(d.getMonth() + 1)}-${pad2(d.getDate())}`;
}

function formatHM(value) {
  const d = toDate(value);
  if (!d) return '';
  return `${pad2(d.getHours())}:${pad2(d.getMinutes())}`;
}

function statusInfo(statusText) {
  const s = String(statusText || '');
  if (s.includes('完成') || s.includes('结束')) return { cls: 'completed', text: '已完成' };
  return { cls: 'upcoming', text: '即将开始' };
}

const viewModels = computed(() => {
  const list = Array.isArray(activities.value) ? activities.value : [];
  const mapped = list.map((a) => {
    const start = formatYMD(a?.startTime);
    const time = formatHM(a?.startTime);
    const end = formatHM(a?.endTime);
    const status = statusInfo(a?.status);

    return {
      id: a?.id,
      name: a?.name || '未命名活动',
      date: start || '--',
      time: time && end ? `${time}-${end}` : time || '--',
      location: a?.location || '未填写地点',
      statusClass: status.cls,
      statusText: status.text,
    };
  });

  // 让“即将开始”排前面
  mapped.sort((x, y) => (x.statusClass === y.statusClass ? 0 : x.statusClass === 'upcoming' ? -1 : 1));
  return mapped;
});

const stats = computed(() => {
  const list = viewModels.value;
  const joined = list.length;
  const ongoing = list.filter((a) => a.statusClass === 'upcoming').length;
  const enrolled = 0;
  return { joined, ongoing, enrolled };
});

async function load() {
  loading.value = true;
  error.value = '';
  try {
    const data = await activityApi.listAll();
    activities.value = Array.isArray(data) ? data : [];
  } catch (e) {
    error.value = e?.message || '加载失败';
  } finally {
    loading.value = false;
  }
}

function viewDetail(id) {
  router.push(`/activities/${id}`);
}

function demoAction() {
  window.alert('演示版本，该功能暂不可用');
}

onMounted(load);
</script>

<template>
  <div class="my-activities-page">
    <div class="top-bar">
      <button class="back-btn" type="button" @click="router.back()">
        <svg class="back-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor"><path d="M19 12H5M12 19l-7-7 7-7" /></svg>
      </button>
      <h1 class="title">我的活动</h1>
      <div class="right-action"></div>
    </div>

    <div class="my-activities-content">
      <div class="activity-stats">
        <div class="stat-item">
          <div class="stat-number">{{ stats.joined }}</div>
          <div class="stat-label">已参加</div>
        </div>
        <div class="stat-item">
          <div class="stat-number">{{ stats.ongoing }}</div>
          <div class="stat-label">进行中</div>
        </div>
        <div class="stat-item">
          <div class="stat-number">{{ stats.enrolled }}</div>
          <div class="stat-label">已报名</div>
        </div>
      </div>

      <div v-if="loading" style="padding: 8px 0; color: var(--text-secondary)">正在加载...</div>
      <div v-else-if="error" style="padding: 8px 0; color: var(--danger-color)">{{ error }}</div>

      <div class="my-activity-list">
        <div v-for="a in viewModels" :key="a.id" class="my-activity-card" :class="a.statusClass">
          <div class="activity-header">
            <div class="activity-name">{{ a.name }}</div>
            <div class="activity-status" :class="a.statusClass">{{ a.statusText }}</div>
          </div>
          <div class="activity-info">
            <div class="activity-info-item">📅 {{ a.date }}</div>
            <div class="activity-info-item">⏰ {{ a.time }}</div>
          </div>
          <div class="activity-actions">
            <button class="action-btn primary" type="button" @click="viewDetail(a.id)">查看详情</button>
            <button class="action-btn secondary" type="button" @click="demoAction">取消/提醒</button>
          </div>
        </div>

        <div v-if="!viewModels.length && !loading" style="padding: 8px 0; color: var(--text-secondary)">暂无活动</div>
      </div>
    </div>
  </div>
</template>
