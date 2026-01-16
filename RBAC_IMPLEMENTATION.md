# RBAC 角色权限管理系统实施说明

## 📋 概述

本文档描述了 Ohelp2025 老年人服务管理系统中完整的 RBAC（Role-Based Access Control，基于角色的访问控制）实现方案。

## 🏗️ 系统架构

### 数据库设计

#### 1. role 表（角色表）
```sql
CREATE TABLE role (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE,      -- 角色名称，如"系统管理员"
  code VARCHAR(50) NOT NULL UNIQUE,       -- 角色编码，如"admin"
  description VARCHAR(500),               -- 角色描述
  is_active TINYINT(1) DEFAULT 1,        -- 是否激活
  created_at DATETIME,
  updated_at DATETIME
);
```

#### 2. permission 表（权限表）
```sql
CREATE TABLE permission (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL UNIQUE,      -- 权限名称，如"用户列表查看"
  code VARCHAR(100) NOT NULL UNIQUE,      -- 权限编码，如"user:view"
  module VARCHAR(50),                     -- 所属模块，如"用户管理"
  description VARCHAR(500),               -- 权限描述
  is_active TINYINT(1) DEFAULT 1,
  created_at DATETIME,
  updated_at DATETIME
);
```

#### 3. role_permission 表（角色权限关联表）
```sql
CREATE TABLE role_permission (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  role_id BIGINT NOT NULL,
  permission_id BIGINT NOT NULL,
  created_at DATETIME,
  FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE,
  FOREIGN KEY (permission_id) REFERENCES permission(id) ON DELETE CASCADE,
  UNIQUE KEY (role_id, permission_id)
);
```

### 后端实现

#### 实体类（Entity）

- **Role.java**：角色实体，包含 name、code、description、isActive
- **Permission.java**：权限实体，包含 name、code、module、description、isActive
- **RolePermission.java**：角色权限关联实体

所有实体均使用：
- JPA 注解（`@Entity`, `@Table`, `@Id`, `@GeneratedValue`）
- Lombok 注解（`@Data` 自动生成 getter/setter）
- `@PrePersist` 和 `@PreUpdate` 自动管理时间戳

#### 数据访问层（Mapper）

使用 MyBatis 注解风格：

- **RoleMapper.java**：
  - `@Select` 查询所有角色、根据ID查询
  - `@Insert` 创建角色
  - `@Update` 更新角色信息、激活/禁用
  - `@Delete` 删除角色

- **PermissionMapper.java**：
  - 完整的 CRUD 操作
  - `selectByModule` 按模块查询权限

- **RolePermissionMapper.java**：
  - `selectPermissionsByRoleId` 查询角色拥有的权限（JOIN 查询）
  - `insertBatch` 批量插入权限分配
  - `deleteByRoleId` 删除角色所有权限

#### 业务逻辑层（Service）

- **RoleService.java**：角色管理业务逻辑
- **PermissionService.java**：权限管理业务逻辑
- **RolePermissionService.java**：
  - `assignPermissionsToRole` 方法：
    - 使用 `@Transactional` 保证事务一致性
    - 先删除该角色的所有权限
    - 再批量插入新的权限分配

#### 控制器层（Controller）

**RoleController.java** (`/api/role`)：
- `GET /` - 查询所有角色
- `GET /{id}` - 根据ID查询角色
- `POST /` - 创建角色
- `PUT /{id}` - 更新角色
- `DELETE /{id}` - 删除角色
- `PUT /{id}/activate` - 激活角色
- `PUT /{id}/deactivate` - 禁用角色
- `GET /{id}/permissions` - 获取角色权限列表
- `PUT /{id}/permissions` - 批量分配权限（接收 `{permissionIds: [1,2,3]}`）

**PermissionController.java** (`/api/permission`)：
- `GET /` - 查询所有权限
- `GET /{id}` - 根据ID查询权限
- `GET /module/{module}` - 按模块查询权限
- `POST /` - 创建权限
- `PUT /{id}` - 更新权限
- `DELETE /{id}` - 删除权限

所有接口返回标准信封格式：
```json
{
  "code": 200,
  "message": "操作成功",
  "data": { ... }
}
```

### 前端实现

#### API 客户端（src/api）

**role.js**：
```javascript
export async function listAll() { ... }
export async function getById(id) { ... }
export async function createRole(data) { ... }
export async function updateRole(id, data) { ... }
export async function deleteRole(id) { ... }
export async function activateRole(id) { ... }
export async function deactivateRole(id) { ... }
export async function getRolePermissions(roleId) { ... }
export async function assignPermissions(roleId, permissionIds) { ... }
```

**permission.js**：
```javascript
export async function listAll() { ... }
export async function getById(id) { ... }
export async function listByModule(module) { ... }
export async function createPermission(data) { ... }
export async function updatePermission(id, data) { ... }
export async function deletePermission(id) { ... }
```

#### 管理界面（src/views）

**AdminRoleManageView.vue**：
- 角色列表展示（ID、名称、编码、用户数、权限范围、状态、创建时间）
- CRUD 对话框：
  - 新增模式：输入角色名称、编码、描述、启用状态
  - 编辑模式：加载现有数据，角色编码不可修改
- 操作按钮：
  - "配置权限"：跳转到 AdminPermissionView 并传递 roleId 和 roleName
  - "编辑"：打开编辑对话框
  - "删除"：确认后删除角色

**AdminPermissionView.vue**：
- 从路由查询参数获取 `roleId` 和 `roleName`
- 加载所有权限列表（按模块分组）
- 加载当前角色已有权限
- 权限开关（Toggle Switch）：
  - 点击切换权限启用/禁用状态
  - 前端维护 `assignedPermissionIds` Set 集合
- "保存设置"按钮：调用 `roleApi.assignPermissions(roleId, permissionIds)` 批量更新
- "返回"按钮：返回角色管理页面

#### UI 一致性

所有管理界面遵循统一的对话框模式：
- `dialogVisible` 控制对话框显示
- `dialogMode` 区分 'add' 和 'edit' 模式
- `dialogForm` 响应式表单数据
- `saveDialog()` 方法根据模式调用不同 API

## 📊 预置数据

### 默认角色

| 角色名称   | 角色编码  | 描述                         |
|-----------|----------|------------------------------|
| 系统管理员 | admin    | 拥有系统所有权限              |
| 操作员     | operator | 可管理日常业务，无法修改系统配置 |
| 普通用户   | user     | 仅可查看和管理个人相关信息     |

### 权限模块

1. **用户管理**：user:view, user:create, user:update, user:delete
2. **老人管理**：elderly:view, elderly:create, elderly:update, elderly:delete
3. **活动管理**：activity:view, activity:create, activity:update, activity:delete, activity:enrollment
4. **健康管理**：health:view, health:create, health:update, health:delete
5. **紧急呼叫**：emergency:view, emergency:handle
6. **服务订单**：service:view, service:create, service:update, service:delete
7. **角色管理**：role:view, role:create, role:update, role:delete
8. **权限管理**：permission:view, permission:assign

### 默认权限分配

- **系统管理员**：拥有所有权限
- **操作员**：拥有日常业务管理权限（用户、老人、活动、健康、紧急、服务订单），但无角色和权限管理权限
- **普通用户**：仅拥有非敏感模块的查看权限（:view）

## 🚀 部署步骤

### 1. 数据库迁移

执行以下 SQL 脚本（按顺序）：

```bash
# 1. 创建表结构
mysql -u root -p ohelp < database_schema.sql

# 2. 插入初始数据
mysql -u root -p ohelp < database_init_data.sql
```

验证表已创建：
```sql
USE ohelp;
SHOW TABLES LIKE '%role%';
-- 应该看到：role, role_permission
SHOW TABLES LIKE '%permission%';
-- 应该看到：permission, role_permission

SELECT COUNT(*) FROM role;           -- 应该返回 3
SELECT COUNT(*) FROM permission;     -- 应该返回 30+
SELECT COUNT(*) FROM role_permission; -- 应该返回多条分配记录
```

### 2. 后端启动

```bash
cd o-b
./mvnw.cmd spring-boot:run
```

验证接口可用：
```bash
# 测试角色列表
curl http://localhost:8080/api/role

# 测试权限列表
curl http://localhost:8080/api/permission
```

### 3. 前端启动

```bash
cd o-f
npm install
npm run dev
```

访问 `http://localhost:5173`，使用管理员账户登录后：
1. 导航到"角色管理"
2. 点击"+ 添加角色"创建测试角色
3. 点击"配置权限"为角色分配权限
4. 返回"用户管理"，为用户分配新创建的角色

## 🧪 测试清单

### 角色管理测试

- [ ] 查看角色列表
- [ ] 创建新角色（名称："测试角色"，编码："test_role"）
- [ ] 编辑角色信息
- [ ] 验证编辑时角色编码不可修改
- [ ] 删除角色（确认提示正常弹出）
- [ ] 验证删除后关联的 role_permission 记录也被删除（CASCADE）

### 权限配置测试

- [ ] 从角色管理点击"配置权限"
- [ ] 验证页面标题显示当前角色名称
- [ ] 验证权限按模块正确分组显示
- [ ] 验证角色现有权限的开关状态正确（已有权限应为开启状态）
- [ ] 切换多个权限开关
- [ ] 点击"保存设置"
- [ ] 返回后重新进入，验证权限状态已持久化

### 集成测试

- [ ] 创建新角色并分配部分权限
- [ ] 在"用户管理"中为用户分配该角色
- [ ] 验证数据库 user 表的 role 字段已更新
- [ ] （可选）实现前端权限拦截：登录时获取用户角色和权限，根据权限隐藏/禁用对应按钮

### 数据一致性测试

- [ ] 删除角色后，验证 role_permission 表中的关联记录被级联删除
- [ ] 删除权限后，验证 role_permission 表中的关联记录被级联删除
- [ ] 批量分配权限时，验证旧权限被完全替换（事务原子性）

## 🔐 安全建议

1. **防止权限提升**：
   - 普通用户不应能修改自己的角色
   - 操作员不应能创建管理员角色或分配超出自己的权限

2. **审计日志**：
   - 记录所有角色和权限的变更操作
   - 记录谁在何时为哪个角色分配了哪些权限

3. **前端权限拦截**：
   - 登录后获取用户权限列表并存储在前端
   - 根据权限动态渲染菜单和按钮（如无 `user:delete` 权限则隐藏删除按钮）
   - 使用路由守卫防止直接 URL 访问无权限页面

4. **后端权限验证**：
   - 所有 API 接口应验证用户权限（目前未实现）
   - 建议使用 Spring Security + JWT 替换现有的 UUID token
   - 实现注解式权限验证，如 `@PreAuthorize("hasPermission('user:delete')")`

## 📁 文件清单

### 后端文件（已创建）

```
o-b/src/main/java/com/soft/ob/role/
├── entity/
│   ├── Role.java
│   ├── Permission.java
│   └── RolePermission.java
├── mapper/
│   ├── RoleMapper.java
│   ├── PermissionMapper.java
│   └── RolePermissionMapper.java
├── service/
│   ├── RoleService.java
│   ├── PermissionService.java
│   └── RolePermissionService.java
└── controller/
    ├── RoleController.java
    └── PermissionController.java
```

### 前端文件（已更新）

```
o-f/src/
├── api/
│   ├── role.js           (新建)
│   └── permission.js     (新建)
└── views/
    ├── AdminRoleManageView.vue      (已更新)
    └── AdminPermissionView.vue      (已更新)
```

### 数据库脚本（已更新）

```
o-b/
├── database_schema.sql      (新增 role, permission, role_permission 表)
└── database_init_data.sql   (新增角色、权限、关联数据)
```

## 🎯 未来扩展

1. **用户-角色多对多关系**：
   - 当前 user 表的 role 字段为 VARCHAR，只能存储一个角色
   - 建议创建 user_role 关联表支持一个用户拥有多个角色

2. **权限继承**：
   - 实现角色继承机制，子角色自动继承父角色权限

3. **数据权限（Data Permission）**：
   - 不仅控制功能权限（能否访问某个功能），还控制数据范围
   - 例如：操作员 A 只能查看区域 A 的老人信息，操作员 B 只能查看区域 B

4. **权限缓存**：
   - 使用 Redis 缓存用户权限列表，减少数据库查询
   - 角色权限变更时清除相关缓存

5. **权限审计**：
   - 创建 role_permission_audit 表记录权限变更历史
   - 支持"谁在何时为哪个角色分配了哪些权限"的审计查询

## ✅ 实施状态

- ✅ 数据库表结构设计完成
- ✅ 后端实体、Mapper、Service、Controller 完成
- ✅ 前端 API 客户端完成
- ✅ 角色管理界面完成（CRUD + 权限配置导航）
- ✅ 权限配置界面完成（Toggle 开关 + 批量保存）
- ✅ 数据库初始化脚本完成（3个角色 + 30+ 权限 + 默认分配）
- ✅ 前端构建测试通过（838ms，无错误）
- ⏳ 数据库迁移执行（待部署时执行）
- ⏳ 集成测试（待后端启动后执行）
- ⏳ 前端权限拦截实现（未来扩展）
- ⏳ 后端权限验证实现（未来扩展）

---

**最后更新**：2025-01-XX
**实施者**：GitHub Copilot
**状态**：✅ 开发完成，待部署测试
