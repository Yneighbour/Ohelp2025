import { nextTick } from 'vue'

// 自定义指令集合

// 键盘导航指令
export const keyboardNavigation = {
  mounted(el, binding) {
    const options = {
      selector: '[tabindex], button, input, select, textarea, a[href]',
      loop: true,
      ...binding.value
    }

    let focusableElements = []
    let currentIndex = -1

    const updateFocusableElements = () => {
      focusableElements = Array.from(el.querySelectorAll(options.selector))
        .filter(element => {
          const style = window.getComputedStyle(element)
          return style.display !== 'none' &&
                 style.visibility !== 'hidden' &&
                 !element.hasAttribute('disabled') &&
                 !element.hasAttribute('inert')
        })
    }

    const focusElement = (index) => {
      if (focusableElements[index]) {
        focusableElements[index].focus()
        currentIndex = index
      }
    }

    const handleKeydown = (event) => {
      if (event.key === 'Tab') {
        event.preventDefault()
        updateFocusableElements()

        if (event.shiftKey) {
          // 向后导航
          currentIndex = currentIndex <= 0
            ? (options.loop ? focusableElements.length - 1 : 0)
            : currentIndex - 1
        } else {
          // 向前导航
          currentIndex = currentIndex >= focusableElements.length - 1
            ? (options.loop ? 0 : focusableElements.length - 1)
            : currentIndex + 1
        }

        focusElement(currentIndex)
      }
    }

    // 绑定键盘事件
    el.addEventListener('keydown', handleKeydown)

    // 存储清理函数
    el._keyboardNavigationCleanup = () => {
      el.removeEventListener('keydown', handleKeydown)
    }

    // 初始化焦点元素
    updateFocusableElements()

    // 如果有初始焦点元素，聚焦第一个
    if (focusableElements.length > 0 && options.autoFocus) {
      focusElement(0)
    }
  },

  unmounted(el) {
    if (el._keyboardNavigationCleanup) {
      el._keyboardNavigationCleanup()
    }
  }
}

// 焦点陷阱指令（用于模态框等）
export const focusTrap = {
  mounted(el, binding) {
    const options = {
      initialFocus: true,
      ...binding.value
    }

    let focusableElements = []
    let firstFocusableElement = null
    let lastFocusableElement = null

    const updateFocusableElements = () => {
      const focusableSelectors = [
        'a[href]',
        'area[href]',
        'input:not([disabled]):not([type="hidden"])',
        'select:not([disabled])',
        'textarea:not([disabled])',
        'button:not([disabled])',
        'iframe',
        'object',
        'embed',
        '[contenteditable]',
        '[tabindex]:not([tabindex="-1"])'
      ]

      focusableElements = Array.from(el.querySelectorAll(focusableSelectors.join(',')))
        .filter(element => {
          const style = window.getComputedStyle(element)
          return style.display !== 'none' &&
                 style.visibility !== 'hidden' &&
                 element.offsetWidth > 0 &&
                 element.offsetHeight > 0
        })

      firstFocusableElement = focusableElements[0]
      lastFocusableElement = focusableElements[focusableElements.length - 1]
    }

    const handleKeydown = (event) => {
      if (event.key !== 'Tab') return

      updateFocusableElements()

      if (focusableElements.length === 0) {
        event.preventDefault()
        return
      }

      if (event.shiftKey) {
        // Shift + Tab
        if (document.activeElement === firstFocusableElement) {
          event.preventDefault()
          lastFocusableElement.focus()
        }
      } else {
        // Tab
        if (document.activeElement === lastFocusableElement) {
          event.preventDefault()
          firstFocusableElement.focus()
        }
      }
    }

    const handleFocusIn = (event) => {
      if (!el.contains(event.target)) {
        // 焦点移出容器，重新聚焦到第一个元素
        if (firstFocusableElement) {
          firstFocusableElement.focus()
        }
      }
    }

    // 绑定事件
    el.addEventListener('keydown', handleKeydown)

    // 防止焦点逃逸
    if (options.escape) {
      document.addEventListener('focusin', handleFocusIn)
    }

    // 设置初始焦点
    if (options.initialFocus && firstFocusableElement) {
      setTimeout(() => {
        firstFocusableElement.focus()
      }, 10)
    }

    // 存储清理函数
    el._focusTrapCleanup = () => {
      el.removeEventListener('keydown', handleKeydown)
      if (options.escape) {
        document.removeEventListener('focusin', handleFocusIn)
      }
    }
  },

  unmounted(el) {
    if (el._focusTrapCleanup) {
      el._focusTrapCleanup()
    }
  }
}

// 自动聚焦指令
export const autoFocus = {
  mounted(el, binding) {
    const options = {
      delay: 0,
      ...binding.value
    }

    const focusElement = () => {
      if (el.focus) {
        el.focus()
        // 如果是输入框，将光标移到末尾
        if (el.setSelectionRange && typeof el.value === 'string') {
          const len = el.value.length
          el.setSelectionRange(len, len)
        }
      }
    }

    if (options.delay > 0) {
      setTimeout(focusElement, options.delay)
    } else {
      // 使用 nextTick 确保 DOM 已更新
      nextTick(focusElement)
    }
  }
}

// 悬停延迟指令（防止意外触发）
export const hoverDelay = {
  mounted(el, binding) {
    const options = {
      enterDelay: 300,
      leaveDelay: 300,
      ...binding.value
    }

    let enterTimer = null
    let leaveTimer = null

    const handleMouseEnter = (event) => {
      clearTimeout(leaveTimer)
      enterTimer = setTimeout(() => {
        binding.value?.onEnter?.(event)
      }, options.enterDelay)
    }

    const handleMouseLeave = (event) => {
      clearTimeout(enterTimer)
      leaveTimer = setTimeout(() => {
        binding.value?.onLeave?.(event)
      }, options.leaveDelay)
    }

    el.addEventListener('mouseenter', handleMouseEnter)
    el.addEventListener('mouseleave', handleMouseLeave)

    el._hoverDelayCleanup = () => {
      clearTimeout(enterTimer)
      clearTimeout(leaveTimer)
      el.removeEventListener('mouseenter', handleMouseEnter)
      el.removeEventListener('mouseleave', handleMouseLeave)
    }
  },

  unmounted(el) {
    if (el._hoverDelayCleanup) {
      el._hoverDelayCleanup()
    }
  }
}

// 长按指令
export const longPress = {
  mounted(el, binding) {
    const options = {
      delay: 500,
      ...binding.value
    }

    let pressTimer = null
    let startTime = 0

    const startPress = (event) => {
      if (event.type === 'mousedown' && event.button !== 0) return
      if (event.type === 'touchstart' && event.touches.length > 1) return

      startTime = Date.now()
      pressTimer = setTimeout(() => {
        binding.value?.onLongPress?.(event)
        el._longPressActive = true
      }, options.delay)

      // 添加视觉反馈
      el.style.transform = 'scale(0.95)'
      el.style.transition = 'transform 0.1s ease'
    }

    const cancelPress = () => {
      if (pressTimer) {
        clearTimeout(pressTimer)
        pressTimer = null
      }

      // 恢复视觉效果
      el.style.transform = ''
      el.style.transition = 'transform 0.1s ease'

      // 如果不是长按，则触发点击
      if (!el._longPressActive && Date.now() - startTime < options.delay) {
        binding.value?.onClick?.()
      }

      el._longPressActive = false
    }

    // 鼠标事件
    el.addEventListener('mousedown', startPress)
    el.addEventListener('mouseup', cancelPress)
    el.addEventListener('mouseleave', cancelPress)

    // 触摸事件
    el.addEventListener('touchstart', startPress, { passive: true })
    el.addEventListener('touchend', cancelPress, { passive: true })
    el.addEventListener('touchcancel', cancelPress, { passive: true })

    el._longPressCleanup = () => {
      if (pressTimer) {
        clearTimeout(pressTimer)
      }
      el.removeEventListener('mousedown', startPress)
      el.removeEventListener('mouseup', cancelPress)
      el.removeEventListener('mouseleave', cancelPress)
      el.removeEventListener('touchstart', startPress)
      el.removeEventListener('touchend', cancelPress)
      el.removeEventListener('touchcancel', cancelPress)
    }
  },

  unmounted(el) {
    if (el._longPressCleanup) {
      el._longPressCleanup()
    }
  }
}

// 滚动到视图指令
export const scrollIntoView = {
  mounted(el, binding) {
    const options = {
      behavior: 'smooth',
      block: 'center',
      inline: 'center',
      ...binding.value
    }

    const scrollToElement = () => {
      el.scrollIntoView(options)
    }

    if (binding.value?.immediate) {
      nextTick(scrollToElement)
    }

    // 存储滚动函数供外部调用
    el._scrollIntoView = scrollToElement
  }
}

// 复制到剪贴板指令
export const clipboard = {
  mounted(el, binding) {
    const options = {
      successText: '已复制到剪贴板',
      errorText: '复制失败',
      ...binding.value
    }

    const handleClick = async (event) => {
      event.preventDefault()

      let text = ''
      if (typeof binding.value === 'string') {
        text = binding.value
      } else if (binding.value?.text) {
        text = binding.value.text
      } else if (el.textContent) {
        text = el.textContent.trim()
      } else if (el.value) {
        text = el.value
      }

      try {
        await navigator.clipboard.writeText(text)

        // 成功反馈
        if (options.successText) {
          // 这里可以集成全局消息服务
          console.log(options.successText)
        }

        // 触发成功回调
        binding.value?.onSuccess?.(text)
      } catch (error) {
        console.error('复制失败:', error)

        // 错误反馈
        if (options.errorText) {
          console.error(options.errorText)
        }

        // 触发失败回调
        binding.value?.onError?.(error)
      }
    }

    el.addEventListener('click', handleClick)
    el.style.cursor = 'pointer'

    el._clipboardCleanup = () => {
      el.removeEventListener('click', handleClick)
    }
  },

  unmounted(el) {
    if (el._clipboardCleanup) {
      el._clipboardCleanup()
    }
  }
}

// 导出所有指令
export const directives = {
  keyboardNavigation,
  focusTrap,
  autoFocus,
  hoverDelay,
  longPress,
  scrollIntoView,
  clipboard
}

// Vue插件
export const DirectivesPlugin = {
  install(app) {
    Object.keys(directives).forEach(key => {
      app.directive(key, directives[key])
    })
  }
}

export default directives