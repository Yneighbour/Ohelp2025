<template>
  <div class="page">
    <SiteHeader />
    <main class="container">
      <h1>服务项目管理</h1>
      <p>管理具体服务项目（名称、类型、价格、数量、详情等）</p>
    </main>
    <SiteFooter />
  </div>
</template>

<script>
import SiteHeader from '../components/Header.vue'
import SiteFooter from '../components/Footer.vue'
export default { components: { SiteHeader, SiteFooter } }
</script>