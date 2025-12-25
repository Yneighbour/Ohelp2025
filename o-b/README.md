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
4. **服务项目管理** - 管理服务项目和类型
5. **文件上传管理** - 处理文件上传和管理
6. **认证与授权** - 用户登录、注册和令牌管理

## API端点

### 老年人管理 - `/api/elderly`
- `GET /api/elderly` - 获取所有老年人信息
- `GET /api/elderly/active` - 获取活跃老年人信息
- `GET /api/elderly/{id}` - 根据ID获取特定老年人信息
- `POST /api/elderly` - 创建新的老年人信息
- `PUT /api/elderly/{id}` - 更新老年人信息
- `DELETE /api/elderly/{id}` - 删除老年人信息（软删除）
- `GET /api/elderly/search?name={name}` - 根据姓名搜索老年人

### 老人管理 - `/api/laoren`
- `GET /api/laoren` - 获取所有老人信息
- `GET /api/laoren/{id}` - 根据ID获取特定老人信息
- `POST /api/laoren/register` - 注册新老人账户
- `POST /api/laoren/login` - 老人登录
- `PUT /api/laoren/{id}` - 更新老人信息
- `DELETE /api/laoren/{id}` - 删除老人信息
- `PUT /api/laoren/{id}/password` - 修改老人密码
- `PUT /api/laoren/{id}/profile` - 更新老人个人资料

### 员工管理 - `/api/laogong`
- `GET /api/laogong` - 获取所有员工信息
- `GET /api/laogong/{id}` - 根据ID获取特定员工信息
- `POST /api/laogong` - 创建新的员工信息
- `PUT /api/laogong/{id}` - 更新员工信息
- `DELETE /api/laogong/{id}` - 删除员工信息
- `POST /api/laogong/login` - 员工登录
- `PUT /api/laogong/{id}/password` - 修改员工密码

### 服务请求管理 - `/api/service-requests`
- `GET /api/service-requests` - 获取所有服务请求
- `GET /api/service-requests/{id}` - 根据ID获取特定服务请求
- `POST /api/service-requests` - 创建新的服务请求
- `PUT /api/service-requests/{id}` - 更新服务请求
- `DELETE /api/service-requests/{id}` - 删除服务请求
- `GET /api/service-requests/laoren/{laorenId}` - 获取特定老年人的所有服务请求
- `GET /api/service-requests/status/{status}` - 根据状态获取服务请求
- `GET /api/service-requests/priority/{priority}` - 根据优先级获取服务请求

### 服务项目管理 - `/api/fuwuxiangmu`
- `GET /api/fuwuxiangmu` - 获取所有服务项目
- `GET /api/fuwuxiangmu/{id}` - 根据ID获取特定服务项目
- `POST /api/fuwuxiangmu` - 创建新的服务项目
- `PUT /api/fuwuxiangmu/{id}` - 更新服务项目
- `DELETE /api/fuwuxiangmu/{id}` - 删除服务项目
- `GET /api/fuwuxiangmu/type/{fuwuleixingId}` - 根据服务类型获取服务项目
- `GET /api/fuwuxiangmu/shangxia/{shangxia}` - 根据上下架状态获取服务项目

### 服务类型管理 - `/api/fuwuleixing`
- `GET /api/fuwuleixing` - 获取所有服务类型
- `GET /api/fuwuleixing/{id}` - 根据ID获取特定服务类型
- `POST /api/fuwuleixing` - 创建新的服务类型
- `PUT /api/fuwuleixing/{id}` - 更新服务类型
- `DELETE /api/fuwuleixing/{id}` - 删除服务类型

### 服务购买管理 - `/api/fuwugoumai`
- `GET /api/fuwugoumai` - 获取所有服务购买记录
- `GET /api/fuwugoumai/{id}` - 根据ID获取特定服务购买记录
- `POST /api/fuwugoumai` - 创建新的服务购买记录
- `PUT /api/fuwugoumai/{id}` - 更新服务购买记录
- `DELETE /api/fuwugoumai/{id}` - 删除服务购买记录
- `GET /api/fuwugoumai/laoren/{laorenUuid}` - 根据老人UUID获取服务购买记录
- `GET /api/fuwugoumai/status/{goumaiYesno}` - 根据状态获取服务购买记录

### 用户管理 - `/api/user`
- `GET /api/user` - 获取所有用户
- `GET /api/user/{id}` - 根据ID获取特定用户
- `POST /api/user/register` - 用户注册
- `POST /api/user/login` - 用户登录
- `POST /api/user/logout` - 用户登出
- `PUT /api/user/{id}` - 更新用户信息
- `DELETE /api/user/{id}` - 删除用户
- `PUT /api/user/{id}/password` - 修改用户密码
- `PUT /api/user/{id}/profile` - 更新用户个人资料

### 文件管理 - `/api/file`
- `POST /api/file/upload` - 上传单个文件
- `POST /api/file/upload/multiple` - 批量上传文件
- `DELETE /api/file/delete/{filename}` - 删除指定文件

### 令牌管理 - `/api/token`
- `POST /api/token/create` - 创建令牌
- `POST /api/token/validate` - 验证令牌
- `POST /api/token/invalidate` - 使令牌失效
- `POST /api/token/refresh` - 刷新令牌

### 系统健康检查 - `/api/health`
- `GET /api/health` - 检查系统健康状态

## 数据库配置

默认使用MySQL数据库，配置在`application.properties`文件中：

```
spring.datasource.url=jdbc:mysql://localhost:3306/ohelp2025?useSSL=false&serverTimezone=UTC&characterEncoding=utf8
spring.datasource.username=root
spring.datasource.password=root
```

## 启动项目

使用Maven启动项目：

```bash
cd o-b
mvnw spring-boot:run
```

或者在IDE中运行`OBApplication`类的main方法。

项目将在端口8080上启动。