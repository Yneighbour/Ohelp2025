/**
 * 后端 User.role -> 前端宏角色归一化
 * @param {string | null | undefined} backendRole
 * @returns {'ADMIN' | 'WORKER' | 'USER' | null}
 */
export function normalizeRole(backendRole) {
  if (!backendRole) return null

  switch (String(backendRole)) {
    case 'admin':
      return 'ADMIN'
    case 'manager':
    case 'staff':
      return 'WORKER'
    case 'user':
      return 'USER'
    default:
      return null
  }
}
