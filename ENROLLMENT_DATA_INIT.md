# 报名管理数据初始化

## 问题
报名管理界面为空，需要初始化数据。

## 解决方案

### 方案一：完整初始化（推荐）
如果是第一次设置，执行完整的数据库初始化：

#### 1. 创建表结构
```bash
mysql -u root -p < o-b/database_schema.sql
```

#### 2. 初始化所有数据（包含报名数据）
```bash
mysql -u root -p < o-b/database_init_data.sql
```

### 方案二：快速恢复报名数据
如果数据库已经存在，只需恢复报名数据：

#### 使用 MySQL 客户端
```bash
mysql -u root -p ohelp < o-b/QUICK_ENROLLMENT_DATA.sql
```

#### 使用 MySQL Workbench
1. 打开 MySQL Workbench
2. 连接到你的 MySQL 服务器
3. 打开文件 `o-b/QUICK_ENROLLMENT_DATA.sql`
4. 点击执行按钮

#### 或直接复制SQL语句执行
打开任何 MySQL 工具，连接到 ohelp 数据库，然后粘贴并执行 `QUICK_ENROLLMENT_DATA.sql` 的内容。

### 方案三：专用报名数据脚本
如果只需要额外的报名数据（不覆盖现有数据）：

```bash
mysql -u root -p ohelp < o-b/enrollment_init_data.sql
```

## 数据说明

### 初始数据包含
- **3个活动**：太极拳课程、书法班、棋类比赛
- **4个老人**：李老人、王老人、刘老人、陈老人
- **12个报名记录**，覆盖5种状态：

| 状态 | 数量 | 说明 |
|------|------|------|
| pending（待确认） | 4个 | 刚报名，等待管理员审核 |
| confirmed（已确认） | 4个 | 管理员已确认，老人准备参加 |
| attended（已签到） | 3个 | 老人已签到参加活动 |
| cancelled（已取消） | 1个 | 老人或管理员取消了报名 |
| absent（未参加） | 1个 | 确认后未参加活动 |

## 快速验证

### 1. 验证数据库连接
在 MySQL 中执行：
```sql
USE ohelp;
SELECT COUNT(*) as '报名总数' FROM enrollment;
SELECT DISTINCT status FROM enrollment;
```

### 2. 验证前端显示
1. 启动后端：`cd o-b && mvn spring-boot:run`
2. 启动前端：`cd o-f && npm run dev`
3. 访问：http://localhost:5173/admin-enrollment
4. 使用账户登录：
   - 用户名：**admin**
   - 密码：**admin123**

### 3. 查看报名数据
在报名管理界面应该能看到：
- 12条报名记录
- 状态包括：待确认、已确认、已签到、未参加、已取消
- 可以按状态过滤和搜索

## 常见问题

### Q1: 执行脚本后还是看不到数据
**解决方案：**
1. 确认数据库是否存在：`SHOW DATABASES;`
2. 确认表是否创建：`SHOW TABLES;`
3. 确认数据是否插入：`SELECT COUNT(*) FROM enrollment;`
4. 刷新浏览器：Ctrl+F5 或 Cmd+Shift+R
5. 检查后端日志是否有错误

### Q2: 如何清空所有报名数据重新初始化
```sql
DELETE FROM enrollment WHERE 1=1;
```
然后重新执行初始化脚本。

### Q3: 如何添加更多测试数据
在报名管理界面点击"+ 添加报名"按钮，或直接SQL插入：
```sql
INSERT INTO enrollment (activity_id, elderly_id, status, enroll_time, notes, is_active) 
VALUES (1, 2, 'pending', NOW(), '新添加的报名', 1);
```

### Q4: 活动ID或老人ID不对应怎么办
首先检查你的 activity 和 elderly 表中的实际ID：
```sql
-- 查看活动
SELECT id, name FROM activity WHERE is_active = 1;

-- 查看老人
SELECT id, name FROM elderly WHERE is_active = 1;
```

然后根据实际ID修改SQL语句中的 activity_id 和 elderly_id。

## 相关文件

- `o-b/database_schema.sql` - 数据库表结构脚本
- `o-b/database_init_data.sql` - 所有模块初始化数据
- `o-b/enrollment_init_data.sql` - 专用报名数据脚本
- `o-b/QUICK_ENROLLMENT_DATA.sql` - 快速恢复脚本
- `DATABASE_INIT_GUIDE.md` - 详细初始化指南
- `ENROLLMENT_COMPLETION_REPORT.md` - 功能完成报告

## 技术支持

如有问题，请检查：
1. MySQL 服务是否运行
2. 连接字符串是否正确
3. 脚本是否完全执行（无错误）
4. 前后端是否都启动
5. 浏览器缓存是否清空
