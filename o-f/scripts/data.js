/**
 * 模拟数据 - 智慧养老系统
 * 所有页面使用的静态演示数据
 */

// 用户信息
const userData = {
  avatar: 'assets/images/avatar.jpg',
  nickname: '张大爷',
  bio: '热爱生活，享受健康',
  phone: '138****8888',
  stats: {
    posts: 12,
    activities: 8,
    healthDays: 365
  }
};

// 健康数据
const healthData = {
  'blood-pressure': {
    type: 'blood-pressure',
    name: '血压',
    currentValue: '120/80',
    unit: 'mmHg',
    status: 'normal',
    statusText: '正常',
    normalRange: '90-140/60-90',
    measureTime: '2026-01-08 08:00',
    advice: '您的血压正常，请继续保持良好的生活习惯。建议每天适量运动，保持心情愉悦，定期监测血压变化。',
    history: [
      { date: '2026-01-08', time: '08:00', value: '120/80', status: 'normal' },
      { date: '2026-01-07', time: '08:00', value: '118/78', status: 'normal' },
      { date: '2026-01-06', time: '08:00', value: '122/82', status: 'normal' },
      { date: '2026-01-05', time: '08:00', value: '119/79', status: 'normal' },
      { date: '2026-01-04', time: '08:00', value: '121/81', status: 'normal' },
      { date: '2026-01-03', time: '08:00', value: '120/80', status: 'normal' },
      { date: '2026-01-02', time: '08:00', value: '118/78', status: 'normal' }
    ]
  },
  'heart-rate': {
    type: 'heart-rate',
    name: '心率',
    currentValue: '75',
    unit: 'bpm',
    status: 'normal',
    statusText: '正常',
    normalRange: '60-100',
    measureTime: '2026-01-08 08:00',
    advice: '您的心率正常，心脏功能良好。建议保持规律作息，避免过度劳累，适当进行有氧运动。',
    history: [
      { date: '2026-01-08', time: '08:00', value: '75', status: 'normal' },
      { date: '2026-01-07', time: '08:00', value: '73', status: 'normal' },
      { date: '2026-01-06', time: '08:00', value: '76', status: 'normal' },
      { date: '2026-01-05', time: '08:00', value: '74', status: 'normal' },
      { date: '2026-01-04', time: '08:00', value: '75', status: 'normal' },
      { date: '2026-01-03', time: '08:00', value: '72', status: 'normal' },
      { date: '2026-01-02', time: '08:00', value: '74', status: 'normal' }
    ]
  },
  'blood-sugar': {
    type: 'blood-sugar',
    name: '血糖',
    currentValue: '5.5',
    unit: 'mmol/L',
    status: 'normal',
    statusText: '正常',
    normalRange: '3.9-6.1',
    measureTime: '2026-01-08 08:00',
    advice: '您的血糖水平正常。建议继续保持健康饮食，少吃甜食，多吃蔬菜水果，适量运动。',
    history: [
      { date: '2026-01-08', time: '08:00', value: '5.5', status: 'normal' },
      { date: '2026-01-07', time: '08:00', value: '5.3', status: 'normal' },
      { date: '2026-01-06', time: '08:00', value: '5.6', status: 'normal' },
      { date: '2026-01-05', time: '08:00', value: '5.4', status: 'normal' },
      { date: '2026-01-04', time: '08:00', value: '5.5', status: 'normal' },
      { date: '2026-01-03', time: '08:00', value: '5.2', status: 'normal' },
      { date: '2026-01-02', time: '08:00', value: '5.4', status: 'normal' }
    ]
  },
  'temperature': {
    type: 'temperature',
    name: '体温',
    currentValue: '36.5',
    unit: '℃',
    status: 'normal',
    statusText: '正常',
    normalRange: '36.0-37.3',
    measureTime: '2026-01-08 08:00',
    advice: '您的体温正常。注意根据天气变化增减衣物，保持室内通风，预防感冒。',
    history: [
      { date: '2026-01-08', time: '08:00', value: '36.5', status: 'normal' },
      { date: '2026-01-07', time: '08:00', value: '36.4', status: 'normal' },
      { date: '2026-01-06', time: '08:00', value: '36.6', status: 'normal' },
      { date: '2026-01-05', time: '08:00', value: '36.5', status: 'normal' },
      { date: '2026-01-04', time: '08:00', value: '36.5', status: 'normal' },
      { date: '2026-01-03', time: '08:00', value: '36.4', status: 'normal' },
      { date: '2026-01-02', time: '08:00', value: '36.6', status: 'normal' }
    ]
  }
};

// 健康预警数据
const alertsData = [
  {
    id: 1,
    level: 'urgent',
    levelText: '紧急',
    title: '血压持续偏高预警',
    time: '2026-01-08 09:30',
    summary: '您的血压连续3天偏高，请及时就医检查',
    content: '根据您最近的健康数据监测，您的血压在过去3天内持续偏高，收缩压平均值达到145 mmHg，超出正常范围。这可能增加心血管疾病的风险。',
    relatedData: '收缩压: 145 mmHg（正常范围: 90-140 mmHg）',
    advice: '建议您：\n1. 立即联系您的家庭医生或到医院就诊\n2. 减少盐分摄入，避免高脂肪食物\n3. 保持情绪稳定，避免过度紧张\n4. 按时服用降压药物（如已处方）\n5. 每天监测血压变化',
    isRead: false
  },
  {
    id: 2,
    level: 'important',
    levelText: '重要',
    title: '运动量不足提醒',
    time: '2026-01-07 18:00',
    summary: '本周运动量较少，建议增加适量运动',
    content: '根据您的活动数据，本周平均每天步行仅3000步，远低于建议的6000-8000步。适量运动对维持健康非常重要。',
    relatedData: '本周平均步数: 3000步/天（建议: 6000-8000步/天）',
    advice: '建议您：\n1. 每天早晚各散步30分钟\n2. 参加社区组织的太极拳或广场舞活动\n3. 选择适合自己的运动方式，循序渐进\n4. 运动前做好热身，避免运动损伤\n5. 如有不适，及时停止并休息',
    isRead: false
  },
  {
    id: 3,
    level: 'info',
    levelText: '提醒',
    title: '体检提醒',
    time: '2026-01-06 10:00',
    summary: '距离上次体检已过6个月，建议进行定期体检',
    content: '定期体检是预防疾病、维护健康的重要手段。建议老年人每半年进行一次全面体检。',
    relatedData: '上次体检时间: 2025-07-06',
    advice: '建议您：\n1. 预约社区医院或体检中心\n2. 体检前一天晚上10点后禁食禁水\n3. 携带既往病历和检查报告\n4. 体检项目应包括血常规、尿常规、心电图、B超等\n5. 体检后及时查看报告，如有异常及时就医',
    isRead: true
  },
  {
    id: 4,
    level: 'important',
    levelText: '重要',
    title: '用药提醒',
    time: '2026-01-05 08:00',
    summary: '降压药即将用完，请及时补充',
    content: '您的降压药物库存不足，预计3天后用完。请及时到医院或药店购买，避免断药影响治疗效果。',
    relatedData: '剩余药量: 3天',
    advice: '建议您：\n1. 尽快到医院开具处方或到药店购买\n2. 不要随意更换药物品牌\n3. 按时按量服药，不可擅自停药\n4. 如有不适反应，及时咨询医生\n5. 建议常备一周以上的药量',
    isRead: true
  },
  {
    id: 5,
    level: 'info',
    levelText: '提醒',
    title: '睡眠质量提醒',
    time: '2026-01-04 07:00',
    summary: '昨晚睡眠时间较短，注意休息',
    content: '根据您的睡眠监测数据，昨晚睡眠时间仅5小时，低于建议的7-8小时。充足的睡眠对健康很重要。',
    relatedData: '昨晚睡眠时间: 5小时（建议: 7-8小时）',
    advice: '建议您：\n1. 保持规律的作息时间\n2. 睡前避免饮用咖啡、浓茶\n3. 创造舒适的睡眠环境\n4. 睡前可以听轻音乐或泡脚放松\n5. 如长期失眠，建议就医咨询',
    isRead: true
  }
];

// 紧急联系人数据
const emergencyContacts = [
  {
    id: 1,
    avatar: 'assets/images/contact1.jpg',
    name: '张小明',
    relation: '子女',
    phone: '138****8888'
  },
  {
    id: 2,
    avatar: 'assets/images/contact2.jpg',
    name: '李医生',
    relation: '家庭医生',
    phone: '139****9999'
  },
  {
    id: 3,
    avatar: 'assets/images/contact3.jpg',
    name: '王护士',
    relation: '社区工作者',
    phone: '137****7777'
  }
];

// 当前位置信息
const locationData = {
  address: '北京市朝阳区幸福社区12号楼3单元501室'
};

// 社交动态数据
const socialPosts = [
  {
    id: 1,
    user: {
      avatar: 'assets/images/user1.jpg',
      nickname: '李阿姨'
    },
    time: '2小时前',
    content: '今天天气真好，和老伙伴们一起去公园散步了，心情特别舒畅！大家也要多出去走走哦~',
    likes: 15,
    comments: 3,
    tags: ['健康生活']
  },
  {
    id: 2,
    user: {
      avatar: 'assets/images/user2.jpg',
      nickname: '王大爷'
    },
    time: '5小时前',
    content: '刚参加完社区的太极拳课程，感觉全身都舒展开了。感谢社区组织这么好的活动！',
    likes: 22,
    comments: 5,
    tags: ['社区活动', '健康生活']
  },
  {
    id: 3,
    user: {
      avatar: 'assets/images/user3.jpg',
      nickname: '赵奶奶'
    },
    time: '1天前',
    content: '今天学会了用手机拍照，给孙子发了好多照片，他可高兴了！老年人也要与时俱进呀😊',
    likes: 28,
    comments: 8,
    tags: ['生活分享']
  },
  {
    id: 4,
    user: {
      avatar: 'assets/images/user4.jpg',
      nickname: '刘大爷'
    },
    time: '1天前',
    content: '分享一个养生小知识：每天早上喝一杯温水，对身体很有好处。大家都试试吧！',
    likes: 35,
    comments: 12,
    tags: ['养生知识']
  },
  {
    id: 5,
    user: {
      avatar: 'assets/images/user5.jpg',
      nickname: '孙阿姨'
    },
    time: '2天前',
    content: '昨天去医院体检，各项指标都正常，医生说我保养得很好。开心！',
    likes: 42,
    comments: 15,
    tags: ['健康生活']
  },
  {
    id: 6,
    user: {
      avatar: 'assets/images/user6.jpg',
      nickname: '周大爷'
    },
    time: '2天前',
    content: '社区图书馆新到了一批书，有兴趣的朋友可以去借阅。我借了一本养生书，很不错！',
    likes: 18,
    comments: 4,
    tags: ['社区活动']
  },
  {
    id: 7,
    user: {
      avatar: 'assets/images/user7.jpg',
      nickname: '吴奶奶'
    },
    time: '3天前',
    content: '今天做了拿手菜红烧肉，孩子们都说好吃。虽然年纪大了，但手艺还在！',
    likes: 31,
    comments: 9,
    tags: ['生活分享']
  },
  {
    id: 8,
    user: {
      avatar: 'assets/images/user8.jpg',
      nickname: '郑大爷'
    },
    time: '3天前',
    content: '参加了社区组织的健康讲座，学到了很多预防疾病的知识。建议大家都去听听！',
    likes: 25,
    comments: 6,
    tags: ['社区活动', '养生知识']
  }
];

// 热门话题标签
const topicTags = [
  { id: 1, name: '健康生活', count: 156 },
  { id: 2, name: '社区活动', count: 98 },
  { id: 3, name: '养生知识', count: 87 },
  { id: 4, name: '生活分享', count: 124 }
];

// 养老活动数据
const activitiesData = [
  {
    id: 1,
    image: 'assets/images/activity1.jpg',
    name: '太极拳健身课',
    category: 'health',
    categoryText: '健康',
    time: '2026-01-15 14:00-16:00',
    location: '社区活动中心',
    organizer: '社区服务中心',
    fee: '免费',
    capacity: 30,
    enrolled: 25,
    status: 'available',
    statusText: '可报名',
    description: '太极拳是一项非常适合老年人的运动，动作柔和，强度适中，能够增强身体柔韧性和平衡能力。本次课程由专业教练指导，适合各个水平的学员参加。',
    notes: '请穿着宽松舒适的运动服装，自备饮用水。如有心脏病、高血压等疾病，请提前告知教练。',
    participants: [
      { avatar: 'assets/images/user1.jpg', name: '李阿姨' },
      { avatar: 'assets/images/user2.jpg', name: '王大爷' },
      { avatar: 'assets/images/user3.jpg', name: '赵奶奶' },
      { avatar: 'assets/images/user4.jpg', name: '刘大爷' },
      { avatar: 'assets/images/user5.jpg', name: '孙阿姨' }
    ]
  },
  {
    id: 2,
    image: 'assets/images/activity2.jpg',
    name: '书法艺术交流会',
    category: 'culture',
    categoryText: '文娱',
    time: '2026-01-18 09:00-11:00',
    location: '社区文化活动室',
    organizer: '社区文化协会',
    fee: '免费',
    capacity: 20,
    enrolled: 18,
    status: 'available',
    statusText: '可报名',
    description: '书法是中国传统文化的瑰宝，练习书法不仅能陶冶情操，还能锻炼手脑协调能力。本次活动邀请了书法名家现场指导，欢迎书法爱好者参加。',
    notes: '请自备笔墨纸砚，或现场购买。活动结束后可将作品带回家。',
    participants: [
      { avatar: 'assets/images/user6.jpg', name: '周大爷' },
      { avatar: 'assets/images/user7.jpg', name: '吴奶奶' },
      { avatar: 'assets/images/user8.jpg', name: '郑大爷' }
    ]
  },
  {
    id: 3,
    image: 'assets/images/activity3.jpg',
    name: '健康养生讲座',
    category: 'health',
    categoryText: '健康',
    time: '2026-01-20 14:30-16:30',
    location: '社区会议室',
    organizer: '社区卫生服务中心',
    fee: '免费',
    capacity: 50,
    enrolled: 42,
    status: 'available',
    statusText: '可报名',
    description: '本次讲座邀请了三甲医院的专家，为大家讲解冬季养生知识、常见疾病预防、合理膳食等内容。讲座结束后还有免费健康咨询环节。',
    notes: '请携带既往病历和体检报告，以便医生提供更有针对性的建议。',
    participants: [
      { avatar: 'assets/images/user1.jpg', name: '李阿姨' },
      { avatar: 'assets/images/user4.jpg', name: '刘大爷' }
    ]
  },
  {
    id: 4,
    image: 'assets/images/activity4.jpg',
    name: '智能手机使用培训',
    category: 'learning',
    categoryText: '学习',
    time: '2026-01-22 10:00-12:00',
    location: '社区电脑室',
    organizer: '社区志愿者协会',
    fee: '免费',
    capacity: 15,
    enrolled: 12,
    status: 'available',
    statusText: '可报名',
    description: '随着科技发展，智能手机已成为生活必需品。本次培训将教大家如何使用微信、支付宝、健康码等常用功能，让老年人也能享受科技带来的便利。',
    notes: '请携带自己的智能手机，确保电量充足。志愿者将一对一指导。',
    participants: [
      { avatar: 'assets/images/user3.jpg', name: '赵奶奶' },
      { avatar: 'assets/images/user5.jpg', name: '孙阿姨' }
    ]
  },
  {
    id: 5,
    image: 'assets/images/activity5.jpg',
    name: '郊外踏青一日游',
    category: 'travel',
    categoryText: '旅游',
    time: '2026-01-25 08:00-17:00',
    location: '香山公园',
    organizer: '社区旅游协会',
    fee: '50元/人',
    capacity: 40,
    enrolled: 35,
    status: 'available',
    statusText: '可报名',
    description: '春暖花开，正是踏青好时节。本次活动将组织大家前往香山公园，欣赏美丽的自然风光，呼吸新鲜空气。全程配备医护人员，确保安全。',
    notes: '请穿着舒适的运动鞋，携带身份证、常用药品。费用包含往返车费、午餐和保险。',
    participants: [
      { avatar: 'assets/images/user2.jpg', name: '王大爷' },
      { avatar: 'assets/images/user6.jpg', name: '周大爷' }
    ]
  },
  {
    id: 6,
    image: 'assets/images/activity6.jpg',
    name: '广场舞培训班',
    category: 'culture',
    categoryText: '文娱',
    time: '2026-01-28 15:00-17:00',
    location: '社区广场',
    organizer: '社区文体协会',
    fee: '免费',
    capacity: 50,
    enrolled: 50,
    status: 'full',
    statusText: '已满员',
    description: '广场舞是深受老年人喜爱的健身方式。本次培训班将教授最新的广场舞曲目，由专业舞蹈老师指导，欢迎舞蹈爱好者参加。',
    notes: '请穿着运动服装和舞蹈鞋，自备饮用水。',
    participants: [
      { avatar: 'assets/images/user7.jpg', name: '吴奶奶' },
      { avatar: 'assets/images/user1.jpg', name: '李阿姨' }
    ]
  }
];

// 活动分类
const activityCategories = [
  { id: 'all', name: '全部' },
  { id: 'culture', name: '文娱' },
  { id: 'health', name: '健康' },
  { id: 'learning', name: '学习' },
  { id: 'travel', name: '旅游' }
];


// ==================== 后台管理模块数据 ====================

// 管理模块菜单配置
const adminModules = [
  {
    id: 'user-management',
    name: '用户管理',
    icon: '👥',
    subModules: [
      { id: 'user-list', name: '用户列表', route: '#admin-user-list' },
      { id: 'role-manage', name: '角色管理', route: '#admin-role-manage' },
      { id: 'permission', name: '权限设置', route: '#admin-permission' }
    ]
  },
  {
    id: 'elder-management',
    name: '老人管理',
    icon: '👴',
    subModules: [
      { id: 'elder-list', name: '老人档案', route: '#admin-elder-list' },
      { id: 'health-record', name: '健康记录', route: '#admin-health-record' },
      { id: 'family-bindng', name: '家属绑定', route: '#admin-family-bindng' }
    ]
  },
  {
    id: 'service-management',
    name: '服务管理',
    icon: '🛎️',
    subModules: [
      { id: 'service-list', name: '服务项目', route: '#admin-service-list' },
      { id: 'service-order', name: '服务预约', route: '#admin-service-order' }
    ]
  },
  {
    id: 'activity-management',
    name: '活动管理',
    icon: '🎉',
    subModules: [
      { id: 'activity-list', name: '活动列表', route: '#admin-activity-list' },
      { id: 'enrollment', name: '报名管理', route: '#admin-enrollment' }
    ]
  }
];

// 用户列表数据
const adminUsersData = [
  { id: 1, username: '张三', phone: '13800138001', role: 'admin', roleText: '管理员', status: 'active', statusText: '正常', createTime: '2025-06-15' },
  { id: 2, username: '李四', phone: '13800138002', role: 'operator', roleText: '操作员', status: 'active', statusText: '正常', createTime: '2025-07-20' },
  { id: 3, username: '王五', phone: '13800138003', role: 'user', roleText: '普通用户', status: 'active', statusText: '正常', createTime: '2025-08-10' },
  { id: 4, username: '赵六', phone: '13800138004', role: 'operator', roleText: '操作员', status: 'disabled', statusText: '禁用', createTime: '2025-09-05' },
  { id: 5, username: '钱七', phone: '13800138005', role: 'user', roleText: '普通用户', status: 'active', statusText: '正常', createTime: '2025-10-12' }
];

// 角色数据
const adminRolesData = [
  { id: 1, name: '超级管理员', code: 'super_admin', userCount: 1, permissions: '全部权限', status: 'active', statusText: '启用', createTime: '2025-01-01' },
  { id: 2, name: '管理员', code: 'admin', userCount: 3, permissions: '用户管理、老人管理、服务管理、活动管理', status: 'active', statusText: '启用', createTime: '2025-01-01' },
  { id: 3, name: '操作员', code: 'operator', userCount: 8, permissions: '老人管理、服务管理、活动管理', status: 'active', statusText: '启用', createTime: '2025-02-15' },
  { id: 4, name: '普通用户', code: 'user', userCount: 156, permissions: '查看信息', status: 'active', statusText: '启用', createTime: '2025-01-01' }
];

// 权限数据
const adminPermissionsData = [
  { id: 1, module: '用户管理', permissions: [
    { id: 'user_view', name: '查看用户', enabled: true },
    { id: 'user_add', name: '添加用户', enabled: true },
    { id: 'user_edit', name: '编辑用户', enabled: true },
    { id: 'user_delete', name: '删除用户', enabled: false }
  ]},
  { id: 2, module: '老人管理', permissions: [
    { id: 'elder_view', name: '查看档案', enabled: true },
    { id: 'elder_add', name: '添加档案', enabled: true },
    { id: 'elder_edit', name: '编辑档案', enabled: true },
    { id: 'elder_delete', name: '删除档案', enabled: false }
  ]},
  { id: 3, module: '服务管理', permissions: [
    { id: 'service_view', name: '查看服务', enabled: true },
    { id: 'service_add', name: '添加服务', enabled: true },
    { id: 'service_edit', name: '编辑服务', enabled: true },
    { id: 'service_toggle', name: '上下架', enabled: true }
  ]},
  { id: 4, module: '活动管理', permissions: [
    { id: 'activity_view', name: '查看活动', enabled: true },
    { id: 'activity_add', name: '添加活动', enabled: true },
    { id: 'activity_edit', name: '编辑活动', enabled: true },
    { id: 'activity_cancel', name: '取消活动', enabled: false }
  ]}
];

// 老人档案数据
const adminEldersData = [
  { id: 1, name: '张大爷', age: 72, gender: '男', phone: '13800138888', address: '幸福社区12号楼501', emergencyContact: '张小明', emergencyPhone: '13800138001', healthStatus: 'normal', healthText: '健康', createTime: '2025-03-15' },
  { id: 2, name: '李奶奶', age: 68, gender: '女', phone: '13800138889', address: '幸福社区8号楼302', emergencyContact: '李小红', emergencyPhone: '13800138002', healthStatus: 'warning', healthText: '亚健康', createTime: '2025-04-20' },
  { id: 3, name: '王大爷', age: 75, gender: '男', phone: '13800138890', address: '幸福社区15号楼101', emergencyContact: '王小刚', emergencyPhone: '13800138003', healthStatus: 'normal', healthText: '健康', createTime: '2025-05-10' },
  { id: 4, name: '赵奶奶', age: 70, gender: '女', phone: '13800138891', address: '幸福社区3号楼602', emergencyContact: '赵小丽', emergencyPhone: '13800138004', healthStatus: 'danger', healthText: '需关注', createTime: '2025-06-05' },
  { id: 5, name: '刘大爷', age: 78, gender: '男', phone: '13800138892', address: '幸福社区20号楼403', emergencyContact: '刘小军', emergencyPhone: '13800138005', healthStatus: 'normal', healthText: '健康', createTime: '2025-07-12' }
];

// 健康记录数据
const adminHealthRecordsData = [
  { id: 1, elderName: '张大爷', recordType: '血压', value: '120/80 mmHg', status: 'normal', statusText: '正常', recordTime: '2026-01-08 08:00', operator: '王护士' },
  { id: 2, elderName: '李奶奶', recordType: '血糖', value: '6.8 mmol/L', status: 'warning', statusText: '偏高', recordTime: '2026-01-08 07:30', operator: '张护士' },
  { id: 3, elderName: '王大爷', recordType: '心率', value: '75 bpm', status: 'normal', statusText: '正常', recordTime: '2026-01-08 09:00', operator: '王护士' },
  { id: 4, elderName: '赵奶奶', recordType: '血压', value: '145/95 mmHg', status: 'danger', statusText: '偏高', recordTime: '2026-01-08 08:30', operator: '李护士' },
  { id: 5, elderName: '刘大爷', recordType: '体温', value: '36.5 ℃', status: 'normal', statusText: '正常', recordTime: '2026-01-08 07:00', operator: '张护士' }
];

// 家属绑定数据
const adminFamilyBindingsData = [
  { id: 1, elderName: '张大爷', familyName: '张小明', relation: '子女', phone: '13800138001', bindTime: '2025-03-15', status: 'bindng', statusText: '已绑定' },
  { id: 2, elderName: '张大爷', familyName: '张小红', relation: '子女', phone: '13800138006', bindTime: '2025-03-16', status: 'bindng', statusText: '已绑定' },
  { id: 3, elderName: '李奶奶', familyName: '李小红', relation: '子女', phone: '13800138002', bindTime: '2025-04-20', status: 'bindng', statusText: '已绑定' },
  { id: 4, elderName: '王大爷', familyName: '王小刚', relation: '子女', phone: '13800138003', bindTime: '2025-05-10', status: 'bindng', statusText: '已绑定' },
  { id: 5, elderName: '赵奶奶', familyName: '赵小丽', relation: '子女', phone: '13800138004', bindTime: '2025-06-05', status: 'pending', statusText: '待确认' }
];

// 服务项目数据
const adminServicesData = [
  { id: 1, name: '日常照料服务', category: 'life', categoryText: '生活照料', price: '50元/次', status: 'online', statusText: '上架', description: '提供日常起居照料服务', createTime: '2025-01-10' },
  { id: 2, name: '上门医疗服务', category: 'medical', categoryText: '医疗护理', price: '100元/次', status: 'online', statusText: '上架', description: '专业医护人员上门服务', createTime: '2025-01-15' },
  { id: 3, name: '心理咨询服务', category: 'mental', categoryText: '精神慰藉', price: '80元/次', status: 'online', statusText: '上架', description: '专业心理咨询师服务', createTime: '2025-02-01' },
  { id: 4, name: '康复理疗服务', category: 'medical', categoryText: '医疗护理', price: '120元/次', status: 'online', statusText: '上架', description: '专业康复理疗服务', createTime: '2025-02-20' },
  { id: 5, name: '家政保洁服务', category: 'life', categoryText: '生活照料', price: '60元/次', status: 'offline', statusText: '下架', description: '家庭保洁服务', createTime: '2025-03-05' }
];

// 服务分类
const serviceCategories = [
  { id: 'all', name: '全部' },
  { id: 'life', name: '生活照料' },
  { id: 'medical', name: '医疗护理' },
  { id: 'mental', name: '精神慰藉' },
  { id: 'other', name: '其他服务' }
];

// 服务预约数据
const adminServiceOrdersData = [
  { id: 1, orderNo: 'SV202601080001', elderName: '张大爷', serviceName: '日常照料服务', orderTime: '2026-01-08 10:00', serviceTime: '2026-01-10 09:00', status: 'pending', statusText: '待服务', operator: '李护工' },
  { id: 2, orderNo: 'SV202601080002', elderName: '李奶奶', serviceName: '上门医疗服务', orderTime: '2026-01-08 11:30', serviceTime: '2026-01-09 14:00', status: 'confirmed', statusText: '已确认', operator: '王医生' },
  { id: 3, orderNo: 'SV202601070001', elderName: '王大爷', serviceName: '康复理疗服务', orderTime: '2026-01-07 09:00', serviceTime: '2026-01-08 10:00', status: 'completed', statusText: '已完成', operator: '张理疗师' },
  { id: 4, orderNo: 'SV202601070002', elderName: '赵奶奶', serviceName: '心理咨询服务', orderTime: '2026-01-07 14:00', serviceTime: '2026-01-08 15:00', status: 'cancelled', statusText: '已取消', operator: '刘咨询师' },
  { id: 5, orderNo: 'SV202601060001', elderName: '刘大爷', serviceName: '日常照料服务', orderTime: '2026-01-06 16:00', serviceTime: '2026-01-07 09:00', status: 'completed', statusText: '已完成', operator: '李护工' }
];

// 活动管理数据（扩展现有活动数据）
const adminActivitiesManageData = [
  { id: 1, name: '太极拳健身课', category: 'health', categoryText: '健康', time: '2026-01-15 14:00', location: '社区活动中心', capacity: 30, enrolled: 25, status: 'pending', statusText: '未开始', createTime: '2026-01-01' },
  { id: 2, name: '书法艺术交流会', category: 'culture', categoryText: '文娱', time: '2026-01-18 09:00', location: '社区文化活动室', capacity: 20, enrolled: 18, status: 'pending', statusText: '未开始', createTime: '2026-01-02' },
  { id: 3, name: '健康养生讲座', category: 'health', categoryText: '健康', time: '2026-01-10 14:30', location: '社区会议室', capacity: 50, enrolled: 42, status: 'ongoing', statusText: '进行中', createTime: '2025-12-20' },
  { id: 4, name: '智能手机使用培训', category: 'learning', categoryText: '学习', time: '2026-01-05 10:00', location: '社区电脑室', capacity: 15, enrolled: 12, status: 'ended', statusText: '已结束', createTime: '2025-12-15' },
  { id: 5, name: '郊外踏青一日游', category: 'travel', categoryText: '旅游', time: '2026-01-25 08:00', location: '香山公园', capacity: 40, enrolled: 35, status: 'pending', statusText: '未开始', createTime: '2026-01-05' },
  { id: 6, name: '广场舞培训班', category: 'culture', categoryText: '文娱', time: '2025-12-28 15:00', location: '社区广场', capacity: 50, enrolled: 50, status: 'cancelled', statusText: '已取消', createTime: '2025-12-10' }
];

// 报名管理数据
const adminEnrollmentsData = [
  { id: 1, activityName: '太极拳健身课', elderName: '张大爷', phone: '13800138888', enrollTime: '2026-01-05 10:30', status: 'confirmed', statusText: '已确认' },
  { id: 2, activityName: '太极拳健身课', elderName: '李奶奶', phone: '13800138889', enrollTime: '2026-01-05 11:00', status: 'confirmed', statusText: '已确认' },
  { id: 3, activityName: '书法艺术交流会', elderName: '王大爷', phone: '13800138890', enrollTime: '2026-01-06 09:00', status: 'confirmed', statusText: '已确认' },
  { id: 4, activityName: '健康养生讲座', elderName: '赵奶奶', phone: '13800138891', enrollTime: '2026-01-03 14:00', status: 'attended', statusText: '已签到' },
  { id: 5, activityName: '郊外踏青一日游', elderName: '刘大爷', phone: '13800138892', enrollTime: '2026-01-08 16:00', status: 'pending', statusText: '待确认' },
  { id: 6, activityName: '智能手机使用培训', elderName: '张大爷', phone: '13800138888', enrollTime: '2025-12-20 10:00', status: 'absent', statusText: '未参加' }
];
