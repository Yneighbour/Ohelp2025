<template>
  <div class="page">
    <SiteHeader />
    <main class="container">
      <h1>活动信息管理</h1>
      <p>管理活动详情（名称、类别、地点、时间、费用、详情等）</p>
    </main>
    <SiteFooter />
  </div>
</template>

<script>
import SiteHeader from '../components/Header.vue'
import SiteFooter from '../components/Footer.vue'
export default { components: { SiteHeader, SiteFooter } }
</script>