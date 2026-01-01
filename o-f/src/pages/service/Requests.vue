<template>
  <div class="page">
    <SiteHeader />
    <main class="container">
      <h1>服务请求</h1>
      <p>示例：请求列表、状态跟踪</p>
    </main>
    <SiteFooter />
  </div>
</template>

<script>
import SiteHeader from '../../components/Header.vue'
import SiteFooter from '../../components/Footer.vue'
export default { components: { SiteHeader, SiteFooter } }
</script>
