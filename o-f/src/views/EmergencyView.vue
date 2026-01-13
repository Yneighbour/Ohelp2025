<script setup>
import { computed, onBeforeUnmount, onMounted, ref } from 'vue';

import '../../styles/emergency.css';

import { emergencyContacts, locationData } from '../data/emergency';
import * as emergencyApi from '../api/emergency';

const loadingHistory = ref(false);
const historyError = ref('');
const history = ref([]);

const countdown = ref(0);
const pressing = ref(false);
let holdTimer = null;
let countdownTimer = null;

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

function formatYMDHM(value) {
  const d = toDate(value);
  if (!d) return '';
  return `${d.getFullYear()}-${pad2(d.getMonth() + 1)}-${pad2(d.getDate())} ${pad2(d.getHours())}:${pad2(d.getMinutes())}`;
}

function historyIcon(type) {
  const t = String(type || '');
  if (t.includes('摔') || t.includes('跌')) return '🩹';
  if (t.includes('火') || t.includes('燃')) return '🔥';
  if (t.includes('不适') || t.includes('身体')) return '🚑';
  return '🚨';
}

function historyStatusText(status) {
  const s = String(status || '');
  if (s === 'pending') return '处理中';
  if (s === 'responded') return '已响应';
  if (s === 'resolved') return '已解决';
  return s || '未知';
}

function historyStatusClass(status) {
  const s = String(status || '');
  return s === 'resolved' ? 'success' : '';
}

const historyView = computed(() => {
  const list = Array.isArray(history.value) ? history.value : [];
  const sorted = list.slice().sort((a, b) => {
    const ta = toDate(a?.createdAt || a?.updatedAt)?.getTime() || 0;
    const tb = toDate(b?.createdAt || b?.updatedAt)?.getTime() || 0;
    return tb - ta;
  });

  return sorted.slice(0, 8).map((h) => {
    return {
      id: h?.id,
      type: h?.type || '紧急求助',
      time: formatYMDHM(h?.createdAt || h?.updatedAt) || '--',
      icon: historyIcon(h?.type),
      statusText: historyStatusText(h?.status),
      statusClass: historyStatusClass(h?.status),
    };
  });
});

async function loadHistory() {
  loadingHistory.value = true;
  historyError.value = '';
  try {
    const data = await emergencyApi.listByElderlyId(elderlyId.value);
    history.value = Array.isArray(data) ? data : [];
  } catch (e) {
    historyError.value = e?.message || '加载失败';
  } finally {
    loadingHistory.value = false;
  }
}

async function sendEmergency(reason = '紧急呼救') {
  try {
    await emergencyApi.create({
      elderlyId: elderlyId.value,
      type: '身体不适',
      description: reason,
      location: locationData.address,
      contactPhone: emergencyContacts[0]?.phone || '',
      priority: 'high',
      isActive: true,
    });
    window.alert('紧急呼救已发送！');
    await loadHistory();
  } catch (e) {
    window.alert(e?.message || '呼救发送失败（演示环境）');
  }
}

function clearTimers() {
  if (holdTimer) {
    clearTimeout(holdTimer);
    holdTimer = null;
  }
  if (countdownTimer) {
    clearInterval(countdownTimer);
    countdownTimer = null;
  }
}

function startHold() {
  if (pressing.value) return;
  pressing.value = true;

  clearTimers();
  countdown.value = 3;
  countdownTimer = setInterval(() => {
    countdown.value = Math.max(0, countdown.value - 1);
  }, 1000);

  holdTimer = setTimeout(async () => {
    clearTimers();
    pressing.value = false;
    countdown.value = 0;
    await sendEmergency('长按触发紧急呼救');
  }, 3000);
}

function endHold() {
  if (!pressing.value) return;
  pressing.value = false;
  clearTimers();
  countdown.value = 0;
}

function quickDial(label) {
  window.alert(`正在拨打 ${label}`);
}

function shareLocation() {
  window.alert('位置已分享');
}

function addContact() {
  window.alert('演示版本，该功能暂不可用');
}

function callContact(name) {
  window.alert(`正在拨打 ${name}`);
}

onMounted(loadHistory);
onBeforeUnmount(clearTimers);
</script>

<template>
  <div class="emergency-page">
    <div class="emergency-content">
      <div class="emergency-button-container">
        <button
          class="emergency-button"
          type="button"
          @click="sendEmergency('点击触发紧急呼救')"
          @mousedown="startHold"
          @mouseup="endHold"
          @mouseleave="endHold"
          @touchstart.passive="startHold"
          @touchend="endHold"
          @touchcancel="endHold"
        >
          <span class="emergency-icon">🚨</span>
          <span class="emergency-text">紧急呼救</span>
        </button>
        <div class="emergency-hint">长按3秒自动呼救</div>
        <div v-if="countdown" class="emergency-countdown">
          <span id="countdown-num">{{ countdown }}</span>
        </div>
      </div>

      <div class="quick-dial-section">
        <div class="quick-dial-title">快速拨号</div>
        <div class="quick-dial-grid">
          <button class="quick-dial-btn" type="button" @click="quickDial('120')">
            <div class="dial-icon">🚑</div>
            <div class="dial-label">急救中心</div>
            <div class="dial-number">120</div>
          </button>
          <button class="quick-dial-btn" type="button" @click="quickDial('110')">
            <div class="dial-icon">🚓</div>
            <div class="dial-label">报警电话</div>
            <div class="dial-number">110</div>
          </button>
          <button class="quick-dial-btn" type="button" @click="quickDial('119')">
            <div class="dial-icon">🚒</div>
            <div class="dial-label">火警电话</div>
            <div class="dial-number">119</div>
          </button>
          <button class="quick-dial-btn" type="button" @click="quickDial('社区医院')">
            <div class="dial-icon">🏥</div>
            <div class="dial-label">社区医院</div>
            <div class="dial-number">社区</div>
          </button>
        </div>
      </div>

      <div class="location-card">
        <div class="location-header">
          <div class="location-title">📍 当前位置</div>
        </div>
        <div class="location-address">{{ locationData.address }}</div>
        <button class="share-location-btn" type="button" @click="shareLocation">分享位置</button>
      </div>

      <div class="contacts-section">
        <div class="contacts-header">
          <div class="contacts-title">紧急联系人</div>
          <button class="add-contact-btn" type="button" @click="addContact">+ 添加</button>
        </div>
        <div class="contacts-list">
          <div v-for="c in emergencyContacts" :key="c.id" class="contact-card">
            <div class="contact-avatar">{{ c.name.charAt(0) }}</div>
            <div class="contact-info">
              <div class="contact-name">{{ c.name }}</div>
              <div class="contact-relation">{{ c.relation }}</div>
              <div class="contact-phone">{{ c.phone }}</div>
            </div>
            <button class="contact-call-btn" type="button" @click="callContact(c.name)">📞</button>
          </div>
        </div>
      </div>

      <div class="history-section">
        <div class="history-title">历史记录</div>
        <div v-if="loadingHistory" class="empty-state" style="padding: 8px 0; color: var(--text-secondary)">正在加载...</div>
        <div v-else-if="historyError" class="empty-state" style="padding: 8px 0; color: var(--danger-color)">{{ historyError }}</div>
        <div v-else class="history-list">
          <div v-for="h in historyView" :key="h.id" class="history-item">
            <div class="history-icon">{{ h.icon }}</div>
            <div class="history-content">
              <div class="history-text">{{ h.type }}</div>
              <div class="history-time">{{ h.time }}</div>
            </div>
            <div class="history-status" :class="h.statusClass">{{ h.statusText }}</div>
          </div>
          <div v-if="!historyView.length" class="empty-state" style="padding: 8px 0; color: var(--text-secondary)">暂无记录</div>
        </div>
      </div>
    </div>
  </div>
</template>
