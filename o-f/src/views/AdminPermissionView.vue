<script setup>
import { computed, onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import AdminLayout from '../components/admin/AdminLayout.vue';
import * as roleApi from '../api/role';
import * as permissionApi from '../api/permission';
import { adminPermissionsData } from '../data/admin';

const route = useRoute();
const router = useRouter();

const loading = ref(false);
const error = ref('');

// 从路由获取角色信息
const roleId = ref(route.query.roleId || '');
const roleName = ref(route.query.roleName || '');

// 所有权限列表（按模块分组）
const allPermissions = ref([]);
// 当前角色已分配的权限ID集合
const assignedPermissionIds = ref(new Set());

// 按模块分组的权限
const modules = computed(() => {
  const moduleMap = new Map();
  
  for (const perm of allPermissions.value) {
    const module = perm.module || '其他';
    if (!moduleMap.has(module)) {
      moduleMap.set(module, {
        id: module,
        module: module,
        permissions: []
      });
    }
    
    moduleMap.get(module).permissions.push({
      id: perm.id,
      name: perm.name,
      code: perm.code,
      description: perm.description,
      enabled: assignedPermissionIds.value.has(perm.id)
    });
  }
  
  return Array.from(moduleMap.values());
});

async function loadPermissions() {
  loading.value = true;
  error.value = '';
  
  try {
    // 加载所有权限
    const allPerms = await permissionApi.listAll();
    if (Array.isArray(allPerms) && allPerms.length) {
      allPermissions.value = allPerms;
    } else {
      // 演示数据
      allPermissions.value = adminPermissionsData.flatMap(m =>
        m.permissions.map(p => ({
          id: p.id,
          name: p.name,
          code: p.id,
          module: m.module,
          description: p.name,
          isActive: true
        }))
      );
    }
    
    // 加载当前角色已有权限
    if (roleId.value) {
      const rolePerms = await roleApi.getRolePermissions(roleId.value);
      if (Array.isArray(rolePerms)) {
        assignedPermissionIds.value = new Set(rolePerms.map(p => p.id));
      }
    }
  } catch (e) {
    console.error(e);
    error.value = '加载权限数据失败，已切换为演示数据';
    // 演示数据
    allPermissions.value = adminPermissionsData.flatMap(m =>
      m.permissions.map(p => ({
        id: p.id,
        name: p.name,
        code: p.id,
        module: m.module,
        description: p.name,
        isActive: true
      }))
    );
    assignedPermissionIds.value = new Set(
      adminPermissionsData.flatMap(m => 
        m.permissions.filter(p => p.enabled).map(p => p.id)
      )
    );
  } finally {
    loading.value = false;
  }
}

function toggle(perm) {
  perm.enabled = !perm.enabled;
  if (perm.enabled) {
    assignedPermissionIds.value.add(perm.id);
  } else {
    assignedPermissionIds.value.delete(perm.id);
  }
}

async function save() {
  if (!roleId.value) {
    window.alert('未选择角色');
    return;
  }
  
  const permissionIds = Array.from(assignedPermissionIds.value);
  
  loading.value = true;
  error.value = '';
  
  try {
    await roleApi.assignPermissions(roleId.value, permissionIds);
    window.alert('权限设置已保存');
  } catch (e) {
    console.error(e);
    window.alert(`保存失败: ${e.message || '请稍后重试'}`);
  } finally {
    loading.value = false;
  }
}

function goBack() {
  router.back();
}

onMounted(loadPermissions);
</script>

<template>
  <AdminLayout active-page="admin-permission" :title="`权限设置${roleName ? ' - ' + roleName : ''}`">
    <div class="admin-toolbar">
      <div class="admin-filter-row">
        <button class="admin-action-btn" type="button" @click="goBack" style="margin-right: 12px">← 返回</button>
        <span v-if="roleName" style="color: var(--text-primary); font-weight: 500">当前角色：{{ roleName }}</span>
        <button class="admin-add-btn" type="button" @click="save" style="margin-left: auto">保存设置</button>
      </div>
    </div>

    <div v-if="loading" style="color: var(--text-secondary); padding: 6px 0">正在加载...</div>
    <div v-else-if="error" style="color: var(--warning-color); padding: 6px 0">{{ error }}</div>

    <div v-for="m in modules" :key="m.id" class="permission-module">
      <div class="permission-module-header">{{ m.module }}</div>
      <div class="permission-list">
        <div v-for="p in m.permissions" :key="p.id" class="permission-item">
          <span class="permission-name">{{ p.name }}</span>
          <div class="toggle-switch" :class="{ active: p.enabled }" role="button" tabindex="0" @click="toggle(p)"></div>
        </div>
      </div>
    </div>
    
    <div v-if="!modules.length && !loading" style="text-align: center; color: var(--text-secondary); padding: 32px 0">
      暂无权限数据
    </div>
  </AdminLayout>
</template>
