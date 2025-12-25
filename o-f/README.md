# 智慧养老服务管理系统 - 前端（Vue3 + Vite）

本项目是「智慧养老服务管理系统」的前端部分，基于 **Vue 3 + Vite** 构建，采用多角色、多页面的后台管理型前端架构，与后端 Spring Boot 项目接口 **完全对齐**。

---

## 一、技术栈

- Vue 3
- Vite 5
- Vue Router 4
- Pinia（状态管理）
- Axios（HTTP 请求）
- Element Plus（UI 组件库）

---

## 二、系统角色说明

系统根据业务划分为三类用户角色：

| 角色   | 说明               | 后端接口前缀   |
| ------ | ------------------ | -------------- |
| 管理员 | 系统管理、数据维护 | `/api/user`    |
| 员工   | 服务处理、老人管理 | `/api/laogong` |
| 老人   | 服务浏览、服务购买 | `/api/laoren`  |

前端采用 **多页面入口 + 不同布局** 的方式进行角色隔离。

---

## 三、项目目录结构

```text
src
├─ api/                     # 后端接口封装
│  ├─ user.js               # 管理员 / 用户接口
│  ├─ laoren.js             # 老人接口
│  ├─ laogong.js            # 员工接口
│  ├─ elderly.js            # 老年人信息管理
│  ├─ serviceRequest.js     # 服务请求管理
│  ├─ fuwuxiangmu.js        # 服务项目
│  ├─ fuwuleixing.js        # 服务类型
│  ├─ fuwugoumai.js         # 服务购买
│  ├─ file.js               # 文件上传
│  └─ token.js              # Token 管理
│
├─ layouts/                 # 页面布局
│  ├─ AdminLayout.vue       # 管理员布局
│  ├─ StaffLayout.vue       # 员工布局
│  └─ ElderLayout.vue       # 老人布局
│
├─ pages/                   # 多页面入口
│  ├─ admin/
│  │  ├─ login.vue
│  │  └─ index.vue
│  ├─ staff/
│  │  ├─ login.vue
│  │  └─ index.vue
│  └─ elder/
│     ├─ login.vue
│     ├─ register.vue
│     └─ index.vue
│
├─ views/                   # 业务功能页面
│  ├─ dashboard/            # 首页仪表盘
│  ├─ elderly/              # 老人信息管理
│  ├─ laoren/               # 老人账户管理
│  ├─ laogong/              # 员工管理
│  ├─ serviceRequest/       # 服务请求
│  ├─ fuwuxiangmu/          # 服务项目
│  ├─ fuwuleixing/          # 服务类型
│  ├─ fuwugoumai/           # 服务购买
│  └─ user/                 # 用户管理
│
├─ router/
│  └─ index.js              # 路由配置
│
├─ store/
│  ├─ user.js               # 用户状态
│  └─ token.js              # Token 状态
│
├─ utils/
│  ├─ request.js            # Axios 封装
│  └─ auth.js               # 权限控制
│
├─ App.vue
└─ main.js
```

## 四、接口对照说明（前后端一致）

### 1. 用户 / 管理员模块

```
POST   /api/user/login
POST   /api/user/register
POST   /api/user/logout
GET    /api/user
GET    /api/user/{id}
PUT    /api/user/{id}
DELETE /api/user/{id}
PUT    /api/user/{id}/password
PUT    /api/user/{id}/profile
```

前端页面：

- `/admin/login`
- `/user/list`
- `/user/edit/:id`

### 2. 老人账户模块

```
POST   /api/laoren/register
POST   /api/laoren/login
GET    /api/laoren
GET    /api/laoren/{id}
PUT    /api/laoren/{id}
DELETE /api/laoren/{id}
PUT    /api/laoren/{id}/password
PUT    /api/laoren/{id}/profile
```

前端页面：

- `/elder/login`
- `/elder/register`
- `/laoren/list`

### 3. 员工管理模块

```
POST   /api/laogong/login
GET    /api/laogong
GET    /api/laogong/{id}
POST   /api/laogong
PUT    /api/laogong/{id}
DELETE /api/laogong/{id}
PUT    /api/laogong/{id}/password
```

前端页面：

- `/staff/login`
- `/laogong/list`

------

### 4. 老年人信息管理

```
GET    /api/elderly
GET    /api/elderly/active
GET    /api/elderly/{id}
POST   /api/elderly
PUT    /api/elderly/{id}
DELETE /api/elderly/{id}
GET    /api/elderly/search
```

前端页面：

- `/elderly/list`
- `/elderly/detail/:id`
- `/elderly/edit/:id`

------

### 5. 服务请求管理

```
GET    /api/service-requests
GET    /api/service-requests/{id}
POST   /api/service-requests
PUT    /api/service-requests/{id}
DELETE /api/service-requests/{id}
GET    /api/service-requests/status/{status}
GET    /api/service-requests/priority/{priority}
```

前端页面：

- `/service-request/list`
- `/service-request/create`

------

### 6. 服务类型 & 服务项目

```
GET    /api/fuwuleixing
POST   /api/fuwuleixing
PUT    /api/fuwuleixing/{id}
DELETE /api/fuwuleixing/{id}

GET    /api/fuwuxiangmu
GET    /api/fuwuxiangmu/type/{id}
GET    /api/fuwuxiangmu/shangxia/{shangxia}
POST   /api/fuwuxiangmu
PUT    /api/fuwuxiangmu/{id}
DELETE /api/fuwuxiangmu/{id}
```

前端页面：

- `/fuwuleixing/list`
- `/fuwuxiangmu/list`

------

### 7. 服务购买管理

```
GET    /api/fuwugoumai
GET    /api/fuwugoumai/{id}
POST   /api/fuwugoumai
PUT    /api/fuwugoumai/{id}
DELETE /api/fuwugoumai/{id}
GET    /api/fuwugoumai/laoren/{uuid}
GET    /api/fuwugoumai/status/{yesno}
```

前端页面：

- `/fuwugoumai/list`

------

### 8. 文件上传

```
POST   /api/file/upload
POST   /api/file/upload/multiple
DELETE /api/file/delete/{filename}
```

前端使用 Element Plus 的 `el-upload` 组件。

------

## 五、Axios 请求封装示例

```
import axios from 'axios'

const request = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 10000
})

request.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = token
  }
  return config
})

export default request
```

------

## 六、UI 设计规范

- 主色调：温和蓝 / 绿色
- 布局方式：卡片化 + 表格管理
- 老人端：大字体、简化操作
- 管理端：表格 + 弹窗编辑

推荐组件：

- el-table
- el-form
- el-card
- el-dialog
- el-upload

------

## 七、启动项目

```
npm install
npm run dev
```

默认访问地址：

```
http://localhost:5173
```

