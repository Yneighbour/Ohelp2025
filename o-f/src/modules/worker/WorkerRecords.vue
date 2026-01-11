<template>
  <div class="worker-records">
    <div class="card">
      <h2>处理记录</h2>
      <p class="hint">任务执行视角 · 我的工作（已处理记录聚合，不做权限控制）</p>

      <div class="toolbar">
        <button class="btn btn-primary" type="button" @click="loadAll" :disabled="state.loading">
          {{ state.loading ? '刷新中...' : '刷新记录' }}
        </button>
        <span v-if="state.error" class="error">{{ state.error }}</span>
      </div>

      <div class="grid">
        <div class="panel">
          <div class="panel-title">求助处理记录</div>
          <div v-if="records.emergencies.length === 0" class="empty">暂无处理记录，可前往【任务中心】查看待处理事项</div>
          <table v-else class="table">
            <thead>
              <tr>
                <th>ID</th>
                <th>老人ID</th>
                <th>类型</th>
                <th>状态</th>
                <th>响应人</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="e in records.emergencies" :key="e.id">
                <td>{{ e.id }}</td>
                <td>{{ e.elderlyId }}</td>
                <td>{{ e.type }}</td>
                <td>{{ e.status }}</td>
                <td>{{ e.responderId || '—' }}</td>
              </tr>
            </tbody>
          </table>
        </div>

        <div class="panel">
          <div class="panel-title">订单完成记录</div>
          <div v-if="records.orders.length === 0" class="empty">暂无完成订单记录，可前往【任务中心】查看进行中订单</div>
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
              <tr v-for="o in records.orders" :key="o.id">
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

// WORKER 相关记录规则（显性化，不改变现有行为）：
// 1) 求助记录：排除 pending；若缺少 userId 则展示所有非 pending；若有 userId 则仅展示 responderId==userId。
// 2) 订单记录：仅展示“我的订单”（provider 接口）中处于“已完成/已取消”等结束态的记录。

const WORKER_RECORD_RULES = Object.freeze({
  emergency: {
    excludeStatus: 'pending',
    showAllWhenNoUserId: true,
    showOnlyMyWhenHasUserId: true
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
  name: 'WorkerRecords',
  data() {
    return {
      userId: Number(localStorage.getItem('userId')) || null,
      state: {
        loading: false,
        error: null
      },
      records: {
        emergencies: [],
        orders: []
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

        const handledEmergencies = emergencies
          .filter(e => {
            const s = normalizeStatus(e?.status)
            if (s === WORKER_RECORD_RULES.emergency.excludeStatus) return false
            if (!this.userId) return WORKER_RECORD_RULES.emergency.showAllWhenNoUserId
            if (!WORKER_RECORD_RULES.emergency.showOnlyMyWhenHasUserId) return true
            return Number(e?.responderId) === this.userId
          })
          .slice(0, WORKER_RECORD_RULES.serviceOrder.maxRows)

        const completedOrders = orders
          .filter(o => isCompletedStatus(o?.status))
          .slice(0, WORKER_RECORD_RULES.serviceOrder.maxRows)

        this.records.emergencies = handledEmergencies
        this.records.orders = completedOrders
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
.worker-records {
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
