<script setup>
import { ref } from 'vue';

import AdminLayout from '../components/admin/AdminLayout.vue';
import { adminPermissionsData } from '../data/admin';

const role = ref('admin');
const modules = ref(adminPermissionsData.map((m) => ({
  ...m,
  permissions: m.permissions.map((p) => ({ ...p })),
})));

function toggle(perm) {
  perm.enabled = !perm.enabled;
  const action = perm.enabled ? '开启' : '关闭';
  window.alert(`已${action}权限：${perm.name}\n\n（演示版本，实际会调用后端API更新权限）`);
}

function save() {
  window.alert('权限设置已保存');
}
</script>

<template>
  <AdminLayout active-page="admin-permission" title="权限设置">
    <div class="admin-toolbar">
      <div class="admin-filter-row">
        <select v-model="role" class="admin-filter-select" @change="() => window.alert('切换角色：' + role)">
          <option value="admin">管理员</option>
          <option value="operator">操作员</option>
          <option value="user">普通用户</option>
        </select>
        <button class="admin-add-btn" type="button" @click="save">保存设置</button>
      </div>
    </div>

    <div v-for="m in modules" :key="m.id" class="permission-module">
      <div class="permission-module-header">{{ m.module }}</div>
      <div class="permission-list">
        <div v-for="p in m.permissions" :key="p.id" class="permission-item">
          <span class="permission-name">{{ p.name }}</span>
          <div class="toggle-switch" :class="{ active: p.enabled }" role="button" tabindex="0" @click="toggle(p)"></div>
        </div>
      </div>
    </div>
  </AdminLayout>
</template>
