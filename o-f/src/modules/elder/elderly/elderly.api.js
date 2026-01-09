import http from '../../../shared/http.js'

// Elderly API 接口
// 严格按照后端接口文档生成，一个接口对应一个方法

/**
 * 创建老人信息
 * POST /elder/elderly/
 * @param {import('./elderly.types.js').CreateElderlyRequest} elderly
 * @returns {Promise<import('../../../shared/types.js').ApiResponse<import('./elderly.types.js').Elderly>>}
 */
export function createElderly(elderly) {
  return http.post('/elder/elderly/', elderly)
}

/**
 * 获取老人详情
 * GET /elder/elderly/{id}
 * @param {number} id - 老人ID
 * @returns {Promise<import('../../../shared/types.js').ApiResponse<import('./elderly.types.js').Elderly>>}
 */
export function getElderly(id) {
  return http.get(`/elder/elderly/${id}`)
}

/**
 * 获取所有老人
 * GET /elder/elderly/
 * @returns {Promise<import('../../../shared/types.js').ApiResponse<import('./elderly.types.js').Elderly[]>>}
 */
export function getAllElderly() {
  return http.get('/elder/elderly/')
}

/**
 * 按名字搜索老人
 * GET /elder/elderly/search/{name}
 * @param {string} name - 老人姓名
 * @returns {Promise<import('../../../shared/types.js').ApiResponse<import('./elderly.types.js').Elderly[]>>}
 */
export function searchElderlyByName(name) {
  return http.get(`/elder/elderly/search/${encodeURIComponent(name)}`)
}

/**
 * 更新老人信息
 * PUT /elder/elderly/{id}
 * @param {number} id - 老人ID
 * @param {import('./elderly.types.js').UpdateElderlyRequest} elderly - 老人信息
 * @returns {Promise<import('../../../shared/types.js').ApiResponse<import('./elderly.types.js').Elderly>>}
 */
export function updateElderly(id, elderly) {
  return http.put(`/elder/elderly/${id}`, elderly)
}

/**
 * 删除老人信息
 * DELETE /elder/elderly/{id}
 * @param {number} id - 老人ID
 * @returns {Promise<import('../../../shared/types.js').ApiResponse>}
 */
export function deleteElderly(id) {
  return http.delete(`/elder/elderly/${id}`)
}

/**
 * 激活老人
 * PUT /elder/elderly/{id}/activate
 * @param {number} id - 老人ID
 * @returns {Promise<import('../../../shared/types.js').ApiResponse<import('./elderly.types.js').Elderly>>}
 */
export function activateElderly(id) {
  return http.put(`/elder/elderly/${id}/activate`)
}

/**
 * 停用老人
 * PUT /elder/elderly/{id}/deactivate
 * @param {number} id - 老人ID
 * @returns {Promise<import('../../../shared/types.js').ApiResponse<import('./elderly.types.js').Elderly>>}
 */
export function deactivateElderly(id) {
  return http.put(`/elder/elderly/${id}/deactivate`)
}