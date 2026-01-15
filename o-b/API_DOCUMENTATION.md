# Ohelp2025 后端 API 接口文档

## 项目概述
Ohelp2025 是一个智慧养老服务服务管理系统的后端 API，采用模块化包结构设计，每个业务模块独立运作。

## 技术栈
- **框架**: Spring Boot 3.5.9
- **数据库**: MySQL
- **ORM**: MyBatis + Spring Data JPA
- **构建工具**: Maven
- **JDK版本**: Java 17

## 模块化结构

### 1. Auth 认证模块 (com.soft.ob.auth)
**基础路由**: `/api/auth`

#### 实体类: Auth
- id: 认证记录ID
- username: 用户名
- password: 密码
- token: 认证令牌
- userId: 关联用户ID
- loginTime: 登录时间
- logoutTime: 登出时间
- isActive: 是否激活

#### API 接口:
| 方法 | 路由 | 描述 |
|------|------|------|
| POST | `/auth/login` | 用户登录 |
| POST | `/auth/logout` | 用户登出 |
| POST | `/auth/register` | 用户注册 |
| GET | `/auth/validate/{token}` | 验证令牌有效性 |
| GET | `/auth/{id}` | 获取认证记录 |
| GET | `/auth/` | 获取所有认证记录 |
| DELETE | `/auth/{id}` | 删除认证记录 |

---

### 2. User 用户管理模块 (com.soft.ob.user)
**基础路由**: `/api/user`

#### 实体类: User
- id: 用户ID
- name: 姓名
- email: 邮箱（唯一）
- phone: 电话（唯一）
- role: 角色
- avatarUrl: 头像URL
- isActive: 是否激活

#### API 接口:
| 方法 | 路由 | 描述 |
|------|------|------|
| POST | `/user/` | 创建用户 |
| GET | `/user/{id}` | 获取用户详情 |
| GET | `/user/email/{email}` | 按邮箱查询用户 |
| GET | `/user/phone/{phone}` | 按电话查询用户 |
| GET | `/user/` | 获取所有用户 |
| PUT | `/user/{id}` | 更新用户信息 |
| DELETE | `/user/{id}` | 删除用户 |
| PUT | `/user/{id}/activate` | 激活用户 |
| PUT | `/user/{id}/deactivate` | 停用用户 |

---

### 3. Elder 老人信息模块 (com.soft.ob.elder)
**基础路由**: `/api/elder`

#### 3.1 Elderly 老人信息
**路由前缀**: `/elder/elderly`

##### 实体类: Elderly
- id: 老人ID
- name: 姓名
- age: 年龄
- dateOfBirth: 出生日期
- gender: 性别
- phoneNumber: 电话
- healthStatus: 健康状态
- medicalHistory: 医疗历史
- address: 地址
- contactPerson: 联系人
- contactPhone: 联系电话
- isActive: 是否激活

##### API 接口:
| 方法 | 路由 | 描述 |
|------|------|------|
| POST | `/elder/elderly/` | 创建老人信息 |
| GET | `/elder/elderly/{id}` | 获取老人详情 |
| GET | `/elder/elderly/` | 获取所有老人 |
| GET | `/elder/elderly/search/{name}` | 按名字搜索老人 |
| PUT | `/elder/elderly/{id}` | 更新老人信息 |
| DELETE | `/elder/elderly/{id}` | 删除老人信息 |
| PUT | `/elder/elderly/{id}/activate` | 激活老人 |
| PUT | `/elder/elderly/{id}/deactivate` | 停用老人 |

#### 3.2 Relative 亲属信息
**路由前缀**: `/elder/relative`

##### 实体类: Relative
- id: 亲属ID
- elderlyId: 关联老人ID
- name: 姓名
- phone: 电话
- relationship: 关系
- email: 邮箱
- isPrimaryContact: 是否主要联系人
- isActive: 是否激活

##### API 接口:
| 方法 | 路由 | 描述 |
|------|------|------|
| POST | `/elder/relative/` | 创建亲属信息 |
| GET | `/elder/relative/{id}` | 获取亲属详情 |
| GET | `/elder/relative/elderly/{elderlyId}` | 获取老人的亲属列表 |
| GET | `/elder/relative/` | 获取所有亲属 |
| PUT | `/elder/relative/{id}` | 更新亲属信息 |
| DELETE | `/elder/relative/{id}` | 删除亲属信息 |
| PUT | `/elder/relative/{id}/activate` | 激活亲属 |
| PUT | `/elder/relative/{id}/deactivate` | 停用亲属 |

---

### 4. Activity 活动管理模块 (com.soft.ob.activity)
**基础路由**: `/api/activity`

#### 实体类: Activity
- id: 活动ID
- name: 活动名称
- category: 活动分类
- description: 活动描述
- location: 活动地点
- startTime: 开始时间
- endTime: 结束时间
- participants: 参与人数
- organizerId: 组织者ID
- status: 活动状态
- isActive: 是否激活

#### API 接口:
| 方法 | 路由 | 描述 |
|------|------|------|
| POST | `/activity/` | 创建活动 |
| GET | `/activity/{id}` | 获取活动详情 |
| GET | `/activity/` | 获取所有活动 |
| GET | `/activity/category/{category}` | 按分类查询活动 |
| GET | `/activity/status/{status}` | 按状态查询活动 |
| PUT | `/activity/{id}` | 更新活动信息 |
| DELETE | `/activity/{id}` | 删除活动 |
| PUT | `/activity/{id}/activate` | 激活活动 |
| PUT | `/activity/{id}/deactivate` | 停用活动 |

---

### 5. Emergency 紧急求助模块 (com.soft.ob.emergency)
**基础路由**: `/api/emergency`

#### 实体类: Emergency
- id: 求助ID
- elderlyId: 老人ID
- type: 求助类型
- description: 求助描述
- location: 求助位置
- contactPhone: 联系电话
- status: 处理状态（pending/responded/resolved）
- responderId: 响应人ID
- responseTime: 响应时间
- resolvedTime: 解决时间
- priority: 优先级（high/medium/low）
- isActive: 是否激活

#### API 接口:
| 方法 | 路由 | 描述 |
|------|------|------|
| POST | `/emergency/` | 创建求助请求 |
| GET | `/emergency/{id}` | 获取求助详情 |
| GET | `/emergency/` | 获取所有求助 |
| GET | `/emergency/elderly/{elderlyId}` | 获取老人的求助记录 |
| GET | `/emergency/status/{status}` | 按状态查询求助 |
| GET | `/emergency/priority/{priority}` | 按优先级查询求助 |
| PUT | `/emergency/{id}` | 更新求助信息 |
| DELETE | `/emergency/{id}` | 删除求助记录 |
| PUT | `/emergency/{id}/respond` | 响应求助请求 |
| PUT | `/emergency/{id}/resolve` | 解决求助请求 |

---

### 6. Health 健康管理模块 (com.soft.ob.health)
**基础路由**: `/api/health`

#### 实体类: HealthRecord
- id: 记录ID
- elderlyId: 老人ID
- recordDate: 记录日期
- bloodPressure: 血压
- heartRate: 心率
- temperature: 体温
- weight: 体重
- glucoseLevel: 血糖水平
- notes: 备注
- doctorId: 医生ID
- isActive: 是否激活

#### API 接口:
| 方法 | 路由 | 描述 |
|------|------|------|
| POST | `/health/` | 创建健康记录 |
| GET | `/health/{id}` | 获取健康记录 |
| GET | `/health/` | 获取所有健康记录 |
| GET | `/health/elderly/{elderlyId}` | 获取老人的健康记录 |
| GET | `/health/date/{recordDate}` | 按日期查询记录 |
| GET | `/health/doctor/{doctorId}` | 获取医生的记录 |
| PUT | `/health/{id}` | 更新健康记录 |
| DELETE | `/health/{id}` | 删除健康记录 |
| PUT | `/health/{id}/activate` | 激活记录 |
| PUT | `/health/{id}/deactivate` | 停用记录 |

---

### 7. ServiceOrder 服务订单模块 (com.soft.ob.serviceorder)
**基础路由**: `/api/serviceorder`

#### 实体类: ServiceOrder
- id: 订单ID
- elderlyId: 老人ID
- serviceType: 服务类型
- serviceProviderId: 服务提供者ID
- startDate: 开始日期
- endDate: 结束日期
- frequency: 服务频率
- price: 价格
- status: 订单状态
- description: 描述
- isActive: 是否激活

#### API 接口:
| 方法 | 路由 | 描述 |
|------|------|------|
| POST | `/serviceorder/` | 创建服务订单 |
| GET | `/serviceorder/{id}` | 获取订单详情 |
| GET | `/serviceorder/` | 获取所有订单 |
| GET | `/serviceorder/elderly/{elderlyId}` | 获取老人的订单 |
| GET | `/serviceorder/service-type/{serviceType}` | 按服务类型查询 |
| GET | `/serviceorder/provider/{serviceProviderId}` | 获取提供者的订单 |
| GET | `/serviceorder/status/{status}` | 按状态查询订单 |
| PUT | `/serviceorder/{id}` | 更新订单信息 |
| DELETE | `/serviceorder/{id}` | 删除订单 |
| PUT | `/serviceorder/{id}/cancel` | 取消订单 |
| PUT | `/serviceorder/{id}/complete` | 完成订单 |

---

### 8. Worker 工作人员模块 (com.soft.ob.worker)
**基础路由**: `/api/worker`

#### 实体类: Worker
- id: 员工ID
- name: 姓名
- email: 邮箱（唯一）
- phone: 电话（唯一）
- position: 职位
- department: 部门
- specialization: 专长
- hireDate: 入职日期
- salary: 薪资
- isAvailable: 是否可用
- isActive: 是否激活

#### API 接口:
| 方法 | 路由 | 描述 |
|------|------|------|
| POST | `/worker/` | 创建员工 |
| GET | `/worker/{id}` | 获取员工详情 |
| GET | `/worker/email/{email}` | 按邮箱查询员工 |
| GET | `/worker/phone/{phone}` | 按电话查询员工 |
| GET | `/worker/` | 获取所有员工 |
| GET | `/worker/department/{department}` | 按部门查询员工 |
| GET | `/worker/position/{position}` | 按职位查询员工 |
| GET | `/worker/available` | 获取可用的员工 |
| PUT | `/worker/{id}` | 更新员工信息 |
| DELETE | `/worker/{id}` | 删除员工 |
| PUT | `/worker/{id}/activate` | 激活员工 |
| PUT | `/worker/{id}/deactivate` | 停用员工 |
| PUT | `/worker/{id}/availability` | 设置员工可用性 |

---

### 9. File 文件管理模块 (com.soft.ob.file)
**基础路由**: `/api/file`

#### 实体类: FileRecord
- id: 文件记录ID
- filename: 文件名
- originalFilename: 原始文件名
- fileType: 文件类型
- fileSize: 文件大小
- filePath: 文件路径
- url: 访问URL
- uploaderId: 上传者ID
- entityType: 关联实体类型
- entityId: 关联实体ID
- description: 描述
- isActive: 是否激活

#### API 接口:
| 方法 | 路由 | 描述 |
|------|------|------|
| POST | `/file/upload` | 上传文件 |
| GET | `/file/{id}` | 获取文件记录 |
| GET | `/file/` | 获取所有文件记录 |
| GET | `/file/uploader/{uploaderId}` | 获取上传者的文件 |
| GET | `/file/entity/{entityType}/{entityId}` | 获取实体关联的文件 |
| GET | `/file/type/{fileType}` | 按文件类型查询 |
| PUT | `/file/{id}` | 更新文件记录 |
| DELETE | `/file/{id}` | 删除文件记录 |
| PUT | `/file/{id}/activate` | 激活文件记录 |
| PUT | `/file/{id}/deactivate` | 停用文件记录 |

---

## 通用响应格式

所有接口返回统一的JSON格式:

```json
{
  "code": 200,
  "message": "Success message",
  "data": {}
}
```

### 状态码说明
- `200`: 成功
- `201`: 创建成功
- `400`: 请求错误
- `401`: 未授权
- `404`: 资源不存在
- `500`: 服务器错误

---

## 数据库配置

在 `application.properties` 中配置:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ohelp?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

---

## 项目启动

```bash
# 构建项目
mvn clean package

# 运行项目
mvn spring-boot:run
```

项目运行在 `http://localhost:8080/api`

---

## 模块独立性说明

所有模块采用独立的包结构设计，每个模块包含：
- **entity**: 数据模型
- **mapper**: 数据访问层（MyBatis）
- **service**: 业务逻辑层
- **controller**: 控制层

模块之间无依赖关系，可独立维护和扩展。
