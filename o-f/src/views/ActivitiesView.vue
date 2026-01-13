<script setup>
import { computed, onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import * as activityApi from '../api/activity';

import '../../styles/activities.css';

const router = useRouter();

const loading = ref(false);
const error = ref('');
const activities = ref([]);

function toDate(value) {
  if (!value) return null;
  if (value instanceof Date) return value;
  const d1 = new Date(value);
  if (!Number.isNaN(d1.getTime())) return d1;
  // 兼容 'YYYY-MM-DD HH:mm:ss'
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

function statusClass(statusText) {
  const s = String(statusText || '');
  if (s.includes('计划')) return 'available';
  if (s.includes('进行')) return 'enrolled';
  if (s.includes('完成') || s.includes('结束')) return 'full';
  return 'available';
}

function categoryIcon(categoryText) {
  const s = String(categoryText || '');
  if (s.includes('健身') || s.includes('运动') || s.includes('太极')) return '💪';
  if (s.includes('文化') || s.includes('书法') || s.includes('绘画')) return '🎨';
  if (s.includes('学习') || s.includes('课堂') || s.includes('讲座')) return '📚';
  if (s.includes('户外') || s.includes('郊游') || s.includes('公园')) return '🌳';
  if (s.includes('娱乐') || s.includes('棋') || s.includes('比赛')) return '🌳';
  return '🌳';
}

const viewModels = computed(() => {
  const list = Array.isArray(activities.value) ? activities.value : [];
  return list.map((a) => {
    const enrolled = Number(a?.participants ?? 0) || 0;
    const capacity = 30;
    return {
      id: a?.id,
      name: a?.name || '未命名活动',
      location: a?.location || '未填写地点',
      dateText: formatYMD(a?.startTime) || '--',
      enrolled,
      capacity,
      statusText: a?.status || '计划中',
      statusClass: statusClass(a?.status),
      icon: categoryIcon(a?.category),
    };
  });
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

onMounted(load);
</script>

<template>
  <div class="activities-page">
    <div class="activities-header">
      <h1 class="activities-title">社区活动</h1>
    </div>

    <div class="activities-tabs">
      <button class="tab-btn" type="button" @click="router.push('/social')">动态</button>
      <button class="tab-btn active" type="button">活动</button>
    </div>

    <div class="activities-content">
      <div v-if="loading" class="empty-state" style="padding: 16px; color: var(--text-secondary)">正在加载...</div>
      <div v-else-if="error" class="empty-state" style="padding: 16px; color: var(--danger-color)">{{ error }}</div>

      <div v-else>
        <div
          v-for="activity in viewModels"
          :key="activity.id"
          class="activity-card"
          role="button"
          tabindex="0"
          @click="router.push(`/activities/${activity.id}`)"
          @keydown.enter="router.push(`/activities/${activity.id}`)"
        >
          <div class="activity-image">{{ activity.icon }}</div>
          <div class="activity-info">
            <div class="activity-title">{{ activity.name }}</div>
            <div class="activity-meta">
              <span>📅 {{ activity.dateText }}</span>
              <span>📍 {{ activity.location }}</span>
            </div>
            <div class="activity-footer">
              <span class="activity-enrolled">{{ activity.enrolled }}/{{ activity.capacity }}人</span>
              <span class="activity-status" :class="activity.statusClass">{{ activity.statusText }}</span>
            </div>
          </div>
        </div>

        <div v-if="!viewModels.length" class="empty-state" style="padding: 16px; color: var(--text-secondary)">暂无活动</div>
      </div>
    </div>
  </div>
</template>
