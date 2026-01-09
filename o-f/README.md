# Vue 3 + Vite

# Ohelp2025 前端项目

基于Vue 3 + Vite构建的Ohelp2025老年人服务管理系统前端应用。

## 项目特性

- **严格按照后端接口文档生成**：前端完全对应后端9个业务模块
- **模块化架构**：每个后端模块对应一个前端业务模块
- **JavaScript开发**：使用纯JavaScript，无TypeScript
- **响应式设计**：支持移动端和桌面端

## 技术栈

- **框架**: Vue 3
- **构建工具**: Vite
- **HTTP客户端**: Axios
- **路由**: Vue Router 4
- **样式**: 自定义CSS

## 项目结构

```
src/
├── api/                    # API层（预留）
├── modules/               # 业务模块层
│   ├── auth/             # 认证模块
│   ├── user/             # 用户管理模块
│   ├── elder/            # 老人信息模块
│   │   ├── elderly/      # 老人信息子模块
│   │   └── relative/     # 亲属信息子模块
│   ├── activity/         # 活动管理模块
│   ├── emergency/        # 紧急求助模块
│   ├── health/           # 健康管理模块
│   ├── serviceorder/     # 服务订单模块
│   ├── worker/           # 工作人员模块
│   └── file/             # 文件管理模块
├── shared/               # 共享工具
│   ├── http.js          # HTTP客户端
│   └── types.js         # 通用类型定义
├── router/               # 路由配置
├── App.vue              # 主应用组件
├── main.js              # 应用入口
└── style.css            # 全局样式
```

## 开发原则

### 后端即宪法原则
- 前端**不得创造、推导、重组业务语义**
- 严格按照后端接口文档生成代码
- 一个后端模块 = 一个前端业务模块

### 模块化铁律
- **一个Controller = 一个api文件**
- **一个接口 = 一个前端方法**
- 禁止跨模块API调用
- 禁止前端私自添加字段

### API层铁律
- URL、HTTP方法、字段名**必须100%一致**
- 每个模块有独立的types.js定义
- 禁止使用any类型

## 安装和运行

### 安装依赖
```bash
npm install
```

### 开发环境运行
```bash
npm run dev
```

### 构建生产版本
```bash
npm run build
```

### 预览生产版本
```bash
npm run preview
```

## 后端对接

前端默认连接到 `http://localhost:8080/api`，请确保后端服务已启动。

如果需要修改后端地址，请编辑 `src/shared/http.js` 中的 `baseURL`。

## 模块说明

### 1. Auth 认证模块
- 用户登录/登出
- 用户注册
- 令牌验证
- 认证记录管理

### 2. User 用户管理模块
- 用户CRUD操作
- 按邮箱/电话查询
- 用户激活/停用

### 3. Elder 老人信息模块
- **Elderly** 老人信息管理
- **Relative** 亲属信息管理
- 完整的老人档案管理

### 4. Activity 活动管理模块
- 活动CRUD操作
- 按分类/状态查询
- 活动组织管理

### 5. Emergency 紧急求助模块
- 求助请求创建
- 求助状态管理（pending/responded/resolved）
- 按优先级处理

### 6. Health 健康管理模块
- 健康记录管理
- 生理指标跟踪
- 医生记录关联

### 7. ServiceOrder 服务订单模块
- 服务订单管理
- 订单状态流转
- 服务提供商关联

### 8. Worker 工作人员模块
- 员工信息管理
- 职位部门管理
- 可用性设置

### 9. File 文件管理模块
- 文件上传下载
- 文件类型管理
- 实体关联管理

## 注意事项

- 前端**不得**在接口文档未定义的情况下添加业务逻辑
- 前端**不得**合并或重组后端接口
- 前端**不得**私自添加或修改字段定义
- 所有业务逻辑必须由后端提供接口支持

## 浏览器支持

- Chrome 70+
- Firefox 70+
- Safari 12+
- Edge 79+ The template uses Vue 3 `<script setup>` SFCs, check out the [script setup docs](https://v3.vuejs.org/api/sfc-script-setup.html#sfc-script-setup) to learn more.

Learn more about IDE Support for Vue in the [Vue Docs Scaling up Guide](https://vuejs.org/guide/scaling-up/tooling.html#ide-support).
