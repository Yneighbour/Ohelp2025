<template>
  <div class="elderly-manager">
    <h3>老人信息管理</h3>

    <!-- 创建老人表单 -->
    <div v-if="!isWorker" class="card">
      <h4>添加老人信息</h4>
      <form @submit.prevent="handleCreateElderly" class="form">
        <div class="form-row">
          <div class="form-group">
            <label for="elderlyName">姓名 *</label>
            <input
              id="elderlyName"
              v-model="createForm.name"
              type="text"
              required
            />
          </div>
          <div class="form-group">
            <label for="elderlyAge">年龄 *</label>
            <input
              id="elderlyAge"
              v-model.number="createForm.age"
              type="number"
              min="1"
              max="150"
              required
            />
          </div>
        </div>

        <div class="form-row">
          <div class="form-group">
            <label for="elderlyDob">出生日期 *</label>
            <input
              id="elderlyDob"
              v-model="createForm.dateOfBirth"
              type="date"
              required
            />
          </div>
          <div class="form-group">
            <label for="elderlyGender">性别 *</label>
            <select id="elderlyGender" v-model="createForm.gender" required>
              <option value="">请选择</option>
              <option value="男">男</option>
              <option value="女">女</option>
            </select>
          </div>
        </div>

        <div class="form-row">
          <div class="form-group">
            <label for="elderlyPhone">电话 *</label>
            <input
              id="elderlyPhone"
              v-model="createForm.phoneNumber"
              type="tel"
              required
            />
          </div>
          <div class="form-group">
            <label for="elderlyHealth">健康状态</label>
            <select id="elderlyHealth" v-model="createForm.healthStatus">
              <option value="">请选择</option>
              <option value="健康">健康</option>
              <option value="良好">良好</option>
              <option value="一般">一般</option>
              <option value="较差">较差</option>
              <option value="病重">病重</option>
            </select>
          </div>
        </div>

        <div class="form-group">
          <label for="elderlyMedical">医疗历史</label>
          <textarea
            id="elderlyMedical"
            v-model="createForm.medicalHistory"
            rows="3"
            placeholder="请输入医疗历史..."
          ></textarea>
        </div>

        <div class="form-group">
          <label for="elderlyAddress">地址 *</label>
          <input
            id="elderlyAddress"
            v-model="createForm.address"
            type="text"
            required
          />
        </div>

        <div class="form-row">
          <div class="form-group">
            <label for="elderlyContact">联系人 *</label>
            <input
              id="elderlyContact"
              v-model="createForm.contactPerson"
              type="text"
              required
            />
          </div>
          <div class="form-group">
            <label for="elderlyContactPhone">联系电话 *</label>
            <input
              id="elderlyContactPhone"
              v-model="createForm.contactPhone"
              type="tel"
              required
            />
          </div>
        </div>

        <button type="submit" class="btn btn-success" :disabled="createState.loading">
          {{ createState.loading ? '创建中...' : '添加老人' }}
        </button>
        <p v-if="createState.error" class="error">{{ createState.error }}</p>
      </form>
    </div>

    <!-- 老人查询 -->
    <div class="card">
      <h4>老人查询</h4>
      <div class="query-section">
        <div class="form-group">
          <label for="searchElderlyId">按ID查询</label>
          <input
            id="searchElderlyId"
            v-model="searchId"
            type="number"
            placeholder="输入老人ID"
          />
          <button @click="handleGetElderlyById" class="btn btn-secondary">查询</button>
        </div>

        <div class="form-group">
          <label for="searchElderlyName">按姓名搜索</label>
          <input
            id="searchElderlyName"
            v-model="searchName"
            type="text"
            placeholder="输入老人姓名"
          />
          <button @click="handleSearchByName" class="btn btn-secondary">搜索</button>
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
              <td><strong>姓名</strong></td>
              <td>{{ queryResult.name }}</td>
            </tr>
            <tr>
              <td><strong>年龄</strong></td>
              <td>{{ queryResult.age }}</td>
            </tr>
            <tr>
              <td><strong>出生日期</strong></td>
              <td>{{ queryResult.dateOfBirth }}</td>
            </tr>
            <tr>
              <td><strong>性别</strong></td>
              <td>{{ queryResult.gender }}</td>
            </tr>
            <tr>
              <td><strong>电话</strong></td>
              <td>{{ queryResult.phoneNumber }}</td>
            </tr>
            <tr>
              <td><strong>健康状态</strong></td>
              <td>{{ queryResult.healthStatus }}</td>
            </tr>
            <tr>
              <td><strong>地址</strong></td>
              <td>{{ queryResult.address }}</td>
            </tr>
            <tr>
              <td><strong>联系人</strong></td>
              <td>{{ queryResult.contactPerson }}</td>
            </tr>
            <tr>
              <td><strong>联系电话</strong></td>
              <td>{{ queryResult.contactPhone }}</td>
            </tr>
            <tr>
              <td><strong>状态</strong></td>
              <td>
                <span :class="queryResult.isActive ? 'status status-active' : 'status status-inactive'">
                  {{ queryResult.isActive ? '激活' : '未激活' }}
                </span>
              </td>
            </tr>
            <tr v-if="queryResult.medicalHistory">
              <td><strong>医疗历史</strong></td>
              <td>{{ queryResult.medicalHistory }}</td>
            </tr>
          </tbody>
        </table>
        <div class="actions">
          <button v-if="!isWorker" @click="handleActivateElderly(queryResult.id)" class="btn btn-success btn-sm" :disabled="!queryResult.isActive">激活</button>
          <button v-if="!isWorker" @click="handleDeactivateElderly(queryResult.id)" class="btn btn-warning btn-sm" :disabled="queryResult.isActive">停用</button>
          <button v-if="!isWorker" @click="handleDeleteElderly(queryResult.id)" class="btn btn-danger btn-sm">删除</button>
        </div>
      </div>
      <p v-if="queryError" class="error">{{ queryError }}</p>
    </div>

    <!-- 所有老人列表 -->
    <div class="card">
      <h4>所有老人</h4>
      <button @click="loadAllElderly" class="btn btn-primary" :disabled="elderlyListState.loading">
        {{ elderlyListState.loading ? '加载中...' : (isWorker ? '加载在档老人' : '加载所有老人') }}
      </button>
      <div v-if="elderlyListState.error" class="error">{{ elderlyListState.error }}</div>
      <div v-if="elderlyListState.data.length > 0" class="table-container">
        <table class="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>姓名</th>
              <th>年龄</th>
              <th>性别</th>
              <th>电话</th>
              <th>健康状态</th>
              <th>联系人</th>
              <th>状态</th>
              <th v-if="!isWorker">操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="elderly in elderlyListState.data" :key="elderly.id">
              <td>{{ elderly.id }}</td>
              <td>{{ elderly.name }}</td>
              <td>{{ elderly.age }}</td>
              <td>{{ elderly.gender }}</td>
              <td>{{ elderly.phoneNumber }}</td>
              <td>{{ elderly.healthStatus }}</td>
              <td>{{ elderly.contactPerson }}</td>
              <td>
                <span :class="elderly.isActive ? 'status status-active' : 'status status-inactive'">
                  {{ elderly.isActive ? '激活' : '未激活' }}
                </span>
              </td>
              <td v-if="!isWorker">
                <button @click="handleActivateElderly(elderly.id)" class="btn btn-success btn-sm" :disabled="!elderly.isActive">激活</button>
                <button @click="handleDeactivateElderly(elderly.id)" class="btn btn-warning btn-sm" :disabled="elderly.isActive">停用</button>
                <button @click="handleDeleteElderly(elderly.id)" class="btn btn-danger btn-sm">删除</button>
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
  createElderly,
  getElderly,
  getAllElderly,
  searchElderlyByName,
  updateElderly,
  deleteElderly,
  activateElderly,
  deactivateElderly
} from './elderly.api.js'

export default {
  name: 'ElderlyManager',
  data() {
    return {
      // 创建表单
      createForm: {
        name: '',
        age: null,
        dateOfBirth: '',
        gender: '',
        phoneNumber: '',
        healthStatus: '',
        medicalHistory: '',
        address: '',
        contactPerson: '',
        contactPhone: ''
      },
      createState: {
        loading: false,
        error: null
      },

      // 查询相关
      searchId: '',
      searchName: '',
      queryResult: null,
      queryError: null,

      // 老人列表
      elderlyListState: {
        loading: false,
        data: [],
        error: null
      }
    }
  },
  computed: {
    currentRole() {
      return localStorage.getItem('role') || ''
    },
    isWorker() {
      return this.currentRole === 'WORKER'
    }
  },
  methods: {
    async handleCreateElderly() {
      this.createState.loading = true
      this.createState.error = null

      try {
        const response = await createElderly(this.createForm)
        console.log('创建老人成功:', response.data)
        alert('创建老人成功！')
        // 清空表单
        this.createForm = {
          name: '',
          age: null,
          dateOfBirth: '',
          gender: '',
          phoneNumber: '',
          healthStatus: '',
          medicalHistory: '',
          address: '',
          contactPerson: '',
          contactPhone: ''
        }
        // 重新加载列表
        if (this.elderlyListState.data.length > 0) {
          await this.loadAllElderly()
        }
      } catch (error) {
        this.createState.error = error.message || '创建失败'
      } finally {
        this.createState.loading = false
      }
    },

    async handleGetElderlyById() {
      if (!this.searchId) {
        alert('请输入老人ID')
        return
      }

      this.queryError = null
      try {
        const response = await getElderly(parseInt(this.searchId))
        this.queryResult = response.data
      } catch (error) {
        this.queryError = error.message || '查询失败'
        this.queryResult = null
      }
    },

    async handleSearchByName() {
      if (!this.searchName.trim()) {
        alert('请输入老人姓名')
        return
      }

      this.queryError = null
      try {
        const response = await searchElderlyByName(this.searchName.trim())
        if (response.data.length === 0) {
          this.queryError = '未找到匹配的老人'
          this.queryResult = null
        } else if (response.data.length === 1) {
          this.queryResult = response.data[0]
        } else {
          // 如果有多个结果，显示第一个，但可以考虑后续扩展为列表显示
          this.queryResult = response.data[0]
          console.log('找到多个结果:', response.data)
        }
      } catch (error) {
        this.queryError = error.message || '搜索失败'
        this.queryResult = null
      }
    },

    async loadAllElderly() {
      this.elderlyListState.loading = true
      this.elderlyListState.error = null

      try {
        const response = await getAllElderly()
        const all = Array.isArray(response.data) ? response.data : []
        this.elderlyListState.data = this.isWorker ? all.filter(e => e && e.isActive) : all
      } catch (error) {
        this.elderlyListState.error = error.message || '加载失败'
      } finally {
        this.elderlyListState.loading = false
      }
    },

    async handleActivateElderly(id) {
      try {
        await activateElderly(id)
        alert('激活成功！')
        await this.loadAllElderly()
        if (this.queryResult && this.queryResult.id === id) {
          this.queryResult.isActive = true
        }
      } catch (error) {
        alert('激活失败: ' + (error.message || '未知错误'))
      }
    },

    async handleDeactivateElderly(id) {
      try {
        await deactivateElderly(id)
        alert('停用成功！')
        await this.loadAllElderly()
        if (this.queryResult && this.queryResult.id === id) {
          this.queryResult.isActive = false
        }
      } catch (error) {
        alert('停用失败: ' + (error.message || '未知错误'))
      }
    },

    async handleDeleteElderly(id) {
      if (!confirm('确定要删除这个老人信息吗？')) {
        return
      }

      try {
        await deleteElderly(id)
        alert('删除成功！')
        await this.loadAllElderly()
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
.elderly-manager h3 {
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