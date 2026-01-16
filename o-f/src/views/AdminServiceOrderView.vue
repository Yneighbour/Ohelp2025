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

// 新增/编辑对话框状态
const dialogVisible = ref(false);
const dialogMode = ref('add'); // 'add' | 'edit'
const dialogForm = ref({
  id: null,
  elderlyId: null,
  serviceType: '',
  serviceProviderId: null,
  startDate: '',
  endDate: '',
  frequency: '',
  price: null,
  description: '',
  status: 'pending',
  isActive: true,
});

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
  // 搜索功能已通过computed filteredRows实现，这里保留空函数
}

function onAdd() {
  dialogMode.value = 'add';
  dialogForm.value = {
    id: null,
    elderlyId: null,
    serviceType: '',
    serviceProviderId: null,
    startDate: new Date().toISOString().slice(0, 16),
    endDate: '',
    frequency: '',
    price: null,
    description: '',
    status: 'pending',
    isActive: true,
  };
  dialogVisible.value = true;
}

function onConfirm(row) {
  const ok = window.confirm(`确定要确认预约单"${row.orderNo}"吗？`);
  if (!ok) return;
  
  loading.value = true;
  error.value = '';
  serviceOrderApi.confirm(row.id)
    .then(() => {
      window.alert(`已确认预约单：${row.orderNo}`);
      return load();
    })
    .catch(e => {
      console.error(e);
      window.alert('确认失败，请稍后重试');
    })
    .finally(() => {
      loading.value = false;
    });
}

function onCancel(row) {
  const ok = window.confirm(`确定要取消预约单“${row.orderNo}”吗？`);
  if (!ok) return;
  loading.value = true;
  error.value = '';
  serviceOrderApi
    .cancel(row.id)
    .then(() => {
      window.alert(`已取消预约单：${row.orderNo}`);
      return load();
    })
    .catch((e) => {
      console.error(e);
      window.alert('取消失败，请稍后重试');
    })
    .finally(() => {
      loading.value = false;
    });
}

function onComplete(row) {
  const ok = window.confirm(`确定要将预约单“${row.orderNo}”标记为已完成吗？`);
  if (!ok) return;
  loading.value = true;
  error.value = '';
  serviceOrderApi
    .complete(row.id)
    .then(() => {
      window.alert(`预约单${row.orderNo}已完成`);
      return load();
    })
    .catch((e) => {
      console.error(e);
      window.alert('操作失败，请稍后重试');
    })
    .finally(() => {
      loading.value = false;
    });
}

function onDetail(row) {
  window.alert(`查看预约单${row.orderNo}详情 (ID: ${row.id})\n\n（详情页面待开发）`);
}

function onEdit(row) {
  dialogMode.value = 'edit';
  loading.value = true;
  serviceOrderApi.getById(row.id)
    .then(data => {
      dialogForm.value = {
        id: data.id,
        elderlyId: data.elderlyId || null,
        serviceType: data.serviceType || '',
        serviceProviderId: data.serviceProviderId || null,
        startDate: data.startDate || '',
        endDate: data.endDate || '',
        frequency: data.frequency || '',
        price: data.price || null,
        description: data.description || '',
        status: data.status || 'pending',
        isActive: data.isActive !== false,
      };
      dialogVisible.value = true;
    })
    .catch(e => {
      console.error(e);
      window.alert('获取预约单信息失败');
    })
    .finally(() => {
      loading.value = false;
    });
}

function onDelete(row) {
  const ok = window.confirm(`确定要删除预约单"${row.orderNo}"吗？\n\n此操作不可恢复！`);
  if (!ok) return;
  
  loading.value = true;
  error.value = '';
  serviceOrderApi.deleteServiceOrder(row.id)
    .then(() => {
      window.alert('删除成功');
      return load();
    })
    .catch(e => {
      console.error(e);
      window.alert('删除失败，请稍后重试');
    })
    .finally(() => {
      loading.value = false;
    });
}

function closeDialog() {
  dialogVisible.value = false;
}

async function saveDialog() {
  const form = dialogForm.value;
  if (!form.elderlyId || !form.serviceType) {
    window.alert('老人ID和服务类型为必填项');
    return;
  }

  loading.value = true;
  error.value = '';
  try {
    if (dialogMode.value === 'add') {
      await serviceOrderApi.createServiceOrder(form);
      window.alert('添加成功');
    } else {
      await serviceOrderApi.updateServiceOrder(form.id, form);
      window.alert('更新成功');
    }
    dialogVisible.value = false;
    await load();
  } catch (e) {
    console.error(e);
    window.alert(`保存失败: ${e.message || '请稍后重试'}`);
  } finally {
    loading.value = false;
  }
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
                  <button class="admin-action-btn edit" type="button" @click="onEdit(o)">编辑</button>
                  <button class="admin-action-btn delete" type="button" @click="onCancel(o)">取消</button>
                </template>
                <template v-else-if="o.status === 'confirmed'">
                  <button class="admin-action-btn view" type="button" @click="onComplete(o)">完成</button>
                  <button class="admin-action-btn edit" type="button" @click="onEdit(o)">编辑</button>
                  <button class="admin-action-btn delete" type="button" @click="onCancel(o)">取消</button>
                </template>
                <button class="admin-action-btn view" type="button" @click="onDetail(o)">详情</button>
                <button class="admin-action-btn delete" type="button" @click="onDelete(o)">删除</button>
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

    <!-- 新建/编辑预约对话框 -->
    <div v-if="dialogVisible" class="admin-dialog-overlay" @click="closeDialog">
      <div class="admin-dialog" @click.stop>
        <div class="admin-dialog-header">
          <h3>{{ dialogMode === 'add' ? '新建预约' : '编辑预约' }}</h3>
          <button class="admin-dialog-close" @click="closeDialog">×</button>
        </div>
        <div class="admin-dialog-body">
          <div class="admin-form-group">
            <label>老人ID <span style="color: red">*</span></label>
            <input v-model.number="dialogForm.elderlyId" type="number" placeholder="请输入老人ID" />
          </div>
          <div class="admin-form-group">
            <label>服务类型 <span style="color: red">*</span></label>
            <select v-model="dialogForm.serviceType">
              <option value="">请选择</option>
              <option value="medical">医疗护理</option>
              <option value="daily">日常照料</option>
              <option value="rehabilitation">康复训练</option>
              <option value="psychological">心理疏导</option>
              <option value="nutrition">营养配餐</option>
              <option value="other">其他</option>
            </select>
          </div>
          <div class="admin-form-group">
            <label>服务人员ID</label>
            <input v-model.number="dialogForm.serviceProviderId" type="number" placeholder="请输入服务人员ID（可选）" />
          </div>
          <div class="admin-form-group">
            <label>开始时间</label>
            <input v-model="dialogForm.startDate" type="datetime-local" />
          </div>
          <div class="admin-form-group">
            <label>结束时间</label>
            <input v-model="dialogForm.endDate" type="datetime-local" />
          </div>
          <div class="admin-form-group">
            <label>服务频次</label>
            <select v-model="dialogForm.frequency">
              <option value="">请选择</option>
              <option value="once">一次性</option>
              <option value="daily">每天</option>
              <option value="weekly">每周</option>
              <option value="monthly">每月</option>
            </select>
          </div>
          <div class="admin-form-group">
            <label>价格（元）</label>
            <input v-model.number="dialogForm.price" type="number" step="0.01" placeholder="请输入价格" />
          </div>
          <div class="admin-form-group">
            <label>备注说明</label>
            <textarea v-model="dialogForm.description" rows="3" placeholder="请输入备注说明"></textarea>
          </div>
          <div class="admin-form-group">
            <label>
              <input v-model="dialogForm.isActive" type="checkbox" />
              启用
            </label>
          </div>
        </div>
        <div class="admin-dialog-footer">
          <button class="admin-btn-secondary" @click="closeDialog">取消</button>
          <button class="admin-btn-primary" @click="saveDialog">{{ dialogMode === 'add' ? '确认添加' : '确认修改' }}</button>
        </div>
      </div>
    </div>
  </AdminLayout>
</template>
