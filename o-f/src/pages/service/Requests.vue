<template>
  <div class="page">
    <SiteHeader />
    <main class="container">
      <div class="page-header">
        <h1>服务请求管理</h1>
        <p>高效处理和跟踪老年人的服务请求</p>
        <button class="btn btn-primary">创建服务请求</button>
      </div>
      
      <!-- 筛选和搜索区域 -->
      <div class="card">
        <div class="filter-section">
          <div class="search-box">
            <input type="text" placeholder="搜索请求标题或老人姓名" class="form-control" v-model="searchQuery">
          </div>
          <div class="filter-group">
            <select class="form-control" v-model="filterStatus">
              <option value="">全部状态</option>
              <option value="待处理">待处理</option>
              <option value="处理中">处理中</option>
              <option value="已完成">已完成</option>
              <option value="已取消">已取消</option>
            </select>
          </div>
          <div class="filter-group">
            <select class="form-control" v-model="filterType">
              <option value="">全部类型</option>
              <option value="生活护理">生活护理</option>
              <option value="医疗护理">医疗护理</option>
              <option value="康复训练">康复训练</option>
              <option value="心理疏导">心理疏导</option>
              <option value="其他服务">其他服务</option>
            </select>
          </div>
        </div>
      </div>
      
      <!-- 请求列表 -->
      <div class="card">
        <table class="table">
          <thead>
            <tr>
              <th>请求ID</th>
              <th>请求标题</th>
              <th>老人姓名</th>
              <th>请求类型</th>
              <th>请求时间</th>
              <th>状态</th>
              <th>优先级</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="request in filteredRequests" :key="request.id" class="table-row">
              <td>{{ request.id }}</td>
              <td class="request-title">{{ request.title }}</td>
              <td>{{ request.elderName }}</td>
              <td>
                <span class="badge badge-info">{{ request.type }}</span>
              </td>
              <td>{{ request.time }}</td>
              <td>
                <span :class="`badge badge-${request.status === '已完成' ? 'success' : request.status === '处理中' ? 'warning' : request.status === '已取消' ? 'danger' : 'info'}`">
                  {{ request.status }}
                </span>
              </td>
              <td>
                <span :class="`badge badge-${request.priority === '高' ? 'danger' : request.priority === '中' ? 'warning' : 'success'}`">
                  {{ request.priority }}
                </span>
              </td>
              <td class="action-buttons">
                <button class="btn btn-info btn-sm" @click="viewRequest(request.id)">查看</button>
                <button class="btn btn-success btn-sm" v-if="request.status === '待处理'" @click="processRequest(request.id)">处理</button>
                <button class="btn btn-warning btn-sm" v-if="request.status === '处理中'" @click="completeRequest(request.id)">完成</button>
                <button class="btn btn-danger btn-sm" v-if="request.status !== '已完成' && request.status !== '已取消'" @click="cancelRequest(request.id)">取消</button>
              </td>
            </tr>
          </tbody>
        </table>
        
        <!-- 空状态 -->
        <div v-if="filteredRequests.length === 0" class="empty-state">
          <div class="empty-icon">📋</div>
          <p>暂无服务请求数据</p>
        </div>
      </div>
      
      <!-- 分页 -->
      <div class="pagination">
        <button class="btn btn-secondary btn-sm" :disabled="currentPage === 1" @click="currentPage--">上一页</button>
        <span class="page-info">第 {{ currentPage }} 页，共 {{ totalPages }} 页</span>
        <button class="btn btn-secondary btn-sm" :disabled="currentPage === totalPages" @click="currentPage++">下一页</button>
      </div>
    </main>
    <SiteFooter />
  </div>
</template>

<script>
import SiteHeader from '../../components/Header.vue'
import SiteFooter from '../../components/Footer.vue'
import { ref, computed } from 'vue'

export default {
  components: { SiteHeader, SiteFooter },
  setup() {
    // 请求数据
    const requests = ref([
      { id: 1, title: '张三老人需要协助洗澡', elderName: '张三', type: '生活护理', time: '2024-01-06 10:30', status: '处理中', priority: '中' },
      { id: 2, title: '李四老人需要测量血压', elderName: '李四', type: '医疗护理', time: '2024-01-06 09:15', status: '已完成', priority: '高' },
      { id: 3, title: '王五老人需要更换床单', elderName: '王五', type: '生活护理', time: '2024-01-06 08:45', status: '待处理', priority: '低' },
      { id: 4, title: '赵六老人需要服药提醒', elderName: '赵六', type: '医疗护理', time: '2024-01-06 07:30', status: '已完成', priority: '中' },
      { id: 5, title: '孙七老人需要康复训练', elderName: '孙七', type: '康复训练', time: '2024-01-05 16:45', status: '待处理', priority: '高' },
      { id: 6, title: '周八老人需要心理疏导', elderName: '周八', type: '心理疏导', time: '2024-01-05 14:30', status: '处理中', priority: '中' },
      { id: 7, title: '吴九老人需要购买物品', elderName: '吴九', type: '其他服务', time: '2024-01-05 11:20', status: '已完成', priority: '低' },
      { id: 8, title: '郑十老人需要协助进食', elderName: '郑十', type: '生活护理', time: '2024-01-05 08:30', status: '已取消', priority: '高' }
    ])
    
    // 筛选和搜索条件
    const searchQuery = ref('')
    const filterStatus = ref('')
    const filterType = ref('')
    
    // 分页
    const currentPage = ref(1)
    const itemsPerPage = ref(5)
    
    // 筛选请求列表
    const filteredRequests = computed(() => {
      return requests.value.filter(request => {
        const matchesSearch = request.title.includes(searchQuery.value) || request.elderName.includes(searchQuery.value)
        const matchesStatus = !filterStatus.value || request.status === filterStatus.value
        const matchesType = !filterType.value || request.type === filterType.value
        return matchesSearch && matchesStatus && matchesType
      })
    })
    
    // 分页计算
    const paginatedRequests = computed(() => {
      const start = (currentPage.value - 1) * itemsPerPage.value
      const end = start + itemsPerPage.value
      return filteredRequests.value.slice(start, end)
    })
    
    const totalPages = computed(() => {
      return Math.ceil(filteredRequests.value.length / itemsPerPage.value)
    })
    
    // 操作方法
    const viewRequest = (id) => {
      console.log('查看请求:', id)
    }
    
    const processRequest = (id) => {
      const request = requests.value.find(r => r.id === id)
      if (request) {
        request.status = '处理中'
      }
    }
    
    const completeRequest = (id) => {
      const request = requests.value.find(r => r.id === id)
      if (request) {
        request.status = '已完成'
      }
    }
    
    const cancelRequest = (id) => {
      if (confirm('确定要取消这个服务请求吗？')) {
        const request = requests.value.find(r => r.id === id)
        if (request) {
          request.status = '已取消'
        }
      }
    }
    
    return {
      requests,
      searchQuery,
      filterStatus,
      filterType,
      currentPage,
      itemsPerPage,
      filteredRequests: paginatedRequests,
      totalPages,
      viewRequest,
      processRequest,
      completeRequest,
      cancelRequest
    }
  }
}
</script>

<style scoped>
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
  flex-wrap: wrap;
  gap: 1rem;
}

.page-header h1 {
  margin-bottom: 0.5rem;
  text-align: left;
}

.page-header p {
  color: #666;
  margin: 0 0 0 0;
}

.filter-section {
  display: flex;
  gap: 1rem;
  align-items: center;
  flex-wrap: wrap;
}

.search-box {
  flex: 1;
  min-width: 250px;
}

.filter-group {
  min-width: 150px;
}

.request-title {
  font-weight: 600;
  color: #2c3e50;
}

.action-buttons {
  display: flex;
  gap: 0.5rem;
  flex-wrap: wrap;
}

.empty-state {
  text-align: center;
  padding: 3rem;
  color: #999;
}

.empty-icon {
  font-size: 4rem;
  margin-bottom: 1rem;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 1rem;
  margin-top: 2rem;
}

.page-info {
  font-weight: 600;
  color: #666;
}

@media (max-width: 768px) {
  .page-header {
    flex-direction: column;
    align-items: flex-start;
  }
  
  .filter-section {
    flex-direction: column;
    align-items: stretch;
  }
  
  .search-box,
  .filter-group {
    width: 100%;
  }
  
  .action-buttons {
    flex-direction: column;
    gap: 0.25rem;
  }
  
  .pagination {
    flex-direction: column;
    gap: 0.5rem;
  }
}
</style>
