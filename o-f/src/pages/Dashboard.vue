<template>
  <div class="page">
    <SiteHeader />
    <main class="container">
      <div class="welcome-section">
        <h1>欢迎回来，{{ userStore.userInfo?.username }}</h1>
        <p class="welcome-subtitle">
          您当前的身份是 <span class="user-role-badge">{{ getRoleLabel(userStore.userInfo?.role) }}</span>
        </p>
        <p>实时监控养老机构运营数据和关键指标</p>
      </div>
      
      <!-- 统计卡片区域 -->
      <div class="stats-grid">
        <div class="card stat-card">
          <div class="stat-content">
            <div class="stat-info">
              <h3>在住老人</h3>
              <p class="stat-number">128</p>
            </div>
            <div class="stat-icon">👴</div>
          </div>
          <div class="stat-trend positive">
            <span>+5</span>
            <small>较上月</small>
          </div>
        </div>
        
        <div class="card stat-card">
          <div class="stat-content">
            <div class="stat-info">
              <h3>服务人员</h3>
              <p class="stat-number">52</p>
            </div>
            <div class="stat-icon">👩⚕️</div>
          </div>
          <div class="stat-trend positive">
            <span>+2</span>
            <small>较上月</small>
          </div>
        </div>
        
        <div class="card stat-card">
          <div class="stat-content">
            <div class="stat-info">
              <h3>今日服务请求</h3>
              <p class="stat-number">24</p>
            </div>
            <div class="stat-icon">📋</div>
          </div>
          <div class="stat-trend negative">
            <span>-3</span>
            <small>较昨日</small>
          </div>
        </div>
        
        <div class="card stat-card">
          <div class="stat-content">
            <div class="stat-info">
              <h3>服务满意度</h3>
              <p class="stat-number">92%</p>
            </div>
            <div class="stat-icon">⭐</div>
          </div>
          <div class="stat-trend positive">
            <span>+1.5%</span>
            <small>较上月</small>
          </div>
        </div>
      </div>
      
      <!-- 图表区域 -->
      <div class="charts-grid">
        <div class="card chart-card">
          <h3>月度服务请求趋势</h3>
          <div class="chart-placeholder">
            <div class="chart-bar-container">
              <div class="chart-bar" style="height: 60%;"><span>1月</span></div>
              <div class="chart-bar" style="height: 75%;"><span>2月</span></div>
              <div class="chart-bar" style="height: 55%;"><span>3月</span></div>
              <div class="chart-bar" style="height: 85%;"><span>4月</span></div>
              <div class="chart-bar" style="height: 70%;"><span>5月</span></div>
              <div class="chart-bar" style="height: 90%;"><span>6月</span></div>
            </div>
          </div>
        </div>
        
        <div class="card chart-card">
          <h3>护理等级分布</h3>
          <div class="chart-placeholder">
            <div class="pie-chart">
              <div class="pie-slice slice-1"></div>
              <div class="pie-slice slice-2"></div>
              <div class="pie-slice slice-3"></div>
              <div class="pie-slice slice-4"></div>
              <div class="pie-center">
                <span>128</span>
                <small>总人数</small>
              </div>
            </div>
            <div class="pie-legend">
              <div class="legend-item">
                <span class="legend-color" style="background: #dc3545;"></span>
                <span>特级护理 (15)</span>
              </div>
              <div class="legend-item">
                <span class="legend-color" style="background: #ffc107;"></span>
                <span>一级护理 (42)</span>
              </div>
              <div class="legend-item">
                <span class="legend-color" style="background: #17a2b8;"></span>
                <span>二级护理 (38)</span>
              </div>
              <div class="legend-item">
                <span class="legend-color" style="background: #28a745;"></span>
                <span>三级护理 (33)</span>
              </div>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 最近动态和待办事项 -->
      <div class="bottom-grid">
        <!-- 最近服务请求 -->
        <div class="card">
          <h3>最近服务请求</h3>
          <div class="activity-list">
            <div class="activity-item" v-for="request in recentRequests" :key="request.id">
              <div class="activity-icon">
                <span :class="`badge badge-${request.status === '已完成' ? 'success' : request.status === '处理中' ? 'warning' : 'info'}`">
                  {{ request.status }}
                </span>
              </div>
              <div class="activity-content">
                <h4>{{ request.title }}</h4>
                <p>{{ request.description }}</p>
                <small>{{ request.time }}</small>
              </div>
            </div>
          </div>
          <a href="/requests.html" class="view-all-link">查看全部请求 →</a>
        </div>
        
        <!-- 待办事项 -->
        <div class="card">
          <h3>今日待办事项</h3>
          <div class="todo-list">
            <div class="todo-item" v-for="todo in todos" :key="todo.id">
              <input type="checkbox" v-model="todo.completed">
              <div class="todo-content">
                <h4>{{ todo.title }}</h4>
                <small>{{ todo.time }}</small>
              </div>
            </div>
          </div>
          <button class="btn btn-secondary btn-block">添加待办事项</button>
        </div>
      </div>
    </main>
    <SiteFooter />
  </div>
</template>

<script>
import SiteHeader from '../components/Header.vue'
import SiteFooter from '../components/Footer.vue'
import { ref } from 'vue'
import { useUserStore } from '../store/user'

export default {
  components: { SiteHeader, SiteFooter },
  setup() {
    const userStore = useUserStore()
    
    // 获取角色中文标签
    const getRoleLabel = (role) => {
      const roleMap = {
        admin: '管理员',
        worker: '护理人员',
        relative: '家属',
        elder: '老人'
      }
      return roleMap[role] || role
    }
    // 最近服务请求数据
    const recentRequests = ref([
      { id: 1, title: '张三老人需要协助洗澡', description: '护理员李护士负责', status: '处理中', time: '2024-01-06 10:30' },
      { id: 2, title: '李四老人需要测量血压', description: '护理员王护士负责', status: '已完成', time: '2024-01-06 09:15' },
      { id: 3, title: '王五老人需要更换床单', description: '护理员张护士负责', status: '待处理', time: '2024-01-06 08:45' },
      { id: 4, title: '赵六老人需要服药提醒', description: '护理员刘护士负责', status: '已完成', time: '2024-01-06 07:30' }
    ])
    
    // 待办事项数据
    const todos = ref([
      { id: 1, title: '参加早会', time: '08:30', completed: false },
      { id: 2, title: '检查老人房间安全', time: '09:00', completed: true },
      { id: 3, title: '准备午餐', time: '10:30', completed: false },
      { id: 4, title: '组织下午活动', time: '14:00', completed: false },
      { id: 5, title: '填写日报表', time: '17:00', completed: false }
    ])
    
    return {
      userStore,
      getRoleLabel,
      recentRequests,
      todos
    }
  }
}
</script>

<style scoped>
/* 欢迎区域样式 */
.welcome-section {
  background: linear-gradient(135deg, rgba(102, 126, 234, 0.1) 0%, rgba(118, 75, 162, 0.1) 100%);
  padding: 2rem;
  border-radius: 12px;
  margin-bottom: 2rem;
  text-align: center;
}

.welcome-section h1 {
  margin-bottom: 0.5rem;
  color: #2c3e50;
}

.welcome-subtitle {
  font-size: 1.125rem;
  color: #666;
  margin-bottom: 1rem;
}

.user-role-badge {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 0.25rem 1rem;
  border-radius: 20px;
  font-weight: 600;
  font-size: 1rem;
}

/* 统计卡片样式 */
.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 1.5rem;
  margin-bottom: 2rem;
}

.stat-card {
  display: flex;
  flex-direction: column;
  transition: all 0.3s ease;
}

.stat-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
}

.stat-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.stat-info h3 {
  margin: 0 0 0.5rem 0;
  color: #666;
  font-size: 1rem;
  font-weight: 500;
}

.stat-number {
  font-size: 2.5rem;
  font-weight: 700;
  margin: 0;
  color: #2c3e50;
}

.stat-icon {
  font-size: 3rem;
}

.stat-trend {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.5rem;
  border-radius: 4px;
  font-weight: 600;
}

.stat-trend.positive {
  background-color: #d4edda;
  color: #155724;
}

.stat-trend.negative {
  background-color: #f8d7da;
  color: #721c24;
}

/* 图表区域 */
.charts-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(400px, 1fr));
  gap: 1.5rem;
  margin-bottom: 2rem;
}

.chart-card {
  transition: all 0.3s ease;
}

.chart-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
}

.chart-card h3 {
  margin-bottom: 1.5rem;
  color: #2c3e50;
}

.chart-placeholder {
  height: 250px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f8f9fa;
  border-radius: 6px;
  padding: 1rem;
}

/* 柱状图样式 */
.chart-bar-container {
  display: flex;
  align-items: end;
  justify-content: space-around;
  height: 100%;
  width: 100%;
  gap: 1rem;
}

.chart-bar {
  flex: 1;
  background: linear-gradient(180deg, #667eea 0%, #764ba2 100%);
  border-radius: 4px 4px 0 0;
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  align-items: center;
  transition: all 0.3s ease;
  position: relative;
}

.chart-bar:hover {
  transform: scaleY(1.05);
}

.chart-bar span {
  color: white;
  font-weight: 600;
  margin-bottom: 0.5rem;
}

/* 饼图样式 */
.pie-chart {
  position: relative;
  width: 200px;
  height: 200px;
  border-radius: 50%;
  background: conic-gradient(
    #dc3545 0deg 54deg,
    #ffc107 54deg 189deg,
    #17a2b8 189deg 324deg,
    #28a745 324deg 360deg
  );
  display: flex;
  align-items: center;
  justify-content: center;
}

.pie-center {
  position: absolute;
  width: 120px;
  height: 120px;
  background: white;
  border-radius: 50%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.pie-center span {
  font-size: 2rem;
  font-weight: 700;
  color: #2c3e50;
}

.pie-center small {
  color: #666;
  font-size: 0.875rem;
}

.pie-legend {
  margin-left: 2rem;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.legend-item {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.legend-color {
  display: inline-block;
  width: 16px;
  height: 16px;
  border-radius: 50%;
}

/* 底部内容区域 */
.bottom-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));
  gap: 1.5rem;
}

/* 活动列表样式 */
.activity-list {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  margin-bottom: 1rem;
}

.activity-item {
  display: flex;
  gap: 1rem;
  padding: 1rem;
  background-color: #f8f9fa;
  border-radius: 6px;
  transition: all 0.2s ease;
}

.activity-item:hover {
  background-color: #e9ecef;
}

.activity-content h4 {
  margin: 0 0 0.25rem 0;
  font-size: 1rem;
}

.activity-content p {
  margin: 0 0 0.5rem 0;
  color: #666;
  font-size: 0.875rem;
}

.activity-content small {
  color: #999;
  font-size: 0.75rem;
}

.view-all-link {
  color: #667eea;
  text-decoration: none;
  font-weight: 500;
  display: block;
  text-align: right;
  transition: color 0.3s ease;
}

.view-all-link:hover {
  color: #764ba2;
  text-decoration: underline;
}

/* 待办事项样式 */
.todo-list {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  margin-bottom: 1rem;
}

.todo-item {
  display: flex;
  align-items: center;
  gap: 1rem;
  padding: 1rem;
  background-color: #f8f9fa;
  border-radius: 6px;
  transition: all 0.2s ease;
}

.todo-item:hover {
  background-color: #e9ecef;
}

.todo-item input[type="checkbox"] {
  width: 18px;
  height: 18px;
  cursor: pointer;
}

.todo-content h4 {
  margin: 0 0 0.25rem 0;
  font-size: 1rem;
  text-decoration: none;
}

.todo-item input[type="checkbox"]:checked + .todo-content h4 {
  text-decoration: line-through;
  color: #999;
}

.todo-content small {
  color: #999;
  font-size: 0.75rem;
}

.btn-block {
  width: 100%;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .stats-grid,
  .charts-grid,
  .bottom-grid {
    grid-template-columns: 1fr;
  }
  
  .chart-placeholder {
    flex-direction: column;
    height: auto;
    min-height: 300px;
  }
  
  .pie-legend {
    margin-left: 0;
    margin-top: 1rem;
    flex-direction: row;
    flex-wrap: wrap;
    justify-content: center;
  }
  
  .legend-item {
    flex: 1 1 45%;
    justify-content: center;
  }
}
</style>
