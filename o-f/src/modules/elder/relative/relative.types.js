// Relative 模块类型定义（JavaScript版本）

/**
 * Relative 亲属实体
 * @typedef {Object} Relative
 * @property {number} id - 亲属ID
 * @property {number} elderlyId - 关联老人ID
 * @property {string} name - 姓名
 * @property {string} phone - 电话
 * @property {string} relationship - 关系
 * @property {string} email - 邮箱
 * @property {boolean} isPrimaryContact - 是否主要联系人
 * @property {boolean} isActive - 是否激活
 */

/**
 * 创建亲属信息请求
 * @typedef {Object} CreateRelativeRequest
 * @property {number} elderlyId - 关联老人ID
 * @property {string} name - 姓名
 * @property {string} phone - 电话
 * @property {string} relationship - 关系
 * @property {string} email - 邮箱
 * @property {boolean} isPrimaryContact - 是否主要联系人
 */

/**
 * 更新亲属信息请求
 * @typedef {Object} UpdateRelativeRequest
 * @property {string} [name] - 姓名
 * @property {string} [phone] - 电话
 * @property {string} [relationship] - 关系
 * @property {string} [email] - 邮箱
 * @property {boolean} [isPrimaryContact] - 是否主要联系人
 */