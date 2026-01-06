import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'
import { useTokenStore } from '../store/token'

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../pages/Login.vue'),
    meta: { requiresAuth: false }
  },
  {
    path: '/home',
    name: 'Home',
    component: () => import('../pages/Home.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: () => import('../pages/Dashboard.vue'),
    meta: { requiresAuth: true }
  },
  // System management module
  {
    path: '/user',
    name: 'User',
    component: () => import('../pages/system/UserList.vue'),
    meta: { requiresAuth: true }
  },
  // Elder management module
  {
    path: '/elder',
    name: 'Elder',
    component: () => import('../pages/elder/LaorenList.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/elder/:id',
    name: 'ElderDetail',
    component: () => import('../pages/elder/LaorenDetail.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/relative',
    name: 'Relative',
    component: () => import('../pages/elder/QinshuList.vue'),
    meta: { requiresAuth: true }
  },
  // Worker management module
  {
    path: '/worker',
    name: 'Worker',
    component: () => import('../pages/worker/LaogongList.vue'),
    meta: { requiresAuth: true }
  },
  // Service management module
  {
    path: '/service-category',
    name: 'ServiceCategory',
    component: () => import('../pages/service/FuwuleixingList.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/service-item',
    name: 'ServiceItem',
    component: () => import('../pages/service/FuwuxiangmuList.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/service-purchase',
    name: 'ServicePurchase',
    component: () => import('../pages/service/FuwugoumaiList.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/service-request',
    name: 'ServiceRequest',
    component: () => import('../pages/service/Requests.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/review',
    name: 'Review',
    component: () => import('../pages/service/PingjiaList.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/care',
    name: 'Care',
    component: () => import('../pages/service/ZhaoliaoList.vue'),
    meta: { requiresAuth: true }
  },
  // Activity management module
  {
    path: '/activity-category',
    name: 'ActivityCategory',
    component: () => import('../pages/activity/HuodongfenleiList.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/activity-info',
    name: 'ActivityInfo',
    component: () => import('../pages/activity/HuodongxinxiList.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/visit',
    name: 'Visit',
    component: () => import('../pages/activity/TanwangList.vue'),
    meta: { requiresAuth: true }
  },
  // Health management module
  {
    path: '/health-record',
    name: 'HealthRecord',
    component: () => import('../pages/health/MeirijiankangList.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/medical-history',
    name: 'MedicalHistory',
    component: () => import('../pages/health/JiwangbingshiList.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/point',
    name: 'Point',
    component: () => import('../pages/health/JifenzengjiaList.vue'),
    meta: { requiresAuth: true }
  },
  // Emergency management module
  {
    path: '/emergency',
    name: 'Emergency',
    component: () => import('../pages/emergency/JinjiqiuzhuList.vue'),
    meta: { requiresAuth: true }
  },
  // 404 page
  {
    path: '/:pathMatch(.*)*',
    redirect: '/login'
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  const tokenStore = useTokenStore()
  
  // 检查路由是否需要认证
  const requiresAuth = to.meta.requiresAuth !== false
  
  // 检查是否已登录（有token且有用户信息）
  const isAuthenticated = tokenStore.token && userStore.userInfo
  
  if (requiresAuth && !isAuthenticated) {
    // 需要认证但未登录，重定向到登录页
    next('/login')
  } else if (to.path === '/login' && isAuthenticated) {
    // 已登录但尝试访问登录页，重定向到首页
    next('/dashboard')
  } else {
    // 其他情况正常访问
    next()
  }
})

export default router