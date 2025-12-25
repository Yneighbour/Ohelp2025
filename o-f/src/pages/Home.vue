<template>
  <div class="page">
    <SiteHeader />
    <main class="container">
      <h1>欢迎使用智慧养老服务管理系统</h1>
      <p>这是主页示例</p>
    </main>
    <SiteFooter />
  </div>
</template>

<script>
import SiteHeader from '../components/Header.vue'
import SiteFooter from '../components/Footer.vue'

export default {
  components: { SiteHeader, SiteFooter }
}
</script>
