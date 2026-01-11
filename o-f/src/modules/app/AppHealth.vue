<template>
  <div class="page">
    <h2 class="title">健康</h2>
    <p class="hint">{{ subtitle }}</p>

    <div class="card">
      <div class="row"><span>今日血压</span><span>{{ latestBloodPressure }}</span></div>
      <div class="row"><span>今日心率</span><span>{{ latestHeartRate }}</span></div>
      <div class="row"><span>体温</span><span>{{ latestTemperature }}</span></div>
    </div>

    <p v-if="state.error" class="error">{{ state.error }}</p>
  </div>
</template>

<script>
import { getUser } from '../user/user.api.js'
import { getAllElderly } from '../elder/elderly/elderly.api.js'
import { getAllRelatives } from '../elder/relative/relative.api.js'
import { getHealthRecordsByElderlyId } from '../health/health.api.js'

export default {
  name: 'AppHealth',
  data() {
    return {
      userView: 'generic',
      currentUser: null,
      boundElderlyId: null,
      records: [],
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
    subtitle() {
      if (this.userView === 'elderly') return '老人视角 · 我的健康记录（默认按绑定老人加载）'
      if (this.userView === 'family') return '家属视角 · 关注老人健康（默认按绑定关系加载）'
      return '通用 USER 视角 · 未绑定老人/家属时仅展示占位数据'
    },
    latestRecord() {
      const arr = Array.isArray(this.records) ? this.records : []
      if (arr.length === 0) return null

      const withDate = arr.filter(r => r && r.recordDate)
      if (withDate.length > 0) {
        const sorted = [...withDate].sort((a, b) => String(b.recordDate).localeCompare(String(a.recordDate)))
        return sorted[0]
      }

      const sorted = [...arr].filter(Boolean).sort((a, b) => (b.id ?? 0) - (a.id ?? 0))
      return sorted[0] || null
    },
    latestBloodPressure() {
      return this.latestRecord?.bloodPressure || '—'
    },
    latestHeartRate() {
      const v = this.latestRecord?.heartRate
      return Number.isFinite(v) ? String(v) : (v ?? '—')
    },
    latestTemperature() {
      const v = this.latestRecord?.temperature
      return Number.isFinite(v) ? `${v}℃` : (v ? `${v}℃` : '—')
    }
  },
  async mounted() {
    await this.resolveBindingAndLoad()
  },
  methods: {
    normalizePhone(v) {
      return String(v || '').replace(/\D/g, '')
    },
    async resolveBindingAndLoad() {
      if (!this.currentUserId) {
        this.userView = 'generic'
        this.boundElderlyId = null
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
          this.boundElderlyId = elderlyMatch.id
        } else {
          const relativeMatch = allRelatives.find(r => {
            if (!r) return false
            const phoneMatch = userPhone && this.normalizePhone(r.phone) === userPhone
            const emailMatch = userEmail && String(r.email || '').toLowerCase() === userEmail
            return phoneMatch || emailMatch
          })

          if (relativeMatch?.elderlyId) {
            this.userView = 'family'
            this.boundElderlyId = relativeMatch.elderlyId
          } else {
            this.userView = 'generic'
            this.boundElderlyId = null
          }
        }

        if (!this.boundElderlyId) {
          this.records = []
          return
        }

        const res = await getHealthRecordsByElderlyId(this.boundElderlyId)
        this.records = Array.isArray(res.data) ? res.data : []
      } catch (e) {
        this.userView = 'generic'
        this.boundElderlyId = null
        this.records = []
        this.state.error = e?.message || '加载健康数据失败'
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
}

.row {
  display: flex;
  justify-content: space-between;
  padding: 10px 0;
  border-bottom: 1px dashed rgba(0, 0, 0, 0.08);
  font-size: 16px;
}

.row:last-child {
  border-bottom: none;
}

.row span:first-child {
  color: #334155;
  font-weight: 700;
}

.error {
  margin-top: 12px;
  color: #b91c1c;
  font-size: 14px;
}
</style>
