<template>
  <div class="auth-module">
    <div class="card">
      <h2>认证管理模块</h2>

      <!-- 登录表单 -->
      <BaseCard>
        <template #header>
          <h3>用户登录</h3>
        </template>

        <form @submit.prevent="handleLogin" class="form">
          <BaseInput
            v-model="loginForm.username"
            label="用户名"
            placeholder="请输入用户名"
            required
          />

          <BaseInput
            v-model="loginForm.password"
            type="password"
            label="密码"
            placeholder="请输入密码"
            required
          />

          <div class="form-actions">
            <BaseButton
              type="submit"
              variant="primary"
              :loading="loginState.loading"
            >
              登录
            </BaseButton>
          </div>

          <p v-if="loginState.error" class="error">{{ loginState.error }}</p>
        </form>
      </BaseCard>

      <!-- 注册表单 -->
      <BaseCard>
        <template #header>
          <h3>用户注册</h3>
        </template>

        <form @submit.prevent="handleRegister" class="form">
          <div class="form-row">
            <BaseInput
              v-model="registerForm.username"
              label="用户名"
              placeholder="请输入用户名"
              required
            />

            <BaseInput
              v-model="registerForm.email"
              type="email"
              label="邮箱"
              placeholder="请输入邮箱地址"
              required
            />
          </div>

          <div class="form-row">
            <BaseInput
              v-model="registerForm.phone"
              type="tel"
              label="电话"
              placeholder="请输入电话号码"
              required
            />

            <div class="form-group">
              <label for="registerRole">角色</label>
              <select id="registerRole" v-model="registerForm.role" class="input" required>
                <option value="">请选择角色</option>
                <option value="admin">管理员</option>
                <option value="worker">工作人员</option>
                <option value="relative">家属</option>
              </select>
            </div>
          </div>

          <BaseInput
            v-model="registerForm.password"
            type="password"
            label="密码"
            placeholder="请输入密码"
            required
          />

          <div class="form-actions">
            <BaseButton
              type="submit"
              variant="success"
              :loading="registerState.loading"
            >
              注册
            </BaseButton>
          </div>

          <p v-if="registerState.error" class="error">{{ registerState.error }}</p>
        </form>
      </BaseCard>

      <!-- 令牌验证 -->
      <BaseCard>
        <template #header>
          <h3>令牌验证</h3>
        </template>

        <div class="token-validation">
          <BaseInput
            v-model="validateTokenInput"
            label="令牌"
            placeholder="输入要验证的令牌"
          />

          <BaseButton
            @click="handleValidateToken"
            variant="secondary"
            :loading="validateState.loading"
          >
            验证令牌
          </BaseButton>
        </div>

        <div v-if="validateState.data" class="result">
          <p><strong>验证结果:</strong> {{ validateState.data.valid ? '有效' : '无效' }}</p>
          <div v-if="validateState.data.valid" class="valid-info">
            <p>用户ID: {{ validateState.data.userId }}</p>
            <p>用户名: {{ validateState.data.username }}</p>
          </div>
        </div>

        <p v-if="validateState.error" class="error">{{ validateState.error }}</p>
      </BaseCard>

      <!-- 认证记录列表 -->
      <BaseCard>
        <template #header>
          <h3>认证记录</h3>
          <BaseButton @click="loadAuthRecords" :loading="authListState.loading">
            加载认证记录
          </BaseButton>
        </template>

        <div v-if="authListState.error" class="error">{{ authListState.error }}</div>

        <BaseTable
          v-if="authListState.data.length > 0"
          :columns="authTableColumns"
          :data="authListState.data"
          :loading="authListState.loading"
          row-key="id"
        >
          <template #column-status="{ row }">
            <span :class="row.isActive ? 'status status-active' : 'status status-inactive'">
              {{ row.isActive ? '激活' : '未激活' }}
            </span>
          </template>

          <template #column-logoutTime="{ row }">
            {{ row.logoutTime || '未登出' }}
          </template>

          <template #actions="{ row }">
            <BaseButton
              @click="handleDeleteAuth(row.id)"
              variant="danger"
              size="sm"
            >
              删除
            </BaseButton>
          </template>
        </BaseTable>
      </BaseCard>
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

import { createUser } from '../user/user.api.js'

import BaseCard from '../../components/base/BaseCard.vue'
import BaseButton from '../../components/base/BaseButton.vue'
import BaseInput from '../../components/base/BaseInput.vue'
import BaseTable from '../../components/base/BaseTable.vue'

export default {
  name: 'AuthModule',
  components: {
    BaseCard,
    BaseButton,
    BaseInput,
    BaseTable
  },
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
  computed: {
    authTableColumns() {
      return [
        { key: 'id', title: 'ID', width: '80px' },
        { key: 'username', title: '用户名', width: '120px' },
        { key: 'userId', title: '用户ID', width: '100px' },
        { key: 'loginTime', title: '登录时间', width: '160px' },
        { key: 'logoutTime', title: '登出时间', width: '160px' },
        { key: 'status', title: '状态', width: '100px' },
        { key: 'actions', title: '操作', width: '100px' }
      ]
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
        // 后端 /auth/register 只接受 { username, password, userId }
        // 这里先创建 user（使用 email/phone/role），再创建 auth 记录。
        const createdUser = await createUser({
          name: this.registerForm.username,
          email: this.registerForm.email,
          phone: this.registerForm.phone,
          role: this.registerForm.role
        })

        const response = await register({
          username: this.registerForm.username,
          password: this.registerForm.password,
          userId: createdUser.data.id
        })

        console.log('注册成功:', response.data)
        alert(`注册成功！用户ID: ${createdUser.data.id}`)
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

.form-actions {
  display: flex;
  justify-content: flex-start;
  margin-top: var(--spacing-md);
}

.token-validation {
  display: flex;
  gap: var(--spacing-md);
  align-items: flex-end;
  margin-bottom: var(--spacing-lg);
}

.token-validation :deep(.base-input) {
  flex: 1;
}

.result {
  margin-top: 1rem;
  padding: 1rem;
  background-color: #f8f9fa;
  border-radius: 4px;
}

.valid-info {
  margin-top: var(--spacing-sm);
  padding-top: var(--spacing-sm);
  border-top: 1px solid var(--border-color);
}

.error {
  color: #dc3545;
  margin-top: 0.5rem;
}
</style>