// 角色 -> 可访问的路由前缀（用于前端差异化展示与软性导航限制）
// 注意：这不是后端权限控制；真正的权限仍应由后端校验。

export const ROLE_ALLOWED_ROUTE_PREFIXES = {
  // 管理员：全量
  admin: [
    '/dashboard',
    '/auth',
    '/user',
    '/elder',
    '/activity',
    '/emergency',
    '/health',
    '/serviceorder',
    '/worker',
    '/file'
  ],

  // 经理：偏运营/档案/活动
  manager: ['/elder', '/activity', '/health', '/serviceorder', '/file'],

  // 一线员工：偏求助与执行
  staff: ['/emergency', '/serviceorder', '/health', '/file'],

  // 兼容角色命名
  worker: ['/emergency', '/serviceorder', '/health', '/file'],
  doctor: ['/health', '/emergency'],

  // 个人端/家属：只看概览与健康（可按需要再放开）
  user: ['/dashboard', '/health', '/activity'],
  relative: ['/dashboard', '/health', '/activity']
}

export function isPathAllowed(role, path) {
  if (!role) return true
  if (!path) return true

  // 登录页放行
  if (path === '/login') return true

  const prefixes = ROLE_ALLOWED_ROUTE_PREFIXES[role]
  // 未知角色：不限制（避免锁死）
  if (!Array.isArray(prefixes)) return true

  return prefixes.some(prefix => path === prefix || path.startsWith(prefix + '/'))
}
