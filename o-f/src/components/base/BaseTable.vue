<template>
  <div class="base-table-wrapper">
    <!-- 表格容器 -->
    <div class="table-container" :class="{ 'table-loading': loading }">
      <table class="base-table" :class="tableSize ? `table-${tableSize}` : ''">
        <!-- 表头 -->
        <thead class="table-header">
          <tr>
            <!-- 选择列 -->
            <th v-if="selectable" class="table-cell table-select-cell">
              <label class="checkbox-wrapper">
                <input
                  type="checkbox"
                  :checked="isAllSelected"
                  :indeterminate="isIndeterminate"
                  @change="handleSelectAll"
                />
                <span class="checkbox-mark"></span>
              </label>
            </th>

            <!-- 数据列 -->
            <th
              v-for="column in columns"
              :key="column.key"
              class="table-cell table-header-cell"
              :class="[
                column.align ? `text-${column.align}` : '',
                column.sortable ? 'sortable' : '',
                column.width ? '' : 'flex-1'
              ]"
              :style="{ width: column.width, minWidth: column.minWidth }"
              @click="column.sortable && handleSort(column.key)"
            >
              <div class="header-content">
                <span class="header-text">{{ column.title }}</span>
                <div v-if="column.sortable" class="sort-indicators">
                  <svg
                    class="sort-icon"
                    :class="{ active: sortField === column.key && sortOrder === 'asc' }"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                  >
                    <path d="M7 14l5-5 5 5z" />
                  </svg>
                  <svg
                    class="sort-icon"
                    :class="{ active: sortField === column.key && sortOrder === 'desc' }"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                  >
                    <path d="M7 10l5 5 5-5z" />
                  </svg>
                </div>
              </div>
            </th>

            <!-- 操作列 -->
            <th v-if="$scopedSlots.actions || hasActions" class="table-cell table-actions-cell">
              {{ actionsTitle || '操作' }}
            </th>
          </tr>
        </thead>

        <!-- 表体 -->
        <tbody class="table-body">
          <tr
            v-for="(row, index) in displayData"
            :key="rowKey ? row[rowKey] : index"
            class="table-row"
            :class="{ 'row-selected': isSelected(row), 'row-hover': hover }"
            @click="handleRowClick(row, index, $event)"
          >
            <!-- 选择列 -->
            <td v-if="selectable" class="table-cell table-select-cell">
              <label class="checkbox-wrapper">
                <input
                  type="checkbox"
                  :checked="isSelected(row)"
                  @change="handleSelectRow(row)"
                />
                <span class="checkbox-mark"></span>
              </label>
            </td>

            <!-- 数据列 -->
            <td
              v-for="column in columns"
              :key="column.key"
              class="table-cell"
              :class="column.align ? `text-${column.align}` : ''"
            >
              <slot
                :name="`column-${column.key}`"
                :row="row"
                :column="column"
                :index="index"
                :value="getCellValue(row, column)"
              >
                {{ getCellValue(row, column) }}
              </slot>
            </td>

            <!-- 操作列 -->
            <td v-if="$scopedSlots.actions || hasActions" class="table-cell table-actions-cell">
              <slot name="actions" :row="row" :index="index" />
            </td>
          </tr>

          <!-- 空数据状态 -->
          <tr v-if="displayData.length === 0" class="table-empty-row">
            <td
              :colspan="totalColumns"
              class="table-cell table-empty-cell"
            >
              <div class="empty-content">
                <slot name="empty">
                  <div class="empty-text">{{ emptyText }}</div>
                </slot>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- 加载状态 -->
    <div v-if="loading" class="table-loading-overlay">
      <div class="loading-spinner"></div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'BaseTable',
  props: {
    columns: {
      type: Array,
      default: () => [],
      validator: (columns) => {
        return columns.every(col =>
          col.key && col.title &&
          ['left', 'center', 'right', undefined].includes(col.align) &&
          [true, false, undefined].includes(col.sortable)
        )
      }
    },
    data: {
      type: Array,
      default: () => []
    },
    loading: {
      type: Boolean,
      default: false
    },
    size: {
      type: String,
      default: '',
      validator: (value) => ['', 'sm', 'lg'].includes(value)
    },
    hover: {
      type: Boolean,
      default: true
    },
    selectable: {
      type: Boolean,
      default: false
    },
    selectedRows: {
      type: Array,
      default: () => []
    },
    rowKey: {
      type: String,
      default: ''
    },
    sortField: {
      type: String,
      default: ''
    },
    sortOrder: {
      type: String,
      default: 'asc',
      validator: (value) => ['asc', 'desc'].includes(value)
    },
    emptyText: {
      type: String,
      default: '暂无数据'
    },
    actionsTitle: {
      type: String,
      default: '操作'
    }
  },
  emits: ['select-change', 'sort-change', 'row-click'],
  computed: {
    tableSize() {
      return this.size
    },
    totalColumns() {
      let count = this.columns.length
      if (this.selectable) count++
      if (this.$scopedSlots.actions || this.hasActions) count++
      return count
    },
    displayData() {
      if (!this.sortField) return this.data

      return [...this.data].sort((a, b) => {
        const aValue = this.getSortValue(a, this.sortField)
        const bValue = this.getSortValue(b, this.sortField)

        if (aValue < bValue) return this.sortOrder === 'asc' ? -1 : 1
        if (aValue > bValue) return this.sortOrder === 'asc' ? 1 : -1
        return 0
      })
    },
    isAllSelected() {
      return this.selectedRows.length === this.data.length && this.data.length > 0
    },
    isIndeterminate() {
      return this.selectedRows.length > 0 && this.selectedRows.length < this.data.length
    },
    hasActions() {
      return this.$scopedSlots.actions
    }
  },
  methods: {
    getCellValue(row, column) {
      if (typeof column.render === 'function') {
        return column.render(row[column.key], row, column)
      }
      return row[column.key]
    },
    getSortValue(row, field) {
      const value = row[field]
      if (typeof value === 'string') {
        return value.toLowerCase()
      }
      return value
    },
    isSelected(row) {
      if (!this.rowKey) return false
      return this.selectedRows.some(selected => selected[this.rowKey] === row[this.rowKey])
    },
    handleSelectAll(event) {
      const selected = event.target.checked ? [...this.data] : []
      this.$emit('select-change', selected)
    },
    handleSelectRow(row) {
      const isSelected = this.isSelected(row)
      let newSelection

      if (isSelected) {
        newSelection = this.selectedRows.filter(selected =>
          selected[this.rowKey] !== row[this.rowKey]
        )
      } else {
        newSelection = [...this.selectedRows, row]
      }

      this.$emit('select-change', newSelection)
    },
    handleSort(field) {
      let newOrder = 'asc'
      if (this.sortField === field && this.sortOrder === 'asc') {
        newOrder = 'desc'
      }
      this.$emit('sort-change', { field, order: newOrder })
    },
    handleRowClick(row, index, event) {
      // 如果点击的是选择框，不触发行点击
      if (event.target.type === 'checkbox') return

      this.$emit('row-click', { row, index, event })
    }
  }
}
</script>

<style scoped>
.base-table-wrapper {
  position: relative;
  width: 100%;
  overflow: hidden;
  border-radius: var(--border-radius-md);
  background-color: var(--white);
  box-shadow: var(--shadow-sm);
}

/* 表格容器 */
.table-container {
  overflow-x: auto;
  position: relative;
}

.table-loading {
  opacity: 0.6;
  pointer-events: none;
}

/* 基础表格样式 */
.base-table {
  width: 100%;
  border-collapse: collapse;
  font-size: var(--font-size-base);
  line-height: var(--line-height-base);
}

.base-table.table-sm {
  font-size: var(--font-size-sm);
}

.base-table.table-lg {
  font-size: var(--font-size-lg);
}

/* 表格头部 */
.table-header {
  background-color: var(--table-header-bg);
}

.table-header-cell {
  position: sticky;
  top: 0;
  z-index: 10;
  font-weight: 600;
  color: var(--text-primary);
  border-bottom: 2px solid var(--border-color);
  user-select: none;
}

.table-header-cell.sortable {
  cursor: pointer;
  transition: background-color var(--transition-fast);
}

.table-header-cell.sortable:hover {
  background-color: var(--background-secondary);
}

/* 头部内容 */
.header-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: var(--spacing-sm) 0;
}

.header-text {
  flex: 1;
}

/* 排序指示器 */
.sort-indicators {
  display: flex;
  flex-direction: column;
  margin-left: var(--spacing-sm);
}

.sort-icon {
  width: 12px;
  height: 12px;
  opacity: 0.3;
  transition: opacity var(--transition-fast);
}

.sort-icon.active {
  opacity: 1;
  color: var(--primary-color);
}

/* 表格行 */
.table-row {
  transition: background-color var(--transition-fast);
}

.table-row.row-hover:hover {
  background-color: var(--background-secondary);
  cursor: pointer;
}

.table-row.row-selected {
  background-color: rgba(124, 58, 237, 0.05);
}

/* 表格单元格 */
.table-cell {
  padding: var(--spacing-md);
  border-bottom: 1px solid var(--table-border-color);
  vertical-align: middle;
}

.table-cell.table-select-cell {
  width: 48px;
  text-align: center;
}

.table-cell.table-actions-cell {
  width: 120px;
  white-space: nowrap;
}

.table-cell.text-center {
  text-align: center;
}

.table-cell.text-right {
  text-align: right;
}

/* 选择框样式 */
.checkbox-wrapper {
  position: relative;
  display: inline-block;
  cursor: pointer;
  user-select: none;
}

.checkbox-wrapper input {
  position: absolute;
  opacity: 0;
  cursor: pointer;
}

.checkbox-mark {
  display: inline-block;
  width: 16px;
  height: 16px;
  border: 2px solid var(--border-color);
  border-radius: 2px;
  background-color: var(--white);
  transition: all var(--transition-fast);
}

.checkbox-wrapper input:checked ~ .checkbox-mark {
  background-color: var(--primary-color);
  border-color: var(--primary-color);
}

.checkbox-wrapper input:checked ~ .checkbox-mark::after {
  content: '';
  position: absolute;
  left: 5px;
  top: 2px;
  width: 4px;
  height: 8px;
  border: solid var(--white);
  border-width: 0 2px 2px 0;
  transform: rotate(45deg);
}

.checkbox-wrapper input:indeterminate ~ .checkbox-mark::after {
  content: '';
  position: absolute;
  left: 3px;
  top: 7px;
  width: 8px;
  height: 2px;
  background-color: var(--primary-color);
  border: none;
  transform: none;
}

/* 空数据状态 */
.table-empty-row {
  background-color: var(--background-secondary);
}

.table-empty-cell {
  text-align: center;
  color: var(--text-secondary);
  font-style: italic;
}

.empty-content {
  padding: var(--spacing-xl) var(--spacing-md);
}

.empty-text {
  margin: 0;
}

/* 加载状态 */
.table-loading-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: rgba(255, 255, 255, 0.8);
  z-index: 20;
}

.loading-spinner {
  width: 32px;
  height: 32px;
  border: 3px solid var(--border-color);
  border-top-color: var(--primary-color);
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

/* 响应式适配 */
@media (max-width: 768px) {
  .table-cell {
    padding: var(--spacing-sm);
    font-size: var(--font-size-sm);
  }

  .table-cell.table-actions-cell {
    width: 100px;
  }

  .header-content {
    flex-direction: column;
    align-items: flex-start;
    gap: var(--spacing-xs);
  }

  .sort-indicators {
    margin-left: 0;
    margin-top: var(--spacing-xs);
  }
}
</style>