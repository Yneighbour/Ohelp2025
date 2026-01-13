<script setup>
import { computed } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const nickname = computed(() => localStorage.getItem('username') || '张大爷');
const avatarText = computed(() => String(nickname.value || '我').charAt(0));
const role = computed(() => localStorage.getItem('role') || 'elderly');

function edit() {
  window.alert('演示版本，该功能暂不可用');
}
</script>

<template>
  <div class="profile-info-page">
    <div class="top-bar">
      <button class="back-btn" type="button" @click="router.back()">
        <svg class="back-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor"><path d="M19 12H5M12 19l-7-7 7-7" /></svg>
      </button>
      <h1 class="title">个人资料</h1>
      <button class="edit-btn" type="button" @click="edit">编辑</button>
    </div>

    <div class="profile-info-content">
      <div class="info-section">
        <div class="info-item" role="button" tabindex="0" @click="edit">
          <div class="info-label">头像</div>
          <div class="info-value">
            <div class="avatar-preview">{{ avatarText }}</div>
            <svg class="info-arrow" viewBox="0 0 24 24" fill="none" stroke="currentColor"><path d="M9 18l6-6-6-6" /></svg>
          </div>
        </div>

        <div class="info-item" role="button" tabindex="0" @click="edit">
          <div class="info-label">昵称</div>
          <div class="info-value">{{ nickname }}<svg class="info-arrow" viewBox="0 0 24 24" fill="none" stroke="currentColor"><path d="M9 18l6-6-6-6" /></svg></div>
        </div>

        <div class="info-item" role="button" tabindex="0" @click="edit">
          <div class="info-label">角色</div>
          <div class="info-value">{{ role }}<svg class="info-arrow" viewBox="0 0 24 24" fill="none" stroke="currentColor"><path d="M9 18l6-6-6-6" /></svg></div>
        </div>

        <div class="info-item" role="button" tabindex="0" @click="edit">
          <div class="info-label">手机号</div>
          <div class="info-value">138****8888<svg class="info-arrow" viewBox="0 0 24 24" fill="none" stroke="currentColor"><path d="M9 18l6-6-6-6" /></svg></div>
        </div>

        <div class="info-item" role="button" tabindex="0" @click="edit">
          <div class="info-label">个人简介</div>
          <div class="info-value">热爱生活，享受健康<svg class="info-arrow" viewBox="0 0 24 24" fill="none" stroke="currentColor"><path d="M9 18l6-6-6-6" /></svg></div>
        </div>
      </div>
    </div>
  </div>
</template>
