/**
 * 图标系统统一配置
 * 为智慧养老服务管理系统提供统一的图标管理
 */

// Element Plus 图标导入（按需导入以减小打包体积）
export const IconComponents = {
  // 导航相关
  HomeFilled: () => import('@element-plus/icons-vue/dist/es/HomeFilled'),
  House: () => import('@element-plus/icons-vue/dist/es/House'),
  DataAnalysis: () => import('@element-plus/icons-vue/dist/es/DataAnalysis'),

  // 用户相关
  User: () => import('@element-plus/icons-vue/dist/es/User'),
  UserFilled: () => import('@element-plus/icons-vue/dist/es/UserFilled'),
  Avatar: () => import('@element-plus/icons-vue/dist/es/Avatar'),

  // 文档和服务相关
  DocumentAdd: () => import('@element-plus/icons-vue/dist/es/DocumentAdd'),
  Document: () => import('@element-plus/icons-vue/dist/es/Document'),
  Service: () => import('@element-plus/icons-vue/dist/es/Service'),

  // 数据展示相关
  TrendCharts: () => import('@element-plus/icons-vue/dist/es/TrendCharts'),
  PieChart: () => import('@element-plus/icons-vue/dist/es/PieChart'),
  Histogram: () => import('@element-plus/icons-vue/dist/es/Histogram'),

  // 操作相关
  Plus: () => import('@element-plus/icons-vue/dist/es/Plus'),
  Edit: () => import('@element-plus/icons-vue/dist/es/Edit'),
  Delete: () => import('@element-plus/icons-vue/dist/es/Delete'),
  Search: () => import('@element-plus/icons-vue/dist/es/Search'),
  Refresh: () => import('@element-plus/icons-vue/dist/es/Refresh'),

  // 状态指示
  Check: () => import('@element-plus/icons-vue/dist/es/Check'),
  Close: () => import('@element-plus/icons-vue/dist/es/Close'),
  Warning: () => import('@element-plus/icons-vue/dist/es/Warning'),
  Error: () => import('@element-plus/icons-vue/dist/es/Error'),
  Info: () => import('@element-plus/icons-vue/dist/es/Info'),
  Success: () => import('@element-plus/icons-vue/dist/es/Success'),

  // 方向和趋势
  Top: () => import('@element-plus/icons-vue/dist/es/Top'),
  Bottom: () => import('@element-plus/icons-vue/dist/es/Bottom'),
  Left: () => import('@element-plus/icons-vue/dist/es/Left'),
  Right: () => import('@element-plus/icons-vue/dist/es/Right'),
  Minus: () => import('@element-plus/icons-vue/dist/es/Minus'),

  // 模式切换
  Sunny: () => import('@element-plus/icons-vue/dist/es/Sunny'),
  Moon: () => import('@element-plus/icons-vue/dist/es/Moon'),
  Lightning: () => import('@element-plus/icons-vue/dist/es/Lightning'),

  // 系统相关
  Setting: () => import('@element-plus/icons-vue/dist/es/Setting'),
  Management: () => import('@element-plus/icons-vue/dist/es/Management'),
  Monitor: () => import('@element-plus/icons-vue/dist/es/Monitor'),

  // 医疗健康相关
  FirstAidKit: () => import('@element-plus/icons-vue/dist/es/FirstAidKit'),
  MedicineBox: () => import('@element-plus/icons-vue/dist/es/MedicineBox'),
  Heart: () => import('@element-plus/icons-vue/dist/es/Heart'),

  // 养老服务相关
  Elderly: () => import('@element-plus/icons-vue/dist/es/Elderly'),
  Phone: () => import('@element-plus/icons-vue/dist/es/Phone'),
  Bell: () => import('@element-plus/icons-vue/dist/es/Bell'),
  Clock: () => import('@element-plus/icons-vue/dist/es/Clock'),
  Calendar: () => import('@element-plus/icons-vue/dist/es/Calendar')
}

// 图标映射配置 - 用于业务场景的图标选择
export const IconMap = {
  // 页面导航
  dashboard: 'DataAnalysis',
  home: 'HomeFilled',
  elderly: 'User',
  requests: 'DocumentAdd',
  services: 'Service',
  settings: 'Setting',

  // 数据统计
  totalElderly: 'User',
  todayRequests: 'DocumentAdd',
  completionRate: 'TrendCharts',
  activeServices: 'Service',

  // 用户操作
  add: 'Plus',
  edit: 'Edit',
  delete: 'Delete',
  search: 'Search',
  refresh: 'Refresh',
  save: 'Check',

  // 状态指示
  success: 'Success',
  warning: 'Warning',
  error: 'Error',
  info: 'Info',

  // 趋势指示
  up: 'Top',
  down: 'Bottom',
  stable: 'Minus',

  // 模式切换
  normal: 'Sunny',
  elder: 'Moon',
  voice: 'Lightning',

  // 医疗健康
  health: 'Heart',
  medicine: 'MedicineBox',
  emergency: 'FirstAidKit',

  // 养老服务
  call: 'Phone',
  notification: 'Bell',
  schedule: 'Calendar',
  time: 'Clock'
}

// 图标大小配置
export const IconSizes = {
  small: 16,
  medium: 20,
  large: 24,
  xl: 32,
  xxl: 48
}

// 图标颜色配置
export const IconColors = {
  primary: 'var(--primary)',
  success: 'var(--success)',
  warning: 'var(--warning)',
  error: 'var(--error)',
  info: 'var(--info)',
  muted: 'var(--text-muted)',
  inverse: 'var(--text-inverse)'
}

/**
 * 获取图标组件
 * @param {string} iconName - 图标名称
 * @returns {Promise} 图标组件
 */
export const getIconComponent = async (iconName) => {
  if (IconComponents[iconName]) {
    const iconModule = await IconComponents[iconName]()
    return iconModule.default
  }
  return null
}

/**
 * 获取业务图标
 * @param {string} businessKey - 业务键名
 * @returns {string} 图标名称
 */
export const getBusinessIcon = (businessKey) => {
  return IconMap[businessKey] || 'Info'
}

/**
 * 创建图标配置对象
 * @param {string} name - 图标名称
 * @param {number} size - 图标大小
 * @param {string} color - 图标颜色
 * @returns {object} 图标配置
 */
export const createIconConfig = (name, size = 'medium', color = 'primary') => {
  return {
    name,
    size: typeof size === 'string' ? IconSizes[size] : size,
    color: IconColors[color] || color
  }
}

// 默认导出所有配置
export default {
  IconComponents,
  IconMap,
  IconSizes,
  IconColors,
  getIconComponent,
  getBusinessIcon,
  createIconConfig
}
