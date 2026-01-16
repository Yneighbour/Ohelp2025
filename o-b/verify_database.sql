-- ==============================================================
-- 数据库验证脚本
-- ==============================================================
-- 用途：验证数据库初始化是否成功
-- 使用方法：在 MySQL 中执行此脚本，查看各个表的数据量
-- ==============================================================

USE ohelp;

-- 显示分隔符
SELECT '================================' AS '数据库验证开始';
SELECT '' AS '';

-- 1. 基础表统计
SELECT '【基础表统计】' AS '类别';
SELECT 
  '用户表' AS 表名,
  COUNT(*) AS 记录数,
  SUM(CASE WHEN is_active = 1 THEN 1 ELSE 0 END) AS 激活数
FROM user;

SELECT 
  '老人表' AS 表名,
  COUNT(*) AS 记录数,
  SUM(CASE WHEN is_active = 1 THEN 1 ELSE 0 END) AS 激活数
FROM elderly;

SELECT 
  '活动表' AS 表名,
  COUNT(*) AS 记录数,
  SUM(CASE WHEN is_active = 1 THEN 1 ELSE 0 END) AS 激活数
FROM activity;

SELECT '' AS '';

-- 2. 报名数据统计
SELECT '【报名管理统计】' AS '类别';
SELECT 
  CASE 
    WHEN status = 'pending' THEN '待确认'
    WHEN status = 'confirmed' THEN '已确认'
    WHEN status = 'attended' THEN '已签到'
    WHEN status = 'absent' THEN '未参加'
    WHEN status = 'cancelled' THEN '已取消'
    ELSE '其他'
  END AS 状态,
  COUNT(*) AS 数量,
  status AS 英文状态
FROM enrollment
GROUP BY status
ORDER BY FIELD(status, 'pending', 'confirmed', 'attended', 'absent', 'cancelled');

SELECT 
  '报名总数' AS '统计项',
  COUNT(*) AS 数量
FROM enrollment;

SELECT '' AS '';

-- 3. 详细报名记录
SELECT '【报名记录详情】' AS '类别';
SELECT 
  e.id,
  e.activity_id AS 活动ID,
  a.name AS 活动名,
  e.elderly_id AS 老人ID,
  el.name AS 老人名,
  e.status AS 状态,
  e.enroll_time AS 报名时间,
  e.check_in_time AS 签到时间,
  e.notes AS 备注
FROM enrollment e
LEFT JOIN activity a ON e.activity_id = a.id
LEFT JOIN elderly el ON e.elderly_id = el.id
ORDER BY e.id;

SELECT '' AS '';

-- 4. 其他表统计
SELECT '【其他表统计】' AS '类别';
SELECT 
  '认证表' AS 表名,
  COUNT(*) AS 记录数,
  SUM(CASE WHEN is_active = 1 THEN 1 ELSE 0 END) AS 激活数
FROM auth;

SELECT 
  '亲属表' AS 表名,
  COUNT(*) AS 记录数,
  SUM(CASE WHEN is_active = 1 THEN 1 ELSE 0 END) AS 激活数
FROM relative;

SELECT 
  '健康记录表' AS 表名,
  COUNT(*) AS 记录数,
  SUM(CASE WHEN is_active = 1 THEN 1 ELSE 0 END) AS 激活数
FROM health_record;

SELECT 
  '服务订单表' AS 表名,
  COUNT(*) AS 记录数,
  SUM(CASE WHEN is_active = 1 THEN 1 ELSE 0 END) AS 激活数
FROM service_order;

SELECT 
  '紧急求助表' AS 表名,
  COUNT(*) AS 记录数,
  SUM(CASE WHEN is_active = 1 THEN 1 ELSE 0 END) AS 激活数
FROM emergency;

SELECT 
  '服务人员表' AS 表名,
  COUNT(*) AS 记录数,
  SUM(CASE WHEN is_active = 1 THEN 1 ELSE 0 END) AS 激活数
FROM worker;

SELECT 
  '角色表' AS 表名,
  COUNT(*) AS 记录数,
  SUM(CASE WHEN is_active = 1 THEN 1 ELSE 0 END) AS 激活数
FROM role;

SELECT 
  '权限表' AS 表名,
  COUNT(*) AS 记录数,
  SUM(CASE WHEN is_active = 1 THEN 1 ELSE 0 END) AS 激活数
FROM permission;

SELECT 
  '角色权限关联表' AS 表名,
  COUNT(*) AS 记录数
FROM role_permission;

SELECT '' AS '';
SELECT '================================' AS '数据库验证完毕';

-- ==============================================================
-- 快速查询
-- ==============================================================
-- 索引检查
SELECT '【索引检查】' AS '类别';
-- 健康记录表索引
SHOW INDEX FROM health_record;
SELECT '' AS '';
-- 服务订单表索引
SHOW INDEX FROM service_order;
SELECT '' AS '';
-- 报名表索引
SHOW INDEX FROM enrollment;
SELECT '' AS '';
-- 紧急求助表索引
SHOW INDEX FROM emergency;
SELECT '' AS '';
-- 认证表索引
SHOW INDEX FROM auth;
SELECT '' AS '';

-- 查看所有报名记录
-- SELECT * FROM enrollment ORDER BY id;

-- 按状态查询报名
-- SELECT * FROM enrollment WHERE status = 'pending';

-- 统计各活动的报名数
-- SELECT a.name, COUNT(*) as 报名数 FROM enrollment e 
--   LEFT JOIN activity a ON e.activity_id = a.id 
--   GROUP BY e.activity_id;

-- ==============================================================
