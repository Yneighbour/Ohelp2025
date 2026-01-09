<template>
  <div class="auth-module">
    <div class="card">
      <h2>认证管理模块</h2>

      <!-- 登录表单 -->
      <div class="card">
        <h3>用户登录</h3>
        <form @submit.prevent="handleLogin" class="form">
          <div class="form-group">
            <label for="loginUsername">用户名</label>
            <input
              id="loginUsername"
              v-model="loginForm.username"
              type="text"
              required
            />
          </div>
          <div class="form-group">
            <label for="loginPassword">密码</label>
            <input
              id="loginPassword"
              v-model="loginForm.password"
              type="password"
              required
            />
          </div>
          <button type="submit" class="btn btn-primary" :disabled="loginState.loading">
            {{ loginState.loading ? '登录中...' : '登录' }}
          </button>
          <p v-if="loginState.error" class="error">{{ loginState.error }}</p>
        </form>
      </div>

      <!-- 注册表单 -->
      <div class="card">
        <h3>用户注册</h3>
        <form @submit.prevent="handleRegister" class="form">
          <div class="form-group">
            <label for="registerUsername">用户名</label>
            <input
              id="registerUsername"
              v-model="registerForm.username"
              type="text"
              required
            />
          </div>
          <div class="form-group">
            <label for="registerPassword">密码</label>
            <input
              id="registerPassword"
              v-model="registerForm.password"
              type="password"
              required
            />
          </div>
          <div class="form-group">
            <label for="registerEmail">邮箱</label>
            <input
              id="registerEmail"
              v-model="registerForm.email"
              type="email"
              required
            />
          </div>
          <div class="form-group">
            <label for="registerPhone">电话</label>
            <input
              id="registerPhone"
              v-model="registerForm.phone"
              type="tel"
              required
            />
          </div>
          <div class="form-group">
            <label for="registerRole">角色</label>
            <select id="registerRole" v-model="registerForm.role" required>
              <option value="">请选择角色</option>
              <option value="admin">管理员</option>
              <option value="worker">工作人员</option>
              <option value="relative">家属</option>
            </select>
          </div>
          <button type="submit" class="btn btn-success" :disabled="registerState.loading">
            {{ registerState.loading ? '注册中...' : '注册' }}
          </button>
          <p v-if="registerState.error" class="error">{{ registerState.error }}</p>
        </form>
      </div>

      <!-- 令牌验证 -->
      <div class="card">
        <h3>令牌验证</h3>
        <div class="form-group">
          <label for="validateToken">令牌</label>
          <input
            id="validateToken"
            v-model="validateTokenInput"
            type="text"
            placeholder="输入要验证的令牌"
          />
        </div>
        <button @click="handleValidateToken" class="btn btn-secondary" :disabled="validateState.loading">
          {{ validateState.loading ? '验证中...' : '验证令牌' }}
        </button>
        <div v-if="validateState.data" class="result">
          <p>验证结果: {{ validateState.data.valid ? '有效' : '无效' }}</p>
          <p v-if="validateState.data.valid">
            用户ID: {{ validateState.data.userId }}<br>
            用户名: {{ validateState.data.username }}
          </p>
        </div>
        <p v-if="validateState.error" class="error">{{ validateState.error }}</p>
      </div>

      <!-- 认证记录列表 -->
      <div class="card">
        <h3>认证记录</h3>
        <button @click="loadAuthRecords" class="btn btn-primary" :disabled="authListState.loading">
          {{ authListState.loading ? '加载中...' : '加载认证记录' }}
        </button>
        <div v-if="authListState.error" class="error">{{ authListState.error }}</div>
        <table v-if="authListState.data.length > 0" class="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>用户名</th>
              <th>用户ID</th>
              <th>登录时间</th>
              <th>登出时间</th>
              <th>状态</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="auth in authListState.data" :key="auth.id">
              <td>{{ auth.id }}</td>
              <td>{{ auth.username }}</td>
              <td>{{ auth.userId }}</td>
              <td>{{ auth.loginTime }}</td>
              <td>{{ auth.logoutTime || '未登出' }}</td>
              <td>
                <span :class="auth.isActive ? 'status status-active' : 'status status-inactive'">
                  {{ auth.isActive ? '激活' : '未激活' }}
                </span>
              </td>
              <td>
                <button @click="handleDeleteAuth(auth.id)" class="btn btn-danger btn-sm">删除</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import {
  login,
  logout,
  register,
  validateToken,
  getAuth,
  getAllAuths,
  deleteAuth
} from './auth.api.js'

export default {
  name: 'AuthModule',
  data() {
    return {
      // 登录表单
      loginForm: {
        username: '',
        password: ''
      },
      loginState: {
        loading: false,
        error: null
      },

      // 注册表单
      registerForm: {
        username: '',
        password: '',
        email: '',
        phone: '',
        role: ''
      },
      registerState: {
        loading: false,
        error: null
      },

      // 令牌验证
      validateTokenInput: '',
      validateState: {
        loading: false,
        data: null,
        error: null
      },

      // 认证记录列表
      authListState: {
        loading: false,
        data: [],
        error: null
      }
    }
  },
  methods: {
    async handleLogin() {
      this.loginState.loading = true
      this.loginState.error = null

      try {
        const response = await login(this.loginForm)
        console.log('登录成功:', response.data)
        alert('登录成功！')
        // 可以在这里保存token到localStorage
        // localStorage.setItem('token', response.data.token)
      } catch (error) {
        this.loginState.error = error.message || '登录失败'
      } finally {
        this.loginState.loading = false
      }
    },

    async handleRegister() {
      this.registerState.loading = true
      this.registerState.error = null

      try {
        const response = await register(this.registerForm)
        console.log('注册成功:', response)
        alert('注册成功！')
        // 清空表单
        this.registerForm = {
          username: '',
          password: '',
          email: '',
          phone: '',
          role: ''
        }
      } catch (error) {
        this.registerState.error = error.message || '注册失败'
      } finally {
        this.registerState.loading = false
      }
    },

    async handleValidateToken() {
      if (!this.validateTokenInput.trim()) {
        alert('请输入令牌')
        return
      }

      this.validateState.loading = true
      this.validateState.error = null
      this.validateState.data = null

      try {
        const response = await validateToken(this.validateTokenInput)
        this.validateState.data = response.data
      } catch (error) {
        this.validateState.error = error.message || '验证失败'
      } finally {
        this.validateState.loading = false
      }
    },

    async loadAuthRecords() {
      this.authListState.loading = true
      this.authListState.error = null

      try {
        const response = await getAllAuths()
        this.authListState.data = response.data
      } catch (error) {
        this.authListState.error = error.message || '加载失败'
      } finally {
        this.authListState.loading = false
      }
    },

    async handleDeleteAuth(id) {
      if (!confirm('确定要删除这条认证记录吗？')) {
        return
      }

      try {
        await deleteAuth(id)
        alert('删除成功！')
        // 重新加载列表
        await this.loadAuthRecords()
      } catch (error) {
        alert('删除失败: ' + (error.message || '未知错误'))
      }
    }
  }
}
</script>

<style scoped>
.auth-module {
  padding: 1rem 0;
}

.form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.btn-sm {
  padding: 0.25rem 0.5rem;
  font-size: 0.875rem;
}

.result {
  margin-top: 1rem;
  padding: 1rem;
  background-color: #f8f9fa;
  border-radius: 4px;
}

.error {
  color: #dc3545;
  margin-top: 0.5rem;
}
</style>