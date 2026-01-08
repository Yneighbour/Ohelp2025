# 智慧养老服务管理系统 - 前端（Vue3 + Vite）

![Vue 3](https://img.shields.io/badge/Vue-3.3.4-brightgreen.svg?style=flat-square&logo=vue.js)
![Vite](https://img.shields.io/badge/Vite-5.0.0-blue.svg?style=flat-square&logo=vite)
![JavaScript](https://img.shields.io/badge/JavaScript-ES6+-yellow.svg?style=flat-square&logo=javascript)
![License](https://img.shields.io/badge/License-MIT-blue.svg?style=flat-square)
![Version](https://img.shields.io/badge/Version-0.1.0-blue.svg?style=flat-square)

## 项目概述

本项目是「智慧养老服务管理系统」的前端部分，基于 **Vue 3 + Vite** 构建，采用模块化架构，与后端 Spring Boot 项目接口 **完全对齐**。系统旨在为养老机构提供全面的服务管理解决方案，包括老人管理、员工管理、服务管理、活动管理、健康管理和应急管理等功能。

### 核心功能

- **老人管理**：老人档案、亲属关系管理
- **员工管理**：员工信息、权限管理
- **服务管理**：服务分类、项目、购买、请求、评价、护理
- **活动管理**：活动分类、信息、探望管理
- **健康管理**：健康记录、病史、积分管理
- **应急管理**：紧急求助管理
- **系统管理**：用户管理、系统配置

### 设计原则

- 前后端分离架构，接口完全对齐
- 模块化设计，高内聚低耦合
- 统一的API调用规范和返回结构
- 响应式设计，适配不同设备
- 良好的用户体验和直观的操作界面

## 相关资源

- **后端项目**：[智慧养老服务管理系统 - 后端](https://github.com/your-org/ohelp-backend)
- **API文档**：[Swagger UI](http://localhost:8080/swagger-ui.html)（后端启动后访问）
- **产品原型**：[Figma设计稿](https://www.figma.com/your-prototype-link)
- **项目管理**：[Jira看板](https://your-jira-link)

## 开发环境搭建

### 环境要求

- **Node.js**：>= 16.0.0
- **npm**：>= 8.0.0
- **Git**：>= 2.0.0

### 安装步骤

1. **克隆仓库**
   ```bash
   git clone https://github.com/your-org/ohelp-frontend.git
   cd ohelp-frontend
   ```

2. **安装依赖**
   ```bash
   npm install
   ```

3. **配置环境变量**
   ```bash
   # 复制环境变量模板
   cp .env.example .env
   
   # 根据实际情况修改.env文件中的配置
   # 主要配置项：
   # VITE_API_BASE_URL - 后端API基础URL
   # VITE_APP_TITLE - 应用标题
   ```

4. **启动开发服务器**
   ```bash
   npm run dev
   ```

5. **访问应用**
   打开浏览器访问：http://localhost:5173

## 技术栈

| 技术 | 版本 | 用途 | 官方文档 |
|------|------|------|----------|
| Vue 3 | 3.3.4 | 前端框架 | [Vue 3 文档](https://vuejs.org/guide/introduction.html) |
| Vite | 5.0.0 | 构建工具 | [Vite 文档](https://vitejs.dev/guide/) |
| Vue Router | 4 | 路由管理 | [Vue Router 文档](https://router.vuejs.org/guide/) |
| Pinia | - | 状态管理 | [Pinia 文档](https://pinia.vuejs.org/introduction.html) |
| Axios | - | HTTP 请求 | [Axios 文档](https://axios-http.com/docs/intro) |
| Element Plus | - | UI 组件库 | [Element Plus 文档](https://element-plus.org/en-US/) |

## 可用脚本命令

在项目根目录下，可以执行以下命令：

| 命令 | 说明 | 环境 |
|------|------|------|
| `npm install` | 安装项目依赖 | 开发/生产 |
| `npm run dev` | 启动开发服务器 | 开发 |
| `npm run build` | 构建生产版本 | 生产 |
| `npm run serve` | 预览生产构建 | 开发/测试 |
| `npm run lint` | 运行代码检查 | 开发 |
| `npm run typecheck` | 运行类型检查 | 开发 |

## 前端模块划分说明（与后端一致）

根据业务领域，前端模块划分为：

| 模块      | 业务领域   | API目录                | 页面目录                | 主要功能 |
|-----------|------------|------------------------|------------------------|----------|
| System    | 系统管理   | `/api/system`          | `/pages/system`        | 用户管理、系统配置 |
| Elder     | 老人管理   | `/api/elder`           | `/pages/elder`         | 老人档案、亲属关系 |
| Worker    | 员工管理   | `/api/worker`          | `/pages/worker`        | 员工信息管理 |
| Service   | 服务管理   | `/api/serviceorder`    | `/pages/service`       | 服务分类、服务项目、服务购买、服务请求、评价、照料 |
| Activity  | 活动管理   | `/api/activity`        | `/pages/activity`      | 活动分类、活动信息、探望管理 |
| Health    | 健康管理   | `/api/health`          | `/pages/health`        | 健康记录、病史、积分管理 |
| Emergency | 应急管理   | `/api/emergency`       | `/pages/emergency`     | 紧急求助管理 |
| Auth      | 认证管理   | `/api/auth`            | `/pages/Login.vue`     | Token管理、登录认证 |
| File      | 文件管理   | `/api/file`            | -                      | 文件上传下载 |
| User      | 用户管理   | `/api/user`            | `/pages/system`        | 用户信息 |

## 三、接口命名规范

### 3.0 核心原则
- **以后端接口命名为准**：前端API层严格遵循后端接口命名规则和路径
- **前端API层作为“英文语义适配层”**：将后端接口封装为具有清晰英文语义的前端函数，提升代码可读性和可维护性

### 3.1 模块化API结构
- 所有API文件按业务模块组织在 `src/api/` 目录下
- 使用英文命名，对应中文业务含义：
  - `elder` (老人模块) - 原 `laoren`
  - `worker` (员工模块) - 原 `laogong` 
  - `serviceorder` (服务订单模块) - 原 `fuwu*`
  - `activity` (活动模块) - 原 `huodong*`
  - `health` (健康模块) - 原 `jiankang*`
  - `emergency` (应急模块) - 原 `jinji*`

### 3.2 API函数命名规范
- 统一使用英文命名
- 遵循 `动词+实体` 的命名模式
- 例如：`getElderList`, `createServiceItem`, `updateWorker`
- 前端函数命名需准确反映后端接口的业务含义，保持与后端接口的逻辑一致性

### 3.3 统一API返回结构
所有API响应遵循统一格式：
```javascript
{
  success: true/false,      // 请求是否成功
  data: {},                 // 响应数据
  status: 200,              // HTTP状态码
  message: 'Success'        // 响应消息
}
```

## 四、API调用示例

### 4.1 导入API
```javascript
// 导入特定模块的API
import { getElderList, createElderly } from '@/api/elder'
import { getServiceItemList, createServiceItem } from '@/api/serviceorder'
import { getWorkerList } from '@/api/worker'
```

### 4.2 调用API
```javascript
// 获取老人列表
const fetchElderList = async () => {
  try {
    const response = await getElderList()
    if (response.success) {
      console.log('老人列表:', response.data)
    } else {
      console.error('获取失败:', response.message)
    }
  } catch (error) {
    console.error('请求错误:', error)
  }
}
```

### 4.3 错误处理
```javascript
// 错误处理示例
const handleCreateService = async (serviceData) => {
  try {
    const response = await createServiceItem(serviceData)
    if (response.success) {
      console.log('创建成功:', response.data)
    } else {
      console.error('创建失败:', response.message)
    }
  } catch (error) {
    console.error('网络错误:', error.message)
  }
}
```

## 五、权限与路由设计说明

### 5.1 路由分组
路由按业务模块分组：
- `/user` - 系统管理模块
- `/elder` - 老人管理模块
- `/worker` - 员工管理模块
- `/service-*` - 服务管理模块相关页面
- `/activity-*` - 活动管理模块相关页面
- `/health-*` - 健康管理模块相关页面
- `/emergency` - 应急管理模块

### 5.2 路由配置
```javascript
// 路由配置示例
{
  path: '/service-item',
  name: 'ServiceItem',
  component: () => import('../pages/service/FuwuxiangmuList.vue')
}
```

### 5.3 权限控制
- 使用Pinia管理用户认证状态
- 通过路由守卫实现页面级权限控制
- 组件内通过用户角色实现功能级权限控制

## 六、项目目录结构

```
src/
├─ api/                  # 业务模块API接口
│  ├─ activity/          # 活动管理API
│  ├─ auth/              # 认证API
│  ├─ elder/             # 老人管理API（含elderly.js、relative.js）
│  ├─ emergency/         # 应急管理API
│  ├─ file/              # 文件管理API
│  ├─ health/            # 健康管理API
│  ├─ serviceorder/      # 服务管理API
│  ├─ user/              # 用户API
│  ├─ worker/            # 员工API
│  └─ index.js           # API统一入口
│
├─ components/           # 公共组件
│  ├─ Header.vue         # 头部
│  └─ Footer.vue         # 底部
│
├─ models/               # 数据模型
│  ├─ User.js            # 用户模型
│  ├─ Elderly.js         # 老人模型
│  ├─ Worker.js          # 员工模型
│  └─ index.js           # 模型统一入口
│
├─ pages/                # 页面组件（按业务模块分组）
│  ├─ Dashboard.vue      # 仪表盘
│  ├─ Home.vue           # 首页
│  ├─ Login.vue          # 登录页
│  ├─ activity/          # 活动管理页面
│  │  ├─ HuodongfenleiList.vue
│  │  ├─ HuodongxinxiList.vue
│  │  └─ TanwangList.vue
│  ├─ elder/             # 老人管理页面
│  │  ├─ LaorenDetail.vue
│  │  ├─ LaorenList.vue
│  │  └─ QinshuList.vue
│  ├─ emergency/         # 应急管理页面
│  │  └─ JinjiqiuzhuList.vue
│  ├─ health/            # 健康管理页面
│  │  ├─ JifenzengjiaList.vue
│  │  ├─ JiwangbingshiList.vue
│  │  └─ MeirijiankangList.vue
│  ├─ service/           # 服务管理页面
│  │  ├─ FuwugoumaiList.vue
│  │  ├─ FuwuleixingList.vue
│  │  ├─ FuwuxiangmuList.vue
│  │  ├─ PingjiaList.vue
│  │  ├─ Requests.vue
│  │  └─ ZhaoliaoList.vue
│  ├─ system/            # 系统管理页面
│  │  └─ UserList.vue
│  └─ worker/            # 员工管理页面
│     └─ LaogongList.vue
│
├─ router/
│  └─ index.js           # 路由配置
│
├─ store/                # 状态管理
│  ├─ token.js           # 认证令牌状态
│  └─ user.js            # 用户状态
│
├─ styles/
│  └─ main.css           # 全局样式
│
├─ utils/
│  └─ request.js         # Axios请求封装
│
├─ App.vue               # 根组件
├─ main.js               # 主入口
├─ main-dashboard.js     # 仪表盘入口
├─ main-home.js          # 首页入口
├─ main-Laoren.js        # 老人管理入口
├─ main-requests.js      # 服务请求入口
└─ vite.config.js        # Vite配置
```

## 七、数据模型使用

### 7.1 模型定义
```javascript
// models/User.js 示例
export class User {
  constructor({...}) {
    // 模型属性定义
  }

  static fromAPI(data) {
    // 从API响应创建模型实例
  }

  toAPI() {
    // 转换为API请求格式
  }
}
```

### 7.2 模型使用
```javascript
import { User } from '@/models'

// 从API响应创建用户实例
const user = User.fromAPI(apiResponse.data)

// 将模型转换为API格式
const apiData = user.toAPI()
```

## 八、启动项目

```bash
npm install
npm run dev
```

默认访问地址：
```
http://localhost:5173
```

## 开发规范

1. **API调用**：所有API调用必须使用统一的返回结构处理
2. **状态管理**：仅将跨页面共享的数据放入Pinia状态管理
3. **模块化**：按业务领域进行模块划分，保持高内聚低耦合
4. **命名规范**：统一使用英文命名，保持命名一致性
5. **错误处理**：统一错误处理机制，提供友好的错误提示
6. **代码风格**：遵循ESLint和Prettier规范
7. **注释规范**：关键代码必须添加注释，说明功能和逻辑
8. **测试规范**：重要功能必须编写单元测试

## 使用示例

### 老人档案管理

1. **查询老人列表**
   - 访问路径：`/elder`
   - 功能：查看所有老人档案信息
   - 操作：点击列表中的"详情"按钮查看老人详细信息

2. **添加老人档案**
   - 访问路径：`/elder/add`
   - 功能：添加新的老人档案
   - 操作：填写老人基本信息、护理等级、亲属关系等，点击"保存"按钮

3. **编辑老人档案**
   - 访问路径：`/elder/edit/:id`
   - 功能：修改老人档案信息
   - 操作：修改老人信息，点击"保存"按钮

### 服务请求处理

1. **查询服务请求**
   - 访问路径：`/service-request`
   - 功能：查看所有服务请求
   - 操作：可以按状态、时间范围筛选请求

2. **处理服务请求**
   - 访问路径：`/service-request/edit/:id`
   - 功能：处理老人的服务请求
   - 操作：修改请求状态，分配服务人员，点击"保存"按钮

### 活动信息管理

1. **查询活动列表**
   - 访问路径：`/activity-info`
   - 功能：查看所有活动信息
   - 操作：可以按活动状态筛选

2. **添加活动信息**
   - 访问路径：`/activity-info/add`
   - 功能：添加新的活动信息
   - 操作：填写活动名称、时间、地点、费用等，点击"保存"按钮

## API文档

### API调用规范

1. **基础URL**：配置在`.env`文件中的`VITE_API_BASE_URL`
2. **请求方法**：GET、POST、PUT、DELETE
3. **请求头**：
   - `Content-Type`: `application/json`
   - `Authorization`: `Bearer {token}`（登录后获取）

### 统一返回结构

```javascript
{
  success: true/false,      // 请求是否成功
  data: {},                 // 响应数据
  status: 200,              // HTTP状态码
  message: 'Success'        // 响应消息
}
```

### 错误码说明

| 错误码 | 含义 | 处理方式 |
|--------|------|----------|
| 400 | 请求参数错误 | 检查请求参数格式和内容 |
| 401 | 未授权 | 重新登录获取token |
| 403 | 禁止访问 | 检查用户权限 |
| 404 | 资源不存在 | 检查请求URL是否正确 |
| 500 | 服务器错误 | 联系后端开发人员 |

### API模块文档

每个模块的API详细文档可以在以下位置查看：

| 模块 | API文件位置 | 主要功能 |
|------|------------|----------|
| 活动管理 | `src/api/activity/index.js` | 活动分类、信息、探望管理 |
| 老人管理 | `src/api/elder/index.js` | 老人账户、信息、亲属关系 |
| 员工管理 | `src/api/worker/index.js` | 员工信息管理 |
| 服务管理 | `src/api/serviceorder/index.js` | 服务分类、项目、购买、请求等 |
| 健康管理 | `src/api/health/index.js` | 健康记录、病史、积分管理 |
| 应急管理 | `src/api/emergency/index.js` | 紧急求助管理 |

## 贡献指南

### 开发流程

1. **创建分支**
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. **开发功能**
   - 按照开发规范编写代码
   - 编写单元测试
   - 确保代码通过lint检查

3. **提交代码**
   ```bash
   git add .
   git commit -m "feat: 描述你的功能"
   git push origin feature/your-feature-name
   ```

4. **创建Pull Request**
   - 在GitHub上创建Pull Request
   - 填写详细的功能描述和修改内容
   - 等待代码审查

5. **合并代码**
   - 代码审查通过后，将分支合并到主分支

### 代码规范

1. **命名规范**
   - 文件命名：使用kebab-case（短横线分隔）
   - 组件命名：使用PascalCase（首字母大写）
   - 变量命名：使用camelCase（驼峰式）
   - 常量命名：使用UPPER_CASE（大写字母+下划线）

2. **代码风格**
   - 使用2个空格缩进
   - 语句末尾使用分号
   - 大括号换行（Java风格）
   - 字符串使用单引号

3. **注释规范**
   - 组件和函数必须添加JSDoc注释
   - 复杂逻辑必须添加单行注释
   - 不要添加不必要的注释

## 故障排除

### 常见问题

1. **开发服务器启动失败**
   - 检查Node.js版本是否符合要求
   - 检查依赖是否安装完整（重新执行`npm install`）
   - 检查端口是否被占用

2. **API请求失败**
   - 检查`.env`文件中的API基础URL配置是否正确
   - 检查后端服务是否启动
   - 检查网络连接是否正常
   - 检查请求参数是否正确

3. **页面白屏或报错**
   - 检查浏览器控制台错误信息
   - 检查代码是否有语法错误
   - 检查组件导入路径是否正确

4. **构建失败**
   - 检查代码是否通过lint检查
   - 检查依赖是否有冲突
   - 检查Vite配置是否正确

### 调试技巧

1. **使用Vue DevTools**：安装浏览器扩展，方便调试Vue组件和状态
2. **检查网络请求**：使用浏览器开发者工具的Network面板查看API请求和响应
3. **日志输出**：在关键位置添加console.log语句，查看变量值和执行流程
4. **断点调试**：使用VS Code的调试功能，在代码中设置断点进行调试

## 版本说明

### 版本历史

| 版本 | 发布日期 | 主要功能 |
|------|----------|----------|
| 0.1.0 | 2026-01-01 | 初始版本，包含基本功能模块 |
| 0.2.0 | 2026-02-01 | 新增健康管理模块，优化UI设计 |
| 0.3.0 | 2026-03-01 | 新增应急管理模块，完善权限控制 |

### 升级说明

1. **从0.1.0升级到0.2.0**
   - 更新依赖：`npm install`
   - 配置新的环境变量
   - 运行数据库迁移脚本

2. **从0.2.0升级到0.3.0**
   - 更新依赖：`npm install`
   - 修改API调用方式
   - 重新构建项目：`npm run build`

## 许可证

MIT License

Copyright (c) 2026 智慧养老服务管理系统

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

## 联系我们

- **项目负责人**：张三 (zhangsan@example.com)
- **技术支持**：李四 (lisi@example.com)
- **反馈建议**：提交Issue到GitHub仓库
- **项目地址**：https://github.com/your-org/ohelp-frontend