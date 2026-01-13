<script setup>
import { computed, ref } from 'vue';

import AdminLayout from '../components/admin/AdminLayout.vue';
import { adminEnrollmentsData } from '../data/admin';

const keyword = ref('');
const statusFilter = ref('all');

const rows = ref(adminEnrollmentsData.slice());

const filteredRows = computed(() => {
  const list = Array.isArray(rows.value) ? rows.value : [];
  const key = keyword.value.trim();
  return list.filter((e) => {
    if (statusFilter.value !== 'all' && e.status !== statusFilter.value) return false;
    if (!key) return true;
    return String(e.activityName).includes(key) || String(e.elderName).includes(key);
  });
});

function onSearch() {
  if (!keyword.value.trim()) {
    window.alert('请输入搜索关键词');
    return;
  }
}

function onAdd() {
  window.alert('添加报名\n\n（演示版本，实际会打开添加表单）');
}

function onConfirm(row) {
  const ok = window.confirm(`确定要确认${row.elderName}参加“${row.activityName}”的报名吗？`);
  if (!ok) return;
  window.alert(`已确认${row.elderName}的报名\n\n（演示版本，实际会调用后端API更新状态）`);
}

function onCheckIn(row) {
  window.alert(`${row.elderName}已签到参加“${row.activityName}”\n\n（演示版本，实际会调用后端API记录签到）`);
}

function onCancel(row) {
  const ok = window.confirm(`确定要取消${row.elderName}参加“${row.activityName}”的报名吗？`);
  if (!ok) return;
  window.alert(`已取消${row.elderName}的报名\n\n（演示版本，实际会调用后端API更新状态）`);
}
</script>

<template>
  <AdminLayout active-page="admin-enrollment" title="报名管理">
    <div class="admin-toolbar">
      <div class="admin-search-row">
        <input v-model="keyword" type="text" class="admin-search-input" placeholder="搜索活动/老人姓名" />
        <button class="admin-search-btn" type="button" @click="onSearch">搜索</button>
      </div>
      <div class="admin-filter-row">
        <select v-model="statusFilter" class="admin-filter-select">
          <option value="all">全部状态</option>
          <option value="pending">待确认</option>
          <option value="confirmed">已确认</option>
          <option value="attended">已签到</option>
          <option value="absent">未参加</option>
        </select>
        <button class="admin-add-btn" type="button" @click="onAdd">+ 添加报名</button>
      </div>
    </div>

    <div class="admin-table-container">
      <table class="admin-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>活动名称</th>
            <th>老人姓名</th>
            <th>联系电话</th>
            <th>报名时间</th>
            <th>状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="e in filteredRows" :key="e.id">
            <td>{{ e.id }}</td>
            <td>{{ e.activityName }}</td>
            <td>{{ e.elderName }}</td>
            <td>{{ e.phone }}</td>
            <td>{{ e.enrollTime }}</td>
            <td><span class="status-tag" :class="e.status">{{ e.statusText }}</span></td>
            <td>
              <div class="admin-actions">
                <button v-if="e.status === 'pending'" class="admin-action-btn view" type="button" @click="onConfirm(e)">确认</button>
                <button v-if="e.status === 'confirmed'" class="admin-action-btn view" type="button" @click="onCheckIn(e)">签到</button>
                <button v-if="e.status !== 'attended' && e.status !== 'absent'" class="admin-action-btn delete" type="button" @click="onCancel(e)">
                  取消
                </button>
              </div>
            </td>
          </tr>
          <tr v-if="!filteredRows.length">
            <td colspan="7" style="text-align: center; color: var(--text-secondary); padding: 16px 0">暂无数据</td>
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
