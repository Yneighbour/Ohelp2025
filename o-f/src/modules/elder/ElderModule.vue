<template>
  <div class="elder-module">
    <div class="card">
      <h2>老人信息模块</h2>

      <div class="module-tabs">
        <button
          @click="activeTab = 'elderly'"
          :class="['tab-btn', { active: activeTab === 'elderly' }]"
        >
          老人信息管理
        </button>
        <button
          @click="activeTab = 'relative'"
          :class="['tab-btn', { active: activeTab === 'relative' }]"
        >
          亲属信息管理
        </button>
      </div>

      <!-- 老人信息管理 -->
      <ElderlyManager v-if="activeTab === 'elderly'" />

      <!-- 亲属信息管理 -->
      <RelativeManager v-if="activeTab === 'relative'" />
    </div>
  </div>
</template>

<script>
import ElderlyManager from './elderly/ElderlyManager.vue'
import RelativeManager from './relative/RelativeManager.vue'

export default {
  name: 'ElderModule',
  components: {
    ElderlyManager,
    RelativeManager
  },
  data() {
    return {
      activeTab: 'elderly' // 默认显示老人信息管理
    }
  }
}
</script>

<style scoped>
.elder-module {
  padding: 1rem 0;
}

.module-tabs {
  display: flex;
  margin-bottom: 1rem;
  border-bottom: 1px solid #ddd;
}

.tab-btn {
  padding: 0.75rem 1rem;
  border: none;
  background: none;
  cursor: pointer;
  border-bottom: 2px solid transparent;
  transition: all 0.3s;
}

.tab-btn:hover {
  background-color: #f8f9fa;
}

.tab-btn.active {
  border-bottom-color: #007bff;
  color: #007bff;
  font-weight: 500;
}
</style>