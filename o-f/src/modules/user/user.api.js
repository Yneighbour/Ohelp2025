import http from '../../shared/http.js'

// User API 接口
// 严格按照后端接口文档生成，一个接口对应一个方法

/**
 * 创建用户
 * POST /user/
 * @param {import('./user.types.js').CreateUserRequest} user
 * @returns {Promise<import('../../shared/types.js').ApiResponse<import('./user.types.js').User>>}
 */
export function createUser(user) {
  return http.post('/user/', user)
}

/**
 * 获取用户详情
 * GET /user/{id}
 * @param {number} id - 用户ID
 * @returns {Promise<import('../../shared/types.js').ApiResponse<import('./user.types.js').User>>}
 */
export function getUser(id) {
  return http.get(`/user/${id}`)
}

/**
 * 按邮箱查询用户
 * GET /user/email/{email}
 * @param {string} email - 用户邮箱
 * @returns {Promise<import('../../shared/types.js').ApiResponse<import('./user.types.js').User>>}
 */
export function getUserByEmail(email) {
  return http.get(`/user/email/${email}`)
}

/**
 * 按电话查询用户
 * GET /user/phone/{phone}
 * @param {string} phone - 用户电话
 * @returns {Promise<import('../../shared/types.js').ApiResponse<import('./user.types.js').User>>}
 */
export function getUserByPhone(phone) {
  return http.get(`/user/phone/${phone}`)
}

/**
 * 获取所有用户
 * GET /user/
 * @returns {Promise<import('../../shared/types.js').ApiResponse<import('./user.types.js').User[]>>}
 */
export function getAllUsers() {
  return http.get('/user/')
}

/**
 * 更新用户信息
 * PUT /user/{id}
 * @param {number} id - 用户ID
 * @param {import('./user.types.js').UpdateUserRequest} user - 用户信息
 * @returns {Promise<import('../../shared/types.js').ApiResponse<import('./user.types.js').User>>}
 */
export function updateUser(id, user) {
  return http.put(`/user/${id}`, user)
}

/**
 * 删除用户
 * DELETE /user/{id}
 * @param {number} id - 用户ID
 * @returns {Promise<import('../../shared/types.js').ApiResponse>}
 */
export function deleteUser(id) {
  return http.delete(`/user/${id}`)
}

/**
 * 激活用户
 * PUT /user/{id}/activate
 * @param {number} id - 用户ID
 * @returns {Promise<import('../../shared/types.js').ApiResponse<import('./user.types.js').User>>}
 */
export function activateUser(id) {
  return http.put(`/user/${id}/activate`)
}

/**
 * 停用用户
 * PUT /user/{id}/deactivate
 * @param {number} id - 用户ID
 * @returns {Promise<import('../../shared/types.js').ApiResponse<import('./user.types.js').User>>}
 */
export function deactivateUser(id) {
  return http.put(`/user/${id}/deactivate`)
}