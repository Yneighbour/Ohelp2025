// Elderly 模块类型定义（JavaScript版本）

/**
 * Elderly 老人实体
 * @typedef {Object} Elderly
 * @property {number} id - 老人ID
 * @property {string} name - 姓名
 * @property {number} age - 年龄
 * @property {string} dateOfBirth - 出生日期
 * @property {string} gender - 性别
 * @property {string} phoneNumber - 电话
 * @property {string} healthStatus - 健康状态
 * @property {string} medicalHistory - 医疗历史
 * @property {string} address - 地址
 * @property {string} contactPerson - 联系人
 * @property {string} contactPhone - 联系电话
 * @property {boolean} isActive - 是否激活
 */

/**
 * 创建老人信息请求
 * @typedef {Object} CreateElderlyRequest
 * @property {string} name - 姓名
 * @property {number} age - 年龄
 * @property {string} dateOfBirth - 出生日期
 * @property {string} gender - 性别
 * @property {string} phoneNumber - 电话
 * @property {string} healthStatus - 健康状态
 * @property {string} medicalHistory - 医疗历史
 * @property {string} address - 地址
 * @property {string} contactPerson - 联系人
 * @property {string} contactPhone - 联系电话
 */

/**
 * 更新老人信息请求
 * @typedef {Object} UpdateElderlyRequest
 * @property {string} [name] - 姓名
 * @property {number} [age] - 年龄
 * @property {string} [dateOfBirth] - 出生日期
 * @property {string} [gender] - 性别
 * @property {string} [phoneNumber] - 电话
 * @property {string} [healthStatus] - 健康状态
 * @property {string} [medicalHistory] - 医疗历史
 * @property {string} [address] - 地址
 * @property {string} [contactPerson] - 联系人
 * @property {string} [contactPhone] - 联系电话
 */