<template>
  <div class="relative-manager">
    <h3>亲属信息管理</h3>

    <!-- 创建亲属表单 -->
    <div class="card">
      <h4>添加亲属信息</h4>
      <form @submit.prevent="handleCreateRelative" class="form">
        <div class="form-row">
          <div class="form-group">
            <label for="relativeElderlyId">老人ID *</label>
            <input
              id="relativeElderlyId"
              v-model.number="createForm.elderlyId"
              type="number"
              required
            />
          </div>
          <div class="form-group">
            <label for="relativeName">姓名 *</label>
            <input
              id="relativeName"
              v-model="createForm.name"
              type="text"
              required
            />
          </div>
        </div>

        <div class="form-row">
          <div class="form-group">
            <label for="relativePhone">电话 *</label>
            <input
              id="relativePhone"
              v-model="createForm.phone"
              type="tel"
              required
            />
          </div>
          <div class="form-group">
            <label for="relativeRelationship">关系 *</label>
            <select id="relativeRelationship" v-model="createForm.relationship" required>
              <option value="">请选择关系</option>
              <option value="子女">子女</option>
              <option value="配偶">配偶</option>
              <option value="兄弟姐妹">兄弟姐妹</option>
              <option value="其他亲属">其他亲属</option>
            </select>
          </div>
        </div>

        <div class="form-row">
          <div class="form-group">
            <label for="relativeEmail">邮箱</label>
            <input
              id="relativeEmail"
              v-model="createForm.email"
              type="email"
            />
          </div>
          <div class="form-group">
            <label for="relativePrimary">
              <input
                id="relativePrimary"
                v-model="createForm.isPrimaryContact"
                type="checkbox"
              />
              主要联系人
            </label>
          </div>
        </div>

        <button type="submit" class="btn btn-success" :disabled="createState.loading">
          {{ createState.loading ? '创建中...' : '添加亲属' }}
        </button>
        <p v-if="createState.error" class="error">{{ createState.error }}</p>
      </form>
    </div>

    <!-- 亲属查询 -->
    <div class="card">
      <h4>亲属查询</h4>
      <div class="query-section">
        <div class="form-group">
          <label for="searchRelativeId">按ID查询</label>
          <input
            id="searchRelativeId"
            v-model="searchId"
            type="number"
            placeholder="输入亲属ID"
          />
          <button @click="handleGetRelativeById" class="btn btn-secondary">查询</button>
        </div>

        <div class="form-group">
          <label for="searchElderlyRelatives">按老人ID查询亲属</label>
          <input
            id="searchElderlyRelatives"
            v-model="searchElderlyId"
            type="number"
            placeholder="输入老人ID"
          />
          <button @click="handleGetRelativesByElderlyId" class="btn btn-secondary">查询</button>
        </div>
      </div>

      <!-- 查询结果 -->
      <div v-if="queryResult" class="result">
        <h5>查询结果</h5>
        <table class="table">
          <tbody>
            <tr>
              <td><strong>ID</strong></td>
              <td>{{ queryResult.id }}</td>
            </tr>
            <tr>
              <td><strong>老人ID</strong></td>
              <td>{{ queryResult.elderlyId }}</td>
            </tr>
            <tr>
              <td><strong>姓名</strong></td>
              <td>{{ queryResult.name }}</td>
            </tr>
            <tr>
              <td><strong>电话</strong></td>
              <td>{{ queryResult.phone }}</td>
            </tr>
            <tr>
              <td><strong>关系</strong></td>
              <td>{{ queryResult.relationship }}</td>
            </tr>
            <tr>
              <td><strong>邮箱</strong></td>
              <td>{{ queryResult.email || '无' }}</td>
            </tr>
            <tr>
              <td><strong>主要联系人</strong></td>
              <td>{{ queryResult.isPrimaryContact ? '是' : '否' }}</td>
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
          <button @click="handleActivateRelative(queryResult.id)" class="btn btn-success btn-sm" :disabled="!queryResult.isActive">激活</button>
          <button @click="handleDeactivateRelative(queryResult.id)" class="btn btn-warning btn-sm" :disabled="queryResult.isActive">停用</button>
          <button @click="handleDeleteRelative(queryResult.id)" class="btn btn-danger btn-sm">删除</button>
        </div>
      </div>

      <!-- 老人亲属列表 -->
      <div v-if="elderlyRelatives.length > 0" class="result">
        <h5>老人亲属列表 (老人ID: {{ searchElderlyId }})</h5>
        <table class="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>姓名</th>
              <th>关系</th>
              <th>电话</th>
              <th>主要联系人</th>
              <th>状态</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="relative in elderlyRelatives" :key="relative.id">
              <td>{{ relative.id }}</td>
              <td>{{ relative.name }}</td>
              <td>{{ relative.relationship }}</td>
              <td>{{ relative.phone }}</td>
              <td>{{ relative.isPrimaryContact ? '是' : '否' }}</td>
              <td>
                <span :class="relative.isActive ? 'status status-active' : 'status status-inactive'">
                  {{ relative.isActive ? '激活' : '未激活' }}
                </span>
              </td>
              <td>
                <button @click="handleActivateRelative(relative.id)" class="btn btn-success btn-sm" :disabled="!relative.isActive">激活</button>
                <button @click="handleDeactivateRelative(relative.id)" class="btn btn-warning btn-sm" :disabled="relative.isActive">停用</button>
                <button @click="handleDeleteRelative(relative.id)" class="btn btn-danger btn-sm">删除</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <p v-if="queryError" class="error">{{ queryError }}</p>
    </div>

    <!-- 所有亲属列表 -->
    <div class="card">
      <h4>所有亲属</h4>
      <button @click="loadAllRelatives" class="btn btn-primary" :disabled="relativeListState.loading">
        {{ relativeListState.loading ? '加载中...' : '加载所有亲属' }}
      </button>
      <div v-if="relativeListState.error" class="error">{{ relativeListState.error }}</div>
      <div v-if="relativeListState.data.length > 0" class="table-container">
        <table class="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>老人ID</th>
              <th>姓名</th>
              <th>关系</th>
              <th>电话</th>
              <th>主要联系人</th>
              <th>状态</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="relative in relativeListState.data" :key="relative.id">
              <td>{{ relative.id }}</td>
              <td>{{ relative.elderlyId }}</td>
              <td>{{ relative.name }}</td>
              <td>{{ relative.relationship }}</td>
              <td>{{ relative.phone }}</td>
              <td>{{ relative.isPrimaryContact ? '是' : '否' }}</td>
              <td>
                <span :class="relative.isActive ? 'status status-active' : 'status status-inactive'">
                  {{ relative.isActive ? '激活' : '未激活' }}
                </span>
              </td>
              <td>
                <button @click="handleActivateRelative(relative.id)" class="btn btn-success btn-sm" :disabled="!relative.isActive">激活</button>
                <button @click="handleDeactivateRelative(relative.id)" class="btn btn-warning btn-sm" :disabled="relative.isActive">停用</button>
                <button @click="handleDeleteRelative(relative.id)" class="btn btn-danger btn-sm">删除</button>
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
  createRelative,
  getRelative,
  getRelativesByElderlyId,
  getAllRelatives,
  updateRelative,
  deleteRelative,
  activateRelative,
  deactivateRelative
} from './relative.api.js'

export default {
  name: 'RelativeManager',
  data() {
    return {
      // 创建表单
      createForm: {
        elderlyId: null,
        name: '',
        phone: '',
        relationship: '',
        email: '',
        isPrimaryContact: false
      },
      createState: {
        loading: false,
        error: null
      },

      // 查询相关
      searchId: '',
      searchElderlyId: '',
      queryResult: null,
      elderlyRelatives: [],
      queryError: null,

      // 亲属列表
      relativeListState: {
        loading: false,
        data: [],
        error: null
      }
    }
  },
  methods: {
    async handleCreateRelative() {
      this.createState.loading = true
      this.createState.error = null

      try {
        const response = await createRelative(this.createForm)
        console.log('创建亲属成功:', response.data)
        alert('创建亲属成功！')
        // 清空表单
        this.createForm = {
          elderlyId: null,
          name: '',
          phone: '',
          relationship: '',
          email: '',
          isPrimaryContact: false
        }
        // 重新加载列表
        if (this.relativeListState.data.length > 0) {
          await this.loadAllRelatives()
        }
      } catch (error) {
        this.createState.error = error.message || '创建失败'
      } finally {
        this.createState.loading = false
      }
    },

    async handleGetRelativeById() {
      if (!this.searchId) {
        alert('请输入亲属ID')
        return
      }

      this.queryError = null
      this.elderlyRelatives = []
      try {
        const response = await getRelative(parseInt(this.searchId))
        this.queryResult = response.data
      } catch (error) {
        this.queryError = error.message || '查询失败'
        this.queryResult = null
      }
    },

    async handleGetRelativesByElderlyId() {
      if (!this.searchElderlyId) {
        alert('请输入老人ID')
        return
      }

      this.queryError = null
      this.queryResult = null
      try {
        const response = await getRelativesByElderlyId(parseInt(this.searchElderlyId))
        this.elderlyRelatives = response.data
        if (response.data.length === 0) {
          this.queryError = '该老人暂无亲属信息'
        }
      } catch (error) {
        this.queryError = error.message || '查询失败'
        this.elderlyRelatives = []
      }
    },

    async loadAllRelatives() {
      this.relativeListState.loading = true
      this.relativeListState.error = null

      try {
        const response = await getAllRelatives()
        this.relativeListState.data = response.data
      } catch (error) {
        this.relativeListState.error = error.message || '加载失败'
      } finally {
        this.relativeListState.loading = false
      }
    },

    async handleActivateRelative(id) {
      try {
        await activateRelative(id)
        alert('激活成功！')
        await this.loadAllRelatives()
        if (this.queryResult && this.queryResult.id === id) {
          this.queryResult.isActive = true
        }
        // 更新老人亲属列表
        if (this.elderlyRelatives.length > 0) {
          const relative = this.elderlyRelatives.find(r => r.id === id)
          if (relative) relative.isActive = true
        }
      } catch (error) {
        alert('激活失败: ' + (error.message || '未知错误'))
      }
    },

    async handleDeactivateRelative(id) {
      try {
        await deactivateRelative(id)
        alert('停用成功！')
        await this.loadAllRelatives()
        if (this.queryResult && this.queryResult.id === id) {
          this.queryResult.isActive = false
        }
        // 更新老人亲属列表
        if (this.elderlyRelatives.length > 0) {
          const relative = this.elderlyRelatives.find(r => r.id === id)
          if (relative) relative.isActive = false
        }
      } catch (error) {
        alert('停用失败: ' + (error.message || '未知错误'))
      }
    },

    async handleDeleteRelative(id) {
      if (!confirm('确定要删除这个亲属信息吗？')) {
        return
      }

      try {
        await deleteRelative(id)
        alert('删除成功！')
        await this.loadAllRelatives()
        if (this.queryResult && this.queryResult.id === id) {
          this.queryResult = null
        }
        // 从老人亲属列表中移除
        this.elderlyRelatives = this.elderlyRelatives.filter(r => r.id !== id)
      } catch (error) {
        alert('删除失败: ' + (error.message || '未知错误'))
      }
    }
  }
}
</script>

<style scoped>
.relative-manager h3 {
  margin-bottom: 1.5rem;
  color: #2c3e50;
}

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1rem;
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

.result h5 {
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

.table-container {
  overflow-x: auto;
  margin-top: 1rem;
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