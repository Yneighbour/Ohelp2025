-- ==============================================================
-- Ohelp2025 老年人服务管理系统 - 数据库DDL脚本
-- ==============================================================
-- 数据库: ohelp
-- 字符集: utf8mb4
-- 排序规则: utf8mb4_unicode_ci
-- ==============================================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS ohelp
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE ohelp;

-- ==============================================================
-- 1. Auth 认证模块表
-- ==============================================================

CREATE TABLE IF NOT EXISTS auth (
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '认证记录ID',
  username VARCHAR(100) NOT NULL UNIQUE COMMENT '用户名',
  password VARCHAR(255) NOT NULL COMMENT '密码',
  token VARCHAR(500) COMMENT '认证令牌',
  user_id BIGINT COMMENT '关联用户ID',
  login_time DATETIME COMMENT '登录时间',
  logout_time DATETIME COMMENT '登出时间',
  is_active TINYINT(1) DEFAULT 1 COMMENT '是否激活',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  INDEX idx_username (username),
  INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='认证记录表';

-- ==============================================================
-- 2. User 用户管理模块表
-- ==============================================================

CREATE TABLE IF NOT EXISTS user (
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
  name VARCHAR(100) NOT NULL COMMENT '姓名',
  email VARCHAR(100) UNIQUE COMMENT '邮箱',
  phone VARCHAR(20) UNIQUE COMMENT '电话',
  role VARCHAR(50) COMMENT '角色',
  avatar_url VARCHAR(500) COMMENT '头像URL',
  is_active TINYINT(1) DEFAULT 1 COMMENT '是否激活',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  INDEX idx_email (email),
  INDEX idx_phone (phone),
  INDEX idx_role (role)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- ==============================================================
-- 3. Elder 老人信息模块表
-- ==============================================================

CREATE TABLE IF NOT EXISTS elderly (
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '老人ID',
  name VARCHAR(100) NOT NULL COMMENT '姓名',
  age INT COMMENT '年龄',
  date_of_birth DATE COMMENT '出生日期',
  gender VARCHAR(10) COMMENT '性别',
  phone_number VARCHAR(20) COMMENT '电话',
  health_status VARCHAR(100) COMMENT '健康状态',
  medical_history TEXT COMMENT '医疗历史',
  address VARCHAR(255) COMMENT '地址',
  contact_person VARCHAR(100) COMMENT '联系人',
  contact_phone VARCHAR(20) COMMENT '联系电话',
  is_active TINYINT(1) DEFAULT 1 COMMENT '是否激活',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  INDEX idx_name (name),
  INDEX idx_phone (phone_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='老人信息表';

CREATE TABLE IF NOT EXISTS relative (
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '亲属ID',
  elderly_id BIGINT NOT NULL COMMENT '关联老人ID',
  name VARCHAR(100) NOT NULL COMMENT '姓名',
  phone VARCHAR(20) COMMENT '电话',
  relationship VARCHAR(50) COMMENT '关系',
  email VARCHAR(100) COMMENT '邮箱',
  is_primary_contact TINYINT(1) DEFAULT 0 COMMENT '是否主要联系人',
  is_active TINYINT(1) DEFAULT 1 COMMENT '是否激活',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  FOREIGN KEY (elderly_id) REFERENCES elderly(id) ON DELETE CASCADE,
  INDEX idx_elderly_id (elderly_id),
  INDEX idx_phone (phone)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='亲属信息表';

-- ==============================================================
-- 4. Activity 活动管理模块表
-- ==============================================================

CREATE TABLE IF NOT EXISTS activity (
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '活动ID',
  name VARCHAR(200) NOT NULL COMMENT '活动名称',
  category VARCHAR(100) COMMENT '活动分类',
  description TEXT COMMENT '活动描述',
  location VARCHAR(255) COMMENT '活动地点',
  start_time DATETIME COMMENT '开始时间',
  end_time DATETIME COMMENT '结束时间',
  participants INT DEFAULT 0 COMMENT '参与人数',
  organizer_id BIGINT COMMENT '组织者ID',
  status VARCHAR(50) COMMENT '活动状态',
  is_active TINYINT(1) DEFAULT 1 COMMENT '是否激活',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  INDEX idx_name (name),
  INDEX idx_category (category),
  INDEX idx_organizer_id (organizer_id),
  INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='活动表';

-- ==============================================================
-- 5. Emergency 紧急求助模块表
-- ==============================================================

CREATE TABLE IF NOT EXISTS emergency (
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '求助ID',
  elderly_id BIGINT NOT NULL COMMENT '老人ID',
  type VARCHAR(100) COMMENT '求助类型',
  description TEXT COMMENT '求助描述',
  location VARCHAR(255) COMMENT '求助位置',
  contact_phone VARCHAR(20) COMMENT '联系电话',
  status VARCHAR(50) COMMENT '处理状态',
  responder_id BIGINT COMMENT '响应人ID',
  response_time DATETIME COMMENT '响应时间',
  resolved_time DATETIME COMMENT '解决时间',
  priority VARCHAR(50) COMMENT '优先级',
  is_active TINYINT(1) DEFAULT 1 COMMENT '是否激活',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  FOREIGN KEY (elderly_id) REFERENCES elderly(id) ON DELETE CASCADE,
  INDEX idx_elderly_id (elderly_id),
  INDEX idx_status (status),
  INDEX idx_priority (priority)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='紧急求助表';

-- ==============================================================
-- 6. Health 健康管理模块表
-- ==============================================================

CREATE TABLE IF NOT EXISTS health_record (
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '记录ID',
  elderly_id BIGINT NOT NULL COMMENT '老人ID',
  record_date DATE NOT NULL COMMENT '记录日期',
  blood_pressure VARCHAR(50) COMMENT '血压',
  heart_rate INT COMMENT '心率',
  temperature DECIMAL(5, 2) COMMENT '体温',
  weight DECIMAL(5, 2) COMMENT '体重',
  glucose_level DECIMAL(5, 2) COMMENT '血糖水平',
  notes TEXT COMMENT '备注',
  doctor_id BIGINT COMMENT '医生ID',
  is_active TINYINT(1) DEFAULT 1 COMMENT '是否激活',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  FOREIGN KEY (elderly_id) REFERENCES elderly(id) ON DELETE CASCADE,
  INDEX idx_elderly_id (elderly_id),
  INDEX idx_record_date (record_date),
  INDEX idx_doctor_id (doctor_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='健康记录表';

-- ==============================================================
-- 7. ServiceOrder 服务订单模块表
-- ==============================================================

CREATE TABLE IF NOT EXISTS service_order (
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '订单ID',
  elderly_id BIGINT NOT NULL COMMENT '老人ID',
  service_type VARCHAR(100) COMMENT '服务类型',
  service_provider_id BIGINT COMMENT '服务提供者ID',
  start_date DATE COMMENT '开始日期',
  end_date DATE COMMENT '结束日期',
  frequency VARCHAR(50) COMMENT '服务频率',
  price DECIMAL(10, 2) COMMENT '价格',
  status VARCHAR(50) COMMENT '订单状态',
  description TEXT COMMENT '描述',
  is_active TINYINT(1) DEFAULT 1 COMMENT '是否激活',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  FOREIGN KEY (elderly_id) REFERENCES elderly(id) ON DELETE CASCADE,
  INDEX idx_elderly_id (elderly_id),
  INDEX idx_service_type (service_type),
  INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='服务订单表';

-- ==============================================================
-- 8. Worker 工作人员模块表
-- ==============================================================

CREATE TABLE IF NOT EXISTS worker (
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '员工ID',
  name VARCHAR(100) NOT NULL COMMENT '姓名',
  email VARCHAR(100) UNIQUE COMMENT '邮箱',
  phone VARCHAR(20) UNIQUE COMMENT '电话',
  position VARCHAR(100) COMMENT '职位',
  department VARCHAR(100) COMMENT '部门',
  specialization VARCHAR(255) COMMENT '专长',
  hire_date DATE COMMENT '入职日期',
  salary DECIMAL(10, 2) COMMENT '薪资',
  is_available TINYINT(1) DEFAULT 1 COMMENT '是否可用',
  is_active TINYINT(1) DEFAULT 1 COMMENT '是否激活',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  INDEX idx_email (email),
  INDEX idx_phone (phone),
  INDEX idx_department (department),
  INDEX idx_position (position)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='员工表';

-- ==============================================================
-- 9. File 文件管理模块表
-- ==============================================================

CREATE TABLE IF NOT EXISTS file_record (
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '文件记录ID',
  filename VARCHAR(255) NOT NULL COMMENT '文件名',
  original_filename VARCHAR(255) COMMENT '原始文件名',
  file_type VARCHAR(100) COMMENT '文件类型',
  file_size BIGINT COMMENT '文件大小',
  file_path VARCHAR(500) COMMENT '文件路径',
  url VARCHAR(500) COMMENT '访问URL',
  uploader_id BIGINT COMMENT '上传者ID',
  entity_type VARCHAR(100) COMMENT '关联实体类型',
  entity_id BIGINT COMMENT '关联实体ID',
  description TEXT COMMENT '描述',
  is_active TINYINT(1) DEFAULT 1 COMMENT '是否激活',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  INDEX idx_uploader_id (uploader_id),
  INDEX idx_entity (entity_type, entity_id),
  INDEX idx_file_type (file_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文件记录表';

-- ==============================================================
-- 索引优化
-- ==============================================================

-- Auth 表索引
ALTER TABLE auth ADD INDEX idx_login_time (login_time);

-- Elderly 表索引
ALTER TABLE elderly ADD INDEX idx_is_active (is_active);

-- Activity 表索引
ALTER TABLE activity ADD INDEX idx_is_active (is_active);

-- Emergency 表索引
ALTER TABLE emergency ADD INDEX idx_created_at (created_at);

-- HealthRecord 表索引
ALTER TABLE health_record ADD INDEX idx_is_active (is_active);

-- ServiceOrder 表索引
ALTER TABLE service_order ADD INDEX idx_is_active (is_active);

-- Worker 表索引
ALTER TABLE worker ADD INDEX idx_is_available (is_available);

-- FileRecord 表索引
ALTER TABLE file_record ADD INDEX idx_created_at (created_at);

-- ==============================================================
-- 8. Role 角色管理模块表
-- ==============================================================

CREATE TABLE IF NOT EXISTS role (
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '角色ID',
  name VARCHAR(50) NOT NULL UNIQUE COMMENT '角色名称',
  code VARCHAR(50) NOT NULL UNIQUE COMMENT '角色编码',
  description VARCHAR(500) COMMENT '角色描述',
  is_active TINYINT(1) DEFAULT 1 COMMENT '是否激活',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  INDEX idx_code (code),
  INDEX idx_is_active (is_active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';

-- ==============================================================
-- 9. Permission 权限管理模块表
-- ==============================================================

CREATE TABLE IF NOT EXISTS permission (
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '权限ID',
  name VARCHAR(100) NOT NULL UNIQUE COMMENT '权限名称',
  code VARCHAR(100) NOT NULL UNIQUE COMMENT '权限编码',
  module VARCHAR(50) COMMENT '所属模块',
  description VARCHAR(500) COMMENT '权限描述',
  is_active TINYINT(1) DEFAULT 1 COMMENT '是否激活',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  INDEX idx_code (code),
  INDEX idx_module (module),
  INDEX idx_is_active (is_active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='权限表';

-- ==============================================================
-- 10. RolePermission 角色权限关联表
-- ==============================================================

CREATE TABLE IF NOT EXISTS role_permission (
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '关联ID',
  role_id BIGINT NOT NULL COMMENT '角色ID',
  permission_id BIGINT NOT NULL COMMENT '权限ID',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE,
  FOREIGN KEY (permission_id) REFERENCES permission(id) ON DELETE CASCADE,
  UNIQUE KEY uk_role_permission (role_id, permission_id),
  INDEX idx_role_id (role_id),
  INDEX idx_permission_id (permission_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色权限关联表';

-- ==============================================================
-- 11. Enrollment 活动报名表
-- ==============================================================

CREATE TABLE IF NOT EXISTS enrollment (
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '报名ID',
  activity_id BIGINT NOT NULL COMMENT '活动ID',
  elderly_id BIGINT NOT NULL COMMENT '老人ID',
  status VARCHAR(50) DEFAULT 'pending' COMMENT '报名状态: pending(待确认), confirmed(已确认), attended(已签到), absent(未参加), cancelled(已取消)',
  enroll_time DATETIME COMMENT '报名时间',
  check_in_time DATETIME COMMENT '签到时间',
  notes TEXT COMMENT '备注说明',
  is_active TINYINT(1) DEFAULT 1 COMMENT '是否激活',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  FOREIGN KEY (activity_id) REFERENCES activity(id) ON DELETE CASCADE,
  FOREIGN KEY (elderly_id) REFERENCES elderly(id) ON DELETE CASCADE,
  INDEX idx_activity_id (activity_id),
  INDEX idx_elderly_id (elderly_id),
  INDEX idx_status (status),
  UNIQUE KEY uk_activity_elderly (activity_id, elderly_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='活动报名表';

-- ==============================================================
-- 脚本执行完毕
-- ==============================================================
