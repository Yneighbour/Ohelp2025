<script setup>
import { computed, nextTick, onMounted, ref, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import * as healthApi from '../api/health';
import {
  parseBloodPressure,
  statusForBloodPressure,
  statusForGlucose,
  statusForHeartRate,
  statusForTemperature,
} from '../utils/health';
import { formatTimeHM } from '../utils/datetime';

import '../../styles/health-detail.css';

const route = useRoute();
const router = useRouter();

const type = computed(() => String(route.params.type || ''));
const elderlyId = computed(() => Number(route.query.elderlyId || localStorage.getItem('elderlyId') || 1));

const loading = ref(false);
const error = ref('');
const records = ref([]);

const sortedRecords = computed(() => {
  const list = Array.isArray(records.value) ? records.value.slice() : [];
  list.sort((a, b) => String(b.recordDate || '').localeCompare(String(a.recordDate || '')));
  return list;
});

const latest = computed(() => sortedRecords.value[0] || null);
const last7 = computed(() => sortedRecords.value.slice(0, 7).slice().reverse());

const descriptor = computed(() => {
  const r = latest.value;
  const t = type.value;

  if (t === 'blood-pressure') {
    const s = statusForBloodPressure(r?.bloodPressure);
    return {
      name: '血压',
      unit: 'mmHg',
      currentValue: r?.bloodPressure || '--/--',
      normalRange: '90-140/60-90',
      status: s.status,
      statusText: s.statusText,
      advice: '建议规律测量血压，保持低盐饮食与适度运动；如持续异常请联系医生。',
    };
  }

  if (t === 'heart-rate') {
    const s = statusForHeartRate(r?.heartRate);
    return {
      name: '心率',
      unit: '次/分',
      currentValue: r?.heartRate ?? '--',
      normalRange: '60-100',
      status: s.status,
      statusText: s.statusText,
      advice: '避免剧烈运动与情绪波动，规律作息；如伴随胸闷头晕请就医。',
    };
  }

  if (t === 'blood-sugar') {
    const s = statusForGlucose(r?.glucoseLevel);
    return {
      name: '血糖',
      unit: 'mmol/L',
      currentValue: r?.glucoseLevel ?? '--',
      normalRange: '3.9-6.1',
      status: s.status,
      statusText: s.statusText,
      advice: '建议少糖少油，按医嘱用药，饭后适度散步并按时复测血糖。',
    };
  }

  if (t === 'temperature') {
    const s = statusForTemperature(r?.temperature);
    return {
      name: '体温',
      unit: '℃',
      currentValue: r?.temperature ?? '--',
      normalRange: '36.0-37.2',
      status: s.status,
      statusText: s.statusText,
      advice: '建议注意保暖与补水；如持续发热或伴随不适请就医。',
    };
  }

  return null;
});

const historyItems = computed(() => {
  const t = type.value;
  return last7.value.map((r) => {
    const time = r?.updatedAt ? formatTimeHM(r.updatedAt) : '';
    const date = r?.recordDate || '';
    const value =
      t === 'blood-pressure'
        ? (r?.bloodPressure || '--/--')
        : t === 'heart-rate'
          ? (r?.heartRate ?? '--')
          : t === 'blood-sugar'
            ? (r?.glucoseLevel ?? '--')
            : (r?.temperature ?? '--');

    return {
      date,
      time,
      value,
    };
  }).reverse();
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
    const data = await healthApi.listHealthRecordsByElderlyId(elderlyId.value);
    records.value = Array.isArray(data) ? data : [];
  } catch (e) {
    error.value = e?.message || '加载失败';
    records.value = [];
  } finally {
    loading.value = false;
  }
}

function valueToNumber(record) {
  const t = type.value;
  if (!record) return null;

  if (t === 'blood-pressure') {
    const { sys } = parseBloodPressure(record.bloodPressure);
    return sys;
  }
  if (t === 'heart-rate') return typeof record.heartRate === 'number' ? record.heartRate : Number(record.heartRate);
  if (t === 'blood-sugar') return typeof record.glucoseLevel === 'number' ? record.glucoseLevel : Number(record.glucoseLevel);
  if (t === 'temperature') return typeof record.temperature === 'number' ? record.temperature : Number(record.temperature);
  return null;
}

function drawTrendChart() {
  const canvas = document.getElementById('trendChart');
  if (!canvas) return;

  const ctx = canvas.getContext('2d');
  if (!ctx) return;

  const width = canvas.offsetWidth;
  const height = canvas.offsetHeight;
  canvas.width = width;
  canvas.height = height;

  const history = last7.value.slice();
  const rawValues = history.map(valueToNumber).filter((v) => Number.isFinite(v));
  if (rawValues.length < 2) {
    ctx.clearRect(0, 0, width, height);
    ctx.fillStyle = '#999';
    ctx.font = '14px sans-serif';
    ctx.fillText('暂无足够数据绘制趋势', 20, 40);
    return;
  }

  const maxValue = Math.max(...rawValues) * 1.1;
  const minValue = Math.min(...rawValues) * 0.9;
  const range = maxValue - minValue || 1;

  ctx.clearRect(0, 0, width, height);

  ctx.strokeStyle = '#f0f0f0';
  ctx.lineWidth = 1;
  for (let i = 0; i <= 4; i++) {
    const y = (height - 40) * i / 4 + 20;
    ctx.beginPath();
    ctx.moveTo(40, y);
    ctx.lineTo(width - 20, y);
    ctx.stroke();
  }

  ctx.strokeStyle = '#7C3AED';
  ctx.lineWidth = 2;
  ctx.beginPath();

  rawValues.forEach((value, index) => {
    const x = 40 + (width - 60) * index / (rawValues.length - 1);
    const y = height - 40 - ((value - minValue) / range) * (height - 60);
    if (index === 0) ctx.moveTo(x, y);
    else ctx.lineTo(x, y);
  });
  ctx.stroke();

  ctx.fillStyle = '#7C3AED';
  rawValues.forEach((value, index) => {
    const x = 40 + (width - 60) * index / (rawValues.length - 1);
    const y = height - 40 - ((value - minValue) / range) * (height - 60);
    ctx.beginPath();
    ctx.arc(x, y, 4, 0, Math.PI * 2);
    ctx.fill();
  });
}

async function refreshAndDraw() {
  await load();
  await nextTick();
  drawTrendChart();
}

watch([type, elderlyId], () => refreshAndDraw());
onMounted(() => refreshAndDraw());
</script>

<template>
  <div class="health-detail-page">
    <div class="top-bar">
      <button class="back-btn" @click="router.back()">
        <svg class="back-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
          <path d="M19 12H5M12 19l-7-7 7-7" />
        </svg>
      </button>
      <h1 class="title">{{ descriptor?.name }}详情</h1>
      <div class="right-action"></div>
    </div>

    <div class="detail-content">
      <div v-if="error" class="card" style="color: #b91c1c;">{{ error }}</div>
      <div v-else-if="loading" class="card">加载中…</div>

      <template v-else>
        <div class="current-value-card">
          <div class="value-label">当前数值</div>
          <div class="value-display">
            {{ descriptor?.currentValue }}<span class="value-unit">{{ descriptor?.unit }}</span>
          </div>
          <div class="value-status" :class="descriptor?.status">{{ statusLabel(descriptor?.status) }}</div>
          <div class="value-range">正常范围: {{ descriptor?.normalRange }} {{ descriptor?.unit }}</div>
        </div>

        <div class="chart-card">
          <div class="chart-title">最近7天趋势</div>
          <canvas id="trendChart" class="chart-canvas"></canvas>
        </div>

        <div class="history-card">
          <div class="history-title">历史记录</div>
          <div class="history-list">
            <div v-for="(h, idx) in historyItems" :key="idx" class="history-item">
              <div>
                <span class="history-date">{{ h.date }}</span>
                <span v-if="h.time" class="history-time">{{ h.time }}</span>
              </div>
              <div class="history-value">{{ h.value }} {{ descriptor?.unit }}</div>
            </div>
          </div>
        </div>

        <div class="advice-card">
          <div class="advice-title">健康建议</div>
          <div class="advice-content">{{ descriptor?.advice }}</div>
        </div>
      </template>
    </div>
  </div>
</template>
