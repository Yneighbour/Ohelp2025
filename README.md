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
- Vue 3 + Composition API
- Vue Router 4（路由管理）
- Vite（构建工具）
- Axios（HTTP请求）
- ECharts（图表）

## 功能特性

### 用户认证系统
- 登录页面作为唯一入口
- 表单验证
- 角色基础的重定向
- 用户身份标识显示
- 路由保护

### 服务模块
- 服务订单管理
- 老人信息管理
- 亲属信息管理
- 紧急求助服务
- 每日健康记录
- 既往病史管理
- 积分增加管理
- 招疗服务
- 评价系统

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

## 前后端联调约定（重要）

- 前端未配置 Vite proxy（见 `o-f/vite.config.js`），请求直接发往后端；后端地址在 `o-f/src/shared/http.js` 里硬编码为 `http://localhost:8080/api`。
- 后端所有接口返回统一包裹：`{ code, message, data? }`；前端在 `o-f/src/shared/http.js` 里会对 `code !== 200 && code !== 201` 直接报错。
- 登录返回自定义 token（UUID），前端会保存到 `localStorage['token']` 并放到 `Authorization` header；后端目前没有全局鉴权拦截（仅提供 `GET /auth/validate/{token}`）。

## 文档与入口

- 后端 API 文档：`o-b/COMPLETE_API_DOCUMENTATION.md`、`o-b/INTERFACE_SUMMARY.md`
- 后端入口类：`o-b/src/main/java/com/soft/ob/OBApplication.java`
- 前端路由入口：`o-f/src/router/index.js`
