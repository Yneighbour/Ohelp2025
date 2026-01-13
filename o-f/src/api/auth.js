import { requestRaw, requestData } from './http';

export async function login({ username, password }) {
  // 注意：后端 /auth/login 在失败时也返回 code=200（演示模式），所以这里用 requestRaw。
  const envelope = await requestRaw({
    method: 'POST',
    url: '/auth/login',
    data: { username, password },
    // 登录请求单独设置较短超时时间，避免长时间无响应
    timeout: 5000,
  });

  if (envelope?.data?.token) return envelope.data;

  const message = envelope?.message || '登录失败';
  throw new Error(message);
}

export async function validateToken(token) {
  // 演示模式：永远 200，data.valid 代表是否有效
  const data = await requestData({
    method: 'GET',
    url: `/auth/validate/${encodeURIComponent(token)}`,
    // Token 校验也设置较短超时时间，避免路由守卫长时间卡住
    timeout: 5000,
  });
  return Boolean(data?.valid);
}
