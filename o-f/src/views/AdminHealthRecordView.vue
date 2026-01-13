<script setup>
import { computed, onMounted, ref } from 'vue';

import AdminLayout from '../components/admin/AdminLayout.vue';
import * as healthApi from '../api/health';
import { adminHealthRecordsData as demoRecords } from '../data/admin';

const loading = ref(false);
const error = ref('');
const keyword = ref('');
const typeFilter = ref('all');
const statusFilter = ref('all');

const rows = ref([]);

function formatDate(value) {
  if (!value) return '';
  const s = String(value);
  return s.length >= 19 ? s.slice(0, 19).replace('T', ' ') : (s.length >= 10 ? s.slice(0, 10) : s);
}

function guessRecordType(r) {
  if (r?.bloodPressure) return '血压';
  if (r?.heartRate != null) return '心率';
  if (r?.glucoseLevel != null) return '血糖';
  if (r?.temperature != null) return '体温';
  return '健康记录';
}

function guessValue(r) {
  if (r?.bloodPressure) return `${r.bloodPressure} mmHg`;
  if (r?.heartRate != null) return `${r.heartRate} bpm`;
  if (r?.glucoseLevel != null) return `${r.glucoseLevel} mmol/L`;
  if (r?.temperature != null) return `${r.temperature} ℃`;
  return '--';
}

function mapStatus(r) {
  // 后端并未提供状态，这里用演示逻辑：只要有值就 normal
  return { status: 'normal', statusText: '正常' };
}

function mapRecord(r) {
  const s = mapStatus(r);
  return {
    id: r?.id ?? '-',
    elderName: r?.elderName || (r?.elderlyId != null ? `老人#${r.elderlyId}` : '-'),
    recordType: guessRecordType(r),
    value: guessValue(r),
    status: s.status,
    statusText: s.statusText,
    recordTime: formatDate(r?.recordDate || r?.createdAt || r?.recordTime),
    operator: r?.operator || (r?.doctorId != null ? `医生#${r.doctorId}` : '--'),
  };
}

const filteredRows = computed(() => {
  const list = Array.isArray(rows.value) ? rows.value : [];
  const key = keyword.value.trim();
  return list.filter((r) => {
    if (typeFilter.value !== 'all' && String(r.recordType) !== typeFilter.value) return false;
    if (statusFilter.value !== 'all' && r.status !== statusFilter.value) return false;
    if (!key) return true;
    return String(r.elderName).includes(key);
  });
});

async function load() {
  loading.value = true;
  error.value = '';
  try {
    const data = await healthApi.listAllHealthRecords();
    if (Array.isArray(data) && data.length) {
      rows.value = data.map(mapRecord);
      return;
    }
    rows.value = demoRecords.slice();
  } catch (e) {
    error.value = '后端接口不可用，已切换为演示数据';
    rows.value = demoRecords.slice();
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
  window.alert('添加健康记录\n\n（演示版本，实际会打开添加表单）');
}

function onView(row) {
  window.alert(`查看${row.elderName}的${row.recordType}记录详情 (ID: ${row.id})\n\n（演示版本，实际会打开详情页面）`);
}

function onEdit(row) {
  window.alert(`编辑${row.elderName}的记录 (ID: ${row.id})\n\n（演示版本，实际会打开编辑表单）`);
}

function onDelete(row) {
  const ok = window.confirm('确定要删除该记录吗？\n\n此操作不可恢复！');
  if (!ok) return;
  window.alert(`已删除记录 (ID: ${row.id})\n\n（演示版本，实际会调用后端API删除）`);
}

onMounted(load);
</script>

<template>
  <AdminLayout active-page="admin-health-record" title="健康记录">
    <div class="admin-toolbar">
      <div class="admin-search-row">
        <input v-model="keyword" type="text" class="admin-search-input" placeholder="搜索老人姓名" />
        <button class="admin-search-btn" type="button" @click="onSearch">搜索</button>
      </div>
      <div class="admin-filter-row">
        <select v-model="typeFilter" class="admin-filter-select">
          <option value="all">全部类型</option>
          <option value="血压">血压</option>
          <option value="心率">心率</option>
          <option value="血糖">血糖</option>
          <option value="体温">体温</option>
        </select>
        <select v-model="statusFilter" class="admin-filter-select">
          <option value="all">全部状态</option>
          <option value="normal">正常</option>
          <option value="warning">偏高/偏低</option>
          <option value="danger">异常</option>
        </select>
        <button class="admin-add-btn" type="button" @click="onAdd">+ 添加记录</button>
      </div>
    </div>

    <div v-if="loading" style="color: var(--text-secondary); padding: 6px 0">正在加载...</div>
    <div v-else-if="error" style="color: var(--warning-color); padding: 6px 0">{{ error }}</div>

    <div class="admin-table-container">
      <table class="admin-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>老人姓名</th>
            <th>记录类型</th>
            <th>测量值</th>
            <th>状态</th>
            <th>记录时间</th>
            <th>操作人</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="r in filteredRows" :key="r.id">
            <td>{{ r.id }}</td>
            <td>{{ r.elderName }}</td>
            <td><span class="category-tag health">{{ r.recordType }}</span></td>
            <td>{{ r.value }}</td>
            <td><span class="status-tag" :class="r.status">{{ r.statusText }}</span></td>
            <td>{{ r.recordTime }}</td>
            <td>{{ r.operator }}</td>
            <td>
              <div class="admin-actions">
                <button class="admin-action-btn view" type="button" @click="onView(r)">详情</button>
                <button class="admin-action-btn edit" type="button" @click="onEdit(r)">编辑</button>
                <button class="admin-action-btn delete" type="button" @click="onDelete(r)">删除</button>
              </div>
            </td>
          </tr>
          <tr v-if="!filteredRows.length">
            <td colspan="8" style="text-align: center; color: var(--text-secondary); padding: 16px 0">暂无数据</td>
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
