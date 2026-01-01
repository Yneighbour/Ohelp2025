# 智慧养老服务管理系统 - 前端（Vue3 + Vite）

本项目是「智慧养老服务管理系统」的前端部分，基于 **Vue 3 + Vite** 构建，采用模块化架构，与后端 Spring Boot 项目接口 **完全对齐**。

## 一、技术栈

- Vue 3
- Vite 5
- Vue Router 4
- Pinia（状态管理）
- Axios（HTTP 请求）
- Element Plus（UI 组件库）

## 二、前端模块划分说明（与后端一致）

根据业务领域，前端模块划分为：

| 模块 | 业务领域 | 对应目录 | 主要功能 |
|------|----------|----------|----------|
| System | 系统管理 | `/api/system`, `/pages/system` | 用户管理、系统配置 |
| Elder | 老人管理 | `/api/elder`, `/pages/elder` | 老人档案、亲属关系 |
| Worker | 员工管理 | `/api/worker`, `/pages/worker` | 员工信息管理 |
| Service | 服务管理 | `/api/serviceorder`, `/pages/service` | 服务分类、服务项目、服务购买、服务请求、评价、护理 |
| Activity | 活动管理 | `/api/activity`, `/pages/activity` | 活动分类、活动信息、探望管理 |
| Health | 健康管理 | `/api/health`, `/pages/health` | 健康记录、病史、积分管理 |
| Emergency | 应急管理 | `/api/emergency`, `/pages/emergency` | 紧急求助管理 |
| Auth | 认证管理 | `/api/auth` | Token管理 |
| File | 文件管理 | `/api/file` | 文件上传下载 |

## 三、接口命名规范

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
src
├─ api/                           # 模块化API接口封装
│  ├─ activity/                   # 活动管理模块API
│  │  └─ index.js                # 活动相关接口
│  ├─ auth/                       # 认证模块API
│  │  └─ index.js                # Token管理接口
│  ├─ elder/                      # 老人模块API
│  │  ├─ index.js                # 老人账户接口
│  │  └─ elderly.js              # 老人信息接口
│  ├─ emergency/                  # 应急模块API
│  │  └─ index.js                # 紧急求助接口
│  ├─ health/                     # 健康模块API
│  │  └─ index.js                # 健康相关接口
│  ├─ serviceorder/               # 服务订单模块API
│  │  └─ index.js                # 服务相关接口
│  ├─ system/                     # 系统模块API
│  │  └─ index.js                # 用户管理接口
│  ├─ user/                       # 用户模块API
│  │  └─ index.js                # 用户相关接口
│  ├─ worker/                     # 员工模块API
│  │  └─ index.js                # 员工相关接口
│  ├─ file/                       # 文件模块API
│  │  └─ index.js                # 文件上传接口
│  └─ index.js                    # API统一入口
│
├─ components/                    # 公共组件
│  ├─ Header.vue                  # 页面头部
│  └─ Footer.vue                  # 页面底部
│
├─ models/                        # 数据模型
│  ├─ User.js                     # 用户模型
│  ├─ Elderly.js                  # 老人模型
│  ├─ Worker.js                   # 员工模型
│  └─ index.js                    # 模型统一入口
│
├─ pages/                         # 页面组件（按业务模块分组）
│  ├─ Dashboard.vue               # 仪表盘页面
│  ├─ Home.vue                    # 首页
│  ├─ activity/                   # 活动管理页面
│  │  ├─ HuodongfenleiList.vue   # 活动分类列表
│  │  ├─ HuodongxinxiList.vue    # 活动信息列表
│  │  └─ TanwangList.vue         # 探望列表
│  ├─ elder/                      # 老人管理页面
│  │  ├─ LaorenList.vue          # 老人档案列表
│  │  └─ QinshuList.vue          # 亲属列表
│  ├─ emergency/                  # 应急管理页面
│  │  └─ JinjiqiuzhuList.vue     # 紧急求助列表
│  ├─ health/                     # 健康管理页面
│  │  ├─ JifenzengjiaList.vue    # 积分列表
│  │  ├─ JiwangbingshiList.vue   # 病史列表
│  │  └─ MeirijiankangList.vue   # 健康记录列表
│  ├─ service/                    # 服务管理页面
│  │  ├─ FuwugoumaiList.vue      # 服务购买列表
│  │  ├─ FuwuleixingList.vue     # 服务分类列表
│  │  ├─ FuwuxiangmuList.vue     # 服务项目列表
│  │  ├─ PingjiaList.vue         # 评价列表
│  │  ├─ Requests.vue            # 服务请求列表
│  │  └─ ZhaoliaoList.vue        # 照料列表
│  ├─ system/                     # 系统管理页面
│  │  └─ UserList.vue            # 用户列表
│  └─ worker/                     # 员工管理页面
│     └─ LaogongList.vue         # 员工列表
│
├─ router/ 
│  └─ index.js                    # 路由配置
│
├─ store/                         # 状态管理
│  ├─ user.js                     # 用户状态（跨页面共享）
│  └─ token.js                    # 认证令牌状态（跨页面共享）
│
├─ styles/
│  └─ main.css                    # 全局样式
│
├─ utils/
│  └─ request.js                  # Axios请求封装（统一响应结构）
│
├─ App.vue                        # 根组件
├─ main-*.js                      # 多入口文件
└─ vite.config.js                 # Vite配置
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

## 九、开发规范

1. **API调用**：所有API调用必须使用统一的返回结构处理
2. **状态管理**：仅将跨页面共享的数据放入Pinia状态管理
3. **模块化**：按业务领域进行模块划分，保持高内聚低耦合
4. **命名规范**：统一使用英文命名，保持命名一致性
5. **错误处理**：统一错误处理机制，提供友好的错误提示