import { login } from './auth.api.js'
import { getUser } from '../user/user.api.js'
import { normalizeRole } from '../../shared/role.js'
import { ROLE_ROUTE_MAP } from '../../router/roleMap.js'

/**
 * 登录成功后的前端处理逻辑示例：
 * 1) /auth/login 拿 token + userId
 * 2) 优先使用 /auth/login 直接返回的 role（若后端提供）；否则 /user/{id} 拿 user.role
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
    // 演示模式下：后端可能返回 code=200 但 data=null（账号密码错误等）。
    // 优先使用后端 message，避免误导为“接口数据不完整”。
    const message = loginRes?.message || '账号或密码错误'
    throw new Error(message)
  }

  // 2) 取角色：优先使用 /auth/login 返回 role（后端已提供时可少一次请求）
  let role = normalizeRole(auth?.role)

  // 回退：仍从 /user/{id} 获取 role（兼容旧后端/旧数据）
  if (!role) {
    const userRes = await getUser(auth.userId)
    const user = userRes?.data
    role = normalizeRole(user?.role)
  }
  if (!role) {
    throw new Error('用户角色不被前端识别')
  }

  // 3) 最小持久化：token + 宏 role
  localStorage.setItem('token', auth.token)
  localStorage.setItem('role', role)
  localStorage.setItem('userId', String(auth.userId))

  // 4) 宏角色 -> 入口路由（唯一分发点）
  const targetPath = ROLE_ROUTE_MAP[role]
  if (!targetPath) {
    localStorage.removeItem('token')
    localStorage.removeItem('role')
    localStorage.removeItem('userId')
    throw new Error('入口路由未配置')
  }

  if (router?.replace) {
    await router.replace(targetPath)
  }

  return { token: auth.token, userId: auth.userId, role }
}
