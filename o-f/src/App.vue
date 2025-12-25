<template>
  <div id="app">
    <SiteHeader />
    <main class="container">
      <router-view />
    </main>
    <SiteFooter />
  </div>
</template>

<script>
import SiteHeader from './components/Header.vue'
import SiteFooter from './components/Footer.vue'

export default {
  components: { SiteHeader, SiteFooter }
}
</script>

<style>
/* Global styles are in styles/main.css */
</style>
