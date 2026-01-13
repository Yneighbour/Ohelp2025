<script setup>
import { computed } from 'vue';
import { useRouter } from 'vue-router';

import { emergencyContacts } from '../data/emergency';

const router = useRouter();

const contacts = computed(() => (Array.isArray(emergencyContacts) ? emergencyContacts : []));

function add() {
  window.alert('演示版本，该功能暂不可用');
}

function call(name) {
  window.alert(`正在拨打 ${name}`);
}

function edit() {
  window.alert('演示版本，该功能暂不可用');
}

function remove() {
  window.alert('演示版本，该功能暂不可用');
}
</script>

<template>
  <div class="emergency-contacts-page">
    <div class="top-bar">
      <button class="back-btn" type="button" @click="router.back()">
        <svg class="back-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor"><path d="M19 12H5M12 19l-7-7 7-7" /></svg>
      </button>
      <h1 class="title">紧急联系人</h1>
      <button class="add-btn" type="button" @click="add">+</button>
    </div>

    <div class="emergency-contacts-content">
      <div class="contacts-tip">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor"><path d="M12 9v4" /><path d="M12 17h.01" /><path d="M10.29 3.86L1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0z" /></svg>
        <span>紧急情况下，系统将自动通知以下联系人</span>
      </div>

      <div class="emergency-contacts-list">
        <div v-for="(c, idx) in contacts" :key="c.id" class="emergency-contact-card">
          <div class="contact-priority">{{ idx + 1 }}</div>
          <div class="contact-avatar">{{ c.name.charAt(0) }}</div>
          <div class="contact-details">
            <div class="contact-name">{{ c.name }}</div>
            <div class="contact-relation">{{ c.relation }}</div>
            <div class="contact-phone">{{ c.phone }}</div>
          </div>
          <div class="contact-actions">
            <button class="contact-action-btn" type="button" @click="call(c.name)">📞</button>
            <button class="contact-action-btn" type="button" @click="edit">✏️</button>
            <button class="contact-action-btn delete" type="button" @click="remove">🗑️</button>
          </div>
        </div>

        <div v-if="!contacts.length" style="padding: 8px 0; color: var(--text-secondary)">暂无联系人</div>
      </div>

      <div class="contacts-note">
        <div class="note-title">💡 温馨提示</div>
        <ul class="note-list">
          <li>建议添加3-5位紧急联系人</li>
          <li>紧急情况下，系统将按优先级顺序通知联系人</li>
          <li>请确保联系人电话号码准确无误</li>
        </ul>
      </div>
    </div>
  </div>
</template>
