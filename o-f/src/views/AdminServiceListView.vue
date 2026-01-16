<script setup>
import { computed, onMounted, ref } from 'vue';

import AdminLayout from '../components/admin/AdminLayout.vue';
import * as workerApi from '../api/worker';
import { adminServicesData, serviceCategories } from '../data/admin';

const loading = ref(false);
const error = ref('');
const keyword = ref('');
const categoryFilter = ref('all');
const statusFilter = ref('all');

const rows = ref([]);

// 新增/编辑对话框状态
const dialogVisible = ref(false);
const dialogMode = ref('add'); // 'add' | 'edit'
const dialogForm = ref({
  id: null,
  name: '',
  email: '',
  phone: '',
  position: '',
  department: '',
  specialization: '',
  salary: null,
  isAvailable: true,
  isActive: true,
});

function formatDate(value) {
  if (!value) return '';
  const s = String(value);
  return s.length >= 10 ? s.slice(0, 10) : s;
}

function mapWorker(w) {
  return {
    id: w?.id ?? '-',
    name: w?.name || '-',
    category: w?.department || 'other',
    categoryText: w?.department || '其他',
    price: w?.salary != null ? `¥${w.salary}` : '-',
    status: w?.isAvailable === false ? 'offline' : 'online',
    statusText: w?.isAvailable === false ? '下架' : '上架',
    description: w?.specialization || w?.position || '-',
    createTime: formatDate(w?.createdAt || w?.hireDate),
  };
}

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

async function load() {
  loading.value = true;
  error.value = '';
  try {
    const data = await workerApi.listAll();
    if (Array.isArray(data) && data.length) {
      rows.value = data.map(mapWorker);
      return;
    }
    rows.value = adminServicesData.slice();
  } catch (e) {
    error.value = '后端接口不可用，已切换为演示数据';
    rows.value = adminServicesData.slice();
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
    name: '',
    email: '',
    phone: '',
    position: '',
    department: '',
    specialization: '',
    salary: null,
    isAvailable: true,
    isActive: true,
  };
  dialogVisible.value = true;
}

function onEdit(row) {
  dialogMode.value = 'edit';
  loading.value = true;
  workerApi.getById(row.id)
    .then(data => {
      dialogForm.value = {
        id: data.id,
        name: data.name || '',
        email: data.email || '',
        phone: data.phone || '',
        position: data.position || '',
        department: data.department || '',
        specialization: data.specialization || '',
        salary: data.salary || null,
        isAvailable: data.isAvailable !== false,
        isActive: data.isActive !== false,
      };
      dialogVisible.value = true;
    })
    .catch(e => {
      console.error(e);
      window.alert('获取服务人员信息失败');
    })
    .finally(() => {
      loading.value = false;
    });
}

function onToggle(row) {
  const action = row.status === 'online' ? '下架' : '上架';
  const ok = window.confirm(`确定要${action}服务“${row.name}”吗？`);
  if (!ok) return;
  
  loading.value = true;
  error.value = '';
  const apiCall = row.status === 'online' 
    ? workerApi.setAvailability(row.id, false)
    : workerApi.setAvailability(row.id, true);
  
  apiCall
    .then(() => {
      window.alert(`已${action}服务：${row.name}`);
      return load();
    })
    .catch(e => {
      console.error(e);
      window.alert(`${action}失败，请稍后重试`);
    })
    .finally(() => {
      loading.value = false;
    });
}

function onDelete(row) {
  const ok = window.confirm(`确定要删除"${row.name}"吗？\n\n此操作不可恢复！`);
  if (!ok) return;
  
  loading.value = true;
  error.value = '';
  workerApi.deleteWorker(row.id)
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
  if (!form.name || !form.email || !form.phone) {
    window.alert('姓名、邮箱和电话为必填项');
    return;
  }

  loading.value = true;
  error.value = '';
  try {
    if (dialogMode.value === 'add') {
      await workerApi.createWorker(form);
      window.alert('添加成功');
    } else {
      await workerApi.updateWorker(form.id, form);
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

    <div v-if="loading" style="color: var(--text-secondary); padding: 6px 0">正在加载...</div>
    <div v-else-if="error" style="color: var(--warning-color); padding: 6px 0">{{ error }}</div>

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

    <!-- 新增/编辑对话框 -->
    <div v-if="dialogVisible" class="admin-dialog-overlay" @click.self="closeDialog">
      <div class="admin-dialog">
        <div class="admin-dialog-header">
          <h3>{{ dialogMode === 'add' ? '添加服务人员' : '编辑服务人员' }}</h3>
          <button class="admin-dialog-close" @click="closeDialog">×</button>
        </div>
        <div class="admin-dialog-body">
          <div class="admin-form-group">
            <label>姓名 <span style="color: red">*</span></label>
            <input v-model="dialogForm.name" type="text" placeholder="请输入姓名" />
          </div>
          <div class="admin-form-group">
            <label>邮箱 <span style="color: red">*</span></label>
            <input v-model="dialogForm.email" type="email" placeholder="请输入邮箱" />
          </div>
          <div class="admin-form-group">
            <label>电话 <span style="color: red">*</span></label>
            <input v-model="dialogForm.phone" type="tel" placeholder="请输入电话号码" />
          </div>
          <div class="admin-form-group">
            <label>职位</label>
            <input v-model="dialogForm.position" type="text" placeholder="请输入职位，如：护理员、医生" />
          </div>
          <div class="admin-form-group">
            <label>部门</label>
            <input v-model="dialogForm.department" type="text" placeholder="请输入部门" />
          </div>
          <div class="admin-form-group">
            <label>专长</label>
            <input v-model="dialogForm.specialization" type="text" placeholder="请输入专长或服务范围" />
          </div>
          <div class="admin-form-group">
            <label>薪资</label>
            <input v-model.number="dialogForm.salary" type="number" step="0.01" placeholder="请输入薪资" />
          </div>
          <div class="admin-form-group">
            <label>
              <input v-model="dialogForm.isAvailable" type="checkbox" />
              可接单状态
            </label>
          </div>
          <div class="admin-form-group">
            <label>
              <input v-model="dialogForm.isActive" type="checkbox" />
              启用状态
            </label>
          </div>
        </div>
        <div class="admin-dialog-footer">
          <button class="admin-dialog-btn cancel" @click="closeDialog">取消</button>
          <button class="admin-dialog-btn confirm" @click="saveDialog">{{ dialogMode === 'add' ? '确认添加' : '确认修改' }}</button>
        </div>
      </div>
    </div>
  </AdminLayout>
</template>
