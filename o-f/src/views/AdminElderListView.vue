<script setup>
import { computed, onMounted, ref } from 'vue';

import AdminLayout from '../components/admin/AdminLayout.vue';
import * as elderApi from '../api/elder';
import { adminEldersData as demoElders } from '../data/admin';

const loading = ref(false);
const error = ref('');
const keyword = ref('');
const healthFilter = ref('all');

const rows = ref([]);

function formatDate(value) {
  if (!value) return '';
  const s = String(value);
  return s.length >= 10 ? s.slice(0, 10) : s;
}

function mapHealthStatus(status) {
  if (status === 'danger') return { cls: 'danger', text: '需关注' };
  if (status === 'warning') return { cls: 'warning', text: '亚健康' };
  return { cls: 'normal', text: '健康' };
}

function mapElder(e) {
  const hs = mapHealthStatus(e?.healthStatus);
  return {
    id: e?.id ?? '-',
    name: e?.name || '-',
    age: e?.age ?? '-',
    gender: e?.gender || '-',
    phone: e?.phoneNumber || e?.phone || '-',
    emergencyContact: e?.contactPerson || e?.emergencyContact || '-',
    healthStatus: hs.cls,
    healthText: hs.text,
    createTime: formatDate(e?.createdAt || e?.createTime),
  };
}

const filteredRows = computed(() => {
  const list = Array.isArray(rows.value) ? rows.value : [];
  const key = keyword.value.trim();
  return list.filter((e) => {
    if (healthFilter.value !== 'all' && e.healthStatus !== healthFilter.value) return false;
    if (!key) return true;
    return String(e.name).includes(key) || String(e.phone).includes(key);
  });
});

async function load() {
  loading.value = true;
  error.value = '';
  try {
    const data = await elderApi.listAll();
    if (Array.isArray(data) && data.length) {
      rows.value = data.map(mapElder);
      return;
    }
    rows.value = demoElders.slice();
  } catch (e) {
    error.value = '后端接口不可用，已切换为演示数据';
    rows.value = demoElders.slice();
  } finally {
    loading.value = false;
  }
}

function onSearch() {
  if (!keyword.value.trim()) {
    window.alert('请输入搜索关键词');
    return;
  }
}

function onAdd() {
  window.alert('添加老人档案\n\n（演示版本，实际会打开添加表单）');
}

function onViewHealth(row) {
  window.alert(`查看${row.name}的健康档案\n\n（演示版本，实际会跳转到健康档案页面）`);
}

function onEdit(row) {
  window.alert(`编辑${row.name} (ID: ${row.id})\n\n（演示版本，实际会打开编辑表单）`);
}

function onDelete(row) {
  const ok = window.confirm(`确定要删除“${row.name}”吗？\n\n此操作不可恢复！`);
  if (!ok) return;
  window.alert(`已删除${row.name} (ID: ${row.id})\n\n（演示版本，实际会调用后端API删除）`);
}

onMounted(load);
</script>

<template>
  <AdminLayout active-page="admin-elder-list" title="老人档案">
    <div class="admin-toolbar">
      <div class="admin-search-row">
        <input v-model="keyword" type="text" class="admin-search-input" placeholder="搜索姓名/电话" />
        <button class="admin-search-btn" type="button" @click="onSearch">搜索</button>
      </div>
      <div class="admin-filter-row">
        <select v-model="healthFilter" class="admin-filter-select">
          <option value="all">全部状态</option>
          <option value="normal">健康</option>
          <option value="warning">亚健康</option>
          <option value="danger">需关注</option>
        </select>
        <button class="admin-add-btn" type="button" @click="onAdd">+ 添加档案</button>
      </div>
    </div>

    <div v-if="loading" style="color: var(--text-secondary); padding: 6px 0">正在加载...</div>
    <div v-else-if="error" style="color: var(--warning-color); padding: 6px 0">{{ error }}</div>

    <div class="admin-table-container">
      <table class="admin-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>姓名</th>
            <th>年龄</th>
            <th>性别</th>
            <th>联系电话</th>
            <th>紧急联系人</th>
            <th>健康状态</th>
            <th>建档时间</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="e in filteredRows" :key="e.id">
            <td>{{ e.id }}</td>
            <td>{{ e.name }}</td>
            <td>{{ e.age }}</td>
            <td>{{ e.gender }}</td>
            <td>{{ e.phone }}</td>
            <td>{{ e.emergencyContact }}</td>
            <td><span class="status-tag" :class="e.healthStatus">{{ e.healthText }}</span></td>
            <td>{{ e.createTime }}</td>
            <td>
              <div class="admin-actions">
                <button class="admin-action-btn view" type="button" @click="onViewHealth(e)">健康档案</button>
                <button class="admin-action-btn edit" type="button" @click="onEdit(e)">编辑</button>
                <button class="admin-action-btn delete" type="button" @click="onDelete(e)">删除</button>
              </div>
            </td>
          </tr>
          <tr v-if="!filteredRows.length">
            <td colspan="9" style="text-align: center; color: var(--text-secondary); padding: 16px 0">暂无数据</td>
          </tr>
        </tbody>
      </table>
    </div>

    <div class="admin-pagination">
      <span>共 {{ filteredRows.length }} 条记录</span>
      <span>第 1/1 页</span>
    </div>
  </AdminLayout>
</template>
