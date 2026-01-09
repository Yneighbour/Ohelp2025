<template>
  <div class="user-module">
    <div class="card">
      <h2>用户管理模块</h2>

      <!-- 创建用户表单 -->
      <div class="card">
        <h3>创建用户</h3>
        <form @submit.prevent="handleCreateUser" class="form">
          <div class="form-group">
            <label for="userName">姓名</label>
            <input
              id="userName"
              v-model="createForm.name"
              type="text"
              required
            />
          </div>
          <div class="form-group">
            <label for="userEmail">邮箱</label>
            <input
              id="userEmail"
              v-model="createForm.email"
              type="email"
              required
            />
          </div>
          <div class="form-group">
            <label for="userPhone">电话</label>
            <input
              id="userPhone"
              v-model="createForm.phone"
              type="tel"
              required
            />
          </div>
          <div class="form-group">
            <label for="userRole">角色</label>
            <select id="userRole" v-model="createForm.role" required>
              <option value="">请选择角色</option>
              <option value="admin">管理员</option>
              <option value="worker">工作人员</option>
              <option value="relative">家属</option>
            </select>
          </div>
          <div class="form-group">
            <label for="userAvatar">头像URL</label>
            <input
              id="userAvatar"
              v-model="createForm.avatarUrl"
              type="url"
              placeholder="可选"
            />
          </div>
          <button type="submit" class="btn btn-success" :disabled="createState.loading">
            {{ createState.loading ? '创建中...' : '创建用户' }}
          </button>
          <p v-if="createState.error" class="error">{{ createState.error }}</p>
        </form>
      </div>

      <!-- 用户查询 -->
      <div class="card">
        <h3>用户查询</h3>
        <div class="query-section">
          <div class="form-group">
            <label for="searchId">按ID查询</label>
            <input
              id="searchId"
              v-model="searchId"
              type="number"
              placeholder="输入用户ID"
            />
            <button @click="handleGetUserById" class="btn btn-secondary">查询</button>
          </div>

          <div class="form-group">
            <label for="searchEmail">按邮箱查询</label>
            <input
              id="searchEmail"
              v-model="searchEmail"
              type="email"
              placeholder="输入用户邮箱"
            />
            <button @click="handleGetUserByEmail" class="btn btn-secondary">查询</button>
          </div>

          <div class="form-group">
            <label for="searchPhone">按电话查询</label>
            <input
              id="searchPhone"
              v-model="searchPhone"
              type="tel"
              placeholder="输入用户电话"
            />
            <button @click="handleGetUserByPhone" class="btn btn-secondary">查询</button>
          </div>
        </div>

        <!-- 查询结果 -->
        <div v-if="queryResult" class="result">
          <h4>查询结果</h4>
          <table class="table">
            <tbody>
              <tr>
                <td><strong>ID</strong></td>
                <td>{{ queryResult.id }}</td>
              </tr>
              <tr>
                <td><strong>姓名</strong></td>
                <td>{{ queryResult.name }}</td>
              </tr>
              <tr>
                <td><strong>邮箱</strong></td>
                <td>{{ queryResult.email }}</td>
              </tr>
              <tr>
                <td><strong>电话</strong></td>
                <td>{{ queryResult.phone }}</td>
              </tr>
              <tr>
                <td><strong>角色</strong></td>
                <td>{{ queryResult.role }}</td>
              </tr>
              <tr>
                <td><strong>头像</strong></td>
                <td>{{ queryResult.avatarUrl || '无' }}</td>
              </tr>
              <tr>
                <td><strong>状态</strong></td>
                <td>
                  <span :class="queryResult.isActive ? 'status status-active' : 'status status-inactive'">
                    {{ queryResult.isActive ? '激活' : '未激活' }}
                  </span>
                </td>
              </tr>
            </tbody>
          </table>
          <div class="actions">
            <button @click="handleActivateUser(queryResult.id)" class="btn btn-success btn-sm" :disabled="!queryResult.isActive">激活</button>
            <button @click="handleDeactivateUser(queryResult.id)" class="btn btn-warning btn-sm" :disabled="queryResult.isActive">停用</button>
            <button @click="handleDeleteUser(queryResult.id)" class="btn btn-danger btn-sm">删除</button>
          </div>
        </div>
        <p v-if="queryError" class="error">{{ queryError }}</p>
      </div>

      <!-- 用户列表 -->
      <div class="card">
        <h3>所有用户</h3>
        <button @click="loadAllUsers" class="btn btn-primary" :disabled="userListState.loading">
          {{ userListState.loading ? '加载中...' : '加载所有用户' }}
        </button>
        <div v-if="userListState.error" class="error">{{ userListState.error }}</div>
        <table v-if="userListState.data.length > 0" class="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>姓名</th>
              <th>邮箱</th>
              <th>电话</th>
              <th>角色</th>
              <th>状态</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="user in userListState.data" :key="user.id">
              <td>{{ user.id }}</td>
              <td>{{ user.name }}</td>
              <td>{{ user.email }}</td>
              <td>{{ user.phone }}</td>
              <td>{{ user.role }}</td>
              <td>
                <span :class="user.isActive ? 'status status-active' : 'status status-inactive'">
                  {{ user.isActive ? '激活' : '未激活' }}
                </span>
              </td>
              <td>
                <button @click="handleActivateUser(user.id)" class="btn btn-success btn-sm" :disabled="!user.isActive">激活</button>
                <button @click="handleDeactivateUser(user.id)" class="btn btn-warning btn-sm" :disabled="user.isActive">停用</button>
                <button @click="handleDeleteUser(user.id)" class="btn btn-danger btn-sm">删除</button>
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
  createUser,
  getUser,
  getUserByEmail,
  getUserByPhone,
  getAllUsers,
  updateUser,
  deleteUser,
  activateUser,
  deactivateUser
} from './user.api.js'

export default {
  name: 'UserModule',
  data() {
    return {
      // 创建用户表单
      createForm: {
        name: '',
        email: '',
        phone: '',
        role: '',
        avatarUrl: ''
      },
      createState: {
        loading: false,
        error: null
      },

      // 查询相关
      searchId: '',
      searchEmail: '',
      searchPhone: '',
      queryResult: null,
      queryError: null,

      // 用户列表
      userListState: {
        loading: false,
        data: [],
        error: null
      }
    }
  },
  methods: {
    async handleCreateUser() {
      this.createState.loading = true
      this.createState.error = null

      try {
        const response = await createUser(this.createForm)
        console.log('创建用户成功:', response.data)
        alert('创建用户成功！')
        // 清空表单
        this.createForm = {
          name: '',
          email: '',
          phone: '',
          role: '',
          avatarUrl: ''
        }
        // 重新加载列表
        if (this.userListState.data.length > 0) {
          await this.loadAllUsers()
        }
      } catch (error) {
        this.createState.error = error.message || '创建失败'
      } finally {
        this.createState.loading = false
      }
    },

    async handleGetUserById() {
      if (!this.searchId) {
        alert('请输入用户ID')
        return
      }

      this.queryError = null
      try {
        const response = await getUser(parseInt(this.searchId))
        this.queryResult = response.data
      } catch (error) {
        this.queryError = error.message || '查询失败'
        this.queryResult = null
      }
    },

    async handleGetUserByEmail() {
      if (!this.searchEmail) {
        alert('请输入用户邮箱')
        return
      }

      this.queryError = null
      try {
        const response = await getUserByEmail(this.searchEmail)
        this.queryResult = response.data
      } catch (error) {
        this.queryError = error.message || '查询失败'
        this.queryResult = null
      }
    },

    async handleGetUserByPhone() {
      if (!this.searchPhone) {
        alert('请输入用户电话')
        return
      }

      this.queryError = null
      try {
        const response = await getUserByPhone(this.searchPhone)
        this.queryResult = response.data
      } catch (error) {
        this.queryError = error.message || '查询失败'
        this.queryResult = null
      }
    },

    async loadAllUsers() {
      this.userListState.loading = true
      this.userListState.error = null

      try {
        const response = await getAllUsers()
        this.userListState.data = response.data
      } catch (error) {
        this.userListState.error = error.message || '加载失败'
      } finally {
        this.userListState.loading = false
      }
    },

    async handleActivateUser(id) {
      try {
        await activateUser(id)
        alert('激活成功！')
        // 重新加载列表
        await this.loadAllUsers()
        // 更新查询结果
        if (this.queryResult && this.queryResult.id === id) {
          this.queryResult.isActive = true
        }
      } catch (error) {
        alert('激活失败: ' + (error.message || '未知错误'))
      }
    },

    async handleDeactivateUser(id) {
      try {
        await deactivateUser(id)
        alert('停用成功！')
        // 重新加载列表
        await this.loadAllUsers()
        // 更新查询结果
        if (this.queryResult && this.queryResult.id === id) {
          this.queryResult.isActive = false
        }
      } catch (error) {
        alert('停用失败: ' + (error.message || '未知错误'))
      }
    },

    async handleDeleteUser(id) {
      if (!confirm('确定要删除这个用户吗？')) {
        return
      }

      try {
        await deleteUser(id)
        alert('删除成功！')
        // 重新加载列表
        await this.loadAllUsers()
        // 清空查询结果
        if (this.queryResult && this.queryResult.id === id) {
          this.queryResult = null
        }
      } catch (error) {
        alert('删除失败: ' + (error.message || '未知错误'))
      }
    }
  }
}
</script>

<style scoped>
.user-module {
  padding: 1rem 0;
}

.form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.query-section {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 1rem;
  margin-bottom: 1rem;
}

.query-section .form-group {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.query-section .form-group input {
  margin-bottom: 0.5rem;
}

.result {
  margin-top: 1rem;
  padding: 1rem;
  background-color: #f8f9fa;
  border-radius: 4px;
}

.result h4 {
  margin-bottom: 1rem;
  color: #2c3e50;
}

.result table {
  margin-bottom: 1rem;
}

.result table td {
  padding: 0.5rem;
  border: 1px solid #ddd;
}

.result table td:first-child {
  font-weight: bold;
  background-color: #f8f9fa;
  width: 120px;
}

.actions {
  display: flex;
  gap: 0.5rem;
}

.btn-sm {
  padding: 0.25rem 0.5rem;
  font-size: 0.875rem;
}

.btn-warning {
  background-color: #ffc107;
  color: #212529;
}

.btn-warning:hover {
  background-color: #e0a800;
}

.error {
  color: #dc3545;
  margin-top: 0.5rem;
}
</style>