<template>
  <div class="page">
    <SiteHeader />
    <main class="container">
      <h1>仪表盘</h1>
      <p>关键指标、服务概览</p>

      <div style="display:flex; gap:16px; margin:16px 0; flex-wrap:wrap">
        <el-card style="flex:1; min-width:160px; text-align:center">
          <div style="font-size:20px; font-weight:600">{{ totalElderly }}</div>
          <div style="color:#888; margin-top:6px">老人总数</div>
        </el-card>
        <el-card style="flex:1; min-width:160px; text-align:center">
          <div style="font-size:20px; font-weight:600">{{ todayRequests }}</div>
          <div style="color:#888; margin-top:6px">今日服务请求</div>
        </el-card>
        <el-card style="flex:1; min-width:160px; text-align:center">
          <div style="font-size:20px; font-weight:600">{{ monthCompleteRate }}%</div>
          <div style="color:#888; margin-top:6px">本月完成率</div>
        </el-card>
      </div>

      <div id="chart" style="width:100%;height:360px;margin-top:12px;background:#fff;border-radius:6px;padding:12px"></div>
    </main>
    <SiteFooter />
  </div>
</template>

<script>
import SiteHeader from '../components/Header.vue'
import SiteFooter from '../components/Footer.vue'
import * as echarts from 'echarts'
import { ref, onMounted } from 'vue'

export default {
  components: { SiteHeader, SiteFooter },
  setup() {
    const totalElderly = ref(128)
    const todayRequests = ref(12)
    const monthCompleteRate = ref(92)

    onMounted(() => {
      const chartDom = document.getElementById('chart')
      if (!chartDom) return
      const chart = echarts.init(chartDom)
      const option = {
        title: { text: '近7天服务请求趋势', left: 'center' },
        tooltip: { trigger: 'axis' },
        xAxis: { type: 'category', data: ['1','2','3','4','5','6','7'] },
        yAxis: { type: 'value' },
        series: [{ name: '请求数', type: 'line', data: [8,12,10,15,9,11,12], smooth:true }]
      }
      chart.setOption(option)
      window.addEventListener('resize', () => chart.resize())
    })

    return { totalElderly, todayRequests, monthCompleteRate }
  }
}
</script>
