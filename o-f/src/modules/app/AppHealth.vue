<template>
  <div class="page">
    <h2 class="title">健康</h2>
    <p class="hint">这里展示健康数据与趋势（占位页面，不调用真实 API）。</p>

    <div class="card">
      <div class="row"><span>今日血压</span><span>120/80</span></div>
      <div class="row"><span>今日心率</span><span>72</span></div>
      <div class="row"><span>步数</span><span>5600</span></div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AppHealth'
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
</style>
