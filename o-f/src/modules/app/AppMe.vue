<template>
  <div class="page">
    <h2 class="title">我的</h2>
    <p class="hint">{{ subtitle }}</p>

    <div class="card">
      <div class="row"><span>当前视角</span><span>{{ viewLabel }}</span></div>
      <div class="row"><span>姓名</span><span>{{ displayName }}</span></div>
      <div class="row"><span>联系方式</span><span>{{ displayContact }}</span></div>
      <div class="row" v-if="bindingPrimary"><span>关联信息</span><span>{{ bindingPrimary }}</span></div>
      <div class="row" v-if="bindingSecondary"><span>更多关联</span><span>{{ bindingSecondary }}</span></div>
      <button class="logout" type="button" @click="logout">退出登录</button>
    </div>
  </div>
</template>

<script>
import { getUser } from '../user/user.api.js'
import { getAllElderly } from '../elder/elderly/elderly.api.js'
import { getAllRelatives } from '../elder/relative/relative.api.js'

export default {
  name: 'AppMe',
  data() {
    return {
      userView: 'generic',
      currentUser: null,
      boundElderly: null,
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
    viewLabel() {
      if (this.userView === 'elderly') return '老人视角'
      if (this.userView === 'family') return '家属视角'
      return '通用 USER 视角'
    },
    subtitle() {
      if (this.state.error) return `加载信息失败：${this.state.error}`
      if (this.userView === 'elderly') return '老人视角 · 展示我的信息与绑定关系（只读）'
      if (this.userView === 'family') return '家属视角 · 展示我的信息与关注关系（只读）'
      return '通用 USER 视角 · 未绑定老人/家属时仅展示基础信息（只读）'
    },
    displayName() {
      return this.currentUser?.name || '—'
    },
    displayContact() {
      const phone = this.currentUser?.phone
      const email = this.currentUser?.email
      if (phone) return phone
      if (email) return email
      return '—'
    },
    bindingPrimary() {
      if (this.userView === 'elderly' && this.boundElderly) return `老人：${this.boundElderly.name}`
      if (this.userView === 'family' && this.boundRelative) {
        const rel = this.boundRelative.relationship ? `（${this.boundRelative.relationship}）` : ''
        return `家属：${this.boundRelative.name}${rel}`
      }
      return ''
    },
    bindingSecondary() {
      if (this.userView === 'family') {
        const e = this.focusedElderly
        if (!e) return ''
        const pieces = []
        if (e.name) pieces.push(`关注老人：${e.name}`)
        if (e.healthStatus) pieces.push(`健康：${e.healthStatus}`)
        return pieces.join('；')
      }
      return ''
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

        if (elderlyMatch) {
          this.userView = 'elderly'
          this.boundElderly = elderlyMatch
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
          this.boundElderly = null
          this.focusedElderly = allElderly.find(e => e?.id === relativeMatch.elderlyId) || null
          return
        }

        this.userView = 'generic'
        this.boundElderly = null
        this.boundRelative = null
        this.focusedElderly = null
      } catch (e) {
        this.userView = 'generic'
        this.boundElderly = null
        this.boundRelative = null
        this.focusedElderly = null
        this.state.error = e?.message || '未知错误'
      } finally {
        this.state.loading = false
      }
    },
    logout() {
      localStorage.removeItem('token')
      localStorage.removeItem('role')
      localStorage.removeItem('userId')
      this.$router.replace('/login')
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
  gap: 10px;
}

.row {
  display: flex;
  justify-content: space-between;
  padding: 10px 0;
  border-bottom: 1px dashed rgba(0, 0, 0, 0.08);
  font-size: 16px;
}

.row:last-of-type {
  border-bottom: none;
}

.row span:first-child {
  color: #334155;
  font-weight: 700;
}

.logout {
  margin-top: 8px;
  height: 48px;
  border-radius: 14px;
  border: 1px solid rgba(220, 38, 38, 0.25);
  background: #fff;
  color: #dc2626;
  font-size: 16px;
  font-weight: 900;
}
</style>
