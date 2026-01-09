// User 模块类型定义（JavaScript版本）

/**
 * User 实体
 * @typedef {Object} User
 * @property {number} id - 用户ID
 * @property {string} name - 姓名
 * @property {string} email - 邮箱（唯一）
 * @property {string} phone - 电话（唯一）
 * @property {string} role - 角色
 * @property {string} avatarUrl - 头像URL
 * @property {boolean} isActive - 是否激活
 */

/**
 * 创建用户请求
 * @typedef {Object} CreateUserRequest
 * @property {string} name - 姓名
 * @property {string} email - 邮箱
 * @property {string} phone - 电话
 * @property {string} role - 角色
 * @property {string} [avatarUrl] - 头像URL
 */

/**
 * 更新用户信息请求
 * @typedef {Object} UpdateUserRequest
 * @property {string} [name] - 姓名
 * @property {string} [email] - 邮箱
 * @property {string} [phone] - 电话
 * @property {string} [role] - 角色
 * @property {string} [avatarUrl] - 头像URL
 */