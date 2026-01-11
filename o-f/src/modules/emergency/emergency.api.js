import http from '../../shared/http.js'

// Emergency API 接口
// 严格按照后端接口文档生成，一个接口对应一个方法

/**
 * 创建求助请求
 * POST /emergency/
 * @param {import('./emergency.types.js').CreateEmergencyRequest} emergency
 * @returns {Promise<import('../../shared/types.js').ApiResponse<import('./emergency.types.js').Emergency>>}
 */
export function createEmergency(emergency) {
  return http.post('/emergency/', emergency)
}

/**
 * 获取求助详情
 * GET /emergency/{id}
 * @param {number} id - 求助ID
 * @returns {Promise<import('../../shared/types.js').ApiResponse<import('./emergency.types.js').Emergency>>}
 */
export function getEmergency(id) {
  return http.get(`/emergency/${id}`)
}

/**
 * 获取所有求助
 * GET /emergency/
 * @returns {Promise<import('../../shared/types.js').ApiResponse<import('./emergency.types.js').Emergency[]>>}
 */
export function getAllEmergencies() {
  return http.get('/emergency/')
}

/**
 * 获取老人的求助记录
 * GET /emergency/elderly/{elderlyId}
 * @param {number} elderlyId - 老人ID
 * @returns {Promise<import('../../shared/types.js').ApiResponse<import('./emergency.types.js').Emergency[]>>}
 */
export function getEmergenciesByElderlyId(elderlyId) {
  return http.get(`/emergency/elderly/${elderlyId}`)
}

/**
 * 按状态查询求助
 * GET /emergency/status/{status}
 * @param {string} status - 处理状态
 * @returns {Promise<import('../../shared/types.js').ApiResponse<import('./emergency.types.js').Emergency[]>>}
 */
export function getEmergenciesByStatus(status) {
  return http.get(`/emergency/status/${encodeURIComponent(status)}`)
}

/**
 * 按优先级查询求助
 * GET /emergency/priority/{priority}
 * @param {string} priority - 优先级
 * @returns {Promise<import('../../shared/types.js').ApiResponse<import('./emergency.types.js').Emergency[]>>}
 */
export function getEmergenciesByPriority(priority) {
  return http.get(`/emergency/priority/${encodeURIComponent(priority)}`)
}

/**
 * 更新求助信息
 * PUT /emergency/{id}
 * @param {number} id - 求助ID
 * @param {import('./emergency.types.js').UpdateEmergencyRequest} emergency - 求助信息
 * @returns {Promise<import('../../shared/types.js').ApiResponse<import('./emergency.types.js').Emergency>>}
 */
export function updateEmergency(id, emergency) {
  return http.put(`/emergency/${id}`, emergency)
}

/**
 * 删除求助记录
 * DELETE /emergency/{id}
 * @param {number} id - 求助ID
 * @returns {Promise<import('../../shared/types.js').ApiResponse>}
 */
export function deleteEmergency(id) {
  return http.delete(`/emergency/${id}`)
}

/**
 * 响应求助请求
 * PUT /emergency/{id}/respond
 * @param {number} id - 求助ID
 * @param {Object} params - 响应参数
 * @param {number} params.responderId - 响应人ID
 * @returns {Promise<import('../../shared/types.js').ApiResponse<import('./emergency.types.js').Emergency>>}
 */
export function respondToEmergency(id, params = {}) {
  // 后端是 @RequestParam responderId
  return http.put(`/emergency/${id}/respond`, null, { params })
}

/**
 * 解决求助请求
 * PUT /emergency/{id}/resolve
 * @param {number} id - 求助ID
 * @returns {Promise<import('../../shared/types.js').ApiResponse<import('./emergency.types.js').Emergency>>}
 */
export function resolveEmergency(id) {
  return http.put(`/emergency/${id}/resolve`)
}