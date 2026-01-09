import http from '../../shared/http.js'

// Activity API 接口
// 严格按照后端接口文档生成，一个接口对应一个方法

/**
 * 创建活动
 * POST /activity/
 * @param {import('./activity.types.js').CreateActivityRequest} activity
 * @returns {Promise<import('../../shared/types.js').ApiResponse<import('./activity.types.js').Activity>>}
 */
export function createActivity(activity) {
  return http.post('/activity/', activity)
}

/**
 * 获取活动详情
 * GET /activity/{id}
 * @param {number} id - 活动ID
 * @returns {Promise<import('../../shared/types.js').ApiResponse<import('./activity.types.js').Activity>>}
 */
export function getActivity(id) {
  return http.get(`/activity/${id}`)
}

/**
 * 获取所有活动
 * GET /activity/
 * @returns {Promise<import('../../shared/types.js').ApiResponse<import('./activity.types.js').Activity[]>>}
 */
export function getAllActivities() {
  return http.get('/activity/')
}

/**
 * 按分类查询活动
 * GET /activity/category/{category}
 * @param {string} category - 活动分类
 * @returns {Promise<import('../../shared/types.js').ApiResponse<import('./activity.types.js').Activity[]>>}
 */
export function getActivitiesByCategory(category) {
  return http.get(`/activity/category/${encodeURIComponent(category)}`)
}

/**
 * 按状态查询活动
 * GET /activity/status/{status}
 * @param {string} status - 活动状态
 * @returns {Promise<import('../../shared/types.js').ApiResponse<import('./activity.types.js').Activity[]>>}
 */
export function getActivitiesByStatus(status) {
  return http.get(`/activity/status/${encodeURIComponent(status)}`)
}

/**
 * 更新活动信息
 * PUT /activity/{id}
 * @param {number} id - 活动ID
 * @param {import('./activity.types.js').UpdateActivityRequest} activity - 活动信息
 * @returns {Promise<import('../../shared/types.js').ApiResponse<import('./activity.types.js').Activity>>}
 */
export function updateActivity(id, activity) {
  return http.put(`/activity/${id}`, activity)
}

/**
 * 删除活动
 * DELETE /activity/{id}
 * @param {number} id - 活动ID
 * @returns {Promise<import('../../shared/types.js').ApiResponse>}
 */
export function deleteActivity(id) {
  return http.delete(`/activity/${id}`)
}

/**
 * 激活活动
 * PUT /activity/{id}/activate
 * @param {number} id - 活动ID
 * @returns {Promise<import('../../shared/types.js').ApiResponse<import('./activity.types.js').Activity>>}
 */
export function activateActivity(id) {
  return http.put(`/activity/${id}/activate`)
}

/**
 * 停用活动
 * PUT /activity/{id}/deactivate
 * @param {number} id - 活动ID
 * @returns {Promise<import('../../shared/types.js').ApiResponse<import('./activity.types.js').Activity>>}
 */
export function deactivateActivity(id) {
  return http.put(`/activity/${id}/deactivate`)
}