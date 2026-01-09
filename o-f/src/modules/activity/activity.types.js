// Activity 模块类型定义（JavaScript版本）

/**
 * Activity 活动实体
 * @typedef {Object} Activity
 * @property {number} id - 活动ID
 * @property {string} name - 活动名称
 * @property {string} category - 活动分类
 * @property {string} description - 活动描述
 * @property {string} location - 活动地点
 * @property {string} startTime - 开始时间
 * @property {string} endTime - 结束时间
 * @property {number} participants - 参与人数
 * @property {number} organizerId - 组织者ID
 * @property {string} status - 活动状态
 * @property {boolean} isActive - 是否激活
 */

/**
 * 创建活动请求
 * @typedef {Object} CreateActivityRequest
 * @property {string} name - 活动名称
 * @property {string} category - 活动分类
 * @property {string} description - 活动描述
 * @property {string} location - 活动地点
 * @property {string} startTime - 开始时间
 * @property {string} endTime - 结束时间
 * @property {number} participants - 参与人数
 * @property {number} organizerId - 组织者ID
 * @property {string} status - 活动状态
 */

/**
 * 更新活动信息请求
 * @typedef {Object} UpdateActivityRequest
 * @property {string} [name] - 活动名称
 * @property {string} [category] - 活动分类
 * @property {string} [description] - 活动描述
 * @property {string} [location] - 活动地点
 * @property {string} [startTime] - 开始时间
 * @property {string} [endTime] - 结束时间
 * @property {number} [participants] - 参与人数
 * @property {number} [organizerId] - 组织者ID
 * @property {string} [status] - 活动状态
 */