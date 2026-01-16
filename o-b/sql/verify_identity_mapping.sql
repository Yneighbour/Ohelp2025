-- 验证身份映射表是否创建成功
USE ohelp;

-- 1. 检查表是否存在
SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES 
WHERE TABLE_SCHEMA='ohelp' AND TABLE_NAME IN ('user_elder_mapping', 'user_family_mapping')
ORDER BY TABLE_NAME;

-- 2. 查看 user_elder_mapping 表结构
DESCRIBE user_elder_mapping;

-- 3. 查看 user_family_mapping 表结构
DESCRIBE user_family_mapping;

-- 4. 查看表中的数据（应为空）
SELECT 'user_elder_mapping records:' AS info, COUNT(*) AS count FROM user_elder_mapping
UNION ALL
SELECT 'user_family_mapping records:', COUNT(*) FROM user_family_mapping;

-- 5. 检查统一视图是否存在
SELECT TABLE_NAME, TABLE_TYPE FROM INFORMATION_SCHEMA.TABLES 
WHERE TABLE_SCHEMA='ohelp' AND TABLE_NAME='v_user_elderly_binding';
