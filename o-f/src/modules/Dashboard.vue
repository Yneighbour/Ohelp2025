<template>
  <div class="dashboard">
    <!-- 欢迎区域 -->
    <div class="welcome-section">
      <BaseCard class="welcome-card">
        <div class="welcome-content">
          <div class="welcome-text">
            <h1 class="welcome-title">欢迎使用 Ohelp2025</h1>
            <p class="welcome-subtitle">智慧养老服务管理系统</p>
            <div class="welcome-stats">
              <div class="stat-item">
                <div class="stat-number">{{ totalUsers }}</div>
                <div class="stat-label">注册用户</div>
              </div>
              <div class="stat-item">
                <div class="stat-number">{{ activeEmergencies }}</div>
                <div class="stat-label">活跃求助</div>
              </div>
              <div class="stat-item">
                <div class="stat-number">{{ totalActivities }}</div>
                <div class="stat-label">活动数量</div>
              </div>
            </div>
          </div>
          <div class="welcome-illustration">
            <div class="illustration-circle">
              <svg viewBox="0 0 120 120" fill="none" stroke="currentColor">
                <circle cx="60" cy="60" r="50" stroke-width="2" opacity="0.2"/>
                <circle cx="60" cy="60" r="35" stroke-width="3" opacity="0.4"/>
                <circle cx="60" cy="60" r="20" stroke-width="4"/>
              </svg>
            </div>
          </div>
        </div>
      </BaseCard>
    </div>

    <!-- 快速入口 -->
    <div class="quick-access-section">
      <h2 class="section-title">快速入口</h2>
      <div class="quick-access-grid">
        <router-link
          v-for="module in quickAccessModules"
          :key="module.key"
          :to="module.path"
          class="quick-access-card"
        >
          <BaseCard class="access-card hover-lift">
            <div class="card-icon">
              <component :is="module.icon" />
            </div>
            <div class="card-content">
              <h3 class="card-title">{{ module.title }}</h3>
              <p class="card-description">{{ module.description }}</p>
            </div>
            <div class="card-arrow">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <path d="M9 18l6-6-6-6" />
              </svg>
            </div>
          </BaseCard>
        </router-link>
      </div>
    </div>

    <!-- 数据概览 -->
    <div class="overview-section">
      <h2 class="section-title">数据概览</h2>
      <div class="overview-grid">
        <!-- 用户统计 -->
        <BaseCard class="overview-card">
          <div class="overview-header">
            <div class="overview-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
                <circle cx="9" cy="7" r="4"></circle>
                <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
                <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
              </svg>
            </div>
            <div class="overview-title">用户管理</div>
          </div>
          <div class="overview-content">
            <div class="overview-number">{{ userStats.total }}</div>
            <div class="overview-trend">
              <span :class="userStats.trend > 0 ? 'trend-up' : 'trend-down'">
                {{ userStats.trend > 0 ? '+' : '' }}{{ userStats.trend }}%
              </span>
              <span class="trend-period">较上月</span>
            </div>
          </div>
        </BaseCard>

        <!-- 用户增长趋势图表 -->
        <BaseCard class="chart-card">
          <template #header>
            <h3>用户增长趋势</h3>
          </template>
          <ChartCanvas
            type="line"
            :data="userGrowthData"
            width="100%"
            height="200"
            :show-legend="false"
          />
        </BaseCard>

        <!-- 老人统计 -->
        <BaseCard class="overview-card">
          <div class="overview-header">
            <div class="overview-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <circle cx="12" cy="8" r="4"></circle>
                <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                <text x="12" y="13" text-anchor="middle" font-size="6" fill="currentColor">👴</text>
              </svg>
            </div>
            <div class="overview-title">老人信息</div>
          </div>
          <div class="overview-content">
            <div class="overview-number">{{ elderStats.total }}</div>
            <div class="overview-trend">
              <span :class="elderStats.trend > 0 ? 'trend-up' : 'trend-down'">
                {{ elderStats.trend > 0 ? '+' : '' }}{{ elderStats.trend }}%
              </span>
              <span class="trend-period">较上月</span>
            </div>
          </div>
        </BaseCard>

        <!-- 活动统计 -->
        <BaseCard class="overview-card">
          <div class="overview-header">
            <div class="overview-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <path d="M14.828 14.828a4 4 0 01-5.656 0M9 10h1.586a1 1 0 01.707.293l.707.707A1 1 0 0013.414 11H15m-6 4a2 2 0 100-4 2 2 0 000 4z"></path>
              </svg>
            </div>
            <div class="overview-title">活动管理</div>
          </div>
          <div class="overview-content">
            <div class="overview-number">{{ activityStats.total }}</div>
            <div class="overview-trend">
              <span :class="activityStats.trend > 0 ? 'trend-up' : 'trend-down'">
                {{ activityStats.trend > 0 ? '+' : '' }}{{ activityStats.trend }}%
              </span>
              <span class="trend-period">较上月</span>
            </div>
          </div>
        </BaseCard>

        <!-- 紧急求助统计 -->
        <BaseCard class="overview-card">
          <div class="overview-header">
            <div class="overview-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <circle cx="12" cy="12" r="10"></circle>
                <line x1="12" y1="8" x2="12" y2="12"></line>
                <line x1="12" y1="16" x2="12.01" y2="16"></line>
              </svg>
            </div>
            <div class="overview-title">紧急求助</div>
          </div>
          <div class="overview-content">
            <div class="overview-number">{{ emergencyStats.total }}</div>
            <div class="overview-trend">
              <span :class="emergencyStats.trend > 0 ? 'trend-up' : 'trend-down'">
                {{ emergencyStats.trend > 0 ? '+' : '' }}{{ emergencyStats.trend }}%
              </span>
              <span class="trend-period">较上月</span>
            </div>
          </div>
        </BaseCard>
      </div>
    </div>

    <!-- 近期活动 -->
    <div class="recent-activities-section">
      <BaseCard>
        <template #header>
          <h2 class="section-title">近期活动</h2>
          <BaseButton variant="secondary" size="sm">
            查看全部
          </BaseButton>
        </template>

        <div class="recent-activities-list">
          <div v-for="activity in recentActivities" :key="activity.id" class="activity-item">
            <div class="activity-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <circle cx="12" cy="12" r="10"></circle>
                <polyline points="12,6 12,12 16,14"></polyline>
              </svg>
            </div>
            <div class="activity-content">
              <div class="activity-title">{{ activity.name }}</div>
              <div class="activity-meta">
                <span class="activity-time">{{ activity.startTime }}</span>
                <span class="activity-location">{{ activity.location }}</span>
              </div>
            </div>
            <div class="activity-status">
              <span :class="`status-${activity.status}`">
                {{ activity.statusText }}
              </span>
            </div>
          </div>
        </div>
      </BaseCard>
    </div>

    <!-- 系统健康状态 -->
    <div class="system-health-section">
      <BaseCard>
        <template #header>
          <h2 class="section-title">系统健康状态</h2>
        </template>

        <div class="health-indicators">
          <div class="health-item">
            <div class="health-label">服务器状态</div>
            <div class="health-value">
              <span class="status-healthy">正常</span>
            </div>
          </div>

          <div class="health-item">
            <div class="health-label">数据库连接</div>
            <div class="health-value">
              <span class="status-healthy">正常</span>
            </div>
          </div>

          <div class="health-item">
            <div class="health-label">API响应时间</div>
            <div class="health-value">
              <span class="response-time">245ms</span>
            </div>
          </div>

          <div class="health-item">
            <div class="health-label">活跃用户</div>
            <div class="health-value">
              <span class="active-users">{{ activeUsers }}</span>
            </div>
          </div>
        </div>
      </BaseCard>
    </div>
  </div>
</template>

<script>
import BaseCard from '../components/base/BaseCard.vue'
import BaseButton from '../components/base/BaseButton.vue'
import ChartCanvas from '../components/common/ChartCanvas.vue'

// 图标组件
const UserIcon = {
  template: `
    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
      <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
      <circle cx="9" cy="7" r="4"></circle>
      <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
      <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
    </svg>
  `
}

const ElderIcon = {
  template: `
    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
      <circle cx="12" cy="8" r="4"></circle>
      <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
      <text x="12" y="13" text-anchor="middle" font-size="6" fill="currentColor">👴</text>
    </svg>
  `
}

const ActivityIcon = {
  template: `
    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
      <path d="M14.828 14.828a4 4 0 01-5.656 0M9 10h1.586a1 1 0 01.707.293l.707.707A1 1 0 0013.414 11H15m-6 4a2 2 0 100-4 2 2 0 000 4z"></path>
    </svg>
  `
}

const EmergencyIcon = {
  template: `
    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
      <circle cx="12" cy="12" r="10"></circle>
      <line x1="12" y1="8" x2="12" y2="12"></line>
      <line x1="12" y1="16" x2="12.01" y2="16"></line>
    </svg>
  `
}

const HealthIcon = {
  template: `
    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
      <path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"></path>
    </svg>
  `
}

const ServiceOrderIcon = {
  template: `
    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
      <path d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
    </svg>
  `
}

const WorkerIcon = {
  template: `
    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
      <path d="M14.7 6.3a1 1 0 0 0 0 1.4l1.6 1.6a1 1 0 0 0 1.4 0l3.77-3.77a6 6 0 0 1-7.94 7.94l-6.91 6.91a2.12 2.12 0 0 1-3-3l6.91-6.91a6 6 0 0 1 7.94-7.94l-3.76 3.76z"></path>
    </svg>
  `
}

const FileIcon = {
  template: `
    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
      <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
      <polyline points="14,2 14,8 20,8"></polyline>
    </svg>
  `
}

const AuthIcon = {
  template: `
    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
      <rect x="3" y="11" width="18" height="11" rx="2" ry="2"></rect>
      <circle cx="12" cy="16" r="1"></circle>
      <path d="M7 11V7a5 5 0 0 1 10 0v4"></path>
    </svg>
  `
}

export default {
  name: 'Dashboard',
  components: {
    BaseCard,
    BaseButton,
    ChartCanvas
  },
  data() {
    return {
      // 统计数据（模拟数据）
      totalUsers: 1250,
      activeEmergencies: 3,
      totalActivities: 28,

      // 快速入口模块
      quickAccessModules: [
        {
          key: 'auth',
          title: '认证管理',
          description: '用户登录、注册和权限管理',
          path: '/auth',
          icon: AuthIcon
        },
        {
          key: 'user',
          title: '用户管理',
          description: '用户信息管理和状态控制',
          path: '/user',
          icon: UserIcon
        },
        {
          key: 'elder',
          title: '老人信息',
          description: '老人档案和家属信息管理',
          path: '/elder',
          icon: ElderIcon
        },
        {
          key: 'activity',
          title: '活动管理',
          description: '养老活动组织和报名管理',
          path: '/activity',
          icon: ActivityIcon
        },
        {
          key: 'emergency',
          title: '紧急求助',
          description: '紧急情况处理和响应管理',
          path: '/emergency',
          icon: EmergencyIcon
        },
        {
          key: 'health',
          title: '健康管理',
          description: '健康记录和体检数据管理',
          path: '/health',
          icon: HealthIcon
        },
        {
          key: 'serviceorder',
          title: '服务订单',
          description: '养老服务订单和进度跟踪',
          path: '/serviceorder',
          icon: ServiceOrderIcon
        },
        {
          key: 'worker',
          title: '工作人员',
          description: '工作人员信息和排班管理',
          path: '/worker',
          icon: WorkerIcon
        },
        {
          key: 'file',
          title: '文件管理',
          description: '文件上传、存储和权限管理',
          path: '/file',
          icon: FileIcon
        }
      ],

      // 统计数据
      userStats: { total: 1250, trend: 12.5 },
      elderStats: { total: 856, trend: 8.3 },
      activityStats: { total: 28, trend: -2.1 },
      emergencyStats: { total: 3, trend: -15.8 },

      // 近期活动
      recentActivities: [
        {
          id: 1,
          name: '太极拳晨练',
          startTime: '08:00',
          location: '社区广场',
          status: 'upcoming',
          statusText: '即将开始'
        },
        {
          id: 2,
          name: '健康讲座',
          startTime: '14:00',
          location: '活动中心',
          status: 'ongoing',
          statusText: '进行中'
        },
        {
          id: 3,
          name: '书法课程',
          startTime: '10:00',
          location: '文化室',
          status: 'completed',
          statusText: '已完成'
        }
      ],

      // 系统状态
      activeUsers: 89,

      // 图表数据
      userGrowthData: [
        { label: '1月', value: 850 },
        { label: '2月', value: 920 },
        { label: '3月', value: 1050 },
        { label: '4月', value: 1150 },
        { label: '5月', value: 1200 },
        { label: '6月', value: 1250 }
      ]
    }
  },
  mounted() {
    // 页面加载动画
    this.$nextTick(() => {
      const cards = document.querySelectorAll('.overview-card')
      cards.forEach((card, index) => {
        card.style.animationDelay = `${index * 0.1}s`
        card.classList.add('fade-in')
      })
    })
  }
}
</script>

<style scoped>
.dashboard {
  padding: 1rem 0;
  max-width: 1400px;
  margin: 0 auto;
}

/* 欢迎区域 */
.welcome-section {
  margin-bottom: var(--spacing-xl);
}

.welcome-card {
  background: linear-gradient(135deg, var(--primary-color) 0%, var(--primary-light) 100%);
  color: white;
  border: none;
}

.welcome-content {
  display: flex;
  align-items: center;
  gap: var(--spacing-xl);
}

.welcome-text {
  flex: 1;
}

.welcome-title {
  font-size: var(--font-size-xxxl);
  font-weight: 700;
  margin-bottom: var(--spacing-sm);
  background: linear-gradient(135deg, #FFFFFF 0%, rgba(255, 255, 255, 0.9) 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.welcome-subtitle {
  font-size: var(--font-size-lg);
  opacity: 0.9;
  margin-bottom: var(--spacing-lg);
}

.welcome-stats {
  display: flex;
  gap: var(--spacing-xl);
}

.stat-item {
  text-align: center;
}

.stat-number {
  font-size: var(--font-size-xxxl);
  font-weight: 700;
  display: block;
  margin-bottom: var(--spacing-xs);
}

.stat-label {
  font-size: var(--font-size-sm);
  opacity: 0.8;
}

.welcome-illustration {
  flex-shrink: 0;
}

.illustration-circle {
  width: 120px;
  height: 120px;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  backdrop-filter: blur(10px);
}

.illustration-circle svg {
  width: 60px;
  height: 60px;
  color: white;
}

/* 快速入口 */
.quick-access-section {
  margin-bottom: var(--spacing-xl);
}

.section-title {
  font-size: var(--font-size-xl);
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: var(--spacing-lg);
}

.quick-access-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: var(--spacing-lg);
}

.quick-access-card {
  text-decoration: none;
  display: block;
}

.access-card {
  height: 100%;
  transition: all var(--transition-base);
}

.access-card:hover {
  transform: translateY(-4px);
  box-shadow: var(--shadow-xl);
}

.card-icon {
  width: 48px;
  height: 48px;
  background: var(--primary-gradient);
  border-radius: var(--border-radius-lg);
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: var(--spacing-md);
  color: white;
}

.card-icon svg {
  width: 24px;
  height: 24px;
}

.card-content {
  margin-bottom: var(--spacing-md);
}

.card-title {
  font-size: var(--font-size-lg);
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: var(--spacing-xs);
}

.card-description {
  font-size: var(--font-size-sm);
  color: var(--text-secondary);
  line-height: 1.5;
}

.card-arrow {
  margin-left: auto;
  width: 24px;
  height: 24px;
  color: var(--primary-color);
  opacity: 0.6;
  transition: opacity var(--transition-fast);
}

.quick-access-card:hover .card-arrow {
  opacity: 1;
}

/* 数据概览 */
.overview-section {
  margin-bottom: var(--spacing-xl);
}

.overview-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: var(--spacing-lg);
}

.overview-card {
  background: linear-gradient(135deg, var(--white) 0%, var(--background-secondary) 100%);
}

.overview-header {
  display: flex;
  align-items: center;
  margin-bottom: var(--spacing-md);
}

.overview-icon {
  width: 40px;
  height: 40px;
  background: var(--primary-gradient);
  border-radius: var(--border-radius-md);
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: var(--spacing-md);
  color: white;
}

.overview-icon svg {
  width: 20px;
  height: 20px;
}

.overview-title {
  font-size: var(--font-size-base);
  font-weight: 600;
  color: var(--text-primary);
}

.overview-content {
  display: flex;
  align-items: baseline;
  justify-content: space-between;
}

.overview-number {
  font-size: var(--font-size-xxxl);
  font-weight: 700;
  color: var(--primary-color);
}

.overview-trend {
  display: flex;
  align-items: center;
  gap: var(--spacing-xs);
}

.trend-up {
  color: var(--success-color);
}

.trend-down {
  color: var(--error-color);
}

.trend-period {
  font-size: var(--font-size-sm);
  color: var(--text-secondary);
}

/* 近期活动 */
.recent-activities-section {
  margin-bottom: var(--spacing-xl);
}

.recent-activities-list {
  display: flex;
  flex-direction: column;
  gap: var(--spacing-md);
}

.activity-item {
  display: flex;
  align-items: center;
  padding: var(--spacing-md);
  background-color: var(--background-secondary);
  border-radius: var(--border-radius-md);
  transition: background-color var(--transition-fast);
}

.activity-item:hover {
  background-color: var(--background-color);
}

.activity-icon {
  width: 40px;
  height: 40px;
  background: var(--primary-gradient);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: var(--spacing-md);
  color: white;
  flex-shrink: 0;
}

.activity-icon svg {
  width: 20px;
  height: 20px;
}

.activity-content {
  flex: 1;
}

.activity-title {
  font-size: var(--font-size-base);
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: var(--spacing-xs);
}

.activity-meta {
  display: flex;
  gap: var(--spacing-md);
  font-size: var(--font-size-sm);
  color: var(--text-secondary);
}

.activity-status {
  flex-shrink: 0;
}

.status-upcoming {
  background-color: var(--info-color);
  color: white;
  padding: var(--spacing-xs) var(--spacing-sm);
  border-radius: var(--border-radius-sm);
  font-size: var(--font-size-sm);
}

.status-ongoing {
  background-color: var(--success-color);
  color: white;
  padding: var(--spacing-xs) var(--spacing-sm);
  border-radius: var(--border-radius-sm);
  font-size: var(--font-size-sm);
}

.status-completed {
  background-color: var(--text-disabled);
  color: var(--text-secondary);
  padding: var(--spacing-xs) var(--spacing-sm);
  border-radius: var(--border-radius-sm);
  font-size: var(--font-size-sm);
}

/* 系统健康状态 */
.system-health-section {
  margin-bottom: var(--spacing-xl);
}

.health-indicators {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: var(--spacing-lg);
}

.health-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: var(--spacing-md);
  background-color: var(--background-secondary);
  border-radius: var(--border-radius-md);
}

.health-label {
  font-size: var(--font-size-base);
  font-weight: 500;
  color: var(--text-primary);
}

.health-value {
  font-size: var(--font-size-lg);
  font-weight: 600;
}

.status-healthy {
  color: var(--success-color);
}

.response-time {
  color: var(--info-color);
}

.active-users {
  color: var(--primary-color);
}

/* 响应式适配 */
@media (max-width: 1024px) {
  .welcome-content {
    flex-direction: column;
    text-align: center;
    gap: var(--spacing-lg);
  }

  .welcome-stats {
    justify-content: center;
  }

  .quick-access-grid {
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  }

  .overview-grid {
    grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
  }
}

@media (max-width: 768px) {
  .welcome-title {
    font-size: var(--font-size-xxl);
  }

  .welcome-stats {
    flex-direction: column;
    gap: var(--spacing-md);
  }

  .quick-access-grid {
    grid-template-columns: 1fr;
  }

  .overview-grid {
    grid-template-columns: repeat(2, 1fr);
  }

  .health-indicators {
    grid-template-columns: 1fr;
  }

  .activity-meta {
    flex-direction: column;
    gap: var(--spacing-xs);
  }
}

@media (max-width: 480px) {
  .overview-grid {
    grid-template-columns: 1fr;
  }

  .welcome-illustration {
    display: none;
  }
}

/* 动画效果 */
.fade-in {
  animation: fadeIn 0.6s ease-out forwards;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>