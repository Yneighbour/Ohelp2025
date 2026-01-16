-- ==============================================================
-- Enrollment 报名初始化脚本
-- ==============================================================
-- 用途：为报名管理界面提供示例数据
-- 执行方式：直接在 MySQL 中执行此脚本
-- ==============================================================

USE ohelp;

-- 清空现有数据（可选，如果需要重新初始化）
-- DELETE FROM enrollment WHERE 1=1;

-- ==============================================================
-- 插入报名数据
-- ==============================================================
-- 假设：
-- - activity 表中已有ID为 1, 2, 3 的活动
-- - elderly 表中已有ID为 1, 2, 3, 4 的老人

INSERT INTO enrollment (activity_id, elderly_id, status, enroll_time, check_in_time, notes, is_active, created_at, updated_at) VALUES

-- 太极拳课程的报名
(1, 1, 'attended', DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY), '已参加', 1, DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY)),
(1, 2, 'confirmed', DATE_SUB(NOW(), INTERVAL 6 DAY), NULL, '已确认，准备参加', 1, DATE_SUB(NOW(), INTERVAL 6 DAY), DATE_SUB(NOW(), INTERVAL 6 DAY)),
(1, 3, 'pending', DATE_SUB(NOW(), INTERVAL 5 DAY), NULL, '刚报名，待管理员确认', 1, DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_SUB(NOW(), INTERVAL 5 DAY)),
(1, 4, 'cancelled', DATE_SUB(NOW(), INTERVAL 4 DAY), NULL, '因身体不适取消', 1, DATE_SUB(NOW(), INTERVAL 4 DAY), DATE_SUB(NOW(), INTERVAL 4 DAY)),

-- 书法班的报名
(2, 1, 'attended', DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 10 DAY), '按时参加', 1, DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 10 DAY)),
(2, 2, 'attended', DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 10 DAY), '参加完成', 1, DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 10 DAY)),
(2, 4, 'absent', DATE_SUB(NOW(), INTERVAL 11 DAY), NULL, '未参加', 1, DATE_SUB(NOW(), INTERVAL 11 DAY), DATE_SUB(NOW(), INTERVAL 11 DAY)),

-- 棋类比赛的报名
(3, 1, 'confirmed', DATE_SUB(NOW(), INTERVAL 2 DAY), NULL, '已确认参加比赛', 1, DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY)),
(3, 2, 'pending', NOW(), NULL, '新报名，等待确认', 1, NOW(), NOW()),
(3, 3, 'pending', NOW(), NULL, '新报名', 1, NOW(), NOW()),
(3, 4, 'confirmed', DATE_SUB(NOW(), INTERVAL 1 DAY), NULL, '已确认', 1, DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY));

-- ==============================================================
-- 数据统计
-- ==============================================================
SELECT 
  status,
  COUNT(*) as 数量,
  CASE 
    WHEN status = 'pending' THEN '待确认'
    WHEN status = 'confirmed' THEN '已确认'
    WHEN status = 'attended' THEN '已签到'
    WHEN status = 'absent' THEN '未参加'
    WHEN status = 'cancelled' THEN '已取消'
    ELSE '其他'
  END as 状态描述
FROM enrollment
GROUP BY status
ORDER BY status;

-- ==============================================================
-- 脚本执行完毕
-- ==============================================================
