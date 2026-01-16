# 🚀 RBAC 系统快速部署指南

## 部署前检查

### 确认文件完整性

运行以下命令验证所有必需文件已创建：

```powershell
# 后端文件（应显示 11 个文件）
Get-ChildItem -Path "o-b\src\main\java\com\soft\ob\role" -Recurse -Filter "*.java" | Measure-Object

# 前端 API 文件（应显示 2 个文件）
Get-ChildItem -Path "o-f\src\api" -Filter "role.js","permission.js"

# 前端视图文件（验证已更新）
Get-ChildItem -Path "o-f\src\views" -Filter "AdminRoleManageView.vue","AdminPermissionView.vue"

# 数据库脚本（验证已更新）
Get-ChildItem -Path "o-b" -Filter "database_*.sql"
```

## 第一步：数据库迁移

### 1.1 连接到 MySQL

```bash
mysql -u root -p
```

### 1.2 执行 Schema 脚本

```sql
USE ohelp;
SOURCE D:/DOCE/ohelp2025/Ohelp2025/o-b/database_schema.sql;
```

**或使用 PowerShell**:

```powershell
mysql -u root -p ohelp < "D:\DOCE\ohelp2025\Ohelp2025\o-b\database_schema.sql"
```

### 1.3 执行初始化数据脚本

```sql
SOURCE D:/DOCE/ohelp2025/Ohelp2025/o-b/database_init_data.sql;
```

**或使用 PowerShell**:

```powershell
mysql -u root -p ohelp < "D:\DOCE\ohelp2025\Ohelp2025\o-b\database_init_data.sql"
```

### 1.4 验证数据已插入

```sql
USE ohelp;

-- 验证表存在
SHOW TABLES LIKE '%role%';
-- 应显示: role, role_permission

SHOW TABLES LIKE '%permission%';
-- 应显示: permission, role_permission

-- 验证数据已插入
SELECT COUNT(*) AS role_count FROM role;
-- 应返回: 3（admin, operator, user）

SELECT COUNT(*) AS permission_count FROM permission;
-- 应返回: 30+ （所有模块的权限）

SELECT COUNT(*) AS assignment_count FROM role_permission;
-- 应返回: 多条（默认权限分配记录）

-- 查看预置角色
SELECT id, name, code, description, is_active FROM role;

-- 查看权限模块分布
SELECT module, COUNT(*) AS permission_count 
FROM permission 
GROUP BY module;

-- 查看管理员权限数量
SELECT COUNT(*) AS admin_permissions
FROM role_permission
WHERE role_id = (SELECT id FROM role WHERE code = 'admin');
-- 应返回: 所有权限数量（30+）
```

## 第二步：后端启动

### 2.1 清理并重新编译

```powershell
cd D:\DOCE\ohelp2025\Ohelp2025\o-b
.\mvnw.cmd clean install -DskipTests
```

### 2.2 启动 Spring Boot

```powershell
.\mvnw.cmd spring-boot:run
```

### 2.3 验证后端接口

**打开新的 PowerShell 窗口**，运行：

```powershell
# 测试角色列表接口
Invoke-RestMethod -Uri "http://localhost:8080/api/role" -Method GET

# 测试权限列表接口
Invoke-RestMethod -Uri "http://localhost:8080/api/permission" -Method GET

# 测试创建角色接口
$body = @{
    name = "测试角色"
    code = "test_role"
    description = "这是一个测试角色"
    isActive = $true
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/role" -Method POST -Body $body -ContentType "application/json"

# 获取角色权限（假设角色ID为1）
Invoke-RestMethod -Uri "http://localhost:8080/api/role/1/permissions" -Method GET
```

**预期响应**（所有接口）:

```json
{
  "code": 200,
  "message": "操作成功",
  "data": [ ... ]
}
```

## 第三步：前端启动

### 3.1 安装依赖（如果尚未安装）

```powershell
cd D:\DOCE\ohelp2025\Ohelp2025\o-f
npm install
```

### 3.2 启动开发服务器

```powershell
npm run dev
```

### 3.3 访问应用

打开浏览器访问：`http://localhost:5173`

## 第四步：功能测试

### 4.1 登录系统

使用管理员账户登录：
- 用户名：`admin`
- 密码：`admin123`

### 4.2 测试角色管理

1. **查看角色列表**
   - 导航到"角色管理"页面
   - 应显示 3 个预置角色（系统管理员、操作员、普通用户）

2. **创建新角色**
   - 点击"+ 添加角色"
   - 输入：
     - 角色名称：`测试管理员`
     - 角色编码：`test_admin`
     - 角色描述：`用于测试的管理员角色`
     - 启用状态：勾选
   - 点击"保存"
   - 验证角色出现在列表中

3. **编辑角色**
   - 点击新创建角色的"编辑"按钮
   - 修改描述为：`这是用于测试的管理员角色`
   - 点击"保存"
   - 验证描述已更新
   - **注意**：编辑时角色编码字段应为禁用状态（不可修改）

4. **删除角色**
   - 点击新创建角色的"删除"按钮
   - 确认删除提示
   - 验证角色从列表中移除

### 4.3 测试权限配置

1. **进入权限配置页面**
   - 在角色列表中，点击"操作员"角色的"配置权限"按钮
   - 应跳转到权限配置页面，标题显示"权限设置 - 操作员"

2. **查看权限分组**
   - 验证权限按模块分组显示（用户管理、老人管理、活动管理等）
   - 验证操作员已有权限的开关为开启状态

3. **修改权限**
   - 找到"角色管理"模块
   - 切换"角色列表查看"权限开关（从禁用改为启用）
   - 点击"保存设置"
   - 验证保存成功提示

4. **验证权限持久化**
   - 点击"返回"按钮回到角色管理
   - 再次点击"操作员"的"配置权限"
   - 验证刚才启用的"角色列表查看"权限仍为开启状态

5. **测试批量权限变更**
   - 在权限配置页面，切换多个权限开关（至少 3 个）
   - 点击"保存设置"
   - 返回后重新进入，验证所有变更都已保存

### 4.4 数据库验证

在 MySQL 中验证数据一致性：

```sql
-- 查询操作员角色的权限数量（应在保存后增加）
SELECT COUNT(*) 
FROM role_permission rp
JOIN role r ON rp.role_id = r.id
WHERE r.code = 'operator';

-- 查询操作员角色的详细权限列表
SELECT p.module, p.name, p.code
FROM role_permission rp
JOIN role r ON rp.role_id = r.id
JOIN permission p ON rp.permission_id = p.id
WHERE r.code = 'operator'
ORDER BY p.module, p.code;
```

## 第五步：集成测试

### 5.1 创建完整流程测试角色

1. **创建角色**
   - 角色名称：`高级操作员`
   - 角色编码：`senior_operator`
   - 描述：`拥有扩展权限的操作员`

2. **分配权限**
   - 为该角色分配以下权限：
     - 用户管理：view, create, update（不包括 delete）
     - 老人管理：所有权限
     - 活动管理：所有权限
     - 健康管理：view, create, update
     - 紧急呼叫：view, handle
     - 服务订单：view, create
   - 保存设置

3. **分配角色给用户**
   - 导航到"用户管理"
   - 点击某个用户的"编辑"按钮
   - 在角色下拉框中选择"高级操作员"
   - 保存

4. **数据库验证**
   ```sql
   -- 验证用户角色已更新
   SELECT id, name, role FROM user WHERE role = 'senior_operator';
   
   -- 验证高级操作员的权限数量
   SELECT COUNT(*) 
   FROM role_permission rp
   JOIN role r ON rp.role_id = r.id
   WHERE r.code = 'senior_operator';
   ```

## 常见问题排查

### 问题 1: 后端接口返回 404

**检查**:
```powershell
# 验证后端是否正常启动
Get-Process | Where-Object { $_.ProcessName -like "*java*" }

# 检查端口 8080 是否被占用
netstat -ano | findstr :8080
```

**解决**:
- 确认后端已启动：`.\mvnw.cmd spring-boot:run`
- 检查控制台日志是否有错误

### 问题 2: 前端显示"后端接口不可用，已切换为演示数据"

**检查**:
```powershell
# 测试后端接口是否可访问
Invoke-RestMethod -Uri "http://localhost:8080/api/role" -Method GET
```

**解决**:
- 确认后端已启动且无错误
- 检查 `o-f/src/api/http.js` 中的 `baseURL` 配置
- 检查浏览器控制台的网络请求是否有 CORS 错误

### 问题 3: 数据库表不存在

**检查**:
```sql
USE ohelp;
SHOW TABLES LIKE '%role%';
SHOW TABLES LIKE '%permission%';
```

**解决**:
- 重新执行 `database_schema.sql`
- 检查 SQL 脚本执行是否有错误

### 问题 4: 保存权限后数据未更新

**检查**:
```sql
-- 查看最新的 role_permission 记录
SELECT * FROM role_permission ORDER BY id DESC LIMIT 10;
```

**解决**:
- 检查浏览器控制台是否有错误
- 检查后端日志是否有异常
- 验证前端发送的 `permissionIds` 数组格式是否正确

### 问题 5: 编辑角色时编码字段可修改

**解决**:
- 检查 `AdminRoleManageView.vue` 中的代码字段是否有 `:disabled="dialogMode === 'edit'"` 属性

## 回滚方案

如果需要回滚 RBAC 功能：

### 1. 删除数据库表

```sql
USE ohelp;

DROP TABLE IF EXISTS role_permission;
DROP TABLE IF EXISTS permission;
DROP TABLE IF EXISTS role;
```

### 2. 停止后端

```powershell
# 在运行 mvnw 的终端按 Ctrl+C
```

### 3. 恢复前端文件（可选）

如果需要恢复到演示版本，使用 Git 还原：

```powershell
git checkout HEAD -- o-f/src/views/AdminRoleManageView.vue
git checkout HEAD -- o-f/src/views/AdminPermissionView.vue
```

## 性能监控

### 监控数据库查询

```sql
-- 查看最慢的查询
SHOW FULL PROCESSLIST;

-- 查看表大小
SELECT 
    table_name AS 'Table',
    ROUND(((data_length + index_length) / 1024 / 1024), 2) AS 'Size (MB)'
FROM information_schema.TABLES 
WHERE table_schema = 'ohelp'
AND table_name IN ('role', 'permission', 'role_permission')
ORDER BY (data_length + index_length) DESC;
```

### 监控后端日志

```powershell
# 实时查看后端日志
Get-Content -Path "o-b\logs\spring.log" -Wait -Tail 50
```

## 下一步

完成部署和测试后，建议：

1. **实现前端权限拦截**: 根据用户权限动态显示/隐藏功能按钮
2. **实现后端权限验证**: 集成 Spring Security，使用注解式权限控制
3. **添加审计日志**: 记录所有角色和权限变更
4. **性能优化**: 使用 Redis 缓存用户权限列表

详细扩展方案请参考 `RBAC_IMPLEMENTATION.md`。

---

**部署完成标志**:
- ✅ 后端成功启动，接口返回 200
- ✅ 前端可正常访问，无控制台错误
- ✅ 数据库包含 3 个角色、30+ 权限
- ✅ 可成功创建/编辑/删除角色
- ✅ 可成功配置角色权限

祝部署顺利！🎉
