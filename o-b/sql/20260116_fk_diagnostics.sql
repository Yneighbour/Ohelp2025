-- =============================================================
-- Ohelp2025 外键诊断脚本 (2026-01-16)
-- 作用：统计各外键的无效引用数量、列出样本数据，并列出现有外键约束
-- =============================================================

USE ohelp;

SELECT '================ 外键无效引用统计 ================' AS info;

-- 1) auth.user_id -> user.id
SELECT 'auth.user_id 无效引用计数' AS item, COUNT(*) AS invalid_count
FROM auth a LEFT JOIN `user` u ON a.user_id = u.id
WHERE a.user_id IS NOT NULL AND u.id IS NULL;
SELECT 'auth.user_id 样本' AS sample, a.*
FROM auth a LEFT JOIN `user` u ON a.user_id = u.id
WHERE a.user_id IS NOT NULL AND u.id IS NULL
LIMIT 10;

-- 2) activity.organizer_id -> user.id
SELECT 'activity.organizer_id 无效引用计数' AS item, COUNT(*) AS invalid_count
FROM activity a LEFT JOIN `user` u ON a.organizer_id = u.id
WHERE a.organizer_id IS NOT NULL AND u.id IS NULL;
SELECT 'activity.organizer_id 样本' AS sample, a.*
FROM activity a LEFT JOIN `user` u ON a.organizer_id = u.id
WHERE a.organizer_id IS NOT NULL AND u.id IS NULL
LIMIT 10;

-- 3) emergency.responder_id -> worker.id
SELECT 'emergency.responder_id 无效引用计数' AS item, COUNT(*) AS invalid_count
FROM emergency e LEFT JOIN worker w ON e.responder_id = w.id
WHERE e.responder_id IS NOT NULL AND w.id IS NULL;
SELECT 'emergency.responder_id 样本' AS sample, e.*
FROM emergency e LEFT JOIN worker w ON e.responder_id = w.id
WHERE e.responder_id IS NOT NULL AND w.id IS NULL
LIMIT 10;

-- 4) health_record.doctor_id -> worker.id
SELECT 'health_record.doctor_id 无效引用计数' AS item, COUNT(*) AS invalid_count
FROM health_record h LEFT JOIN worker w ON h.doctor_id = w.id
WHERE h.doctor_id IS NOT NULL AND w.id IS NULL;
SELECT 'health_record.doctor_id 样本' AS sample, h.*
FROM health_record h LEFT JOIN worker w ON h.doctor_id = w.id
WHERE h.doctor_id IS NOT NULL AND w.id IS NULL
LIMIT 10;

-- 5) service_order.service_provider_id -> worker.id
SELECT 'service_order.service_provider_id 无效引用计数' AS item, COUNT(*) AS invalid_count
FROM service_order s LEFT JOIN worker w ON s.service_provider_id = w.id
WHERE s.service_provider_id IS NOT NULL AND w.id IS NULL;
SELECT 'service_order.service_provider_id 样本' AS sample, s.*
FROM service_order s LEFT JOIN worker w ON s.service_provider_id = w.id
WHERE s.service_provider_id IS NOT NULL AND w.id IS NULL
LIMIT 10;

-- 6) file_record.uploader_id -> user.id
SELECT 'file_record.uploader_id 无效引用计数' AS item, COUNT(*) AS invalid_count
FROM file_record f LEFT JOIN `user` u ON f.uploader_id = u.id
WHERE f.uploader_id IS NOT NULL AND u.id IS NULL;
SELECT 'file_record.uploader_id 样本' AS sample, f.*
FROM file_record f LEFT JOIN `user` u ON f.uploader_id = u.id
WHERE f.uploader_id IS NOT NULL AND u.id IS NULL
LIMIT 10;

SELECT '================ 现有外键约束列表 ================' AS info;
SELECT CONSTRAINT_NAME, TABLE_NAME, REFERENCED_TABLE_NAME
FROM information_schema.KEY_COLUMN_USAGE
WHERE CONSTRAINT_SCHEMA = 'ohelp' AND REFERENCED_TABLE_NAME IS NOT NULL
ORDER BY TABLE_NAME, CONSTRAINT_NAME;
