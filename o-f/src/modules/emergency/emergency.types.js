// Emergency 模块类型定义（JavaScript版本）

/**
 * Emergency 求助实体
 * @typedef {Object} Emergency
 * @property {number} id - 求助ID
 * @property {number} elderlyId - 老人ID
 * @property {string} type - 求助类型
 * @property {string} description - 求助描述
 * @property {string} location - 求助位置
 * @property {string} contactPhone - 联系电话
 * @property {string} status - 处理状态（pending/responded/resolved）
 * @property {number} responderId - 响应人ID
 * @property {string} responseTime - 响应时间
 * @property {string} resolvedTime - 解决时间
 * @property {string} priority - 优先级（high/medium/low）
 * @property {boolean} isActive - 是否激活
 */

/**
 * 创建求助请求
 * @typedef {Object} CreateEmergencyRequest
 * @property {number} elderlyId - 老人ID
 * @property {string} type - 求助类型
 * @property {string} description - 求助描述
 * @property {string} location - 求助位置
 * @property {string} contactPhone - 联系电话
 * @property {string} priority - 优先级
 */

/**
 * 更新求助信息请求
 * @typedef {Object} UpdateEmergencyRequest
 * @property {string} [type] - 求助类型
 * @property {string} [description] - 求助描述
 * @property {string} [location] - 求助位置
 * @property {string} [contactPhone] - 联系电话
 * @property {string} [priority] - 优先级
 */