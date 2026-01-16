# 智慧养老服务平台

这是一个完整的智慧养老服务平台，包含前端界面和后端服务。

## 项目结构

- `o-b`：后端服务（Spring Boot）
- `o-f`：前端界面（Vue 3 + Vite）

## 技术栈

### 后端
- Spring Boot 3.5.9（Java 17，Maven）
- Spring Web（REST API）
- MyBatis（注解 Mapper 为主）
- MySQL（默认数据库，库名 `ohelp`）
- Spring Data JPA（项目依赖中包含，个别模块可能使用/保留）

### 前端
- Vue 3（JS）
- Vue Router 4（路由管理）
- Vite（构建工具）
- Axios（HTTP 请求）
- Canvas 2D（趋势图绘制）

## 功能特性

### 用户端（老人/家属）
- 登录页面作为唯一入口，支持选择“用户端/管理端”角色
- 表单验证 + 基于角色的登录后重定向
- 健康概览、健康详情（含趋势图）、健康预警列表与详情
- 一键紧急呼救与历史记录
- 养老活动列表与详情
- 个人中心（资料、健康档案、我的活动、紧急联系人、设置、退出登录）

### 管理端（后台）
- 用户管理：用户列表、角色/状态筛选，支持启用/禁用/删除用户（已接入后端接口）
- 老人档案管理：老人列表、健康状态标识（读接口已接入）
- 家属绑定管理：老人-家属关联视图（读接口已接入）
- 健康记录管理：全量健康记录查看（读接口已接入）
- 服务项目管理：服务项目列表（已完整对接后端CRUD）
- 服务预约管理：服务订单列表，支持取消/完成操作（已完整对接后端CRUD）
- 活动管理：活动列表查看，支持新增/编辑/删除（已完整对接后端CRUD）
- **报名管理**：活动报名列表，支持新增/编辑/删除、确认/签到/取消等状态操作（已完整对接后端CRUD）

> 前后端每个模块的"已对接/部分对接/仅演示"的详细梳理，见 `o-f/前后端交互对接说明.md`。

## 数据库初始化

### 快速开始

**如果是第一次设置：**

```powershell
# Windows PowerShell
mysql -u root -p ohelp < "o-b\database_schema.sql"
mysql -u root -p ohelp < "o-b\database_init_data.sql"
```

**Linux/Mac：**
```bash
mysql -u root -p ohelp < o-b/database_schema.sql
mysql -u root -p ohelp < o-b/database_init_data.sql
```

### 快速恢复报名数据

如果报名管理界面为空，可直接执行报名数据恢复脚本：

```powershell
mysql -u root -p ohelp < "o-b\QUICK_ENROLLMENT_DATA.sql"
```

详见 [数据初始化快速指南](ENROLLMENT_DATA_QUICK_FIX.md)。

## 启动说明

### 后端服务
1. 确保已安装Java 17
2. 在`o-b`目录下执行：
```bash
./mvnw.cmd -U -DskipTests spring-boot:run
```
3. 服务将在 http://localhost:8080 启动

后端接口基路径为 `/api`（见 `o-b/src/main/resources/application.properties`），例如：
- 登录：`POST http://localhost:8080/api/auth/login`
- 用户：`GET  http://localhost:8080/api/user/`

### 前端服务
1. 确保已安装Node.js和npm
2. 在`o-f`目录下执行：
```bash
npm install
npm run dev
```
3. 服务将在 http://localhost:5173 启动

> 说明：`o-f` 目录下原有的 `index.html` 与 `scripts/` 纯静态实现仅作为设计与对照参考，实际运行入口已完全迁移到 Vue 3 + Vite（`src/main.js` + `src/router` + `src/views`）。UI 展示与静态版保持一致，用户端与管理端后台页面均已迁移为单页应用路由。

## 数据库配置

项目默认使用 MySQL（见 `o-b/src/main/resources/application.properties`）。

本地初始化（Windows PowerShell 示例）：
```powershell
mysql -u root -p
CREATE DATABASE IF NOT EXISTS ohelp DEFAULT CHARSET=utf8mb4;
EXIT
mysql -u root -p ohelp < "o-b\database_schema.sql"
mysql -u root -p ohelp < "o-b\database_init_data.sql"
```

## 初始化数据

数据库已预置了各种角色的用户，管理员账号密码均为admin。

### 包含的示例数据
- ✓ 4个用户账户
- ✓ 4个老人信息
- ✓ 3个活动
- ✓ 12个活动报名记录（覆盖5种状态）
- ✓ 健康记录、紧急求助、服务订单等

**首次初始化需要执行两个脚本：**
1. `database_schema.sql` - 创建表结构
2. `database_init_data.sql` - 初始化数据

## 前后端联调约定（重要）

- 前端通过 Vite proxy 代理 `/api` 到后端（见 `o-f/vite.config.js`），默认 API baseURL 为 `/api`（见 `o-f/.env.example` 与 `o-f/src/api/http.js`）。
- 后端所有接口返回统一包裹：`{ code, message, data? }`；前端在 `o-f/src/api/http.js` 里会对 `code !== 200 && code !== 201` 直接报错。
- 登录返回自定义 token（UUID），前端会保存到 `localStorage['token']` 并放到 `Authorization` header；后端目前没有全局鉴权拦截（仅提供 `GET /auth/validate/{token}`）。

## 文档与入口

- 后端 API 文档：`o-b/COMPLETE_API_DOCUMENTATION.md`、`o-b/INTERFACE_SUMMARY.md`
- 后端入口类：`o-b/src/main/java/com/soft/ob/OBApplication.java`
- 前端路由入口：`o-f/src/router/index.js`（包含用户端与 `/admin-*` 管理端路由）
- 前后端交互对接总览：`o-f/前后端交互对接说明.md`

