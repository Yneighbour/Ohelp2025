<template>
  <div class="worker-tasks">
    <div class="card">
      <h2>任务中心</h2>
      <p class="hint">任务执行视角 · 我的工作（前端视角过滤，不做权限控制）</p>

      <div class="toolbar">
        <button class="btn btn-primary" type="button" @click="loadAll" :disabled="state.loading">
          {{ state.loading ? '刷新中...' : '刷新任务' }}
        </button>
        <span v-if="state.error" class="error">{{ state.error }}</span>
      </div>

      <div class="grid">
        <div class="panel">
          <div class="panel-title">待处理求助</div>
          <div v-if="tasks.pendingEmergencies.length === 0" class="empty">
            暂无可执行求助任务，可稍后刷新或前往【处理记录】查看历史处理
          </div>
          <table v-else class="table">
            <thead>
              <tr>
                <th>ID</th>
                <th>老人ID</th>
                <th>类型</th>
                <th>优先级</th>
                <th>位置</th>
                <th>状态</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="e in tasks.pendingEmergencies" :key="e.id">
                <td>{{ e.id }}</td>
                <td>{{ e.elderlyId }}</td>
                <td>{{ e.type }}</td>
                <td>{{ e.priority }}</td>
                <td>{{ e.location }}</td>
                <td>{{ e.status }}</td>
              </tr>
            </tbody>
          </table>
        </div>

        <div class="panel">
          <div class="panel-title">进行中订单</div>
          <div v-if="tasks.activeOrders.length === 0" class="empty">
            暂无进行中订单，可稍后刷新或前往【处理记录】查看已完成订单
          </div>
          <table v-else class="table">
            <thead>
              <tr>
                <th>ID</th>
                <th>老人ID</th>
                <th>服务类型</th>
                <th>价格</th>
                <th>状态</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="o in tasks.activeOrders" :key="o.id">
                <td>{{ o.id }}</td>
                <td>{{ o.elderlyId }}</td>
                <td>{{ o.serviceType }}</td>
                <td>{{ o.price }}</td>
                <td>{{ o.status }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getAllEmergencies } from '../emergency/emergency.api.js'
import { getServiceOrdersByProvider } from '../serviceorder/serviceorder.api.js'

// WORKER 相关任务规则（显性化，不改变现有行为）：
// 1) 求助任务：展示所有 pending；若已登录且有 userId，则额外展示 responderId==userId 且未 resolved 的记录。
// 2) 服务订单：仅展示“我的订单”（provider 接口），并在前端过滤掉“已完成/已取消”等结束态。

const WORKER_TASK_RULES = Object.freeze({
  emergency: {
    includeAllPending: true,
    includeMyUnresolvedWhenLoggedIn: true,
    unresolvedStatus: 'resolved'
  },
  serviceOrder: {
    scope: 'provider',
    maxRows: 50
  }
})

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
  name: 'WorkerTasks',
  data() {
    return {
      userId: Number(localStorage.getItem('userId')) || null,
      state: {
        loading: false,
        error: null
      },
      tasks: {
        pendingEmergencies: [],
        activeOrders: []
      }
    }
  },
  mounted() {
    this.loadAll()
  },
  methods: {
    async loadAll() {
      this.state.loading = true
      this.state.error = null
      try {
        const [emRes, orderRes] = await Promise.all([
          getAllEmergencies(),
          this.userId ? getServiceOrdersByProvider(this.userId) : Promise.resolve({ data: [] })
        ])

        const emergencies = Array.isArray(emRes?.data) ? emRes.data : []
        const orders = Array.isArray(orderRes?.data) ? orderRes.data : []

        const pendingEmergencies = emergencies
          .filter(e => {
            const s = normalizeStatus(e?.status)
            if (WORKER_TASK_RULES.emergency.includeAllPending && s === 'pending') return true
            if (!this.userId) return false
            if (!WORKER_TASK_RULES.emergency.includeMyUnresolvedWhenLoggedIn) return false
            return Number(e?.responderId) === this.userId && s !== WORKER_TASK_RULES.emergency.unresolvedStatus
          })
          .sort((a, b) => {
            const pr = { high: 0, medium: 1, low: 2 }
            return (pr[normalizeStatus(a?.priority)] ?? 9) - (pr[normalizeStatus(b?.priority)] ?? 9)
          })

        const activeOrders = orders
          .filter(o => !isCompletedStatus(o?.status))
          .slice(0, WORKER_TASK_RULES.serviceOrder.maxRows)

        this.tasks.pendingEmergencies = pendingEmergencies
        this.tasks.activeOrders = activeOrders
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
.worker-tasks {
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

.grid {
  margin-top: 16px;
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
  .grid {
    grid-template-columns: 1fr 1fr;
  }
}
</style>
