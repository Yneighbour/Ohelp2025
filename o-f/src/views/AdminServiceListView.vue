<script setup>
import { computed, ref } from 'vue';

import AdminLayout from '../components/admin/AdminLayout.vue';
import { adminServicesData, serviceCategories } from '../data/admin';

const keyword = ref('');
const categoryFilter = ref('all');
const statusFilter = ref('all');

const rows = ref(adminServicesData.slice());

const filteredRows = computed(() => {
  const list = Array.isArray(rows.value) ? rows.value : [];
  const key = keyword.value.trim();
  return list.filter((s) => {
    if (categoryFilter.value !== 'all' && s.category !== categoryFilter.value) return false;
    if (statusFilter.value !== 'all' && s.status !== statusFilter.value) return false;
    if (!key) return true;
    return String(s.name).includes(key);
  });
});

function onSearch() {
  if (!keyword.value.trim()) {
    window.alert('请输入搜索关键词');
    return;
  }
}

function onAdd() {
  window.alert('添加服务\n\n（演示版本，实际会打开添加表单）');
}

function onEdit(row) {
  window.alert(`编辑${row.name} (ID: ${row.id})\n\n（演示版本，实际会打开编辑表单）`);
}

function onToggle(row) {
  const action = row.status === 'online' ? '下架' : '上架';
  const ok = window.confirm(`确定要${action}服务“${row.name}”吗？`);
  if (!ok) return;
  window.alert(`已${action}服务：${row.name}\n\n（演示版本，实际会调用后端API更新状态）`);
}

function onDelete(row) {
  const ok = window.confirm(`确定要删除“${row.name}”吗？\n\n此操作不可恢复！`);
  if (!ok) return;
  window.alert(`已删除${row.name} (ID: ${row.id})\n\n（演示版本，实际会调用后端API删除）`);
}
</script>

<template>
  <AdminLayout active-page="admin-service-list" title="服务项目">
    <div class="admin-toolbar">
      <div class="admin-search-row">
        <input v-model="keyword" type="text" class="admin-search-input" placeholder="搜索服务名称" />
        <button class="admin-search-btn" type="button" @click="onSearch">搜索</button>
      </div>
      <div class="admin-filter-row">
        <select v-model="categoryFilter" class="admin-filter-select">
          <option value="all">全部类型</option>
          <option v-for="c in serviceCategories" :key="c.id" :value="c.id" v-show="c.id !== 'all'">
            {{ c.name }}
          </option>
        </select>
        <select v-model="statusFilter" class="admin-filter-select">
          <option value="all">全部状态</option>
          <option value="online">上架</option>
          <option value="offline">下架</option>
        </select>
        <button class="admin-add-btn" type="button" @click="onAdd">+ 添加服务</button>
      </div>
    </div>

    <div class="admin-table-container">
      <table class="admin-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>服务名称</th>
            <th>服务类型</th>
            <th>价格</th>
            <th>状态</th>
            <th>服务描述</th>
            <th>创建时间</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="s in filteredRows" :key="s.id">
            <td>{{ s.id }}</td>
            <td>{{ s.name }}</td>
            <td><span class="category-tag" :class="s.category">{{ s.categoryText }}</span></td>
            <td>{{ s.price }}</td>
            <td><span class="status-tag" :class="s.status">{{ s.statusText }}</span></td>
            <td style="max-width: 150px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis" :title="s.description">
              {{ s.description }}
            </td>
            <td>{{ s.createTime }}</td>
            <td>
              <div class="admin-actions">
                <button class="admin-action-btn edit" type="button" @click="onEdit(s)">编辑</button>
                <button class="admin-action-btn toggle" type="button" @click="onToggle(s)">
                  {{ s.status === 'online' ? '下架' : '上架' }}
                </button>
                <button class="admin-action-btn delete" type="button" @click="onDelete(s)">删除</button>
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
