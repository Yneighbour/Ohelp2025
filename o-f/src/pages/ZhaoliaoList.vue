<template>
  <div class="page">
    <SiteHeader />
    <main class="container">
      <h1>照料计划管理</h1>
      <p>创建照料计划（每日送餐，洗浴）</p>
    </main>
    <SiteFooter />
  </div>
</template>

<script>
import SiteHeader from '../components/Header.vue'
import SiteFooter from '../components/Footer.vue'
export default { components: { SiteHeader, SiteFooter } }
</script>