import { createRouter, createWebHistory } from 'vue-router'

import { ROLE_ROUTE_MAP } from './roleMap.js'
import { isPathAllowed } from './roleAccess.js'
import { MessageService } from '../shared/message.js'

// 模块路由
const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../modules/auth/Login.vue'),
    meta: {
      public: true
    }
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: () => import('../modules/Dashboard.vue')
  },
  {
    path: '/auth',
    name: 'Auth',
    component: () => import('../modules/auth/AuthModule.vue')
  },
  {
    path: '/user',
    name: 'User',
    component: () => import('../modules/user/UserModule.vue')
  },
  {
    path: '/elder',
    name: 'Elder',
    component: () => import('../modules/elder/ElderModule.vue')
  },
  {
    path: '/activity',
    name: 'Activity',
    component: () => import('../modules/activity/ActivityModule.vue')
  },
  {
    path: '/emergency',
    name: 'Emergency',
    component: () => import('../modules/emergency/EmergencyModule.vue')
  },
  {
    path: '/health',
    name: 'Health',
    component: () => import('../modules/health/HealthModule.vue')
  },
  {
    path: '/serviceorder',
    name: 'ServiceOrder',
    component: () => import('../modules/serviceorder/ServiceOrderModule.vue')
  },
  {
    path: '/worker',
    name: 'Worker',
    component: () => import('../modules/worker/WorkerModule.vue')
  },
  {
    path: '/file',
    name: 'File',
    component: () => import('../modules/file/FileModule.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 全局路由守卫：只负责“你是不是已登录的人”，不判断 role
router.beforeEach((to) => {
  // 已登录但访问 /login：按本地 role 做一次入口分发（非权限判断）
  if (to.path === '/login') {
    const token = localStorage.getItem('token')
    if (!token) return true

    const role = localStorage.getItem('role')
    const targetPath = role ? ROLE_ROUTE_MAP[role] : null
    return targetPath || '/dashboard'
  }

  if (to.meta?.public) {
    return true
  }

  const token = localStorage.getItem('token')
  if (!token) {
    return '/login'
  }

  // 软性角色导航限制（防止“所有角色看起来都一样”）
  const role = localStorage.getItem('role')
  if (role && !isPathAllowed(role, to.path)) {
    const targetPath = ROLE_ROUTE_MAP[role] || '/dashboard'
    MessageService.warning('当前角色无权访问该页面，已为你跳转到默认入口')
    return targetPath
  }

  return true
})

export default router