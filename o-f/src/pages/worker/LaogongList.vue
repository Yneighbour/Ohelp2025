<template>
  <div class="page">
    <SiteHeader />
    <main class="container">
      <h1>老工管理</h1>
      <p>管理工作人员信息</p>
    </main>
    <SiteFooter />
  </div>
</template>

<script>
import SiteHeader from '../components/Header.vue'
import SiteFooter from '../components/Footer.vue'
export default { components: { SiteHeader, SiteFooter } }
</script>