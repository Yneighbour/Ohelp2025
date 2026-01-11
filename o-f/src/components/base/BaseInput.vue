<template>
  <div class="base-input-wrapper">
    <label v-if="label" class="input-label" :for="inputId">
      <span class="input-label-text">{{ label }}</span>
      <span v-if="isRequired" class="input-required" aria-hidden="true">*</span>
    </label>
    <div class="input-container" :class="{ 'input-error': hasError, 'input-success': hasSuccess }">
      <!-- 前缀图标 -->
      <div v-if="$slots.prefix" class="input-prefix">
        <slot name="prefix" />
      </div>

      <!-- 输入框 -->
      <input
        v-if="type !== 'textarea'"
        ref="input"
        class="base-input"
        :class="[inputSize ? `input-${inputSize}` : '']"
        :type="inputType"
        :value="modelValue"
        :placeholder="placeholder"
        :disabled="disabled"
        :readonly="readonly"
        :maxlength="maxlength"
        :min="min"
        :max="max"
        :step="step"
        v-bind="inputAttrs"
        @input="$emit('update:modelValue', $event.target.value)"
        @focus="$emit('focus', $event)"
        @blur="$emit('blur', $event)"
        @change="$emit('change', $event)"
        @keyup="$emit('keyup', $event)"
      />

      <!-- 文本域 -->
      <textarea
        v-else
        ref="textarea"
        class="base-input base-textarea"
        :class="[inputSize ? `input-${inputSize}` : '']"
        :value="modelValue"
        :placeholder="placeholder"
        :disabled="disabled"
        :readonly="readonly"
        :maxlength="maxlength"
        :rows="rows"
        v-bind="inputAttrs"
        @input="$emit('update:modelValue', $event.target.value)"
        @focus="$emit('focus', $event)"
        @blur="$emit('blur', $event)"
        @change="$emit('change', $event)"
        @keyup="$emit('keyup', $event)"
      />

      <!-- 后缀图标 -->
      <div v-if="$slots.suffix" class="input-suffix">
        <slot name="suffix" />
      </div>

      <!-- 密码可见性切换 -->
      <button
        v-if="type === 'password'"
        type="button"
        class="input-suffix input-toggle"
        @click="togglePasswordVisibility"
      >
        <svg v-if="showPassword" viewBox="0 0 24 24" fill="none" stroke="currentColor">
          <path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24" />
          <line x1="1" y1="1" x2="23" y2="23" />
        </svg>
        <svg v-else viewBox="0 0 24 24" fill="none" stroke="currentColor">
          <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z" />
          <circle cx="12" cy="12" r="3" />
        </svg>
      </button>
    </div>

    <!-- 帮助文本 -->
    <div v-if="$slots.help || help" class="input-help">
      <slot name="help">{{ help }}</slot>
    </div>

    <!-- 错误信息 -->
    <div v-if="error" class="input-error-text">
      {{ error }}
    </div>
  </div>
</template>

<script>
export default {
  name: 'BaseInput',
  inheritAttrs: false,
  props: {
    modelValue: {
      type: [String, Number],
      default: ''
    },
    label: {
      type: String,
      default: ''
    },
    type: {
      type: String,
      default: 'text',
      validator: (value) => ['text', 'password', 'email', 'tel', 'number', 'url', 'textarea'].includes(value)
    },
    placeholder: {
      type: String,
      default: ''
    },
    size: {
      type: String,
      default: '',
      validator: (value) => ['', 'sm', 'lg'].includes(value)
    },
    disabled: {
      type: Boolean,
      default: false
    },
    readonly: {
      type: Boolean,
      default: false
    },
    maxlength: {
      type: [String, Number],
      default: ''
    },
    min: {
      type: [String, Number],
      default: ''
    },
    max: {
      type: [String, Number],
      default: ''
    },
    step: {
      type: [String, Number],
      default: ''
    },
    rows: {
      type: [String, Number],
      default: 3
    },
    help: {
      type: String,
      default: ''
    },
    error: {
      type: String,
      default: ''
    },
    success: {
      type: Boolean,
      default: false
    }
  },
  emits: ['update:modelValue', 'focus', 'blur', 'change', 'keyup'],
  data() {
    return {
      showPassword: false
    }
  },
  computed: {
    inputId() {
      return this.$attrs.id || `base-input-${this._uid}`
    },
    isRequired() {
      return this.$attrs.required !== undefined
    },
    inputAttrs() {
      // 将诸如 required/autocomplete/name/id 等原生属性绑定到真实 input/textarea 上
      return {
        ...this.$attrs,
        id: this.inputId
      }
    },
    inputType() {
      if (this.type === 'password') {
        return this.showPassword ? 'text' : 'password'
      }
      return this.type === 'textarea' ? 'text' : this.type
    },
    inputSize() {
      return this.size
    },
    hasError() {
      return !!this.error
    },
    hasSuccess() {
      return this.success && !this.error
    }
  },
  methods: {
    togglePasswordVisibility() {
      this.showPassword = !this.showPassword
    },
    focus() {
      this.$refs[this.type === 'textarea' ? 'textarea' : 'input'].focus()
    },
    blur() {
      this.$refs[this.type === 'textarea' ? 'textarea' : 'input'].blur()
    }
  }
}
</script>

<style scoped>
.base-input-wrapper {
  width: 100%;
}

.input-label {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  margin-bottom: var(--spacing-xs);
  color: var(--form-label-color);
  font-size: var(--font-size-sm);
  font-weight: 600;
}

.input-required {
  color: var(--error-color);
  font-size: var(--font-size-base);
  line-height: 1;
}

/* 输入框容器 */
.input-container {
  position: relative;
  display: flex;
  align-items: center;
  background-color: var(--white);
  border: 1px solid var(--border-color);
  border-radius: var(--border-radius-md);
  transition: all var(--transition-fast);
}

.input-container:focus-within {
  border-color: var(--primary-color);
  box-shadow: 0 0 0 3px rgba(124, 58, 237, 0.1);
}

.input-container.input-error {
  border-color: var(--error-color);
}

.input-container.input-error:focus-within {
  box-shadow: 0 0 0 3px rgba(239, 68, 68, 0.1);
}

.input-container.input-success {
  border-color: var(--success-color);
}

.input-container.input-success:focus-within {
  box-shadow: 0 0 0 3px rgba(16, 185, 129, 0.1);
}

/* 输入框基础样式 */
.base-input {
  flex: 1;
  width: 100%;
  min-height: var(--min-touch-target);
  padding: 0 var(--spacing-md);
  font-size: var(--font-size-base);
  color: var(--text-primary);
  background: transparent;
  border: none;
  outline: none;
}

.base-input::placeholder {
  color: var(--text-disabled);
}

/* 文本域样式 */
.base-textarea {
  min-height: 80px;
  resize: vertical;
  padding: var(--spacing-md);
}

/* 尺寸变体 */
.base-input.input-sm {
  min-height: 32px;
  padding: 0 var(--spacing-sm);
  font-size: var(--font-size-sm);
}

.base-input.input-lg {
  min-height: 48px;
  padding: 0 var(--spacing-lg);
  font-size: var(--font-size-lg);
}

/* 前缀和后缀 */
.input-prefix,
.input-suffix {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0 var(--spacing-sm);
  color: var(--text-secondary);
}

.input-prefix {
  border-right: 1px solid var(--border-color);
}

.input-suffix {
  border-left: 1px solid var(--border-color);
}

.input-toggle {
  background: none;
  border: none;
  cursor: pointer;
  color: var(--text-secondary);
  padding: var(--spacing-sm);
  border-radius: var(--border-radius-sm);
  transition: color var(--transition-fast);
}

.input-toggle:hover {
  color: var(--primary-color);
}

.input-toggle svg {
  width: 16px;
  height: 16px;
}

/* 帮助文本 */
.input-help {
  margin-top: var(--spacing-xs);
  font-size: var(--font-size-sm);
  color: var(--text-secondary);
  line-height: 1.4;
}

/* 错误信息 */
.input-error-text {
  margin-top: var(--spacing-xs);
  font-size: var(--font-size-sm);
  color: var(--error-color);
  line-height: 1.4;
}

/* 禁用状态 */
.input-container:has(.base-input:disabled),
.input-container:has(.base-textarea:disabled) {
  background-color: var(--background-secondary);
  opacity: 0.6;
  cursor: not-allowed;
}

.base-input:disabled,
.base-textarea:disabled {
  cursor: not-allowed;
}

/* 只读状态 */
.base-input[readonly],
.base-textarea[readonly] {
  background-color: var(--background-secondary);
}

/* 响应式适配 */
@media (max-width: 768px) {
  .base-input,
  .base-textarea {
    font-size: 16px; /* 防止移动端缩放 */
  }
}
</style>