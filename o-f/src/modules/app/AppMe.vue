<template>
  <div class="page">
    <h2 class="title">我的</h2>
    <p class="hint">这里展示个人信息与退出入口（占位页面，不调用真实 API）。</p>

    <div class="card">
      <div class="row"><span>当前身份</span><span>老人/家属</span></div>
      <div class="row"><span>联系电话</span><span>138****0000</span></div>
      <button class="logout" type="button" @click="logout">退出登录</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AppMe',
  methods: {
    logout() {
      localStorage.removeItem('token')
      localStorage.removeItem('role')
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
