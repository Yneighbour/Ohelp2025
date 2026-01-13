<script setup>
import { computed, onMounted, ref } from 'vue';

import AdminLayout from '../components/admin/AdminLayout.vue';
import * as serviceOrderApi from '../api/serviceorder';
import { adminServiceOrdersData as demoOrders } from '../data/admin';

const loading = ref(false);
const error = ref('');
const keyword = ref('');
const statusFilter = ref('all');

const rows = ref([]);

function formatDateTime(value) {
  if (!value) return '';
  const s = String(value);
  return s.length >= 19 ? s.slice(0, 19).replace('T', ' ') : s;
}

function mapStatus(status) {
  if (status === 'completed') return { status: 'completed', text: '已完成' };
  if (status === 'cancelled') return { status: 'cancelled', text: '已取消' };
  if (status === 'confirmed') return { status: 'confirmed', text: '已确认' };
  return { status: 'pending', text: '待服务' };
}

function mapOrder(o) {
  const st = mapStatus(o?.status);
  const orderNo = o?.orderNo || (o?.id != null ? `SV${String(o.id).padStart(12, '0')}` : '-');
  return {
    id: o?.id ?? '-',
    orderNo,
    elderName: o?.elderName || (o?.elderlyId != null ? `老人#${o.elderlyId}` : '-'),
    serviceName: o?.serviceName || o?.serviceType || '-',
    orderTime: formatDateTime(o?.createdAt || o?.orderTime),
    serviceTime: formatDateTime(o?.startDate || o?.serviceTime),
    status: st.status,
    statusText: o?.statusText || st.text,
    operator: o?.operator || (o?.serviceProviderId != null ? `服务人员#${o.serviceProviderId}` : '--'),
  };
}

const filteredRows = computed(() => {
  const list = Array.isArray(rows.value) ? rows.value : [];
  const key = keyword.value.trim();
  return list.filter((o) => {
    if (statusFilter.value !== 'all' && o.status !== statusFilter.value) return false;
    if (!key) return true;
    return String(o.orderNo).includes(key) || String(o.elderName).includes(key);
  });
});

async function load() {
  loading.value = true;
  error.value = '';
  try {
    const data = await serviceOrderApi.listAll();
    if (Array.isArray(data) && data.length) {
      rows.value = data.map(mapOrder);
      return;
    }
    rows.value = demoOrders.slice();
  } catch (e) {
    error.value = '后端接口不可用，已切换为演示数据';
    rows.value = demoOrders.slice();
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
  window.alert('新建预约\n\n（演示版本，实际会打开新建预约表单）');
}

function onConfirm(row) {
  const ok = window.confirm(`确定要确认预约单“${row.orderNo}”吗？`);
  if (!ok) return;
  window.alert(`已确认预约单：${row.orderNo}\n\n（演示版本，实际会调用后端API更新状态）`);
}

function onCancel(row) {
  const ok = window.confirm(`确定要取消预约单“${row.orderNo}”吗？`);
  if (!ok) return;
  window.alert(`已取消预约单：${row.orderNo}\n\n（演示版本，实际会调用后端API更新状态）`);
}

function onComplete(row) {
  const ok = window.confirm(`确定要将预约单“${row.orderNo}”标记为已完成吗？`);
  if (!ok) return;
  window.alert(`预约单${row.orderNo}已完成\n\n（演示版本，实际会调用后端API更新状态）`);
}

function onDetail(row) {
  window.alert(`查看预约单${row.orderNo}详情 (ID: ${row.id})\n\n（演示版本，实际会打开详情页面）`);
}

onMounted(load);
</script>

<template>
  <AdminLayout active-page="admin-service-order" title="服务预约">
    <div class="admin-toolbar">
      <div class="admin-search-row">
        <input v-model="keyword" type="text" class="admin-search-input" placeholder="搜索预约单号/老人姓名" />
        <button class="admin-search-btn" type="button" @click="onSearch">搜索</button>
      </div>
      <div class="admin-filter-row">
        <select v-model="statusFilter" class="admin-filter-select">
          <option value="all">全部状态</option>
          <option value="pending">待服务</option>
          <option value="confirmed">已确认</option>
          <option value="completed">已完成</option>
          <option value="cancelled">已取消</option>
        </select>
        <button class="admin-add-btn" type="button" @click="onAdd">+ 新建预约</button>
      </div>
    </div>

    <div v-if="loading" style="color: var(--text-secondary); padding: 6px 0">正在加载...</div>
    <div v-else-if="error" style="color: var(--warning-color); padding: 6px 0">{{ error }}</div>

    <div class="admin-table-container">
      <table class="admin-table">
        <thead>
          <tr>
            <th>预约单号</th>
            <th>老人姓名</th>
            <th>服务项目</th>
            <th>预约时间</th>
            <th>服务时间</th>
            <th>状态</th>
            <th>服务人员</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="o in filteredRows" :key="o.orderNo">
            <td>{{ o.orderNo }}</td>
            <td>{{ o.elderName }}</td>
            <td>{{ o.serviceName }}</td>
            <td>{{ o.orderTime }}</td>
            <td>{{ o.serviceTime }}</td>
            <td><span class="status-tag" :class="o.status">{{ o.statusText }}</span></td>
            <td>{{ o.operator }}</td>
            <td>
              <div class="admin-actions">
                <template v-if="o.status === 'pending'">
                  <button class="admin-action-btn view" type="button" @click="onConfirm(o)">确认</button>
                  <button class="admin-action-btn delete" type="button" @click="onCancel(o)">取消</button>
                </template>
                <template v-else-if="o.status === 'confirmed'">
                  <button class="admin-action-btn view" type="button" @click="onComplete(o)">完成</button>
                  <button class="admin-action-btn delete" type="button" @click="onCancel(o)">取消</button>
                </template>
                <button class="admin-action-btn edit" type="button" @click="onDetail(o)">详情</button>
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
