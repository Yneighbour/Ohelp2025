// Health 模块类型定义（JavaScript版本）

/**
 * HealthRecord 健康记录实体
 * @typedef {Object} HealthRecord
 * @property {number} id - 记录ID
 * @property {number} elderlyId - 老人ID
 * @property {string} recordDate - 记录日期
 * @property {string} bloodPressure - 血压
 * @property {string} heartRate - 心率
 * @property {string} temperature - 体温
 * @property {string} weight - 体重
 * @property {string} glucoseLevel - 血糖水平
 * @property {string} notes - 备注
 * @property {number} doctorId - 医生ID
 * @property {boolean} isActive - 是否激活
 */

/**
 * 创建健康记录请求
 * @typedef {Object} CreateHealthRecordRequest
 * @property {number} elderlyId - 老人ID
 * @property {string} recordDate - 记录日期
 * @property {string} bloodPressure - 血压
 * @property {string} heartRate - 心率
 * @property {string} temperature - 体温
 * @property {string} weight - 体重
 * @property {string} glucoseLevel - 血糖水平
 * @property {string} notes - 备注
 * @property {number} doctorId - 医生ID
 */

/**
 * 更新健康记录请求
 * @typedef {Object} UpdateHealthRecordRequest
 * @property {string} [recordDate] - 记录日期
 * @property {string} [bloodPressure] - 血压
 * @property {string} [heartRate] - 心率
 * @property {string} [temperature] - 体温
 * @property {string} [weight] - 体重
 * @property {string} [glucoseLevel] - 血糖水平
 * @property {string} [notes] - 备注
 * @property {number} [doctorId] - 医生ID
 */