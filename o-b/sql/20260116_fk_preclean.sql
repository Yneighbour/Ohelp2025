-- =============================================================
-- Ohelp2025 外键预清洗脚本 (2026-01-16)
-- 目的：在添加外键前，将不满足约束的数据置为 NULL，避免 FK 创建失败
-- =============================================================

USE ohelp;

-- auth.user_id -> user.id
UPDATE auth a
LEFT JOIN `user` u ON a.user_id = u.id
SET a.user_id = NULL
WHERE a.user_id IS NOT NULL AND u.id IS NULL;

-- activity.organizer_id -> user.id
UPDATE activity a
LEFT JOIN `user` u ON a.organizer_id = u.id
SET a.organizer_id = NULL
WHERE a.organizer_id IS NOT NULL AND u.id IS NULL;

-- emergency.responder_id -> worker.id
UPDATE emergency e
LEFT JOIN worker w ON e.responder_id = w.id
SET e.responder_id = NULL
WHERE e.responder_id IS NOT NULL AND w.id IS NULL;

-- health_record.doctor_id -> worker.id
UPDATE health_record h
LEFT JOIN worker w ON h.doctor_id = w.id
SET h.doctor_id = NULL
WHERE h.doctor_id IS NOT NULL AND w.id IS NULL;

-- service_order.service_provider_id -> worker.id
UPDATE service_order s
LEFT JOIN worker w ON s.service_provider_id = w.id
SET s.service_provider_id = NULL
WHERE s.service_provider_id IS NOT NULL AND w.id IS NULL;

-- file_record.uploader_id -> user.id
UPDATE file_record f
LEFT JOIN `user` u ON f.uploader_id = u.id
SET f.uploader_id = NULL
WHERE f.uploader_id IS NOT NULL AND u.id IS NULL;

-- 可选：打印受影响行的数量（仅供手动执行时参考）
-- SELECT ROW_COUNT() AS affected_rows;
