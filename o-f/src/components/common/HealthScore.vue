<template>
  <div class="health-score" :class="size ? `health-score-${size}` : ''">
    <!-- 分数显示区域 -->
    <div class="score-display">
      <!-- 主要分数 -->
      <div class="main-score">
        <span class="score-number">{{ displayScore }}</span>
        <span class="score-unit">分</span>
      </div>

      <!-- 分数等级 -->
      <div class="score-level" :class="`level-${scoreLevel}`">
        {{ scoreLevelText }}
      </div>

      <!-- 等级图标 -->
      <div class="level-icon">
        <svg v-if="scoreLevel === 'excellent'" viewBox="0 0 24 24" fill="none" stroke="currentColor">
          <path d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
        </svg>
        <svg v-else-if="scoreLevel === 'good'" viewBox="0 0 24 24" fill="none" stroke="currentColor">
          <path d="M14.828 14.828a4 4 0 01-5.656 0M9 10h1.586a1 1 0 01.707.293l.707.707A1 1 0 0013.414 11H15m-6 4a2 2 0 100-4 2 2 0 000 4z" />
        </svg>
        <svg v-else-if="scoreLevel === 'fair'" viewBox="0 0 24 24" fill="none" stroke="currentColor">
          <path d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-2.5L13.732 4c-.77-.833-1.964-.833-2.732 0L4.082 16.5c-.77.833.192 2.5 1.732 2.5z" />
        </svg>
        <svg v-else viewBox="0 0 24 24" fill="none" stroke="currentColor">
          <path d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
        </svg>
      </div>
    </div>

    <!-- 环形进度条 -->
    <div class="score-ring">
      <svg class="ring-svg" viewBox="0 0 120 120">
        <!-- 背景环 -->
        <circle
          cx="60"
          cy="60"
          r="50"
          fill="none"
          stroke="var(--background-secondary)"
          stroke-width="8"
        />

        <!-- 进度环 -->
        <circle
          cx="60"
          cy="60"
          r="50"
          fill="none"
          :stroke="ringColor"
          stroke-width="8"
          stroke-linecap="round"
          stroke-dasharray="314"
          :stroke-dashoffset="ringOffset"
          transform="rotate(-90 60 60)"
          class="progress-ring"
        />
      </svg>
    </div>

    <!-- 描述文本 -->
    <div v-if="description" class="score-description">
      {{ description }}
    </div>

    <!-- 建议文本 -->
    <div v-if="$slots.suggestion || suggestion" class="score-suggestion">
      <slot name="suggestion">
        <span v-if="suggestion">{{ suggestion }}</span>
      </slot>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HealthScore',
  props: {
    score: {
      type: [Number, String],
      default: 0,
      validator: (value) => {
        const num = Number(value)
        return num >= 0 && num <= 100
      }
    },
    size: {
      type: String,
      default: '',
      validator: (value) => ['', 'sm', 'lg'].includes(value)
    },
    description: {
      type: String,
      default: ''
    },
    suggestion: {
      type: String,
      default: ''
    },
    animated: {
      type: Boolean,
      default: true
    }
  },
  computed: {
    numericScore() {
      return Math.round(Number(this.score) || 0)
    },
    displayScore() {
      return this.animated ? this.animatedScore : this.numericScore
    },
    scoreLevel() {
      const score = this.numericScore
      if (score >= 90) return 'excellent'
      if (score >= 75) return 'good'
      if (score >= 60) return 'fair'
      return 'poor'
    },
    scoreLevelText() {
      const levelMap = {
        excellent: '优秀',
        good: '良好',
        fair: '一般',
        poor: '较差'
      }
      return levelMap[this.scoreLevel]
    },
    ringColor() {
      const colorMap = {
        excellent: 'var(--success-color)',
        good: 'var(--primary-color)',
        fair: 'var(--warning-color)',
        poor: 'var(--error-color)'
      }
      return colorMap[this.scoreLevel]
    },
    ringOffset() {
      const circumference = 2 * Math.PI * 50 // 2πr
      const progress = this.numericScore / 100
      return circumference - (circumference * progress)
    }
  },
  data() {
    return {
      animatedScore: 0
    }
  },
  mounted() {
    if (this.animated) {
      this.animateScore()
    }
  },
  methods: {
    animateScore() {
      const duration = 1500 // 1.5秒
      const steps = 60
      const increment = this.numericScore / steps
      let currentStep = 0

      const animate = () => {
        currentStep++
        this.animatedScore = Math.round(increment * currentStep)

        if (currentStep < steps) {
          requestAnimationFrame(animate)
        }
      }

      // 延迟开始动画
      setTimeout(() => {
        requestAnimationFrame(animate)
      }, 300)
    }
  }
}
</script>

<style scoped>
.health-score {
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
  padding: var(--spacing-lg);
}

/* 尺寸变体 */
.health-score.health-score-sm {
  padding: var(--spacing-md);
}

.health-score.health-score-lg {
  padding: var(--spacing-xl);
}

/* 分数显示区域 */
.score-display {
  display: flex;
  flex-direction: column;
  align-items: center;
  z-index: 2;
  position: relative;
}

/* 主要分数 */
.main-score {
  display: flex;
  align-items: baseline;
  margin-bottom: var(--spacing-xs);
}

.score-number {
  font-size: 48px;
  font-weight: 700;
  background: var(--primary-gradient);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  line-height: 1;
}

.health-score-sm .score-number {
  font-size: 36px;
}

.health-score-lg .score-number {
  font-size: 64px;
}

.score-unit {
  font-size: 24px;
  font-weight: 500;
  color: var(--text-secondary);
  margin-left: var(--spacing-xs);
}

.health-score-sm .score-unit {
  font-size: 18px;
}

.health-score-lg .score-unit {
  font-size: 32px;
}

/* 分数等级 */
.score-level {
  font-size: var(--font-size-base);
  font-weight: 600;
  padding: var(--spacing-xs) var(--spacing-sm);
  border-radius: var(--border-radius-md);
  margin-bottom: var(--spacing-sm);
}

.score-level.level-excellent {
  background-color: var(--health-normal-bg);
  color: var(--health-normal);
}

.score-level.level-good {
  background: linear-gradient(135deg, #7C3AED 0%, #A78BFA 100%);
  color: var(--white);
}

.score-level.level-fair {
  background-color: var(--health-warning-bg);
  color: var(--health-warning);
}

.score-level.level-poor {
  background-color: var(--health-danger-bg);
  color: var(--health-danger);
}

/* 等级图标 */
.level-icon {
  width: 32px;
  height: 32px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  background-color: var(--white);
  box-shadow: var(--shadow-sm);
  margin-bottom: var(--spacing-md);
}

.level-icon svg {
  width: 20px;
  height: 20px;
  color: var(--primary-color);
}

/* 环形进度条 */
.score-ring {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 1;
}

.ring-svg {
  width: 120px;
  height: 120px;
}

.health-score-sm .ring-svg {
  width: 100px;
  height: 100px;
}

.health-score-lg .ring-svg {
  width: 160px;
  height: 160px;
}

/* 进度环动画 */
.progress-ring {
  transition: stroke-dashoffset 1.5s ease-in-out;
}

/* 描述文本 */
.score-description {
  text-align: center;
  font-size: var(--font-size-sm);
  color: var(--text-secondary);
  margin-bottom: var(--spacing-sm);
  max-width: 200px;
}

/* 建议文本 */
.score-suggestion {
  text-align: center;
  font-size: var(--font-size-sm);
  color: var(--primary-color);
  font-weight: 500;
  max-width: 200px;
}

/* 响应式适配 */
@media (max-width: 768px) {
  .health-score {
    padding: var(--spacing-md);
  }

  .score-number {
    font-size: 36px;
  }

  .score-unit {
    font-size: 18px;
  }

  .ring-svg {
    width: 100px;
    height: 100px;
  }

  .score-description,
  .score-suggestion {
    font-size: var(--font-size-xs);
    max-width: 160px;
  }
}
</style>