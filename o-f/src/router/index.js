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
  // 用户管理模块
  {
    path: '/user',
    name: 'User',
    component: () => import('../pages/UserList.vue')
  },
  // 老人管理模块
  {
    path: '/laoren',
    name: 'Laoren',
    component: () => import('../pages/LaorenList.vue')
  },
  // 服务管理模块
  {
    path: '/fuwuleixing',
    name: 'Fuwuleixing',
    component: () => import('../pages/FuwuleixingList.vue')
  },
  {
    path: '/fuwuxiangmu',
    name: 'Fuwuxiangmu',
    component: () => import('../pages/FuwuxiangmuList.vue')
  },
  {
    path: '/fuwugoumai',
    name: 'Fuwugoumai',
    component: () => import('../pages/FuwugoumaiList.vue')
  },
  // 活动管理模块
  {
    path: '/huodongfenlei',
    name: 'Huodongfenlei',
    component: () => import('../pages/HuodongfenleiList.vue')
  },
  {
    path: '/huodongxinxi',
    name: 'Huodongxinxi',
    component: () => import('../pages/HuodongxinxiList.vue')
  },
  // 健康与医疗模块
  {
    path: '/meirijiankang',
    name: 'Meirijiankang',
    component: () => import('../pages/MeirijiankangList.vue')
  },
  {
    path: '/jiwangbingshi',
    name: 'Jiwangbingshi',
    component: () => import('../pages/JiwangbingshiList.vue')
  },
  {
    path: '/jifenzengjia',
    name: 'Jifenzengjia',
    component: () => import('../pages/JifenzengjiaList.vue')
  },
  // 社交与紧急模块
  {
    path: '/qinshu',
    name: 'Qinshu',
    component: () => import('../pages/QinshuList.vue')
  },
  {
    path: '/laogong',
    name: 'Laogong',
    component: () => import('../pages/LaogongList.vue')
  },
  {
    path: '/jinjiqiuzhu',
    name: 'Jinjiqiuzhu',
    component: () => import('../pages/JinjiqiuzhuList.vue')
  },
  // 护工管理模块
  {
    path: '/zhaoliao',
    name: 'Zhaoliao',
    component: () => import('../pages/ZhaoliaoList.vue')
  },
  {
    path: '/pingjia',
    name: 'Pingjia',
    component: () => import('../pages/PingjiaList.vue')
  },
  // 家属探望模块
  {
    path: '/tanwang',
    name: 'Tanwang',
    component: () => import('../pages/TanwangList.vue')
  },
  {
    path: '/requests',
    name: 'Requests',
    component: () => import('../pages/Requests.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router