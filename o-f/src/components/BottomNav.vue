<script setup>
import { useRoute } from 'vue-router';

const route = useRoute();
</script>

<template>
  <nav class="bottom-nav">
    <router-link
      to="/health"
      class="nav-item"
      active-class="active"
      :class="{ active: route.path.startsWith('/health') || route.path.startsWith('/alerts') }"
      data-page="health"
    >
      <svg class="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
        <path
          d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"
        ></path>
      </svg>
      <span>健康</span>
    </router-link>

    <router-link
      to="/social"
      class="nav-item"
      active-class="active"
      :class="{ active: route.path.startsWith('/social') || route.path.startsWith('/activities') }"
      data-page="social"
    >
      <svg class="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
        <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
        <circle cx="9" cy="7" r="4"></circle>
        <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
        <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
      </svg>
      <span>社交</span>
    </router-link>

    <router-link to="/emergency" class="nav-item" active-class="active" data-page="emergency">
      <svg class="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
        <circle cx="12" cy="12" r="10"></circle>
        <line x1="12" y1="8" x2="12" y2="12"></line>
        <line x1="12" y1="16" x2="12.01" y2="16"></line>
      </svg>
      <span>紧急</span>
    </router-link>

    <router-link to="/profile" class="nav-item" active-class="active" data-page="profile">
      <svg class="nav-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
        <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
        <circle cx="12" cy="7" r="4"></circle>
      </svg>
      <span>我的</span>
    </router-link>
  </nav>
</template>
