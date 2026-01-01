# Ohelp2025
基于Spring Boot + Vue 3的智慧养老服务管理系统的设计与实现

## 项目概述

### 项目背景

随着我国人口老龄化程度不断加深，养老服务需求日益增长。传统的养老服务管理方式已经难以满足现代养老机构的需求，急需一套高效、便捷、智能的养老服务管理系统来提升服务质量和管理效率。

### 项目目标

开发一套基于前后端分离架构的智慧养老服务管理系统，实现老人管理、服务管理、活动管理、健康管理、应急管理等功能，为养老机构提供全面的信息化管理解决方案。

### 核心功能

- **老人管理**：老人档案、亲属关系、账户管理
- **员工管理**：员工信息、权限管理
- **服务管理**：服务分类、项目、购买、请求、评价、护理
- **活动管理**：活动分类、信息、探望管理
- **健康管理**：健康记录、病史、积分管理
- **应急管理**：紧急求助管理
- **系统管理**：用户管理、系统配置

## 技术栈

### 后端技术栈

| 技术 | 版本 | 用途 | 官方文档 |
|------|------|------|----------|
| Spring Boot | 2.7.x | 后端框架 | [Spring Boot 文档](https://spring.io/projects/spring-boot) |
| Spring Security | 5.x | 安全框架 | [Spring Security 文档](https://spring.io/projects/spring-security) |
| MyBatis Plus | 3.x | ORM框架 | [MyBatis Plus 文档](https://baomidou.com/) |
| MySQL | 8.0 | 数据库 | [MySQL 文档](https://dev.mysql.com/doc/) |
| Swagger | 3.0 | API文档 | [Swagger 文档](https://swagger.io/) |
| Redis | 6.x | 缓存 | [Redis 文档](https://redis.io/documentation) |

### 前端技术栈

| 技术 | 版本 | 用途 | 官方文档 |
|------|------|------|----------|
| Vue 3 | 3.3.4 | 前端框架 | [Vue 3 文档](https://vuejs.org/guide/introduction.html) |
| Vite | 5.0.0 | 构建工具 | [Vite 文档](https://vitejs.dev/guide/) |
| Vue Router | 4 | 路由管理 | [Vue Router 文档](https://router.vuejs.org/guide/) |
| Pinia | - | 状态管理 | [Pinia 文档](https://pinia.vuejs.org/introduction.html) |
| Axios | - | HTTP 请求 | [Axios 文档](https://axios-http.com/docs/intro) |
| Element Plus | - | UI 组件库 | [Element Plus 文档](https://element-plus.org/en-US/) |

## 架构设计

### 前后端分离架构

```
┌─────────────────────────────────────────────────────────┐
│                       前端应用                          │
│  (Vue 3 + Vite + Element Plus)                        │
└─────────────────────────────────────────────────────────┘
                              ▲
                              │
                              │ HTTP/HTTPS
                              │ RESTful API
                              ▼
┌─────────────────────────────────────────────────────────┐
│                       后端应用                          │
│  ┌────────────┐ ┌────────────┐ ┌────────────┐        │
│  │ Controller │ │  Service   │ │ Repository │        │
│  └────────────┘ └────────────┘ └────────────┘        │
│  ┌────────────┐ ┌────────────┐ ┌────────────┐        │
│  │  Security  │ │    Cache   │ │    MQ      │        │
│  └────────────┘ └────────────┘ └────────────┘        │
└─────────────────────────────────────────────────────────┘
                              ▲
                              │
                              │ JDBC
                              ▼
┌─────────────────────────────────────────────────────────┐
│                       数据库                            │
│  (MySQL + Redis)                                      │
└─────────────────────────────────────────────────────────┘
```

### 模块划分

| 模块 | 业务领域 | 后端目录 | 前端目录 |
|------|----------|----------|----------|
| System | 系统管理 | `src/main/java/com/soft/ob/user` | `src/api/user`, `src/pages/system` |
| Elder | 老人管理 | `src/main/java/com/soft/ob/elder` | `src/api/elder`, `src/pages/elder` |
| Worker | 员工管理 | `src/main/java/com/soft/ob/worker` | `src/api/worker`, `src/pages/worker` |
| Service | 服务管理 | `src/main/java/com/soft/ob/serviceorder` | `src/api/serviceorder`, `src/pages/service` |
| Activity | 活动管理 | `src/main/java/com/soft/ob/activity` | `src/api/activity`, `src/pages/activity` |
| Health | 健康管理 | `src/main/java/com/soft/ob/health` | `src/api/health`, `src/pages/health` |
| Emergency | 应急管理 | `src/main/java/com/soft/ob/emergency` | `src/api/emergency`, `src/pages/emergency` |
| Auth | 认证管理 | `src/main/java/com/soft/ob/auth` | `src/api/auth` |
| File | 文件管理 | `src/main/java/com/soft/ob/file` | `src/api/file` |

## 项目结构

```
Ohelp2025/
├── o-b/                     # 后端项目
│   ├── db/                  # 数据库脚本
│   ├── src/                 # 后端源代码
│   │   └── main/            # 主代码目录
│   │       ├── java/        # Java源代码
│   │       └── resources/   # 资源文件
│   ├── .gitignore           # Git忽略文件
│   ├── README.md           # 后端README
│   ├── mvnw                # Maven包装器
│   ├── mvnw.cmd            # Maven包装器（Windows）
│   └── pom.xml             # Maven依赖配置
│
├── o-f/                     # 前端项目
│   ├── src/                 # 前端源代码
│   │   ├── api/            # API接口封装
│   │   ├── components/      # 公共组件
│   │   ├── pages/           # 页面组件
│   │   ├── router/          # 路由配置
│   │   ├── store/           # 状态管理
│   │   ├── styles/          # 样式文件
│   │   ├── utils/           # 工具函数
│   │   └── main-*.js        # 入口文件
│   ├── .gitignore           # Git忽略文件
│   ├── README.md           # 前端README
│   ├── index.html          # HTML模板
│   ├── package.json        # npm依赖配置
│   └── vite.config.js      # Vite配置
│
├── .gitignore               # Git忽略文件
└── README.md               # 项目根README
```

## 功能模块详细说明

### 1. 用户管理模块

- **用户登录**：支持用户名/密码登录
- **用户注册**：新用户注册功能
- **密码重置**：通过邮箱/手机号重置密码
- **用户信息管理**：查询、修改、删除用户信息
- **权限管理**：基于角色的权限控制

### 2. 老人管理模块

- **老人档案管理**：老人基本信息、照片、联系方式等
- **亲属关系管理**：管理老人的亲属信息
- **老人账户管理**：老人登录账户管理
- **老人健康档案**：健康记录、病史等

### 3. 员工管理模块

- **员工信息管理**：员工基本信息、联系方式等
- **员工权限管理**：基于角色的权限控制
- **员工排班管理**：工作排班、考勤记录

### 4. 服务管理模块

- **服务类型管理**：服务分类（如生活照料、医疗护理、精神慰藉等）
- **服务项目管理**：具体服务项目（名称、类型、价格、详情等）
- **服务购买管理**：服务购买订单处理
- **服务请求管理**：老人服务请求的接收、处理、反馈
- **服务评价管理**：老人或家属对服务的评价
- **护理计划管理**：为老人制定个性化护理计划

### 5. 活动管理模块

- **活动分类管理**：活动分类（如文化娱乐、体育健身、健康教育等）
- **活动信息管理**：活动详情（名称、时间、地点、费用、详情等）
- **活动报名管理**：老人活动报名、签到
- **探望管理**：家属探望登记、记录

### 6. 健康管理模块

- **健康记录管理**：每日健康记录、体检报告
- **病史管理**：既往病史、慢性病管理
- **健康评估**：定期健康评估、风险预警
- **积分管理**：健康积分、积分兑换

### 7. 应急管理模块

- **紧急求助**：老人紧急求助信号接收、处理
- **应急预案**：紧急情况处理流程
- **通知提醒**：相关人员通知、提醒

## 安装部署

### 环境要求

- **JDK**：>= 1.8
- **Maven**：>= 3.6.0
- **MySQL**：>= 8.0
- **Node.js**：>= 16.0.0
- **npm**：>= 8.0.0

### 后端部署

1. **克隆仓库**
   ```bash
   git clone https://github.com/your-org/ohelp2025.git
   cd ohelp2025/o-b
   ```

2. **配置数据库**
   ```bash
   # 创建数据库
   CREATE DATABASE ohelp2025 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   
   # 导入数据库脚本
   mysql -u root -p ohelp2025 < db/ohelp2025.sql
   ```

3. **配置application.properties**
   ```properties
   # 修改数据库连接信息
   spring.datasource.url=jdbc:mysql://localhost:3306/ohelp2025?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
   spring.datasource.username=root
   spring.datasource.password=your_password
   
   # 其他配置项
   ```

4. **构建项目**
   ```bash
   ./mvnw clean package -DskipTests
   ```

5. **运行项目**
   ```bash
   java -jar target/ob-1.0-SNAPSHOT.jar
   ```

6. **访问后端API文档**
   打开浏览器访问：http://localhost:8080/swagger-ui.html

### 前端部署

1. **进入前端目录**
   ```bash
   cd ohelp2025/o-f
   ```

2. **安装依赖**
   ```bash
   npm install
   ```

3. **配置环境变量**
   ```bash
   # 复制环境变量模板
   cp .env.example .env
   
   # 修改.env文件中的配置
   VITE_API_BASE_URL=http://localhost:8080
   ```

4. **构建生产版本**
   ```bash
   npm run build
   ```

5. **部署到Nginx**
   ```bash
   # 将dist目录下的文件复制到Nginx的html目录
   cp -r dist/* /usr/local/nginx/html/
   
   # 重启Nginx
   nginx -s reload
   ```

## 开发指南

### 后端开发

1. **开发工具**：IntelliJ IDEA 或 Eclipse
2. **代码规范**：遵循Spring Boot编码规范
3. **API设计**：RESTful风格，使用Swagger文档
4. **数据库设计**：使用MyBatis Plus，遵循数据库设计规范
5. **测试**：单元测试、集成测试

### 前端开发

1. **开发工具**：VS Code
2. **代码规范**：遵循ESLint和Prettier规范
3. **组件开发**：可复用组件设计
4. **API调用**：统一的API调用规范和返回结构
5. **状态管理**：使用Pinia管理全局状态

### 开发流程

1. **需求分析**：理解需求，制定开发计划
2. **设计**：架构设计、数据库设计、接口设计
3. **开发**：按照设计实现功能
4. **测试**：单元测试、集成测试、系统测试
5. **部署**：部署到测试环境、生产环境
6. **维护**： bug修复、功能优化

## API文档

### API调用规范

1. **基础URL**：`http://localhost:8080/api`
2. **请求方法**：GET、POST、PUT、DELETE
3. **请求头**：
   - `Content-Type`: `application/json`
   - `Authorization`: `Bearer {token}`（登录后获取）

### 统一返回结构

```json
{
  "code": 200,
  "message": "Success",
  "data": {},
  "timestamp": 1609459200000
}
```

### 错误码说明

| 错误码 | 含义 |
|--------|------|
| 200 | 请求成功 |
| 400 | 请求参数错误 |
| 401 | 未授权 |
| 403 | 禁止访问 |
| 404 | 资源不存在 |
| 500 | 服务器错误 |

## 数据库设计

### 主要表结构

1. **用户表** (`user`)
   - id: 用户ID
   - username: 用户名
   - password: 密码
   - name: 姓名
   - role: 角色
   - status: 状态

2. **老人表** (`laoren`)
   - id: 老人ID
   - name: 姓名
   - gender: 性别
   - age: 年龄
   - photo: 照片
   - phone: 联系方式
   - address: 地址

3. **服务类型表** (`fuwuleixing`)
   - id: 类型ID
   - name: 类型名称
   - description: 类型描述

4. **服务项目表** (`fuwuxiangmu`)
   - id: 项目ID
   - name: 项目名称
   - type_id: 类型ID
   - price: 价格
   - description: 项目描述

5. **活动信息表** (`huodongxinxi`)
   - id: 活动ID
   - name: 活动名称
   - category_id: 分类ID
   - location: 活动地点
   - time: 活动时间
   - cost: 活动费用
   - description: 活动描述

## 测试

### 后端测试

1. **单元测试**：使用JUnit进行单元测试
2. **集成测试**：使用Spring Boot Test进行集成测试
3. **API测试**：使用Postman或Swagger进行API测试

### 前端测试

1. **单元测试**：使用Vitest进行单元测试
2. **E2E测试**：使用Cypress进行E2E测试
3. **手动测试**：功能测试、兼容性测试

## 贡献指南

1. **Fork仓库**
2. **创建分支**：`git checkout -b feature/your-feature`
3. **提交代码**：`git commit -m "feat: add your feature"`
4. **推送分支**：`git push origin feature/your-feature`
5. **创建Pull Request**

## 许可证

MIT License

## 联系方式

- **项目负责人**：张三 (zhangsan@example.com)
- **技术支持**：李四 (lisi@example.com)
- **GitHub仓库**：[https://github.com/your-org/ohelp2025](https://github.com/your-org/ohelp2025)

## 致谢

感谢所有为项目做出贡献的开发者和支持者！

