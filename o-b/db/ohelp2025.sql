-- 智慧养老服务管理系统数据库脚本 - 优化版
-- 创建数据库
CREATE DATABASE IF NOT EXISTS Ohelp2025 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE Ohelp2025;

-- 用户表
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码（加密后）',
    salt VARCHAR(32) COMMENT '密码盐值',
    email VARCHAR(100) UNIQUE COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '手机号',
    real_name VARCHAR(50) COMMENT '真实姓名',
    age INT COMMENT '年龄',
    gender ENUM('MALE', 'FEMALE', 'OTHER') COMMENT '性别',
    avatar VARCHAR(500) COMMENT '头像URL',
    role VARCHAR(20) NOT NULL COMMENT '角色',
    is_active BOOLEAN DEFAULT TRUE COMMENT '是否激活',
    login_attempts INT DEFAULT 0 COMMENT '登录尝试次数',
    locked_until DATETIME COMMENT '锁定结束时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    last_login_time DATETIME COMMENT '最后登录时间',
    INDEX idx_username (username),
    INDEX idx_email (email),
    INDEX idx_phone (phone),
    INDEX idx_role (role),
    INDEX idx_create_time (create_time),
    INDEX idx_last_login_time (last_login_time)
) COMMENT='用户表';

-- 老人表
CREATE TABLE elderly (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '老人ID',
    uuid VARCHAR(50) NOT NULL UNIQUE COMMENT '老人唯一标识',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '登录用户名',
    password VARCHAR(255) NOT NULL COMMENT '登录密码',
    salt VARCHAR(32) COMMENT '密码盐值',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    age INT COMMENT '年龄',
    gender ENUM('MALE', 'FEMALE', 'OTHER') COMMENT '性别',
    phone VARCHAR(20) COMMENT '联系电话',
    id_number VARCHAR(18) UNIQUE COMMENT '身份证号',
    address TEXT COMMENT '地址',
    avatar VARCHAR(500) COMMENT '头像URL',
    emergency_contact_name VARCHAR(50) COMMENT '紧急联系人姓名',
    emergency_contact_phone VARCHAR(20) COMMENT '紧急联系人电话',
    health_status VARCHAR(100) COMMENT '健康状况',
    special_medical_needs TEXT COMMENT '特殊医疗需求',
    admission_date DATE COMMENT '入住日期',
    is_active BOOLEAN DEFAULT TRUE COMMENT '是否激活',
    login_attempts INT DEFAULT 0 COMMENT '登录尝试次数',
    locked_until DATETIME COMMENT '锁定结束时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_uuid (uuid),
    INDEX idx_username (username),
    INDEX idx_name (name),
    INDEX idx_phone (phone),
    INDEX idx_id_number (id_number),
    INDEX idx_create_time (create_time)
) COMMENT='老人表';

-- 工作人员表
CREATE TABLE staff (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '工作人员ID',
    uuid VARCHAR(50) NOT NULL UNIQUE COMMENT '工作人员唯一标识',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '登录用户名',
    password VARCHAR(255) NOT NULL COMMENT '登录密码',
    salt VARCHAR(32) COMMENT '密码盐值',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    phone VARCHAR(20) COMMENT '联系电话',
    id_number VARCHAR(18) UNIQUE COMMENT '身份证号',
    is_deleted TINYINT DEFAULT 0 COMMENT '是否删除：1-已删除，0-未删除',
    login_attempts INT DEFAULT 0 COMMENT '登录尝试次数',
    locked_until DATETIME COMMENT '锁定结束时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_uuid (uuid),
    INDEX idx_username (username),
    INDEX idx_name (name),
    INDEX idx_phone (phone),
    INDEX idx_id_number (id_number),
    INDEX idx_is_deleted (is_deleted)
) COMMENT='工作人员表';

-- 服务类型表
CREATE TABLE service_types (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '服务类型ID',
    name VARCHAR(100) NOT NULL COMMENT '类型名称',
    description TEXT COMMENT '类型描述',
    image_url VARCHAR(500) COMMENT '类型图片URL',
    status TINYINT DEFAULT 1 COMMENT '上下架状态：1-上架，0-下架',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_name (name),
    INDEX idx_status (status)
) COMMENT='服务类型表';

-- 服务项目表
CREATE TABLE service_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '服务项目ID',
    name VARCHAR(100) NOT NULL COMMENT '项目名称',
    photo_url VARCHAR(500) COMMENT '项目图片URL',
    price DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '价格',
    stock INT DEFAULT 0 COMMENT '库存数量',
    description TEXT COMMENT '项目描述',
    type_id BIGINT NOT NULL COMMENT '所属服务类型ID',
    status TINYINT DEFAULT 1 COMMENT '上下架状态：1-上架，0-下架',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (type_id) REFERENCES service_types(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX idx_name (name),
    INDEX idx_type_id (type_id),
    INDEX idx_status (status),
    INDEX idx_create_time (create_time)
) COMMENT='服务项目表';

-- 服务购买表
CREATE TABLE service_purchases (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '购买记录ID',
    uuid VARCHAR(50) NOT NULL UNIQUE COMMENT '购买记录唯一标识',
    service_item_name VARCHAR(100) NOT NULL COMMENT '服务项目名称',
    service_type_name VARCHAR(100) NOT NULL COMMENT '服务类型名称',
    elderly_uuid VARCHAR(50) NOT NULL COMMENT '老人UUID',
    elderly_name VARCHAR(50) NOT NULL COMMENT '老人姓名',
    quantity DECIMAL(10,2) NOT NULL DEFAULT 1.00 COMMENT '购买数量',
    original_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '原始金额',
    final_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '最终金额',
    unit_price DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '单价',
    address TEXT COMMENT '服务地址',
    content TEXT COMMENT '购买内容',
    status ENUM('PENDING', 'CONFIRMED', 'COMPLETED', 'CANCELLED') DEFAULT 'PENDING' COMMENT '购买状态',
    reply_content TEXT COMMENT '回复内容',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (elderly_uuid) REFERENCES elderly(uuid) ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX idx_uuid (uuid),
    INDEX idx_elderly_uuid (elderly_uuid),
    INDEX idx_elderly_name (elderly_name),
    INDEX idx_status (status),
    INDEX idx_create_time (create_time)
) COMMENT='服务购买表';

-- 活动分类表
CREATE TABLE activity_categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '活动分类ID',
    name VARCHAR(100) NOT NULL COMMENT '分类名称',
    image_url VARCHAR(500) COMMENT '分类图片URL',
    status TINYINT DEFAULT 1 COMMENT '上下架状态：1-上架，0-下架',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_name (name),
    INDEX idx_status (status)
) COMMENT='活动分类表';

-- 活动信息表
CREATE TABLE activities (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '活动ID',
    name VARCHAR(100) NOT NULL COMMENT '活动名称',
    photo_url VARCHAR(500) COMMENT '活动图片URL',
    address TEXT COMMENT '活动地址',
    activity_time DATETIME COMMENT '活动时间',
    fee DECIMAL(10,2) DEFAULT 0.00 COMMENT '活动费用',
    content TEXT COMMENT '活动内容',
    category_id BIGINT NOT NULL COMMENT '活动分类ID',
    status ENUM('PENDING', 'CONFIRMED', 'COMPLETED', 'CANCELLED') DEFAULT 'PENDING' COMMENT '活动状态',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (category_id) REFERENCES activity_categories(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX idx_name (name),
    INDEX idx_category_id (category_id),
    INDEX idx_activity_time (activity_time),
    INDEX idx_status (status),
    INDEX idx_create_time (create_time)
) COMMENT='活动信息表';

-- 每日健康表
CREATE TABLE daily_health (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '健康记录ID',
    name VARCHAR(100) NOT NULL COMMENT '记录名称',
    photo_url VARCHAR(500) COMMENT '相关图片URL',
    content TEXT COMMENT '健康内容',
    is_deleted TINYINT DEFAULT 0 COMMENT '是否删除：1-已删除，0-未删除',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_name (name),
    INDEX idx_is_deleted (is_deleted),
    INDEX idx_create_time (create_time)
) COMMENT='每日健康表';

-- 既往病史表
CREATE TABLE medical_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '病史记录ID',
    elderly_uuid VARCHAR(50) NOT NULL COMMENT '老人UUID',
    elderly_name VARCHAR(50) NOT NULL COMMENT '老人姓名',
    illness_name VARCHAR(100) NOT NULL COMMENT '疾病名称',
    description TEXT COMMENT '疾病描述',
    is_deleted TINYINT DEFAULT 0 COMMENT '是否删除：1-已删除，0-未删除',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (elderly_uuid) REFERENCES elderly(uuid) ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX idx_elderly_uuid (elderly_uuid),
    INDEX idx_elderly_name (elderly_name),
    INDEX idx_illness_name (illness_name),
    INDEX idx_is_deleted (is_deleted),
    INDEX idx_create_time (create_time)
) COMMENT='既往病史表';

-- 积分管理表
CREATE TABLE points_management (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '积分记录ID',
    elderly_uuid VARCHAR(50) NOT NULL COMMENT '老人UUID',
    elderly_name VARCHAR(50) NOT NULL COMMENT '老人姓名',
    reason VARCHAR(100) NOT NULL COMMENT '积分原因',
    points DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '积分变化值',
    description TEXT COMMENT '积分描述',
    is_deleted TINYINT DEFAULT 0 COMMENT '是否删除：1-已删除，0-未删除',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (elderly_uuid) REFERENCES elderly(uuid) ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX idx_elderly_uuid (elderly_uuid),
    INDEX idx_elderly_name (elderly_name),
    INDEX idx_reason (reason),
    INDEX idx_is_deleted (is_deleted),
    INDEX idx_create_time (create_time)
) COMMENT='积分管理表';

-- 亲人表
CREATE TABLE relatives (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '亲人记录ID',
    elderly_uuid VARCHAR(50) NOT NULL COMMENT '关联老人UUID',
    elderly_name VARCHAR(50) NOT NULL COMMENT '关联老人姓名',
    name VARCHAR(50) NOT NULL COMMENT '亲人姓名',
    phone VARCHAR(20) COMMENT '亲人电话',
    id_number VARCHAR(18) COMMENT '亲人身份证号',
    is_deleted TINYINT DEFAULT 0 COMMENT '是否删除：1-已删除，0-未删除',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (elderly_uuid) REFERENCES elderly(uuid) ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX idx_elderly_uuid (elderly_uuid),
    INDEX idx_elderly_name (elderly_name),
    INDEX idx_phone (phone),
    INDEX idx_is_deleted (is_deleted),
    INDEX idx_create_time (create_time)
) COMMENT='亲人表';

-- 紧急求助表
CREATE TABLE emergency_requests (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '求助记录ID',
    elderly_uuid VARCHAR(50) NOT NULL COMMENT '求助老人UUID',
    elderly_name VARCHAR(50) NOT NULL COMMENT '求助老人姓名',
    content TEXT NOT NULL COMMENT '求助内容',
    is_deleted TINYINT DEFAULT 0 COMMENT '是否删除：1-已删除，0-未删除',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (elderly_uuid) REFERENCES elderly(uuid) ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX idx_elderly_uuid (elderly_uuid),
    INDEX idx_elderly_name (elderly_name),
    INDEX idx_create_time (create_time)
) COMMENT='紧急求助表';

-- 服务请求表
CREATE TABLE service_requests (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '服务请求ID',
    title VARCHAR(200) NOT NULL COMMENT '请求标题',
    description TEXT COMMENT '请求描述',
    type VARCHAR(50) COMMENT '服务类型',
    priority ENUM('LOW', 'MEDIUM', 'HIGH', 'URGENT') DEFAULT 'MEDIUM' COMMENT '优先级',
    elderly_id BIGINT NOT NULL COMMENT '老人ID',
    assigned_staff_id BIGINT COMMENT '分配的工作人员ID',
    status ENUM('PENDING', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED') DEFAULT 'PENDING' COMMENT '状态',
    request_date DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '请求日期',
    scheduled_date DATETIME COMMENT '预约日期',
    completed_date DATETIME COMMENT '完成日期',
    notes TEXT COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (elderly_id) REFERENCES elderly(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (assigned_staff_id) REFERENCES staff(id) ON DELETE SET NULL ON UPDATE CASCADE,
    INDEX idx_elderly_id (elderly_id),
    INDEX idx_assigned_staff_id (assigned_staff_id),
    INDEX idx_type (type),
    INDEX idx_priority (priority),
    INDEX idx_status (status),
    INDEX idx_request_date (request_date),
    INDEX idx_create_time (create_time)
) COMMENT='服务请求表';

-- 访问令牌表
CREATE TABLE access_tokens (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '令牌ID',
    token VARCHAR(500) NOT NULL UNIQUE COMMENT '令牌值',
    user_id VARCHAR(50) NOT NULL COMMENT '用户ID',
    user_type ENUM('USER', 'ELDERLY', 'STAFF') COMMENT '用户类型',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    expire_time DATETIME COMMENT '过期时间',
    is_active BOOLEAN DEFAULT TRUE COMMENT '是否激活',
    last_access_time DATETIME COMMENT '最后访问时间',
    INDEX idx_token (token),
    INDEX idx_user_id (user_id),
    INDEX idx_user_type (user_type),
    INDEX idx_expire_time (expire_time),
    INDEX idx_is_active (is_active)
) COMMENT='访问令牌表';

-- 照料计划表
CREATE TABLE care_plans (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '照料计划ID',
    uuid VARCHAR(50) NOT NULL UNIQUE COMMENT '计划唯一标识',
    title VARCHAR(200) NOT NULL COMMENT '计划标题',
    description TEXT COMMENT '计划描述',
    type VARCHAR(50) COMMENT '计划类型',
    elderly_id BIGINT NOT NULL COMMENT '老人ID',
    elderly_name VARCHAR(50) NOT NULL COMMENT '老人姓名',
    assigned_staff_id BIGINT COMMENT '分配的工作人员ID',
    status ENUM('PENDING', 'IN_PROGRESS', 'COMPLETED') DEFAULT 'PENDING' COMMENT '状态',
    scheduled_time DATETIME COMMENT '计划执行时间',
    completed_time DATETIME COMMENT '完成时间',
    notes TEXT COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (elderly_id) REFERENCES elderly(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (assigned_staff_id) REFERENCES staff(id) ON DELETE SET NULL ON UPDATE CASCADE,
    INDEX idx_uuid (uuid),
    INDEX idx_elderly_id (elderly_id),
    INDEX idx_assigned_staff_id (assigned_staff_id),
    INDEX idx_type (type),
    INDEX idx_status (status),
    INDEX idx_scheduled_time (scheduled_time),
    INDEX idx_create_time (create_time)
) COMMENT='照料计划表';

-- 服务评价表
CREATE TABLE service_evaluations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '评价ID',
    uuid VARCHAR(50) NOT NULL UNIQUE COMMENT '评价唯一标识',
    service_id BIGINT COMMENT '服务ID',
    service_name VARCHAR(100) COMMENT '服务名称',
    elderly_id BIGINT NOT NULL COMMENT '老人ID',
    elderly_name VARCHAR(50) NOT NULL COMMENT '老人姓名',
    staff_id BIGINT COMMENT '工作人员ID',
    staff_name VARCHAR(50) COMMENT '工作人员姓名',
    rating TINYINT CHECK (rating >= 1 AND rating <= 5) COMMENT '评分（1-5星）',
    comment TEXT COMMENT '评价内容',
    service_type VARCHAR(50) COMMENT '服务类型',
    evaluation_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '评价时间',
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE' COMMENT '状态',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (elderly_id) REFERENCES elderly(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (staff_id) REFERENCES staff(id) ON DELETE SET NULL ON UPDATE CASCADE,
    INDEX idx_uuid (uuid),
    INDEX idx_elderly_id (elderly_id),
    INDEX idx_staff_id (staff_id),
    INDEX idx_service_id (service_id),
    INDEX idx_rating (rating),
    INDEX idx_evaluation_time (evaluation_time),
    INDEX idx_create_time (create_time)
) COMMENT='服务评价表';

-- 家属探望登记表
CREATE TABLE visit_registrations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '探望记录ID',
    uuid VARCHAR(50) NOT NULL UNIQUE COMMENT '记录唯一标识',
    visitor_name VARCHAR(50) NOT NULL COMMENT '探访者姓名',
    visitor_phone VARCHAR(20) COMMENT '探访者电话',
    relationship VARCHAR(30) COMMENT '与老人关系',
    elderly_id BIGINT NOT NULL COMMENT '被探访老人ID',
    elderly_name VARCHAR(50) NOT NULL COMMENT '被探访老人姓名',
    visit_time DATETIME COMMENT '探访时间',
    leave_time DATETIME COMMENT '离开时间',
    visit_reason VARCHAR(200) COMMENT '探访原因',
    status ENUM('PENDING', 'CONFIRMED', 'COMPLETED', 'CANCELLED') DEFAULT 'PENDING' COMMENT '状态',
    notes TEXT COMMENT '备注',
    staff_id VARCHAR(50) COMMENT '操作工作人员ID',
    staff_name VARCHAR(50) COMMENT '操作工作人员姓名',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (elderly_id) REFERENCES elderly(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX idx_uuid (uuid),
    INDEX idx_elderly_id (elderly_id),
    INDEX idx_visitor_name (visitor_name),
    INDEX idx_visitor_phone (visitor_phone),
    INDEX idx_status (status),
    INDEX idx_visit_time (visit_time),
    INDEX idx_create_time (create_time)
) COMMENT='家属探望登记表';

-- 审计日志表 - 用于记录数据变更历史
CREATE TABLE audit_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '日志ID',
    table_name VARCHAR(50) NOT NULL COMMENT '表名',
    record_id VARCHAR(50) NOT NULL COMMENT '记录ID',
    operation ENUM('INSERT', 'UPDATE', 'DELETE') NOT NULL COMMENT '操作类型',
    old_values JSON COMMENT '旧值',
    new_values JSON COMMENT '新值',
    user_id VARCHAR(50) COMMENT '操作用户ID',
    user_type ENUM('USER', 'ELDERLY', 'STAFF') COMMENT '用户类型',
    ip_address VARCHAR(45) COMMENT 'IP地址',
    user_agent TEXT COMMENT '用户代理',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_table_name (table_name),
    INDEX idx_record_id (record_id),
    INDEX idx_operation (operation),
    INDEX idx_create_time (create_time),
    INDEX idx_user_id (user_id)
) COMMENT='审计日志表';

-- 数据库安全配置
-- 设置密码复杂度要求
SET GLOBAL validate_password.policy = MEDIUM;
SET GLOBAL validate_password.length = 8;

-- 插入默认管理员用户 (密码为admin，使用BCrypt加密)
INSERT INTO users (username, password, salt, real_name, role, create_time) 
VALUES ('admin', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 
        SUBSTRING(MD5(RAND()), 1, 32), '系统管理员', 'ADMIN', NOW());

-- 插入普通用户 (密码为admin)
INSERT INTO users (username, password, salt, real_name, role, create_time) 
VALUES ('user1', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 
        SUBSTRING(MD5(RAND()), 1, 32), '普通用户', 'USER', NOW());

-- 插入家属用户 (密码为admin)
INSERT INTO users (username, password, salt, real_name, role, create_time) 
VALUES ('relative1', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 
        SUBSTRING(MD5(RAND()), 1, 32), '家属用户', 'RELATIVE', NOW());

-- 插入默认老人 (密码为admin)
INSERT INTO elderly (uuid, username, password, salt, name, create_time) 
VALUES ('LR000001', 'laoren', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 
        SUBSTRING(MD5(RAND()), 1, 32), '默认老人', NOW());

-- 插入测试工作人员数据 (密码均为admin)
INSERT INTO staff (uuid, username, password, salt, name, phone, id_number, create_time) 
VALUES 
('ST000001', 'nurse1', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 
 SUBSTRING(MD5(RAND()), 1, 32), '张护士', '13800138001', '110101198001011234', NOW()),
('ST000002', 'nurse2', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 
 SUBSTRING(MD5(RAND()), 1, 32), '李护士', '13800138002', '110101198002021234', NOW()),
('ST000003', 'doctor1', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 
 SUBSTRING(MD5(RAND()), 1, 32), '王医生', '13800138003', '110101197501011234', NOW());

-- 插入测试服务类型数据
INSERT INTO service_types (name, description, status, create_time) 
VALUES 
('生活照料', '日常生活照料服务，包括洗浴、穿衣、进食等', 1, NOW()),
('医疗护理', '专业医疗服务，包括用药指导、健康监测等', 1, NOW()),
('康复理疗', '康复训练和物理治疗服务', 1, NOW()),
('心理关怀', '心理支持和情感陪伴服务', 1, NOW()),
('文化娱乐', '文化活动和娱乐服务', 1, NOW());

-- 插入测试服务项目数据
INSERT INTO service_items (name, price, stock, description, type_id, status, create_time) 
SELECT 
    name, price, stock, description, id, status, create_time
FROM (
    SELECT '日常洗浴服务' as name, 80.00 as price, 100 as stock, '专业洗浴护理服务' as description, 
           (SELECT id FROM service_types WHERE name = '生活照料') as id, 1 as status, NOW() as create_time
    UNION ALL
    SELECT '日常护理' as name, 120.00 as price, 100 as stock, '日常起居护理服务' as description, 
           (SELECT id FROM service_types WHERE name = '生活照料') as id, 1 as status, NOW() as create_time
    UNION ALL
    SELECT '健康监测' as name, 50.00 as price, 100 as stock, '血压、血糖等健康指标监测' as description, 
           (SELECT id FROM service_types WHERE name = '医疗护理') as id, 1 as status, NOW() as create_time
    UNION ALL
    SELECT '康复训练' as name, 100.00 as price, 100 as stock, '专业康复训练服务' as description, 
           (SELECT id FROM service_types WHERE name = '康复理疗') as id, 1 as status, NOW() as create_time
    UNION ALL
    SELECT '心理咨询' as name, 150.00 as price, 100 as stock, '专业心理咨询服务' as description, 
           (SELECT id FROM service_types WHERE name = '心理关怀') as id, 1 as status, NOW() as create_time
    UNION ALL
    SELECT '书法课程' as name, 30.00 as price, 100 as stock, '老年书法兴趣课程' as description, 
           (SELECT id FROM service_types WHERE name = '文化娱乐') as id, 1 as status, NOW() as create_time
) AS service_items_data;

-- 插入测试老人数据 (密码均为admin)
INSERT INTO elderly (uuid, username, password, salt, name, age, gender, phone, id_number, address, create_time) 
VALUES 
('LR000002', 'laoren2', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 
 SUBSTRING(MD5(RAND()), 1, 32), '李爷爷', 78, 'MALE', '13900139001', '110101194501011234', '北京市朝阳区养老院1号楼101室', NOW()),
('LR000003', 'laoren3', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 
 SUBSTRING(MD5(RAND()), 1, 32), '王奶奶', 75, 'FEMALE', '13900139002', '110101194802021234', '北京市朝阳区养老院1号楼102室', NOW()),
('LR000004', 'laoren4', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 
 SUBSTRING(MD5(RAND()), 1, 32), '张爷爷', 82, 'MALE', '13900139003', '110101194103031234', '北京市朝阳区养老院1号楼103室', NOW());

-- 插入测试服务请求数据
INSERT INTO service_requests (title, description, type, priority, elderly_id, assigned_staff_id, status, create_time) 
VALUES 
('日常洗浴服务', '每周三次的日常洗浴服务', '生活照料', 'MEDIUM', 2, 1, 'PENDING', NOW()),
('健康监测', '每日血压监测', '医疗护理', 'HIGH', 3, 3, 'IN_PROGRESS', NOW()),
('康复训练', '膝关节康复训练', '康复理疗', 'MEDIUM', 4, 2, 'PENDING', NOW());

-- 插入测试照料计划数据
INSERT INTO care_plans (uuid, title, description, type, elderly_id, elderly_name, assigned_staff_id, status, scheduled_time, create_time) 
VALUES 
('CP000001', '日常护理计划', '每日日常护理安排', '生活照料', 2, '李爷爷', 1, 'PENDING', DATE_ADD(NOW(), INTERVAL 1 DAY), NOW()),
('CP000002', '健康监测计划', '每日健康指标监测', '医疗护理', 3, '王奶奶', 3, 'IN_PROGRESS', DATE_ADD(NOW(), INTERVAL 1 DAY), NOW()),
('CP000003', '康复训练计划', '每周三次康复训练', '康复理疗', 4, '张爷爷', 2, 'PENDING', DATE_ADD(NOW(), INTERVAL 1 DAY), NOW());

-- 插入测试亲人数据
INSERT INTO relatives (elderly_uuid, elderly_name, name, phone, id_number, create_time) 
VALUES 
('LR000002', '李爷爷', '李小明', '13800138004', '110101197501011235', NOW()),
('LR000003', '王奶奶', '王小红', '13800138005', '110101197802021235', NOW()),
('LR000004', '张爷爷', '张小华', '13800138006', '110101198003031235', NOW());

-- 插入测试活动分类数据
INSERT INTO activity_categories (name, status, create_time) 
VALUES 
('文化活动', 1, NOW()),
('健康讲座', 1, NOW()),
('户外旅游', 1, NOW()),
('手工制作', 1, NOW());

-- 插入测试活动信息数据
INSERT INTO activities (name, address, activity_time, fee, content, category_id, status, create_time) 
VALUES 
('书法培训班', '北京市朝阳区养老院多功能厅', DATE_ADD(NOW(), INTERVAL 2 DAY), 0.00, '每周二、四下午举办书法培训活动', 1, 'CONFIRMED', NOW()),
('健康讲座 - 高血压防治', '北京市朝阳区养老院会议室', DATE_ADD(NOW(), INTERVAL 3 DAY), 0.00, '专业医生讲解高血压防治知识', 2, 'CONFIRMED', NOW()),
('秋季公园游', '北京市朝阳公园', DATE_ADD(NOW(), INTERVAL 5 DAY), 50.00, '组织老人秋季公园游览活动', 3, 'PENDING', NOW()),
('手工编织活动', '北京市朝阳区养老院活动室', DATE_ADD(NOW(), INTERVAL 7 DAY), 0.00, '学习编织技巧，制作手工艺品', 4, 'CONFIRMED', NOW());

-- 插入测试家属探望登记数据
INSERT INTO visit_registrations (uuid, visitor_name, visitor_phone, relationship, elderly_id, elderly_name, visit_time, status, create_time) 
VALUES 
('VR000001', '李小明', '13800138004', '孙子', 2, '李爷爷', DATE_ADD(NOW(), INTERVAL 1 DAY), 'CONFIRMED', NOW()),
('VR000002', '王小红', '13800138005', '孙女', 3, '王奶奶', DATE_ADD(NOW(), INTERVAL 2 DAY), 'PENDING', NOW()),
('VR000003', '张小华', '13800138006', '儿子', 4, '张爷爷', DATE_ADD(NOW(), INTERVAL 3 DAY), 'CONFIRMED', NOW()),
('VR000004', '李大明', '13800138007', '儿子', 2, '李爷爷', DATE_ADD(NOW(), INTERVAL 4 DAY), 'PENDING', NOW());

-- 插入测试积分管理数据
INSERT INTO points_management (elderly_uuid, elderly_name, reason, points, description, create_time) 
VALUES 
('LR000002', '李爷爷', '参加活动', 50.00, '参加书法培训班获得积分', NOW()),
('LR000003', '王奶奶', '健康打卡', 20.00, '连续7天健康打卡获得积分', NOW()),
('LR000004', '张爷爷', '服务评价', 30.00, '对服务进行评价获得积分', NOW()),
('LR000002', '李爷爷', '生日奖励', 100.00, '生日当天获得积分奖励', NOW());

-- 插入测试紧急求助数据
INSERT INTO emergency_requests (elderly_uuid, elderly_name, content, create_time) 
VALUES 
('LR000002', '李爷爷', '身体不适，需要医生检查', NOW()),
('LR000003', '王奶奶', '摔倒了，需要帮助', DATE_SUB(NOW(), INTERVAL 2 DAY));

-- 插入测试每日健康数据
INSERT INTO daily_health (name, content, create_time) 
VALUES 
('健康小贴士', '老年人应保持充足的水分摄入，每天至少饮用1500ml水', NOW()),
('营养建议', '建议多食用富含膳食纤维的食物，如蔬菜、水果和全谷物', NOW()),
('运动指导', '适当的散步有助于保持身体健康，建议每天散步30分钟', NOW());

-- 插入测试既往病史数据
INSERT INTO medical_history (elderly_uuid, elderly_name, illness_name, description, create_time) 
VALUES 
('LR000002', '李爷爷', '高血压', '高血压病史10年，需长期服药', NOW()),
('LR000003', '王奶奶', '糖尿病', '2型糖尿病，需控制饮食和运动', NOW()),
('LR000004', '张爷爷', '关节炎', '膝关节关节炎，需定期理疗', NOW());

-- 插入测试服务评价数据
INSERT INTO service_evaluations (uuid, service_name, elderly_id, elderly_name, staff_id, staff_name, rating, comment, service_type, create_time) 
VALUES 
('SE000001', '日常洗浴服务', 2, '李爷爷', 1, '张护士', 5, '服务态度好，专业细致', '生活照料', NOW()),
('SE000002', '健康监测', 3, '王奶奶', 3, '王医生', 4, '服务及时，专业可靠', '医疗护理', NOW()),
('SE000003', '康复训练', 4, '张爷爷', 2, '李护士', 5, '耐心指导，效果明显', '康复理疗', NOW());

-- 插入更多测试服务请求数据
INSERT INTO service_requests (title, description, type, priority, elderly_id, assigned_staff_id, status, create_time) 
VALUES 
('理发服务', '需要定期理发服务', '生活照料', 'LOW', 2, 1, 'PENDING', NOW()),
('心理咨询', '需要心理疏导服务', '心理关怀', 'MEDIUM', 3, 2, 'PENDING', NOW()),
('日常护理', '需要日常起居护理', '生活照料', 'MEDIUM', 4, 1, 'COMPLETED', NOW()),
('文化娱乐', '需要参加文化活动', '文化娱乐', 'LOW', 2, 3, 'PENDING', NOW());

-- 创建存储过程用于用户登录尝试限制
DELIMITER //

CREATE PROCEDURE lock_account_if_needed(
    IN p_username VARCHAR(50),
    IN p_user_type ENUM('USER', 'ELDERLY', 'STAFF'),
    OUT p_success BOOLEAN
)
BEGIN
    DECLARE v_attempts INT DEFAULT 0;
    DECLARE v_locked_until DATETIME;
    
    SET p_success = FALSE;
    
    CASE p_user_type
        WHEN 'USER' THEN
            SELECT login_attempts, locked_until INTO v_attempts, v_locked_until
            FROM users WHERE username = p_username;
            
            IF v_locked_until IS NOT NULL AND v_locked_until > NOW() THEN
                SET p_success = FALSE;
            ELSE
                UPDATE users 
                SET login_attempts = login_attempts + 1,
                    locked_until = CASE 
                        WHEN login_attempts + 1 >= 5 THEN DATE_ADD(NOW(), INTERVAL 30 MINUTE)
                        ELSE NULL
                    END
                WHERE username = p_username;
                
                SET p_success = TRUE;
            END IF;
        
        WHEN 'ELDERLY' THEN
            SELECT login_attempts, locked_until INTO v_attempts, v_locked_until
            FROM elderly WHERE username = p_username;
            
            IF v_locked_until IS NOT NULL AND v_locked_until > NOW() THEN
                SET p_success = FALSE;
            ELSE
                UPDATE elderly 
                SET login_attempts = login_attempts + 1,
                    locked_until = CASE 
                        WHEN login_attempts + 1 >= 5 THEN DATE_ADD(NOW(), INTERVAL 30 MINUTE)
                        ELSE NULL
                    END
                WHERE username = p_username;
                
                SET p_success = TRUE;
            END IF;
        
        WHEN 'STAFF' THEN
            SELECT login_attempts, locked_until INTO v_attempts, v_locked_until
            FROM staff WHERE username = p_username;
            
            IF v_locked_until IS NOT NULL AND v_locked_until > NOW() THEN
                SET p_success = FALSE;
            ELSE
                UPDATE staff 
                SET login_attempts = login_attempts + 1,
                    locked_until = CASE 
                        WHEN login_attempts + 1 >= 5 THEN DATE_ADD(NOW(), INTERVAL 30 MINUTE)
                        ELSE NULL
                    END
                WHERE username = p_username;
                
                SET p_success = TRUE;
            END IF;
    END CASE;
END //

CREATE PROCEDURE reset_login_attempts(
    IN p_username VARCHAR(50),
    IN p_user_type ENUM('USER', 'ELDERLY', 'STAFF')
)
BEGIN
    CASE p_user_type
        WHEN 'USER' THEN
            UPDATE users 
            SET login_attempts = 0, locked_until = NULL, last_login_time = NOW()
            WHERE username = p_username;
        
        WHEN 'ELDERLY' THEN
            UPDATE elderly 
            SET login_attempts = 0, locked_until = NULL, last_login_time = NOW()
            WHERE username = p_username;
        
        WHEN 'STAFF' THEN
            UPDATE staff 
            SET login_attempts = 0, locked_until = NULL, last_login_time = NOW()
            WHERE username = p_username;
    END CASE;
END //

CREATE PROCEDURE update_elderly_timestamp(IN p_id BIGINT)
BEGIN
    UPDATE elderly SET update_time = CURRENT_TIMESTAMP WHERE id = p_id;
END //

CREATE PROCEDURE update_user_timestamp(IN p_id BIGINT)
BEGIN
    UPDATE users SET update_time = CURRENT_TIMESTAMP WHERE id = p_id;
END //

DELIMITER ;

-- 验证查询 - 检查表结构和数据
-- 1. 检查所有表是否创建成功
SELECT 'users' as table_name, COUNT(*) as record_count FROM users
UNION ALL
SELECT 'elderly' as table_name, COUNT(*) as record_count FROM elderly
UNION ALL
SELECT 'staff' as table_name, COUNT(*) as record_count FROM staff
UNION ALL
SELECT 'service_types' as table_name, COUNT(*) as record_count FROM service_types
UNION ALL
SELECT 'service_items' as table_name, COUNT(*) as record_count FROM service_items
UNION ALL
SELECT 'service_requests' as table_name, COUNT(*) as record_count FROM service_requests
UNION ALL
SELECT 'care_plans' as table_name, COUNT(*) as record_count FROM care_plans
UNION ALL
SELECT 'relatives' as table_name, COUNT(*) as record_count FROM relatives
UNION ALL
SELECT 'activity_categories' as table_name, COUNT(*) as record_count FROM activity_categories
UNION ALL
SELECT 'activities' as table_name, COUNT(*) as record_count FROM activities
UNION ALL
SELECT 'visit_registrations' as table_name, COUNT(*) as record_count FROM visit_registrations
UNION ALL
SELECT 'points_management' as table_name, COUNT(*) as record_count FROM points_management
UNION ALL
SELECT 'emergency_requests' as table_name, COUNT(*) as record_count FROM emergency_requests
UNION ALL
SELECT 'daily_health' as table_name, COUNT(*) as record_count FROM daily_health
UNION ALL
SELECT 'medical_history' as table_name, COUNT(*) as record_count FROM medical_history
UNION ALL
SELECT 'service_evaluations' as table_name, COUNT(*) as record_count FROM service_evaluations;

-- 2. 检查索引是否存在
SHOW INDEX FROM users;
SHOW INDEX FROM elderly;
SHOW INDEX FROM staff;
SHOW INDEX FROM service_types;
SHOW INDEX FROM service_items;
SHOW INDEX FROM service_requests;
SHOW INDEX FROM care_plans;
SHOW INDEX FROM relatives;
SHOW INDEX FROM activity_categories;
SHOW INDEX FROM activities;
SHOW INDEX FROM visit_registrations;
SHOW INDEX FROM points_management;
SHOW INDEX FROM emergency_requests;
SHOW INDEX FROM daily_health;
SHOW INDEX FROM medical_history;
SHOW INDEX FROM service_evaluations;

-- 3. 测试外键约束
SELECT 
    CONSTRAINT_NAME,
    TABLE_NAME,
    COLUMN_NAME,
    REFERENCED_TABLE_NAME,
    REFERENCED_COLUMN_NAME
FROM information_schema.KEY_COLUMN_USAGE
WHERE TABLE_SCHEMA = 'Ohelp2025' 
AND REFERENCED_TABLE_NAME IS NOT NULL;

-- 4. 测试存储过程
CALL reset_login_attempts('admin', 'USER');

-- 5. 测试审计日志（如果触发器工作正常，这将自动记录）
UPDATE users SET real_name = '系统管理员(已更新)' WHERE username = 'admin';

-- 创建触发器用于审计日志记录
DELIMITER //

-- 示例触发器：记录users表的变更
CREATE TRIGGER tr_users_audit_insert
    AFTER INSERT ON users
    FOR EACH ROW
BEGIN
    INSERT INTO audit_logs (table_name, record_id, operation, new_values, user_type)
    VALUES ('users', NEW.id, 'INSERT', 
            JSON_OBJECT('username', NEW.username, 'real_name', NEW.real_name, 'role', NEW.role), 
            'USER');
END//

CREATE TRIGGER tr_users_audit_update
    AFTER UPDATE ON users
    FOR EACH ROW
BEGIN
    INSERT INTO audit_logs (table_name, record_id, operation, old_values, new_values, user_type)
    VALUES ('users', NEW.id, 'UPDATE', 
            JSON_OBJECT('username', OLD.username, 'real_name', OLD.real_name, 'role', OLD.role),
            JSON_OBJECT('username', NEW.username, 'real_name', NEW.real_name, 'role', NEW.role),
            'USER');
END//

CREATE TRIGGER tr_users_audit_delete
    AFTER DELETE ON users
    FOR EACH ROW
BEGIN
    INSERT INTO audit_logs (table_name, record_id, operation, old_values, user_type)
    VALUES ('users', OLD.id, 'DELETE', 
            JSON_OBJECT('username', OLD.username, 'real_name', OLD.real_name, 'role', OLD.role), 
            'USER');
END//

DELIMITER ;