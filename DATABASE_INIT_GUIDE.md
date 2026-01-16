# 数据库初始化指南

## 快速开始

### 1. 创建数据库表结构
在 MySQL 中执行以下脚本：

```bash
# Linux/Mac
mysql -u root -p < o-b/database_schema.sql

# 或者在 MySQL 客户端中
SOURCE o-b/database_schema.sql;
```

### 2. 初始化示例数据
执行主数据初始化脚本，包含所有模块的测试数据：

```bash
mysql -u root -p < o-b/database_init_data.sql
```

### 3. （可选）补充报名数据
如果需要更多报名管理的示例数据，可额外执行：

```bash
mysql -u root -p < o-b/enrollment_init_data.sql
```

## 脚本说明

### database_schema.sql
创建数据库和所有表的结构：
- auth（认证）
- user（用户）
- elderly（老人）
- relative（亲属）
- activity（活动）
- emergency（紧急求助）
- health_record（健康记录）
- service_order（服务订单）
- worker（服务人员）
- role（角色）
- permission（权限）
- role_permission（角色权限关联）
- **enrollment（活动报名）** ← 新增

### database_init_data.sql
初始化各模块的示例数据：
- ✓ 4个用户账户（admin, manager, staff, user）
- ✓ 4个老人信息
- ✓ 4个亲属信息
- ✓ 3个活动
- ✓ 3个紧急求助记录
- ✓ 4个健康记录
- ✓ 3个服务订单
- ✓ 3个服务人员
- ✓ 3个角色
- ✓ 15+个权限
- ✓ 角色权限关联
- **✓ 12个报名记录**（新增）

### enrollment_init_data.sql
专用的报名数据初始化脚本，包含：
- 太极拳课程的4个报名（已参加、已确认、待确认、已取消）
- 书法班的4个报名（已参加、已参加、待确认、未参加）
- 棋类比赛的4个报名（已确认、待确认、已确认、待确认）

**总共12条报名记录，覆盖所有5种状态**

## 数据库连接配置

在 `o-b/src/main/resources/application.properties` 中配置：

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ohelp?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8mb4
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

## 报名管理界面数据查询

### 待确认报名（pending）
```sql
SELECT * FROM enrollment WHERE status = 'pending';
```

### 已确认报名（confirmed）
```sql
SELECT * FROM enrollment WHERE status = 'confirmed';
```

### 已签到报名（attended）
```sql
SELECT * FROM enrollment WHERE status = 'attended';
```

### 未参加报名（absent）
```sql
SELECT * FROM enrollment WHERE status = 'absent';
```

### 已取消报名（cancelled）
```sql
SELECT * FROM enrollment WHERE status = 'cancelled';
```

## 常见问题

### Q: 执行脚本后报名管理界面仍为空
**A:** 检查以下项：
1. 确认 MySQL 服务已启动
2. 确认执行了 `database_schema.sql`（创建enrollment表）
3. 确认执行了 `database_init_data.sql`（插入数据）
4. 确认后端连接字符串正确，与脚本中的database一致
5. 刷新浏览器缓存（Ctrl+F5 或 Cmd+Shift+R）

### Q: 如何清空报名数据重新初始化
**A:** 执行以下SQL：
```sql
DELETE FROM enrollment WHERE 1=1;
```
然后重新运行 `database_init_data.sql`

### Q: 如何添加新的报名记录
**A:** 通过前端报名管理界面的"+ 添加报名"按钮，或直接SQL插入：
```sql
INSERT INTO enrollment (activity_id, elderly_id, status, enroll_time, notes, is_active) 
VALUES (1, 1, 'pending', NOW(), '新报名记录', 1);
```

## 数据统计

当前初始化数据包含：
- **活动总数**：3个
- **老人总数**：4个
- **报名总数**：12个
- **状态分布**：
  - 待确认：4个
  - 已确认：4个
  - 已签到：3个
  - 未参加：1个
  - 已取消：1个

## 后续操作

1. **启动后端**：`mvn spring-boot:run`
2. **启动前端**：`npm run dev`
3. **访问管理界面**：http://localhost:5173/admin-enrollment
4. **使用账户登录**：
   - 用户名：admin
   - 密码：admin123
