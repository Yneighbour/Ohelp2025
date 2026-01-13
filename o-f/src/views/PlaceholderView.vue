<script setup>
import { useRoute, useRouter } from 'vue-router';

const route = useRoute();
const router = useRouter();
</script>

<template>
  <div class="page">
    <div class="top-bar">
      <button class="back-btn" @click="router.back()">
        <svg class="back-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
          <path d="M19 12H5M12 19l-7-7 7-7" />
        </svg>
      </button>
      <h1 class="title">{{ route.meta?.title || '功能页' }}</h1>
      <div class="right-action"></div>
    </div>

    <div class="container">
      <div class="card">
        <div style="font-size: 16px; font-weight: 600; margin-bottom: 8px;">该模块待迁移</div>
        <div style="opacity: 0.8; line-height: 1.6;">
          当前已优先完成“健康(health_record) + 后端接口接入”。
          如果你希望我按原静态演示继续迁移该页面，我会保持样式与交互一致。
        </div>
      </div>
    </div>
  </div>
</template>
