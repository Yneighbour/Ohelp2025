<script setup>
import { computed, onMounted, ref } from 'vue';

import AdminLayout from '../components/admin/AdminLayout.vue';
import * as elderApi from '../api/elder';
import * as relativeApi from '../api/relative';
import { adminFamilyBindingsData as demoBindings } from '../data/admin';

const loading = ref(false);
const error = ref('');
const keyword = ref('');
const statusFilter = ref('all');

const rows = ref([]);

function formatDate(value) {
  if (!value) return '';
  const s = String(value);
  return s.length >= 10 ? s.slice(0, 10) : s;
}

function mapBinding(rel, elderNameMap) {
  const elderName = elderNameMap.get(rel?.elderlyId) || (rel?.elderlyId != null ? `老人#${rel.elderlyId}` : '-');
  return {
    id: rel?.id ?? '-',
    elderName,
    familyName: rel?.name || '-',
    relation: rel?.relationship || '-',
    phone: rel?.phone || '-',
    bindTime: formatDate(rel?.createdAt),
    status: rel?.isActive === false ? 'pending' : 'bindng',
    statusText: rel?.isActive === false ? '待确认' : '已绑定',
  };
}

const filteredRows = computed(() => {
  const list = Array.isArray(rows.value) ? rows.value : [];
  const key = keyword.value.trim();
  return list.filter((b) => {
    if (statusFilter.value !== 'all' && b.status !== statusFilter.value) return false;
    if (!key) return true;
    return String(b.elderName).includes(key) || String(b.familyName).includes(key);
  });
});

async function load() {
  loading.value = true;
  error.value = '';
  try {
    const [elders, relatives] = await Promise.all([
      elderApi.listAll().catch(() => []),
      relativeApi.listAll().catch(() => []),
    ]);

    if (Array.isArray(relatives) && relatives.length) {
      const map = new Map();
      if (Array.isArray(elders)) {
        elders.forEach((e) => map.set(e?.id, e?.name));
      }
      rows.value = relatives.map((r) => mapBinding(r, map));
      return;
    }

    rows.value = demoBindings.slice();
  } catch (e) {
    error.value = '后端接口不可用，已切换为演示数据';
    rows.value = demoBindings.slice();
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
  window.alert('添加家属绑定\n\n（演示版本，实际会打开添加表单）');
}

function onInvite(row) {
  window.alert(`向${row.elderName}的家属发送绑定邀请\n\n（演示版本，实际会发送短信邀请）`);
}

function onEdit(row) {
  window.alert(`编辑${row.elderName}与${row.familyName}的绑定 (ID: ${row.id})\n\n（演示版本，实际会打开编辑表单）`);
}

function onUnbind(row) {
  const ok = window.confirm(`确定要解除${row.elderName}与${row.familyName}的绑定关系吗？`);
  if (!ok) return;
  window.alert('已解除绑定关系\n\n（演示版本，实际会调用后端API解除绑定）');
}

onMounted(load);
</script>

<template>
  <AdminLayout active-page="admin-family-bindng" title="家属绑定">
    <div class="admin-toolbar">
      <div class="admin-search-row">
        <input v-model="keyword" type="text" class="admin-search-input" placeholder="搜索老人/家属姓名" />
        <button class="admin-search-btn" type="button" @click="onSearch">搜索</button>
      </div>
      <div class="admin-filter-row">
        <select v-model="statusFilter" class="admin-filter-select">
          <option value="all">全部状态</option>
          <option value="bindng">已绑定</option>
          <option value="pending">待确认</option>
        </select>
        <button class="admin-add-btn" type="button" @click="onAdd">+ 添加绑定</button>
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
            <th>家属姓名</th>
            <th>关系</th>
            <th>家属电话</th>
            <th>绑定时间</th>
            <th>状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="b in filteredRows" :key="b.id">
            <td>{{ b.id }}</td>
            <td>{{ b.elderName }}</td>
            <td>{{ b.familyName }}</td>
            <td>{{ b.relation }}</td>
            <td>{{ b.phone }}</td>
            <td>{{ b.bindTime }}</td>
            <td><span class="status-tag" :class="b.status">{{ b.statusText }}</span></td>
            <td>
              <div class="admin-actions">
                <button v-if="b.status === 'pending'" class="admin-action-btn view" type="button" @click="onInvite(b)">
                  发送邀请
                </button>
                <button class="admin-action-btn edit" type="button" @click="onEdit(b)">编辑</button>
                <button class="admin-action-btn delete" type="button" @click="onUnbind(b)">解除绑定</button>
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
