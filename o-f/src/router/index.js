import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    redirect: '/home'
  },
  {
    path: '/home',
    name: 'Home',
    component: () => import('../pages/Home.vue')
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: () => import('../pages/Dashboard.vue')
  },
  // System management module
  {
    path: '/user',
    name: 'User',
    component: () => import('../pages/system/UserList.vue')
  },
  // Elder management module
  {
    path: '/elder',
    name: 'Elder',
    component: () => import('../pages/elder/LaorenList.vue')
  },
  {
    path: '/elder/:id',
    name: 'ElderDetail',
    component: () => import('../pages/elder/LaorenDetail.vue')
  },
  {
    path: '/relative',
    name: 'Relative',
    component: () => import('../pages/elder/QinshuList.vue')
  },
  // Worker management module
  {
    path: '/worker',
    name: 'Worker',
    component: () => import('../pages/worker/LaogongList.vue')
  },
  // Service management module
  {
    path: '/service-category',
    name: 'ServiceCategory',
    component: () => import('../pages/service/FuwuleixingList.vue')
  },
  {
    path: '/service-item',
    name: 'ServiceItem',
    component: () => import('../pages/service/FuwuxiangmuList.vue')
  },
  {
    path: '/service-purchase',
    name: 'ServicePurchase',
    component: () => import('../pages/service/FuwugoumaiList.vue')
  },
  {
    path: '/service-request',
    name: 'ServiceRequest',
    component: () => import('../pages/service/Requests.vue')
  },
  {
    path: '/review',
    name: 'Review',
    component: () => import('../pages/service/PingjiaList.vue')
  },
  {
    path: '/care',
    name: 'Care',
    component: () => import('../pages/service/ZhaoliaoList.vue')
  },
  // Activity management module
  {
    path: '/activity-category',
    name: 'ActivityCategory',
    component: () => import('../pages/activity/HuodongfenleiList.vue')
  },
  {
    path: '/activity-info',
    name: 'ActivityInfo',
    component: () => import('../pages/activity/HuodongxinxiList.vue')
  },
  {
    path: '/visit',
    name: 'Visit',
    component: () => import('../pages/activity/TanwangList.vue')
  },
  // Health management module
  {
    path: '/health-record',
    name: 'HealthRecord',
    component: () => import('../pages/health/MeirijiankangList.vue')
  },
  {
    path: '/medical-history',
    name: 'MedicalHistory',
    component: () => import('../pages/health/JiwangbingshiList.vue')
  },
  {
    path: '/point',
    name: 'Point',
    component: () => import('../pages/health/JifenzengjiaList.vue')
  },
  // Emergency management module
  {
    path: '/emergency',
    name: 'Emergency',
    component: () => import('../pages/emergency/JinjiqiuzhuList.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router