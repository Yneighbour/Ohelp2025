<script setup>
import { computed, onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';

import * as healthApi from '../api/health';

const router = useRouter();

const loading = ref(false);
const error = ref('');
const records = ref([]);

const elderlyId = computed(() => Number(localStorage.getItem('elderlyId') || localStorage.getItem('userId') || 1));

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

const latest = computed(() => {
  const list = Array.isArray(records.value) ? records.value.slice() : [];
  list.sort((a, b) => String(b.recordDate || '').localeCompare(String(a.recordDate || '')));
  return list[0] || null;
});

const basicGrid = computed(() => {
  const r = latest.value;
  return [
    { label: '血压', value: r?.bloodPressure ? `${r.bloodPressure}` : '--/--' },
    { label: '心率', value: r?.heartRate != null ? `${r.heartRate}` : '--' },
    { label: '体温', value: r?.temperature != null ? `${r.temperature}` : '--' },
    { label: '血糖', value: r?.glucoseLevel != null ? `${r.glucoseLevel}` : '--' },
  ];
});

const checkups = computed(() => {
  const list = Array.isArray(records.value) ? records.value.slice() : [];
  list.sort((a, b) => String(b.recordDate || '').localeCompare(String(a.recordDate || '')));
  return list.slice(0, 5).map((r) => ({
    date: formatYMD(r?.recordDate) || '--',
    name: '健康记录',
    resultText: '正常',
  }));
});

async function load() {
  loading.value = true;
  error.value = '';
  try {
    const data = await healthApi.listByElderlyId(elderlyId.value);
    records.value = Array.isArray(data) ? data : [];
  } catch (e) {
    error.value = e?.message || '加载失败';
  } finally {
    loading.value = false;
  }
}

function demoClick() {
  window.alert('演示版本，该功能暂不可用');
}

onMounted(load);
</script>

<template>
  <div class="health-records-page">
    <div class="top-bar">
      <button class="back-btn" type="button" @click="router.back()">
        <svg class="back-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor"><path d="M19 12H5M12 19l-7-7 7-7" /></svg>
      </button>
      <h1 class="title">健康档案</h1>
      <div class="right-action"></div>
    </div>

    <div class="health-records-content">
      <div v-if="loading" style="padding: 8px 0; color: var(--text-secondary)">正在加载...</div>
      <div v-else-if="error" style="padding: 8px 0; color: var(--danger-color)">{{ error }}</div>

      <div class="record-section">
        <div class="section-title">最近指标</div>
        <div class="record-grid">
          <div v-for="item in basicGrid" :key="item.label" class="record-item">
            <div class="record-label">{{ item.label }}</div>
            <div class="record-value">{{ item.value }}</div>
          </div>
        </div>
      </div>

      <div class="record-section">
        <div class="section-title">慢性病史</div>
        <div class="disease-list">
          <div class="disease-item">
            <div class="disease-icon">💊</div>
            <div class="disease-info">
              <div class="disease-name">高血压</div>
              <div class="disease-time">确诊时间：2020年3月</div>
            </div>
            <div class="disease-status controlled">已控制</div>
          </div>
          <div class="disease-item">
            <div class="disease-icon">🩺</div>
            <div class="disease-info">
              <div class="disease-name">糖尿病</div>
              <div class="disease-time">确诊时间：2021年6月</div>
            </div>
            <div class="disease-status controlled">已控制</div>
          </div>
        </div>
      </div>

      <div class="record-section">
        <div class="section-title">过敏史</div>
        <div class="allergy-list">
          <span class="allergy-tag">青霉素</span>
        </div>
      </div>

      <div class="record-section">
        <div class="section-title">用药记录</div>
        <div class="medicine-list">
          <div class="medicine-item">
            <div class="medicine-name">降压药（硝苯地平）</div>
            <div class="medicine-usage">每日1次，每次1片，早餐后服用</div>
          </div>
          <div class="medicine-item">
            <div class="medicine-name">降糖药（二甲双胍）</div>
            <div class="medicine-usage">每日1次，每次1粒，早餐后服用</div>
          </div>
        </div>
      </div>

      <div class="record-section">
        <div class="section-title">体检记录</div>
        <div class="checkup-list">
          <div v-for="c in checkups" :key="c.date" class="checkup-item" role="button" tabindex="0" @click="demoClick">
            <div class="checkup-date">{{ c.date }}</div>
            <div class="checkup-name">{{ c.name }}</div>
            <div class="checkup-result success">{{ c.resultText }}</div>
            <svg class="checkup-arrow" viewBox="0 0 24 24" fill="none" stroke="currentColor"><path d="M9 18l6-6-6-6" /></svg>
          </div>
          <div v-if="!checkups.length" style="padding: 8px 0; color: var(--text-secondary)">暂无记录</div>
        </div>
      </div>
    </div>
  </div>
</template>
