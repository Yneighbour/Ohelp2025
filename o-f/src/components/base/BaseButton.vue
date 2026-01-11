<template>
  <button
    class="base-btn"
    :class="[
      `btn-${variant}`,
      size ? `btn-${size}` : '',
      { 'btn-block': block, 'btn-loading': loading, 'btn-disabled': disabled }
    ]"
    :disabled="disabled || loading"
    @click="$emit('click', $event)"
  >
    <!-- 加载状态 -->
    <span v-if="loading" class="btn-spinner">
      <svg viewBox="0 0 20 20" fill="currentColor">
        <path fill-rule="evenodd" d="M4 2a1 1 0 011 1v2.101a7.002 7.002 0 0111.601 2.566 1 1 0 11-1.885.666A5.002 5.002 0 005.999 7H9a1 1 0 010 2H4a1 1 0 01-1-1V3a1 1 0 011-1zm.008 9.057a1 1 0 011.276.61A5.002 5.002 0 0014.001 13H11a1 1 0 110-2h5a1 1 0 011 1v5a1 1 0 11-2 0v-2.101a7.002 7.002 0 01-11.601-2.566 1 1 0 01.61-1.276z" clip-rule="evenodd" />
      </svg>
    </span>

    <!-- 图标插槽 -->
    <span v-if="$slots.icon && !loading" class="btn-icon">
      <slot name="icon" />
    </span>

    <!-- 按钮文本 -->
    <span v-if="$slots.default" class="btn-text">
      <slot />
    </span>
  </button>
</template>

<script>
export default {
  name: 'BaseButton',
  props: {
    variant: {
      type: String,
      default: 'primary',
      validator: (value) => ['primary', 'secondary', 'success', 'warning', 'danger'].includes(value)
    },
    size: {
      type: String,
      default: '',
      validator: (value) => ['', 'sm', 'lg'].includes(value)
    },
    block: {
      type: Boolean,
      default: false
    },
    loading: {
      type: Boolean,
      default: false
    },
    disabled: {
      type: Boolean,
      default: false
    }
  },
  emits: ['click']
}
</script>

<style scoped>
.base-btn {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  min-height: var(--min-touch-target);
  padding: 0 var(--spacing-lg);
  font-size: var(--font-size-base);
  font-weight: 500;
  border-radius: var(--border-radius-md);
  transition: all var(--transition-base);
  cursor: pointer;
  user-select: none;
  border: 1px solid transparent;
  text-decoration: none;
  position: relative;
  overflow: hidden;
}

/* 按钮变体 */
.base-btn.btn-primary {
  background: var(--primary-gradient);
  color: var(--white);
  border-color: var(--primary-color);
}

.base-btn.btn-secondary {
  background-color: var(--white);
  color: var(--text-secondary);
  border-color: var(--border-color);
}

.base-btn.btn-success {
  background-color: var(--success-color);
  color: var(--white);
}

.base-btn.btn-warning {
  background-color: var(--warning-color);
  color: var(--white);
}

.base-btn.btn-danger {
  background-color: var(--error-color);
  color: var(--white);
}

/* 尺寸变体 */
.base-btn.btn-sm {
  min-height: 32px;
  padding: 0 var(--spacing-md);
  font-size: var(--font-size-sm);
}

.base-btn.btn-lg {
  min-height: 48px;
  padding: 0 var(--spacing-xl);
  font-size: var(--font-size-lg);
}

/* 状态样式 */
.base-btn.btn-block {
  width: 100%;
}

.base-btn.btn-loading {
  pointer-events: none;
}

.base-btn.btn-disabled {
  opacity: 0.6;
  cursor: not-allowed;
  pointer-events: none;
}

/* 悬停效果 */
.base-btn:not(.btn-disabled):hover {
  transform: translateY(-1px);
}

.base-btn.btn-primary:not(.btn-disabled):hover {
  box-shadow: var(--shadow-purple);
}

.base-btn.btn-secondary:not(.btn-disabled):hover {
  background-color: var(--background-secondary);
  border-color: var(--primary-color);
  color: var(--primary-color);
}

/* 点击效果 */
.base-btn:not(.btn-disabled):active {
  transform: scale(0.98);
}

/* 加载状态 */
.btn-spinner {
  width: 16px;
  height: 16px;
  margin-right: var(--spacing-sm);
  animation: spin 1s linear infinite;
}

.btn-spinner svg {
  width: 100%;
  height: 100%;
}

/* 图标和文本布局 */
.btn-icon {
  margin-right: var(--spacing-sm);
  display: flex;
  align-items: center;
  justify-content: center;
}

.btn-icon:empty {
  margin-right: 0;
}

.btn-text {
  flex: 1;
  text-align: center;
}

/* 只有图标时的样式 */
.base-btn .btn-icon:only-child {
  margin-right: 0;
}

/* 动画 */
@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

/* 响应式适配 */
@media (max-width: 768px) {
  .base-btn {
    min-height: 44px; /* 移动端保持44px最小触摸目标 */
  }

  .base-btn.btn-sm {
    min-height: 40px;
  }
}
</style>