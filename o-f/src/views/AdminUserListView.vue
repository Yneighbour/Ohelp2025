<script setup>
import { computed, onMounted, ref } from 'vue';

import AdminLayout from '../components/admin/AdminLayout.vue';
import * as usersApi from '../api/users';
import { adminUsersData as demoUsers } from '../data/admin';

const loading = ref(false);
const error = ref('');
const keyword = ref('');
const statusFilter = ref('all');
const roleFilter = ref('all');

const rows = ref([]);

function formatDate(value) {
  if (!value) return '';
  const s = String(value);
  if (s.length >= 10) return s.slice(0, 10);
  return s;
}

function mapRoleText(role) {
  if (role === 'admin') return '管理员';
  if (role === 'operator') return '操作员';
  return '普通用户';
}

function mapUser(u) {
  const role = u?.role || 'user';
  const isActive = u?.isActive !== false;
  return {
    id: u?.id ?? '-',
    username: u?.name || u?.username || '-',
    phone: u?.phone || '-',
    role,
    roleText: mapRoleText(role),
    status: isActive ? 'active' : 'disabled',
    statusText: isActive ? '正常' : '禁用',
    createTime: formatDate(u?.createdAt || u?.createTime),
  };
}

const filteredRows = computed(() => {
  const list = Array.isArray(rows.value) ? rows.value : [];
  const key = keyword.value.trim();
  return list.filter((u) => {
    if (statusFilter.value !== 'all' && u.status !== statusFilter.value) return false;
    if (roleFilter.value !== 'all' && u.role !== roleFilter.value) return false;
    if (!key) return true;
    return String(u.username).includes(key) || String(u.phone).includes(key);
  });
});

async function load() {
  loading.value = true;
  error.value = '';
  try {
    const data = await usersApi.listUsers();
    if (Array.isArray(data) && data.length) {
      rows.value = data.map(mapUser);
      return;
    }
    rows.value = demoUsers.slice();
  } catch (e) {
    error.value = '后端接口不可用，已切换为演示数据';
    rows.value = demoUsers.slice();
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
  window.alert('添加用户\n\n（演示版本，实际会打开添加表单）');
}

function onEdit(row) {
  window.alert(`编辑${row.username} (ID: ${row.id})\n\n（演示版本，实际会打开编辑表单）`);
}

function onToggle(row) {
  const action = row.status === 'active' ? '禁用' : '启用';
  const ok = window.confirm(`确定要${action}“${row.username}”吗？`);
  if (!ok) return;

  loading.value = true;
  error.value = '';
  const fn = row.status === 'active' ? usersApi.deactivateUser : usersApi.activateUser;
  fn(row.id)
    .then(() => {
      window.alert(`${action}成功`);
      return load();
    })
    .catch((e) => {
      console.error(e);
      window.alert(`${action}失败，请稍后重试`);
    })
    .finally(() => {
      loading.value = false;
    });
}

function onDelete(row) {
  const ok = window.confirm(`确定要删除“${row.username}”吗？\n\n此操作不可恢复！`);
  if (!ok) return;
  loading.value = true;
  error.value = '';
  usersApi
    .deleteUser(row.id)
    .then(() => {
      window.alert('删除成功');
      return load();
    })
    .catch((e) => {
      console.error(e);
      window.alert('删除失败，请稍后重试');
    })
    .finally(() => {
      loading.value = false;
    });
}

onMounted(load);
</script>

<template>
  <AdminLayout active-page="admin-user-list" title="用户列表">
    <div class="admin-toolbar">
      <div class="admin-search-row">
        <input v-model="keyword" type="text" class="admin-search-input" placeholder="搜索用户名/手机号" />
        <button class="admin-search-btn" type="button" @click="onSearch">搜索</button>
      </div>
      <div class="admin-filter-row">
        <select v-model="statusFilter" class="admin-filter-select">
          <option value="all">全部状态</option>
          <option value="active">正常</option>
          <option value="disabled">禁用</option>
        </select>
        <select v-model="roleFilter" class="admin-filter-select">
          <option value="all">全部角色</option>
          <option value="admin">管理员</option>
          <option value="operator">操作员</option>
          <option value="user">普通用户</option>
        </select>
        <button class="admin-add-btn" type="button" @click="onAdd">+ 添加用户</button>
      </div>
    </div>

    <div v-if="loading" style="color: var(--text-secondary); padding: 6px 0">正在加载...</div>
    <div v-else-if="error" style="color: var(--warning-color); padding: 6px 0">{{ error }}</div>

    <div class="admin-table-container">
      <table class="admin-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>用户名</th>
            <th>手机号</th>
            <th>角色</th>
            <th>状态</th>
            <th>注册时间</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="u in filteredRows" :key="u.id">
            <td>{{ u.id }}</td>
            <td>{{ u.username }}</td>
            <td>{{ u.phone }}</td>
            <td><span class="category-tag">{{ u.roleText }}</span></td>
            <td><span class="status-tag" :class="u.status">{{ u.statusText }}</span></td>
            <td>{{ u.createTime }}</td>
            <td>
              <div class="admin-actions">
                <button class="admin-action-btn edit" type="button" @click="onEdit(u)">编辑</button>
                <button class="admin-action-btn toggle" type="button" @click="onToggle(u)">
                  {{ u.status === 'active' ? '禁用' : '启用' }}
                </button>
                <button class="admin-action-btn delete" type="button" @click="onDelete(u)">删除</button>
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
