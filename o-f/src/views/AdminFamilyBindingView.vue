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
const elderOptions = ref([]); // 老人选项列表

// 新增/编辑对话框状态
const dialogVisible = ref(false);
const dialogMode = ref('add'); // 'add' | 'edit'
const dialogForm = ref({
  id: null,
  elderlyId: null,
  name: '',
  relationship: '',
  phone: '',
  isActive: true,
});

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

    // 保存老人选项用于对话框
    if (Array.isArray(elders)) {
      elderOptions.value = elders.map(e => ({ id: e.id, name: e.name || `老人#${e.id}` }));
    }

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
  dialogMode.value = 'add';
  dialogForm.value = {
    id: null,
    elderlyId: elderOptions.value[0]?.id || null,
    name: '',
    relationship: '',
    phone: '',
    isActive: true,
  };
  dialogVisible.value = true;
}

function onInvite(row) {
  window.alert(`向${row.elderName}的家属发送绑定邀请\n\n（演示版本，实际会发送短信邀请）`);
}

function onEdit(row) {
  dialogMode.value = 'edit';
  // 从后端重新获取完整数据
  loading.value = true;
  relativeApi.getById(row.id)
    .then(data => {
      dialogForm.value = {
        id: data.id,
        elderlyId: data.elderlyId || null,
        name: data.name || '',
        relationship: data.relationship || '',
        phone: data.phone || '',
        isActive: data.isActive !== false,
      };
      dialogVisible.value = true;
    })
    .catch(e => {
      console.error(e);
      window.alert('获取家属信息失败');
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
  if (!form.elderlyId || !form.name || !form.phone) {
    window.alert('老人、家属姓名和电话不能为空');
    return;
  }

  loading.value = true;
  error.value = '';
  try {
    if (dialogMode.value === 'add') {
      await relativeApi.createRelative(form);
      window.alert('添加成功');
    } else {
      await relativeApi.updateRelative(form.id, form);
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

function onUnbind(row) {
  const ok = window.confirm(`确定要解除${row.elderName}与${row.familyName}的绑定关系吗?`);
  if (!ok) return;
  loading.value = true;
  error.value = '';
  relativeApi.deleteRelative(row.id)
    .then(() => {
      window.alert('已解除绑定关系');
      return load();
    })
    .catch(e => {
      console.error(e);
      window.alert('解除失败，请稍后重试');
    })
    .finally(() => {
      loading.value = false;
    });
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

    <!-- 新增/编辑对话框 -->
    <div v-if="dialogVisible" class="admin-dialog-overlay" @click.self="closeDialog">
      <div class="admin-dialog">
        <div class="admin-dialog-header">
          <h3>{{ dialogMode === 'add' ? '添加家属绑定' : '编辑家属信息' }}</h3>
          <button class="admin-dialog-close" @click="closeDialog">×</button>
        </div>
        <div class="admin-dialog-body">
          <div class="admin-form-group">
            <label>选择老人 <span style="color: red">*</span></label>
            <select v-model="dialogForm.elderlyId" :disabled="dialogMode === 'edit'">
              <option v-for="elder in elderOptions" :key="elder.id" :value="elder.id">
                {{ elder.name }}
              </option>
            </select>
          </div>
          <div class="admin-form-group">
            <label>家属姓名 <span style="color: red">*</span></label>
            <input v-model="dialogForm.name" type="text" placeholder="请输入家属姓名" />
          </div>
          <div class="admin-form-group">
            <label>关系</label>
            <input v-model="dialogForm.relationship" type="text" placeholder="例：子女、配偶、兄弟姐妹" />
          </div>
          <div class="admin-form-group">
            <label>联系电话 <span style="color: red">*</span></label>
            <input v-model="dialogForm.phone" type="text" placeholder="请输入联系电话" />
          </div>
          <div class="admin-form-group">
            <label>
              <input v-model="dialogForm.isActive" type="checkbox" />
              已确认绑定
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
