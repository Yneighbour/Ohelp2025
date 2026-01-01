<template>
  <div class="page">
    <SiteHeader />
    <main class="container">
      <h1>老人详情</h1>
      
      <!-- Tab Navigation -->
      <div class="tab-navigation">
        <button 
          v-for="tab in tabs" 
          :key="tab.key" 
          :class="['tab-btn', { active: activeTab === tab.key }]"
          @click="activeTab = tab.key"
        >
          {{ tab.label }}
        </button>
      </div>
      
      <!-- Tab Content -->
      <div class="tab-content">
        <!-- 基本信息 Tab -->
        <div v-if="activeTab === 'basic'" class="tab-pane">
          <h2>基本信息</h2>
          <div class="info-grid">
            <div class="info-item">
              <span class="label">老人姓名：</span>
              <span class="value">{{ elderly.name }}</span>
            </div>
            <div class="info-item">
              <span class="label">年龄：</span>
              <span class="value">{{ elderly.age }}</span>
            </div>
            <div class="info-item">
              <span class="label">房间号：</span>
              <span class="value">{{ elderly.roomNumber }}</span>
            </div>
            <div class="info-item">
              <span class="label">护理等级：</span>
              <span class="value">{{ elderly.careLevel }}</span>
            </div>
            <div class="info-item">
              <span class="label">联系电话：</span>
              <span class="value">{{ elderly.phone }}</span>
            </div>
            <div class="info-item">
              <span class="label">入住时间：</span>
              <span class="value">{{ formatDate(elderly.checkinDate) }}</span>
            </div>
          </div>
        </div>
        
        <!-- 健康档案 Tab -->
        <div v-if="activeTab === 'health'" class="tab-pane">
          <h2>健康档案</h2>
          <div class="health-info">
            <p>健康档案内容示例</p>
          </div>
        </div>
        
        <!-- 服务请求 Tab -->
        <div v-if="activeTab === 'serviceRequests'" class="tab-pane">
          <h2>服务请求</h2>
          <div class="service-requests-list">
            <table class="request-table">
              <thead>
                <tr>
                  <th>请求ID</th>
                  <th>服务类型</th>
                  <th>请求内容</th>
                  <th>请求时间</th>
                  <th>请求状态</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="request in serviceRequests" :key="request.id">
                  <td>{{ request.id }}</td>
                  <td>{{ request.serviceType }}</td>
                  <td>{{ request.content }}</td>
                  <td>{{ formatDate(request.createTime) }}</td>
                  <td>
                    <span class="status-tag" :class="getRequestStatusClass(request.status)">
                      {{ getRequestStatusText(request.status) }}
                    </span>
                  </td>
                  <td>
                    <button class="btn btn-small btn-view">查看</button>
                  </td>
                </tr>
                <tr v-if="serviceRequests.length === 0">
                  <td colspan="6" class="empty-state">
                    <div class="empty-content">
                      <div class="empty-icon">📋</div>
                      <div class="empty-text">暂无服务请求</div>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </main>
    <SiteFooter />
  </div>
</template>

<script>
import { ref, onMounted, watch } from 'vue'
import SiteHeader from '../components/Header.vue'
import SiteFooter from '../components/Footer.vue'
import { getServiceRequestsByElder } from '../../api/serviceorder/index.js'

export default {
  components: { SiteHeader, SiteFooter },
  setup() {
    const activeTab = ref('basic')
    const elderly = ref({
      id: 1,
      name: '张三',
      age: 85,
      roomNumber: '302',
      careLevel: '一级护理',
      phone: '13800138000',
      checkinDate: '2023-01-15'
    })
    const serviceRequests = ref([])
    const isLoading = ref(false)
    
    // Tab configuration
    const tabs = [
      { key: 'basic', label: '基本信息' },
      { key: 'health', label: '健康档案' },
      { key: 'serviceRequests', label: '服务请求' }
    ]
    
    // Format date
    const formatDate = (dateString) => {
      if (!dateString) return ''
      const date = new Date(dateString)
      return date.toLocaleDateString()
    }
    
    // Get request status text
    const getRequestStatusText = (status) => {
      const statusMap = {
        0: '待处理',
        1: '已完成',
        2: '已取消'
      }
      return statusMap[status] || '未知'
    }
    
    // Get request status class
    const getRequestStatusClass = (status) => {
      const classMap = {
        0: 'pending',
        1: 'completed',
        2: 'cancelled'
      }
      return classMap[status] || ''
    }
    
    // Fetch service requests by elder ID (lazy load when tab is activated)
    const fetchServiceRequests = async () => {
      isLoading.value = true
      try {
        const response = await getServiceRequestsByElder(elderly.value.id)
        if (response.success) {
          serviceRequests.value = response.data
        }
      } catch (error) {
        console.error('Failed to fetch service requests:', error)
      } finally {
        isLoading.value = false
      }
    }
    
    // Watch for tab changes to implement lazy loading
    watch(activeTab, (newTab) => {
      if (newTab === 'serviceRequests' && serviceRequests.value.length === 0) {
        fetchServiceRequests()
      }
    })
    
    return {
      activeTab,
      tabs,
      elderly,
      serviceRequests,
      isLoading,
      formatDate,
      getRequestStatusText,
      getRequestStatusClass
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

/* Tab Navigation Styles */
.tab-navigation {
  display: flex;
  gap: 10px;
  margin: 20px 0;
  border-bottom: 1px solid #e4e7ed;
}

.tab-btn {
  padding: 10px 20px;
  border: none;
  background: none;
  font-size: 16px;
  color: #606266;
  cursor: pointer;
  position: relative;
  transition: all 0.3s;
}

.tab-btn:hover {
  color: #409eff;
}

.tab-btn.active {
  color: #409eff;
  font-weight: bold;
}

.tab-btn.active::after {
  content: '';
  position: absolute;
  bottom: -1px;
  left: 0;
  width: 100%;
  height: 2px;
  background-color: #409eff;
}

/* Tab Content Styles */
.tab-pane {
  padding: 20px;
  background-color: white;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

/* Info Grid Styles */
.info-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
  margin-top: 20px;
}

.info-item {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 12px;
  background-color: #f5f7fa;
  border-radius: 4px;
}

.label {
  font-weight: bold;
  color: #606266;
  min-width: 100px;
}

.value {
  color: #303133;
}

/* Service Requests List Styles */
.request-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

.request-table th,
.request-table td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #ebeef5;
}

.request-table th {
  background-color: #f5f7fa;
  font-weight: bold;
  color: #303133;
}

.request-table tr:hover {
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

/* Button Styles */
.btn {
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  transition: background-color 0.3s;
}

.btn-small {
  padding: 4px 8px;
  font-size: 12px;
}

.btn-view {
  background-color: #409eff;
  color: white;
}

.btn-view:hover {
  background-color: #66b1ff;
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