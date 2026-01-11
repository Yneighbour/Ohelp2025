<template>
  <TransitionGroup name="message-list" tag="div" class="message-container">
    <div
      v-for="message in messages"
      :key="message.id"
      :class="[
        'message-toast',
        `message-${message.type}`,
        { 'message-closable': message.closable }
      ]"
      @mouseenter="pauseAutoClose(message.id)"
      @mouseleave="resumeAutoClose(message.id)"
    >
      <!-- 图标 -->
      <div class="message-icon">
        <component :is="getIconComponent(message.type)" />
      </div>

      <!-- 内容 -->
      <div class="message-content">
        <div v-if="message.title" class="message-title">{{ message.title }}</div>
        <div class="message-text">{{ message.content }}</div>
        <div v-if="message.description" class="message-description">{{ message.description }}</div>
      </div>

      <!-- 操作按钮 -->
      <div class="message-actions">
        <!-- 关闭按钮 -->
        <button
          v-if="message.closable !== false"
          class="message-close"
          @click="closeMessage(message.id)"
        >
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
            <line x1="18" y1="6" x2="6" y2="18"></line>
            <line x1="6" y1="6" x2="18" y2="18"></line>
          </svg>
        </button>
      </div>

      <!-- 进度条 -->
      <div
        v-if="message.duration > 0"
        class="message-progress"
        :style="{ animationDuration: message.duration + 'ms' }"
      ></div>
    </div>
  </TransitionGroup>
</template>

<script>
import { nextTick } from 'vue'

// 图标组件
const SuccessIcon = {
  template: `
    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
      <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path>
      <polyline points="22,4 12,14.01 9,11.01"></polyline>
    </svg>
  `
}

const ErrorIcon = {
  template: `
    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
      <circle cx="12" cy="12" r="10"></circle>
      <line x1="15" y1="9" x2="9" y2="15"></line>
      <line x1="9" y1="9" x2="15" y2="15"></line>
    </svg>
  `
}

const WarningIcon = {
  template: `
    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
      <path d="M10.29 3.86L1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0z"></path>
      <line x1="12" y1="9" x2="12" y2="13"></line>
      <line x1="12" y1="17" x2="12.01" y2="17"></line>
    </svg>
  `
}

const InfoIcon = {
  template: `
    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
      <circle cx="12" cy="12" r="10"></circle>
      <path d="M12 16v-4"></path>
      <path d="M12 8h.01"></path>
    </svg>
  `
}

const LoadingIcon = {
  template: `
    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" class="loading-icon">
      <path d="M12 2v4M12 18v4M4.93 4.93l2.83 2.83M16.24 16.24l2.83 2.83M2 12h4M18 12h4M4.93 19.07l2.83-2.83M16.24 7.76l2.83-2.83"></path>
    </svg>
  `
}

export default {
  name: 'MessageToast',
  components: {
    SuccessIcon,
    ErrorIcon,
    WarningIcon,
    InfoIcon,
    LoadingIcon
  },
  data() {
    return {
      messages: [],
      messageId: 0,
      timers: new Map()
    }
  },
  methods: {
    getIconComponent(type) {
      const iconMap = {
        success: SuccessIcon,
        error: ErrorIcon,
        warning: WarningIcon,
        info: InfoIcon,
        loading: LoadingIcon
      }
      return iconMap[type] || InfoIcon
    },

    addMessage(message) {
      const id = ++this.messageId
      const defaultDuration = {
        success: 3000,
        error: 5000,
        warning: 4000,
        info: 3000,
        loading: 0 // loading 不自动关闭
      }

      const newMessage = {
        id,
        type: message.type || 'info',
        title: message.title || '',
        content: message.content || '',
        description: message.description || '',
        duration: message.duration !== undefined ? message.duration : defaultDuration[message.type] || 3000,
        closable: message.closable !== false,
        onClose: message.onClose,
        ...message
      }

      this.messages.push(newMessage)

      // 设置自动关闭定时器
      if (newMessage.duration > 0) {
        this.setAutoCloseTimer(id, newMessage.duration)
      }

      return id
    },

    setAutoCloseTimer(id, duration) {
      const timer = setTimeout(() => {
        this.closeMessage(id)
      }, duration)
      this.timers.set(id, timer)
    },

    pauseAutoClose(id) {
      const timer = this.timers.get(id)
      if (timer) {
        clearTimeout(timer)
        this.timers.delete(id)
      }
    },

    resumeAutoClose(id) {
      const message = this.messages.find(m => m.id === id)
      if (message && message.duration > 0) {
        this.setAutoCloseTimer(id, message.duration)
      }
    },

    closeMessage(id) {
      const index = this.messages.findIndex(m => m.id === id)
      if (index > -1) {
        const message = this.messages[index]

        // 清除定时器
        this.pauseAutoClose(id)

        // 执行关闭回调
        if (message.onClose) {
          message.onClose()
        }

        // 移除消息
        this.messages.splice(index, 1)
      }
    },

    // 便捷方法
    success(content, options = {}) {
      return this.addMessage({ ...options, type: 'success', content })
    },

    error(content, options = {}) {
      return this.addMessage({ ...options, type: 'error', content })
    },

    warning(content, options = {}) {
      return this.addMessage({ ...options, type: 'warning', content })
    },

    info(content, options = {}) {
      return this.addMessage({ ...options, type: 'info', content })
    },

    loading(content, options = {}) {
      return this.addMessage({ ...options, type: 'loading', content, duration: 0 })
    },

    // 清除所有消息
    clear() {
      this.messages.forEach(message => {
        this.pauseAutoClose(message.id)
      })
      this.messages = []
    }
  }
}
</script>

<style scoped>
.message-container {
  position: fixed;
  top: var(--top-bar-height, 50px);
  right: 20px;
  z-index: var(--z-index-fixed, 1200);
  max-width: 400px;
  pointer-events: none;
}

.message-toast {
  display: flex;
  align-items: flex-start;
  gap: var(--spacing-md);
  padding: var(--spacing-lg);
  margin-bottom: var(--spacing-md);
  background-color: var(--white);
  border-radius: var(--border-radius-lg);
  box-shadow: var(--shadow-xl);
  border-left: 4px solid;
  pointer-events: auto;
  min-width: 300px;
  max-width: 100%;
  position: relative;
  overflow: hidden;
  animation: slideInRight 0.3s ease-out;
}

/* 消息类型样式 */
.message-success {
  border-left-color: var(--success-color);
}

.message-success .message-icon {
  color: var(--success-color);
}

.message-error {
  border-left-color: var(--error-color);
}

.message-error .message-icon {
  color: var(--error-color);
}

.message-warning {
  border-left-color: var(--warning-color);
}

.message-warning .message-icon {
  color: var(--warning-color);
}

.message-info {
  border-left-color: var(--info-color);
}

.message-info .message-icon {
  color: var(--info-color);
}

.message-loading {
  border-left-color: var(--primary-color);
}

.message-loading .message-icon {
  color: var(--primary-color);
}

/* 图标 */
.message-icon {
  flex-shrink: 0;
  width: 20px;
  height: 20px;
  margin-top: 2px;
}

.message-icon svg {
  width: 100%;
  height: 100%;
}

.loading-icon {
  animation: spin 1s linear infinite;
}

/* 内容 */
.message-content {
  flex: 1;
  min-width: 0;
}

.message-title {
  font-size: var(--font-size-base);
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: var(--spacing-xs);
}

.message-text {
  font-size: var(--font-size-base);
  color: var(--text-primary);
  line-height: 1.5;
}

.message-description {
  font-size: var(--font-size-sm);
  color: var(--text-secondary);
  margin-top: var(--spacing-xs);
  line-height: 1.4;
}

/* 操作按钮 */
.message-actions {
  flex-shrink: 0;
}

.message-close {
  background: none;
  border: none;
  width: 20px;
  height: 20px;
  padding: 0;
  cursor: pointer;
  color: var(--text-disabled);
  border-radius: var(--border-radius-sm);
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all var(--transition-fast);
}

.message-close:hover {
  color: var(--text-primary);
  background-color: var(--background-secondary);
}

/* 进度条 */
.message-progress {
  position: absolute;
  bottom: 0;
  left: 0;
  height: 3px;
  background: linear-gradient(90deg, var(--primary-color), var(--primary-light));
  animation: progress linear;
  animation-fill-mode: forwards;
}

/* 动画 */
@keyframes slideInRight {
  from {
    opacity: 0;
    transform: translateX(100%);
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
}

@keyframes progress {
  from {
    width: 100%;
  }
  to {
    width: 0%;
  }
}

@keyframes spin {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}

/* 消息列表过渡动画 */
.message-list-enter-active,
.message-list-leave-active {
  transition: all 0.3s ease;
}

.message-list-enter-from {
  opacity: 0;
  transform: translateX(100%);
}

.message-list-leave-to {
  opacity: 0;
  transform: translateX(100%);
}

.message-list-move {
  transition: transform 0.3s ease;
}

/* 响应式适配 */
@media (max-width: 768px) {
  .message-container {
    right: var(--spacing-md);
    left: var(--spacing-md);
    max-width: none;
  }

  .message-toast {
    min-width: auto;
    margin-bottom: var(--spacing-sm);
  }

  .message-content {
    flex: 1;
  }
}

/* 无障碍支持 */
@media (prefers-reduced-motion: reduce) {
  .message-toast,
  .message-list-enter-active,
  .message-list-leave-active,
  .message-list-move {
    animation: none;
    transition: none;
  }
}

/* 高对比度模式 */
@media (prefers-contrast: high) {
  .message-toast {
    border: 2px solid;
    box-shadow: none;
  }

  .message-success {
    border-left-color: var(--success-color);
    background-color: var(--white);
    color: var(--text-primary);
  }

  .message-error {
    border-left-color: var(--error-color);
  }

  .message-warning {
    border-left-color: var(--warning-color);
  }

  .message-info {
    border-left-color: var(--info-color);
  }
}
</style>