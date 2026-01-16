import axios from 'axios';
import { useAuthStore } from '@/stores/auth';

const baseURL = import.meta.env.VITE_API_BASE_URL || '/api';

export const api = axios.create({
  baseURL,
  timeout: 15000,
});

/**
 * 请求拦截器：自动添加 Authorization header
 */
api.interceptors.request.use((config) => {
  const authStore = useAuthStore();
  
  // 优先使用 Pinia store 中的 token
  const token = authStore.token || localStorage.getItem('auth_token');
  
  if (token) {
    config.headers = config.headers || {};
    config.headers.Authorization = `Bearer ${token}`;
  }
  
  return config;
});

/**
 * 响应拦截器：处理 401/403 错误
 */
api.interceptors.response.use(
  (response) => response,
  (error) => {
    const authStore = useAuthStore();
    
    // 处理 401 Unauthorized - 令牌过期或无效
    if (error.response?.status === 401) {
      authStore.clearIdentity();
      window.location.href = '/login?reason=unauthorized';
    }
    
    // 处理 403 Forbidden - 权限不足
    if (error.response?.status === 403) {
      window.location.href = '/unauthorized?reason=forbidden';
    }
    
    return Promise.reject(error);
  }
);

export async function requestRaw(config) {
  const res = await api.request(config);
  return res.data;
}

export async function requestData(config) {
  const envelope = await requestRaw(config);
  const code = envelope?.code;
  if (code !== 200 && code !== 201) {
    const message = envelope?.message || '请求失败';
    throw new Error(message);
  }
  return envelope?.data;
}

