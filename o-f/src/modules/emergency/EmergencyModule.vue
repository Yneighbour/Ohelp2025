<template>
  <div class="emergency-module">
    <div class="card">
      <h2>紧急求助模块</h2>
      <p class="hint">{{ roleSubtitle }}</p>

      <!-- 创建求助请求表单 -->
      <div v-if="!isWorker" class="card">
        <h3>创建求助请求</h3>
        <form @submit.prevent="handleCreateEmergency" class="form">
          <div class="form-row">
            <div class="form-group">
              <label for="emergencyElderlyId">老人ID *</label>
              <input
                id="emergencyElderlyId"
                v-model.number="createForm.elderlyId"
                type="number"
                required
              />
            </div>
            <div class="form-group">
              <label for="emergencyType">求助类型 *</label>
              <select id="emergencyType" v-model="createForm.type" required>
                <option value="">请选择类型</option>
                <option value="医疗">医疗</option>
                <option value="跌倒">跌倒</option>
                <option value="走失">走失</option>
                <option value="其他">其他</option>
              </select>
            </div>
          </div>

          <div class="form-group">
            <label for="emergencyDescription">求助描述 *</label>
            <textarea
              id="emergencyDescription"
              v-model="createForm.description"
              rows="3"
              required
              placeholder="详细描述求助情况..."
            ></textarea>
          </div>

          <div class="form-row">
            <div class="form-group">
              <label for="emergencyLocation">求助位置 *</label>
              <input
                id="emergencyLocation"
                v-model="createForm.location"
                type="text"
                required
              />
            </div>
            <div class="form-group">
              <label for="emergencyContactPhone">联系电话 *</label>
              <input
                id="emergencyContactPhone"
                v-model="createForm.contactPhone"
                type="tel"
                required
              />
            </div>
          </div>

          <div class="form-group">
            <label for="emergencyPriority">优先级 *</label>
            <select id="emergencyPriority" v-model="createForm.priority" required>
              <option value="">请选择优先级</option>
              <option value="high">高</option>
              <option value="medium">中</option>
              <option value="low">低</option>
            </select>
          </div>

          <button type="submit" class="btn btn-danger" :disabled="createState.loading">
            {{ createState.loading ? '创建中...' : '发出求助请求' }}
          </button>
          <p v-if="createState.error" class="error">{{ createState.error }}</p>
        </form>
      </div>

      <!-- 求助查询 -->
      <div v-if="!isWorker" class="card">
        <h3>求助查询</h3>
        <div class="query-section">
          <div class="form-group">
            <label for="searchEmergencyId">按ID查询</label>
            <input
              id="searchEmergencyId"
              v-model="searchId"
              type="number"
              placeholder="输入求助ID"
            />
            <button @click="handleGetEmergencyById" class="btn btn-secondary">查询</button>
          </div>

          <div class="form-group">
            <label for="searchEmergencyElderly">按老人ID查询</label>
            <input
              id="searchEmergencyElderly"
              v-model="searchElderlyId"
              type="number"
              placeholder="输入老人ID"
            />
            <button @click="handleGetEmergenciesByElderlyId" class="btn btn-secondary">查询</button>
          </div>

          <div class="form-group">
            <label for="searchEmergencyStatus">按状态查询</label>
            <select id="searchEmergencyStatus" v-model="searchStatus">
              <option value="">选择状态</option>
              <option value="pending">待处理</option>
              <option value="responded">已响应</option>
              <option value="resolved">已解决</option>
            </select>
            <button @click="handleSearchByStatus" class="btn btn-secondary">查询</button>
          </div>

          <div class="form-group">
            <label for="searchEmergencyPriority">按优先级查询</label>
            <select id="searchEmergencyPriority" v-model="searchPriority">
              <option value="">选择优先级</option>
              <option value="high">高</option>
              <option value="medium">中</option>
              <option value="low">低</option>
            </select>
            <button @click="handleSearchByPriority" class="btn btn-secondary">查询</button>
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
                <td><strong>老人ID</strong></td>
                <td>{{ queryResult.elderlyId }}</td>
              </tr>
              <tr>
                <td><strong>求助类型</strong></td>
                <td>{{ queryResult.type }}</td>
              </tr>
              <tr>
                <td><strong>描述</strong></td>
                <td>{{ queryResult.description }}</td>
              </tr>
              <tr>
                <td><strong>位置</strong></td>
                <td>{{ queryResult.location }}</td>
              </tr>
              <tr>
                <td><strong>联系电话</strong></td>
                <td>{{ queryResult.contactPhone }}</td>
              </tr>
              <tr>
                <td><strong>状态</strong></td>
                <td>{{ queryResult.status }}</td>
              </tr>
              <tr>
                <td><strong>优先级</strong></td>
                <td>{{ queryResult.priority }}</td>
              </tr>
              <tr>
                <td><strong>响应人ID</strong></td>
                <td>{{ queryResult.responderId || '未分配' }}</td>
              </tr>
              <tr>
                <td><strong>响应时间</strong></td>
                <td>{{ queryResult.responseTime || '未响应' }}</td>
              </tr>
              <tr>
                <td><strong>解决时间</strong></td>
                <td>{{ queryResult.resolvedTime || '未解决' }}</td>
              </tr>
            </tbody>
          </table>
          <div class="actions">
            <button
              v-if="queryResult.status === 'pending'"
              @click="handleRespondToEmergency(queryResult.id)"
              class="btn btn-warning btn-sm"
            >
              响应求助
            </button>
            <button
              v-if="queryResult.status === 'responded'"
              @click="handleResolveEmergency(queryResult.id)"
              class="btn btn-success btn-sm"
            >
              解决求助
            </button>
            <button v-if="!isWorker" @click="handleDeleteEmergency(queryResult.id)" class="btn btn-danger btn-sm">删除</button>
          </div>
        </div>
        <p v-if="queryError" class="error">{{ queryError }}</p>
      </div>

      <!-- 所有求助列表 -->
      <div class="card">
        <h3>所有求助请求</h3>
        <button @click="loadAllEmergencies" class="btn btn-primary" :disabled="emergencyListState.loading">
          {{ emergencyListState.loading ? '加载中...' : (isWorker ? '加载待处理 / 我的任务' : '加载所有求助') }}
        </button>
        <div v-if="emergencyListState.error" class="error">{{ emergencyListState.error }}</div>
        <div
          v-if="!emergencyListState.loading && !emergencyListState.error && emergencyListState.data.length === 0"
          class="empty"
        >
          {{ listEmptyHint }}
        </div>
        <div v-if="emergencyListState.data.length > 0" class="table-container">
          <table class="table">
            <thead>
              <tr>
                <th>ID</th>
                <th>老人ID</th>
                <th>类型</th>
                <th>位置</th>
                <th>优先级</th>
                <th>状态</th>
                <th>响应人</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="emergency in emergencyListState.data" :key="emergency.id">
                <td>{{ emergency.id }}</td>
                <td>{{ emergency.elderlyId }}</td>
                <td>{{ emergency.type }}</td>
                <td>{{ emergency.location }}</td>
                <td>
                  <span :class="getPriorityClass(emergency.priority)">
                    {{ emergency.priority }}
                  </span>
                </td>
                <td>
                  <span :class="getStatusClass(emergency.status)">
                    {{ emergency.status }}
                  </span>
                </td>
                <td>{{ emergency.responderId || '未分配' }}</td>
                <td>
                  <button
                    v-if="emergency.status === 'pending'"
                    @click="handleRespondToEmergency(emergency.id)"
                    class="btn btn-warning btn-sm"
                  >
                    响应
                  </button>
                  <button
                    v-if="emergency.status === 'responded'"
                    @click="handleResolveEmergency(emergency.id)"
                    class="btn btn-success btn-sm"
                  >
                    解决
                  </button>
                  <button v-if="!isWorker" @click="handleDeleteEmergency(emergency.id)" class="btn btn-danger btn-sm">删除</button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {
  createEmergency,
  getEmergency,
  getAllEmergencies,
  getEmergenciesByElderlyId,
  getEmergenciesByStatus,
  getEmergenciesByPriority,
  updateEmergency,
  deleteEmergency,
  respondToEmergency,
  resolveEmergency
} from './emergency.api.js'

// WORKER 视角规则（显性化，不改变现有行为）：
// 1) 列表默认展示：所有 pending；若已登录且有 userId，则额外展示 responderId==userId 且未 resolved 的记录。
// 2) 响应操作：WORKER 默认使用自身 userId 作为 responderId。

const EMERGENCY_VIEW_RULES = Object.freeze({
  worker: {
    includeAllPending: true,
    includeMyUnresolvedWhenLoggedIn: true,
    unresolvedStatus: 'resolved'
  }
})

export default {
  name: 'EmergencyModule',
  data() {
    return {
      // 创建表单
      createForm: {
        elderlyId: null,
        type: '',
        description: '',
        location: '',
        contactPhone: '',
        priority: ''
      },
      createState: {
        loading: false,
        error: null
      },

      // 查询相关
      searchId: '',
      searchElderlyId: '',
      searchStatus: '',
      searchPriority: '',
      queryResult: null,
      queryError: null,

      // 求助列表
      emergencyListState: {
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
    currentUserId() {
      const raw = localStorage.getItem('userId')
      const parsed = raw ? parseInt(raw, 10) : NaN
      return Number.isFinite(parsed) ? parsed : null
    },
    isAdmin() {
      return this.currentRole === 'ADMIN'
    },
    isWorker() {
      return this.currentRole === 'WORKER'
    },
    roleSubtitle() {
      if (this.isWorker) return '任务执行视角 · 求助处理（前端视角过滤，不做权限控制）'
      if (this.isAdmin) return '系统管理视角 · 全局求助概览（不做权限控制）'
      return '个人使用视角 · 发起求助与查看进度'
    },
    listEmptyHint() {
      if (this.isWorker) return '暂无可执行求助任务，可稍后刷新或前往【处理记录】查看历史处理'
      if (this.isAdmin) return '暂无求助数据，可稍后刷新或引导用户发起求助'
      return '暂无求助记录，可在上方创建求助请求'
    }
  },
  methods: {
    async handleCreateEmergency() {
      this.createState.loading = true
      this.createState.error = null

      try {
        const response = await createEmergency(this.createForm)
        console.log('创建求助成功:', response.data)
        alert('求助请求已发出！')
        // 清空表单
        this.createForm = {
          elderlyId: null,
          type: '',
          description: '',
          location: '',
          contactPhone: '',
          priority: ''
        }
        // 重新加载列表
        if (this.emergencyListState.data.length > 0) {
          await this.loadAllEmergencies()
        }
      } catch (error) {
        this.createState.error = error.message || '创建失败'
      } finally {
        this.createState.loading = false
      }
    },

    async handleGetEmergencyById() {
      if (!this.searchId) {
        alert('请输入求助ID')
        return
      }

      this.queryError = null
      try {
        const response = await getEmergency(parseInt(this.searchId))
        this.queryResult = response.data
      } catch (error) {
        this.queryError = error.message || '查询失败'
        this.queryResult = null
      }
    },

    async handleGetEmergenciesByElderlyId() {
      if (!this.searchElderlyId) {
        alert('请输入老人ID')
        return
      }

      this.queryError = null
      try {
        const response = await getEmergenciesByElderlyId(parseInt(this.searchElderlyId))
        if (response.data.length === 0) {
          this.queryError = '该老人暂无求助记录'
          this.queryResult = null
        } else {
          this.queryResult = response.data[0] // 显示第一个结果
        }
      } catch (error) {
        this.queryError = error.message || '查询失败'
        this.queryResult = null
      }
    },

    async handleSearchByStatus() {
      if (!this.searchStatus) {
        alert('请选择求助状态')
        return
      }

      this.queryError = null
      try {
        const response = await getEmergenciesByStatus(this.searchStatus)
        if (response.data.length === 0) {
          this.queryError = '该状态下暂无求助记录'
          this.queryResult = null
        } else {
          this.queryResult = response.data[0] // 显示第一个结果
        }
      } catch (error) {
        this.queryError = error.message || '搜索失败'
        this.queryResult = null
      }
    },

    async handleSearchByPriority() {
      if (!this.searchPriority) {
        alert('请选择优先级')
        return
      }

      this.queryError = null
      try {
        const response = await getEmergenciesByPriority(this.searchPriority)
        if (response.data.length === 0) {
          this.queryError = '该优先级下暂无求助记录'
          this.queryResult = null
        } else {
          this.queryResult = response.data[0] // 显示第一个结果
        }
      } catch (error) {
        this.queryError = error.message || '搜索失败'
        this.queryResult = null
      }
    },

    async loadAllEmergencies() {
      this.emergencyListState.loading = true
      this.emergencyListState.error = null

      try {
        const response = await getAllEmergencies()
        const all = Array.isArray(response.data) ? response.data : []

        if (!this.isWorker) {
          this.emergencyListState.data = all
          return
        }

        const userId = this.currentUserId
        const filtered = all.filter((e) => {
          if (!e) return false
          if (EMERGENCY_VIEW_RULES.worker.includeAllPending && e.status === 'pending') return true
          if (!userId) return false
          if (!EMERGENCY_VIEW_RULES.worker.includeMyUnresolvedWhenLoggedIn) return false
          return e.responderId === userId && e.status !== EMERGENCY_VIEW_RULES.worker.unresolvedStatus
        })

        const priorityRank = { high: 0, medium: 1, low: 2 }
        const statusRank = { pending: 0, responded: 1, resolved: 2 }

        filtered.sort((a, b) => {
          const sr = (statusRank[a.status] ?? 9) - (statusRank[b.status] ?? 9)
          if (sr !== 0) return sr
          const pr = (priorityRank[a.priority] ?? 9) - (priorityRank[b.priority] ?? 9)
          if (pr !== 0) return pr
          return (b.id ?? 0) - (a.id ?? 0)
        })

        this.emergencyListState.data = filtered
      } catch (error) {
        this.emergencyListState.error = error.message || '加载失败'
      } finally {
        this.emergencyListState.loading = false
      }
    },

    async handleRespondToEmergency(id) {
      try {
        const responderId = (this.isWorker && this.currentUserId)
          ? this.currentUserId
          : parseInt(prompt('请输入响应人ID:'), 10)
        if (!Number.isFinite(responderId)) return

        await respondToEmergency(id, { responderId })
        alert('响应成功！')
        await this.loadAllEmergencies()
        if (this.queryResult && this.queryResult.id === id) {
          this.queryResult.status = 'responded'
          this.queryResult.responderId = responderId
        }
      } catch (error) {
        alert('响应失败: ' + (error.message || '未知错误'))
      }
    },

    async handleResolveEmergency(id) {
      try {
        await resolveEmergency(id)
        alert('解决成功！')
        await this.loadAllEmergencies()
        if (this.queryResult && this.queryResult.id === id) {
          this.queryResult.status = 'resolved'
        }
      } catch (error) {
        alert('解决失败: ' + (error.message || '未知错误'))
      }
    },

    async handleDeleteEmergency(id) {
      if (!confirm('确定要删除这条求助记录吗？')) {
        return
      }

      try {
        await deleteEmergency(id)
        alert('删除成功！')
        await this.loadAllEmergencies()
        if (this.queryResult && this.queryResult.id === id) {
          this.queryResult = null
        }
      } catch (error) {
        alert('删除失败: ' + (error.message || '未知错误'))
      }
    },

    getPriorityClass(priority) {
      switch (priority) {
        case 'high': return 'status-danger'
        case 'medium': return 'status-warning'
        case 'low': return 'status-info'
        default: return 'status-secondary'
      }
    },

    getStatusClass(status) {
      switch (status) {
        case 'pending': return 'status-warning'
        case 'responded': return 'status-info'
        case 'resolved': return 'status-success'
        default: return 'status-secondary'
      }
    }
  }
}
</script>

<style scoped>
.emergency-module {
  padding: 1rem 0;
}

.hint {
  margin-top: 6px;
  color: #64748b;
}

.empty {
  margin-top: 10px;
  color: #64748b;
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
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 1rem;
  margin-bottom: 1rem;
}

.query-section .form-group {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.query-section .form-group input,
.query-section .form-group select {
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
  flex-wrap: wrap;
}

.table-container {
  overflow-x: auto;
  margin-top: 1rem;
}

.btn-sm {
  padding: 0.25rem 0.5rem;
  font-size: 0.875rem;
}

.status-danger {
  background-color: #f8d7da;
  color: #721c24;
  padding: 0.25rem 0.5rem;
  border-radius: 4px;
  font-size: 0.875rem;
}

.status-warning {
  background-color: #fff3cd;
  color: #856404;
  padding: 0.25rem 0.5rem;
  border-radius: 4px;
  font-size: 0.875rem;
}

.status-info {
  background-color: #d1ecf1;
  color: #0c5460;
  padding: 0.25rem 0.5rem;
  border-radius: 4px;
  font-size: 0.875rem;
}

.status-success {
  background-color: #d4edda;
  color: #155724;
  padding: 0.25rem 0.5rem;
  border-radius: 4px;
  font-size: 0.875rem;
}

.status-secondary {
  background-color: #e2e3e5;
  color: #383d41;
  padding: 0.25rem 0.5rem;
  border-radius: 4px;
  font-size: 0.875rem;
}

.error {
  color: #dc3545;
  margin-top: 0.5rem;
}
</style>