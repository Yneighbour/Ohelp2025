<template>
  <div class="page">
    <SiteHeader />
    <main class="container">
      <div class="page-header">
        <h1>老人档案管理</h1>
        <p>全面管理老年人的基本信息、健康状况和护理记录</p>
        <button class="btn btn-primary">添加老人档案</button>
      </div>
      
      <!-- 搜索和筛选区域 -->
      <div class="card">
        <div class="filter-section">
          <div class="search-box">
            <input type="text" placeholder="搜索老人姓名或ID" class="form-control" v-model="searchQuery">
          </div>
          <div class="filter-group">
            <select class="form-control" v-model="filterLevel">
              <option value="">全部护理等级</option>
              <option value="特级护理">特级护理</option>
              <option value="一级护理">一级护理</option>
              <option value="二级护理">二级护理</option>
              <option value="三级护理">三级护理</option>
            </select>
          </div>
        </div>
      </div>
      
      <!-- Elder List -->
      <div class="card">
        <table class="table">
          <thead>
            <tr>
              <th>老人ID</th>
              <th>姓名</th>
              <th>年龄</th>
              <th>房间号</th>
              <th>护理等级</th>
              <th>健康状况</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="elder in filteredElders" :key="elder.id" class="table-row">
              <td>{{ elder.id }}</td>
              <td class="elder-name">{{ elder.name }}</td>
              <td>{{ elder.age }}</td>
              <td>{{ elder.roomNumber }}</td>
              <td>
                <span :class="['badge', `badge-${getCareLevelClass(elder.careLevel)}`]">
                  {{ elder.careLevel }}
                </span>
              </td>
              <td>
                <span :class="['badge', `badge-${elder.healthStatus === '健康' ? 'success' : elder.healthStatus === '一般' ? 'warning' : 'danger'}`]">
                  {{ elder.healthStatus }}
                </span>
              </td>
              <td class="action-buttons">
                <button class="btn btn-info btn-sm" @click="viewDetails(elder.id)">详情</button>
                <button class="btn btn-success btn-sm" @click="editElder(elder.id)">编辑</button>
                <button class="btn btn-danger btn-sm" @click="deleteElder(elder.id)">删除</button>
              </td>
            </tr>
          </tbody>
        </table>
        
        <!-- 空状态 -->
        <div v-if="filteredElders.length === 0" class="empty-state">
          <div class="empty-icon">👴</div>
          <p>暂无老人档案数据</p>
        </div>
      </div>
    </main>
    <SiteFooter />
  </div>
</template>

<script>
import { ref, computed } from 'vue'
import SiteHeader from '../../components/Header.vue'
import SiteFooter from '../../components/Footer.vue'

export default {
  components: { SiteHeader, SiteFooter },
  setup() {
    const elders = ref([
      { id: 1, name: '张三', age: 85, roomNumber: '302', careLevel: '一级护理', healthStatus: '健康' },
      { id: 2, name: '李四', age: 78, roomNumber: '205', careLevel: '二级护理', healthStatus: '一般' },
      { id: 3, name: '王五', age: 92, roomNumber: '401', careLevel: '特级护理', healthStatus: '健康' },
      { id: 4, name: '赵六', age: 88, roomNumber: '305', careLevel: '一级护理', healthStatus: '一般' },
      { id: 5, name: '孙七', age: 75, roomNumber: '201', careLevel: '三级护理', healthStatus: '健康' }
    ])
    
    const searchQuery = ref('')
    const filterLevel = ref('')
    
    // 筛选老人列表
    const filteredElders = computed(() => {
      return elders.value.filter(elder => {
        const matchesSearch = elder.name.includes(searchQuery.value) || elder.id.toString().includes(searchQuery.value)
        const matchesLevel = !filterLevel.value || elder.careLevel === filterLevel.value
        return matchesSearch && matchesLevel
      })
    })
    
    // 获取护理等级对应的样式类
    const getCareLevelClass = (level) => {
      switch(level) {
        case '特级护理': return 'danger'
        case '一级护理': return 'warning'
        case '二级护理': return 'info'
        case '三级护理': return 'success'
        default: return 'info'
      }
    }
    
    // 查看详情
    const viewDetails = (id) => {
      console.log('查看详情:', id)
      // 这里可以添加路由跳转逻辑
    }
    
    // 编辑老人信息
    const editElder = (id) => {
      console.log('编辑老人:', id)
      // 这里可以添加编辑逻辑
    }
    
    // 删除老人信息
    const deleteElder = (id) => {
      if (confirm('确定要删除这位老人的档案吗？')) {
        elders.value = elders.value.filter(elder => elder.id !== id)
      }
    }
    
    return {
      elders,
      searchQuery,
      filterLevel,
      filteredElders,
      getCareLevelClass,
      viewDetails,
      editElder,
      deleteElder
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

.table-row {
  transition: all 0.2s ease;
}

.table-row:hover {
  background-color: #f8f9fa;
  transform: translateY(-1px);
}

.elder-name {
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
}
</style>
