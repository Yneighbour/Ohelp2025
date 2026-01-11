<template>
  <div class="chart-canvas-wrapper">
    <canvas
      ref="canvas"
      :width="width"
      :height="height"
      class="chart-canvas"
      :class="canvasClass"
    ></canvas>

    <!-- 图表图例 -->
    <div v-if="showLegend && legendData.length > 0" class="chart-legend">
      <div
        v-for="(item, index) in legendData"
        :key="index"
        class="legend-item"
        @click="$emit('legend-click', item, index)"
      >
        <span
          class="legend-color"
          :style="{ backgroundColor: item.color }"
        ></span>
        <span class="legend-label">{{ item.label }}</span>
        <span v-if="item.value !== undefined" class="legend-value">{{ item.value }}</span>
      </div>
    </div>

    <!-- 加载状态 -->
    <div v-if="loading" class="chart-loading">
      <div class="loading-spinner"></div>
      <span class="loading-text">{{ loadingText }}</span>
    </div>

    <!-- 空数据状态 -->
    <div v-else-if="isEmpty" class="chart-empty">
      <svg viewBox="0 0 24 24" fill="none" stroke="currentColor">
        <path d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z" />
      </svg>
      <span class="empty-text">{{ emptyText }}</span>
    </div>

    <!-- 工具提示 -->
    <div
      v-if="showTooltip && tooltip.visible"
      class="chart-tooltip"
      :style="{
        left: tooltip.x + 'px',
        top: tooltip.y + 'px',
        transform: tooltipPosition
      }"
    >
      <div class="tooltip-content">
        <div v-if="tooltip.title" class="tooltip-title">{{ tooltip.title }}</div>
        <div
          v-for="(item, index) in tooltip.items"
          :key="index"
          class="tooltip-item"
        >
          <span
            v-if="item.color"
            class="tooltip-color"
            :style="{ backgroundColor: item.color }"
          ></span>
          <span class="tooltip-label">{{ item.label }}</span>
          <span class="tooltip-value">{{ item.value }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ChartCanvas',
  props: {
    width: {
      type: [Number, String],
      default: 400
    },
    height: {
      type: [Number, String],
      default: 300
    },
    data: {
      type: Array,
      default: () => []
    },
    type: {
      type: String,
      default: 'bar',
      validator: (value) => ['bar', 'line', 'pie', 'doughnut', 'area'].includes(value)
    },
    options: {
      type: Object,
      default: () => ({})
    },
    colors: {
      type: Array,
      default: () => [
        '#7C3AED', '#A78BFA', '#C084FC', '#DDD6FE',
        '#10B981', '#34D399', '#6EE7B7', '#A7F3D0',
        '#F59E0B', '#FCD34D', '#FDE68A', '#FEF3C7',
        '#EF4444', '#F87171', '#FCA5A5', '#FEE2E2'
      ]
    },
    showLegend: {
      type: Boolean,
      default: true
    },
    showTooltip: {
      type: Boolean,
      default: true
    },
    loading: {
      type: Boolean,
      default: false
    },
    loadingText: {
      type: String,
      default: '加载中...'
    },
    emptyText: {
      type: String,
      default: '暂无数据'
    },
    canvasClass: {
      type: String,
      default: ''
    }
  },
  emits: ['legend-click', 'tooltip-show', 'tooltip-hide'],
  data() {
    return {
      ctx: null,
      legendData: [],
      tooltip: {
        visible: false,
        x: 0,
        y: 0,
        title: '',
        items: []
      },
      isEmpty: false,
      animationFrame: null
    }
  },
  computed: {
    tooltipPosition() {
      // 根据位置调整transform
      return 'translate(-50%, -100%)'
    }
  },
  mounted() {
    this.initCanvas()
    this.renderChart()
  },
  beforeUnmount() {
    if (this.animationFrame) {
      cancelAnimationFrame(this.animationFrame)
    }
  },
  watch: {
    data: {
      handler() {
        this.renderChart()
      },
      deep: true
    },
    type() {
      this.renderChart()
    },
    options: {
      handler() {
        this.renderChart()
      },
      deep: true
    }
  },
  methods: {
    initCanvas() {
      const canvas = this.$refs.canvas
      if (canvas) {
        this.ctx = canvas.getContext('2d')
        // 设置高清屏支持
        const dpr = window.devicePixelRatio || 1
        const rect = canvas.getBoundingClientRect()
        canvas.width = rect.width * dpr
        canvas.height = rect.height * dpr
        this.ctx.scale(dpr, dpr)
        canvas.style.width = rect.width + 'px'
        canvas.style.height = rect.height + 'px'
      }
    },

    renderChart() {
      if (!this.ctx || !this.data || this.data.length === 0) {
        this.isEmpty = true
        return
      }

      this.isEmpty = false
      this.clearCanvas()

      switch (this.type) {
        case 'bar':
          this.renderBarChart()
          break
        case 'line':
          this.renderLineChart()
          break
        case 'pie':
          this.renderPieChart()
          break
        case 'doughnut':
          this.renderDoughnutChart()
          break
        case 'area':
          this.renderAreaChart()
          break
      }

      this.updateLegend()
    },

    clearCanvas() {
      if (this.ctx) {
        this.ctx.clearRect(0, 0, this.width, this.height)
      }
    },

    renderBarChart() {
      const { data, colors } = this
      const barWidth = (this.width - 80) / data.length
      const maxValue = Math.max(...data.map(d => d.value || 0))
      const padding = 40

      data.forEach((item, index) => {
        const x = padding + index * barWidth + barWidth * 0.1
        const barHeight = ((item.value || 0) / maxValue) * (this.height - padding * 2)
        const y = this.height - padding - barHeight

        // 绘制柱子
        this.ctx.fillStyle = item.color || colors[index % colors.length]
        this.ctx.fillRect(x, y, barWidth * 0.8, barHeight)

        // 绘制边框
        this.ctx.strokeStyle = item.color || colors[index % colors.length]
        this.ctx.lineWidth = 1
        this.ctx.strokeRect(x, y, barWidth * 0.8, barHeight)

        // 绘制数值标签
        this.ctx.fillStyle = '#374151'
        this.ctx.font = '12px Arial'
        this.ctx.textAlign = 'center'
        this.ctx.fillText(item.value || 0, x + barWidth * 0.4, y - 5)
      })
    },

    renderLineChart() {
      const { data, colors } = this
      const padding = 40
      const maxValue = Math.max(...data.map(d => d.value || 0))
      const minValue = Math.min(...data.map(d => d.value || 0))
      const range = maxValue - minValue || 1

      // 绘制线条
      this.ctx.strokeStyle = colors[0]
      this.ctx.lineWidth = 3
      this.ctx.beginPath()

      data.forEach((item, index) => {
        const x = padding + (index / (data.length - 1)) * (this.width - padding * 2)
        const y = this.height - padding - ((item.value - minValue) / range) * (this.height - padding * 2)

        if (index === 0) {
          this.ctx.moveTo(x, y)
        } else {
          this.ctx.lineTo(x, y)
        }

        // 绘制数据点
        this.ctx.fillStyle = colors[0]
        this.ctx.beginPath()
        this.ctx.arc(x, y, 4, 0, Math.PI * 2)
        this.ctx.fill()

        // 绘制数值标签
        this.ctx.fillStyle = '#374151'
        this.ctx.font = '12px Arial'
        this.ctx.textAlign = 'center'
        this.ctx.fillText(item.value || 0, x, y - 15)
      })

      this.ctx.stroke()
    },

    renderPieChart() {
      const { data, colors } = this
      const centerX = this.width / 2
      const centerY = this.height / 2
      const radius = Math.min(centerX, centerY) - 40
      const total = data.reduce((sum, item) => sum + (item.value || 0), 0)

      let startAngle = -Math.PI / 2

      data.forEach((item, index) => {
        const value = item.value || 0
        const angle = (value / total) * Math.PI * 2

        // 绘制扇形
        this.ctx.fillStyle = item.color || colors[index % colors.length]
        this.ctx.beginPath()
        this.ctx.moveTo(centerX, centerY)
        this.ctx.arc(centerX, centerY, radius, startAngle, startAngle + angle)
        this.ctx.closePath()
        this.ctx.fill()

        // 绘制边框
        this.ctx.strokeStyle = '#FFFFFF'
        this.ctx.lineWidth = 2
        this.ctx.stroke()

        // 计算标签位置
        const labelAngle = startAngle + angle / 2
        const labelX = centerX + Math.cos(labelAngle) * (radius * 0.7)
        const labelY = centerY + Math.sin(labelAngle) * (radius * 0.7)

        // 绘制百分比标签
        const percentage = ((value / total) * 100).toFixed(1)
        this.ctx.fillStyle = '#FFFFFF'
        this.ctx.font = 'bold 14px Arial'
        this.ctx.textAlign = 'center'
        this.ctx.fillText(`${percentage}%`, labelX, labelY)

        startAngle += angle
      })
    },

    renderDoughnutChart() {
      const { data, colors } = this
      const centerX = this.width / 2
      const centerY = this.height / 2
      const outerRadius = Math.min(centerX, centerY) - 20
      const innerRadius = outerRadius * 0.6
      const total = data.reduce((sum, item) => sum + (item.value || 0), 0)

      let startAngle = -Math.PI / 2

      data.forEach((item, index) => {
        const value = item.value || 0
        const angle = (value / total) * Math.PI * 2

        // 绘制外弧
        this.ctx.fillStyle = item.color || colors[index % colors.length]
        this.ctx.beginPath()
        this.ctx.arc(centerX, centerY, outerRadius, startAngle, startAngle + angle)
        this.ctx.arc(centerX, centerY, innerRadius, startAngle + angle, startAngle, true)
        this.ctx.closePath()
        this.ctx.fill()

        // 绘制边框
        this.ctx.strokeStyle = '#FFFFFF'
        this.ctx.lineWidth = 2
        this.ctx.stroke()

        startAngle += angle
      })

      // 绘制中心空白
      this.ctx.fillStyle = '#FFFFFF'
      this.ctx.beginPath()
      this.ctx.arc(centerX, centerY, innerRadius, 0, Math.PI * 2)
      this.ctx.fill()
    },

    renderAreaChart() {
      const { data, colors } = this
      const padding = 40
      const maxValue = Math.max(...data.map(d => d.value || 0))
      const minValue = Math.min(...data.map(d => d.value || 0))
      const range = maxValue - minValue || 1

      // 绘制填充区域
      this.ctx.fillStyle = colors[0] + '40' // 40% 透明度
      this.ctx.beginPath()

      // 起始点
      const firstX = padding
      const firstY = this.height - padding - ((data[0]?.value - minValue) / range) * (this.height - padding * 2)
      this.ctx.moveTo(firstX, this.height - padding)
      this.ctx.lineTo(firstX, firstY)

      // 绘制数据线
      data.forEach((item, index) => {
        const x = padding + (index / (data.length - 1)) * (this.width - padding * 2)
        const y = this.height - padding - ((item.value - minValue) / range) * (this.height - padding * 2)
        this.ctx.lineTo(x, y)
      })

      // 封闭路径
      const lastX = padding + (this.width - padding * 2)
      this.ctx.lineTo(lastX, this.height - padding)
      this.ctx.closePath()
      this.ctx.fill()

      // 绘制边框线
      this.ctx.strokeStyle = colors[0]
      this.ctx.lineWidth = 2
      this.ctx.beginPath()
      this.ctx.moveTo(firstX, firstY)

      data.forEach((item, index) => {
        const x = padding + (index / (data.length - 1)) * (this.width - padding * 2)
        const y = this.height - padding - ((item.value - minValue) / range) * (this.height - padding * 2)
        this.ctx.lineTo(x, y)
      })

      this.ctx.stroke()
    },

    updateLegend() {
      this.legendData = this.data.map((item, index) => ({
        label: item.label || `项目 ${index + 1}`,
        value: item.value,
        color: item.color || this.colors[index % this.colors.length]
      }))
    },

    showTooltip(x, y, title, items) {
      this.tooltip = {
        visible: true,
        x,
        y,
        title,
        items
      }
      this.$emit('tooltip-show', this.tooltip)
    },

    hideTooltip() {
      this.tooltip.visible = false
      this.$emit('tooltip-hide')
    }
  }
}
</script>

<style scoped>
.chart-canvas-wrapper {
  position: relative;
  width: 100%;
  height: 100%;
  min-height: 200px;
}

.chart-canvas {
  display: block;
  width: 100%;
  height: 100%;
  border-radius: var(--border-radius-md);
}

/* 图例样式 */
.chart-legend {
  display: flex;
  flex-wrap: wrap;
  gap: var(--spacing-md);
  margin-top: var(--spacing-md);
  padding: var(--spacing-md);
  background-color: var(--background-secondary);
  border-radius: var(--border-radius-md);
}

.legend-item {
  display: flex;
  align-items: center;
  gap: var(--spacing-sm);
  cursor: pointer;
  padding: var(--spacing-xs) var(--spacing-sm);
  border-radius: var(--border-radius-sm);
  transition: background-color var(--transition-fast);
}

.legend-item:hover {
  background-color: var(--background-color);
}

.legend-color {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  flex-shrink: 0;
}

.legend-label {
  font-size: var(--font-size-sm);
  color: var(--text-secondary);
}

.legend-value {
  font-size: var(--font-size-sm);
  font-weight: 600;
  color: var(--text-primary);
  margin-left: auto;
}

/* 加载状态 */
.chart-loading {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: var(--spacing-md);
  color: var(--text-secondary);
}

.loading-spinner {
  width: 32px;
  height: 32px;
  border: 3px solid var(--border-color);
  border-top-color: var(--primary-color);
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

.loading-text {
  font-size: var(--font-size-sm);
}

/* 空数据状态 */
.chart-empty {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: var(--spacing-md);
  color: var(--text-secondary);
}

.chart-empty svg {
  width: 48px;
  height: 48px;
  opacity: 0.5;
}

.empty-text {
  font-size: var(--font-size-base);
  text-align: center;
}

/* 工具提示 */
.chart-tooltip {
  position: fixed;
  pointer-events: none;
  z-index: 1100;
  background-color: var(--white);
  border: 1px solid var(--border-color);
  border-radius: var(--border-radius-md);
  box-shadow: var(--shadow-lg);
  padding: var(--spacing-md);
  max-width: 200px;
  animation: fadeIn 0.2s ease-out;
}

.tooltip-content {
  color: var(--text-primary);
}

.tooltip-title {
  font-size: var(--font-size-sm);
  font-weight: 600;
  margin-bottom: var(--spacing-sm);
  color: var(--text-primary);
}

.tooltip-item {
  display: flex;
  align-items: center;
  gap: var(--spacing-sm);
  font-size: var(--font-size-sm);
  margin-bottom: var(--spacing-xs);
}

.tooltip-item:last-child {
  margin-bottom: 0;
}

.tooltip-color {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  flex-shrink: 0;
}

.tooltip-label {
  flex: 1;
  color: var(--text-secondary);
}

.tooltip-value {
  font-weight: 600;
  color: var(--text-primary);
}

/* 动画 */
@keyframes spin {
  to {
    transform: translate(-50%, -50%) rotate(360deg);
  }
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translate(-50%, -100%) scale(0.9);
  }
  to {
    opacity: 1;
    transform: translate(-50%, -100%) scale(1);
  }
}

/* 响应式适配 */
@media (max-width: 768px) {
  .chart-legend {
    flex-direction: column;
    gap: var(--spacing-sm);
  }

  .legend-item {
    justify-content: space-between;
  }

  .chart-tooltip {
    max-width: 150px;
    padding: var(--spacing-sm);
  }
}
</style>