-- =============================================================
-- Ohelp2025 索引补齐脚本 (仅索引，不含外键) 2026-01-16
-- 目的：在外键已存在或重复时报错后，单独补齐索引。
-- =============================================================
USE ohelp;

-- 为兼容不支持 IF EXISTS 的版本，使用信息_schema 判断后再创建

-- 健康记录：按老人+日期查询
SET @exists := (SELECT COUNT(*) FROM information_schema.STATISTICS WHERE TABLE_SCHEMA='ohelp' AND TABLE_NAME='health_record' AND INDEX_NAME='idx_health_elderly_record_date');
SET @sql := IF(@exists=0, 'CREATE INDEX idx_health_elderly_record_date ON health_record (elderly_id, record_date);', 'SELECT 1;');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- 服务订单：按服务提供者与状态查询
SET @exists := (SELECT COUNT(*) FROM information_schema.STATISTICS WHERE TABLE_SCHEMA='ohelp' AND TABLE_NAME='service_order' AND INDEX_NAME='idx_service_provider_status');
SET @sql := IF(@exists=0, 'CREATE INDEX idx_service_provider_status ON service_order (service_provider_id, status);', 'SELECT 1;');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- 服务订单：按老人与状态查询
SET @exists := (SELECT COUNT(*) FROM information_schema.STATISTICS WHERE TABLE_SCHEMA='ohelp' AND TABLE_NAME='service_order' AND INDEX_NAME='idx_service_elderly_status');
SET @sql := IF(@exists=0, 'CREATE INDEX idx_service_elderly_status ON service_order (elderly_id, status);', 'SELECT 1;');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- 活动报名：按活动+状态、老人+状态查询
SET @exists := (SELECT COUNT(*) FROM information_schema.STATISTICS WHERE TABLE_SCHEMA='ohelp' AND TABLE_NAME='enrollment' AND INDEX_NAME='idx_enroll_activity_status');
SET @sql := IF(@exists=0, 'CREATE INDEX idx_enroll_activity_status ON enrollment (activity_id, status);', 'SELECT 1;');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @exists := (SELECT COUNT(*) FROM information_schema.STATISTICS WHERE TABLE_SCHEMA='ohelp' AND TABLE_NAME='enrollment' AND INDEX_NAME='idx_enroll_elderly_status');
SET @sql := IF(@exists=0, 'CREATE INDEX idx_enroll_elderly_status ON enrollment (elderly_id, status);', 'SELECT 1;');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- 紧急求助：按老人+状态、状态+优先级查询
SET @exists := (SELECT COUNT(*) FROM information_schema.STATISTICS WHERE TABLE_SCHEMA='ohelp' AND TABLE_NAME='emergency' AND INDEX_NAME='idx_emergency_elderly_status');
SET @sql := IF(@exists=0, 'CREATE INDEX idx_emergency_elderly_status ON emergency (elderly_id, status);', 'SELECT 1;');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @exists := (SELECT COUNT(*) FROM information_schema.STATISTICS WHERE TABLE_SCHEMA='ohelp' AND TABLE_NAME='emergency' AND INDEX_NAME='idx_emergency_status_priority');
SET @sql := IF(@exists=0, 'CREATE INDEX idx_emergency_status_priority ON emergency (status, priority);', 'SELECT 1;');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- 认证：按 token 查询
SET @exists := (SELECT COUNT(*) FROM information_schema.STATISTICS WHERE TABLE_SCHEMA='ohelp' AND TABLE_NAME='auth' AND INDEX_NAME='idx_auth_token');
SET @sql := IF(@exists=0, 'CREATE INDEX idx_auth_token ON auth (token);', 'SELECT 1;');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;
