<template>
  <div class="page">
    <SiteHeader />
    <main class="container">
      <h1>服务购买管理</h1>
      <p>处理服务购买订单</p>
    </main>
    <SiteFooter />
  </div>
</template>

<script>
import SiteHeader from '../components/Header.vue'
import SiteFooter from '../components/Footer.vue'
export default { components: { SiteHeader, SiteFooter } }
</script>