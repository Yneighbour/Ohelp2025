import http from '../../../shared/http.js'

// Relative API 接口
// 严格按照后端接口文档生成，一个接口对应一个方法

/**
 * 创建亲属信息
 * POST /elder/relative/
 * @param {import('./relative.types.js').CreateRelativeRequest} relative
 * @returns {Promise<import('../../../shared/types.js').ApiResponse<import('./relative.types.js').Relative>>}
 */
export function createRelative(relative) {
  return http.post('/elder/relative/', relative)
}

/**
 * 获取亲属详情
 * GET /elder/relative/{id}
 * @param {number} id - 亲属ID
 * @returns {Promise<import('../../../shared/types.js').ApiResponse<import('./relative.types.js').Relative>>}
 */
export function getRelative(id) {
  return http.get(`/elder/relative/${id}`)
}

/**
 * 获取老人的亲属列表
 * GET /elder/relative/elderly/{elderlyId}
 * @param {number} elderlyId - 老人ID
 * @returns {Promise<import('../../../shared/types.js').ApiResponse<import('./relative.types.js').Relative[]>>}
 */
export function getRelativesByElderlyId(elderlyId) {
  return http.get(`/elder/relative/elderly/${elderlyId}`)
}

/**
 * 获取所有亲属
 * GET /elder/relative/
 * @returns {Promise<import('../../../shared/types.js').ApiResponse<import('./relative.types.js').Relative[]>>}
 */
export function getAllRelatives() {
  return http.get('/elder/relative/')
}

/**
 * 更新亲属信息
 * PUT /elder/relative/{id}
 * @param {number} id - 亲属ID
 * @param {import('./relative.types.js').UpdateRelativeRequest} relative - 亲属信息
 * @returns {Promise<import('../../../shared/types.js').ApiResponse<import('./relative.types.js').Relative>>}
 */
export function updateRelative(id, relative) {
  return http.put(`/elder/relative/${id}`, relative)
}

/**
 * 删除亲属信息
 * DELETE /elder/relative/{id}
 * @param {number} id - 亲属ID
 * @returns {Promise<import('../../../shared/types.js').ApiResponse>}
 */
export function deleteRelative(id) {
  return http.delete(`/elder/relative/${id}`)
}

/**
 * 激活亲属
 * PUT /elder/relative/{id}/activate
 * @param {number} id - 亲属ID
 * @returns {Promise<import('../../../shared/types.js').ApiResponse<import('./relative.types.js').Relative>>}
 */
export function activateRelative(id) {
  return http.put(`/elder/relative/${id}/activate`)
}

/**
 * 停用亲属
 * PUT /elder/relative/{id}/deactivate
 * @param {number} id - 亲属ID
 * @returns {Promise<import('../../../shared/types.js').ApiResponse<import('./relative.types.js').Relative>>}
 */
export function deactivateRelative(id) {
  return http.put(`/elder/relative/${id}/deactivate`)
}