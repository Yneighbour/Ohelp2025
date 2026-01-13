// 演示用的后台管理静态数据（来自原静态版 o-f/scripts/data.js）

export const adminUsersData = [
  { id: 1, username: '张三', phone: '13800138001', role: 'admin', roleText: '管理员', status: 'active', statusText: '正常', createTime: '2025-06-15' },
  { id: 2, username: '李四', phone: '13800138002', role: 'operator', roleText: '操作员', status: 'active', statusText: '正常', createTime: '2025-07-20' },
  { id: 3, username: '王五', phone: '13800138003', role: 'user', roleText: '普通用户', status: 'active', statusText: '正常', createTime: '2025-08-10' },
  { id: 4, username: '赵六', phone: '13800138004', role: 'operator', roleText: '操作员', status: 'disabled', statusText: '禁用', createTime: '2025-09-05' },
  { id: 5, username: '钱七', phone: '13800138005', role: 'user', roleText: '普通用户', status: 'active', statusText: '正常', createTime: '2025-10-12' },
];

export const adminRolesData = [
  { id: 1, name: '超级管理员', code: 'super_admin', userCount: 1, permissions: '全部权限', status: 'active', statusText: '启用', createTime: '2025-01-01' },
  { id: 2, name: '管理员', code: 'admin', userCount: 3, permissions: '用户管理、老人管理、服务管理、活动管理', status: 'active', statusText: '启用', createTime: '2025-01-01' },
  { id: 3, name: '操作员', code: 'operator', userCount: 8, permissions: '老人管理、服务管理、活动管理', status: 'active', statusText: '启用', createTime: '2025-02-15' },
  { id: 4, name: '普通用户', code: 'user', userCount: 156, permissions: '查看信息', status: 'active', statusText: '启用', createTime: '2025-01-01' },
];

export const adminPermissionsData = [
  {
    id: 1,
    module: '用户管理',
    permissions: [
      { id: 'user_view', name: '查看用户', enabled: true },
      { id: 'user_add', name: '添加用户', enabled: true },
      { id: 'user_edit', name: '编辑用户', enabled: true },
      { id: 'user_delete', name: '删除用户', enabled: false },
    ],
  },
  {
    id: 2,
    module: '老人管理',
    permissions: [
      { id: 'elder_view', name: '查看档案', enabled: true },
      { id: 'elder_add', name: '添加档案', enabled: true },
      { id: 'elder_edit', name: '编辑档案', enabled: true },
      { id: 'elder_delete', name: '删除档案', enabled: false },
    ],
  },
  {
    id: 3,
    module: '服务管理',
    permissions: [
      { id: 'service_view', name: '查看服务', enabled: true },
      { id: 'service_add', name: '添加服务', enabled: true },
      { id: 'service_edit', name: '编辑服务', enabled: true },
      { id: 'service_toggle', name: '上下架', enabled: true },
    ],
  },
  {
    id: 4,
    module: '活动管理',
    permissions: [
      { id: 'activity_view', name: '查看活动', enabled: true },
      { id: 'activity_add', name: '添加活动', enabled: true },
      { id: 'activity_edit', name: '编辑活动', enabled: true },
      { id: 'activity_cancel', name: '取消活动', enabled: false },
    ],
  },
];

export const adminEldersData = [
  { id: 1, name: '张大爷', age: 72, gender: '男', phone: '13800138888', address: '幸福社区12号楼501', emergencyContact: '张小明', emergencyPhone: '13800138001', healthStatus: 'normal', healthText: '健康', createTime: '2025-03-15' },
  { id: 2, name: '李奶奶', age: 68, gender: '女', phone: '13800138889', address: '幸福社区8号楼302', emergencyContact: '李小红', emergencyPhone: '13800138002', healthStatus: 'warning', healthText: '亚健康', createTime: '2025-04-20' },
  { id: 3, name: '王大爷', age: 75, gender: '男', phone: '13800138890', address: '幸福社区15号楼101', emergencyContact: '王小刚', emergencyPhone: '13800138003', healthStatus: 'normal', healthText: '健康', createTime: '2025-05-10' },
  { id: 4, name: '赵奶奶', age: 70, gender: '女', phone: '13800138891', address: '幸福社区3号楼602', emergencyContact: '赵小丽', emergencyPhone: '13800138004', healthStatus: 'danger', healthText: '需关注', createTime: '2025-06-05' },
  { id: 5, name: '刘大爷', age: 78, gender: '男', phone: '13800138892', address: '幸福社区20号楼403', emergencyContact: '刘小军', emergencyPhone: '13800138005', healthStatus: 'normal', healthText: '健康', createTime: '2025-07-12' },
];

export const adminHealthRecordsData = [
  { id: 1, elderName: '张大爷', recordType: '血压', value: '120/80 mmHg', status: 'normal', statusText: '正常', recordTime: '2026-01-08 08:00', operator: '王护士' },
  { id: 2, elderName: '李奶奶', recordType: '血糖', value: '6.8 mmol/L', status: 'warning', statusText: '偏高', recordTime: '2026-01-08 07:30', operator: '张护士' },
  { id: 3, elderName: '王大爷', recordType: '心率', value: '75 bpm', status: 'normal', statusText: '正常', recordTime: '2026-01-08 09:00', operator: '王护士' },
  { id: 4, elderName: '赵奶奶', recordType: '血压', value: '145/95 mmHg', status: 'danger', statusText: '偏高', recordTime: '2026-01-08 08:30', operator: '李护士' },
  { id: 5, elderName: '刘大爷', recordType: '体温', value: '36.5 ℃', status: 'normal', statusText: '正常', recordTime: '2026-01-08 07:00', operator: '张护士' },
];

export const adminFamilyBindingsData = [
  { id: 1, elderName: '张大爷', familyName: '张小明', relation: '子女', phone: '13800138001', bindTime: '2025-03-15', status: 'bindng', statusText: '已绑定' },
  { id: 2, elderName: '张大爷', familyName: '张小红', relation: '子女', phone: '13800138006', bindTime: '2025-03-16', status: 'bindng', statusText: '已绑定' },
  { id: 3, elderName: '李奶奶', familyName: '李小红', relation: '子女', phone: '13800138002', bindTime: '2025-04-20', status: 'bindng', statusText: '已绑定' },
  { id: 4, elderName: '王大爷', familyName: '王小刚', relation: '子女', phone: '13800138003', bindTime: '2025-05-10', status: 'bindng', statusText: '已绑定' },
  { id: 5, elderName: '赵奶奶', familyName: '赵小丽', relation: '子女', phone: '13800138004', bindTime: '2025-06-05', status: 'pending', statusText: '待确认' },
];

export const adminServicesData = [
  { id: 1, name: '日常照料服务', category: 'life', categoryText: '生活照料', price: '50元/次', status: 'online', statusText: '上架', description: '提供日常起居照料服务', createTime: '2025-01-10' },
  { id: 2, name: '上门医疗服务', category: 'medical', categoryText: '医疗护理', price: '100元/次', status: 'online', statusText: '上架', description: '专业医护人员上门服务', createTime: '2025-01-15' },
  { id: 3, name: '心理咨询服务', category: 'mental', categoryText: '精神慰藉', price: '80元/次', status: 'online', statusText: '上架', description: '专业心理咨询师服务', createTime: '2025-02-01' },
  { id: 4, name: '康复理疗服务', category: 'medical', categoryText: '医疗护理', price: '120元/次', status: 'online', statusText: '上架', description: '专业康复理疗服务', createTime: '2025-02-20' },
  { id: 5, name: '家政保洁服务', category: 'life', categoryText: '生活照料', price: '60元/次', status: 'offline', statusText: '下架', description: '家庭保洁服务', createTime: '2025-03-05' },
];

export const serviceCategories = [
  { id: 'all', name: '全部' },
  { id: 'life', name: '生活照料' },
  { id: 'medical', name: '医疗护理' },
  { id: 'mental', name: '精神慰藉' },
  { id: 'other', name: '其他服务' },
];

export const adminServiceOrdersData = [
  { id: 1, orderNo: 'SV202601080001', elderName: '张大爷', serviceName: '日常照料服务', orderTime: '2026-01-08 10:00', serviceTime: '2026-01-10 09:00', status: 'pending', statusText: '待服务', operator: '李护工' },
  { id: 2, orderNo: 'SV202601080002', elderName: '李奶奶', serviceName: '上门医疗服务', orderTime: '2026-01-08 11:30', serviceTime: '2026-01-09 14:00', status: 'confirmed', statusText: '已确认', operator: '王医生' },
  { id: 3, orderNo: 'SV202601070001', elderName: '王大爷', serviceName: '康复理疗服务', orderTime: '2026-01-07 09:00', serviceTime: '2026-01-08 10:00', status: 'completed', statusText: '已完成', operator: '张理疗师' },
  { id: 4, orderNo: 'SV202601070002', elderName: '赵奶奶', serviceName: '心理咨询服务', orderTime: '2026-01-07 14:00', serviceTime: '2026-01-08 15:00', status: 'cancelled', statusText: '已取消', operator: '刘咨询师' },
  { id: 5, orderNo: 'SV202601060001', elderName: '刘大爷', serviceName: '日常照料服务', orderTime: '2026-01-06 16:00', serviceTime: '2026-01-07 09:00', status: 'completed', statusText: '已完成', operator: '李护工' },
];

export const adminActivitiesManageData = [
  { id: 1, name: '太极拳健身课', category: 'health', categoryText: '健康', time: '2026-01-15 14:00', location: '社区活动中心', capacity: 30, enrolled: 25, status: 'pending', statusText: '未开始', createTime: '2026-01-01' },
  { id: 2, name: '书法艺术交流会', category: 'culture', categoryText: '文娱', time: '2026-01-18 09:00', location: '社区文化活动室', capacity: 20, enrolled: 18, status: 'pending', statusText: '未开始', createTime: '2026-01-02' },
  { id: 3, name: '健康养生讲座', category: 'health', categoryText: '健康', time: '2026-01-10 14:30', location: '社区会议室', capacity: 50, enrolled: 42, status: 'ongoing', statusText: '进行中', createTime: '2025-12-20' },
  { id: 4, name: '智能手机使用培训', category: 'learning', categoryText: '学习', time: '2026-01-05 10:00', location: '社区电脑室', capacity: 15, enrolled: 12, status: 'ended', statusText: '已结束', createTime: '2025-12-15' },
  { id: 5, name: '郊外踏青一日游', category: 'travel', categoryText: '旅游', time: '2026-01-25 08:00', location: '香山公园', capacity: 40, enrolled: 35, status: 'pending', statusText: '未开始', createTime: '2026-01-05' },
  { id: 6, name: '广场舞培训班', category: 'culture', categoryText: '文娱', time: '2025-12-28 15:00', location: '社区广场', capacity: 50, enrolled: 50, status: 'cancelled', statusText: '已取消', createTime: '2025-12-10' },
];

export const adminEnrollmentsData = [
  { id: 1, activityName: '太极拳健身课', elderName: '张大爷', phone: '13800138888', enrollTime: '2026-01-05 10:30', status: 'confirmed', statusText: '已确认' },
  { id: 2, activityName: '太极拳健身课', elderName: '李奶奶', phone: '13800138889', enrollTime: '2026-01-05 11:00', status: 'confirmed', statusText: '已确认' },
  { id: 3, activityName: '书法艺术交流会', elderName: '王大爷', phone: '13800138890', enrollTime: '2026-01-06 09:00', status: 'confirmed', statusText: '已确认' },
  { id: 4, activityName: '健康养生讲座', elderName: '赵奶奶', phone: '13800138891', enrollTime: '2026-01-03 14:00', status: 'attended', statusText: '已签到' },
  { id: 5, activityName: '郊外踏青一日游', elderName: '刘大爷', phone: '13800138892', enrollTime: '2026-01-08 16:00', status: 'pending', statusText: '待确认' },
  { id: 6, activityName: '智能手机使用培训', elderName: '张大爷', phone: '13800138888', enrollTime: '2025-12-20 10:00', status: 'absent', statusText: '未参加' },
];
