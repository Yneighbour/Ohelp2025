<template>
  <div id="app">
    <SiteHeader />
    <main class="container">
      <router-view v-slot="{ Component }">
        <transition name="page" mode="out-in">
          <component :is="Component" />
        </transition>
      </router-view>
    </main>
    <SiteFooter />
    <Message />
  </div>
</template>

<script>
import SiteHeader from './components/Header.vue'
import SiteFooter from './components/Footer.vue'
import Message from './components/Message.vue'

export default {
  components: {
    SiteHeader,
    SiteFooter,
    Message
  }
}
</script>

<style>
/* Global styles are in styles/main.css */
</style>
