# 报名管理界面数据初始化指南

> **问题**：报名管理界面是空的，需要初始数据

> **原因**：数据库尚未初始化或初始化脚本中报名数据不足

## 快速解决方案

### ⚡ 最快方式（5分钟）

1. **打开 MySQL 客户端**（任何 MySQL 工具均可）

2. **连接到数据库**
   ```sql
   USE ohelp;
   ```

3. **复制以下SQL并执行**
   ```sql
   -- 清空现有数据（可选）
   -- DELETE FROM enrollment WHERE 1=1;
   
   -- 插入报名数据
   INSERT INTO enrollment (activity_id, elderly_id, status, enroll_time, check_in_time, notes, is_active) VALUES
   (1, 1, 'attended', DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY), '已参加太极拳课程', 1),
   (1, 2, 'confirmed', DATE_SUB(NOW(), INTERVAL 6 DAY), NULL, '已确认参加太极拳', 1),
   (1, 3, 'pending', DATE_SUB(NOW(), INTERVAL 5 DAY), NULL, '刚报名太极拳，待管理员确认', 1),
   (1, 4, 'cancelled', DATE_SUB(NOW(), INTERVAL 4 DAY), NULL, '因身体不适取消太极拳报名', 1),
   (2, 1, 'attended', DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 10 DAY), '参加书法班学习', 1),
   (2, 2, 'attended', DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_SUB(NOW(), INTERVAL 10 DAY), '完成书法班课程', 1),
   (2, 3, 'pending', DATE_SUB(NOW(), INTERVAL 9 DAY), NULL, '报名书法班，等待确认', 1),
   (2, 4, 'absent', DATE_SUB(NOW(), INTERVAL 11 DAY), NULL, '报名书法班但未参加', 1),
   (3, 1, 'confirmed', DATE_SUB(NOW(), INTERVAL 2 DAY), NULL, '已确认参加棋类比赛', 1),
   (3, 2, 'pending', NOW(), NULL, '新报名棋类比赛', 1),
   (3, 3, 'confirmed', DATE_SUB(NOW(), INTERVAL 1 DAY), NULL, '已确认参加棋类比赛', 1),
   (3, 4, 'pending', NOW(), NULL, '报名棋类比赛中', 1);
   ```

4. **验证数据**
   ```sql
   SELECT COUNT(*) as 报名总数, status FROM enrollment GROUP BY status;
   ```

5. **刷新浏览器**
   - 在报名管理界面按 `Ctrl+F5` 或 `Cmd+Shift+R` 清除缓存

✅ 完成！应该能看到 12 条报名记录了

---

## 详细初始化步骤

### 第一次完整初始化

如果数据库还没有任何数据，请按顺序执行：

#### 1️⃣ 创建数据库表结构
```bash
mysql -u root -p < o-b/database_schema.sql
```
或在 MySQL 客户端执行：
```sql
SOURCE /path/to/o-b/database_schema.sql;
```

#### 2️⃣ 初始化所有模块的示例数据
```bash
mysql -u root -p < o-b/database_init_data.sql
```

此步骤包含了：
- ✓ 用户账户
- ✓ 老人信息
- ✓ 活动信息
- ✓ **报名数据**（新增，12条记录）
- ✓ 健康记录
- ✓ 紧急求助
- ✓ 服务订单
- ✓ 角色和权限

---

## 数据统计

### 初始化后的报名数据

| 活动 | 报名数 | 待确认 | 已确认 | 已签到 | 未参加 | 已取消 |
|------|--------|--------|--------|--------|--------|--------|
| 太极拳课程 | 4 | 1 | 1 | 1 | 0 | 1 |
| 书法班 | 4 | 1 | 0 | 2 | 1 | 0 |
| 棋类比赛 | 4 | 2 | 2 | 0 | 0 | 0 |
| **总计** | **12** | **4** | **3** | **3** | **1** | **1** |

### 状态分布
- 🔵 **待确认（pending）**：4条 - 新报名，需要管理员审核
- 🟢 **已确认（confirmed）**：3条 - 已确认，准备参加或参加中
- 🟡 **已签到（attended）**：3条 - 已完成签到
- ⚪ **未参加（absent）**：1条 - 确认后没有参加
- 🔴 **已取消（cancelled）**：1条 - 已取消报名

---

## 验证清单

✅ **SQL执行成功**
```sql
SELECT COUNT(*) FROM enrollment;
-- 应该返回 12 或更多
```

✅ **状态分布正确**
```sql
SELECT status, COUNT(*) FROM enrollment GROUP BY status;
```

✅ **前端显示正确**
1. 访问 http://localhost:5173/admin-enrollment
2. 使用账户登录（admin/admin123）
3. 应该看到12条报名记录

✅ **功能正常**
- 列表过滤：选择不同状态，记录数应该减少
- 搜索功能：输入活动ID或老人ID，应该能搜索到
- 操作按钮：根据状态显示不同的操作按钮

---

## 常见问题排查

### ❌ 仍然看不到数据？

#### 1. 检查MySQL连接
```bash
mysql -u root -p
mysql> SHOW DATABASES;
mysql> USE ohelp;
mysql> SELECT COUNT(*) FROM enrollment;
```

#### 2. 检查表是否存在
```sql
SHOW TABLES LIKE 'enrollment';
-- 应该返回 enrollment 表
```

#### 3. 检查后端连接配置
编辑 `o-b/src/main/resources/application.properties`：
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ohelp?serverTimezone=Asia/Shanghai
spring.datasource.username=root
spring.datasource.password=your_password
```

#### 4. 重启后端服务
```bash
cd o-b
mvn spring-boot:run
# 等待服务启动完成
```

#### 5. 清除浏览器缓存
- Chrome: `Ctrl+Shift+Delete`
- Firefox: `Ctrl+Shift+Delete`
- Safari: `Cmd+Shift+Delete`

### ❌ SQL执行出错？

#### 外键约束错误
确保 activity 表和 elderly 表中有对应的ID：
```sql
SELECT id, name FROM activity WHERE is_active = 1;
SELECT id, name FROM elderly WHERE is_active = 1;
```

#### 重复键错误
如果出现"Duplicate entry"，先清空数据：
```sql
DELETE FROM enrollment WHERE 1=1;
```

然后重新执行插入语句。

---

## 可用的脚本文件

| 文件 | 用途 | 说明 |
|------|------|------|
| `database_schema.sql` | 创建表结构 | 第一次初始化必须执行 |
| `database_init_data.sql` | 初始化所有数据 | 包含报名在内的所有模块数据 |
| `enrollment_init_data.sql` | 专用报名数据 | 如需更多报名数据可执行 |
| `QUICK_ENROLLMENT_DATA.sql` | 快速恢复脚本 | 可直接用于恢复报名数据 |

---

## 下一步

1. ✅ 执行SQL初始化数据
2. ✅ 启动后端：`mvn spring-boot:run`
3. ✅ 启动前端：`npm run dev`
4. ✅ 访问报名管理界面
5. 🎯 开始测试各项功能：
   - 创建新报名
   - 编辑报名信息
   - 确认报名
   - 签到
   - 取消报名
   - 删除报名

---

## 相关文档

- 📄 [完整初始化指南](DATABASE_INIT_GUIDE.md)
- 📄 [报名功能完成报告](ENROLLMENT_COMPLETION_REPORT.md)
- 📄 [详细初始化说明](ENROLLMENT_DATA_INIT.md)
