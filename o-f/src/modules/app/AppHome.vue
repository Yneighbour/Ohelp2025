<template>
  <div class="home">
    <section class="grid">
      <div class="card">
        <div class="card-title">我的信息</div>
        <div class="card-desc">（老人/家属）姓名、联系方式、绑定关系</div>
        <div class="card-body">
          <div class="kv"><span>姓名</span><span>张三</span></div>
          <div class="kv"><span>联系方式</span><span>138****0000</span></div>
        </div>
      </div>

      <div class="card">
        <div class="card-title">今日健康概览</div>
        <div class="card-desc">今日状态一目了然</div>
        <div class="card-body">
          <div class="pill">血压：120/80</div>
          <div class="pill">心率：72</div>
          <div class="pill">睡眠：7.5h</div>
        </div>
      </div>

      <div class="card">
        <div class="card-title">我的服务订单（摘要）</div>
        <div class="card-desc">近期服务进度</div>
        <div class="card-body">
          <div class="kv"><span>进行中</span><span>1 单</span></div>
          <div class="kv"><span>已完成</span><span>3 单</span></div>
        </div>
      </div>

      <div class="card emergency">
        <div class="card-title">紧急求助</div>
        <div class="card-desc">遇到紧急情况请立即使用</div>
        <button class="emergency-btn" type="button" @click="onEmergency">
          一键求助
        </button>
        <div class="tip">（演示占位：不调用真实接口）</div>
      </div>
    </section>
  </div>
</template>

<script>
export default {
  name: 'AppHome',
  methods: {
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
