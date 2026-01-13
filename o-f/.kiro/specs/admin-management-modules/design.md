# Design Document

## Overview

为智慧养老系统添加4个后台管理静态页面：用户管理、老人管理、服务管理、活动管理。页面采用与现有系统一致的紫色主题风格，使用模拟数据展示，通过alert提示模拟操作反馈。

## Architecture

```
index.html (单页应用)
├── 管理页面路由
│   ├── #admin-users     (用户管理)
│   ├── #admin-elders    (老人管理)
│   ├── #admin-services  (服务管理)
│   └── #admin-activities (活动管理)
├── styles/
│   └── admin.css        (管理页面样式)
└── scripts/
    ├── data.js          (添加管理模块数据)
    ├── router.js        (添加管理页面路由)
    └── admin-handlers.js (管理页面交互处理)
```

## Components and Interfaces

### 1. 页面组件结构

每个管理页面包含以下通用组件：

```
┌─────────────────────────────────┐
│  顶部导航栏 (返回 + 标题)        │
├─────────────────────────────────┤
│  搜索筛选区域                    │
│  [搜索框] [筛选下拉] [添加按钮]  │
├─────────────────────────────────┤
│  数据表格                        │
│  ┌───┬───┬───┬───┬───┐         │
│  │列1│列2│列3│...│操作│         │
│  ├───┼───┼───┼───┼───┤         │
│  │   │   │   │   │按钮│         │
│  └───┴───┴───┴───┴───┘         │
├─────────────────────────────────┤
│  分页信息 (静态展示)             │
└─────────────────────────────────┘
```

### 2. 页面渲染函数接口

```javascript
// 用户管理页面
function renderAdminUsersPage() -> HTMLString

// 老人管理页面  
function renderAdminEldersPage() -> HTMLString

// 服务管理页面
function renderAdminServicesPage() -> HTMLString

// 活动管理页面
function renderAdminActivitiesPage() -> HTMLString
```

### 3. 事件处理函数接口

```javascript
// 搜索处理
function handleAdminSearch(type: string, keyword: string) -> void

// 添加操作
function handleAdminAdd(type: string) -> void

// 编辑操作
function handleAdminEdit(type: string, id: number) -> void

// 删除操作
function handleAdminDelete(type: string, id: number) -> void

// 查看详情
function handleAdminView(type: string, id: number) -> void

// 状态切换
function handleAdminToggleStatus(type: string, id: number) -> void
```

## Data Models

### 1. 用户数据模型

```javascript
const adminUsersData = [
  {
    id: number,           // 用户ID
    username: string,     // 用户名
    phone: string,        // 手机号
    role: string,         // 角色: admin/operator/user
    roleText: string,     // 角色显示文本
    status: string,       // 状态: active/disabled
    statusText: string,   // 状态显示文本
    createTime: string    // 注册时间
  }
]
```

### 2. 老人数据模型

```javascript
const adminEldersData = [
  {
    id: number,              // 老人ID
    name: string,            // 姓名
    age: number,             // 年龄
    gender: string,          // 性别
    phone: string,           // 联系电话
    address: string,         // 居住地址
    emergencyContact: string,// 紧急联系人
    emergencyPhone: string,  // 紧急联系电话
    healthStatus: string,    // 健康状态: normal/warning/danger
    healthText: string,      // 健康状态文本
    createTime: string       // 建档时间
  }
]
```

### 3. 服务数据模型

```javascript
const adminServicesData = [
  {
    id: number,           // 服务ID
    name: string,         // 服务名称
    category: string,     // 服务类型: life/medical/mental/other
    categoryText: string, // 类型显示文本
    price: string,        // 价格
    status: string,       // 状态: online/offline
    statusText: string,   // 状态显示文本
    description: string,  // 服务描述
    createTime: string    // 创建时间
  }
]
```

### 4. 活动管理数据模型（扩展现有activitiesData）

```javascript
// 复用现有activitiesData，添加管理所需字段
const adminActivitiesData = activitiesData.map(item => ({
  ...item,
  adminStatus: string,    // 管理状态: pending/ongoing/ended/cancelled
  adminStatusText: string // 状态显示文本
}))
```

## Correctness Properties

*A property is a characteristic or behavior that should hold true across all valid executions of a system-essentially, a formal statement about what the system should do. Properties serve as the bridge between human-readable specifications and machine-verifiable correctness guarantees.*

由于本项目是纯静态演示页面，主要验证UI渲染和交互提示的正确性：

Property 1: 页面渲染完整性
*For any* 管理页面，渲染后应包含顶部导航、搜索区域、数据表格和分页信息四个区域
**Validates: Requirements 5.2, 5.3**

Property 2: 数据展示一致性
*For any* 数据表格中的记录，其状态标签颜色应与状态值对应（正常-绿色，警告-橙色，异常-红色）
**Validates: Requirements 2.6, 3.6, 4.6**

Property 3: 操作反馈完整性
*For any* 用户点击操作按钮（添加/编辑/删除/查看），系统应显示对应的alert提示
**Validates: Requirements 1.3, 1.4, 1.5, 2.3, 2.4, 2.5, 3.3, 3.4, 3.5, 4.3, 4.4, 4.5**

## Error Handling

由于是静态演示页面，错误处理主要包括：

1. **路由错误**: 未知路由跳转到登录页
2. **数据为空**: 表格显示"暂无数据"提示
3. **搜索无结果**: 显示"未找到匹配数据"提示

## Testing Strategy

### 手动测试

由于是静态演示页面，主要通过手动测试验证：

1. 页面渲染是否完整
2. 样式是否与现有系统一致
3. 点击操作是否显示正确的alert提示
4. 响应式布局是否正常

### 测试用例

1. 访问各管理页面URL，验证页面正常显示
2. 点击各操作按钮，验证alert提示内容正确
3. 在移动端视图下验证表格滚动和布局
