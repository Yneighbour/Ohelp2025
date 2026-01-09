// Auth 模块类型定义（JavaScript版本）

/**
 * Auth 实体
 * @typedef {Object} Auth
 * @property {number} id - 认证记录ID
 * @property {string} username - 用户名
 * @property {string} password - 密码
 * @property {string} token - 认证令牌
 * @property {number} userId - 关联用户ID
 * @property {string} loginTime - 登录时间
 * @property {string|null} logoutTime - 登出时间
 * @property {boolean} isActive - 是否激活
 */

/**
 * 登录请求
 * @typedef {Object} LoginRequest
 * @property {string} username - 用户名
 * @property {string} password - 密码
 */

/**
 * 注册请求
 * @typedef {Object} RegisterRequest
 * @property {string} username - 用户名
 * @property {string} password - 密码
 * @property {string} email - 邮箱
 * @property {string} phone - 电话
 * @property {string} role - 角色
 */

/**
 * 登录响应
 * @typedef {Object} LoginResponse
 * @property {string} token - 认证令牌
 * @property {number} userId - 用户ID
 * @property {string} username - 用户名
 */

/**
 * 验证令牌响应
 * @typedef {Object} ValidateTokenResponse
 * @property {boolean} valid - 是否有效
 * @property {number|null} userId - 用户ID
 * @property {string|null} username - 用户名
 */