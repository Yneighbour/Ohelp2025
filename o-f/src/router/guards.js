/**
 * 路由守卫与权限控制
 * 
 * 功能：
 * 1. 身份认证守卫：未登录用户重定向到登录页
 * 2. 角色权限守卫：根据 userType 控制路由访问
 * 3. 管理端/用户端路由隔离
 */

import { useAuthStore } from '@/stores/auth';

/**
 * 设置路由守卫
 * @param {Router} router - Vue Router 实例
 */
export function setupRouterGuards(router) {
  /**
   * 全局前置守卫
   */
  router.beforeEach((to, from, next) => {
    const authStore = useAuthStore();

    // 1. 公开路由（无需认证）
    if (to.meta?.public) {
      // 已登录用户访问登录页则重定向到首页
      if (authStore.isAuthenticated) {
        return next(authStore.isAdminUser ? '/admin' : '/user');
      }
      return next();
    }

    // 2. 需要认证的路由
    if (!authStore.isAuthenticated) {
      return next({
        name: 'login',
        query: { redirect: to.fullPath },
      });
    }

    // 3. 管理端路由权限检查
    if (to.meta?.requiresAdmin) {
      if (!authStore.isAdminUser) {
        return next({
          name: 'unauthorized',
          query: { reason: 'admin_required' },
        });
      }
    }

    // 4. 用户端路由权限检查
    if (to.meta?.requiresClient) {
      if (!authStore.isClientUser) {
        return next({
          name: 'unauthorized',
          query: { reason: 'client_required' },
        });
      }
    }

    // 5. 老人专属路由
    if (to.meta?.requiresElder) {
      if (!authStore.isElderUser) {
        return next({
          name: 'unauthorized',
          query: { reason: 'elder_required' },
        });
      }
    }

    // 6. 家属专属路由
    if (to.meta?.requiresFamily) {
      if (!authStore.isFamilyUser) {
        return next({
          name: 'unauthorized',
          query: { reason: 'family_required' },
        });
      }
    }

    // 7. 角色白名单检查
    if (to.meta?.allowedRoles && Array.isArray(to.meta.allowedRoles)) {
      if (!to.meta.allowedRoles.includes(authStore.userType)) {
        return next({
          name: 'unauthorized',
          query: { reason: 'role_not_allowed' },
        });
      }
    }

    next();
  });

  /**
   * 全局后置守卫（用于处理路由切换后的其他逻辑，如页面标题、分析等）
   */
  router.afterEach((to, from) => {
    // 更新页面标题
    if (to.meta?.title) {
      document.title = `${to.meta.title} - Ohelp2025`;
    }
  });
}

/**
 * 权限检查工具函数（可在组件中使用）
 */
export function useRouterAuth() {
  const authStore = useAuthStore();

  return {
    /**
     * 检查用户是否拥有指定角色
     */
    hasRole(role) {
      if (Array.isArray(role)) {
        return role.includes(authStore.userType);
      }
      return authStore.userType === role;
    },

    /**
     * 检查用户是否为管理端用户
     */
    isAdmin() {
      return authStore.isAdminUser;
    },

    /**
     * 检查用户是否为用户端用户
     */
    isClient() {
      return authStore.isClientUser;
    },

    /**
     * 检查用户是否为老人
     */
    isElder() {
      return authStore.isElderUser;
    },

    /**
     * 检查用户是否为家属
     */
    isFamily() {
      return authStore.isFamilyUser;
    },

    /**
     * 获取当前老人 ID
     */
    getElderlyId() {
      return authStore.elderlyId;
    },

    /**
     * 获取当前亲属 ID
     */
    getRelativeId() {
      return authStore.relativeId;
    },

    /**
     * 获取当前用户类型
     */
    getUserType() {
      return authStore.userType;
    },
  };
}
