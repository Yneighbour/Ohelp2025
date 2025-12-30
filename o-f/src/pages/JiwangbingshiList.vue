<template>
  <div class="page">
    <SiteHeader />
    <main class="container">
      <h1>既往病史管理</h1>
      <p>记录老人病史信息</p>
    </main>
    <SiteFooter />
  </div>
</template>

<script>
import SiteHeader from '../components/Header.vue'
import SiteFooter from '../components/Footer.vue'
export default { components: { SiteHeader, SiteFooter } }
</script>