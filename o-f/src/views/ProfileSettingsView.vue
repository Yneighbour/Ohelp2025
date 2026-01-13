<script setup>
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const healthRemind = ref(true);
const activityNotify = ref(true);
const socialNotify = ref(true);
const locationService = ref(true);

function load() {
  try {
    healthRemind.value = JSON.parse(localStorage.getItem('setting_healthRemind') ?? 'true');
    activityNotify.value = JSON.parse(localStorage.getItem('setting_activityNotify') ?? 'true');
    socialNotify.value = JSON.parse(localStorage.getItem('setting_socialNotify') ?? 'true');
    locationService.value = JSON.parse(localStorage.getItem('setting_locationService') ?? 'true');
  } catch {
    // ignore
  }
}

function persist() {
  localStorage.setItem('setting_healthRemind', JSON.stringify(healthRemind.value));
  localStorage.setItem('setting_activityNotify', JSON.stringify(activityNotify.value));
  localStorage.setItem('setting_socialNotify', JSON.stringify(socialNotify.value));
  localStorage.setItem('setting_locationService', JSON.stringify(locationService.value));
  window.alert('设置已更新');
}

function clearCache() {
  const ok = window.confirm('确定要清除缓存吗？');
  if (!ok) return;
  localStorage.removeItem('readAlertKeys');
  window.alert('已清除');
}

function demoClick() {
  window.alert('演示版本，该功能暂不可用');
}

onMounted(load);
</script>

<template>
  <div class="settings-page">
    <div class="top-bar">
      <button class="back-btn" type="button" @click="router.back()">
        <svg class="back-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor"><path d="M19 12H5M12 19l-7-7 7-7" /></svg>
      </button>
      <h1 class="title">系统设置</h1>
      <div class="right-action"></div>
    </div>

    <div class="settings-content">
      <div class="settings-section">
        <div class="section-title">通知设置</div>

        <div class="setting-item">
          <div class="setting-info">
            <div class="setting-name">健康提醒</div>
            <div class="setting-desc">体检测量、用药等健康提醒</div>
          </div>
          <label class="switch">
            <input type="checkbox" v-model="healthRemind" @change="persist" />
            <span class="slider"></span>
          </label>
        </div>

        <div class="setting-item">
          <div class="setting-info">
            <div class="setting-name">活动通知</div>
            <div class="setting-desc">活动报名、开始提醒</div>
          </div>
          <label class="switch">
            <input type="checkbox" v-model="activityNotify" @change="persist" />
            <span class="slider"></span>
          </label>
        </div>

        <div class="setting-item">
          <div class="setting-info">
            <div class="setting-name">社交消息</div>
            <div class="setting-desc">点赞、评论等消息通知</div>
          </div>
          <label class="switch">
            <input type="checkbox" v-model="socialNotify" @change="persist" />
            <span class="slider"></span>
          </label>
        </div>
      </div>

      <div class="settings-section">
        <div class="section-title">隐私设置</div>

        <div class="setting-item">
          <div class="setting-info">
            <div class="setting-name">位置服务</div>
            <div class="setting-desc">用于紧急呼救定位</div>
          </div>
          <label class="switch">
            <input type="checkbox" v-model="locationService" @change="persist" />
            <span class="slider"></span>
          </label>
        </div>

        <div class="setting-item clickable" @click="demoClick">
          <div class="setting-info">
            <div class="setting-name">账号与安全</div>
            <div class="setting-desc">修改密码、绑定手机号等</div>
          </div>
          <svg class="setting-arrow" viewBox="0 0 24 24" fill="none" stroke="currentColor"><path d="M9 18l6-6-6-6" /></svg>
        </div>
      </div>

      <button class="clear-cache-btn" type="button" @click="clearCache">清除缓存</button>
    </div>
  </div>
</template>
