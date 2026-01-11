<template>
  <div class="home">
    <section class="grid">
      <div class="card">
        <div class="card-title">{{ infoCard.title }}</div>
        <div class="card-desc">{{ infoCard.desc }}</div>
        <div class="card-body">
          <div class="kv"><span>姓名</span><span>{{ displayName }}</span></div>
          <div class="kv"><span>联系方式</span><span>{{ displayContact }}</span></div>
          <div class="kv"><span>当前视角</span><span>{{ viewLabel }}</span></div>
          <div class="kv" v-if="bindingSummary"><span>关联信息</span><span>{{ bindingSummary }}</span></div>
        </div>
      </div>

      <div class="card">
        <div class="card-title">{{ focusCard.title }}</div>
        <div class="card-desc">{{ focusCard.desc }}</div>
        <div class="card-body">
          <template v-if="userView === 'family'">
            <div class="kv"><span>关注老人</span><span>{{ focusedElderlyName }}</span></div>
            <div class="kv"><span>健康状态</span><span>{{ focusedElderlyHealth }}</span></div>
            <div class="kv"><span>住址</span><span>{{ focusedElderlyAddress }}</span></div>
          </template>
          <template v-else>
            <div class="pill">血压：120/80</div>
            <div class="pill">心率：72</div>
            <div class="pill">睡眠：7.5h</div>
          </template>
        </div>
      </div>

      <div class="card">
        <div class="card-title">{{ serviceCard.title }}</div>
        <div class="card-desc">{{ serviceCard.desc }}</div>
        <div class="card-body">
          <div class="kv"><span>进行中</span><span>1 单</span></div>
          <div class="kv"><span>已完成</span><span>3 单</span></div>
        </div>
      </div>

      <div class="card emergency">
        <div class="card-title">{{ emergencyCard.title }}</div>
        <div class="card-desc">{{ emergencyCard.desc }}</div>
        <button class="emergency-btn" type="button" @click="onEmergency">
          一键求助
        </button>
        <div class="tip">（演示占位：不调用真实接口）</div>
      </div>
    </section>
  </div>
</template>

<script>
import { getUser } from '../user/user.api.js'
import { getAllElderly } from '../elder/elderly/elderly.api.js'
import { getAllRelatives } from '../elder/relative/relative.api.js'

export default {
  name: 'AppHome',
  data() {
    return {
      bindingState: {
        loading: false,
        error: null
      },
      currentUser: null,
      userView: 'generic',
      boundElderly: null,
      boundRelative: null,
      focusedElderly: null
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
    bindingSummary() {
      if (this.userView === 'elderly' && this.boundElderly) return `老人：${this.boundElderly.name}`
      if (this.userView === 'family' && this.boundRelative) {
        const rel = this.boundRelative.relationship ? `（${this.boundRelative.relationship}）` : ''
        return `家属：${this.boundRelative.name}${rel}`
      }
      return ''
    },
    focusedElderlyName() {
      return this.focusedElderly?.name || '—'
    },
    focusedElderlyHealth() {
      return this.focusedElderly?.healthStatus || '—'
    },
    focusedElderlyAddress() {
      return this.focusedElderly?.address || '—'
    },
    infoCard() {
      if (this.userView === 'elderly') {
        return { title: '我的信息（老人）', desc: '个人信息与绑定关系（只读展示）' }
      }
      if (this.userView === 'family') {
        return { title: '我的信息（家属）', desc: '家属信息与关注关系（只读展示）' }
      }
      return { title: '我的信息', desc: '姓名、联系方式、绑定关系（只读展示）' }
    },
    focusCard() {
      if (this.userView === 'elderly') {
        return { title: '我的健康（概览）', desc: '关注身体状态，异常及时求助' }
      }
      if (this.userView === 'family') {
        return { title: '关注的老人', desc: '优先展示我所关注老人的关键信息' }
      }
      return { title: '今日健康概览', desc: '今日状态一目了然' }
    },
    serviceCard() {
      if (this.userView === 'family') {
        return { title: '代办服务（摘要）', desc: '以关注老人服务为主（功能不分裂）' }
      }
      return { title: '我的服务订单（摘要）', desc: '近期服务进度' }
    },
    emergencyCard() {
      if (this.userView === 'elderly') {
        return { title: '紧急求助', desc: '身体不适/跌倒等紧急情况可立即发起' }
      }
      if (this.userView === 'family') {
        return { title: '紧急求助（关注老人）', desc: '为关注老人快速发起求助（演示占位）' }
      }
      return { title: '紧急求助', desc: '遇到紧急情况请立即使用' }
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

      this.bindingState.loading = true
      this.bindingState.error = null

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
        this.bindingState.error = e?.message || '加载绑定关系失败'
      } finally {
        this.bindingState.loading = false
      }
    },
    onEmergency() {
      // 仅 UI 占位：不调用任何真实 API
      alert('已触发紧急求助（演示占位）')
    }
  }
}
</script>

<style scoped>
.home {
  max-width: 960px;
  margin: 0 auto;
}

.grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: 14px;
}

.card {
  background: #fff;
  border: 1px solid rgba(0, 0, 0, 0.06);
  border-radius: 16px;
  padding: 14px;
  box-shadow: 0 8px 24px rgba(15, 23, 42, 0.06);
}

.card-title {
  font-size: 18px;
  font-weight: 900;
  color: #0f172a;
}

.card-desc {
  margin-top: 6px;
  font-size: 14px;
  color: #475569;
}

.card-body {
  margin-top: 12px;
  display: grid;
  gap: 10px;
}

.kv {
  display: flex;
  justify-content: space-between;
  font-size: 16px;
  color: #0f172a;
}

.kv span:first-child {
  color: #334155;
  font-weight: 700;
}

.pill {
  display: inline-flex;
  padding: 10px 12px;
  border-radius: 999px;
  background: #eff6ff;
  color: #1d4ed8;
  font-weight: 800;
  font-size: 15px;
}

.emergency {
  border-color: rgba(220, 38, 38, 0.18);
  background: linear-gradient(180deg, #fff 0%, rgba(254, 242, 242, 0.9) 100%);
}

.emergency-btn {
  margin-top: 12px;
  width: 100%;
  height: 56px;
  border-radius: 14px;
  border: none;
  background: linear-gradient(135deg, #dc2626 0%, #ef4444 60%, #fb7185 100%);
  color: #fff;
  font-size: 18px;
  font-weight: 900;
  letter-spacing: 1px;
  box-shadow: 0 10px 28px rgba(220, 38, 38, 0.30);
}

.emergency-btn:active {
  transform: translateY(1px);
}

.tip {
  margin-top: 10px;
  font-size: 13px;
  color: #64748b;
}
</style>
