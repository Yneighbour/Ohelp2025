// 宏角色 -> 登录后默认入口路由（仅负责“去哪里”，不负责“能做什么”）
// 约束：localStorage['role'] 只允许保存 'ADMIN' | 'WORKER' | 'USER'
export const ROLE_ROUTE_MAP = {
  ADMIN: '/admin/dashboard',
  WORKER: '/worker/tasks',
  USER: '/app'
}

