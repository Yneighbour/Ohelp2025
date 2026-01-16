import { defineStore } from 'pinia';
import { login as apiLogin } from '@/api/auth';

/**
 * Auth Store - 管理用户认证与身份信息
 * 
 * 存储内容：
 * - token: JWT/Bearer token
 * - user: 用户基本信息
 * - userType: 用户类型 ('elder', 'family', 'admin', 'operator', 'manager')
 * - elderlyId: 老人ID（elder/family 才有值）
 * - relativeId: 亲属ID（family 才有值）
 * - isAuthenticated: 是否已认证
 */
export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: null,
    user: null,
    userType: null,        // 'admin', 'operator', 'manager', 'elder', 'family'
    elderlyId: null,       // 若是 elder/family 则有值
    relativeId: null,      // 若是 family 则有值
    isAuthenticated: false,
  }),

  getters: {
    /**
     * 判断当前用户是否为管理端用户
     */
    isAdminUser: (state) => {
      return ['admin', 'operator', 'manager'].includes(state.userType);
    },

    /**
     * 判断当前用户是否为用户端用户（elder/family）
     */
    isClientUser: (state) => {
      return ['elder', 'family'].includes(state.userType);
    },

    /**
     * 判断当前用户是否为老人账号
     */
    isElderUser: (state) => {
      return state.userType === 'elder';
    },

    /**
     * 判断当前用户是否为家属账号
     */
    isFamilyUser: (state) => {
      return state.userType === 'family';
    },

    /**
     * 获取用户显示名称
     */
    displayName: (state) => {
      return state.user?.name || state.user?.username || '用户';
    },
  },

  actions: {
    /**
     * 登录
     * @param {string} username - 用户名
     * @param {string} password - 密码
     */
    async login(username, password) {
      try {
        const response = await apiLogin({ username, password });

        // 保存认证信息
        this.token = response.token;
        this.user = response;
        this.isAuthenticated = true;

        // 保存身份信息（后端返回）
        this.userType = response.userType;        // 'elder', 'family', 'admin'
        this.elderlyId = response.elderlyId;      // elder/family 有值
        this.relativeId = response.relativeId;    // family 有值

        // 持久化到 localStorage
        this._persistToLocalStorage();

        return response;
      } catch (error) {
        this.isAuthenticated = false;
        throw error;
      }
    },

    /**
     * 登出
     */
    logout() {
      this.token = null;
      this.user = null;
      this.userType = null;
      this.elderlyId = null;
      this.relativeId = null;
      this.isAuthenticated = false;

      // 清除 localStorage
      localStorage.removeItem('auth_token');
      localStorage.removeItem('auth_user_type');
      localStorage.removeItem('auth_elderly_id');
      localStorage.removeItem('auth_relative_id');
    },

    /**
     * 从 localStorage 恢复身份信息
     * 应在应用初始化时调用
     */
    restoreFromLocalStorage() {
      const token = localStorage.getItem('auth_token');
      if (!token) {
        this.isAuthenticated = false;
        return false;
      }

      this.token = token;
      this.userType = localStorage.getItem('auth_user_type');
      this.elderlyId = localStorage.getItem('auth_elderly_id');
      this.relativeId = localStorage.getItem('auth_relative_id');
      this.isAuthenticated = true;

      return true;
    },

    /**
     * 清除身份信息（不发起 logout 请求）
     */
    clearIdentity() {
      this.token = null;
      this.user = null;
      this.userType = null;
      this.elderlyId = null;
      this.relativeId = null;
      this.isAuthenticated = false;
      localStorage.removeItem('auth_token');
      localStorage.removeItem('auth_user_type');
      localStorage.removeItem('auth_elderly_id');
      localStorage.removeItem('auth_relative_id');
    },

    /**
     * 私有方法：持久化到 localStorage
     */
    _persistToLocalStorage() {
      localStorage.setItem('auth_token', this.token);
      localStorage.setItem('auth_user_type', this.userType || '');
      localStorage.setItem('auth_elderly_id', this.elderlyId || '');
      localStorage.setItem('auth_relative_id', this.relativeId || '');
    },
  },
});
