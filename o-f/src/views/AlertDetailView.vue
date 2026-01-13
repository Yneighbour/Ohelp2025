<script setup>
import { computed, onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import * as healthApi from '../api/health';
import { buildDerivedAlertsFromLatestRecord } from '../utils/health';

import '../../styles/alert-detail.css';

const route = useRoute();
const router = useRouter();

const key = computed(() => String(route.params.key || ''));
const elderlyId = computed(() => Number(localStorage.getItem('elderlyId') || 1));

const loading = ref(false);
const error = ref('');
const alert = ref(null);

async function load() {
  loading.value = true;
  error.value = '';
  try {
    const data = await healthApi.listHealthRecordsByElderlyId(elderlyId.value);
    const list = Array.isArray(data) ? data.slice() : [];
    list.sort((a, b) => String(b.recordDate || '').localeCompare(String(a.recordDate || '')));
    const latest = list[0] || null;
    const alerts = buildDerivedAlertsFromLatestRecord(latest);
    alert.value = alerts.find((a) => a.key === key.value) || null;
    if (!alert.value) error.value = '预警不存在';
  } catch (e) {
    error.value = e?.message || '加载失败';
  } finally {
    loading.value = false;
  }
}

onMounted(load);
</script>

<template>
  <div class="alert-detail-page">
    <div class="top-bar">
      <button class="back-btn" @click="router.back()">
        <svg class="back-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
          <path d="M19 12H5M12 19l-7-7 7-7" />
        </svg>
      </button>
      <h1 class="title">预警详情</h1>
      <div class="right-action"></div>
    </div>

    <div v-if="error" class="card" style="margin: 12px; color: #b91c1c;">{{ error }}</div>
    <div v-else-if="loading" class="card" style="margin: 12px;">加载中…</div>

    <template v-else>
      <div class="alert-detail-content">
        <div class="alert-detail-header">
          <span class="alert-detail-level" :class="alert.level">{{ alert.levelText }}</span>
          <h2 class="alert-detail-title">{{ alert.title }}</h2>
          <div class="alert-detail-time">{{ alert.time }}</div>
        </div>

        <div class="alert-detail-section">
          <div class="section-title">预警说明</div>
          <div class="section-content">{{ alert.content }}</div>
        </div>

        <div class="alert-detail-section">
          <div class="section-title">健康建议</div>
          <div class="section-content">{{ alert.advice }}</div>
        </div>
      </div>

      <div class="alert-detail-actions">
        <button class="action-btn action-btn-secondary" @click="router.back()">返回</button>
        <button class="action-btn action-btn-primary" @click="window.alert('演示版本，该功能暂不可用')">联系医生</button>
      </div>
    </template>
  </div>
</template>
