<template>
  <div
    class="base-card"
    :class="[
      variant ? `card-${variant}` : '',
      shadow ? `shadow-${shadow}` : '',
      { 'card-hover': hover }
    ]"
  >
    <div v-if="$slots.header" class="card-header">
      <slot name="header" />
    </div>

    <div class="card-body">
      <slot />
    </div>

    <div v-if="$slots.footer" class="card-footer">
      <slot name="footer" />
    </div>
  </div>
</template>

<script>
export default {
  name: 'BaseCard',
  props: {
    variant: {
      type: String,
      default: '',
      validator: (value) => ['', 'primary', 'secondary', 'success', 'warning', 'danger'].includes(value)
    },
    shadow: {
      type: String,
      default: 'md',
      validator: (value) => ['sm', 'md', 'lg', 'xl', 'none'].includes(value)
    },
    hover: {
      type: Boolean,
      default: false
    }
  }
}
</script>

<style scoped>
.base-card {
  background-color: var(--white);
  border-radius: var(--border-radius-lg);
  border: 1px solid var(--border-color);
  transition: all var(--transition-base);
  overflow: hidden;
}

.base-card.shadow-none {
  box-shadow: none;
}

.base-card.shadow-sm {
  box-shadow: var(--shadow-sm);
}

.base-card.shadow-md {
  box-shadow: var(--shadow-md);
}

.base-card.shadow-lg {
  box-shadow: var(--shadow-lg);
}

.base-card.shadow-xl {
  box-shadow: var(--shadow-xl);
}

/* 卡片变体样式 */
.base-card.card-primary {
  border-color: var(--primary-color);
  box-shadow: var(--shadow-purple);
}

.base-card.card-success {
  border-color: var(--success-color);
}

.base-card.card-warning {
  border-color: var(--warning-color);
}

.base-card.card-danger {
  border-color: var(--error-color);
}

/* 悬停效果 */
.base-card.card-hover {
  cursor: pointer;
}

.base-card.card-hover:hover {
  transform: translateY(-2px);
  box-shadow: var(--shadow-lg);
}

/* 卡片头部 */
.card-header {
  padding: var(--spacing-lg);
  border-bottom: 1px solid var(--border-color);
  background-color: var(--background-secondary);
}

.card-header:empty {
  display: none;
}

/* 卡片主体 */
.card-body {
  padding: var(--spacing-lg);
}

/* 卡片底部 */
.card-footer {
  padding: var(--spacing-lg);
  border-top: 1px solid var(--border-color);
  background-color: var(--background-secondary);
}

.card-footer:empty {
  display: none;
}

/* 响应式适配 */
@media (max-width: 768px) {
  .card-header,
  .card-body,
  .card-footer {
    padding: var(--spacing-md);
  }
}
</style>