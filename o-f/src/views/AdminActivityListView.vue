<script setup>
import { computed, onMounted, ref } from 'vue';

import AdminLayout from '../components/admin/AdminLayout.vue';
import * as activityApi from '../api/activity';
import { adminActivitiesManageData as demoActivities } from '../data/admin';

const loading = ref(false);
const error = ref('');
const keyword = ref('');
const categoryFilter = ref('all');
const statusFilter = ref('all');

const rows = ref([]);

function formatDateTime(value) {
  if (!value) return '';
  const s = String(value);
  return s.length >= 19 ? s.slice(0, 19).replace('T', ' ') : s;
}

function mapCategoryText(category) {
  if (category === 'health') return '健康';
  if (category === 'culture') return '文娱';
  if (category === 'learning') return '学习';
  if (category === 'travel') return '旅游';
  return category || '其他';
}

function mapStatusText(status) {
  if (status === 'ongoing') return '进行中';
  if (status === 'ended') return '已结束';
  if (status === 'cancelled') return '已取消';
  return '未开始';
}

function mapActivity(a) {
  const category = a?.category || 'other';
  const status = a?.status || 'pending';
  return {
    id: a?.id ?? '-',
    name: a?.name || '-',
    category,
    categoryText: mapCategoryText(category),
    time: formatDateTime(a?.startTime) || a?.time || '-',
    location: a?.location || '-',
    enrolled: a?.participants ?? a?.enrolled ?? '--',
    capacity: a?.capacity ?? '--',
    status,
    statusText: mapStatusText(status),
  };
}

const filteredRows = computed(() => {
  const list = Array.isArray(rows.value) ? rows.value : [];
  const key = keyword.value.trim();
  return list.filter((a) => {
    if (categoryFilter.value !== 'all' && a.category !== categoryFilter.value) return false;
    if (statusFilter.value !== 'all' && a.status !== statusFilter.value) return false;
    if (!key) return true;
    return String(a.name).includes(key);
  });
});

async function load() {
  loading.value = true;
  error.value = '';
  try {
    const data = await activityApi.listAll();
    if (Array.isArray(data) && data.length) {
      rows.value = data.map(mapActivity);
      return;
    }
    rows.value = demoActivities.slice();
  } catch (e) {
    error.value = '后端接口不可用，已切换为演示数据';
    rows.value = demoActivities.slice();
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
  window.alert('添加活动\n\n（演示版本，实际会打开添加表单）');
}

function onViewEnrollments(row) {
  window.alert(`查看${row.name}的报名列表\n\n（演示版本，实际会跳转到报名管理页面）`);
}

function onEdit(row) {
  window.alert(`编辑${row.name} (ID: ${row.id})\n\n（演示版本，实际会打开编辑表单）`);
}

function onCancel(row) {
  const ok = window.confirm(`确定要取消活动“${row.name}”吗？\n\n已报名的用户将收到取消通知。`);
  if (!ok) return;
  window.alert(`已取消活动：${row.name}\n\n（演示版本，实际会调用后端API并发送通知）`);
}

onMounted(load);
</script>

<template>
  <AdminLayout active-page="admin-activity-list" title="活动列表">
    <div class="admin-toolbar">
      <div class="admin-search-row">
        <input v-model="keyword" type="text" class="admin-search-input" placeholder="搜索活动名称" />
        <button class="admin-search-btn" type="button" @click="onSearch">搜索</button>
      </div>
      <div class="admin-filter-row">
        <select v-model="categoryFilter" class="admin-filter-select">
          <option value="all">全部类型</option>
          <option value="health">健康</option>
          <option value="culture">文娱</option>
          <option value="learning">学习</option>
          <option value="travel">旅游</option>
        </select>
        <select v-model="statusFilter" class="admin-filter-select">
          <option value="all">全部状态</option>
          <option value="pending">未开始</option>
          <option value="ongoing">进行中</option>
          <option value="ended">已结束</option>
          <option value="cancelled">已取消</option>
        </select>
        <button class="admin-add-btn" type="button" @click="onAdd">+ 添加活动</button>
      </div>
    </div>

    <div v-if="loading" style="color: var(--text-secondary); padding: 6px 0">正在加载...</div>
    <div v-else-if="error" style="color: var(--warning-color); padding: 6px 0">{{ error }}</div>

    <div class="admin-table-container">
      <table class="admin-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>活动名称</th>
            <th>类型</th>
            <th>活动时间</th>
            <th>地点</th>
            <th>报名人数</th>
            <th>状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="a in filteredRows" :key="a.id">
            <td>{{ a.id }}</td>
            <td>{{ a.name }}</td>
            <td><span class="category-tag" :class="a.category">{{ a.categoryText }}</span></td>
            <td>{{ a.time }}</td>
            <td>{{ a.location }}</td>
            <td>{{ a.enrolled }}/{{ a.capacity }}</td>
            <td><span class="status-tag" :class="a.status">{{ a.statusText }}</span></td>
            <td>
              <div class="admin-actions">
                <button class="admin-action-btn view" type="button" @click="onViewEnrollments(a)">报名列表</button>
                <button class="admin-action-btn edit" type="button" @click="onEdit(a)">编辑</button>
                <button v-if="a.status === 'pending'" class="admin-action-btn delete" type="button" @click="onCancel(a)">取消</button>
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
