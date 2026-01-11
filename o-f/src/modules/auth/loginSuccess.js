import { login } from './auth.api.js'
import { getUser } from '../user/user.api.js'
import { normalizeRole } from '../../shared/role.js'
import { ROLE_ROUTE_MAP } from '../../router/roleMap.js'

/**
 * 登录成功后的前端处理逻辑示例：
 * 1) /auth/login 拿 token + userId
 * 2) /user/{id} 拿 user.role
 * 3) normalizeRole 归一化为宏角色（ADMIN/WORKER/USER）
 * 4) 写入 localStorage：token、role（宏角色）
 * 5) 按 role 跳转到对应入口
 *
 * @param {{ username: string, password: string, router: any }} params
 * @returns {Promise<{ token: string, userId: number, role: 'ADMIN'|'WORKER'|'USER' }>} 
 */
export async function loginAndRedirect(params) {
  const { username, password, router } = params

  // 1) 登录：拿 token + userId
  const loginRes = await login({ username, password })
  const auth = loginRes?.data
  if (!auth?.token || !auth?.userId) {
    throw new Error('登录返回数据不完整')
  }

  // 2) 取用户：拿 role（角色只来源于后端 User.role）
  const userRes = await getUser(auth.userId)
  const user = userRes?.data
  const role = normalizeRole(user?.role)
  if (!role) {
    throw new Error('用户角色不被前端识别')
  }

  // 3) 最小持久化：token + 宏 role
  localStorage.setItem('token', auth.token)
  localStorage.setItem('role', role)

  // 4) 宏角色 -> 入口路由（唯一分发点）
  const targetPath = ROLE_ROUTE_MAP[role]
  if (!targetPath) {
    localStorage.removeItem('token')
    localStorage.removeItem('role')
    throw new Error('入口路由未配置')
  }

  if (router?.replace) {
    await router.replace(targetPath)
  }

  return { token: auth.token, userId: auth.userId, role }
}
