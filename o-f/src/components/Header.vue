<template>
  <header class="site-header">
    <nav class="nav">
      <a href="/index.html">主页</a>
      <a href="/dashboard.html">仪表盘</a>
      <a href="/Laoren.html">老人档案</a>
      <a href="/requests.html">服务请求</a>
      <div style="margin-left:12px; display:flex; align-items:center; gap:8px;">
        <span style="color:#fff; font-size:13px">老人模式</span>
        <el-switch v-model="isElder" active-color="#13ce66" inactive-color="#409EFF" />
      </div>
    </nav>
  </header>
</template>

<script>
import { defineComponent, computed } from 'vue'
import { useUserStore } from '../store/user'
import { ElSwitch } from 'element-plus'

export default defineComponent({
  name: 'SiteHeader',
  components: { ElSwitch },
  setup() {
    const store = useUserStore()
    // ensure body class sync on load
    if (store.uiMode === 'elder') document.body.classList.add('elder-mode')
    const isElder = computed({
      get: () => store.uiMode === 'elder',
      set: v => store.setUIMode(v ? 'elder' : 'admin')
    })
    return { isElder }
  }
})
</script>
