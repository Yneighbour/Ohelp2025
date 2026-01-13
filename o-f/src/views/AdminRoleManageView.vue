<script setup>
import { ref } from 'vue';

import AdminLayout from '../components/admin/AdminLayout.vue';
import { adminRolesData } from '../data/admin';

const rows = ref(adminRolesData.slice());

function onAdd() {
  window.alert('添加角色\n\n（演示版本，实际会打开添加表单）');
}

function onConfig(row) {
  window.alert(`配置${row.name}的权限\n\n（演示版本，实际会打开权限配置页面）`);
}

function onEdit(row) {
  window.alert(`编辑${row.name} (ID: ${row.id})\n\n（演示版本，实际会打开编辑表单）`);
}

function onDelete(row) {
  const ok = window.confirm(`确定要删除“${row.name}”吗？\n\n此操作不可恢复！`);
  if (!ok) return;
  window.alert(`已删除${row.name} (ID: ${row.id})\n\n（演示版本，实际会调用后端API删除）`);
}
</script>

<template>
  <AdminLayout active-page="admin-role-manage" title="角色管理">
    <div class="admin-toolbar">
      <div class="admin-filter-row">
        <button class="admin-add-btn" type="button" @click="onAdd">+ 添加角色</button>
      </div>
    </div>

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
        </tbody>
      </table>
    </div>

    <div class="admin-pagination"><span>共 {{ rows.length }} 条记录</span></div>
  </AdminLayout>
</template>
