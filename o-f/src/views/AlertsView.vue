<script setup>
import { computed, onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import * as healthApi from '../api/health';
import { buildDerivedAlertsFromLatestRecord } from '../utils/health';

import '../../styles/alerts.css';

const route = useRoute();
const router = useRouter();

const elderlyId = computed(() => Number(route.query.elderlyId || localStorage.getItem('elderlyId') || 1));

const loading = ref(false);
const error = ref('');
const alerts = ref([]);

const readKeys = computed(() => new Set(JSON.parse(localStorage.getItem('readAlertKeys') || '[]')));

function markRead(key) {
  const keys = new Set(JSON.parse(localStorage.getItem('readAlertKeys') || '[]'));
  keys.add(key);
  localStorage.setItem('readAlertKeys', JSON.stringify(Array.from(keys)));
}

const unreadCount = computed(() => alerts.value.filter(a => !readKeys.value.has(a.key)).length);

async function load() {
  loading.value = true;
  error.value = '';
  try {
    const data = await healthApi.listHealthRecordsByElderlyId(elderlyId.value);
    const list = Array.isArray(data) ? data.slice() : [];
    list.sort((a, b) => String(b.recordDate || '').localeCompare(String(a.recordDate || '')));
    const latest = list[0] || null;
    alerts.value = buildDerivedAlertsFromLatestRecord(latest);
  } catch (e) {
    error.value = e?.message || '加载失败';
    alerts.value = [];
  } finally {
    loading.value = false;
  }
}

function openAlert(a) {
  markRead(a.key);
  router.push(`/alerts/${encodeURIComponent(a.key)}`);
}

onMounted(load);
</script>

<template>
  <div class="alerts-page">
    <div class="top-bar">
      <button class="back-btn" @click="router.back()">
        <svg class="back-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
          <path d="M19 12H5M12 19l-7-7 7-7" />
        </svg>
      </button>
      <h1 class="title">健康预警</h1>
      <div class="right-action"></div>
    </div>

    <div class="alerts-content">
      <div class="alerts-stats">
        <span class="stats-text">您有</span>
        <span class="stats-number">{{ unreadCount }}</span>
        <span class="stats-text">条未读预警</span>
      </div>

      <div v-if="error" class="card" style="margin: 12px; color: #b91c1c;">{{ error }}</div>
      <div v-else-if="loading" class="card" style="margin: 12px;">加载中…</div>

      <div v-else class="alerts-list">
        <div
          v-for="a in alerts"
          :key="a.key"
          class="alert-item"
          :class="[a.level, !readKeys.has(a.key) ? 'unread' : '']"
          @click="openAlert(a)"
        >
          <div class="alert-header">
            <div class="alert-icon" :class="a.level">{{ a.level === 'urgent' ? '⚠️' : a.level === 'important' ? '❗' : 'ℹ️' }}</div>
            <div class="alert-header-content">
              <div class="alert-title-row">
                <div class="alert-title">{{ a.title }}</div>
                <span class="alert-level" :class="a.level">{{ a.levelText }}</span>
              </div>
              <div class="alert-time">{{ a.time }}</div>
            </div>
          </div>
          <div class="alert-summary">{{ a.summary }}</div>
        </div>

        <div v-if="alerts.length === 0" class="card" style="margin: 12px;">暂无预警</div>
      </div>
    </div>
  </div>
</template>
