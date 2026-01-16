import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import path from 'path';

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'),
    },
  },
  server: {
    port: 5173,
    proxy: {
      // 开发期走同源代理，避免 CORS + 便于改环境
      '/api': {
        target: 'http://localhost:8090',
        changeOrigin: true,
        // 将前缀 /api 去掉，映射到后端根路径
        rewrite: (path) => path.replace(/^\/api/, ''),
      },
    },
  },
});
