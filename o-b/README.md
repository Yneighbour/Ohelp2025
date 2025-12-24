# 智慧养老服务管理系统 - 后端

这是一个基于Spring Boot的智慧养老服务管理系统后端，用于管理老年人信息、服务请求和员工信息。

## 项目结构

- `entity/` - JPA实体类
- `repository/` - 数据访问层接口
- `service/` - 业务逻辑层
- `controller/` - REST API控制器
- `exception/` - 全局异常处理

## 主要功能

1. **老年人管理** - 管理老年人基本信息、健康状况、住宿信息等
2. **服务请求管理** - 记录和跟踪老年人的服务请求
3. **员工管理** - 管理员工信息和权限

## API端点

### 老年人管理
- `GET /api/elderly` - 获取所有老年人信息
- `GET /api/elderly/active` - 获取活跃老年人信息
- `GET /api/elderly/{id}` - 根据ID获取特定老年人信息
- `POST /api/elderly` - 创建新的老年人信息
- `PUT /api/elderly/{id}` - 更新老年人信息
- `DELETE /api/elderly/{id}` - 删除老年人信息（软删除）
- `GET /api/elderly/search?name={name}` - 根据姓名搜索老年人

### 服务请求管理
- `GET /api/service-requests` - 获取所有服务请求
- `GET /api/service-requests/{id}` - 根据ID获取特定服务请求
- `POST /api/service-requests` - 创建新的服务请求
- `PUT /api/service-requests/{id}` - 更新服务请求
- `DELETE /api/service-requests/{id}` - 删除服务请求
- `GET /api/service-requests/elderly/{elderlyId}` - 获取特定老年人的所有服务请求
- `GET /api/service-requests/status/{status}` - 根据状态获取服务请求
- `GET /api/service-requests/priority/{priority}` - 根据优先级获取服务请求

### 系统健康检查
- `GET /api/health` - 检查系统健康状态

## 数据库配置

默认使用MySQL数据库，配置在`application.properties`文件中：

```
spring.datasource.url=jdbc:mysql://localhost:3306/elderly_care_system?useSSL=false&serverTimezone=UTC&characterEncoding=utf8
spring.datasource.username=root
spring.datasource.password=password
```

## 启动项目

使用Maven启动项目：

```bash
cd o-b
mvnw spring-boot:run
```

或者在IDE中运行`OBApplication`类的main方法。

项目将在端口8080上启动。