import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'

export default defineConfig({
  plugins: [vue()],
  // 使用默认 single-page 应用构建（保留 HTML 文件但不作为独立入口）
  build: {}
})
