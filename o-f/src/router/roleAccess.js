// 注意：后端当前没有 RBAC/权限校验。
// 前端不实现任何“能不能访问/能不能操作”的权限语义。
// 该文件仅作为历史兼容占位（如未来需要可再演进）。

export function isPathAllowed() {
  return true
}
