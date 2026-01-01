<template>
  <div class="page">
    <SiteHeader />
    <main class="container">
      <h1>活动信息管理</h1>
      <p>管理活动详情（名称、类别、地点、时间、费用、详情等）</p>
      
      <!-- Status Filter -->
      <div class="filter-section">
        <div class="filter-item">
          <label for="status-select">活动状态：</label>
          <select id="status-select" v-model="selectedStatus">
            <option value="">全部</option>
            <option value="0">未启用</option>
            <option value="1">启用</option>
          </select>
        </div>
        <div class="filter-actions">
          <button class="btn btn-primary" @click="handleQuery">查询</button>
          <button class="btn btn-secondary" @click="handleReset">重置</button>
        </div>
      </div>
      
      <!-- Activity List -->
      <div class="table-container">
        <table class="activity-table">
          <thead>
            <tr>
              <th>活动ID</th>
              <th>活动名称</th>
              <th>活动类别</th>
              <th>活动地点</th>
              <th>活动时间</th>
              <th>活动费用</th>
              <th>活动状态</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="activity in activities" :key="activity.id">
              <td>{{ activity.id }}</td>
              <td>{{ activity.huodongmingcheng }}</td>
              <td>{{ activity.huodongfenlei }}</td>
              <td>{{ activity.huodongdidian }}</td>
              <td>{{ formatDate(activity.huodongshijian) }}</td>
              <td>{{ activity.huodongfeiyong }}</td>
              <td>
                <span class="status-tag" :class="activity.huodongYesno === 1 ? 'active' : 'inactive'">
                  {{ activity.huodongYesno === 1 ? '启用' : '未启用' }}
                </span>
              </td>
              <td>
                <button class="btn btn-small btn-edit">编辑</button>
                <button class="btn btn-small btn-delete">删除</button>
              </td>
            </tr>
            <tr v-if="activities.length === 0">
              <td colspan="8" class="empty-state">暂无活动数据</td>
            </tr>
          </tbody>
        </table>
      </div>
    </main>
    <SiteFooter />
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import SiteHeader from '../components/Header.vue'
import SiteFooter from '../components/Footer.vue'
import { getActivityInfoList, getActivityInfoByStatus } from '../../api/activity/index.js'

export default {
  components: { SiteHeader, SiteFooter },
  setup() {
    const activities = ref([])
    const selectedStatus = ref('')
    
    // Fetch all activities
    const fetchActivities = async () => {
      try {
        const response = await getActivityInfoList()
        if (response.success) {
          activities.value = response.data
        }
      } catch (error) {
        console.error('Failed to fetch activities:', error)
      }
    }
    
    // Handle query by status
    const handleQuery = async () => {
      try {
        if (selectedStatus.value === '') {
          // Fetch all activities if no status selected
          await fetchActivities()
        } else {
          // Fetch activities by status
          const response = await getActivityInfoByStatus(selectedStatus.value)
          if (response.success) {
            activities.value = response.data
          }
        }
      } catch (error) {
        console.error('Failed to query activities:', error)
      }
    }
    
    // Handle reset
    const handleReset = () => {
      selectedStatus.value = ''
      fetchActivities()
    }
    
    // Format date
    const formatDate = (dateString) => {
      if (!dateString) return ''
      const date = new Date(dateString)
      return date.toLocaleString()
    }
    
    // Initial fetch
    onMounted(() => {
      fetchActivities()
    })
    
    return {
      activities,
      selectedStatus,
      handleQuery,
      handleReset,
      formatDate
    }
  }
}
</script>

<style scoped>
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.filter-section {
  display: flex;
  align-items: center;
  gap: 20px;
  margin: 20px 0;
  padding: 15px;
  background-color: #f5f5f5;
  border-radius: 8px;
}

.filter-item {
  display: flex;
  align-items: center;
  gap: 10px;
}

.filter-item label {
  font-weight: bold;
}

.filter-item select {
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.filter-actions {
  display: flex;
  gap: 10px;
}

.btn {
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  transition: background-color 0.3s;
}

.btn-primary {
  background-color: #409eff;
  color: white;
}

.btn-primary:hover {
  background-color: #66b1ff;
}

.btn-secondary {
  background-color: #909399;
  color: white;
}

.btn-secondary:hover {
  background-color: #a6a9ad;
}

.btn-small {
  padding: 4px 8px;
  font-size: 12px;
  margin-right: 5px;
}

.btn-edit {
  background-color: #67c23a;
  color: white;
}

.btn-edit:hover {
  background-color: #85ce61;
}

.btn-delete {
  background-color: #f56c6c;
  color: white;
}

.btn-delete:hover {
  background-color: #f78989;
}

.table-container {
  overflow-x: auto;
}

.activity-table {
  width: 100%;
  border-collapse: collapse;
  background-color: white;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.activity-table th, .activity-table td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #ebeef5;
}

.activity-table th {
  background-color: #f5f7fa;
  font-weight: bold;
  color: #303133;
}

.activity-table tr:hover {
  background-color: #f5f7fa;
}

.status-tag {
  padding: 2px 8px;
  border-radius: 10px;
  font-size: 12px;
  font-weight: bold;
}

.status-tag.active {
  background-color: #ecf5ff;
  color: #409eff;
  border: 1px solid #d9ecff;
}

.status-tag.inactive {
  background-color: #fef0f0;
  color: #f56c6c;
  border: 1px solid #fbc4c4;
}

.empty-state {
  text-align: center;
  padding: 40px 0;
  color: #909399;
}
</style>