import { createVNode, render } from 'vue'
import MessageToast from '../components/common/MessageToast.vue'

// 全局消息服务
let messageInstance = null
let mountContainer = null

export const MessageService = {
  // 初始化消息实例
  init(instance) {
    messageInstance = instance
  },

  // 卸载（可选）
  destroy() {
    if (mountContainer) {
      render(null, mountContainer)
      mountContainer.remove()
      mountContainer = null
    }
    messageInstance = null
  },

  // 成功消息
  success(content, options = {}) {
    if (messageInstance) {
      return messageInstance.success(content, options)
    }
    console.warn('MessageService not initialized')
  },

  // 错误消息
  error(content, options = {}) {
    if (messageInstance) {
      return messageInstance.error(content, options)
    }
    console.warn('MessageService not initialized')
  },

  // 警告消息
  warning(content, options = {}) {
    if (messageInstance) {
      return messageInstance.warning(content, options)
    }
    console.warn('MessageService not initialized')
  },

  // 信息消息
  info(content, options = {}) {
    if (messageInstance) {
      return messageInstance.info(content, options)
    }
    console.warn('MessageService not initialized')
  },

  // 加载消息
  loading(content, options = {}) {
    if (messageInstance) {
      return messageInstance.loading(content, options)
    }
    console.warn('MessageService not initialized')
  },

  // 清除所有消息
  clear() {
    if (messageInstance) {
      messageInstance.clear()
    }
  }
}

// Vue 插件
export const MessagePlugin = {
  install(app) {
    // 全局注册（可用于模板直接引用）
    app.component('MessageToast', MessageToast)

    // 创建实例并挂载到 body（Vue 3）
    mountContainer = document.createElement('div')
    document.body.appendChild(mountContainer)

    const vnode = createVNode(MessageToast)
    vnode.appContext = app._context
    render(vnode, mountContainer)

    // MessageToast 是 Options API 组件：通过 proxy 拿到实例方法
    const instanceProxy = vnode.component?.proxy
    if (instanceProxy) {
      MessageService.init(instanceProxy)
    }

    // 挂载到全局属性
    app.config.globalProperties.$message = MessageService
    app.provide('message', MessageService)
  }
}

export default MessageService