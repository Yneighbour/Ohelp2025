<script setup>
import { computed, onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import * as activityApi from '../api/activity';

import '../../styles/activities.css';
import '../../styles/activity-detail.css';

const route = useRoute();
const router = useRouter();

const loading = ref(false);
const error = ref('');
const activity = ref(null);

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

function formatYMDHM(value) {
  const d = toDate(value);
  if (!d) return '';
  return `${d.getFullYear()}-${pad2(d.getMonth() + 1)}-${pad2(d.getDate())} ${pad2(d.getHours())}:${pad2(d.getMinutes())}`;
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

const viewModel = computed(() => {
  const a = activity.value;
  if (!a) return null;

  const start = formatYMDHM(a?.startTime);
  const end = formatYMDHM(a?.endTime);
  const timeText = start && end ? `${start} - ${end.split(' ')[1]}` : start || end || '--';

  const enrolled = Number(a?.participants ?? 0) || 0;
  const capacity = 30;

  const statusText = a?.status || '计划中';
  const cls = statusClass(statusText);

  const actionLabel = statusText.includes('完成') || statusText.includes('结束') ? '已结束' : '立即报名';
  const actionDisabled = actionLabel !== '立即报名';

  const displayParticipants = Math.min(enrolled, 12);

  return {
    id: a?.id,
    name: a?.name || '未命名活动',
    category: a?.category || '活动',
    description: a?.description || '暂无介绍',
    location: a?.location || '未填写地点',
    timeText,
    fee: '免费',
    enrolled,
    capacity,
    organizer: '社区服务中心',
    notes: '请按时到达，注意安全，如有身体不适请及时告知工作人员。',
    statusText,
    statusClass: cls,
    icon: categoryIcon(a?.category),
    actionLabel,
    actionDisabled,
    displayParticipants,
  };
});

async function load() {
  const id = route.params.id;
  if (!id) {
    router.replace('/activities');
    return;
  }

  loading.value = true;
  error.value = '';
  try {
    activity.value = await activityApi.getById(id);
  } catch (e) {
    error.value = e?.message || '加载失败';
  } finally {
    loading.value = false;
  }
}

function onPrimaryAction() {
  if (viewModel.value?.actionDisabled) {
    window.alert('活动已结束');
    return;
  }
  window.alert('报名成功！（演示）');
}

onMounted(load);
</script>

<template>
  <div class="activity-detail-page">
    <div class="top-bar">
      <button class="back-btn" type="button" @click="router.back()">
        <svg class="back-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
          <path d="M19 12H5M12 19l-7-7 7-7" />
        </svg>
      </button>
      <h1 class="title">活动详情</h1>
      <div class="right-action"></div>
    </div>

    <div class="activity-detail-content">
      <div v-if="loading" class="empty-state" style="padding: 16px; color: var(--text-secondary)">正在加载...</div>
      <div v-else-if="error" class="empty-state" style="padding: 16px; color: var(--danger-color)">{{ error }}</div>

      <template v-else-if="viewModel">
        <div class="activity-detail-header">
          <div class="activity-image-large">{{ viewModel.icon }}</div>
          <h2 class="activity-detail-title">{{ viewModel.name }}</h2>
          <span class="activity-status" :class="viewModel.statusClass">{{ viewModel.statusText }}</span>
        </div>

        <div class="activity-detail-info">
          <div class="info-item"><span class="info-label">时间：</span><span>{{ viewModel.timeText }}</span></div>
          <div class="info-item"><span class="info-label">地点：</span><span>{{ viewModel.location }}</span></div>
          <div class="info-item"><span class="info-label">费用：</span><span>{{ viewModel.fee }}</span></div>
          <div class="info-item"><span class="info-label">名额：</span><span>{{ viewModel.enrolled }}/{{ viewModel.capacity }}人</span></div>
          <div class="info-item"><span class="info-label">主办方：</span><span>{{ viewModel.organizer }}</span></div>
        </div>

        <div class="activity-detail-section">
          <div class="section-title">活动介绍</div>
          <div class="section-content">{{ viewModel.description }}</div>
        </div>

        <div class="activity-detail-section">
          <div class="section-title">注意事项</div>
          <div class="section-content">{{ viewModel.notes }}</div>
        </div>

        <div class="activity-detail-section">
          <div class="section-title">已报名 ({{ viewModel.enrolled }}人)</div>
          <div class="participants-list">
            <span v-for="n in viewModel.displayParticipants" :key="n" class="participant-avatar">人</span>
          </div>
        </div>
      </template>

      <div v-else class="empty-state" style="padding: 16px; color: var(--text-secondary)">未找到活动</div>
    </div>

    <div class="activity-detail-actions">
      <button class="action-btn action-btn-secondary" type="button" @click="router.back()">返回</button>
      <button
        class="action-btn action-btn-primary"
        type="button"
        :disabled="viewModel?.actionDisabled"
        :style="viewModel?.actionDisabled ? 'opacity:0.7; cursor:not-allowed' : ''"
        @click="onPrimaryAction"
      >
        {{ viewModel?.actionLabel || '立即报名' }}
      </button>
    </div>
  </div>
</template>
