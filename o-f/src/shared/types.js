// 通用API响应类型（JavaScript版本）
export const ApiResponse = {
  // 这是一个类型定义的占位符，实际使用中通过注释说明
}

/**
 * API响应格式
 * @typedef {Object} ApiResponse
 * @property {number} code - 响应码
 * @property {string} message - 响应消息
 * @property {any} data - 响应数据
 */

/**
 * 分页响应格式
 * @typedef {ApiResponse} PageResponse
 * @property {Object} data
 * @property {Array} data.content - 内容数组
 * @property {number} data.totalElements - 总元素数
 * @property {number} data.totalPages - 总页数
 * @property {number} data.size - 页面大小
 * @property {number} data.number - 当前页码
 * @property {boolean} data.first - 是否第一页
 * @property {boolean} data.last - 是否最后一页
 */

/**
 * 基础实体接口
 * @typedef {Object} BaseEntity
 * @property {number} id - ID
 * @property {boolean} isActive - 是否激活
 */

/**
 * 表单状态接口
 * @typedef {Object} FormState
 * @property {boolean} loading - 加载状态
 * @property {any|null} data - 数据
 * @property {string|null} error - 错误信息
 */

/**
 * 列表状态接口
 * @typedef {Object} ListState
 * @property {boolean} loading - 加载状态
 * @property {Array} data - 数据数组
 * @property {string|null} error - 错误信息
 * @property {Object} [pagination] - 分页信息
 * @property {number} pagination.page - 当前页
 * @property {number} pagination.size - 页面大小
 * @property {number} pagination.total - 总数量
 */