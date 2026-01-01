<template>
  <div class="page">
    <SiteHeader />
    <main class="container">
      <h1>用户管理</h1>
      <p>用户登录、注册、登出、密码重置、用户信息管理</p>
    </main>
    <SiteFooter />
  </div>
</template>

<script>
import SiteHeader from '../components/Header.vue'
import SiteFooter from '../components/Footer.vue'
export default { components: { SiteHeader, SiteFooter } }
</script>