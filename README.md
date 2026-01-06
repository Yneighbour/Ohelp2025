# 智慧养老服务平台

这是一个完整的智慧养老服务平台，包含前端界面和后端服务。

## 项目结构

- `o-b`：后端服务（Spring Boot）
- `o-f`：前端界面（Vue 3 + Vite）

## 技术栈

### 后端
- Spring Boot 4.0.1
- Spring Security（用户认证）
- Spring Data JPA（数据访问）
- H2 Database（开发数据库）
- MySQL（生产数据库）

### 前端
- Vue 3 + Composition API
- Vue Router 4（路由管理）
- Pinia（状态管理）
- Vite（构建工具）
- Axios（HTTP请求）

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
mvn spring-boot:run
```
3. 服务将在 http://localhost:8080 启动

### 前端服务
1. 确保已安装Node.js和npm
2. 在`o-f`目录下执行：
```bash
npm install
npm run dev
```
3. 服务将在 http://localhost:3000 启动

## 数据库配置

项目使用H2内存数据库进行开发，数据在服务重启后会丢失。如需使用MySQL，请修改`o-b/src/main/resources/application.properties`文件。

## 初始化数据

数据库已预置了各种角色的用户，管理员账号密码均为admin。

## API代理

前端已配置API代理，所有以`/api`开头的请求将被转发到后端服务。