-- Active: 1765811423701@@127.0.0.1@3306@ohelp
-- ==============================================================
-- Ohelp2025 数据库初始化数据脚本（示例数据）
-- ==============================================================
-- 注意: 这是示例数据，用于测试和演示目的

USE ohelp;

-- ==============================================================
-- 1. 插入用户数据
-- ==============================================================

INSERT INTO user (name, email, phone, role, is_active) VALUES
('张三', 'zhangsan@example.com', '13800138001', 'admin', 1),
('李四', 'lisi@example.com', '13800138002', 'manager', 1),
('王五', 'wangwu@example.com', '13800138003', 'staff', 1),
('赵六', 'zhaoliu@example.com', '13800138004', 'user', 1);

-- ==============================================================
-- 2. 插入认证数据
-- ==============================================================

-- 说明：为避免 user 表自增 ID 在不同环境不一致导致 auth.user_id 失配，
-- 这里按 user.role 动态绑定到真实的 user.id，并允许重复执行（upsert）。

INSERT INTO auth (username, password, token, user_id, is_active)
SELECT 'admin', 'admin123', UUID(), id, 1
FROM user
WHERE role = 'admin'
ORDER BY id
LIMIT 1
ON DUPLICATE KEY UPDATE
	password = VALUES(password),
	token = '',
	user_id = VALUES(user_id),
	is_active = 1,
	logout_time = NULL;

INSERT INTO auth (username, password, token, user_id, is_active)
SELECT 'manager', 'manager123', UUID(), id, 1
FROM user
WHERE role = 'manager'
ORDER BY id
LIMIT 1
ON DUPLICATE KEY UPDATE
	password = VALUES(password),
	token = '',
	user_id = VALUES(user_id),
	is_active = 1,
	logout_time = NULL;

INSERT INTO auth (username, password, token, user_id, is_active)
SELECT 'staff', 'staff123', UUID(), id, 1
FROM user
WHERE role = 'staff'
ORDER BY id
LIMIT 1
ON DUPLICATE KEY UPDATE
	password = VALUES(password),
	token = '',
	user_id = VALUES(user_id),
	is_active = 1,
	logout_time = NULL;

INSERT INTO auth (username, password, token, user_id, is_active)
SELECT 'user', 'user123', UUID(), id, 1
FROM user
WHERE role = 'user'
ORDER BY id
LIMIT 1
ON DUPLICATE KEY UPDATE
	password = VALUES(password),
	token = '',
	user_id = VALUES(user_id),
	is_active = 1,
	logout_time = NULL;

-- ==============================================================
-- 3. 插入老人信息数据
-- ==============================================================

INSERT INTO elderly (name, age, date_of_birth, gender, phone_number, health_status, address, contact_person, contact_phone, is_active) VALUES
('李老人', 75, '1949-05-15', '男', '13900139001', '身体健康', '北京市朝阳区', '李小明', '13800138001', 1),
('王老人', 68, '1956-08-20', '女', '13900139002', '轻微高血压', '北京市朝阳区', '王小美', '13800138002', 1),
('刘老人', 82, '1942-03-10', '男', '13900139003', '需要护理', '北京市东城区', '刘小强', '13800138003', 1),
('陈老人', 72, '1952-07-25', '女', '13900139004', '身体健康', '北京市西城区', '陈小丽', '13800138004', 1);

-- ==============================================================
-- 4. 插入亲属信息数据
-- ==============================================================

INSERT INTO relative (elderly_id, name, phone, relationship, email, is_primary_contact, is_active) VALUES
(1, '李小明', '13800138001', '儿子', 'lixiaoming@example.com', 1, 1),
(1, '李小红', '13800138005', '女儿', 'lixiaohong@example.com', 0, 1),
(2, '王小美', '13800138002', '女儿', 'wangxiaomei@example.com', 1, 1),
(3, '刘小强', '13800138003', '孙子', 'liuxiaoqiang@example.com', 1, 1);

-- ==============================================================
-- 5. 插入活动数据
-- ==============================================================

INSERT INTO activity (name, category, description, location, start_time, end_time, participants, organizer_id, status, is_active) VALUES
('太极拳课程', '健身活动', '每周三、五上午进行', '活动室A', '2026-01-15 09:00:00', '2026-01-15 10:30:00', 12, 2, '进行中', 1),
('书法班', '文化活动', '每周一上午进行', '书法室', '2026-01-20 10:00:00', '2026-01-20 11:30:00', 8, 2, '已完成', 1),
('棋类比赛', '娱乐活动', '象棋、围棋友谊赛', '活动室B', '2026-02-01 14:00:00', '2026-02-01 16:00:00', 15, 2, '计划中', 1);

-- ==============================================================
-- 6. 插入紧急求助数据
-- ==============================================================

INSERT INTO emergency (elderly_id, type, description, location, contact_phone, status, responder_id, response_time, resolved_time, priority, is_active) VALUES
(1, '摔跤', '在家摔倒', '北京市朝阳区', '13900139001', 'resolved', 3, '2026-01-08 10:15:00', '2026-01-08 11:30:00', 'high', 1),
(2, '身体不适', '突然头晕', '北京市朝阳区', '13900139002', 'responded', 3, '2026-01-09 14:20:00', NULL, 'high', 1),
(3, '需要帮助', '日常生活困难', '北京市东城区', '13900139003', 'pending', NULL, NULL, NULL, 'medium', 1);

-- ==============================================================
-- 7. 插入健康记录数据
-- ==============================================================

INSERT INTO health_record (elderly_id, record_date, blood_pressure, heart_rate, temperature, weight, glucose_level, notes, doctor_id, is_active) VALUES
(1, '2026-01-08', '120/80', 72, 36.5, 65.5, 100, '身体状况良好', 2, 1),
(2, '2026-01-08', '140/90', 75, 36.6, 62.0, 110, '血压偏高', 2, 1),
(3, '2026-01-07', '135/85', 78, 36.7, 70.0, 105, '需要控制血糖', 2, 1),
(4, '2026-01-08', '125/82', 70, 36.5, 58.5, 98, '身体健康', 2, 1);

-- ==============================================================
-- 8. 插入服务订单数据
-- ==============================================================

INSERT INTO service_order (elderly_id, service_type, service_provider_id, start_date, end_date, frequency, price, status, description, is_active) VALUES
(1, '日常护理', 3, '2026-01-01', '2026-03-01', '每日', 100, '进行中', '日常生活护理服务', 1),
(2, '康复治疗', 3, '2026-01-15', '2026-02-15', '每周三次', 150, '计划中', '物理康复治疗', 1),
(3, '医疗护理', 3, '2025-12-01', '2026-06-01', '每日', 200, '进行中', '专业医疗护理', 1);

-- ==============================================================
-- 9. 插入员工数据
-- ==============================================================

INSERT INTO worker (name, email, phone, position, department, specialization, hire_date, salary, is_available, is_active) VALUES
('张护士', 'zhanghushi@example.com', '13900139010', '护士', '护理部', '老年护理、康复护理', '2020-06-01', 5000, 1, 1),
('李医生', 'liyisheng@example.com', '13900139011', '医生', '医疗部', '老年医学、心内科', '2018-09-01', 8000, 1, 1),
('王护工', 'wanghugong@example.com', '13900139012', '护工', '护理部', '日常护理、生活护助', '2021-03-15', 3500, 1, 1),
('刘管理', 'liuguanli@example.com', '13900139013', '管理员', '行政部', '项目管理', '2019-01-01', 6000, 0, 1);

-- ==============================================================
-- 10. 插入文件记录数据
-- ==============================================================

INSERT INTO file_record (filename, original_filename, file_type, file_size, file_path, url, uploader_id, entity_type, entity_id, description, is_active) VALUES
('elderly_001_photo.jpg', '李老人照片.jpg', 'image/jpeg', 524288, '/uploads/elderly/001/', 'http://localhost:8080/api/files/elderly_001_photo.jpg', 1, 'elderly', 1, '老人身份照', 1),
('health_record_001.pdf', '健康记录.pdf', 'application/pdf', 102400, '/uploads/health/001/', 'http://localhost:8080/api/files/health_record_001.pdf', 2, 'health_record', 1, '健康检查报告', 1),
('service_order_001.doc', '服务订单.doc', 'application/msword', 51200, '/uploads/service/', 'http://localhost:8080/api/files/service_order_001.doc', 3, 'service_order', 1, '服务订单合同', 1);

-- ==============================================================
-- 11. 插入角色数据
-- ==============================================================

INSERT INTO role (name, code, description, is_active) VALUES
('系统管理员', 'admin', '拥有系统所有权限', 1),
('操作员', 'operator', '可管理日常业务，无法修改系统配置', 1),
('普通用户', 'user', '仅可查看和管理个人相关信息', 1);

-- ==============================================================
-- 12. 插入权限数据
-- ==============================================================

INSERT INTO permission (name, code, module, description, is_active) VALUES
-- 用户管理权限
('用户列表查看', 'user:view', '用户管理', '查看用户列表', 1),
('用户信息创建', 'user:create', '用户管理', '创建新用户', 1),
('用户信息编辑', 'user:update', '用户管理', '编辑用户信息', 1),
('用户信息删除', 'user:delete', '用户管理', '删除用户', 1),

-- 老人管理权限
('老人列表查看', 'elderly:view', '老人管理', '查看老人列表', 1),
('老人信息创建', 'elderly:create', '老人管理', '创建老人档案', 1),
('老人信息编辑', 'elderly:update', '老人管理', '编辑老人信息', 1),
('老人信息删除', 'elderly:delete', '老人管理', '删除老人档案', 1),

-- 活动管理权限
('活动列表查看', 'activity:view', '活动管理', '查看活动列表', 1),
('活动创建', 'activity:create', '活动管理', '创建新活动', 1),
('活动编辑', 'activity:update', '活动管理', '编辑活动信息', 1),
('活动删除', 'activity:delete', '活动管理', '删除活动', 1),
('活动报名管理', 'activity:enrollment', '活动管理', '管理活动报名', 1),

-- 健康管理权限
('健康记录查看', 'health:view', '健康管理', '查看健康记录', 1),
('健康记录创建', 'health:create', '健康管理', '创建健康记录', 1),
('健康记录编辑', 'health:update', '健康管理', '编辑健康记录', 1),
('健康记录删除', 'health:delete', '健康管理', '删除健康记录', 1),

-- 紧急呼叫权限
('紧急呼叫查看', 'emergency:view', '紧急呼叫', '查看紧急呼叫记录', 1),
('紧急呼叫处理', 'emergency:handle', '紧急呼叫', '处理紧急呼叫', 1),

-- 服务订单权限
('服务订单查看', 'service:view', '服务订单', '查看服务订单', 1),
('服务订单创建', 'service:create', '服务订单', '创建服务订单', 1),
('服务订单编辑', 'service:update', '服务订单', '编辑服务订单', 1),
('服务订单删除', 'service:delete', '服务订单', '删除服务订单', 1),

-- 角色管理权限
('角色列表查看', 'role:view', '角色管理', '查看角色列表', 1),
('角色创建', 'role:create', '角色管理', '创建新角色', 1),
('角色编辑', 'role:update', '角色管理', '编辑角色信息', 1),
('角色删除', 'role:delete', '角色管理', '删除角色', 1),

-- 权限管理权限
('权限列表查看', 'permission:view', '权限管理', '查看权限列表', 1),
('权限配置', 'permission:assign', '权限管理', '为角色配置权限', 1);

-- ==============================================================
-- 13. 插入角色权限关联数据
-- ==============================================================

-- 系统管理员：拥有所有权限
INSERT INTO role_permission (role_id, permission_id)
SELECT 
  (SELECT id FROM role WHERE code = 'admin'),
  id
FROM permission
WHERE is_active = 1;

-- 操作员：拥有日常业务管理权限（除角色和权限管理外）
INSERT INTO role_permission (role_id, permission_id)
SELECT 
  (SELECT id FROM role WHERE code = 'operator'),
  id
FROM permission
WHERE module IN ('用户管理', '老人管理', '活动管理', '健康管理', '紧急呼叫', '服务订单')
  AND is_active = 1;

-- 普通用户：仅拥有查看权限
INSERT INTO role_permission (role_id, permission_id)
SELECT 
  (SELECT id FROM role WHERE code = 'user'),
  id
FROM permission
WHERE code LIKE '%:view'
  AND module NOT IN ('角色管理', '权限管理')
  AND is_active = 1;

-- ==============================================================
-- 14. 插入活动报名数据
-- ==============================================================

INSERT INTO enrollment (activity_id, elderly_id, status, enroll_time, check_in_time, notes, is_active) VALUES
-- 太极拳课程的报名
(1, 1, 'attended', DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY), '已参加太极拳课程', 1),
(1, 2, 'confirmed', DATE_SUB(NOW(), INTERVAL 6 DAY), NULL, '已确认参加', 1),
(1, 3, 'pending', DATE_SUB(NOW(), INTERVAL 5 DAY), NULL, '刚报名，待管理员确认', 1),
(1, 4, 'cancelled', DATE_SUB(NOW(), INTERVAL 4 DAY), NULL, '因身体不适取消', 1),

-- 书法班的报名
(2, 1, 'attended', DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 10 DAY), '参加书法班学习', 1),
(2, 2, 'attended', DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 10 DAY), '完成书法班课程', 1),
(2, 3, 'pending', DATE_SUB(NOW(), INTERVAL 9 DAY), NULL, '新报名', 1),
(2, 4, 'absent', DATE_SUB(NOW(), INTERVAL 11 DAY), NULL, '缺课', 1),

-- 棋类比赛的报名
(3, 1, 'confirmed', DATE_SUB(NOW(), INTERVAL 2 DAY), NULL, '已确认参加比赛', 1),
(3, 2, 'pending', NOW(), NULL, '新报名棋类比赛', 1),
(3, 3, 'confirmed', DATE_SUB(NOW(), INTERVAL 1 DAY), NULL, '已确认参加比赛', 1),
(3, 4, 'pending', NOW(), NULL, '报名比赛中', 1);

-- ==============================================================
-- 数据初始化完毕
-- ==============================================================
