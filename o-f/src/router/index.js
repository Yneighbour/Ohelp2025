import { createRouter, createWebHistory } from 'vue-router'

import { ROLE_ROUTE_MAP } from './roleMap.js'


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
    path: '/admin',
    redirect: '/admin/dashboard'
  },
  {
    path: '/admin/dashboard',
    name: 'AdminDashboard',
    component: () => import('../modules/admin/AdminDashboard.vue'),
    meta: {
      role: 'ADMIN'
    }
  },
  {
    path: '/dashboard',
    redirect: '/admin/dashboard'
  },
  {
    path: '/app',
    component: () => import('../layouts/AppLayout.vue'),
    meta: {
      role: 'USER'
    },
    children: [
      {
        path: '',
        redirect: '/app/home'
      },
      {
        path: 'home',
        name: 'AppHome',
        component: () => import('../modules/app/AppHome.vue'),
        meta: { role: 'USER' }
      },
      {
        path: 'health',
        name: 'AppHealth',
        component: () => import('../modules/app/AppHealth.vue'),
        meta: { role: 'USER' }
      },
      {
        path: 'service',
        name: 'AppService',
        component: () => import('../modules/app/AppService.vue'),
        meta: { role: 'USER' }
      },
      {
        path: 'me',
        name: 'AppMe',
        component: () => import('../modules/app/AppMe.vue'),
        meta: { role: 'USER' }
      }
    ]
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
    redirect: '/worker/tasks'
  },
  {
    path: '/worker/tasks',
    name: 'WorkerTasks',
    component: () => import('../modules/worker/WorkerTasks.vue'),
    meta: {
      role: 'WORKER'
    }
  },
  {
    path: '/worker/records',
    name: 'WorkerRecords',
    component: () => import('../modules/worker/WorkerRecords.vue'),
    meta: {
      role: 'WORKER'
    }
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
    return targetPath || true
  }

  if (to.meta?.public) {
    return true
  }

  const token = localStorage.getItem('token')
  if (!token) {
    return '/login'
  }

  return true
})

export default router