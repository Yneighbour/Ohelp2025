import { validateToken } from '../api/auth';

let validatedOnce = false;
let lastToken = null;

export async function validateExistingToken(token) {
  // 同一个 token 只校验一次，避免每次路由跳转都发请求
  if (validatedOnce && lastToken === token) return true;

  try {
    const ok = await validateToken(token);
    validatedOnce = true;
    lastToken = token;
    return ok;
  } catch {
    return false;
  }
}
