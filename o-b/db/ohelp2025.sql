-- 智慧养老服务管理系统数据库脚本
-- 创建数据库
CREATE DATABASE IF NOT EXISTS Ohelp2025 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE Ohelp2025;

-- 用户表
CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE,
    phone VARCHAR(20),
    real_name VARCHAR(100),
    age INT,
    gender VARCHAR(10),
    avatar VARCHAR(500),
    role VARCHAR(50),
    is_active BOOLEAN DEFAULT TRUE,
    create_time DATETIME,
    update_time DATETIME,
    last_login_time DATETIME
);

-- 老人表
CREATE TABLE laoren (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    laoren_uuid VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100),
    age INT,
    gender VARCHAR(10),
    phone VARCHAR(20),
    id_number VARCHAR(18),
    address VARCHAR(500),
    avatar VARCHAR(500),
    emergency_contact_name VARCHAR(100),
    emergency_contact_phone VARCHAR(20),
    health_status VARCHAR(255),
    special_medical_needs TEXT,
    admission_date DATE,
    is_active BOOLEAN DEFAULT TRUE,
    create_time DATETIME,
    update_time DATETIME
);

-- 服务类型表
CREATE TABLE fuwuleixing (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    leixing_name VARCHAR(255) NOT NULL,
    leixing_desc TEXT,
    leixing_img VARCHAR(500),
    shangxia INT DEFAULT 1,
    create_time DATETIME,
    update_time DATETIME
);

-- 服务项目表
CREATE TABLE fuwuxiangmu (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    xiangmu_name VARCHAR(255) NOT NULL,
    xiangmu_photo VARCHAR(500),
    xiangmu_news_price DECIMAL(10,2),
    xiangmu_number INT,
    xiangmu_desc TEXT,
    fuwuleixing_id BIGINT,
    shangxia INT DEFAULT 1,
    create_time DATETIME,
    update_time DATETIME,
    FOREIGN KEY (fuwuleixing_id) REFERENCES fuwuleixing(id)
);

-- 服务购买表
CREATE TABLE fuwugoumai (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    goumai_uuid VARCHAR(255),
    fuwuxiangmu_name VARCHAR(255),
    fuwuleixing_name VARCHAR(255),
    laoren_uuid VARCHAR(255),
    laoren_name VARCHAR(100),
    goumai_number DECIMAL(10,2),
    goumai_old_number DECIMAL(10,2),
    goumai_new_number DECIMAL(10,2),
    goumai_new_price DECIMAL(10,2),
    goumai_address VARCHAR(500),
    goumai_content TEXT,
    goumai_yesno VARCHAR(10) DEFAULT '0',
    goumai_content_reply TEXT,
    create_time DATETIME,
    update_time DATETIME
);

-- 活动分类表
CREATE TABLE huodongfenlei (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fenlei_name VARCHAR(255) NOT NULL,
    fenlei_img VARCHAR(500),
    shangxia INT DEFAULT 1,
    create_time DATETIME,
    update_time DATETIME
);

-- 活动信息表
CREATE TABLE huodongxinxi (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    huodong_name VARCHAR(255) NOT NULL,
    huodong_photo VARCHAR(500),
    huodong_address VARCHAR(500),
    huodong_time DATETIME,
    huodong_money DECIMAL(10,2),
    huodong_content TEXT,
    huodongfenlei_id BIGINT,
    huodong_yesno VARCHAR(10) DEFAULT '0',
    create_time DATETIME,
    update_time DATETIME,
    FOREIGN KEY (huodongfenlei_id) REFERENCES huodongfenlei(id)
);

-- 每日健康表
CREATE TABLE meirijiankang (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    jiankang_name VARCHAR(255) NOT NULL,
    jiankang_photo VARCHAR(500),
    jiankang_content TEXT,
    jiankang_delete VARCHAR(10) DEFAULT '0',
    create_time DATETIME,
    update_time DATETIME
);

-- 既往病史表
CREATE TABLE jiwangbingshi (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    laoren_uuid VARCHAR(255),
    laoren_name VARCHAR(100),
    bingshi_name VARCHAR(255),
    bingshi_content TEXT,
    bingshi_delete VARCHAR(10) DEFAULT '0',
    create_time DATETIME,
    update_time DATETIME
);

-- 积分增加表
CREATE TABLE jifenzengjia (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    laoren_uuid VARCHAR(255),
    laoren_name VARCHAR(100),
    jifen_name VARCHAR(255),
    jifen_number DECIMAL(10,2),
    jifen_content TEXT,
    jifen_delete VARCHAR(10) DEFAULT '0',
    create_time DATETIME,
    update_time DATETIME
);

-- 亲人表
CREATE TABLE qinshu (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    laoren_uuid VARCHAR(255),
    laoren_name VARCHAR(100),
    qinshu_name VARCHAR(100),
    qinshu_phone VARCHAR(20),
    qinshu_id_number VARCHAR(18),
    qinshu_delete VARCHAR(10) DEFAULT '0',
    create_time DATETIME,
    update_time DATETIME
);

-- 老工表
CREATE TABLE laogong (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    laogong_uuid VARCHAR(255),
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    laogong_name VARCHAR(100),
    laogong_phone VARCHAR(20),
    laogong_id_number VARCHAR(18),
    laogong_delete VARCHAR(10) DEFAULT '0',
    create_time DATETIME,
    update_time DATETIME
);

-- 紧急求助表
CREATE TABLE jinjiqiuzhu (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    laoren_uuid VARCHAR(255),
    laoren_name VARCHAR(100),
    jinjiqiuzhu_text TEXT,
    jinjiqiuzhu_delete VARCHAR(10) DEFAULT '0',
    create_time DATETIME,
    update_time DATETIME
);

-- 服务请求表
CREATE TABLE service_request (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    type VARCHAR(100),
    priority VARCHAR(20), -- LOW, MEDIUM, HIGH, URGENT
    laoren_id BIGINT,
    assigned_staff VARCHAR(100),
    status VARCHAR(20), -- PENDING, IN_PROGRESS, COMPLETED, CANCELLED
    request_date DATETIME,
    scheduled_date DATETIME,
    completed_date DATETIME,
    notes TEXT,
    FOREIGN KEY (laoren_id) REFERENCES laoren(id)
);

-- 令牌表
CREATE TABLE token (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(500) NOT NULL UNIQUE,
    user_id VARCHAR(255) NOT NULL,
    user_type VARCHAR(50), -- USER, LAOREN, LAOGONG, etc.
    create_time DATETIME,
    expire_time DATETIME,
    is_active BOOLEAN DEFAULT TRUE,
    last_access_time DATETIME
);

-- 照料计划表
CREATE TABLE zhaoliao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    zhaoliao_uuid VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    type VARCHAR(100), -- 如：日常护理、送餐服务、洗浴服务等
    laoren_id BIGINT,
    laoren_name VARCHAR(100),
    assigned_staff VARCHAR(100),
    status VARCHAR(20), -- 待执行、执行中、已完成
    scheduled_time DATETIME,
    completed_time DATETIME,
    notes TEXT,
    create_time DATETIME,
    update_time DATETIME,
    FOREIGN KEY (laoren_id) REFERENCES laoren(id)
);

-- 服务评价表
CREATE TABLE pingjia (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pingjia_uuid VARCHAR(255) NOT NULL,
    service_id BIGINT,
    service_name VARCHAR(255),
    laoren_id BIGINT,
    laoren_name VARCHAR(100),
    staff_id BIGINT,
    staff_name VARCHAR(100),
    rating INT, -- 评分（1-5星）
    comment TEXT,
    service_type VARCHAR(100),
    evaluation_time DATETIME,
    status VARCHAR(20),
    create_time DATETIME,
    update_time DATETIME,
    FOREIGN KEY (laoren_id) REFERENCES laoren(id)
);

-- 家属探望登记表
CREATE TABLE tanwang (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tanwang_uuid VARCHAR(255) NOT NULL,
    visitor_name VARCHAR(100),
    visitor_phone VARCHAR(20),
    relationship VARCHAR(50),
    laoren_id BIGINT,
    laoren_name VARCHAR(100),
    visit_time DATETIME,
    leave_time DATETIME,
    visit_reason VARCHAR(255),
    status VARCHAR(20), -- 待确认、已确认、已完成、已取消
    notes TEXT,
    staff_id VARCHAR(100),
    staff_name VARCHAR(100),
    create_time DATETIME,
    update_time DATETIME,
    FOREIGN KEY (laoren_id) REFERENCES laoren(id)
);

-- 插入默认管理员用户
INSERT INTO user (username, password, real_name, role, create_time) 
VALUES ('admin', '$2a$10$vaQV8ezZxkZQSx4N8z4Mz.T44vV8z4Mz.T44vV8z4Mz.T44vV8z4Mz', '系统管理员', 'ADMIN', NOW());

-- 插入默认老人
INSERT INTO laoren (laoren_uuid, username, password, name, create_time) 
VALUES ('LR000001', 'laoren', '$2a$10$vaQV8ezZxkZQSx4N8z4Mz.T44vV8z4Mz.T44vV8z4Mz.T44vV8z4Mz', '默认老人', NOW());