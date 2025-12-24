<template>
  <div class="page">
    <SiteHeader />
    <main class="container">
      <h1>老人档案</h1>
      <p>示例列表：姓名、年龄、房间号、护理等级</p>
    </main>
    <SiteFooter />
  </div>
</template>

<script>
import SiteHeader from '../components/Header.vue'
import SiteFooter from '../components/Footer.vue'
export default { components: { SiteHeader, SiteFooter } }
</script>
