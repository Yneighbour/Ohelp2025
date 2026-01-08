# 后端接口清单汇总表

## 📌 接口统计

| 模块 | 实体数量 | Mapper接口 | Service方法 | Controller接口 | 总计 |
|------|---------|-----------|-----------|--------------|------|
| Auth | 1 | 4 | 7 | 7 | **19** |
| User | 1 | 6 | 8 | 9 | **23** |
| Elder | 2 | 8 | 16 | 16 | **40** |
| Activity | 1 | 5 | 8 | 9 | **22** |
| Emergency | 1 | 7 | 9 | 10 | **26** |
| Health | 1 | 6 | 8 | 9 | **23** |
| ServiceOrder | 1 | 7 | 9 | 11 | **27** |
| Worker | 1 | 8 | 11 | 12 | **31** |
| File | 1 | 6 | 8 | 10 | **24** |
| **总计** | **10** | **57** | **84** | **93** | **235** |

---

## 🔗 完整接口清单

### 1. Auth 认证模块 (7个接口)
```
POST   /auth/login              - 用户登录
POST   /auth/logout             - 用户登出
POST   /auth/register           - 用户注册
GET    /auth/validate/{token}   - 验证令牌
GET    /auth/{id}               - 获取认证记录
GET    /auth/                   - 获取所有认证记录
DELETE /auth/{id}               - 删除认证记录
```

### 2. User 用户模块 (9个接口)
```
POST   /user/                   - 创建用户
GET    /user/{id}               - 获取用户
GET    /user/email/{email}      - 按邮箱查询
GET    /user/phone/{phone}      - 按电话查询
GET    /user/                   - 获取所有用户
PUT    /user/{id}               - 更新用户
DELETE /user/{id}               - 删除用户
PUT    /user/{id}/activate      - 激活用户
PUT    /user/{id}/deactivate    - 停用用户
```

### 3. Elder 老人模块 (16个接口)

#### Elderly (8个接口)
```
POST   /elder/elderly/          - 创建老人
GET    /elder/elderly/{id}      - 获取老人
GET    /elder/elderly/          - 获取所有老人
GET    /elder/elderly/search/{name} - 搜索老人
PUT    /elder/elderly/{id}      - 更新老人
DELETE /elder/elderly/{id}      - 删除老人
PUT    /elder/elderly/{id}/activate - 激活老人
PUT    /elder/elderly/{id}/deactivate - 停用老人
```

#### Relative (8个接口)
```
POST   /elder/relative/         - 创建亲属
GET    /elder/relative/{id}     - 获取亲属
GET    /elder/relative/elderly/{elderlyId} - 获取老人亲属
GET    /elder/relative/         - 获取所有亲属
PUT    /elder/relative/{id}     - 更新亲属
DELETE /elder/relative/{id}     - 删除亲属
PUT    /elder/relative/{id}/activate - 激活亲属
PUT    /elder/relative/{id}/deactivate - 停用亲属
```

### 4. Activity 活动模块 (9个接口)
```
POST   /activity/               - 创建活动
GET    /activity/{id}           - 获取活动
GET    /activity/               - 获取所有活动
GET    /activity/category/{category} - 按分类查询
GET    /activity/status/{status} - 按状态查询
PUT    /activity/{id}           - 更新活动
DELETE /activity/{id}           - 删除活动
PUT    /activity/{id}/activate  - 激活活动
PUT    /activity/{id}/deactivate - 停用活动
```

### 5. Emergency 紧急求助模块 (10个接口)
```
POST   /emergency/              - 创建求助
GET    /emergency/{id}          - 获取求助
GET    /emergency/              - 获取所有求助
GET    /emergency/elderly/{elderlyId} - 获取老人求助
GET    /emergency/status/{status} - 按状态查询
GET    /emergency/priority/{priority} - 按优先级查询
PUT    /emergency/{id}          - 更新求助
DELETE /emergency/{id}          - 删除求助
PUT    /emergency/{id}/respond   - 响应求助
PUT    /emergency/{id}/resolve   - 解决求助
```

### 6. Health 健康管理模块 (9个接口)
```
POST   /health/                 - 创建健康记录
GET    /health/{id}             - 获取健康记录
GET    /health/                 - 获取所有记录
GET    /health/elderly/{elderlyId} - 获取老人记录
GET    /health/date/{recordDate} - 按日期查询
GET    /health/doctor/{doctorId} - 获取医生记录
PUT    /health/{id}             - 更新记录
DELETE /health/{id}             - 删除记录
PUT    /health/{id}/activate    - 激活记录
PUT    /health/{id}/deactivate  - 停用记录 (显示9个)
```

### 7. ServiceOrder 服务订单模块 (11个接口)
```
POST   /serviceorder/           - 创建订单
GET    /serviceorder/{id}       - 获取订单
GET    /serviceorder/           - 获取所有订单
GET    /serviceorder/elderly/{elderlyId} - 获取老人订单
GET    /serviceorder/service-type/{serviceType} - 按服务类型
GET    /serviceorder/provider/{serviceProviderId} - 获取提供者订单
GET    /serviceorder/status/{status} - 按状态查询
PUT    /serviceorder/{id}       - 更新订单
DELETE /serviceorder/{id}       - 删除订单
PUT    /serviceorder/{id}/cancel - 取消订单
PUT    /serviceorder/{id}/complete - 完成订单
```

### 8. Worker 工作人员模块 (12个接口)
```
POST   /worker/                 - 创建员工
GET    /worker/{id}             - 获取员工
GET    /worker/email/{email}    - 按邮箱查询
GET    /worker/phone/{phone}    - 按电话查询
GET    /worker/                 - 获取所有员工
GET    /worker/department/{department} - 按部门查询
GET    /worker/position/{position} - 按职位查询
GET    /worker/available        - 获取可用员工
PUT    /worker/{id}             - 更新员工
DELETE /worker/{id}             - 删除员工
PUT    /worker/{id}/activate    - 激活员工
PUT    /worker/{id}/deactivate  - 停用员工
PUT    /worker/{id}/availability - 设置可用性 (显示12个)
```

### 9. File 文件管理模块 (10个接口)
```
POST   /file/upload             - 上传文件
GET    /file/{id}               - 获取文件记录
GET    /file/                   - 获取所有文件
GET    /file/uploader/{uploaderId} - 获取上传者文件
GET    /file/entity/{entityType}/{entityId} - 获取实体文件
GET    /file/type/{fileType}    - 按文件类型查询
PUT    /file/{id}               - 更新文件记录
DELETE /file/{id}               - 删除文件
PUT    /file/{id}/activate      - 激活文件记录
PUT    /file/{id}/deactivate    - 停用文件记录
```

---

## 🎯 常用接口场景

### 场景1: 用户登录流程
```
1. POST /auth/login (登录)
2. GET  /auth/validate/{token} (验证令牌)
3. GET  /user/{id} (获取用户信息)
```

### 场景2: 创建老人档案
```
1. POST /elder/elderly/ (创建老人)
2. POST /elder/relative/ (添加亲属)
3. POST /health/ (创建健康记录)
4. POST /file/upload (上传身份证等文件)
```

### 场景3: 处理紧急求助
```
1. POST /emergency/ (创建求助)
2. PUT  /emergency/{id}/respond (响应求助)
3. GET  /worker/available (获取可用员工)
4. POST /serviceorder/ (创建服务单)
5. PUT  /emergency/{id}/resolve (解决求助)
```

### 场景4: 管理活动
```
1. POST /activity/ (创建活动)
2. GET  /activity/status/scheduled (查询待开始活动)
3. GET  /elder/elderly/ (获取所有老人)
4. PUT  /activity/{id} (更新活动信息)
```

### 场景5: 健康数据记录
```
1. GET  /elder/elderly/{id} (获取老人)
2. POST /health/ (创建健康记录)
3. GET  /health/elderly/{elderlyId} (查询老人健康记录)
4. GET  /health/date/{recordDate} (查询特定日期记录)
```

---

## 💾 数据库表清单

| 表名 | 描述 | 主要字段 |
|------|------|---------|
| `auth` | 认证 | id, username, password, token, user_id |
| `user` | 用户 | id, name, email, phone, role |
| `elderly` | 老人 | id, name, age, gender, health_status |
| `relative` | 亲属 | id, elderly_id, name, phone, relationship |
| `activity` | 活动 | id, name, category, location, start_time |
| `emergency_request` | 紧急求助 | id, elderly_id, type, status, priority |
| `health_record` | 健康记录 | id, elderly_id, record_date, blood_pressure |
| `service_order` | 服务订单 | id, elderly_id, service_type, status |
| `worker` | 工作人员 | id, name, position, department, salary |
| `file_record` | 文件记录 | id, filename, file_type, entity_type |

---

## 🔐 HTTP 状态码参考

| 状态码 | 含义 | 使用场景 |
|--------|------|---------|
| 200 | OK | 查询、更新成功 |
| 201 | Created | 创建成功 |
| 400 | Bad Request | 请求参数错误 |
| 401 | Unauthorized | 未授权/令牌无效 |
| 404 | Not Found | 资源不存在 |
| 500 | Server Error | 服务器异常 |

---

## ⚙️ 通用请求/响应示例

### 请求示例 (创建用户)
```bash
curl -X POST http://localhost:8080/api/user/ \
  -H "Content-Type: application/json" \
  -d '{
    "name": "张三",
    "email": "zhangsan@example.com",
    "phone": "13800138000",
    "role": "admin"
  }'
```

### 响应示例 (成功)
```json
{
  "code": 201,
  "message": "User created successfully",
  "data": {
    "id": 1,
    "name": "张三",
    "email": "zhangsan@example.com",
    "phone": "13800138000",
    "role": "admin",
    "isActive": true,
    "createdAt": "2026-01-08T10:30:00",
    "updatedAt": "2026-01-08T10:30:00"
  }
}
```

### 响应示例 (失败)
```json
{
  "code": 404,
  "message": "User not found",
  "data": null
}
```

---

**文档生成时间**: 2026-01-08  
**后端版本**: v1.0.0 (Alpha)  
**总接口数**: 93个
