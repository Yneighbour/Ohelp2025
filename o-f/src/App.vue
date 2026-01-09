<template>
  <div id="app">
    <header class="app-header">
      <h1>Ohelp2025 - 老年人服务管理系统</h1>
      <nav>
        <router-link to="/auth">认证</router-link> |
        <router-link to="/user">用户管理</router-link> |
        <router-link to="/elder">老人信息</router-link> |
        <router-link to="/activity">活动管理</router-link> |
        <router-link to="/emergency">紧急求助</router-link> |
        <router-link to="/health">健康管理</router-link> |
        <router-link to="/serviceorder">服务订单</router-link> |
        <router-link to="/worker">工作人员</router-link> |
        <router-link to="/file">文件管理</router-link>
      </nav>
    </header>
    <main>
      <router-view />
    </main>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style scoped>
.app-header {
  background-color: #2c3e50;
  color: white;
  padding: 1rem;
  margin-bottom: 2rem;
}

.app-header h1 {
  margin: 0 0 1rem 0;
  font-size: 1.5rem;
}

.app-header nav {
  display: flex;
  gap: 1rem;
  flex-wrap: wrap;
}

.app-header nav a {
  color: #ecf0f1;
  text-decoration: none;
  padding: 0.5rem;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.app-header nav a:hover,
.app-header nav a.router-link-active {
  background-color: #34495e;
}

main {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1rem;
}
</style>