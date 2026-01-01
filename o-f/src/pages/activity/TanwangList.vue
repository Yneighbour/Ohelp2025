<template>
  <div class="page">
    <SiteHeader />
    <main class="container">
      <h1>家属探望管理</h1>
      <p>家属探望登记</p>
      
      <!-- Advanced Filter Section -->
      <div class="filter-section advanced-filter">
        <h3>高级筛选</h3>
        <div class="filter-grid">
          <!-- Visitor Name/Phone -->
          <div class="filter-item">
            <label for="visitor-input">访客姓名/手机：</label>
            <input 
              id="visitor-input" 
              type="text" 
              v-model="filterParams.visitor" 
              placeholder="输入访客姓名或手机号码" 
              @input="handleSingleFilter"
            >
          </div>
          
          <!-- Employee Dropdown -->
          <div class="filter-item">
            <label for="employee-select">员工：</label>
            <select 
              id="employee-select" 
              v-model="filterParams.employeeId"
              @change="handleSingleFilter"
            >
              <option value="">全部员工</option>
              <option v-for="employee in employees" :key="employee.id" :value="employee.id">
                {{ employee.name }}
              </option>
            </select>
          </div>
          
          <!-- Visit Status -->
          <div class="filter-item">
            <label for="status-select">探访状态：</label>
            <select 
              id="status-select" 
              v-model="filterParams.status"
              @change="handleSingleFilter"
            >
              <option value="">全部状态</option>
              <option value="0">未完成</option>
              <option value="1">已完成</option>
              <option value="2">已取消</option>
            </select>
          </div>
          
          <!-- Time Range -->
          <div class="filter-item">
            <label>时间范围：</label>
            <div class="time-range">
              <input 
                type="date" 
                v-model="filterParams.startTime"
                @change="handleSingleFilter"
              >
              <span class="time-separator">至</span>
              <input 
                type="date" 
                v-model="filterParams.endTime"
                @change="handleSingleFilter"
              >
            </div>
          </div>
        </div>
        
        <div class="filter-actions">
          <button class="btn btn-primary" @click="handleQuery">查询</button>
          <button class="btn btn-secondary" @click="handleReset">重置</button>
        </div>
        
        <!-- Filter Restriction Hint -->
        <div class="filter-hint">
          <span class="hint-icon">⚠️</span>
          <span>提示：当前仅支持单条件筛选</span>
        </div>
      </div>
      
      <!-- Visit List -->
      <div class="table-container">
        <table class="visit-table">
          <thead>
            <tr>
              <th>探访ID</th>
              <th>老人姓名</th>
              <th>访客姓名</th>
              <th>访客手机</th>
              <th>探访时间</th>
              <th>探访员工</th>
              <th>探访状态</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="visit in visits" :key="visit.id">
              <td>{{ visit.id }}</td>
              <td>{{ visit.laorenxingming }}</td>
              <td>{{ visit.fangke }}</td>
              <td>{{ visit.shouji }}</td>
              <td>{{ formatDate(visit.tanwangshijian) }}</td>
              <td>{{ visit.yuangong }}</td>
              <td>
                <span class="status-tag" :class="getStatusClass(visit.tanwangzhuangtai)">
                  {{ getStatusText(visit.tanwangzhuangtai) }}
                </span>
              </td>
              <td>
                <button class="btn btn-small btn-edit">编辑</button>
                <button class="btn btn-small btn-delete">删除</button>
              </td>
            </tr>
            <tr v-if="visits.length === 0">
              <td colspan="8" class="empty-state">
                <div class="empty-content">
                  <div class="empty-icon">📋</div>
                  <div class="empty-text">暂无探访记录</div>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </main>
    <SiteFooter />
  </div>
</template>

<script>
import { ref, onMounted, computed } from 'vue'
import SiteHeader from '../components/Header.vue'
import SiteFooter from '../components/Footer.vue'
import { 
  getVisitList, 
  getVisitByVisitor, 
  getVisitByStatus, 
  getVisitByEmployee, 
  getVisitByTimeRange 
} from '../../api/activity/index.js'
import { getWorkerList } from '../../api/worker/index.js'

export default {
  components: { SiteHeader, SiteFooter },
  setup() {
    const visits = ref([])
    const employees = ref([])
    
    // Filter parameters
    const filterParams = ref({
      visitor: '',
      employeeId: '',
      status: '',
      startTime: '',
      endTime: ''
    })
    
    // Fetch all visits
    const fetchVisits = async () => {
      try {
        const response = await getVisitList()
        if (response.success) {
          visits.value = response.data
        }
      } catch (error) {
        console.error('Failed to fetch visits:', error)
      }
    }
    
    // Fetch employees from real API
    const fetchEmployees = async () => {
      try {
        const response = await getWorkerList()
        if (response.success) {
          // Map the response data to match our expected format
          employees.value = response.data.map(worker => ({
            id: worker.id,
            name: worker.name || worker.gonghao // Use appropriate field from API response
          }))
        }
      } catch (error) {
        console.error('Failed to fetch employees:', error)
        // Fallback to mock data if API fails
        employees.value = [
          { id: 1, name: '张三' },
          { id: 2, name: '李四' },
          { id: 3, name: '王五' }
        ]
      }
    }
    
    // Get status text
    const getStatusText = (status) => {
      const statusMap = {
        0: '未完成',
        1: '已完成',
        2: '已取消'
      }
      return statusMap[status] || '未知'
    }
    
    // Get status class
    const getStatusClass = (status) => {
      const classMap = {
        0: 'pending',
        1: 'completed',
        2: 'cancelled'
      }
      return classMap[status] || ''
    }
    
    // Format date
    const formatDate = (dateString) => {
      if (!dateString) return ''
      const date = new Date(dateString)
      return date.toLocaleString()
    }
    
    // Handle single filter change (only one condition can be active)
    const handleSingleFilter = () => {
      // Count active filters
      const activeFilters = Object.values(filterParams.value).filter(v => v !== '').length
      
      // If more than one filter is active, reset others
      if (activeFilters > 1) {
        // Find which filter was just changed
        // For simplicity, we'll reset all except the last changed one
        // In a real app, you'd track which one was changed last
        alert('当前仅支持单条件筛选，请先重置其他条件')
      }
    }
    
    // Handle query
    const handleQuery = async () => {
      try {
        let response
        
        // Determine which filter to use (only one at a time)
        if (filterParams.value.visitor) {
          response = await getVisitByVisitor(filterParams.value.visitor)
        } else if (filterParams.value.status) {
          response = await getVisitByStatus(filterParams.value.status)
        } else if (filterParams.value.employeeId) {
          response = await getVisitByEmployee(filterParams.value.employeeId)
        } else if (filterParams.value.startTime && filterParams.value.endTime) {
          response = await getVisitByTimeRange(filterParams.value.startTime, filterParams.value.endTime)
        } else {
          // No filters, fetch all
          response = await getVisitList()
        }
        
        if (response.success) {
          visits.value = response.data
        }
      } catch (error) {
        console.error('Failed to query visits:', error)
      }
    }
    
    // Handle reset
    const handleReset = () => {
      filterParams.value = {
        visitor: '',
        employeeId: '',
        status: '',
        startTime: '',
        endTime: ''
      }
      fetchVisits()
    }
    
    // Initial fetch
    onMounted(() => {
      fetchVisits()
      fetchEmployees()
    })
    
    return {
      visits,
      employees,
      filterParams,
      handleQuery,
      handleReset,
      handleSingleFilter,
      getStatusText,
      getStatusClass,
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

/* Advanced Filter Styles */
.filter-section {
  margin: 20px 0;
  padding: 20px;
  background-color: #f5f5f5;
  border-radius: 8px;
}

.filter-section h3 {
  margin-top: 0;
  margin-bottom: 15px;
  color: #303133;
}

.filter-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
  margin-bottom: 20px;
}

.filter-item {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.filter-item label {
  font-weight: bold;
  font-size: 14px;
  color: #606266;
}

.filter-item input,
.filter-item select {
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
}

.time-range {
  display: flex;
  align-items: center;
  gap: 10px;
}

.time-separator {
  color: #909399;
}

.filter-actions {
  display: flex;
  gap: 10px;
  margin-bottom: 15px;
}

.filter-hint {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px;
  background-color: #fff3cd;
  border: 1px solid #ffeeba;
  border-radius: 4px;
  color: #856404;
  font-size: 14px;
}

.hint-icon {
  font-size: 16px;
}

/* Button Styles */
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

/* Table Styles */
.table-container {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.visit-table {
  width: 100%;
  border-collapse: collapse;
}

.visit-table th,
.visit-table td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #ebeef5;
}

.visit-table th {
  background-color: #f5f7fa;
  font-weight: bold;
  color: #303133;
}

.visit-table tr:hover {
  background-color: #f5f7fa;
}

/* Status Tag Styles */
.status-tag {
  padding: 2px 8px;
  border-radius: 10px;
  font-size: 12px;
  font-weight: bold;
}

.status-tag.pending {
  background-color: #ecf5ff;
  color: #409eff;
  border: 1px solid #d9ecff;
}

.status-tag.completed {
  background-color: #f0f9eb;
  color: #67c23a;
  border: 1px solid #e1f3d8;
}

.status-tag.cancelled {
  background-color: #fef0f0;
  color: #f56c6c;
  border: 1px solid #fbc4c4;
}

/* Empty State Styles */
.empty-state {
  text-align: center;
  padding: 60px 0;
  color: #909399;
}

.empty-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 15px;
}

.empty-icon {
  font-size: 48px;
}

.empty-text {
  font-size: 16px;
  color: #909399;
}
</style>