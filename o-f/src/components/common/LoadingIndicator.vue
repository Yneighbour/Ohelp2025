<template>
  <Transition name="loading-overlay" appear>
    <div
      v-if="visible"
      class="loading-overlay"
      :class="{ 'loading-fullscreen': fullscreen }"
      :style="{ zIndex }"
    >
      <!-- 背景遮罩 -->
      <div
        v-if="showBackdrop"
        class="loading-backdrop"
        :class="{ 'backdrop-blur': blur }"
        @click="handleBackdropClick"
      ></div>

      <!-- 加载器容器 -->
      <div class="loading-container" :class="position">
        <!-- 自定义加载器 -->
        <slot name="loader">
          <div class="loading-spinner" :class="`spinner-${size}`">
            <!-- 圆环样式 -->
            <div v-if="type === 'ring'" class="spinner-ring">
              <div class="ring-track"></div>
              <div class="ring-progress"></div>
            </div>

            <!-- 点样式 -->
            <div v-else-if="type === 'dots'" class="spinner-dots">
              <div
                v-for="i in 3"
                :key="i"
                class="dot"
                :style="{ animationDelay: `${i * 0.2}s` }"
              ></div>
            </div>

            <!-- 脉冲样式 -->
            <div v-else-if="type === 'pulse'" class="spinner-pulse">
              <div class="pulse-wave"></div>
              <div class="pulse-core"></div>
            </div>

            <!-- 默认圆点样式 -->
            <div v-else class="spinner-default">
              <div class="bounce1"></div>
              <div class="bounce2"></div>
              <div class="bounce3"></div>
            </div>
          </div>
        </slot>

        <!-- 加载文本 -->
        <div v-if="text" class="loading-text" :class="`text-${textSize}`">
          {{ text }}
        </div>

        <!-- 进度条 -->
        <div v-if="showProgress && progress !== null" class="loading-progress">
          <div class="progress-track">
            <div
              class="progress-bar"
              :style="{ width: progress + '%' }"
            ></div>
          </div>
          <div v-if="showProgressText" class="progress-text">
            {{ progress }}%
          </div>
        </div>

        <!-- 自定义内容 -->
        <slot></slot>
      </div>
    </div>
  </Transition>
</template>

<script>
export default {
  name: 'LoadingIndicator',
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    type: {
      type: String,
      default: 'ring',
      validator: (value) => ['ring', 'dots', 'pulse', 'bounce'].includes(value)
    },
    size: {
      type: String,
      default: 'medium',
      validator: (value) => ['small', 'medium', 'large'].includes(value)
    },
    text: {
      type: String,
      default: ''
    },
    textSize: {
      type: String,
      default: 'medium',
      validator: (value) => ['small', 'medium', 'large'].includes(value)
    },
    fullscreen: {
      type: Boolean,
      default: false
    },
    position: {
      type: String,
      default: 'center',
      validator: (value) => ['center', 'top', 'bottom', 'left', 'right', 'top-left', 'top-right', 'bottom-left', 'bottom-right'].includes(value)
    },
    showBackdrop: {
      type: Boolean,
      default: true
    },
    backdropClosable: {
      type: Boolean,
      default: false
    },
    blur: {
      type: Boolean,
      default: false
    },
    zIndex: {
      type: [Number, String],
      default: 1000
    },
    progress: {
      type: Number,
      default: null,
      validator: (value) => value === null || (value >= 0 && value <= 100)
    },
    showProgress: {
      type: Boolean,
      default: false
    },
    showProgressText: {
      type: Boolean,
      default: true
    }
  },
  emits: ['backdrop-click'],
  methods: {
    handleBackdropClick() {
      if (this.backdropClosable) {
        this.$emit('backdrop-click')
      }
    }
  }
}
</script>

<style scoped>
/* 加载覆盖层 */
.loading-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.loading-overlay.loading-fullscreen {
  position: fixed;
  z-index: 9999;
}

/* 背景遮罩 */
.loading-backdrop {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  transition: opacity var(--transition-base);
}

.loading-backdrop.backdrop-blur {
  backdrop-filter: blur(4px);
}

/* 加载器容器 */
.loading-container {
  position: relative;
  z-index: 10;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: var(--spacing-md);
  padding: var(--spacing-xl);
  background-color: var(--white);
  border-radius: var(--border-radius-lg);
  box-shadow: var(--shadow-xl);
  min-width: 120px;
  min-height: 120px;
}

/* 位置定位 */
.loading-overlay .loading-container.center {
  position: static;
}

.loading-overlay .loading-container.top {
  position: absolute;
  top: var(--spacing-xl);
  left: 50%;
  transform: translateX(-50%);
}

.loading-overlay .loading-container.bottom {
  position: absolute;
  bottom: var(--spacing-xl);
  left: 50%;
  transform: translateX(-50%);
}

.loading-overlay .loading-container.left {
  position: absolute;
  left: var(--spacing-xl);
  top: 50%;
  transform: translateY(-50%);
}

.loading-overlay .loading-container.right {
  position: absolute;
  right: var(--spacing-xl);
  top: 50%;
  transform: translateY(-50%);
}

.loading-overlay .loading-container.top-left {
  position: absolute;
  top: var(--spacing-xl);
  left: var(--spacing-xl);
}

.loading-overlay .loading-container.top-right {
  position: absolute;
  top: var(--spacing-xl);
  right: var(--spacing-xl);
}

.loading-overlay .loading-container.bottom-left {
  position: absolute;
  bottom: var(--spacing-xl);
  left: var(--spacing-xl);
}

.loading-overlay .loading-container.bottom-right {
  position: absolute;
  bottom: var(--spacing-xl);
  right: var(--spacing-xl);
}

/* 加载文本 */
.loading-text {
  color: var(--text-primary);
  font-weight: 500;
  text-align: center;
  white-space: nowrap;
}

.loading-text.text-small {
  font-size: var(--font-size-sm);
}

.loading-text.text-medium {
  font-size: var(--font-size-base);
}

.loading-text.text-large {
  font-size: var(--font-size-lg);
}

/* 圆环加载器 */
.spinner-ring {
  position: relative;
  width: 100%;
  height: 100%;
}

.ring-track {
  position: absolute;
  width: 100%;
  height: 100%;
  border: 3px solid var(--border-color);
  border-radius: 50%;
}

.ring-progress {
  position: absolute;
  width: 100%;
  height: 100%;
  border: 3px solid transparent;
  border-top-color: var(--primary-color);
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

/* 点加载器 */
.spinner-dots {
  display: flex;
  gap: 4px;
  align-items: center;
}

.dot {
  width: 8px;
  height: 8px;
  background-color: var(--primary-color);
  border-radius: 50%;
  animation: dots-bounce 1.4s infinite ease-in-out both;
}

/* 脉冲加载器 */
.spinner-pulse {
  position: relative;
  width: 100%;
  height: 100%;
}

.pulse-wave {
  position: absolute;
  width: 100%;
  height: 100%;
  border: 2px solid var(--primary-color);
  border-radius: 50%;
  animation: pulse-wave 1.5s infinite;
}

.pulse-core {
  position: absolute;
  top: 50%;
  left: 50%;
  width: 50%;
  height: 50%;
  background-color: var(--primary-color);
  border-radius: 50%;
  transform: translate(-50%, -50%);
}

/* 默认跳动加载器 */
.spinner-default {
  display: flex;
  gap: 4px;
  align-items: flex-end;
}

.bounce1,
.bounce2,
.bounce3 {
  width: 8px;
  height: 8px;
  background-color: var(--primary-color);
  border-radius: 50%;
  animation: bounce 1.4s infinite ease-in-out both;
}

.bounce2 {
  animation-delay: -0.32s;
}

.bounce3 {
  animation-delay: -0.16s;
}

/* 加载器尺寸 */
.spinner-small {
  width: 24px;
  height: 24px;
}

.spinner-medium {
  width: 40px;
  height: 40px;
}

.spinner-large {
  width: 56px;
  height: 56px;
}

/* 进度条 */
.loading-progress {
  width: 100%;
  max-width: 200px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: var(--spacing-xs);
}

.progress-track {
  width: 100%;
  height: 4px;
  background-color: var(--background-secondary);
  border-radius: var(--border-radius-sm);
  overflow: hidden;
}

.progress-bar {
  height: 100%;
  background: var(--primary-gradient);
  border-radius: var(--border-radius-sm);
  transition: width 0.3s ease;
}

.progress-text {
  font-size: var(--font-size-sm);
  color: var(--text-secondary);
  font-weight: 500;
}

/* 动画 */
@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

@keyframes dots-bounce {
  0%, 80%, 100% {
    transform: scale(0);
    opacity: 0.5;
  }
  40% {
    transform: scale(1);
    opacity: 1;
  }
}

@keyframes pulse-wave {
  0% {
    transform: scale(0.8);
    opacity: 1;
  }
  100% {
    transform: scale(1.5);
    opacity: 0;
  }
}

@keyframes bounce {
  0%, 20%, 53%, 80%, 100% {
    animation-timing-function: cubic-bezier(0.215, 0.61, 0.355, 1);
    transform: translate3d(0, 0, 0);
  }
  40%, 43% {
    animation-timing-function: cubic-bezier(0.755, 0.05, 0.855, 0.06);
    transform: translate3d(0, -12px, 0);
  }
  70% {
    animation-timing-function: cubic-bezier(0.755, 0.05, 0.855, 0.06);
    transform: translate3d(0, -6px, 0);
  }
  90% {
    transform: translate3d(0, -2px, 0);
  }
}

/* 覆盖层过渡动画 */
.loading-overlay-enter-active,
.loading-overlay-leave-active {
  transition: opacity var(--transition-base);
}

.loading-overlay-enter-from,
.loading-overlay-leave-to {
  opacity: 0;
}

/* 响应式适配 */
@media (max-width: 768px) {
  .loading-container {
    padding: var(--spacing-lg);
    min-width: 100px;
    min-height: 100px;
  }

  .spinner-medium {
    width: 32px;
    height: 32px;
  }

  .spinner-large {
    width: 48px;
    height: 48px;
  }
}

/* 无障碍支持 */
@media (prefers-reduced-motion: reduce) {
  .ring-progress,
  .dot,
  .pulse-wave,
  .bounce1,
  .bounce2,
  .bounce3,
  .loading-overlay-enter-active,
  .loading-overlay-leave-active {
    animation: none;
  }

  .progress-bar {
    transition: none;
  }
}

/* 高对比度模式 */
@media (prefers-contrast: high) {
  .loading-backdrop {
    background-color: rgba(0, 0, 0, 0.8);
  }

  .loading-container {
    border: 2px solid var(--text-primary);
  }

  .ring-track {
    border-color: var(--text-primary);
  }

  .ring-progress {
    border-top-color: var(--primary-color);
  }
}
</style>