<script setup>
import { onMounted, ref } from 'vue';
import * as usersApi from '../api/users';

const loading = ref(true);
const error = ref('');
const users = ref([]);

async function load() {
  loading.value = true;
  error.value = '';
  try {
    const data = await usersApi.listUsers();
    users.value = Array.isArray(data) ? data : [];
  } catch (e) {
    error.value = e?.message || '加载失败';
  } finally {
    loading.value = false;
  }
}

onMounted(load);
</script>

<template>
  <div class="card">
    <div style="display: flex; justify-content: space-between; align-items: center; gap: 12px;">
      <h2 style="margin: 0;">用户列表</h2>
      <button class="btn" @click="load" :disabled="loading">刷新</button>
    </div>

    <p style="margin-top: 8px; opacity: 0.75;">
      这是“接入后端接口”的最小可用示例：GET /api/user/
    </p>

    <div v-if="error" class="error">{{ error }}</div>
    <div v-else-if="loading">加载中…</div>

    <table v-else style="width: 100%; border-collapse: collapse; margin-top: 12px;">
      <thead>
        <tr style="text-align: left; border-bottom: 1px solid rgba(0,0,0,0.08);">
          <th style="padding: 10px;">ID</th>
          <th style="padding: 10px;">姓名</th>
          <th style="padding: 10px;">电话</th>
          <th style="padding: 10px;">角色</th>
          <th style="padding: 10px;">状态</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="u in users" :key="u.id" style="border-bottom: 1px solid rgba(0,0,0,0.06);">
          <td style="padding: 10px;">{{ u.id }}</td>
          <td style="padding: 10px;">{{ u.name }}</td>
          <td style="padding: 10px;">{{ u.phone }}</td>
          <td style="padding: 10px;">{{ u.role }}</td>
          <td style="padding: 10px;">{{ u.isActive ? '启用' : '停用' }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>
