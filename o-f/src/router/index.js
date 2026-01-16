import { createRouter, createWebHistory } from 'vue-router';
import { useAuthStore } from '../stores/auth';
import { setupRouterGuards } from './guards';
import LoginView from '../views/LoginView.vue';
import UsersView from '../views/UsersView.vue';
import HealthView from '../views/HealthView.vue';
import HealthDetailView from '../views/HealthDetailView.vue';
import AlertsView from '../views/AlertsView.vue';
import AlertDetailView from '../views/AlertDetailView.vue';
import ActivitiesView from '../views/ActivitiesView.vue';
import ActivityDetailView from '../views/ActivityDetailView.vue';
import SocialView from '../views/SocialView.vue';
import EmergencyView from '../views/EmergencyView.vue';
import ProfileView from '../views/ProfileView.vue';
import ProfileInfoView from '../views/ProfileInfoView.vue';
import ProfileHealthRecordsView from '../views/ProfileHealthRecordsView.vue';
import ProfileMyActivitiesView from '../views/ProfileMyActivitiesView.vue';
import ProfileContactsView from '../views/ProfileContactsView.vue';
import ProfileSettingsView from '../views/ProfileSettingsView.vue';
import AdminUserListView from '../views/AdminUserListView.vue';
import AdminRoleManageView from '../views/AdminRoleManageView.vue';
import AdminPermissionView from '../views/AdminPermissionView.vue';
import AdminElderListView from '../views/AdminElderListView.vue';
import AdminHealthRecordView from '../views/AdminHealthRecordView.vue';
import AdminFamilyBindingView from '../views/AdminFamilyBindingView.vue';
import AdminServiceListView from '../views/AdminServiceListView.vue';
import AdminServiceOrderView from '../views/AdminServiceOrderView.vue';
import AdminActivityListView from '../views/AdminActivityListView.vue';
import AdminEnrollmentView from '../views/AdminEnrollmentView.vue';
import { validateExistingToken } from '../services/session';

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      redirect: () => {
        const authStore = useAuthStore();
        if (!authStore.isAuthenticated) {
          return '/login';
        }
        // 根据用户类型重定向
        if (authStore.isAdminUser) {
          return '/admin-user-list';
        }
        return '/health';
      },
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView,
      meta: { public: true, title: '登录' },
    },
    {
      path: '/unauthorized',
      name: 'unauthorized',
      component: () => ({
        template: '<div class="text-center p-6"><h1>无权访问</h1><p>您没有权限访问此页面</p><router-link to="/login">返回登录</router-link></div>'
      }),
      meta: { public: true, title: '无权访问' },
    },
    {
      path: '/health',
      name: 'health',
      component: HealthView,
      meta: { requiresAuth: true, requiresClient: true, title: '健康', showBottomNav: true },
    },
    {
      path: '/health/detail/:type',
      name: 'health-detail',
      component: HealthDetailView,
      meta: { requiresAuth: true, requiresClient: true, title: '健康详情', showBottomNav: false },
    },
    {
      path: '/alerts',
      name: 'alerts',
      component: AlertsView,
      meta: { requiresAuth: true, requiresClient: true, title: '健康预警', showBottomNav: false },
    },
    {
      path: '/alerts/:key',
      name: 'alert-detail',
      component: AlertDetailView,
      meta: { requiresAuth: true, requiresClient: true, title: '预警详情', showBottomNav: false },
    },
    {
      path: '/users',
      name: 'users',
      component: UsersView,
      meta: { requiresAuth: true, title: '用户列表(示例接入)', showBottomNav: false },
    },

    // 其他页面先保持路由可达，逐步按原静态演示迁移
    { path: '/social', component: SocialView, meta: { requiresAuth: true, requiresClient: true, title: '社交', showBottomNav: true } },
    { path: '/emergency', component: EmergencyView, meta: { requiresAuth: true, requiresClient: true, title: '紧急呼救', showBottomNav: true } },
    { path: '/profile', component: ProfileView, meta: { requiresAuth: true, requiresClient: true, title: '个人中心', showBottomNav: true } },
    { path: '/profile/info', component: ProfileInfoView, meta: { requiresAuth: true, requiresClient: true, title: '个人资料', showBottomNav: false } },
    { path: '/profile/health-records', component: ProfileHealthRecordsView, meta: { requiresAuth: true, requiresClient: true, title: '健康档案', showBottomNav: false } },
    { path: '/profile/my-activities', component: ProfileMyActivitiesView, meta: { requiresAuth: true, requiresClient: true, title: '我的活动', showBottomNav: false } },
    { path: '/profile/contacts', component: ProfileContactsView, meta: { requiresAuth: true, requiresClient: true, title: '紧急联系人', showBottomNav: false } },
    { path: '/profile/settings', component: ProfileSettingsView, meta: { requiresAuth: true, requiresClient: true, title: '系统设置', showBottomNav: false } },
    { path: '/activities', component: ActivitiesView, meta: { requiresAuth: true, requiresClient: true, title: '活动', showBottomNav: true } },
    { path: '/activities/:id', component: ActivityDetailView, meta: { requiresAuth: true, requiresClient: true, title: '活动详情', showBottomNav: false } },

    // ========== 管理端（静态版 /admin-* 路由迁移） ==========
    { path: '/admin', redirect: '/admin-user-list' },
    { path: '/admin-user-list', component: AdminUserListView, meta: { requiresAuth: true, requiresAdmin: true, title: '用户列表', showBottomNav: false } },
    { path: '/admin-role-manage', component: AdminRoleManageView, meta: { requiresAuth: true, requiresAdmin: true, title: '角色管理', showBottomNav: false } },
    { path: '/admin-permission', component: AdminPermissionView, meta: { requiresAuth: true, requiresAdmin: true, title: '权限设置', showBottomNav: false } },
    { path: '/admin-elder-list', component: AdminElderListView, meta: { requiresAuth: true, requiresAdmin: true, title: '老人档案', showBottomNav: false } },
    { path: '/admin-health-record', component: AdminHealthRecordView, meta: { requiresAuth: true, requiresAdmin: true, title: '健康记录', showBottomNav: false } },
    { path: '/admin-family-bindng', component: AdminFamilyBindingView, meta: { requiresAuth: true, requiresAdmin: true, title: '家属绑定', showBottomNav: false } },
    { path: '/admin-service-list', component: AdminServiceListView, meta: { requiresAuth: true, requiresAdmin: true, title: '服务项目', showBottomNav: false } },
    { path: '/admin-service-order', component: AdminServiceOrderView, meta: { requiresAuth: true, requiresAdmin: true, title: '服务预约', showBottomNav: false } },
    { path: '/admin-activity-list', component: AdminActivityListView, meta: { requiresAuth: true, requiresAdmin: true, title: '活动列表', showBottomNav: false } },
    { path: '/admin-enrollment', component: AdminEnrollmentView, meta: { requiresAuth: true, requiresAdmin: true, title: '报名管理', showBottomNav: false } },

    // ========== 404 页面 ==========
    {
      path: '/:pathMatch(.*)*',
      name: 'not-found',
      component: () => ({
        template: '<div class="text-center p-6"><h1>404 页面未找到</h1><router-link to="/login">返回登录</router-link></div>'
      }),
      meta: { public: true, title: '页面未找到' },
    },
  ],
});

router.beforeEach(async (to) => {
  if (!to.meta?.requiresAuth) return true;

  const token = localStorage.getItem('token');
  if (!token) return { path: '/login', query: { next: to.fullPath } };

  // 演示模式：后端不强制鉴权，但我们在前端做一次“软校验”，便于后续扩展。
  const ok = await validateExistingToken(token);
  if (!ok) {
    localStorage.removeItem('token');
    return { path: '/login' };
  }

  if (to.meta?.requiresAdmin) {
    const role = localStorage.getItem('role');
    if (role !== 'admin') return { path: '/health' };
  }

  return true;
});


// 设置路由守卫
setupRouterGuards(router);

export default router;
