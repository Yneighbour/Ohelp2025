<template>
  <div class="page">
    <SiteHeader />
    <main class="container">
      <!-- 页面头部 -->
      <div class="page-header">
        <div class="page-title">
          <el-icon class="title-icon">
            <DataAnalysis />
          </el-icon>
          <div>
            <h1 class="title-text">数据仪表盘</h1>
            <p class="title-subtitle">实时监控养老服务关键指标</p>
          </div>
        </div>
        <div class="page-actions">
          <el-button type="primary" @click="refreshData">
            <el-icon><Refresh /></el-icon>
            刷新数据
          </el-button>
        </div>
      </div>

      <!-- 统计卡片区域 -->
      <div class="stats-grid">
        <el-card class="stat-card elderly-card">
          <div class="stat-content">
            <div class="stat-icon">
              <el-icon size="32">
                <User />
              </el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ totalElderly }}</div>
              <div class="stat-label">老人总数</div>
              <div class="stat-trend">
                <el-icon class="trend-icon up">
                  <Top />
                </el-icon>
                <span>+12%</span>
              </div>
            </div>
          </div>
        </el-card>

        <el-card class="stat-card request-card">
          <div class="stat-content">
            <div class="stat-icon">
              <el-icon size="32">
                <DocumentAdd />
              </el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ todayRequests }}</div>
              <div class="stat-label">今日服务请求</div>
              <div class="stat-trend">
                <el-icon class="trend-icon up">
                  <Top />
                </el-icon>
                <span>+8%</span>
              </div>
            </div>
          </div>
        </el-card>

        <el-card class="stat-card rate-card">
          <div class="stat-content">
            <div class="stat-icon">
              <el-icon size="32">
                <TrendCharts />
              </el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ monthCompleteRate }}<span class="unit">%</span></div>
              <div class="stat-label">本月完成率</div>
              <div class="stat-trend">
                <el-icon class="trend-icon up">
                  <Top />
                </el-icon>
                <span>+5%</span>
              </div>
            </div>
          </div>
        </el-card>

        <el-card class="stat-card service-card">
          <div class="stat-content">
            <div class="stat-icon">
              <el-icon size="32">
                <Service />
              </el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ activeServices }}</div>
              <div class="stat-label">进行中服务</div>
              <div class="stat-trend">
                <el-icon class="trend-icon stable">
                  <Minus />
                </el-icon>
                <span>稳定</span>
              </div>
            </div>
          </div>
        </el-card>
      </div>

      <!-- 图表区域 -->
      <div class="chart-section">
        <el-card class="chart-card">
          <template #header>
            <div class="chart-header">
              <el-icon class="chart-icon">
                <TrendCharts />
              </el-icon>
              <span class="chart-title">近7天服务请求趋势</span>
              <el-select v-model="timeRange" size="small" style="margin-left: auto;">
                <el-option label="近7天" value="7d" />
                <el-option label="近30天" value="30d" />
                <el-option label="近90天" value="90d" />
              </el-select>
            </div>
          </template>
          <div id="chart" class="chart-container"></div>
        </el-card>
      </div>

      <!-- 快速操作区域 -->
      <div class="quick-actions">
        <el-card class="actions-card">
          <template #header>
            <div class="actions-header">
              <el-icon class="actions-icon">
                <Lightning />
              </el-icon>
              <span>快速操作</span>
            </div>
          </template>
          <div class="actions-grid">
            <el-button type="primary" @click="$router.push('/elderly/list')" class="action-btn">
              <el-icon><User /></el-icon>
              <span>老人管理</span>
            </el-button>
            <el-button type="success" @click="$router.push('/requests')" class="action-btn">
              <el-icon><DocumentAdd /></el-icon>
              <span>服务请求</span>
            </el-button>
            <el-button type="warning" @click="$router.push('/service-request/list')" class="action-btn">
              <el-icon><Service /></el-icon>
              <span>服务处理</span>
            </el-button>
            <el-button type="info" @click="$router.push('/user/list')" class="action-btn">
              <el-icon><Setting /></el-icon>
              <span>系统设置</span>
            </el-button>
          </div>
        </el-card>
      </div>
    </main>
    <SiteFooter />
  </div>
</template>

<script>
import SiteHeader from '../components/Header.vue'
import SiteFooter from '../components/Footer.vue'
import * as echarts from 'echarts'
import { ref, onMounted } from 'vue'
import {
  DataAnalysis,
  Refresh,
  User,
  DocumentAdd,
  TrendCharts,
  Service,
  Top,
  Minus,
  Lightning,
  Setting
} from '@element-plus/icons-vue'

export default {
  components: { SiteHeader, SiteFooter },
  setup() {
    const totalElderly = ref(128)
    const todayRequests = ref(12)
    const monthCompleteRate = ref(92)
    const activeServices = ref(24)
    const timeRange = ref('7d')

    const refreshData = () => {
      // 模拟数据刷新
      totalElderly.value = Math.floor(Math.random() * 20) + 120
      todayRequests.value = Math.floor(Math.random() * 10) + 8
      monthCompleteRate.value = Math.floor(Math.random() * 10) + 85
      activeServices.value = Math.floor(Math.random() * 10) + 20
      initChart()
    }

    const initChart = () => {
      const chartDom = document.getElementById('chart')
      if (!chartDom) return

      const chart = echarts.init(chartDom)

      const option = {
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        tooltip: {
          trigger: 'axis',
          backgroundColor: 'rgba(255, 255, 255, 0.95)',
          borderColor: 'var(--border)',
          textStyle: {
            color: 'var(--text-primary)'
          }
        },
        xAxis: {
          type: 'category',
          data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
          axisLine: {
            lineStyle: {
              color: 'var(--border)'
            }
          },
          axisLabel: {
            color: 'var(--text-secondary)'
          }
        },
        yAxis: {
          type: 'value',
          axisLine: {
            lineStyle: {
              color: 'var(--border)'
            }
          },
          axisLabel: {
            color: 'var(--text-secondary)'
          },
          splitLine: {
            lineStyle: {
              color: 'var(--border-light)',
              type: 'dashed'
            }
          }
        },
        series: [{
          name: '服务请求数',
          type: 'line',
          data: [8, 12, 10, 15, 9, 11, 12],
          smooth: true,
          symbol: 'circle',
          symbolSize: 6,
          lineStyle: {
            color: 'var(--primary)',
            width: 3
          },
          itemStyle: {
            color: 'var(--primary)'
          },
          areaStyle: {
            color: {
              type: 'linear',
              x: 0,
              y: 0,
              x2: 0,
              y2: 1,
              colorStops: [{
                offset: 0, color: 'rgba(79, 70, 229, 0.1)'
              }, {
                offset: 1, color: 'rgba(79, 70, 229, 0.02)'
              }]
            }
          }
        }]
      }

      chart.setOption(option)
      window.addEventListener('resize', () => chart.resize())
    }

    onMounted(() => {
      initChart()
    })

    return {
      totalElderly,
      todayRequests,
      monthCompleteRate,
      activeServices,
      timeRange,
      refreshData,
      // 图标
      DataAnalysis,
      Refresh,
      User,
      DocumentAdd,
      TrendCharts,
      Service,
      Top,
      Minus,
      Lightning,
      Setting
    }
  }
}
</script>

<style scoped>
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: var(--space-8);
  padding-bottom: var(--space-4);
  border-bottom: 1px solid var(--border-light);
}

.page-title {
  display: flex;
  align-items: center;
  gap: var(--space-3);
}

.title-icon {
  color: var(--primary);
  background: linear-gradient(135deg, var(--primary-light), var(--primary));
  border-radius: var(--radius);
  padding: var(--space-2);
}

.title-text {
  font-size: var(--font-size-3xl);
  font-weight: var(--font-weight-bold);
  color: var(--text-primary);
  margin: 0;
}

.title-subtitle {
  font-size: var(--font-size-base);
  color: var(--text-secondary);
  margin: var(--space-1) 0 0 0;
}

.page-actions .el-button {
  display: flex;
  align-items: center;
  gap: var(--space-2);
}

/* 统计卡片网格 */
.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: var(--space-6);
  margin-bottom: var(--space-8);
}

.stat-card {
  background: linear-gradient(135deg, var(--bg-card) 0%, rgba(255, 255, 255, 0.8) 100%);
  border: none;
  transition: all 0.3s ease;
}

.stat-card:hover {
  transform: translateY(-4px);
  box-shadow: var(--shadow-xl);
}

.stat-content {
  display: flex;
  align-items: center;
  gap: var(--space-4);
  padding: var(--space-6);
}

.stat-icon {
  flex-shrink: 0;
  width: 60px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: var(--radius-lg);
  background: var(--bg-secondary);
}

.elderly-card .stat-icon {
  background: linear-gradient(135deg, #dbeafe, #bfdbfe);
  color: #3b82f6;
}

.request-card .stat-icon {
  background: linear-gradient(135deg, #dcfce7, #bbf7d0);
  color: var(--success);
}

.rate-card .stat-icon {
  background: linear-gradient(135deg, #fef3c7, #fde68a);
  color: var(--warning);
}

.service-card .stat-icon {
  background: linear-gradient(135deg, #e0e7ff, #c7d2fe);
  color: var(--primary);
}

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: var(--font-size-4xl);
  font-weight: var(--font-weight-bold);
  color: var(--text-primary);
  line-height: 1;
  margin-bottom: var(--space-1);
}

.stat-value .unit {
  font-size: var(--font-size-xl);
  font-weight: var(--font-weight-normal);
  color: var(--text-secondary);
}

.stat-label {
  font-size: var(--font-size-base);
  color: var(--text-secondary);
  font-weight: var(--font-weight-medium);
  margin-bottom: var(--space-2);
}

.stat-trend {
  display: flex;
  align-items: center;
  gap: var(--space-1);
  font-size: var(--font-size-sm);
}

.trend-icon {
  font-size: 14px;
}

.trend-icon.up {
  color: var(--success);
}

.trend-icon.down {
  color: var(--error);
}

.trend-icon.stable {
  color: var(--text-muted);
}

/* 图表区域 */
.chart-section {
  margin-bottom: var(--space-8);
}

.chart-card {
  border: none;
  box-shadow: var(--shadow);
}

.chart-header {
  display: flex;
  align-items: center;
  gap: var(--space-2);
}

.chart-icon {
  color: var(--primary);
}

.chart-title {
  font-weight: var(--font-weight-semibold);
  color: var(--text-primary);
}

.chart-container {
  width: 100%;
  height: 400px;
}

/* 快速操作区域 */
.actions-card {
  border: none;
  box-shadow: var(--shadow);
}

.actions-header {
  display: flex;
  align-items: center;
  gap: var(--space-2);
}

.actions-icon {
  color: var(--warning);
}

.actions-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: var(--space-4);
}

.action-btn {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: var(--space-2);
  padding: var(--space-6) var(--space-4);
  height: auto;
  min-height: 80px;
  border-radius: var(--radius-lg);
  transition: all 0.2s ease;
}

.action-btn:hover {
  transform: translateY(-2px);
  box-shadow: var(--shadow-lg);
}

.action-btn .el-icon {
  font-size: 24px;
}

.action-btn span {
  font-weight: var(--font-weight-medium);
}

/* 老人模式特殊样式 */
.elder-mode .title-text {
  font-size: var(--font-size-2xl);
}

.elder-mode .title-subtitle {
  font-size: var(--font-size-lg);
}

.elder-mode .stat-value {
  font-size: var(--font-size-3xl);
}

.elder-mode .stat-label {
  font-size: var(--font-size-lg);
}

.elder-mode .action-btn {
  min-height: 100px;
  padding: var(--space-8) var(--space-6);
}

.elder-mode .action-btn .el-icon {
  font-size: 32px;
}

.elder-mode .action-btn span {
  font-size: var(--font-size-lg);
}

/* 响应式设计 */
@media (max-width: 1024px) {
  .stats-grid {
    grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
    gap: var(--space-4);
  }

  .chart-container {
    height: 320px;
  }
}

@media (max-width: 768px) {
  .page-header {
    flex-direction: column;
    align-items: flex-start;
    gap: var(--space-4);
  }

  .stats-grid {
    grid-template-columns: 1fr;
  }

  .stat-content {
    padding: var(--space-4);
  }

  .stat-icon {
    width: 50px;
    height: 50px;
  }

  .stat-icon .el-icon {
    font-size: 24px;
  }

  .stat-value {
    font-size: var(--font-size-3xl);
  }

  .actions-grid {
    grid-template-columns: repeat(2, 1fr);
  }

  .action-btn {
    min-height: 70px;
    padding: var(--space-4);
  }
}

@media (max-width: 480px) {
  .actions-grid {
    grid-template-columns: 1fr;
  }

  .chart-container {
    height: 280px;
  }
}
</style>
