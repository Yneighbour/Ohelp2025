<template>
  <div class="progress-bar-wrapper">
    <!-- 进度条容器 -->
    <div
      class="progress-bar-container"
      :class="[
        variant ? `progress-${variant}` : '',
        size ? `progress-${size}` : '',
        { 'progress-striped': striped, 'progress-animated': animated }
      ]"
    >
      <!-- 进度条轨道 -->
      <div class="progress-track">
        <!-- 进度条填充 -->
        <div
          class="progress-fill"
          :style="{ width: `${clampedValue}%` }"
          :class="{ 'progress-indeterminate': indeterminate }"
        >
          <!-- 进度文本 -->
          <span v-if="showValue" class="progress-text">
            {{ displayValue }}
          </span>
        </div>
      </div>
    </div>

    <!-- 标签 -->
    <div v-if="label" class="progress-label">
      {{ label }}
    </div>

    <!-- 描述文本 -->
    <div v-if="$slots.description || description" class="progress-description">
      <slot name="description">{{ description }}</slot>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ProgressBar',
  props: {
    value: {
      type: [Number, String],
      default: 0
    },
    max: {
      type: [Number, String],
      default: 100
    },
    min: {
      type: [Number, String],
      default: 0
    },
    variant: {
      type: String,
      default: 'primary',
      validator: (value) => ['primary', 'success', 'warning', 'danger', 'info'].includes(value)
    },
    size: {
      type: String,
      default: '',
      validator: (value) => ['', 'sm', 'lg'].includes(value)
    },
    striped: {
      type: Boolean,
      default: false
    },
    animated: {
      type: Boolean,
      default: false
    },
    showValue: {
      type: Boolean,
      default: false
    },
    indeterminate: {
      type: Boolean,
      default: false
    },
    label: {
      type: String,
      default: ''
    },
    description: {
      type: String,
      default: ''
    },
    format: {
      type: Function,
      default: (value) => `${value}%`
    }
  },
  computed: {
    numericValue() {
      return Number(this.value) || 0
    },
    numericMax() {
      return Number(this.max) || 100
    },
    numericMin() {
      return Number(this.min) || 0
    },
    clampedValue() {
      if (this.indeterminate) return 100
      const percentage = ((this.numericValue - this.numericMin) / (this.numericMax - this.numericMin)) * 100
      return Math.min(Math.max(percentage, 0), 100)
    },
    displayValue() {
      return this.format(this.clampedValue)
    }
  }
}
</script>

<style scoped>
.progress-bar-wrapper {
  width: 100%;
}

/* 进度条容器 */
.progress-bar-container {
  width: 100%;
  background-color: var(--background-secondary);
  border-radius: var(--border-radius-lg);
  overflow: hidden;
  position: relative;
}

/* 进度条尺寸 */
.progress-bar-container.progress-sm {
  height: 6px;
}

.progress-bar-container.progress-lg {
  height: 16px;
}

.progress-bar-container:not(.progress-sm):not(.progress-lg) {
  height: 10px;
}

/* 进度条轨道 */
.progress-track {
  width: 100%;
  height: 100%;
  position: relative;
}

/* 进度条填充 */
.progress-fill {
  height: 100%;
  background: var(--primary-gradient);
  border-radius: inherit;
  transition: width var(--transition-base);
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  min-width: 0;
}

/* 变体样式 */
.progress-primary .progress-fill {
  background: var(--primary-gradient);
}

.progress-success .progress-fill {
  background-color: var(--success-color);
}

.progress-warning .progress-fill {
  background-color: var(--warning-color);
}

.progress-danger .progress-fill {
  background-color: var(--error-color);
}

.progress-info .progress-fill {
  background-color: var(--info-color);
}

/* 条纹样式 */
.progress-striped .progress-fill {
  background-image: linear-gradient(
    45deg,
    rgba(255, 255, 255, 0.15) 25%,
    transparent 25%,
    transparent 50%,
    rgba(255, 255, 255, 0.15) 50%,
    rgba(255, 255, 255, 0.15) 75%,
    transparent 75%,
    transparent
  );
  background-size: 16px 16px;
}

/* 动画样式 */
.progress-animated .progress-fill {
  animation: progress-stripes 1s linear infinite;
}

@keyframes progress-stripes {
  0% {
    background-position: 16px 0;
  }
  100% {
    background-position: 0 0;
  }
}

/* 不确定状态 */
.progress-indeterminate .progress-fill {
  animation: progress-indeterminate 1.5s ease-in-out infinite;
}

@keyframes progress-indeterminate {
  0% {
    transform: translateX(-100%);
  }
  100% {
    transform: translateX(100%);
  }
}

/* 进度文本 */
.progress-text {
  color: var(--white);
  font-size: 12px;
  font-weight: 600;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
  white-space: nowrap;
  z-index: 1;
}

/* 标签 */
.progress-label {
  margin-top: var(--spacing-sm);
  font-size: var(--font-size-sm);
  font-weight: 500;
  color: var(--text-primary);
  text-align: center;
}

/* 描述文本 */
.progress-description {
  margin-top: var(--spacing-xs);
  font-size: var(--font-size-sm);
  color: var(--text-secondary);
  line-height: var(--line-height-base);
}

/* 响应式适配 */
@media (max-width: 768px) {
  .progress-bar-container.progress-lg {
    height: 14px;
  }

  .progress-text {
    font-size: 10px;
  }

  .progress-label {
    font-size: var(--font-size-xs);
  }

  .progress-description {
    font-size: var(--font-size-xs);
  }
}
</style>