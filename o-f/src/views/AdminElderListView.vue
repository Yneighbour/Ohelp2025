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

// 新增/编辑对话框状态
const dialogVisible = ref(false);
const dialogMode = ref('add'); // 'add' | 'edit'
const dialogForm = ref({
  id: null,
  name: '',
  age: null,
  gender: '男',
  phoneNumber: '',
  contactPerson: '',
  roomNumber: '',
  medicalHistory: '',
  healthStatus: 'normal',
});

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
  dialogMode.value = 'add';
  dialogForm.value = {
    id: null,
    name: '',
    age: null,
    gender: '男',
    phoneNumber: '',
    contactPerson: '',
    roomNumber: '',
    medicalHistory: '',
    healthStatus: 'normal',
  };
  dialogVisible.value = true;
}

function onViewHealth(row) {
  window.alert(`查看${row.name}的健康档案\n\n（演示版本，实际会跳转到健康档案页面）`);
}

function onEdit(row) {
  dialogMode.value = 'edit';
  // 从后端重新获取完整数据
  loading.value = true;
  elderApi.getById(row.id)
    .then(data => {
      dialogForm.value = {
        id: data.id,
        name: data.name || '',
        age: data.age || null,
        gender: data.gender || '男',
        phoneNumber: data.phoneNumber || '',
        contactPerson: data.contactPerson || '',
        roomNumber: data.roomNumber || '',
        medicalHistory: data.medicalHistory || '',
        healthStatus: data.healthStatus || 'normal',
      };
      dialogVisible.value = true;
    })
    .catch(e => {
      console.error(e);
      window.alert('获取老人信息失败');
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
  if (!form.name || !form.phoneNumber) {
    window.alert('姓名和联系电话不能为空');
    return;
  }

  loading.value = true;
  error.value = '';
  try {
    if (dialogMode.value === 'add') {
      await elderApi.createElderly(form);
      window.alert('添加成功');
    } else {
      await elderApi.updateElderly(form.id, form);
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

function onDelete(row) {
  const ok = window.confirm(`确定要删除"${row.name}"吗?\n\n此操作不可恢复！`);
  if (!ok) return;
  loading.value = true;
  error.value = '';
  elderApi.deleteElderly(row.id)
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

    <!-- 新增/编辑对话框 -->
    <div v-if="dialogVisible" class="admin-dialog-overlay" @click.self="closeDialog">
      <div class="admin-dialog">
        <div class="admin-dialog-header">
          <h3>{{ dialogMode === 'add' ? '添加老人档案' : '编辑老人档案' }}</h3>
          <button class="admin-dialog-close" @click="closeDialog">×</button>
        </div>
        <div class="admin-dialog-body">
          <div class="admin-form-group">
            <label>姓名 <span style="color: red">*</span></label>
            <input v-model="dialogForm.name" type="text" placeholder="请输入姓名" />
          </div>
          <div class="admin-form-group">
            <label>年龄</label>
            <input v-model.number="dialogForm.age" type="number" min="0" max="150" placeholder="请输入年龄" />
          </div>
          <div class="admin-form-group">
            <label>性别</label>
            <select v-model="dialogForm.gender">
              <option value="男">男</option>
              <option value="女">女</option>
            </select>
          </div>
          <div class="admin-form-group">
            <label>联系电话 <span style="color: red">*</span></label>
            <input v-model="dialogForm.phoneNumber" type="text" placeholder="请输入联系电话" />
          </div>
          <div class="admin-form-group">
            <label>紧急联系人</label>
            <input v-model="dialogForm.contactPerson" type="text" placeholder="请输入紧急联系人" />
          </div>
          <div class="admin-form-group">
            <label>房间号</label>
            <input v-model="dialogForm.roomNumber" type="text" placeholder="请输入房间号" />
          </div>
          <div class="admin-form-group">
            <label>病史</label>
            <textarea v-model="dialogForm.medicalHistory" rows="3" placeholder="请输入病史信息"></textarea>
          </div>
          <div class="admin-form-group">
            <label>健康状态</label>
            <select v-model="dialogForm.healthStatus">
              <option value="normal">健康</option>
              <option value="warning">亚健康</option>
              <option value="danger">需关注</option>
            </select>
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
