# 用户身份与业务主体统一映射实现指南

## 📋 设计概述

本文档定义了 Ohelp2025 系统中**统一登录、身份代理、权限隔离**的实现方案。

### 核心目标
- ✅ 统一登录主体（`user` 表）
- ✅ 老人与家属均可登录系统，业务操作代表其绑定的老人
- ✅ 权限由 `role` 与 `permission` 统一管理
- ✅ 认证数据与业务数据解耦（避免直接修改 `elderly` / `relative` 密码字段）

---

## 🏗️ 数据库设计

### 表结构

#### 1. `user_elder_mapping` - 老人端账号映射

| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | BIGINT | PK | 主键 |
| user_id | BIGINT | FK, UNIQUE | 登录用户ID（每个user仅映射一个elderly） |
| elderly_id | BIGINT | FK, UNIQUE | 老人ID（每个elderly仅有一个user） |
| account_source | VARCHAR(50) | | 账号来源：manual(手工)/auto(自动) |
| created_at | DATETIME | | 创建时间 |
| updated_at | DATETIME | | 更新时间 |

**外键约束**
- `user_id` → `user.id` (ON DELETE CASCADE)
- `elderly_id` → `elderly.id` (ON DELETE CASCADE)

---

#### 2. `user_family_mapping` - 家属端账号映射

| 字段 | 类型 | 约束 | 说明 |
|------|------|------|------|
| id | BIGINT | PK | 主键 |
| user_id | BIGINT | FK, UNIQUE | 家属登录用户ID（一对一绑定某个elderly） |
| elderly_id | BIGINT | FK | 所代理的老人ID（允许多个family绑定同一elderly） |
| relative_id | BIGINT | FK, UNIQUE | 对应的亲属记录ID（每个relative仅有一个user） |
| is_primary_contact | TINYINT(1) | | 是否为主要联系人（冗余，来自relative表） |
| account_source | VARCHAR(50) | | 账号来源 |
| created_at | DATETIME | | 创建时间 |
| updated_at | DATETIME | | 更新时间 |

**外键约束**
- `user_id` → `user.id` (ON DELETE CASCADE)
- `elderly_id` → `elderly.id` (ON DELETE CASCADE)
- `relative_id` → `relative.id` (ON DELETE CASCADE)

---

#### 3. 统一查询视图 `v_user_elderly_binding`

```sql
SELECT user_id, elderly_id, 'elder' AS user_type, NULL AS relative_id FROM user_elder_mapping
UNION ALL
SELECT user_id, elderly_id, 'family' AS user_type, relative_id FROM user_family_mapping;
```

**用途**
- 后端快速查询当前用户绑定的 `elderly_id`
- 返回用户类型（elder/family），便于权限与数据范围控制

---

## 🔐 登录与身份解析流程

### 登录流程

```
1️⃣ 用户输入
   ├─ 用户名：user.phone（或 user.id）
   └─ 密码：elderly.phone_number（或 relative.phone）的初始值

2️⃣ 后端认证（现有 /auth/login）
   ├─ 查询 auth 表：WHERE username=? AND password=?
   ├─ 返回 auth 记录与 auth.user_id
   └─ 生成 JWT/Token（包含 user_id）

3️⃣ 身份解析（新增 ElderlyIdResolver 服务）
   ├─ 从 Token 解出 user_id
   ├─ 查询 v_user_elderly_binding：WHERE user_id=?
   ├─ 返回 elderly_id 与 user_type（elder/family）
   └─ 存储到当前请求上下文（ThreadLocal / SecurityContext）

4️⃣ 业务操作
   └─ 所有业务接口自动从上下文获取 current_elderly_id，作为数据范围过滤条件
```

---

## 🛠️ 后端实现

### 1. ElderlyIdResolver 服务

用于从当前用户解析出绑定的 `elderly_id` 与 `user_type`。

```java
package com.soft.ob.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ElderlyIdResolver {
    
    @Autowired
    private UserElderMappingMapper userElderMappingMapper;
    
    @Autowired
    private UserFamilyMappingMapper userFamilyMappingMapper;
    
    /**
     * 根据 user_id 解析该用户绑定的老人ID
     * @param userId 登录用户ID
     * @return 包含 elderlyId 与 userType 的结果对象
     */
    public Optional<ElderlyBinding> resolveElderlyId(Long userId) {
        // 先查 elder 映射
        Optional<UserElderMapping> elderMapping = userElderMappingMapper.selectByUserId(userId);
        if (elderMapping.isPresent()) {
            return Optional.of(new ElderlyBinding(
                elderMapping.get().getElderlyId(),
                "elder",
                null
            ));
        }
        
        // 再查 family 映射
        Optional<UserFamilyMapping> familyMapping = userFamilyMappingMapper.selectByUserId(userId);
        if (familyMapping.isPresent()) {
            return Optional.of(new ElderlyBinding(
                familyMapping.get().getElderlyId(),
                "family",
                familyMapping.get().getRelativeId()
            ));
        }
        
        return Optional.empty();
    }
    
    /**
     * 结果对象
     */
    public static class ElderlyBinding {
        public final Long elderlyId;
        public final String userType;  // "elder" or "family"
        public final Long relativeId;  // 仅当 userType="family" 时有值
        
        public ElderlyBinding(Long elderlyId, String userType, Long relativeId) {
            this.elderlyId = elderlyId;
            this.userType = userType;
            this.relativeId = relativeId;
        }
    }
}
```

---

### 2. ElderlyContext 上下文（ThreadLocal 存储）

在请求处理期间保存当前用户的身份信息。

```java
package com.soft.ob.common.context;

public class ElderlyContext {
    private static final ThreadLocal<ElderlyInfo> CONTEXT = new ThreadLocal<>();
    
    public static class ElderlyInfo {
        public Long userId;
        public Long elderlyId;
        public String userType;  // "elder" or "family"
        public Long relativeId;
    }
    
    public static void set(ElderlyInfo info) {
        CONTEXT.set(info);
    }
    
    public static ElderlyInfo get() {
        return CONTEXT.get();
    }
    
    public static Long getCurrentElderlyId() {
        ElderlyInfo info = CONTEXT.get();
        return info != null ? info.elderlyId : null;
    }
    
    public static String getCurrentUserType() {
        ElderlyInfo info = CONTEXT.get();
        return info != null ? info.userType : null;
    }
    
    public static void clear() {
        CONTEXT.remove();
    }
}
```

---

### 3. ElderlyContextInterceptor 拦截器

在每个请求开始时解析身份信息并存入上下文。

```java
package com.soft.ob.common.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ElderlyContextInterceptor implements HandlerInterceptor {
    
    @Autowired
    private ElderlyIdResolver elderlyIdResolver;
    
    @Autowired
    private JwtTokenProvider tokenProvider;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            // 从 Authorization header 解出 token
            String token = extractTokenFromHeader(request);
            if (token == null) {
                return true;  // 不存在 token 则跳过（可能是公开接口）
            }
            
            // 从 token 解出 user_id
            Long userId = tokenProvider.getUserIdFromToken(token);
            if (userId == null) {
                return true;
            }
            
            // 查询 user_id 对应的 elderly_id
            var binding = elderlyIdResolver.resolveElderlyId(userId);
            if (binding.isPresent()) {
                ElderlyContext.ElderlyInfo info = new ElderlyContext.ElderlyInfo();
                info.userId = userId;
                info.elderlyId = binding.get().elderlyId;
                info.userType = binding.get().userType;
                info.relativeId = binding.get().relativeId;
                ElderlyContext.set(info);
            }
            
            return true;
        } catch (Exception e) {
            // 日志记录，继续处理
            return true;
        }
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ElderlyContext.clear();
    }
    
    private String extractTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
```

---

### 4. WebMvcConfig 配置

注册拦截器。

```java
package com.soft.ob.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    
    @Autowired
    private ElderlyContextInterceptor elderlyContextInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(elderlyContextInterceptor)
            .addPathPatterns("/api/**")
            .excludePathPatterns(
                "/api/auth/login",
                "/api/auth/register",
                "/api/auth/validate/**"
            );
    }
}
```

---

### 5. 业务模块使用示例

以"获取当前老人的健康记录"为例。

```java
package com.soft.ob.health.controller;

import com.soft.ob.common.context.ElderlyContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/health")
public class HealthController {
    
    @Autowired
    private HealthService healthService;
    
    /**
     * 获取当前用户（老人/家属）绑定的老人的所有健康记录
     * 无需客户端传 elderlyId，由系统自动从身份上下文获取
     */
    @GetMapping("/")
    public Response<List<HealthRecord>> listMyHealthRecords() {
        Long currentElderlyId = ElderlyContext.getCurrentElderlyId();
        if (currentElderlyId == null) {
            return Response.error(401, "身份信息未找到");
        }
        
        List<HealthRecord> records = healthService.listByElderlyId(currentElderlyId);
        return Response.success(records);
    }
    
    /**
     * 创建健康记录（仅允许为当前老人创建）
     */
    @PostMapping("/")
    public Response<HealthRecord> createRecord(@RequestBody CreateHealthRecordDTO dto) {
        Long currentElderlyId = ElderlyContext.getCurrentElderlyId();
        
        // 验证：所创建的记录必须属于当前用户代表的老人
        if (!dto.getElderlyId().equals(currentElderlyId)) {
            return Response.error(403, "无权为其他老人创建记录");
        }
        
        HealthRecord record = healthService.create(dto);
        return Response.success(record);
    }
}
```

---

### 6. 权限校验增强

结合现有 RBAC，在关键接口增加权限校验。

```java
@PostMapping("/{elderlyId}/emergency")
public Response<?> reportEmergency(
    @PathVariable Long elderlyId,
    @RequestBody EmergencyDTO dto
) {
    // 身份校验：确保只能操作自己代表的老人
    Long currentElderlyId = ElderlyContext.getCurrentElderlyId();
    if (!elderlyId.equals(currentElderlyId)) {
        return Response.error(403, "无权操作其他老人的数据");
    }
    
    // 权限校验：检查是否有 "emergency:create" 权限
    String userType = ElderlyContext.getCurrentUserType();
    // 假设 elder 和 family 都有此权限
    if (!hasPermission("emergency:create")) {
        return Response.error(403, "无权创建紧急求助");
    }
    
    // 业务逻辑
    Emergency emergency = emergencyService.create(elderlyId, dto);
    return Response.success(emergency);
}
```

---

## 🎨 前端实现

### 1. 登录后解析用户身份

```javascript
// src/stores/auth.js (Pinia/Vuex)

import { defineStore } from 'pinia';
import { login as apiLogin, validateToken } from '@/api/auth';

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null,
    token: null,
    elderlyId: null,
    userType: null,  // 'admin', 'operator', 'elder', 'family', etc.
    isAuthenticated: false,
  }),
  
  actions: {
    async login(username, password) {
      try {
        const response = await apiLogin({ username, password });
        
        this.token = response.token;
        this.user = response;
        this.isAuthenticated = true;
        
        // 从响应中获取身份信息（后端应返回）
        this.userType = response.userType;        // 'elder', 'family', 'admin'
        this.elderlyId = response.elderlyId;      // 若是 elder/family 则有值
        
        // 存储到 localStorage 供后续请求使用
        localStorage.setItem('token', this.token);
        localStorage.setItem('userType', this.userType);
        localStorage.setItem('elderlyId', this.elderlyId);
        
        return response;
      } catch (error) {
        this.isAuthenticated = false;
        throw error;
      }
    },
    
    logout() {
      this.user = null;
      this.token = null;
      this.elderlyId = null;
      this.userType = null;
      this.isAuthenticated = false;
      localStorage.removeItem('token');
      localStorage.removeItem('userType');
      localStorage.removeItem('elderlyId');
    },
    
    restoreFromLocalStorage() {
      const token = localStorage.getItem('token');
      if (token) {
        this.token = token;
        this.userType = localStorage.getItem('userType');
        this.elderlyId = localStorage.getItem('elderlyId');
        this.isAuthenticated = true;
      }
    }
  }
});
```

---

### 2. 后端登录接口改进

确保登录响应返回 `userType` 与 `elderlyId`。

```javascript
// src/api/auth.js

export async function login({ username, password }) {
  const envelope = await requestRaw({
    method: 'POST',
    url: '/auth/login',
    data: { username, password },
  });
  
  if (!envelope?.data?.token) {
    throw new Error(envelope?.message || '登录失败');
  }
  
  return {
    token: envelope.data.token,
    userId: envelope.data.userId,
    // 新增字段（需后端支持）
    userType: envelope.data.userType,      // 'elder', 'family', 'admin', etc.
    elderlyId: envelope.data.elderlyId,    // elder/family 才有值
    username: envelope.data.username,
  };
}
```

---

### 3. 角色感知的路由守卫

```javascript
// src/router/guards.js

import { useAuthStore } from '@/stores/auth';

export function setupRouterGuards(router) {
  router.beforeEach((to, from, next) => {
    const authStore = useAuthStore();
    
    // 公开路由（无需认证）
    if (to.meta?.public) {
      return next();
    }
    
    // 需要认证的路由
    if (!authStore.isAuthenticated) {
      return next({ name: 'login' });
    }
    
    // 管理端路由（admin/operator/manager）
    if (to.meta?.requiresAdmin) {
      const adminRoles = ['admin', 'operator', 'manager'];
      if (!adminRoles.includes(authStore.userType)) {
        return next({ name: 'unauthorized' });
      }
    }
    
    // 用户端路由（elder/family）
    if (to.meta?.requiresUser) {
      const userRoles = ['elder', 'family'];
      if (!userRoles.includes(authStore.userType)) {
        return next({ name: 'unauthorized' });
      }
    }
    
    next();
  });
}
```

---

### 4. 路由定义

```javascript
// src/router/index.js

const routes = [
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/LoginView.vue'),
    meta: { public: true }
  },
  
  // 管理端
  {
    path: '/admin',
    name: 'admin',
    component: () => import('@/layout/AdminLayout.vue'),
    meta: { requiresAdmin: true },
    children: [
      {
        path: 'users',
        name: 'admin-users',
        component: () => import('@/views/admin/UsersView.vue')
      },
      {
        path: 'elders',
        name: 'admin-elders',
        component: () => import('@/views/admin/EldersView.vue')
      }
    ]
  },
  
  // 用户端
  {
    path: '/user',
    name: 'user',
    component: () => import('@/layout/UserLayout.vue'),
    meta: { requiresUser: true },
    children: [
      {
        path: 'profile',
        name: 'user-profile',
        component: () => import('@/views/user/ProfileView.vue')
      },
      {
        path: 'health',
        name: 'user-health',
        component: () => import('@/views/user/HealthView.vue')
      },
      {
        path: 'emergency',
        name: 'user-emergency',
        component: () => import('@/views/user/EmergencyView.vue')
      }
    ]
  }
];
```

---

### 5. 权限受限的 UI 组件

```vue
<!-- src/components/PermissionGuard.vue -->

<template>
  <div v-if="hasPermission" class="permission-guard">
    <slot />
  </div>
</template>

<script setup>
import { computed } from 'vue';
import { useAuthStore } from '@/stores/auth';
import { usePermissionStore } from '@/stores/permission';

const props = defineProps({
  permission: String,  // e.g., 'elderly:delete'
  requiresAll: Boolean  // true: 需要所有权限；false: 需要任意一个
});

const authStore = useAuthStore();
const permissionStore = usePermissionStore();

const hasPermission = computed(() => {
  const permissions = props.permission.split(',');
  
  if (props.requiresAll) {
    return permissions.every(p => permissionStore.hasPermission(p));
  } else {
    return permissions.some(p => permissionStore.hasPermission(p));
  }
});
</script>
```

使用示例：

```vue
<PermissionGuard permission="elderly:delete">
  <button @click="deleteElderly">删除老人</button>
</PermissionGuard>

<!-- 多权限：只要有其中一个就显示 -->
<PermissionGuard permission="elderly:view,health:view">
  <button @click="viewDetails">查看详情</button>
</PermissionGuard>
```

---

### 6. API 请求拦截（自动带上 Authorization）

```javascript
// src/api/http.js

import axios from 'axios';
import { useAuthStore } from '@/stores/auth';

const client = axios.create({
  baseURL: import.meta.env.VITE_API_BASE,
  timeout: 10000,
});

// 请求拦截：自动添加 token
client.interceptors.request.use((config) => {
  const authStore = useAuthStore();
  if (authStore.token) {
    config.headers.Authorization = `Bearer ${authStore.token}`;
  }
  return config;
}, (error) => {
  return Promise.reject(error);
});

// 响应拦截：处理 401 Unauthorized
client.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      const authStore = useAuthStore();
      authStore.logout();
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export default client;
```

---

## 📝 迁移步骤

### 1. 数据库迁移

```bash
# 执行映射表创建脚本
mysql -u root -p ohelp < o-b/sql/20260116_identity_mapping.sql

# 验证表已创建
mysql -u root -p -e "USE ohelp; SHOW TABLES LIKE 'user_%';"
```

---

### 2. 后端实现

1. 创建 Mapper 接口（`UserElderMappingMapper`, `UserFamilyMappingMapper`）
2. 实现 `ElderlyIdResolver` 服务
3. 实现 `ElderlyContext` 上下文管理
4. 添加 `ElderlyContextInterceptor` 拦截器
5. 配置 `WebMvcConfig` 注册拦截器
6. 修改登录接口返回 `userType` 与 `elderlyId`
7. 业务模块使用 `ElderlyContext.getCurrentElderlyId()` 作为数据范围过滤

---

### 3. 前端实现

1. 修改 `auth.js` 登录 API 返回结构
2. 创建 `useAuthStore` 存储 `userType` 与 `elderlyId`
3. 设置路由守卫 `setupRouterGuards()`
4. 更新路由定义，添加 `meta.requiresAdmin` / `meta.requiresUser`
5. 创建 `PermissionGuard` 组件
6. 配置 HTTP 拦截器自动添加 Authorization header

---

## 🧪 测试用例

### 场景1：老人登录

```
输入：
  username: 13900139005 （某个老人的手机号）
  password: 13900139005 （该老人的初始密码）

预期：
  ✓ 登录成功
  ✓ 返回 token 与 userType='elder', elderlyId=5
  ✓ 后续请求自动使用 elderly_id=5 作为数据范围
  ✓ 只能查看/编辑 elderly_id=5 相关的数据
```

---

### 场景2：家属登录

```
输入：
  username: 13800138006 （某位家属的手机号）
  password: 13800138006 （该家属的初始密码）

预期：
  ✓ 登录成功
  ✓ 返回 token 与 userType='family', elderlyId=5, relativeId=12
  ✓ 后续请求自动使用 elderly_id=5 作为数据范围（代理老人）
  ✓ 权限范围由 role 与 permission 限制（例如不能删除老人信息）
```

---

### 场景3：权限隔离

```
测试：family 账号尝试删除老人信息

输入：
  DELETE /api/elderly/5
  Authorization: Bearer <family_token>

预期：
  ✗ 返回 403 Forbidden
  ✗ 错误信息："无权执行此操作（权限不足）"
  
理由：
  - 虽然可以操作 elderly_id=5（身份映射允许）
  - 但 family 角色无 'elderly:delete' 权限（RBAC 限制）
```

---

## ✅ 实施清单

- [ ] 数据库迁移执行（`20260116_identity_mapping.sql`）
- [ ] 后端 Mapper 创建
- [ ] 后端 Service/拦截器实现
- [ ] 后端登录接口改进
- [ ] 前端 Auth Store 改进
- [ ] 前端路由守卫配置
- [ ] 前端权限组件创建
- [ ] 业务模块集成测试
- [ ] 权限隔离测试
- [ ] 用户端/管理端分离测试

---

## 📌 备注

- 所有业务操作都应使用 `ElderlyContext.getCurrentElderlyId()` 而非请求参数中的 `elderlyId`
- family 角色可拥有 elder 角色的所有权限子集（通过 role_permission 配置）
- 若后续需要"多账号代理"功能，可创建 `user_delegation` 表支持临时授权

---

**最后更新**：2026-01-16  
**版本**：1.0 Alpha
