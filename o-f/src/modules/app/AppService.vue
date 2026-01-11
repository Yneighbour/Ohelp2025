<template>
  <div class="page">
    <h2 class="title">服务</h2>
    <p class="hint">这里展示我的服务订单摘要与评价入口（占位页面，不调用真实 API）。</p>

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
export default {
  name: 'AppService'
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
