-- =============================================================
-- Ohelp2025 数据库结构优化迁移脚本 (2026-01-16)
-- 重点：补充外键约束、增加常用查询索引、保持兼容
-- 说明：在生产执行前请先在测试库验证；如已有数据不满足约束，需先清洗或暂时跳过相应外键
-- =============================================================

USE ohelp;


-- 1) 补充外键约束
-- -------------------------------------------------------------
-- auth.user_id -> user(id)
ALTER TABLE auth
  ADD CONSTRAINT fk_auth_user
  FOREIGN KEY (user_id) REFERENCES user(id)
  ON DELETE SET NULL ON UPDATE CASCADE;

-- activity.organizer_id -> user(id)
ALTER TABLE activity
  ADD CONSTRAINT fk_activity_organizer_user
  FOREIGN KEY (organizer_id) REFERENCES user(id)
  ON DELETE SET NULL ON UPDATE CASCADE;

-- emergency.responder_id -> worker(id)
ALTER TABLE emergency
  ADD CONSTRAINT fk_emergency_responder_worker
  FOREIGN KEY (responder_id) REFERENCES worker(id)
  ON DELETE SET NULL ON UPDATE CASCADE;

-- health_record.doctor_id -> worker(id)
ALTER TABLE health_record
  ADD CONSTRAINT fk_health_record_doctor_worker
  FOREIGN KEY (doctor_id) REFERENCES worker(id)
  ON DELETE SET NULL ON UPDATE CASCADE;

-- service_order.service_provider_id -> worker(id)
ALTER TABLE service_order
  ADD CONSTRAINT fk_service_order_provider_worker
  FOREIGN KEY (service_provider_id) REFERENCES worker(id)
  ON DELETE SET NULL ON UPDATE CASCADE;

-- file_record.uploader_id -> user(id)
ALTER TABLE file_record
  ADD CONSTRAINT fk_file_record_uploader_user
  FOREIGN KEY (uploader_id) REFERENCES user(id)
  ON DELETE SET NULL ON UPDATE CASCADE;

-- 2) 复合索引优化（按接口与查询模式）
-- -------------------------------------------------------------
-- 健康记录：按老人+日期查询
CREATE INDEX IF NOT EXISTS idx_health_elderly_record_date
  ON health_record (elderly_id, record_date);

-- 服务订单：按服务提供者与状态查询
CREATE INDEX IF NOT EXISTS idx_service_provider_status
  ON service_order (service_provider_id, status);

-- 服务订单：按老人与状态查询
CREATE INDEX IF NOT EXISTS idx_service_elderly_status
  ON service_order (elderly_id, status);

-- 活动报名：按活动+状态、老人+状态查询
CREATE INDEX IF NOT EXISTS idx_enroll_activity_status
  ON enrollment (activity_id, status);
CREATE INDEX IF NOT EXISTS idx_enroll_elderly_status
  ON enrollment (elderly_id, status);

-- 紧急求助：按老人+状态、状态+优先级查询
CREATE INDEX IF NOT EXISTS idx_emergency_elderly_status
  ON emergency (elderly_id, status);
CREATE INDEX IF NOT EXISTS idx_emergency_status_priority
  ON emergency (status, priority);

-- 认证：按 token 查询（常见按令牌快速定位会话）
CREATE INDEX IF NOT EXISTS idx_auth_token ON auth (token);

-- 3) 轻量数据一致性与性能建议（可选）
-- -------------------------------------------------------------
-- 说明：以下为建议，需结合现有数据与代码评估后再执行
-- 示例：将若干布尔字段设为 NOT NULL DEFAULT，避免三态
-- ALTER TABLE elderly MODIFY is_active TINYINT(1) NOT NULL DEFAULT 1;
-- ALTER TABLE worker MODIFY is_available TINYINT(1) NOT NULL DEFAULT 1;
-- ALTER TABLE health_record MODIFY is_active TINYINT(1) NOT NULL DEFAULT 1;

-- 示例：对经常按时间查询的表增加时间索引（已在 DDL 末尾覆盖）
-- CREATE INDEX IF NOT EXISTS idx_health_created_at ON health_record (created_at);

-- 回滚提示：
--  - 外键移除：ALTER TABLE <table> DROP FOREIGN KEY <fk_name>;
--  - 索引删除：DROP INDEX <index_name> ON <table>;

-- 脚本结束
