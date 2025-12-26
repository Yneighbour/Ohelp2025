<template>
  <transition-group name="message" tag="div" class="message-container">
    <div
      v-for="msg in messages"
      :key="msg.id"
      :class="['message-item', `message-${msg.type}`]"
      @click="removeMessage(msg.id)"
    >
      <div class="message-content">
        <el-icon class="message-icon" :size="20">
          <Check v-if="msg.type === 'success'" />
          <Warning v-else-if="msg.type === 'warning'" />
          <Error v-else-if="msg.type === 'error'" />
          <Info v-else />
        </el-icon>
        <span class="message-text">{{ msg.text }}</span>
        <el-icon class="message-close" @click.stop="removeMessage(msg.id)">
          <Close />
        </el-icon>
      </div>
    </div>
  </transition-group>
</template>

<script>
import { defineComponent, ref } from 'vue'
import { Check, Warning, Error, Info, Close } from '@element-plus/icons-vue'

let messageId = 0

export default defineComponent({
  name: 'Message',
  components: {
    Check,
    Warning,
    Error,
    Info,
    Close
  },
  setup() {
    const messages = ref([])

    const addMessage = (text, type = 'info', duration = 3000) => {
      const id = ++messageId
      const message = {
        id,
        text,
        type,
        duration
      }

      messages.value.push(message)

      if (duration > 0) {
        setTimeout(() => {
          removeMessage(id)
        }, duration)
      }

      return id
    }

    const removeMessage = (id) => {
      const index = messages.value.findIndex(msg => msg.id === id)
      if (index > -1) {
        messages.value.splice(index, 1)
      }
    }

    const clearAll = () => {
      messages.value = []
    }

    // 提供全局方法
    window.$message = {
      success: (text, duration) => addMessage(text, 'success', duration),
      warning: (text, duration) => addMessage(text, 'warning', duration),
      error: (text, duration) => addMessage(text, 'error', duration),
      info: (text, duration) => addMessage(text, 'info', duration),
      clear: clearAll
    }

    return {
      messages,
      removeMessage
    }
  }
})
</script>

<style scoped>
.message-container {
  position: fixed;
  top: var(--space-6);
  right: var(--space-6);
  z-index: 10000;
  pointer-events: none;
}

.message-item {
  display: flex;
  align-items: center;
  margin-bottom: var(--space-3);
  padding: var(--space-4) var(--space-6);
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-lg);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.1);
  min-width: 300px;
  max-width: 500px;
  pointer-events: auto;
  cursor: pointer;
  transition: all 0.3s ease;
  animation: slideInRight 0.5s ease-out;
}

.message-item:hover {
  transform: translateX(-4px);
}

.message-success {
  background: linear-gradient(135deg, #dcfce7, #bbf7d0);
  border-left: 4px solid var(--success);
  color: #166534;
}

.message-warning {
  background: linear-gradient(135deg, #fef3c7, #fde68a);
  border-left: 4px solid var(--warning);
  color: #92400e;
}

.message-error {
  background: linear-gradient(135deg, #fee2e2, #fecaca);
  border-left: 4px solid var(--error);
  color: #991b1b;
}

.message-info {
  background: linear-gradient(135deg, #dbeafe, #bfdbfe);
  border-left: 4px solid var(--info);
  color: #1e40af;
}

.message-content {
  display: flex;
  align-items: center;
  gap: var(--space-3);
  width: 100%;
}

.message-icon {
  flex-shrink: 0;
}

.message-text {
  flex: 1;
  font-size: var(--font-size-sm);
  font-weight: var(--font-weight-medium);
  line-height: 1.4;
}

.message-close {
  flex-shrink: 0;
  cursor: pointer;
  opacity: 0.7;
  transition: opacity 0.2s ease;
}

.message-close:hover {
  opacity: 1;
}

/* 动画效果 */
.message-enter-active,
.message-leave-active {
  transition: all 0.5s ease;
}

.message-enter-from {
  opacity: 0;
  transform: translateX(100%);
}

.message-leave-to {
  opacity: 0;
  transform: translateX(100%);
}

.message-move {
  transition: transform 0.5s ease;
}

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

/* 老人模式特殊样式 */
.elder-mode .message-item {
  min-width: 350px;
  padding: var(--space-6) var(--space-8);
}

.elder-mode .message-text {
  font-size: var(--font-size-base);
}

.elder-mode .message-icon {
  font-size: 24px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .message-container {
    top: var(--space-4);
    right: var(--space-4);
    left: var(--space-4);
  }

  .message-item {
    min-width: auto;
    max-width: none;
  }
}
</style>
