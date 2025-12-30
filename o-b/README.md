# 智慧养老服务管理系统 - 后端

这是一个基于Spring Boot的智慧养老服务管理系统后端，采用模块化架构设计，用于管理老年人信息、服务请求、健康档案、活动安排等养老服务相关功能。

## 项目结构

- `entity/` - JPA实体类
- `repository/` - 数据访问层接口
- `auth/` - 认证与授权模块
- `user/` - 后台用户管理模块
- `elder/` - 老人核心领域模块
- `worker/` - 护工/员工管理模块
- `serviceorder/` - 服务与订单管理模块
- `activity/` - 活动与探望管理模块
- `health/` - 健康与医疗管理模块
- `emergency/` - 紧急求助管理模块
- `file/` - 文件管理模块
- `system/` - 系统级通用模块

## 主要功能模块

### 1. 认证与授权模块 (auth)
- 用户登录、登出、Token校验和刷新
- 权限管理与访问控制

### 2. 后台用户模块 (user)
- 后台管理员账户管理
- 权限分配和用户资料维护

### 3. 老人核心领域模块 (elder)
- 老人账户管理（注册、登录、密码管理）
- 老人基本信息档案管理
- 亲属关系与紧急联系人管理

### 4. 护工/员工模块 (worker)
- 员工信息管理
- 员工认证与权限管理

### 5. 服务与订单模块 (serviceorder)
- 服务类型管理
- 服务项目管理
- 服务订单与购买记录管理
- 服务评价管理
- 招聘信息管理

### 6. 活动与探望模块 (activity)
- 活动分类管理
- 活动信息发布与管理
- 家属探望记录管理

### 7. 健康与医疗模块 (health)
- 每日健康记录管理
- 既往病史档案管理
- 积分变动记录管理

### 8. 紧急求助模块 (emergency)
- 紧急求助请求处理
- 应急响应管理

## API端点

### 认证管理 - `/api/auth`
- `POST /api/auth/login` - 用户登录
- `POST /api/auth/logout` - 用户登出
- `POST /api/auth/validate` - Token验证
- `POST /api/auth/refresh` - Token刷新

### 用户管理 - `/api/user`
- `GET /api/user` - 获取所有用户
- `GET /api/user/{id}` - 根据ID获取特定用户
- `POST /api/user` - 创建新用户
- `PUT /api/user/{id}` - 更新用户信息
- `DELETE /api/user/{id}` - 删除用户

### 老人账户管理 - `/api/laoren`
- `GET /api/laoren` - 获取所有老人信息
- `GET /api/laoren/{id}` - 根据ID获取特定老人信息
- `POST /api/laoren/register` - 注册新老人账户
- `POST /api/laoren/login` - 老人登录
- `PUT /api/laoren/{id}` - 更新老人信息
- `DELETE /api/laoren/{id}` - 删除老人信息
- `PUT /api/laoren/{id}/password` - 修改老人密码
- `PUT /api/laoren/{id}/profile` - 更新老人个人资料

### 老人档案管理 - `/api/elderly`
- `GET /api/elderly` - 获取所有老年人信息
- `GET /api/elderly/active` - 获取活跃老年人信息
- `GET /api/elderly/{id}` - 根据ID获取特定老年人信息
- `POST /api/elderly` - 创建新的老年人信息
- `PUT /api/elderly/{id}` - 更新老年人信息
- `DELETE /api/elderly/{id}` - 删除老年人信息（软删除）
- `GET /api/elderly/search?name={name}` - 根据姓名搜索老年人

### 老人亲属关系管理 - `/api/qinshu`
- `GET /api/qinshu` - 获取所有亲属信息
- `GET /api/qinshu/{id}` - 根据ID获取特定亲属信息
- `POST /api/qinshu` - 创建新的亲属关系
- `PUT /api/qinshu/{id}` - 更新亲属信息
- `DELETE /api/qinshu/{id}` - 删除亲属信息

### 员工管理 - `/api/laogong`
- `GET /api/laogong` - 获取所有员工信息
- `GET /api/laogong/{id}` - 根据ID获取特定员工信息
- `POST /api/laogong` - 创建新的员工信息
- `PUT /api/laogong/{id}` - 更新员工信息
- `DELETE /api/laogong/{id}` - 删除员工信息
- `POST /api/laogong/login` - 员工登录
- `PUT /api/laogong/{id}/password` - 修改员工密码

### 服务类型管理 - `/api/fuwuleixing`
- `GET /api/fuwuleixing` - 获取所有服务类型
- `GET /api/fuwuleixing/{id}` - 根据ID获取特定服务类型
- `POST /api/fuwuleixing` - 创建新的服务类型
- `PUT /api/fuwuleixing/{id}` - 更新服务类型
- `DELETE /api/fuwuleixing/{id}` - 删除服务类型

### 服务项目管理 - `/api/fuwuxiangmu`
- `GET /api/fuwuxiangmu` - 获取所有服务项目
- `GET /api/fuwuxiangmu/{id}` - 根据ID获取特定服务项目
- `POST /api/fuwuxiangmu` - 创建新的服务项目
- `PUT /api/fuwuxiangmu/{id}` - 更新服务项目
- `DELETE /api/fuwuxiangmu/{id}` - 删除服务项目
- `GET /api/fuwuxiangmu/type/{fuwuleixingId}` - 根据服务类型获取服务项目
- `GET /api/fuwuxiangmu/shangxia/{shangxia}` - 根据上下架状态获取服务项目

### 服务订单管理 - `/api/fuwugoumai`
- `GET /api/fuwugoumai` - 获取所有服务购买记录
- `GET /api/fuwugoumai/{id}` - 根据ID获取特定服务购买记录
- `POST /api/fuwugoumai` - 创建新的服务购买记录
- `PUT /api/fuwugoumai/{id}` - 更新服务购买记录
- `DELETE /api/fuwugoumai/{id}` - 删除服务购买记录
- `GET /api/fuwugoumai/laoren/{laorenUuid}` - 根据老人UUID获取服务购买记录
- `GET /api/fuwugoumai/status/{goumaiYesno}` - 根据状态获取服务购买记录

### 服务请求管理 - `/api/service-requests`
- `GET /api/service-requests` - 获取所有服务请求
- `GET /api/service-requests/{id}` - 根据ID获取特定服务请求
- `POST /api/service-requests` - 创建新的服务请求
- `PUT /api/service-requests/{id}` - 更新服务请求
- `DELETE /api/service-requests/{id}` - 删除服务请求
- `GET /api/service-requests/laoren/{laorenId}` - 获取特定老年人的所有服务请求
- `GET /api/service-requests/status/{status}` - 根据状态获取服务请求
- `GET /api/service-requests/priority/{priority}` - 根据优先级获取服务请求

### 活动分类管理 - `/api/huodongfenlei`
- `GET /api/huodongfenlei` - 获取所有活动分类
- `GET /api/huodongfenlei/{id}` - 根据ID获取特定活动分类
- `POST /api/huodongfenlei` - 创建新的活动分类
- `PUT /api/huodongfenlei/{id}` - 更新活动分类
- `DELETE /api/huodongfenlei/{id}` - 删除活动分类

### 活动信息管理 - `/api/huodongxinxi`
- `GET /api/huodongxinxi` - 获取所有活动信息
- `GET /api/huodongxinxi/{id}` - 根据ID获取特定活动信息
- `POST /api/huodongxinxi` - 创建新的活动信息
- `PUT /api/huodongxinxi/{id}` - 更新活动信息
- `DELETE /api/huodongxinxi/{id}` - 删除活动信息
- `GET /api/huodongxinxi/fenlei/{fenleiId}` - 根据分类获取活动信息

### 探望管理 - `/api/tanwang`
- `GET /api/tanwang` - 获取所有探望记录
- `GET /api/tanwang/{id}` - 根据ID获取特定探望记录
- `POST /api/tanwang` - 创建新的探望记录
- `PUT /api/tanwang/{id}` - 更新探望记录
- `DELETE /api/tanwang/{id}` - 删除探望记录
- `GET /api/tanwang/laoren/{laorenId}` - 根据老人ID获取探望记录
- `GET /api/tanwang/qinshu/{qinshuId}` - 根据亲属ID获取探望记录

### 每日健康记录管理 - `/api/meirijiankang`
- `GET /api/meirijiankang` - 获取所有每日健康记录
- `GET /api/meirijiankang/{id}` - 根据ID获取特定健康记录
- `POST /api/meirijiankang` - 创建新的健康记录
- `PUT /api/meirijiankang/{id}` - 更新健康记录
- `DELETE /api/meirijiankang/{id}` - 删除健康记录
- `GET /api/meirijiankang/laoren/{laorenId}` - 根据老人ID获取健康记录

### 既往病史管理 - `/api/jiwangbingshi`
- `GET /api/jiwangbingshi` - 获取所有既往病史记录
- `GET /api/jiwangbingshi/{id}` - 根据ID获取特定病史记录
- `POST /api/jiwangbingshi` - 创建新的病史记录
- `PUT /api/jiwangbingshi/{id}` - 更新病史记录
- `DELETE /api/jiwangbingshi/{id}` - 删除病史记录
- `GET /api/jiwangbingshi/laoren/{laorenId}` - 根据老人ID获取病史记录

### 积分变动管理 - `/api/jifenzengjia`
- `GET /api/jifenzengjia` - 获取所有积分变动记录
- `GET /api/jifenzengjia/{id}` - 根据ID获取特定积分记录
- `POST /api/jifenzengjia` - 创建新的积分变动记录
- `PUT /api/jifenzengjia/{id}` - 更新积分记录
- `DELETE /api/jifenzengjia/{id}` - 删除积分记录
- `GET /api/jifenzengjia/laoren/{laorenId}` - 根据老人ID获取积分记录

### 紧急求助管理 - `/api/jinjiqiuzhu`
- `GET /api/jinjiqiuzhu` - 获取所有紧急求助记录
- `GET /api/jinjiqiuzhu/{id}` - 根据ID获取特定求助记录
- `POST /api/jinjiqiuzhu` - 创建新的紧急求助记录
- `PUT /api/jinjiqiuzhu/{id}` - 更新求助记录
- `DELETE /api/jinjiqiuzhu/{id}` - 删除求助记录
- `GET /api/jinjiqiuzhu/laoren/{laorenId}` - 根据老人ID获取求助记录
- `GET /api/jinjiqiuzhu/status/{status}` - 根据状态获取求助记录

### 评价管理 - `/api/pingjia`
- `GET /api/pingjia` - 获取所有评价记录
- `GET /api/pingjia/{id}` - 根据ID获取特定评价记录
- `POST /api/pingjia` - 创建新的评价记录
- `PUT /api/pingjia/{id}` - 更新评价记录
- `DELETE /api/pingjia/{id}` - 删除评价记录
- `GET /api/pingjia/laoren/{laorenId}` - 根据老人ID获取评价记录
- `GET /api/pingjia/fuwugoumai/{fuwugoumaiId}` - 根据服务购买ID获取评价记录

### 招聘管理 - `/api/zhaoliao`
- `GET /api/zhaoliao` - 获取所有招聘信息
- `GET /api/zhaoliao/{id}` - 根据ID获取特定招聘信息
- `POST /api/zhaoliao` - 创建新的招聘信息
- `PUT /api/zhaoliao/{id}` - 更新招聘信息
- `DELETE /api/zhaoliao/{id}` - 删除招聘信息
- `GET /api/zhaoliao/status/{status}` - 根据状态获取招聘信息

### 文件管理 - `/api/file`
- `POST /api/file/upload` - 上传单个文件
- `POST /api/file/upload/multiple` - 批量上传文件
- `DELETE /api/file/delete/{filename}` - 删除指定文件

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

## 项目特点

1. **模块化架构** - 按业务功能划分模块，便于维护和扩展
2. **RESTful API** - 遵循REST设计原则
3. **分层架构** - Controller-Service-Repository分层设计
4. **数据安全** - 实现了用户认证和授权机制
5. **健康监控** - 提供系统健康检查端点