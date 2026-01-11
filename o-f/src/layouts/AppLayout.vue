<template>
  <div class="app-shell">
    <header class="app-header">
      <div class="title">Ohelp2025 智慧养老服务</div>
      <div class="subtitle">个人使用视角 · 我的服务与健康</div>
    </header>

    <main class="app-main">
      <router-view />
    </main>

    <nav class="app-nav" aria-label="底部导航">
      <router-link class="nav-item" to="/app/home">
        <span class="nav-text">首页</span>
      </router-link>
      <router-link class="nav-item" to="/app/health">
        <span class="nav-text">健康</span>
      </router-link>
      <router-link class="nav-item" to="/app/service">
        <span class="nav-text">服务</span>
      </router-link>
      <router-link class="nav-item" to="/app/me">
        <span class="nav-text">我的</span>
      </router-link>
    </nav>
  </div>
</template>

<script>
export default {
  name: 'AppLayout'
}
</script>

<style scoped>
.app-shell {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background: #f6f7fb;
}

.app-header {
  padding: 16px 16px 12px;
  background: linear-gradient(135deg, #1e40af 0%, #2563eb 50%, #60a5fa 100%);
  color: #fff;
}

.title {
  font-size: 20px;
  font-weight: 800;
  letter-spacing: 0.5px;
}

.subtitle {
  margin-top: 6px;
  font-size: 14px;
  opacity: 0.92;
}

.app-main {
  flex: 1;
  padding: 16px;
  padding-bottom: 88px;
}

.app-nav {
  position: fixed;
  left: 0;
  right: 0;
  bottom: 0;
  height: 72px;
  background: rgba(255, 255, 255, 0.96);
  border-top: 1px solid rgba(0, 0, 0, 0.06);
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  backdrop-filter: blur(10px);
}

.nav-item {
  display: flex;
  align-items: center;
  justify-content: center;
  text-decoration: none;
  color: #334155;
  font-size: 16px;
  font-weight: 700;
}

.nav-item.router-link-active {
  color: #1d4ed8;
}

.nav-text {
  line-height: 1;
}

@media (max-width: 480px) {
  .title {
    font-size: 18px;
  }
  .nav-item {
    font-size: 15px;
  }
}
</style>
