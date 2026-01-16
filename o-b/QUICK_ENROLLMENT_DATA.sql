-- ==============================================================
-- 快速恢复报名管理数据脚本
-- ==============================================================
-- 用途：快速恢复报名管理界面的示例数据
-- 说明：该脚本假设数据库中已存在以下信息：
--       - 数据库 ohelp 已创建
--       - activity 表中存在 ID 为 1, 2, 3 的活动
--       - elderly 表中存在 ID 为 1, 2, 3, 4 的老人
--
-- 执行步骤：
-- 1. 打开 MySQL 客户端或 Workbench
-- 2. 连接到你的 MySQL 服务器
-- 3. 选择 ohelp 数据库：USE ohelp;
-- 4. 复制并执行此脚本的内容
-- ==============================================================

USE ohelp;

-- 先清空现有的报名数据（可选）
-- DELETE FROM enrollment WHERE 1=1;

-- 插入报名数据
INSERT INTO enrollment (activity_id, elderly_id, status, enroll_time, check_in_time, notes, is_active) VALUES
-- 太极拳课程（活动ID=1）的报名
(1, 1, 'attended', DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY), '已参加太极拳课程', 1),
(1, 2, 'confirmed', DATE_SUB(NOW(), INTERVAL 6 DAY), NULL, '已确认参加太极拳', 1),
(1, 3, 'pending', DATE_SUB(NOW(), INTERVAL 5 DAY), NULL, '刚报名太极拳，待管理员确认', 1),
(1, 4, 'cancelled', DATE_SUB(NOW(), INTERVAL 4 DAY), NULL, '因身体不适取消太极拳报名', 1),

-- 书法班（活动ID=2）的报名
(2, 1, 'attended', DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 10 DAY), '参加书法班学习', 1),
(2, 2, 'attended', DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 10 DAY), '完成书法班课程', 1),
(2, 3, 'pending', DATE_SUB(NOW(), INTERVAL 9 DAY), NULL, '报名书法班，等待确认', 1),
(2, 4, 'absent', DATE_SUB(NOW(), INTERVAL 11 DAY), NULL, '报名书法班但未参加', 1),

-- 棋类比赛（活动ID=3）的报名
(3, 1, 'confirmed', DATE_SUB(NOW(), INTERVAL 2 DAY), NULL, '已确认参加棋类比赛', 1),
(3, 2, 'pending', NOW(), NULL, '新报名棋类比赛', 1),
(3, 3, 'confirmed', DATE_SUB(NOW(), INTERVAL 1 DAY), NULL, '已确认参加棋类比赛', 1),
(3, 4, 'pending', NOW(), NULL, '报名棋类比赛中', 1);

-- 显示统计信息
SELECT '===== 报名数据统计 =====' as '统计信息';
SELECT 
  status as '报名状态',
  COUNT(*) as '记录数',
  CASE 
    WHEN status = 'pending' THEN '待确认'
    WHEN status = 'confirmed' THEN '已确认'
    WHEN status = 'attended' THEN '已签到'
    WHEN status = 'absent' THEN '未参加'
    WHEN status = 'cancelled' THEN '已取消'
    ELSE '其他'
  END as '状态描述'
FROM enrollment
GROUP BY status
ORDER BY FIELD(status, 'pending', 'confirmed', 'attended', 'absent', 'cancelled');

-- 显示所有报名记录
SELECT '===== 所有报名记录 =====' as '记录列表';
SELECT 
  id, 
  activity_id as '活动ID', 
  elderly_id as '老人ID', 
  status as '状态', 
  enroll_time as '报名时间',
  check_in_time as '签到时间',
  notes as '备注'
FROM enrollment
ORDER BY id;

-- ==============================================================
-- 脚本执行完毕
-- ==============================================================
