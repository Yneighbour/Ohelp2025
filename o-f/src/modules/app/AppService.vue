<template>
  <div class="page">
    <h2 class="title">{{ pageTitle }}</h2>
    <p class="hint">{{ subtitle }}</p>

    <div class="card">
      <div class="item">
        <div class="name">上门护理</div>
        <div class="meta">状态：进行中</div>
      </div>
      <div class="item">
        <div class="name">陪诊服务</div>
        <div class="meta">状态：已完成（可评价）</div>
      </div>
    </div>
  </div>
</template>

<script>
import { getUser } from '../user/user.api.js'
import { getAllElderly } from '../elder/elderly/elderly.api.js'
import { getAllRelatives } from '../elder/relative/relative.api.js'

export default {
  name: 'AppService',
  data() {
    return {
      userView: 'generic',
      currentUser: null,
      boundRelative: null,
      focusedElderly: null,
      state: {
        loading: false,
        error: null
      }
    }
  },
  computed: {
    currentUserId() {
      const raw = localStorage.getItem('userId')
      const parsed = raw ? parseInt(raw, 10) : NaN
      return Number.isFinite(parsed) ? parsed : null
    },
    pageTitle() {
      if (this.userView === 'elderly') return '我的服务'
      if (this.userView === 'family') return '代办服务'
      return '服务'
    },
    subtitle() {
      if (this.userView === 'elderly') return '老人视角 · 这里展示我的服务订单摘要与评价入口（占位页面，不调用真实 API）。'
      if (this.userView === 'family') {
        const name = this.focusedElderly?.name
        return name
          ? `家属视角 · 这里展示关注老人（${name}）的服务订单摘要与评价入口（占位页面，不调用真实 API）。`
          : '家属视角 · 这里展示关注老人的服务订单摘要与评价入口（占位页面，不调用真实 API）。'
      }
      return '通用 USER 视角 · 这里展示我的服务订单摘要与评价入口（占位页面，不调用真实 API）。'
    }
  },
  async mounted() {
    await this.resolveBindingView()
  },
  methods: {
    normalizePhone(v) {
      return String(v || '').replace(/\D/g, '')
    },
    async resolveBindingView() {
      if (!this.currentUserId) {
        this.userView = 'generic'
        return
      }

      this.state.loading = true
      this.state.error = null

      try {
        const userRes = await getUser(this.currentUserId)
        const user = userRes.data || null
        this.currentUser = user

        const [elderlyRes, relativeRes] = await Promise.all([
          getAllElderly(),
          getAllRelatives()
        ])

        const allElderly = Array.isArray(elderlyRes.data) ? elderlyRes.data : []
        const allRelatives = Array.isArray(relativeRes.data) ? relativeRes.data : []

        const userPhone = this.normalizePhone(user?.phone)
        const userEmail = (user?.email || '').toLowerCase()

        const elderlyMatch = userPhone
          ? allElderly.find(e => this.normalizePhone(e?.phoneNumber) === userPhone)
          : null

        if (elderlyMatch?.id) {
          this.userView = 'elderly'
          this.boundRelative = null
          this.focusedElderly = elderlyMatch
          return
        }

        const relativeMatch = allRelatives.find(r => {
          if (!r) return false
          const phoneMatch = userPhone && this.normalizePhone(r.phone) === userPhone
          const emailMatch = userEmail && String(r.email || '').toLowerCase() === userEmail
          return phoneMatch || emailMatch
        })

        if (relativeMatch) {
          this.userView = 'family'
          this.boundRelative = relativeMatch
          this.focusedElderly = allElderly.find(e => e?.id === relativeMatch.elderlyId) || null
          return
        }

        this.userView = 'generic'
        this.boundRelative = null
        this.focusedElderly = null
      } catch (e) {
        this.userView = 'generic'
        this.boundRelative = null
        this.focusedElderly = null
        this.state.error = e?.message || '加载绑定关系失败'
      } finally {
        this.state.loading = false
      }
    }
  }
}
</script>

<style scoped>
.page {
  max-width: 960px;
  margin: 0 auto;
}

.title {
  margin: 0 0 10px;
  font-size: 20px;
  font-weight: 900;
  color: #0f172a;
}

.hint {
  margin: 0 0 14px;
  color: #475569;
  font-size: 15px;
}

.card {
  background: #fff;
  border: 1px solid rgba(0, 0, 0, 0.06);
  border-radius: 16px;
  padding: 14px;
  box-shadow: 0 8px 24px rgba(15, 23, 42, 0.06);
  display: grid;
  gap: 12px;
}

.item {
  padding: 12px;
  border-radius: 14px;
  background: #f8fafc;
  border: 1px solid rgba(0, 0, 0, 0.05);
}

.name {
  font-size: 17px;
  font-weight: 900;
  color: #0f172a;
}

.meta {
  margin-top: 6px;
  color: #334155;
  font-size: 15px;
}
</style>
