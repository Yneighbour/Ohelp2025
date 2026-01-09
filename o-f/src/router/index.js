import { createRouter, createWebHistory } from 'vue-router'

// 模块路由
const routes = [
  {
    path: '/',
    redirect: '/auth'
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

export default router