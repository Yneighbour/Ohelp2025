// 角色 -> 登录后默认入口路由（仅负责“去哪里”，不负责“能做什么”）
export const ROLE_ROUTE_MAP = {
  admin: '/dashboard',
  manager: '/elder',
  staff: '/emergency',

  // 兼容可能存在的角色命名差异
  worker: '/emergency',
  doctor: '/health',
  user: '/dashboard'
}
