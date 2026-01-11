<template>
  <div class="form-validator">
    <slot></slot>

    <!-- 验证错误显示 -->
    <TransitionGroup
      v-if="showErrors && validationErrors.length > 0"
      name="error-list"
      tag="div"
      class="validation-errors"
    >
      <div
        v-for="(error, index) in validationErrors"
        :key="error.field + error.rule"
        class="validation-error"
        :class="{ 'error-shake': error.shake }"
      >
        <div class="error-icon">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
            <circle cx="12" cy="12" r="10"></circle>
            <line x1="15" y1="9" x2="9" y2="15"></line>
            <line x1="9" y1="9" x2="15" y2="15"></line>
          </svg>
        </div>
        <div class="error-content">
          <div class="error-field">{{ getFieldLabel(error.field) }}</div>
          <div class="error-message">{{ error.message }}</div>
        </div>
        <button
          v-if="error.closable"
          class="error-close"
          @click="removeError(index)"
        >
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
            <line x1="18" y1="6" x2="6" y2="18"></line>
            <line x1="6" y1="6" x2="18" y2="18"></line>
          </svg>
        </button>
      </div>
    </TransitionGroup>

    <!-- 验证成功反馈 -->
    <Transition
      name="success-feedback"
      v-if="showSuccess && validationSuccess"
    >
      <div class="validation-success">
        <div class="success-icon">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
            <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path>
            <polyline points="22,4 12,14.01 9,11.01"></polyline>
          </svg>
        </div>
        <div class="success-message">{{ successMessage }}</div>
      </div>
    </Transition>
  </div>
</template>

<script>
export default {
  name: 'FormValidator',
  props: {
    modelValue: {
      type: Object,
      default: () => ({})
    },
    rules: {
      type: Object,
      default: () => ({})
    },
    fieldLabels: {
      type: Object,
      default: () => ({})
    },
    showErrors: {
      type: Boolean,
      default: true
    },
    showSuccess: {
      type: Boolean,
      default: false
    },
    validateOnChange: {
      type: Boolean,
      default: true
    },
    validateOnBlur: {
      type: Boolean,
      default: true
    },
    debounceDelay: {
      type: Number,
      default: 300
    }
  },
  emits: ['update:modelValue', 'validate', 'error-change', 'success'],
  data() {
    return {
      validationErrors: [],
      validationSuccess: false,
      successMessage: '验证通过',
      debounceTimer: null,
      fieldStates: {} // 字段验证状态
    }
  },
  watch: {
    modelValue: {
      handler(newValue, oldValue) {
        if (this.validateOnChange && JSON.stringify(newValue) !== JSON.stringify(oldValue)) {
          this.debouncedValidate()
        }
      },
      deep: true
    }
  },
  methods: {
    // 防抖验证
    debouncedValidate() {
      if (this.debounceTimer) {
        clearTimeout(this.debounceTimer)
      }
      this.debounceTimer = setTimeout(() => {
        this.validate()
      }, this.debounceDelay)
    },

    // 验证单个字段
    validateField(fieldName) {
      const rules = this.rules[fieldName]
      const value = this.modelValue[fieldName]

      if (!rules) return true

      // 清除该字段之前的错误
      this.validationErrors = this.validationErrors.filter(error => error.field !== fieldName)

      for (const rule of rules) {
        const isValid = this.checkRule(value, rule)
        if (!isValid) {
          const error = {
            field: fieldName,
            rule: rule.name || 'custom',
            message: rule.message || this.getDefaultMessage(fieldName, rule),
            closable: rule.closable !== false,
            shake: true
          }
          this.validationErrors.push(error)

          // 移除抖动效果
          setTimeout(() => {
            error.shake = false
          }, 500)

          return false
        }
      }

      return true
    },

    // 验证所有字段
    async validate() {
      this.validationErrors = []
      let isValid = true

      for (const fieldName in this.rules) {
        if (!this.validateField(fieldName)) {
          isValid = false
        }
      }

      this.validationSuccess = isValid && Object.keys(this.rules).length > 0
      this.$emit('validate', isValid, this.validationErrors)
      this.$emit('error-change', this.validationErrors)

      if (isValid && this.showSuccess) {
        this.$emit('success')
      }

      return isValid
    },

    // 检查单个规则
    checkRule(value, rule) {
      const ruleName = rule.name || 'custom'

      switch (ruleName) {
        case 'required':
          return this.checkRequired(value, rule)
        case 'min':
          return this.checkMin(value, rule)
        case 'max':
          return this.checkMax(value, rule)
        case 'minLength':
          return this.checkMinLength(value, rule)
        case 'maxLength':
          return this.checkMaxLength(value, rule)
        case 'pattern':
          return this.checkPattern(value, rule)
        case 'email':
          return this.checkEmail(value, rule)
        case 'phone':
          return this.checkPhone(value, rule)
        case 'custom':
          return rule.validator ? rule.validator(value, this.modelValue) : true
        default:
          return true
      }
    },

    // 必填验证
    checkRequired(value, rule) {
      if (rule.required === false) return true
      if (Array.isArray(value)) return value.length > 0
      if (typeof value === 'string') return value.trim().length > 0
      return value !== null && value !== undefined && value !== ''
    },

    // 最小值验证
    checkMin(value, rule) {
      if (value === null || value === undefined || value === '') return true
      const numValue = Number(value)
      return !isNaN(numValue) && numValue >= rule.min
    },

    // 最大值验证
    checkMax(value, rule) {
      if (value === null || value === undefined || value === '') return true
      const numValue = Number(value)
      return !isNaN(numValue) && numValue <= rule.max
    },

    // 最小长度验证
    checkMinLength(value, rule) {
      if (value === null || value === undefined || value === '') return true
      return String(value).length >= rule.minLength
    },

    // 最大长度验证
    checkMaxLength(value, rule) {
      if (value === null || value === undefined || value === '') return true
      return String(value).length <= rule.maxLength
    },

    // 正则验证
    checkPattern(value, rule) {
      if (value === null || value === undefined || value === '') return true
      return rule.pattern.test(String(value))
    },

    // 邮箱验证
    checkEmail(value, rule) {
      if (value === null || value === undefined || value === '') return true
      const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
      return emailPattern.test(String(value))
    },

    // 手机号验证
    checkPhone(value, rule) {
      if (value === null || value === undefined || value === '') return true
      const phonePattern = /^1[3-9]\d{9}$/
      return phonePattern.test(String(value))
    },

    // 获取字段标签
    getFieldLabel(fieldName) {
      return this.fieldLabels[fieldName] || fieldName
    },

    // 获取默认错误消息
    getDefaultMessage(fieldName, rule) {
      const fieldLabel = this.getFieldLabel(fieldName)
      const ruleName = rule.name || 'custom'

      const messages = {
        required: `${fieldLabel}不能为空`,
        min: `${fieldLabel}不能小于${rule.min}`,
        max: `${fieldLabel}不能大于${rule.max}`,
        minLength: `${fieldLabel}长度不能少于${rule.minLength}个字符`,
        maxLength: `${fieldLabel}长度不能超过${rule.maxLength}个字符`,
        pattern: `${fieldLabel}格式不正确`,
        email: `请输入有效的邮箱地址`,
        phone: `请输入有效的手机号码`,
        custom: `${fieldLabel}验证失败`
      }

      return messages[ruleName] || `${fieldLabel}验证失败`
    },

    // 移除错误
    removeError(index) {
      this.validationErrors.splice(index, 1)
    },

    // 清除所有错误
    clearErrors() {
      this.validationErrors = []
      this.validationSuccess = false
    },

    // 手动触发验证（通常在表单提交时调用）
    validateAll() {
      return this.validate()
    },

    // 字段失焦验证
    onFieldBlur(fieldName) {
      if (this.validateOnBlur) {
        this.validateField(fieldName)
      }
    },

    // 字段输入验证
    onFieldInput(fieldName) {
      if (this.validateOnChange) {
        // 清除该字段的错误，延迟验证
        this.validationErrors = this.validationErrors.filter(error => error.field !== fieldName)
        this.debouncedValidate()
      }
    }
  },

  beforeUnmount() {
    if (this.debounceTimer) {
      clearTimeout(this.debounceTimer)
    }
  }
}
</script>

<style scoped>
.form-validator {
  position: relative;
}

/* 验证错误列表 */
.validation-errors {
  margin-top: var(--spacing-md);
}

.validation-error {
  display: flex;
  align-items: flex-start;
  gap: var(--spacing-sm);
  padding: var(--spacing-md);
  background-color: var(--error-color);
  background-color: rgba(239, 68, 68, 0.05);
  border: 1px solid rgba(239, 68, 68, 0.2);
  border-left: 4px solid var(--error-color);
  border-radius: var(--border-radius-md);
  margin-bottom: var(--spacing-sm);
  animation: slideInLeft 0.3s ease-out;
}

.error-icon {
  flex-shrink: 0;
  width: 16px;
  height: 16px;
  color: var(--error-color);
  margin-top: 2px;
}

.error-icon svg {
  width: 100%;
  height: 100%;
}

.error-content {
  flex: 1;
  min-width: 0;
}

.error-field {
  font-size: var(--font-size-sm);
  font-weight: 600;
  color: var(--error-color);
  margin-bottom: var(--spacing-xs);
}

.error-message {
  font-size: var(--font-size-sm);
  color: var(--text-primary);
  line-height: 1.4;
}

.error-close {
  flex-shrink: 0;
  background: none;
  border: none;
  width: 16px;
  height: 16px;
  padding: 0;
  cursor: pointer;
  color: var(--text-disabled);
  border-radius: var(--border-radius-sm);
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all var(--transition-fast);
}

.error-close:hover {
  color: var(--error-color);
  background-color: rgba(239, 68, 68, 0.1);
}

/* 抖动效果 */
.error-shake {
  animation: shake 0.5s ease-in-out;
}

/* 验证成功反馈 */
.validation-success {
  display: flex;
  align-items: center;
  gap: var(--spacing-sm);
  padding: var(--spacing-md);
  background-color: var(--success-color);
  background-color: rgba(16, 185, 129, 0.05);
  border: 1px solid rgba(16, 185, 129, 0.2);
  border-left: 4px solid var(--success-color);
  border-radius: var(--border-radius-md);
  margin-top: var(--spacing-md);
  animation: slideInUp 0.3s ease-out;
}

.success-icon {
  flex-shrink: 0;
  width: 16px;
  height: 16px;
  color: var(--success-color);
}

.success-icon svg {
  width: 100%;
  height: 100%;
}

.success-message {
  font-size: var(--font-size-sm);
  font-weight: 600;
  color: var(--success-color);
}

/* 错误列表动画 */
.error-list-enter-active,
.error-list-leave-active {
  transition: all 0.3s ease;
}

.error-list-enter-from,
.error-list-leave-to {
  opacity: 0;
  transform: translateX(-20px);
}

.error-list-move {
  transition: transform 0.3s ease;
}

/* 成功反馈动画 */
.success-feedback-enter-active,
.success-feedback-leave-active {
  transition: all 0.3s ease;
}

.success-feedback-enter-from,
.success-feedback-leave-to {
  opacity: 0;
  transform: translateY(-10px);
}

/* 动画关键帧 */
@keyframes slideInLeft {
  from {
    opacity: 0;
    transform: translateX(-20px);
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
}

@keyframes slideInUp {
  from {
    opacity: 0;
    transform: translateY(-10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes shake {
  0%, 100% { transform: translateX(0); }
  10%, 30%, 50%, 70%, 90% { transform: translateX(-2px); }
  20%, 40%, 60%, 80% { transform: translateX(2px); }
}

/* 响应式适配 */
@media (max-width: 768px) {
  .validation-error,
  .validation-success {
    padding: var(--spacing-sm);
  }

  .error-content,
  .success-message {
    font-size: var(--font-size-xs);
  }
}

/* 无障碍支持 */
@media (prefers-reduced-motion: reduce) {
  .validation-error,
  .validation-success,
  .error-list-enter-active,
  .error-list-leave-active,
  .error-list-move,
  .success-feedback-enter-active,
  .success-feedback-leave-active {
    animation: none;
  }

  .error-shake {
    animation: none;
  }
}

/* 高对比度模式 */
@media (prefers-contrast: high) {
  .validation-error {
    border: 2px solid var(--error-color);
    background-color: var(--white);
  }

  .validation-success {
    border: 2px solid var(--success-color);
    background-color: var(--white);
  }
}
</style>