# Ohelp2025 后端 API 完整文档

## 📋 目录
1. [项目概述](#项目概述)
2. [技术栈](#技术栈)
3. [API 通用说明](#api-通用说明)
4. [模块接口详情](#模块接口详情)
5. [数据库配置](#数据库配置)
6. [项目启动](#项目启动)
7. [联系方式](#联系方式)

---

## 项目概述

**Ohelp2025** 是一个老年人服务管理系统，采用前后端分离架构，后端基于 Spring Boot 框架，提供 RESTful API 接口。系统包含 9 个主要业务模块，涵盖认证、用户管理、老人信息、活动管理、紧急求助、健康管理、服务订单、员工管理和文件管理等功能。

---

## 技术栈

| 组件 | 版本 |
|------|------|
| Spring Boot | 3.5.9 |
| Java | 17 |
| MySQL | 5.7+ |
| MyBatis | 3.5+ |
| Spring Data JPA | 3.1+ |
| Maven | 3.6+ |

---

## API 通用说明

### 请求基础 URL
```
http://localhost:8080/api
```

### 统一响应格式

所有 API 接口返回统一的 JSON 格式：

```json
{
  "code": 200,
  "message": "Success",
  "data": {}
}
```

### HTTP 状态码

| 状态码 | 含义 |
|--------|------|
| 200 | 成功 |
| 201 | 资源创建成功 |
| 400 | 请求参数错误 |
| 401 | 未授权 |
| 403 | 禁止访问 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |

### 请求头

所有请求应包含以下 header：
```
Content-Type: application/json
Authorization: Bearer {token}  // 除了登录接口外
```

---

## 模块接口详情

### 1️⃣ Auth 认证模块 (`/api/auth`)

#### 用户登录
```
POST /api/auth/login
Content-Type: application/json

请求:
{
  "username": "admin",
  "password": "admin123"
}

响应 (200):
{
  "code": 200,
  "message": "登录成功",
  "data": {
    "id": 1,
    "username": "admin",
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "userId": 1
  }
}
```

#### 用户注册
```
POST /api/auth/register
Content-Type: application/json

请求:
{
  "username": "newuser",
  "password": "password123"
}

响应 (201):
{
  "code": 201,
  "message": "注册成功",
  "data": {
    "id": 5,
    "username": "newuser"
  }
}
```

#### 用户登出
```
POST /api/auth/logout

响应 (200):
{
  "code": 200,
  "message": "登出成功"
}
```

#### 验证令牌
```
GET /api/auth/validate/{token}

响应 (200):
{
  "code": 200,
  "message": "令牌有效",
  "data": {
    "valid": true,
    "userId": 1
  }
}
```

#### 获取认证记录
```
GET /api/auth/{id}

响应 (200):
{
  "code": 200,
  "message": "Success",
  "data": {
    "id": 1,
    "username": "admin",
    "token": "...",
    "userId": 1,
    "loginTime": "2026-01-08 10:00:00",
    "logoutTime": null
  }
}
```

#### 获取所有认证记录
```
GET /api/auth/

响应 (200):
{
  "code": 200,
  "message": "Success",
  "data": [
    { ... },
    { ... }
  ]
}
```

#### 删除认证记录
```
DELETE /api/auth/{id}

响应 (200):
{
  "code": 200,
  "message": "删除成功"
}
```

---

### 2️⃣ User 用户管理模块 (`/api/user`)

#### 创建用户
```
POST /api/user/
Content-Type: application/json

请求:
{
  "name": "李四",
  "email": "lisi@example.com",
  "phone": "13800138002",
  "role": "manager"
}

响应 (201):
{
  "code": 201,
  "message": "用户创建成功",
  "data": {
    "id": 5,
    "name": "李四",
    "email": "lisi@example.com",
    "phone": "13800138002",
    "role": "manager"
  }
}
```

#### 获取用户详情
```
GET /api/user/{id}

响应 (200):
{
  "code": 200,
  "message": "Success",
  "data": {
    "id": 1,
    "name": "张三",
    "email": "zhangsan@example.com",
    "phone": "13800138001",
    "role": "admin",
    "avatarUrl": "http://..."
  }
}
```

#### 按邮箱查询用户
```
GET /api/user/email/{email}

示例: GET /api/user/email/zhangsan@example.com
```

#### 按电话查询用户
```
GET /api/user/phone/{phone}

示例: GET /api/user/phone/13800138001
```

#### 获取所有用户
```
GET /api/user/

响应 (200):
{
  "code": 200,
  "message": "Success",
  "data": [
    { ... },
    { ... }
  ]
}
```

#### 更新用户信息
```
PUT /api/user/{id}
Content-Type: application/json

请求:
{
  "name": "张三",
  "email": "zhangsan@example.com",
  "phone": "13800138001"
}

响应 (200):
{
  "code": 200,
  "message": "用户更新成功"
}
```

#### 激活/停用用户
```
PUT /api/user/{id}/activate   # 激活
PUT /api/user/{id}/deactivate # 停用

响应 (200):
{
  "code": 200,
  "message": "操作成功"
}
```

#### 删除用户
```
DELETE /api/user/{id}

响应 (200):
{
  "code": 200,
  "message": "用户删除成功"
}
```

---

### 3️⃣ Elder 老人信息模块 (`/api/elder`)

#### 老人信息管理 (`/api/elder/elderly/*`)

##### 创建老人
```
POST /api/elder/elderly/
Content-Type: application/json

请求:
{
  "name": "李老人",
  "age": 75,
  "dateOfBirth": "1949-05-15",
  "gender": "男",
  "phoneNumber": "13900139001",
  "healthStatus": "身体健康",
  "address": "北京市朝阳区",
  "contactPerson": "李小明",
  "contactPhone": "13800138001"
}

响应 (201):
{
  "code": 201,
  "message": "老人信息创建成功",
  "data": {
    "id": 1,
    "name": "李老人",
    ...
  }
}
```

##### 获取老人详情
```
GET /api/elder/elderly/{id}

示例: GET /api/elder/elderly/1
```

##### 获取所有老人
```
GET /api/elder/elderly/
```

##### 按名字搜索老人
```
GET /api/elder/elderly/search/{name}

示例: GET /api/elder/elderly/search/李老人
```

##### 更新老人信息
```
PUT /api/elder/elderly/{id}
```

##### 激活/停用老人
```
PUT /api/elder/elderly/{id}/activate
PUT /api/elder/elderly/{id}/deactivate
```

##### 删除老人
```
DELETE /api/elder/elderly/{id}
```

#### 亲属信息管理 (`/api/elder/relative/*`)

##### 创建亲属
```
POST /api/elder/relative/
Content-Type: application/json

请求:
{
  "elderlyId": 1,
  "name": "李小明",
  "phone": "13800138001",
  "relationship": "儿子",
  "email": "lixiaoming@example.com",
  "isPrimaryContact": true
}
```

##### 获取亲属列表
```
GET /api/elder/relative/           # 所有亲属
GET /api/elder/relative/{id}       # 按ID查询
GET /api/elder/relative/elderly/{elderlyId}  # 按老人ID查询
```

##### 更新亲属信息
```
PUT /api/elder/relative/{id}
```

##### 激活/停用亲属
```
PUT /api/elder/relative/{id}/activate
PUT /api/elder/relative/{id}/deactivate
```

##### 删除亲属
```
DELETE /api/elder/relative/{id}
```

---

### 4️⃣ Activity 活动管理模块 (`/api/activity`)

#### 创建活动
```
POST /api/activity/
Content-Type: application/json

请求:
{
  "name": "太极拳课程",
  "category": "健身活动",
  "description": "每周三、五上午进行",
  "location": "活动室A",
  "startTime": "2026-01-15 09:00:00",
  "endTime": "2026-01-15 10:30:00",
  "organizerId": 1,
  "status": "进行中"
}
```

#### 获取活动列表
```
GET /api/activity/                # 所有活动
GET /api/activity/{id}            # 按ID查询
GET /api/activity/category/{category}  # 按分类查询
GET /api/activity/status/{status}      # 按状态查询
```

#### 更新活动
```
PUT /api/activity/{id}
```

#### 激活/停用活动
```
PUT /api/activity/{id}/activate
PUT /api/activity/{id}/deactivate
```

#### 删除活动
```
DELETE /api/activity/{id}
```

---

### 5️⃣ Emergency 紧急求助模块 (`/api/emergency`)

#### 创建求助请求
```
POST /api/emergency/
Content-Type: application/json

请求:
{
  "elderlyId": 1,
  "type": "摔跤",
  "description": "在家摔倒",
  "location": "北京市朝阳区",
  "contactPhone": "13900139001",
  "priority": "high"
}
```

#### 获取求助列表
```
GET /api/emergency/                    # 所有求助
GET /api/emergency/{id}                # 按ID查询
GET /api/emergency/elderly/{elderlyId} # 按老人ID查询
GET /api/emergency/status/{status}     # 按状态查询
GET /api/emergency/priority/{priority} # 按优先级查询
```

#### 响应求助
```
PUT /api/emergency/{id}/respond
Content-Type: application/json

请求:
{
  "responderId": 2,
  "responseTime": "2026-01-08 10:30:00"
}
```

#### 解决求助
```
PUT /api/emergency/{id}/resolve
Content-Type: application/json

请求:
{
  "resolvedTime": "2026-01-08 11:00:00",
  "notes": "已妥善处理"
}
```

---

### 6️⃣ Health 健康管理模块 (`/api/health`)

#### 创建健康记录
```
POST /api/health/
Content-Type: application/json

请求:
{
  "elderlyId": 1,
  "recordDate": "2026-01-08",
  "bloodPressure": "120/80",
  "heartRate": 72,
  "temperature": 36.5,
  "weight": 65.5,
  "glucoseLevel": 100,
  "notes": "身体状况良好",
  "doctorId": 2
}
```

#### 获取健康记录
```
GET /api/health/                       # 所有记录
GET /api/health/{id}                   # 按ID查询
GET /api/health/elderly/{elderlyId}    # 按老人ID查询
GET /api/health/date/{recordDate}      # 按日期查询
GET /api/health/doctor/{doctorId}      # 按医生ID查询
```

#### 更新健康记录
```
PUT /api/health/{id}
```

#### 激活/停用记录
```
PUT /api/health/{id}/activate
PUT /api/health/{id}/deactivate
```

---

### 7️⃣ ServiceOrder 服务订单模块 (`/api/serviceorder`)

#### 创建服务订单
```
POST /api/serviceorder/
Content-Type: application/json

请求:
{
  "elderlyId": 1,
  "serviceType": "日常护理",
  "serviceProviderId": 3,
  "startDate": "2026-01-01",
  "endDate": "2026-03-01",
  "frequency": "每日",
  "price": 100,
  "status": "进行中",
  "description": "日常生活护理服务"
}
```

#### 获取订单列表
```
GET /api/serviceorder/                            # 所有订单
GET /api/serviceorder/{id}                        # 按ID查询
GET /api/serviceorder/elderly/{elderlyId}         # 按老人ID查询
GET /api/serviceorder/service-type/{serviceType}  # 按服务类型查询
GET /api/serviceorder/provider/{serviceProviderId}# 按提供者查询
GET /api/serviceorder/status/{status}             # 按状态查询
```

#### 取消订单
```
PUT /api/serviceorder/{id}/cancel
```

#### 完成订单
```
PUT /api/serviceorder/{id}/complete
```

---

### 8️⃣ Worker 工作人员模块 (`/api/worker`)

#### 创建员工
```
POST /api/worker/
Content-Type: application/json

请求:
{
  "name": "张护士",
  "email": "zhanghushi@example.com",
  "phone": "13900139010",
  "position": "护士",
  "department": "护理部",
  "specialization": "老年护理、康复护理",
  "hireDate": "2020-06-01",
  "salary": 5000
}
```

#### 获取员工列表
```
GET /api/worker/                      # 所有员工
GET /api/worker/{id}                  # 按ID查询
GET /api/worker/email/{email}         # 按邮箱查询
GET /api/worker/phone/{phone}         # 按电话查询
GET /api/worker/department/{department}  # 按部门查询
GET /api/worker/position/{position}   # 按职位查询
GET /api/worker/available             # 获取可用员工
```

#### 设置员工可用性
```
PUT /api/worker/{id}/availability
Content-Type: application/json

请求:
{
  "isAvailable": true
}
```

#### 激活/停用员工
```
PUT /api/worker/{id}/activate
PUT /api/worker/{id}/deactivate
```

---

### 9️⃣ File 文件管理模块 (`/api/file`)

#### 上传文件
```
POST /api/file/upload
Content-Type: multipart/form-data

参数:
- file: 要上传的文件
- entityType: 关联实体类型
- entityId: 关联实体ID

响应:
{
  "code": 201,
  "message": "文件上传成功",
  "data": {
    "id": 1,
    "filename": "elderly_001_photo.jpg",
    "url": "http://localhost:8080/api/file/download/elderly_001_photo.jpg"
  }
}
```

#### 获取文件列表
```
GET /api/file/                                    # 所有文件
GET /api/file/{id}                                # 按ID查询
GET /api/file/uploader/{uploaderId}               # 按上传者查询
GET /api/file/entity/{entityType}/{entityId}      # 按实体查询
GET /api/file/type/{fileType}                     # 按文件类型查询
```

#### 激活/停用文件
```
PUT /api/file/{id}/activate
PUT /api/file/{id}/deactivate
```

#### 删除文件
```
DELETE /api/file/{id}
```

---

## 数据库配置

在 `application.properties` 中配置数据库：

```properties
# MySQL 数据库配置
spring.datasource.url=jdbc:mysql://localhost:3306/ohelp?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA 配置
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# MyBatis 配置
mybatis.mapper-locations=classpath:mapper/*.xml
mybatis.type-aliases-package=com.soft.ob.*.entity
```

### 数据库初始化

1. 创建数据库：
```bash
mysql -u root -p < database_schema.sql
```

2. 导入初始化数据（可选）：
```bash
mysql -u root -p ohelp < database_init_data.sql
```

---

## 项目启动

### 前置条件
- JDK 17+
- MySQL 5.7+
- Maven 3.6+

### 启动步骤

1. **克隆项目**
```bash
git clone <项目地址>
cd o-b
```

2. **安装依赖**
```bash
mvn clean install
```

3. **配置数据库**
编辑 `src/main/resources/application.properties`，修改数据库连接信息

4. **创建数据库表**
执行 SQL 脚本 `database_schema.sql`

5. **运行项目**
```bash
mvn spring-boot:run
```

或者编译后运行：
```bash
mvn clean package
java -jar target/o-b-0.0.1-SNAPSHOT.jar
```

6. **访问 API**
打开浏览器访问：`http://localhost:8080/api`

---

## 项目结构

```
o-b/
├── src/
│   ├── main/
│   │   ├── java/com/soft/ob/
│   │   │   ├── OBApplication.java          # 主启动类
│   │   │   ├── activity/                   # 活动模块
│   │   │   ├── auth/                       # 认证模块
│   │   │   ├── elder/                      # 老人信息模块
│   │   │   ├── emergency/                  # 紧急求助模块
│   │   │   ├── file/                       # 文件管理模块
│   │   │   ├── health/                     # 健康管理模块
│   │   │   ├── serviceorder/               # 服务订单模块
│   │   │   ├── user/                       # 用户管理模块
│   │   │   └── worker/                     # 员工管理模块
│   │   └── resources/
│   │       └── application.properties      # 应用配置
│   └── test/                               # 测试代码
├── pom.xml                                 # Maven 配置
├── database_schema.sql                     # 数据库建表脚本
├── database_init_data.sql                  # 数据库初始化脚本
└── README.md                               # 项目说明
```

---

## 模块独立性说明

所有模块采用独立的包结构设计，每个模块包含：
- **entity**: 数据模型（ORM 映射）
- **mapper**: 数据访问层（MyBatis 接口）
- **service**: 业务逻辑层
- **controller**: 控制层（RESTful API）

**模块之间无依赖关系**，可独立维护和扩展。

---

## 联系方式

- **项目名称**: Ohelp2025 老年人服务管理系统
- **版本**: 1.0.0
- **最后更新**: 2026年1月8日
- **开发者**: 毕设团队

---

## 附录：常见错误处理

### 401 Unauthorized
- 检查是否包含有效的 Authorization header
- 验证 token 是否过期

### 404 Not Found
- 检查请求路径是否正确
- 确认资源是否存在

### 500 Internal Server Error
- 查看服务器日志获取详细错误信息
- 确保数据库连接正常

---

**文档版本**: v1.0  
**最后更新**: 2026-01-08
