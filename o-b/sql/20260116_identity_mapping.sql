-- ==============================================================
-- Ohelp2025 用户身份与业务主体统一映射设计
-- ==============================================================
-- 设计原则：
-- 1️⃣ 登录主体：所有用户（老人/家属/管理端）均以 user 表统一登录
--    - 用户名（username）：user.id 或 user.phone（系统唯一标识）
--    - 初始密码：elderly.phone_number 或 relative.phone（对应老人/家属手机号）
-- 
-- 2️⃣ 身份映射：
--    - elder 账号：user (1) ↔ elderly (1)  [一对一映射]
--    - family 账号：user (1) → elderly (n) [一对一绑定某个老人]
--    - family 代理老人业务操作（业务主体始终是 elderly）
--
-- 3️⃣ 业务关系：elderly (1) ← (n) relative [老人一对多家属]
--    - 创建 family 账号时，需绑定一个 relative 记录到某个 elderly
--
-- 4️⃣ 约束与规则：
--    - 每个老人最多只有一个 elder 登录账号
--    - 一个家属账号只能绑定一个老人（但多个家属可绑定同一老人）
--    - 登录后所有业务操作代表绑定的 elderly_id，权限受 role 限制
-- ==============================================================

USE ohelp;

-- ========== 一、老人 elder 角色账号与业务主体一对一映射 ==========
CREATE TABLE IF NOT EXISTS user_elder_mapping (
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
  user_id BIGINT NOT NULL COMMENT '登录用户ID（user.id）',
  elderly_id BIGINT NOT NULL COMMENT '老人业务主体ID（elderly.id）',
  account_source VARCHAR(50) COMMENT '账号来源：manual(手工创建), auto(自动生成)',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  UNIQUE KEY uk_user_id (user_id) COMMENT '确保每个 user 仅映射一个 elderly',
  UNIQUE KEY uk_elderly_id (elderly_id) COMMENT '确保每个 elderly 仅有一个 user',
  CONSTRAINT fk_uem_user FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
  CONSTRAINT fk_uem_elderly FOREIGN KEY (elderly_id) REFERENCES elderly(id) ON DELETE CASCADE,
  INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci 
COMMENT='老人端登录账号：一个 user 对应一个 elderly（role='elder'）';

-- ========== 二、家属 family 角色账号与老人绑定 ==========
CREATE TABLE IF NOT EXISTS user_family_mapping (
  id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
  user_id BIGINT NOT NULL COMMENT '家属登录用户ID（user.id）',
  elderly_id BIGINT NOT NULL COMMENT '所代理的老人ID（elderly.id）',
  relative_id BIGINT NOT NULL COMMENT '对应的亲属记录ID（relative.id，family 账号必须绑定一个 relative）',
  is_primary_contact TINYINT(1) DEFAULT 0 COMMENT '是否为主要联系人（冗余字段，来自 relative.is_primary_contact）',
  account_source VARCHAR(50) COMMENT '账号来源：manual(手工创建), auto(自动生成)',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  UNIQUE KEY uk_user_id (user_id) COMMENT '家属账号一对一绑定某个老人',
  UNIQUE KEY uk_relative_id (relative_id) COMMENT '每个 relative 仅有一个对应的 user 账号',
  INDEX idx_elderly_id (elderly_id) COMMENT '查询某个老人的所有家属账号',
  CONSTRAINT fk_ufm_user FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
  CONSTRAINT fk_ufm_elderly FOREIGN KEY (elderly_id) REFERENCES elderly(id) ON DELETE CASCADE,
  CONSTRAINT fk_ufm_relative FOREIGN KEY (relative_id) REFERENCES relative(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci 
COMMENT='家属端登录账号：代理其绑定老人的业务操作（role='family'）';

-- ========== 三、统一视图：快速查询用户绑定的老人ID ==========
DROP VIEW IF EXISTS v_user_elderly_binding;
CREATE VIEW v_user_elderly_binding AS
  SELECT 
    user_id,
    elderly_id,
    'elder' AS user_type,
    NULL AS relative_id
  FROM user_elder_mapping
  UNION ALL
  SELECT 
    user_id,
    elderly_id,
    'family' AS user_type,
    relative_id
  FROM user_family_mapping;

-- 后端使用示例：
-- SELECT elderly_id, user_type FROM v_user_elderly_binding WHERE user_id = ? LIMIT 1;
-- 登录后解析当前用户绑定的 elderly_id，所有后续业务操作均基于此 elderly_id 进行权限与数据范围限制

-- ========== 四、初始化说明与示例 ==========
/*
-- 示例1：为一位老人创建 elder 登录账号
-- 前置条件：已存在 elderly.id=5 和 phone_number='13900139005'
-- 步骤：
--   1) 创建用户账号
INSERT INTO user (name, email, phone, role, is_active, created_at) 
VALUES ('Li Lao (elder)', NULL, '13900139005', 'elder', 1, NOW());
-- 获得 user.id = 1001

--   2) 映射 user → elderly
INSERT INTO user_elder_mapping (user_id, elderly_id, account_source) 
VALUES (1001, 5, 'manual');

--   3) 更新 auth 记录（认证时 username 使用 user.phone，初始密码为 elderly.phone_number）
INSERT INTO auth (username, password, user_id, is_active, created_at) 
VALUES ('13900139005', '13900139005', 1001, 1, NOW());

-- 示例2：为一位老人的家属创建 family 登录账号
-- 前置条件：
--   - elderly.id=5
--   - relative.id=12, elderly_id=5, phone='13800138006'（李老人的儿子）
-- 步骤：
--   1) 创建用户账号
INSERT INTO user (name, email, phone, role, is_active, created_at) 
VALUES ('Li Xiaoming (family)', NULL, '13800138006', 'family', 1, NOW());
-- 获得 user.id = 2001

--   2) 映射 user → elderly（代理该老人）
INSERT INTO user_family_mapping (user_id, elderly_id, relative_id, is_primary_contact, account_source) 
VALUES (2001, 5, 12, 1, 'manual');

--   3) 更新 auth 记录（认证时 username 使用 user.phone，初始密码为 relative.phone）
INSERT INTO auth (username, password, user_id, is_active, created_at) 
VALUES ('13800138006', '13800138006', 2001, 1, NOW());

-- 登录时的行为：
--   - 李老人（elder）使用 username='13900139005', password='13900139005' 登录
--   - 李小明（family）使用 username='13800138006', password='13800138006' 登录
--   - 登录后双方都代表 elderly_id=5 进行业务操作
--   - 权限范围由 role 与 permission 表控制
*/

-- ========== 五、数据一致性检查脚本 ==========
-- 检查是否存在孤立的映射记录（对应的 user/elderly/relative 被删除）
-- SELECT * FROM user_elder_mapping WHERE user_id NOT IN (SELECT id FROM user) OR elderly_id NOT IN (SELECT id FROM elderly);
-- SELECT * FROM user_family_mapping WHERE user_id NOT IN (SELECT id FROM user) OR elderly_id NOT IN (SELECT id FROM elderly) OR relative_id NOT IN (SELECT id FROM relative);

-- 检查 elderly 表中是否存在未被映射的记录（无对应的 elder 账号）
-- SELECT id, name FROM elderly WHERE id NOT IN (SELECT elderly_id FROM user_elder_mapping);

-- 统计每个老人对应的账号数
-- SELECT elderly_id, COUNT(*) AS account_count FROM v_user_elderly_binding GROUP BY elderly_id;
