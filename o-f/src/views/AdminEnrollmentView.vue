<script setup>
import { computed, ref, onMounted } from 'vue';

import AdminLayout from '../components/admin/AdminLayout.vue';
import * as enrollmentApi from '../api/enrollment';

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
  activityId: null,
  elderlyId: null,
  status: 'pending',
  enrollTime: '',
  notes: '',
});

const filteredRows = computed(() => {
  const list = Array.isArray(rows.value) ? rows.value : [];
  const key = keyword.value.trim();
  return list.filter((e) => {
    if (statusFilter.value !== 'all' && e.status !== statusFilter.value) return false;
    if (!key) return true;
    return String(e.activityId).includes(key) || String(e.elderlyId).includes(key);
  });
});

async function load() {
  loading.value = true;
  error.value = '';
  try {
    const data = await enrollmentApi.listAll();
    rows.value = Array.isArray(data) ? data : [];
  } catch (e) {
    console.error(e);
    error.value = '加载数据失败';
    rows.value = [];
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
    activityId: null,
    elderlyId: null,
    status: 'pending',
    enrollTime: new Date().toISOString().slice(0, 16),
    notes: '',
  };
  dialogVisible.value = true;
}

function onConfirm(row) {
  const ok = window.confirm(`确定要确认老人ID ${row.elderlyId} 参加活动ID ${row.activityId} 的报名吗？`);
  if (!ok) return;
  
  loading.value = true;
  error.value = '';
  enrollmentApi.confirm(row.id)
    .then(() => {
      window.alert('已确认报名');
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

function onCheckIn(row) {
  const ok = window.confirm(`确定要标记老人ID ${row.elderlyId} 为已签到吗？`);
  if (!ok) return;
  
  loading.value = true;
  error.value = '';
  enrollmentApi.checkIn(row.id)
    .then(() => {
      window.alert('签到成功');
      return load();
    })
    .catch(e => {
      console.error(e);
      window.alert('签到失败，请稍后重试');
    })
    .finally(() => {
      loading.value = false;
    });
}

function onCancel(row) {
  const ok = window.confirm(`确定要取消老人ID ${row.elderlyId} 的报名吗？`);
  if (!ok) return;
  loading.value = true;
  error.value = '';
  enrollmentApi.cancel(row.id)
    .then(() => {
      window.alert('已取消报名');
      return load();
    })
    .catch(e => {
      console.error(e);
      window.alert('取消失败，请稍后重试');
    })
    .finally(() => {
      loading.value = false;
    });
}

function onEdit(row) {
  dialogMode.value = 'edit';
  loading.value = true;
  enrollmentApi.getById(row.id)
    .then(data => {
      dialogForm.value = {
        id: data.id,
        activityId: data.activityId,
        elderlyId: data.elderlyId,
        status: data.status || 'pending',
        enrollTime: data.enrollTime || '',
        notes: data.notes || '',
      };
      dialogVisible.value = true;
    })
    .catch(e => {
      console.error(e);
      window.alert('获取报名信息失败');
    })
    .finally(() => {
      loading.value = false;
    });
}

function onDelete(row) {
  const ok = window.confirm(`确定要删除老人ID ${row.elderlyId} 的报名记录吗？\n\n此操作不可恢复！`);
  if (!ok) return;
  
  loading.value = true;
  error.value = '';
  enrollmentApi.deleteEnrollment(row.id)
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
  if (!form.activityId || !form.elderlyId) {
    window.alert('活动ID和老人ID为必填项');
    return;
  }

  loading.value = true;
  error.value = '';
  try {
    if (dialogMode.value === 'add') {
      await enrollmentApi.create(form);
      window.alert('添加成功');
    } else {
      await enrollmentApi.update(form.id, form);
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
  <AdminLayout active-page="admin-enrollment" title="报名管理">
    <div class="admin-toolbar">
      <div class="admin-search-row">
        <input v-model="keyword" type="text" class="admin-search-input" placeholder="搜索活动ID/老人ID" />
        <button class="admin-search-btn" type="button" @click="onSearch">搜索</button>
      </div>
      <div class="admin-filter-row">
        <select v-model="statusFilter" class="admin-filter-select">
          <option value="all">全部状态</option>
          <option value="pending">待确认</option>
          <option value="confirmed">已确认</option>
          <option value="attended">已签到</option>
          <option value="absent">未参加</option>
          <option value="cancelled">已取消</option>
        </select>
        <button class="admin-add-btn" type="button" @click="onAdd">+ 添加报名</button>
      </div>
    </div>

    <div v-if="loading" style="color: var(--text-secondary); padding: 6px 0">正在加载...</div>
    <div v-else-if="error" style="color: var(--warning-color); padding: 6px 0">{{ error }}</div>

    <div class="admin-table-container">
      <table class="admin-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>活动ID</th>
            <th>老人ID</th>
            <th>报名时间</th>
            <th>状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="e in filteredRows" :key="e.id">
            <td>{{ e.id }}</td>
            <td>{{ e.activityId }}</td>
            <td>{{ e.elderlyId }}</td>
            <td>{{ e.enrollTime }}</td>
            <td><span class="status-tag" :class="e.status">{{ e.status }}</span></td>
            <td>
              <div class="admin-actions">
                <button v-if="e.status === 'pending'" class="admin-action-btn view" type="button" @click="onConfirm(e)">确认</button>
                <button v-if="e.status === 'confirmed'" class="admin-action-btn view" type="button" @click="onCheckIn(e)">签到</button>
                <button v-if="e.status !== 'attended' && e.status !== 'absent' && e.status !== 'cancelled'" class="admin-action-btn delete" type="button" @click="onCancel(e)">取消</button>
                <button class="admin-action-btn edit" type="button" @click="onEdit(e)">编辑</button>
                <button class="admin-action-btn delete" type="button" @click="onDelete(e)">删除</button>
              </div>
            </td>
          </tr>
          <tr v-if="!filteredRows.length">
            <td colspan="6" style="text-align: center; color: var(--text-secondary); padding: 16px 0">暂无数据</td>
          </tr>
        </tbody>
      </table>
    </div>

    <div class="admin-pagination">
      <span>共 {{ filteredRows.length }} 条记录</span>
      <span>第 1/1 页</span>
    </div>

    <!-- 新建/编辑报名对话框 -->
    <div v-if="dialogVisible" class="admin-dialog-overlay" @click="closeDialog">
      <div class="admin-dialog" @click.stop>
        <div class="admin-dialog-header">
          <h3>{{ dialogMode === 'add' ? '新建报名' : '编辑报名' }}</h3>
          <button class="admin-dialog-close" @click="closeDialog">×</button>
        </div>
        <div class="admin-dialog-body">
          <div class="admin-form-group">
            <label>活动ID <span style="color: red">*</span></label>
            <input v-model.number="dialogForm.activityId" type="number" placeholder="请输入活动ID" />
          </div>
          <div class="admin-form-group">
            <label>老人ID <span style="color: red">*</span></label>
            <input v-model.number="dialogForm.elderlyId" type="number" placeholder="请输入老人ID" />
          </div>
          <div class="admin-form-group">
            <label>报名状态</label>
            <select v-model="dialogForm.status">
              <option value="pending">待确认</option>
              <option value="confirmed">已确认</option>
              <option value="attended">已签到</option>
              <option value="absent">未参加</option>
              <option value="cancelled">已取消</option>
            </select>
          </div>
          <div class="admin-form-group">
            <label>报名时间</label>
            <input v-model="dialogForm.enrollTime" type="datetime-local" />
          </div>
          <div class="admin-form-group">
            <label>备注</label>
            <textarea v-model="dialogForm.notes" rows="3" placeholder="请输入备注说明"></textarea>
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
