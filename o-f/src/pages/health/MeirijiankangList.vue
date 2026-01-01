<template>
  <div class="page">
    <SiteHeader />
    <main class="container">
      <h1>每日健康管理</h1>
      <p>管理健康资讯内容</p>
    </main>
    <SiteFooter />
  </div>
</template>

<script>
import SiteHeader from '../components/Header.vue'
import SiteFooter from '../components/Footer.vue'
export default { components: { SiteHeader, SiteFooter } }
</script>