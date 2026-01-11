<template>
  <div class="admin-dashboard">
    <div class="card">
      <h2>系统概览</h2>
      <p class="hint">系统管理视角 · 全局数据概览（不做权限控制）</p>

      <div class="toolbar">
        <button class="btn btn-primary" type="button" @click="loadSummary" :disabled="state.loading">
          {{ state.loading ? '刷新中...' : '刷新概览' }}
        </button>
        <span v-if="state.error" class="error">{{ state.error }}</span>
      </div>

      <section class="kpi-grid" aria-label="关键指标">
        <div class="kpi">
          <div class="kpi-label">用户总数</div>
          <div class="kpi-value">{{ summary.totalUsers }}</div>
        </div>
        <div class="kpi">
          <div class="kpi-label">老人档案</div>
          <div class="kpi-value">{{ summary.totalElderly }}</div>
        </div>
        <div class="kpi warn">
          <div class="kpi-label">待处理求助</div>
          <div class="kpi-value">{{ summary.pendingEmergencies }}</div>
        </div>
        <div class="kpi">
          <div class="kpi-label">活跃订单</div>
          <div class="kpi-value">{{ summary.activeOrders }}</div>
        </div>
      </section>

      <section class="grid" aria-label="管理者关注">
        <div class="panel">
          <div class="panel-title">待处理求助（TOP 5）</div>
          <div v-if="pendingList.length === 0" class="empty">暂无待处理求助，可稍后刷新或前往【紧急求助】查看全部记录</div>
          <table v-else class="table">
            <thead>
              <tr>
                <th>ID</th>
                <th>老人ID</th>
                <th>类型</th>
                <th>优先级</th>
                <th>位置</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="e in pendingList" :key="e.id">
                <td>{{ e.id }}</td>
                <td>{{ e.elderlyId }}</td>
                <td>{{ e.type }}</td>
                <td>{{ e.priority }}</td>
                <td>{{ e.location }}</td>
              </tr>
            </tbody>
          </table>
        </div>

        <div class="panel">
          <div class="panel-title">订单摘要（TOP 5）</div>
          <div v-if="orderList.length === 0" class="empty">暂无订单，可稍后刷新或前往【服务订单】查看全部记录</div>
          <table v-else class="table">
            <thead>
              <tr>
                <th>ID</th>
                <th>老人ID</th>
                <th>服务类型</th>
                <th>服务人员</th>
                <th>状态</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="o in orderList" :key="o.id">
                <td>{{ o.id }}</td>
                <td>{{ o.elderlyId }}</td>
                <td>{{ o.serviceType }}</td>
                <td>{{ o.serviceProviderId ?? '—' }}</td>
                <td>{{ o.status }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
import { getAllUsers } from '../user/user.api.js'
import { getAllElderly } from '../elder/elderly/elderly.api.js'
import { getEmergenciesByStatus } from '../emergency/emergency.api.js'
import { getAllServiceOrders } from '../serviceorder/serviceorder.api.js'

function normalizeStatus(v) {
  return String(v || '').trim().toLowerCase()
}

function isCompletedStatus(v) {
  const s = normalizeStatus(v)
  return (
    s.includes('complete') ||
    s.includes('completed') ||
    s.includes('done') ||
    s.includes('finish') ||
    s.includes('finished') ||
    s.includes('cancel') ||
    s.includes('cancelled') ||
    s.includes('canceled') ||
    s.includes('已完成') ||
    s.includes('已取消')
  )
}

export default {
  name: 'AdminDashboard',
  data() {
    return {
      state: {
        loading: false,
        error: null
      },
      summary: {
        totalUsers: 0,
        totalElderly: 0,
        pendingEmergencies: 0,
        activeOrders: 0
      },
      pendingList: [],
      orderList: []
    }
  },
  mounted() {
    this.loadSummary()
  },
  methods: {
    async loadSummary() {
      this.state.loading = true
      this.state.error = null
      try {
        const [usersRes, elderlyRes, pendingRes, ordersRes] = await Promise.all([
          getAllUsers(),
          getAllElderly(),
          getEmergenciesByStatus('pending'),
          getAllServiceOrders()
        ])

        const users = Array.isArray(usersRes?.data) ? usersRes.data : []
        const elderly = Array.isArray(elderlyRes?.data) ? elderlyRes.data : []
        const pending = Array.isArray(pendingRes?.data) ? pendingRes.data : []
        const orders = Array.isArray(ordersRes?.data) ? ordersRes.data : []

        const activeOrders = orders.filter(o => !isCompletedStatus(o?.status))

        this.summary.totalUsers = users.length
        this.summary.totalElderly = elderly.length
        this.summary.pendingEmergencies = pending.length
        this.summary.activeOrders = activeOrders.length

        const pr = { high: 0, medium: 1, low: 2 }
        this.pendingList = pending
          .slice()
          .sort((a, b) => (pr[normalizeStatus(a?.priority)] ?? 9) - (pr[normalizeStatus(b?.priority)] ?? 9))
          .slice(0, 5)

        this.orderList = orders.slice(0, 5)
      } catch (e) {
        this.state.error = e?.message || '加载失败'
      } finally {
        this.state.loading = false
      }
    }
  }
}
</script>

<style scoped>
.admin-dashboard {
  padding: 1rem 0;
}

.hint {
  color: #64748b;
  margin-top: 6px;
}

.toolbar {
  margin-top: 12px;
  display: flex;
  align-items: center;
  gap: 12px;
}

.error {
  color: #dc2626;
  font-weight: 700;
}

.kpi-grid {
  margin-top: 14px;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 12px;
}

.kpi {
  background: #fff;
  border: 1px solid rgba(0, 0, 0, 0.06);
  border-radius: 14px;
  padding: 12px;
}

.kpi.warn {
  border-color: rgba(220, 38, 38, 0.22);
  background: linear-gradient(180deg, #fff 0%, rgba(254, 242, 242, 0.9) 100%);
}

.kpi-label {
  color: #475569;
  font-weight: 800;
}

.kpi-value {
  margin-top: 8px;
  font-size: 28px;
  font-weight: 900;
  color: #0f172a;
}

.grid {
  margin-top: 14px;
  display: grid;
  gap: 14px;
  grid-template-columns: 1fr;
}

.panel {
  padding: 12px;
  border-radius: 14px;
  background: #fff;
  border: 1px solid rgba(0, 0, 0, 0.06);
}

.panel-title {
  font-weight: 900;
  color: #0f172a;
  margin-bottom: 10px;
}

.empty {
  color: #64748b;
}

@media (min-width: 980px) {
  .kpi-grid {
    grid-template-columns: repeat(4, 1fr);
  }
  .grid {
    grid-template-columns: 1fr 1fr;
  }
}
</style>
