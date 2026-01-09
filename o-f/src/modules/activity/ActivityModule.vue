<template>
  <div class="activity-module">
    <div class="card">
      <h2>活动管理模块</h2>

      <!-- 创建活动表单 -->
      <div class="card">
        <h3>创建活动</h3>
        <form @submit.prevent="handleCreateActivity" class="form">
          <div class="form-row">
            <div class="form-group">
              <label for="activityName">活动名称 *</label>
              <input
                id="activityName"
                v-model="createForm.name"
                type="text"
                required
              />
            </div>
            <div class="form-group">
              <label for="activityCategory">活动分类 *</label>
              <select id="activityCategory" v-model="createForm.category" required>
                <option value="">请选择分类</option>
                <option value="娱乐">娱乐</option>
                <option value="健身">健身</option>
                <option value="学习">学习</option>
                <option value="社交">社交</option>
                <option value="其他">其他</option>
              </select>
            </div>
          </div>

          <div class="form-group">
            <label for="activityDescription">活动描述</label>
            <textarea
              id="activityDescription"
              v-model="createForm.description"
              rows="3"
              placeholder="请输入活动描述..."
            ></textarea>
          </div>

          <div class="form-row">
            <div class="form-group">
              <label for="activityLocation">活动地点 *</label>
              <input
                id="activityLocation"
                v-model="createForm.location"
                type="text"
                required
              />
            </div>
            <div class="form-group">
              <label for="activityParticipants">参与人数</label>
              <input
                id="activityParticipants"
                v-model.number="createForm.participants"
                type="number"
                min="1"
              />
            </div>
          </div>

          <div class="form-row">
            <div class="form-group">
              <label for="activityStartTime">开始时间 *</label>
              <input
                id="activityStartTime"
                v-model="createForm.startTime"
                type="datetime-local"
                required
              />
            </div>
            <div class="form-group">
              <label for="activityEndTime">结束时间 *</label>
              <input
                id="activityEndTime"
                v-model="createForm.endTime"
                type="datetime-local"
                required
              />
            </div>
          </div>

          <div class="form-row">
            <div class="form-group">
              <label for="activityOrganizerId">组织者ID *</label>
              <input
                id="activityOrganizerId"
                v-model.number="createForm.organizerId"
                type="number"
                required
              />
            </div>
            <div class="form-group">
              <label for="activityStatus">活动状态 *</label>
              <select id="activityStatus" v-model="createForm.status" required>
                <option value="">请选择状态</option>
                <option value="planning">规划中</option>
                <option value="active">进行中</option>
                <option value="completed">已完成</option>
                <option value="cancelled">已取消</option>
              </select>
            </div>
          </div>

          <button type="submit" class="btn btn-success" :disabled="createState.loading">
            {{ createState.loading ? '创建中...' : '创建活动' }}
          </button>
          <p v-if="createState.error" class="error">{{ createState.error }}</p>
        </form>
      </div>

      <!-- 活动查询 -->
      <div class="card">
        <h3>活动查询</h3>
        <div class="query-section">
          <div class="form-group">
            <label for="searchActivityId">按ID查询</label>
            <input
              id="searchActivityId"
              v-model="searchId"
              type="number"
              placeholder="输入活动ID"
            />
            <button @click="handleGetActivityById" class="btn btn-secondary">查询</button>
          </div>

          <div class="form-group">
            <label for="searchActivityCategory">按分类查询</label>
            <select id="searchActivityCategory" v-model="searchCategory">
              <option value="">选择分类</option>
              <option value="娱乐">娱乐</option>
              <option value="健身">健身</option>
              <option value="学习">学习</option>
              <option value="社交">社交</option>
              <option value="其他">其他</option>
            </select>
            <button @click="handleSearchByCategory" class="btn btn-secondary">查询</button>
          </div>

          <div class="form-group">
            <label for="searchActivityStatus">按状态查询</label>
            <select id="searchActivityStatus" v-model="searchStatus">
              <option value="">选择状态</option>
              <option value="planning">规划中</option>
              <option value="active">进行中</option>
              <option value="completed">已完成</option>
              <option value="cancelled">已取消</option>
            </select>
            <button @click="handleSearchByStatus" class="btn btn-secondary">查询</button>
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
                <td><strong>活动名称</strong></td>
                <td>{{ queryResult.name }}</td>
              </tr>
              <tr>
                <td><strong>分类</strong></td>
                <td>{{ queryResult.category }}</td>
              </tr>
              <tr>
                <td><strong>地点</strong></td>
                <td>{{ queryResult.location }}</td>
              </tr>
              <tr>
                <td><strong>开始时间</strong></td>
                <td>{{ queryResult.startTime }}</td>
              </tr>
              <tr>
                <td><strong>结束时间</strong></td>
                <td>{{ queryResult.endTime }}</td>
              </tr>
              <tr>
                <td><strong>参与人数</strong></td>
                <td>{{ queryResult.participants }}</td>
              </tr>
              <tr>
                <td><strong>组织者ID</strong></td>
                <td>{{ queryResult.organizerId }}</td>
              </tr>
              <tr>
                <td><strong>状态</strong></td>
                <td>{{ queryResult.status }}</td>
              </tr>
              <tr>
                <td><strong>激活状态</strong></td>
                <td>
                  <span :class="queryResult.isActive ? 'status status-active' : 'status status-inactive'">
                    {{ queryResult.isActive ? '激活' : '未激活' }}
                  </span>
                </td>
              </tr>
              <tr v-if="queryResult.description">
                <td><strong>描述</strong></td>
                <td>{{ queryResult.description }}</td>
              </tr>
            </tbody>
          </table>
          <div class="actions">
            <button @click="handleActivateActivity(queryResult.id)" class="btn btn-success btn-sm" :disabled="!queryResult.isActive">激活</button>
            <button @click="handleDeactivateActivity(queryResult.id)" class="btn btn-warning btn-sm" :disabled="queryResult.isActive">停用</button>
            <button @click="handleDeleteActivity(queryResult.id)" class="btn btn-danger btn-sm">删除</button>
          </div>
        </div>
        <p v-if="queryError" class="error">{{ queryError }}</p>
      </div>

      <!-- 所有活动列表 -->
      <div class="card">
        <h3>所有活动</h3>
        <button @click="loadAllActivities" class="btn btn-primary" :disabled="activityListState.loading">
          {{ activityListState.loading ? '加载中...' : '加载所有活动' }}
        </button>
        <div v-if="activityListState.error" class="error">{{ activityListState.error }}</div>
        <div v-if="activityListState.data.length > 0" class="table-container">
          <table class="table">
            <thead>
              <tr>
                <th>ID</th>
                <th>活动名称</th>
                <th>分类</th>
                <th>地点</th>
                <th>开始时间</th>
                <th>参与人数</th>
                <th>状态</th>
                <th>激活状态</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="activity in activityListState.data" :key="activity.id">
                <td>{{ activity.id }}</td>
                <td>{{ activity.name }}</td>
                <td>{{ activity.category }}</td>
                <td>{{ activity.location }}</td>
                <td>{{ activity.startTime }}</td>
                <td>{{ activity.participants }}</td>
                <td>{{ activity.status }}</td>
                <td>
                  <span :class="activity.isActive ? 'status status-active' : 'status status-inactive'">
                    {{ activity.isActive ? '激活' : '未激活' }}
                  </span>
                </td>
                <td>
                  <button @click="handleActivateActivity(activity.id)" class="btn btn-success btn-sm" :disabled="!activity.isActive">激活</button>
                  <button @click="handleDeactivateActivity(activity.id)" class="btn btn-warning btn-sm" :disabled="activity.isActive">停用</button>
                  <button @click="handleDeleteActivity(activity.id)" class="btn btn-danger btn-sm">删除</button>
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
  createActivity,
  getActivity,
  getAllActivities,
  getActivitiesByCategory,
  getActivitiesByStatus,
  updateActivity,
  deleteActivity,
  activateActivity,
  deactivateActivity
} from './activity.api.js'

export default {
  name: 'ActivityModule',
  data() {
    return {
      // 创建表单
      createForm: {
        name: '',
        category: '',
        description: '',
        location: '',
        startTime: '',
        endTime: '',
        participants: null,
        organizerId: null,
        status: ''
      },
      createState: {
        loading: false,
        error: null
      },

      // 查询相关
      searchId: '',
      searchCategory: '',
      searchStatus: '',
      queryResult: null,
      queryError: null,

      // 活动列表
      activityListState: {
        loading: false,
        data: [],
        error: null
      }
    }
  },
  methods: {
    async handleCreateActivity() {
      this.createState.loading = true
      this.createState.error = null

      try {
        const response = await createActivity(this.createForm)
        console.log('创建活动成功:', response.data)
        alert('创建活动成功！')
        // 清空表单
        this.createForm = {
          name: '',
          category: '',
          description: '',
          location: '',
          startTime: '',
          endTime: '',
          participants: null,
          organizerId: null,
          status: ''
        }
        // 重新加载列表
        if (this.activityListState.data.length > 0) {
          await this.loadAllActivities()
        }
      } catch (error) {
        this.createState.error = error.message || '创建失败'
      } finally {
        this.createState.loading = false
      }
    },

    async handleGetActivityById() {
      if (!this.searchId) {
        alert('请输入活动ID')
        return
      }

      this.queryError = null
      try {
        const response = await getActivity(parseInt(this.searchId))
        this.queryResult = response.data
      } catch (error) {
        this.queryError = error.message || '查询失败'
        this.queryResult = null
      }
    },

    async handleSearchByCategory() {
      if (!this.searchCategory) {
        alert('请选择活动分类')
        return
      }

      this.queryError = null
      try {
        const response = await getActivitiesByCategory(this.searchCategory)
        if (response.data.length === 0) {
          this.queryError = '该分类下暂无活动'
          this.queryResult = null
        } else {
          this.queryResult = response.data[0] // 显示第一个结果
        }
      } catch (error) {
        this.queryError = error.message || '搜索失败'
        this.queryResult = null
      }
    },

    async handleSearchByStatus() {
      if (!this.searchStatus) {
        alert('请选择活动状态')
        return
      }

      this.queryError = null
      try {
        const response = await getActivitiesByStatus(this.searchStatus)
        if (response.data.length === 0) {
          this.queryError = '该状态下暂无活动'
          this.queryResult = null
        } else {
          this.queryResult = response.data[0] // 显示第一个结果
        }
      } catch (error) {
        this.queryError = error.message || '搜索失败'
        this.queryResult = null
      }
    },

    async loadAllActivities() {
      this.activityListState.loading = true
      this.activityListState.error = null

      try {
        const response = await getAllActivities()
        this.activityListState.data = response.data
      } catch (error) {
        this.activityListState.error = error.message || '加载失败'
      } finally {
        this.activityListState.loading = false
      }
    },

    async handleActivateActivity(id) {
      try {
        await activateActivity(id)
        alert('激活成功！')
        await this.loadAllActivities()
        if (this.queryResult && this.queryResult.id === id) {
          this.queryResult.isActive = true
        }
      } catch (error) {
        alert('激活失败: ' + (error.message || '未知错误'))
      }
    },

    async handleDeactivateActivity(id) {
      try {
        await deactivateActivity(id)
        alert('停用成功！')
        await this.loadAllActivities()
        if (this.queryResult && this.queryResult.id === id) {
          this.queryResult.isActive = false
        }
      } catch (error) {
        alert('停用失败: ' + (error.message || '未知错误'))
      }
    },

    async handleDeleteActivity(id) {
      if (!confirm('确定要删除这个活动吗？')) {
        return
      }

      try {
        await deleteActivity(id)
        alert('删除成功！')
        await this.loadAllActivities()
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
.activity-module {
  padding: 1rem 0;
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