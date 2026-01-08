# Ohelp2025 后端项目结构总结

## 📋 项目概览

这是一个基于 Spring Boot 3.5.9 的模块化老年人服务管理系统后端，采用标准的企业级 MVC 架构。

## 🏗️ 包结构树

```
com.soft.ob/
├── auth/                    # 认证模块
│   ├── entity/
│   │   └── Auth.java
│   ├── mapper/
│   │   └── AuthMapper.java
│   ├── service/
│   │   └── AuthService.java
│   └── controller/
│       └── AuthController.java
│
├── user/                    # 用户管理模块
│   ├── entity/
│   │   └── User.java
│   ├── mapper/
│   │   └── UserMapper.java
│   ├── service/
│   │   └── UserService.java
│   └── controller/
│       └── UserController.java
│
├── elder/                   # 老人信息模块
│   ├── entity/
│   │   ├── Elderly.java
│   │   └── Relative.java
│   ├── mapper/
│   │   ├── ElderlyMapper.java
│   │   └── RelativeMapper.java
│   ├── service/
│   │   ├── ElderlyService.java
│   │   └── RelativeService.java
│   └── controller/
│       ├── ElderlyController.java
│       └── RelativeController.java
│
├── activity/                # 活动管理模块
│   ├── entity/
│   │   └── Activity.java
│   ├── mapper/
│   │   └── ActivityMapper.java
│   ├── service/
│   │   └── ActivityService.java
│   └── controller/
│       └── ActivityController.java
│
├── emergency/               # 紧急求助模块
│   ├── entity/
│   │   └── Emergency.java
│   ├── mapper/
│   │   └── EmergencyMapper.java
│   ├── service/
│   │   └── EmergencyService.java
│   └── controller/
│       └── EmergencyController.java
│
├── health/                  # 健康管理模块
│   ├── entity/
│   │   └── HealthRecord.java
│   ├── mapper/
│   │   └── HealthRecordMapper.java
│   ├── service/
│   │   └── HealthRecordService.java
│   └── controller/
│       └── HealthRecordController.java
│
├── serviceorder/            # 服务订单模块
│   ├── entity/
│   │   └── ServiceOrder.java
│   ├── mapper/
│   │   └── ServiceOrderMapper.java
│   ├── service/
│   │   └── ServiceOrderService.java
│   └── controller/
│       └── ServiceOrderController.java
│
├── worker/                  # 工作人员模块
│   ├── entity/
│   │   └── Worker.java
│   ├── mapper/
│   │   └── WorkerMapper.java
│   ├── service/
│   │   └── WorkerService.java
│   └── controller/
│       └── WorkerController.java
│
└── file/                    # 文件管理模块
    ├── entity/
    │   └── FileRecord.java
    ├── mapper/
    │   └── FileRecordMapper.java
    ├── service/
    │   └── FileRecordService.java
    └── controller/
        └── FileRecordController.java
```

## 📊 模块详细说明

### 1️⃣ Auth 认证模块
**职责**: 用户身份验证和授权
- 支持用户登录、登出、注册
- 令牌验证和管理
- 实体表: `auth`

### 2️⃣ User 用户模块
**职责**: 用户基本信息管理
- 用户创建、修改、删除
- 用户激活/停用
- 实体表: `user`

### 3️⃣ Elder 老人信息模块
**职责**: 老人及其亲属信息管理
- Elderly: 老人基本信息
- Relative: 亲属信息
- 实体表: `elderly`、`relative`

### 4️⃣ Activity 活动模块
**职责**: 老年人活动管理
- 活动创建、查询、更新
- 活动分类和状态管理
- 实体表: `activity`

### 5️⃣ Emergency 紧急求助模块
**职责**: 应急求助处理
- 求助请求创建和跟踪
- 响应和解决流程管理
- 优先级处理
- 实体表: `emergency_request`

### 6️⃣ Health 健康管理模块
**职责**: 老人健康数据管理
- 健康记录创建和维护
- 医疗数据查询
- 实体表: `health_record`

### 7️⃣ ServiceOrder 服务订单模块
**职责**: 服务订单管理
- 订单创建和跟踪
- 订单状态管理（pending/cancelled/completed）
- 服务提供者关联
- 实体表: `service_order`

### 8️⃣ Worker 工作人员模块
**职责**: 员工信息管理
- 员工档案管理
- 部门和职位分类
- 可用性状态管理
- 实体表: `worker`

### 9️⃣ File 文件管理模块
**职责**: 文件上传和管理
- 文件上传（multipart）
- 文件元数据管理
- 实体关联
- 实体表: `file_record`

## 🔧 技术栈详情

| 组件 | 版本 | 说明 |
|------|------|------|
| Spring Boot | 3.5.9 | Web框架 |
| Spring Data JPA | 3.5.9 | ORM框架 |
| MyBatis | 3.0.3 | 数据访问层 |
| MySQL Connector | Latest | 数据库驱动 |
| Lombok | Latest | 代码简化工具 |
| Jakarta Persistence | 3.x | JPA API |

## 🚀 启动步骤

### 1. 环境准备
- 安装 JDK 17+
- 安装 MySQL 8.0+
- 配置 Maven

### 2. 数据库初始化
在 MySQL 中创建 `ohelp` 数据库，系统会自动创建表结构

### 3. 配置文件
编辑 `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ohelp?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=123456
```

### 4. 构建运行
```bash
# 清理并构建
mvn clean package

# 运行应用
mvn spring-boot:run
```

### 5. 验证启动
访问 `http://localhost:8080/api` 查看API

## 📝 设计特点

### ✅ 模块化设计
- 每个模块完全独立，包含 entity → mapper → service → controller
- 模块之间无依赖关系
- 易于维护和扩展

### ✅ 分层架构
- **Entity 层**: 数据模型和数据库映射
- **Mapper 层**: 数据访问层，使用 MyBatis 进行数据库操作
- **Service 层**: 业务逻辑层，处理复杂的业务规则
- **Controller 层**: 控制层，处理HTTP请求和响应

### ✅ 统一规范
- 所有接口返回统一的 JSON 响应格式
- 使用标准 HTTP 状态码
- RESTful API 设计

### ✅ 功能完整性
- CRUD 操作齐全
- 激活/停用逻辑
- 状态管理
- 时间戳记录

## 📚 依赖关系说明

所有模块采用**水平依赖**结构：
```
Auth ─┐
      ├─→ User ──→ ...
User ─┤
      └─→ Others (independent)

Elder ─→ (independent)
Activity ─→ (independent)
Emergency ─→ (independent)
Health ─→ (independent)
ServiceOrder ─→ (independent)
Worker ─→ (independent)
File ─→ (independent)
```

模块之间通过业务逻辑独立，不存在代码级别的相互调用。

## 🔄 请求流程示例

```
HTTP Request
    ↓
Controller (接收请求)
    ↓
Service (业务处理)
    ↓
Mapper (数据库操作)
    ↓
Database (MySQL)
    ↓
Response (JSON格式返回)
```

## ✨ 后续扩展建议

1. **添加认证过滤器** - 在 Controller 层添加权限验证
2. **添加日志管理** - 记录所有重要操作
3. **添加缓存层** - 使用 Redis 缓存频繁查询
4. **添加事务管理** - 复杂业务操作添加事务支持
5. **添加异常处理** - 统一的全局异常处理
6. **添加API文档** - 集成 Swagger/SpringDoc
7. **添加单元测试** - 为 Service 和 Controller 层编写测试
8. **添加国际化** - 支持多语言

---

**项目创建时间**: 2026-01-08  
**版本**: v1.0.0 (Alpha)
