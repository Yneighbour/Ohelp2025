<script setup>
import { computed, onMounted, ref, watch } from 'vue';
import { useRouter } from 'vue-router';
import * as healthApi from '../api/health';
import {
  statusForBloodPressure,
  statusForGlucose,
  statusForHeartRate,
  statusForTemperature,
  buildDerivedAlertsFromLatestRecord,
} from '../utils/health';
import { formatTimeHM } from '../utils/datetime';

import '../../styles/health.css';

const router = useRouter();

const elderlyId = ref(Number(localStorage.getItem('elderlyId') || localStorage.getItem('userId') || 1));
const loading = ref(false);
const error = ref('');
const records = ref([]);

const username = computed(() => localStorage.getItem('username') || '用户');

const sortedRecords = computed(() => {
  const list = Array.isArray(records.value) ? records.value.slice() : [];
  list.sort((a, b) => String(b.recordDate || '').localeCompare(String(a.recordDate || '')));
  return list;
});

const latest = computed(() => sortedRecords.value[0] || null);

const alerts = computed(() => buildDerivedAlertsFromLatestRecord(latest.value));
const unreadAlerts = computed(() => {
  const readKeys = new Set(JSON.parse(localStorage.getItem('readAlertKeys') || '[]'));
  return alerts.value.filter(a => !readKeys.has(a.key)).length;
});

const healthCards = computed(() => {
  const r = latest.value;
  const measureTime = r?.updatedAt ? formatTimeHM(r.updatedAt) : '';

  const bp = statusForBloodPressure(r?.bloodPressure);
  const hr = statusForHeartRate(r?.heartRate);
  const glu = statusForGlucose(r?.glucoseLevel);
  const temp = statusForTemperature(r?.temperature);

  return [
    {
      type: 'blood-pressure',
      name: '血压',
      unit: 'mmHg',
      currentValue: r?.bloodPressure || '--/--',
      normalRange: '90-140/60-90',
      measureTime: measureTime ? `最近测量 · ${measureTime}` : '暂无测量时间',
      ...bp,
    },
    {
      type: 'heart-rate',
      name: '心率',
      unit: '次/分',
      currentValue: r?.heartRate ?? '--',
      normalRange: '60-100',
      measureTime: measureTime ? `最近测量 · ${measureTime}` : '暂无测量时间',
      ...hr,
    },
    {
      type: 'blood-sugar',
      name: '血糖',
      unit: 'mmol/L',
      currentValue: r?.glucoseLevel ?? '--',
      normalRange: '3.9-6.1',
      measureTime: measureTime ? `最近测量 · ${measureTime}` : '暂无测量时间',
      ...glu,
    },
    {
      type: 'temperature',
      name: '体温',
      unit: '℃',
      currentValue: r?.temperature ?? '--',
      normalRange: '36.0-37.2',
      measureTime: measureTime ? `最近测量 · ${measureTime}` : '暂无测量时间',
      ...temp,
    },
  ];
});

function statusLabel(status) {
  if (status === 'danger') return '异常';
  if (status === 'warning') return '注意';
  return '正常';
}

async function load() {
  loading.value = true;
  error.value = '';
  try {
    localStorage.setItem('elderlyId', String(elderlyId.value));
    const data = await healthApi.listHealthRecordsByElderlyId(elderlyId.value);
    records.value = Array.isArray(data) ? data : [];
  } catch (e) {
    error.value = e?.message || '加载失败';
    records.value = [];
  } finally {
    loading.value = false;
  }
}

function goDetail(type) {
  router.push({ path: `/health/detail/${type}`, query: { elderlyId: String(elderlyId.value) } });
}

function goAlerts() {
  router.push({ path: '/alerts', query: { elderlyId: String(elderlyId.value) } });
}

function changeElderlyId() {
  const input = window.prompt('请输入要查看的老人ID（elderlyId）', String(elderlyId.value));
  if (!input) return;
  const next = Number(input);
  if (!Number.isFinite(next) || next <= 0) return;
  elderlyId.value = next;
}

watch(elderlyId, () => load());
onMounted(load);
</script>

<template>
  <div class="health-page">
    <div class="health-header">
      <div class="user-info">
        <div class="user-avatar">{{ username.charAt(0) }}</div>
        <div class="user-details">
          <div class="user-name">{{ username }}</div>
          <div class="user-greeting">今天身体状况良好</div>
        </div>
        <button class="alert-btn" @click="goAlerts">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
            <path
              d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"
            ></path>
            <path d="M13.73 21a2 2 0 0 1-3.46 0"></path>
          </svg>
          <span v-if="unreadAlerts > 0" class="alert-badge">{{ unreadAlerts }}</span>
        </button>
      </div>

      <div class="health-score-card">
        <div class="score-label">今日健康评分</div>
        <div class="score-value">85<span class="score-unit">/100</span></div>
        <div class="score-bar"><div class="score-progress" style="width: 85%"></div></div>
        <div class="score-tip">继续保持，您的健康状况良好！</div>
      </div>
    </div>

    <div class="quick-actions">
      <button class="action-item" @click="window.alert('演示版本，该功能暂不可用')">
        <div class="action-icon">📊</div>
        <div class="action-text">测量数据</div>
      </button>
      <button class="action-item" @click="router.push('/activities')">
        <div class="action-icon">🏃</div>
        <div class="action-text">运动打卡</div>
      </button>
      <button class="action-item" @click="window.alert('演示版本，该功能暂不可用')">
        <div class="action-icon">💊</div>
        <div class="action-text">用药提醒</div>
      </button>
      <button class="action-item" @click="window.alert('演示版本，该功能暂不可用')">
        <div class="action-icon">📋</div>
        <div class="action-text">健康报告</div>
      </button>
    </div>

    <div class="health-section">
      <div class="section-header">
        <h2 class="section-title">健康指标</h2>
        <a class="section-more" @click.prevent="changeElderlyId">更多 →</a>
      </div>

      <div v-if="error" class="container" style="padding-top: 12px;">
        <div class="card" style="color: #b91c1c;">{{ error }}</div>
      </div>

      <div v-else-if="loading" class="container" style="padding-top: 12px;">
        <div class="card">加载中…</div>
      </div>

      <div class="health-cards">
        <div
          v-for="card in healthCards"
          :key="card.type"
          class="health-card"
          @click="goDetail(card.type)"
        >
          <div class="card-header">
            <div class="card-icon" :class="card.type">
              {{ card.type === 'blood-pressure' ? '💓' : card.type === 'heart-rate' ? '❤️' : card.type === 'blood-sugar' ? '🩸' : '🌡️' }}
            </div>
            <span class="card-status" :class="card.status">{{ statusLabel(card.status) }}</span>
          </div>
          <div class="card-title">{{ card.name }}</div>
          <div class="card-value">
            {{ card.currentValue }}<span class="card-unit">{{ card.unit }}</span>
          </div>
          <div class="card-time">{{ card.measureTime }}</div>
        </div>
      </div>
    </div>

    <div class="health-section">
      <div class="section-header"><h2 class="section-title">健康建议</h2></div>
      <div class="health-tips">
        <div class="tip-card">
          <div class="tip-icon">🥗</div>
          <div class="tip-content">
            <div class="tip-title">饮食建议</div>
            <div class="tip-text">建议多吃新鲜蔬菜水果，少油少盐</div>
          </div>
        </div>
        <div class="tip-card">
          <div class="tip-icon">💤</div>
          <div class="tip-content">
            <div class="tip-title">睡眠建议</div>
            <div class="tip-text">保持规律作息，每天睡眠7-8小时</div>
          </div>
        </div>
        <div class="tip-card">
          <div class="tip-icon">🧘</div>
          <div class="tip-content">
            <div class="tip-title">运动建议</div>
            <div class="tip-text">每天坚持30分钟有氧运动</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
