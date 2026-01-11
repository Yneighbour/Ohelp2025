import http from '../../shared/http.js'

// Auth API 接口
// 严格按照后端接口文档生成，一个接口对应一个方法

/**
 * 用户登录
 * POST /auth/login
 * @param {import('./auth.types.js').LoginRequest} loginRequest
 * @returns {Promise<import('../../shared/types.js').ApiResponse<import('./auth.types.js').LoginResponse>>}
 */
export function login(loginRequest) {
  return http.post('/auth/login', loginRequest)
}

/**
 * 用户登出
 * POST /auth/logout
 * @param {Object} params - 登出参数
 * @returns {Promise<import('../../shared/types.js').ApiResponse>}
 */
export function logout(params = {}) {
  // 后端是 @RequestParam authId
  return http.post('/auth/logout', null, { params })
}

/**
 * 用户注册
 * POST /auth/register
 * @param {import('./auth.types.js').RegisterRequest} registerRequest
 * @returns {Promise<import('../../shared/types.js').ApiResponse>}
 */
export function register(registerRequest) {
  return http.post('/auth/register', registerRequest)
}

/**
 * 验证令牌有效性
 * GET /auth/validate/{token}
 * @param {string} token - 认证令牌
 * @returns {Promise<import('../../shared/types.js').ApiResponse<import('./auth.types.js').ValidateTokenResponse>>}
 */
export function validateToken(token) {
  return http.get(`/auth/validate/${token}`)
}

/**
 * 获取认证记录
 * GET /auth/{id}
 * @param {number} id - 认证记录ID
 * @returns {Promise<import('../../shared/types.js').ApiResponse<import('./auth.types.js').Auth>>}
 */
export function getAuth(id) {
  return http.get(`/auth/${id}`)
}

/**
 * 获取所有认证记录
 * GET /auth/
 * @returns {Promise<import('../../shared/types.js').ApiResponse<import('./auth.types.js').Auth[]>>}
 */
export function getAllAuths() {
  return http.get('/auth/')
}

/**
 * 删除认证记录
 * DELETE /auth/{id}
 * @param {number} id - 认证记录ID
 * @returns {Promise<import('../../shared/types.js').ApiResponse>}
 */
export function deleteAuth(id) {
  return http.delete(`/auth/${id}`)
}