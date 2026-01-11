<template>
  <Transition
    :name="transitionName"
    :mode="transitionMode"
    :duration="duration"
    @enter="onEnter"
    @leave="onLeave"
    @after-enter="onAfterEnter"
    @after-leave="onAfterLeave"
  >
    <slot></slot>
  </Transition>
</template>

<script>
export default {
  name: 'PageTransition',
  props: {
    name: {
      type: String,
      default: 'fade'
    },
    mode: {
      type: String,
      default: 'out-in',
      validator: (value) => ['out-in', 'in-out'].includes(value)
    },
    duration: {
      type: [Number, Object],
      default: 300
    },
    direction: {
      type: String,
      default: 'forward',
      validator: (value) => ['forward', 'backward', 'up', 'down', 'left', 'right'].includes(value)
    }
  },
  emits: ['enter', 'leave', 'after-enter', 'after-leave'],
  computed: {
    transitionName() {
      // 根据方向生成过渡名称
      if (this.name === 'slide') {
        return `slide-${this.direction}`
      }
      if (this.name === 'scale') {
        return 'scale'
      }
      if (this.name === 'bounce') {
        return 'bounce'
      }
      return this.name
    },

    transitionMode() {
      return this.mode
    }
  },
  methods: {
    onEnter(el, done) {
      this.$emit('enter', el, done)
    },

    onLeave(el, done) {
      this.$emit('leave', el, done)
    },

    onAfterEnter(el) {
      this.$emit('after-enter', el)
    },

    onAfterLeave(el) {
      this.$emit('after-leave', el)
    }
  }
}
</script>

<style>
/* 基础淡入淡出 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity var(--transition-base);
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

/* 滑动过渡 */
.slide-forward-enter-active,
.slide-forward-leave-active,
.slide-backward-enter-active,
.slide-backward-leave-active,
.slide-left-enter-active,
.slide-left-leave-active,
.slide-right-enter-active,
.slide-right-leave-active,
.slide-up-enter-active,
.slide-up-leave-active,
.slide-down-enter-active,
.slide-down-leave-active {
  transition: all var(--transition-base) ease-in-out;
}

.slide-forward-enter-from,
.slide-left-enter-from {
  opacity: 0;
  transform: translateX(30px);
}

.slide-forward-leave-to,
.slide-left-leave-to {
  opacity: 0;
  transform: translateX(-30px);
}

.slide-backward-enter-from,
.slide-right-enter-from {
  opacity: 0;
  transform: translateX(-30px);
}

.slide-backward-leave-to,
.slide-right-leave-to {
  opacity: 0;
  transform: translateX(30px);
}

.slide-up-enter-from {
  opacity: 0;
  transform: translateY(30px);
}

.slide-up-leave-to {
  opacity: 0;
  transform: translateY(-30px);
}

.slide-down-enter-from {
  opacity: 0;
  transform: translateY(-30px);
}

.slide-down-leave-to {
  opacity: 0;
  transform: translateY(30px);
}

/* 缩放过渡 */
.scale-enter-active,
.scale-leave-active {
  transition: all var(--transition-base) ease-in-out;
}

.scale-enter-from,
.scale-leave-to {
  opacity: 0;
  transform: scale(0.95);
}

/* 弹跳过渡 */
.bounce-enter-active,
.bounce-leave-active {
  animation-duration: 0.5s;
  animation-fill-mode: both;
}

.bounce-enter-from {
  animation-name: bounceIn;
}

.bounce-leave-to {
  animation-name: bounceOut;
}

@keyframes bounceIn {
  0% {
    opacity: 0;
    transform: scale(0.3);
  }
  50% {
    opacity: 1;
    transform: scale(1.05);
  }
  70% {
    transform: scale(0.9);
  }
  100% {
    opacity: 1;
    transform: scale(1);
  }
}

@keyframes bounceOut {
  0% {
    opacity: 1;
    transform: scale(1);
  }
  30% {
    transform: scale(1.05);
  }
  100% {
    opacity: 0;
    transform: scale(0.3);
  }
}

/* 路由页面过渡 */
.router-view-enter-active,
.router-view-leave-active {
  transition: all 0.3s ease-in-out;
}

.router-view-enter-from {
  opacity: 0;
  transform: translateX(20px);
}

.router-view-leave-to {
  opacity: 0;
  transform: translateX(-20px);
}

/* 加载过渡 */
.loading-enter-active,
.loading-leave-active {
  transition: opacity var(--transition-fast);
}

.loading-enter-from,
.loading-leave-to {
  opacity: 0;
}

/* 模态框过渡 */
.modal-enter-active,
.modal-leave-active {
  transition: all 0.3s ease-in-out;
}

.modal-enter-from {
  opacity: 0;
  transform: scale(0.8) translateY(-20px);
}

.modal-leave-to {
  opacity: 0;
  transform: scale(0.8) translateY(-20px);
}

/* 列表项过渡 */
.list-enter-active,
.list-leave-active {
  transition: all 0.3s ease;
}

.list-enter-from,
.list-leave-to {
  opacity: 0;
  transform: translateX(30px);
}

.list-move {
  transition: transform 0.3s ease;
}

/* 卡片过渡 */
.card-enter-active,
.card-leave-active {
  transition: all 0.4s ease;
}

.card-enter-from,
.card-leave-to {
  opacity: 0;
  transform: translateY(20px) scale(0.95);
}

.card-move {
  transition: transform 0.3s ease;
}

/* 无障碍支持 - 减少动画 */
@media (prefers-reduced-motion: reduce) {
  .fade-enter-active,
  .fade-leave-active,
  .slide-forward-enter-active,
  .slide-forward-leave-active,
  .slide-backward-enter-active,
  .slide-backward-leave-active,
  .slide-left-enter-active,
  .slide-left-leave-active,
  .slide-right-enter-active,
  .slide-right-leave-active,
  .slide-up-enter-active,
  .slide-up-leave-active,
  .slide-down-enter-active,
  .slide-down-leave-active,
  .scale-enter-active,
  .scale-leave-active,
  .bounce-enter-active,
  .bounce-leave-active,
  .router-view-enter-active,
  .router-view-leave-active,
  .loading-enter-active,
  .loading-leave-active,
  .modal-enter-active,
  .modal-leave-active,
  .list-enter-active,
  .list-leave-active,
  .list-move,
  .card-enter-active,
  .card-leave-active,
  .card-move {
    transition: none;
    animation: none;
  }
}

/* 高对比度模式 */
@media (prefers-contrast: high) {
  .fade-enter-from,
  .fade-leave-to,
  .slide-forward-enter-from,
  .slide-forward-leave-to,
  .slide-backward-enter-from,
  .slide-backward-leave-to,
  .slide-left-enter-from,
  .slide-left-leave-to,
  .slide-right-enter-from,
  .slide-right-leave-to,
  .slide-up-enter-from,
  .slide-up-leave-to,
  .slide-down-enter-from,
  .slide-down-leave-to,
  .scale-enter-from,
  .scale-leave-to {
    opacity: 1;
    transform: none;
  }
}
</style>