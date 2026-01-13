import axios from 'axios';

const baseURL = import.meta.env.VITE_API_BASE_URL || '/api';

export const api = axios.create({
  baseURL,
  timeout: 15000,
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers = config.headers || {};
    config.headers.Authorization = token;
  }
  return config;
});

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
