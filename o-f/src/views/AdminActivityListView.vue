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

// 新增/编辑对话框状态
const dialogVisible = ref(false);
const dialogMode = ref('add'); // 'add' | 'edit'
const dialogForm = ref({
  id: null,
  name: '',
  category: 'health',
  location: '',
  startTime: '',
  endTime: '',
  capacity: 50,
  description: '',
  requirements: '',
  status: 'pending',
});

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
  dialogMode.value = 'add';
  dialogForm.value = {
    id: null,
    name: '',
    category: 'health',
    location: '',
    startTime: '',
    endTime: '',
    capacity: 50,
    description: '',
    requirements: '',
    status: 'pending',
  };
  dialogVisible.value = true;
}

function onViewEnrollments(row) {
  window.alert(`查看${row.name}的报名列表\n\n（演示版本，实际会跳转到报名管理页面）`);
}

function onEdit(row) {
  dialogMode.value = 'edit';
  // 从后端重新获取完整数据
  loading.value = true;
  activityApi.getById(row.id)
    .then(data => {
      dialogForm.value = {
        id: data.id,
        name: data.name || '',
        category: data.category || 'health',
        location: data.location || '',
        startTime: data.startTime || '',
        endTime: data.endTime || '',
        capacity: data.capacity || 50,
        description: data.description || '',
        requirements: data.requirements || '',
        status: data.status || 'pending',
      };
      dialogVisible.value = true;
    })
    .catch(e => {
      console.error(e);
      window.alert('获取活动详情失败');
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
  if (!form.name || !form.location || !form.startTime) {
    window.alert('活动名称、地点和开始时间不能为空');
    return;
  }

  loading.value = true;
  error.value = '';
  try {
    if (dialogMode.value === 'add') {
      await activityApi.createActivity(form);
      window.alert('添加成功');
    } else {
      await activityApi.updateActivity(form.id, form);
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
  const ok = window.confirm(`确定要删除活动"${row.name}"吗?\n\n此操作不可恢复！`);
  if (!ok) return;
  loading.value = true;
  error.value = '';
  activityApi.deleteActivity(row.id)
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

function onReactivate(row) {
  const ok = window.confirm(`确定要重新上架活动"${row.name}"吗?`);
  if (!ok) return;
  loading.value = true;
  error.value = '';
  activityApi.activateActivity(row.id)
    .then(() => {
      window.alert('已重新上架');
      return load();
    })
    .catch(e => {
      console.error(e);
      window.alert('操作失败，请稍后重试');
    })
    .finally(() => {
      loading.value = false;
    });
}

function onCancel(row) {
  const ok = window.confirm(`确定要取消活动“${row.name}”吗？\n\n已报名的用户将收到取消通知。`);
  if (!ok) return;
  loading.value = true;
  error.value = '';
  activityApi
    .deactivateActivity(row.id)
    .then(() => {
      window.alert(`已取消活动：${row.name}`);
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
                <button v-if="a.status !== 'cancelled'" class="admin-action-btn delete" type="button" @click="onCancel(a)">取消</button>
                <button v-if="a.status === 'cancelled'" class="admin-action-btn toggle" type="button" @click="onReactivate(a)">重新上架</button>
                <button class="admin-action-btn delete" type="button" @click="onDelete(a)">删除</button>
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
          <h3>{{ dialogMode === 'add' ? '添加活动' : '编辑活动' }}</h3>
          <button class="admin-dialog-close" @click="closeDialog">×</button>
        </div>
        <div class="admin-dialog-body">
          <div class="admin-form-group">
            <label>活动名称 <span style="color: red">*</span></label>
            <input v-model="dialogForm.name" type="text" placeholder="请输入活动名称" />
          </div>
          <div class="admin-form-group">
            <label>活动类型</label>
            <select v-model="dialogForm.category">
              <option value="health">健康</option>
              <option value="culture">文娱</option>
              <option value="learning">学习</option>
              <option value="travel">旅游</option>
            </select>
          </div>
          <div class="admin-form-group">
            <label>活动地点 <span style="color: red">*</span></label>
            <input v-model="dialogForm.location" type="text" placeholder="请输入活动地点" />
          </div>
          <div class="admin-form-group">
            <label>开始时间 <span style="color: red">*</span></label>
            <input v-model="dialogForm.startTime" type="datetime-local" />
          </div>
          <div class="admin-form-group">
            <label>结束时间</label>
            <input v-model="dialogForm.endTime" type="datetime-local" />
          </div>
          <div class="admin-form-group">
            <label>活动容量</label>
            <input v-model.number="dialogForm.capacity" type="number" min="1" placeholder="请输入活动容量" />
          </div>
          <div class="admin-form-group">
            <label>活动介绍</label>
            <textarea v-model="dialogForm.description" rows="3" placeholder="请输入活动介绍"></textarea>
          </div>
          <div class="admin-form-group">
            <label>参与要求</label>
            <textarea v-model="dialogForm.requirements" rows="2" placeholder="请输入参与要求"></textarea>
          </div>
          <div class="admin-form-group">
            <label>状态</label>
            <select v-model="dialogForm.status">
              <option value="pending">未开始</option>
              <option value="ongoing">进行中</option>
              <option value="ended">已结束</option>
              <option value="cancelled">已取消</option>
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
