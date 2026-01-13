<script setup>
import { computed } from 'vue';
import { useRoute } from 'vue-router';
import BottomNav from './components/BottomNav.vue';

const route = useRoute();
const showBottomNav = computed(() => Boolean(route.meta?.showBottomNav));
</script>

<template>
  <router-view />
  <BottomNav v-if="showBottomNav" />
</template>
