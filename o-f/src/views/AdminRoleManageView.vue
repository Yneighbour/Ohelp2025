<script setup>
import { computed, onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';

import AdminLayout from '../components/admin/AdminLayout.vue';
import * as roleApi from '../api/role';
import { adminRolesData } from '../data/admin';

const router = useRouter();
const loading = ref(false);
const error = ref('');
const rows = ref([]);

// 新增/编辑对话框状态
const dialogVisible = ref(false);
const dialogMode = ref('add'); // 'add' | 'edit'
const dialogForm = ref({
  id: null,
  name: '',
  code: '',
  description: '',
  isActive: true,
});

function formatDate(value) {
  if (!value) return '';
  const s = String(value);
  return s.length >= 10 ? s.slice(0, 10) : s;
}

function mapRole(r) {
  return {
    id: r?.id ?? '-',
    name: r?.name || '-',
    code: r?.code || '-',
    userCount: r?.userCount ?? 0,
    permissions: r?.permissions || '-',
    status: r?.isActive === false ? 'inactive' : 'active',
    statusText: r?.isActive === false ? '禁用' : '正常',
    createTime: formatDate(r?.createdAt || r?.createTime),
  };
}

async function load() {
  loading.value = true;
  error.value = '';
  try {
    const data = await roleApi.listAll();
    if (Array.isArray(data) && data.length) {
      rows.value = data.map(mapRole);
      return;
    }
    rows.value = adminRolesData.slice().map(mapRole);
  } catch (e) {
    error.value = '后端接口不可用，已切换为演示数据';
    rows.value = adminRolesData.slice().map(mapRole);
  } finally {
    loading.value = false;
  }
}

function onAdd() {
  dialogMode.value = 'add';
  dialogForm.value = {
    id: null,
    name: '',
    code: '',
    description: '',
    isActive: true,
  };
  dialogVisible.value = true;
}

function onConfig(row) {
  router.push({
    path: '/admin-permission',
    query: { roleId: row.id, roleName: row.name }
  });
}

function onEdit(row) {
  dialogMode.value = 'edit';
  loading.value = true;
  roleApi.getById(row.id)
    .then(data => {
      dialogForm.value = {
        id: data.id,
        name: data.name || '',
        code: data.code || '',
        description: data.description || '',
        isActive: data.isActive !== false,
      };
      dialogVisible.value = true;
    })
    .catch(e => {
      console.error(e);
      window.alert('获取角色信息失败');
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
  if (!form.name || !form.code) {
    window.alert('角色名称和角色编码不能为空');
    return;
  }

  loading.value = true;
  error.value = '';
  try {
    if (dialogMode.value === 'add') {
      await roleApi.createRole(form);
      window.alert('添加成功');
    } else {
      await roleApi.updateRole(form.id, form);
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
  const ok = window.confirm(`确定要删除"${row.name}"吗？\n\n此操作不可恢复！`);
  if (!ok) return;
  loading.value = true;
  error.value = '';
  roleApi.deleteRole(row.id)
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
  <AdminLayout active-page="admin-role-manage" title="角色管理">
    <div class="admin-toolbar">
      <div class="admin-filter-row">
        <button class="admin-add-btn" type="button" @click="onAdd">+ 添加角色</button>
      </div>
    </div>

    <div v-if="loading" style="color: var(--text-secondary); padding: 6px 0">正在加载...</div>
    <div v-else-if="error" style="color: var(--warning-color); padding: 6px 0">{{ error }}</div>

    <div class="admin-table-container">
      <table class="admin-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>角色名称</th>
            <th>角色编码</th>
            <th>用户数</th>
            <th>权限范围</th>
            <th>状态</th>
            <th>创建时间</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="r in rows" :key="r.id">
            <td>{{ r.id }}</td>
            <td>{{ r.name }}</td>
            <td>{{ r.code }}</td>
            <td>{{ r.userCount }}</td>
            <td style="max-width: 200px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis" :title="r.permissions">
              {{ r.permissions }}
            </td>
            <td><span class="status-tag" :class="r.status">{{ r.statusText }}</span></td>
            <td>{{ r.createTime }}</td>
            <td>
              <div class="admin-actions">
                <button class="admin-action-btn view" type="button" @click="onConfig(r)">配置权限</button>
                <button class="admin-action-btn edit" type="button" @click="onEdit(r)">编辑</button>
                <button class="admin-action-btn delete" type="button" @click="onDelete(r)">删除</button>
              </div>
            </td>
          </tr>
          <tr v-if="!rows.length">
            <td colspan="8" style="text-align: center; color: var(--text-secondary); padding: 16px 0">暂无数据</td>
          </tr>
        </tbody>
      </table>
    </div>

    <div class="admin-pagination"><span>共 {{ rows.length }} 条记录</span></div>

    <!-- 新增/编辑对话框 -->
    <div v-if="dialogVisible" class="admin-dialog-overlay" @click.self="closeDialog">
      <div class="admin-dialog">
        <div class="admin-dialog-header">
          <h3>{{ dialogMode === 'add' ? '添加角色' : '编辑角色' }}</h3>
          <button class="admin-dialog-close" @click="closeDialog">×</button>
        </div>
        <div class="admin-dialog-body">
          <div class="admin-form-group">
            <label>角色名称 <span style="color: red">*</span></label>
            <input v-model="dialogForm.name" type="text" placeholder="请输入角色名称，如：系统管理员" />
          </div>
          <div class="admin-form-group">
            <label>角色编码 <span style="color: red">*</span></label>
            <input v-model="dialogForm.code" type="text" placeholder="请输入角色编码，如：admin" :disabled="dialogMode === 'edit'" />
          </div>
          <div class="admin-form-group">
            <label>角色描述</label>
            <textarea v-model="dialogForm.description" rows="3" placeholder="请输入角色描述"></textarea>
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
