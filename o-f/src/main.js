import { createApp } from 'vue'
import { createPinia } from 'pinia'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import './styles/main.css'

const app = createApp(App)
app.use(createPinia())
app.use(router)
app.use(ElementPlus)

// 根据打开的 HTML 文件决定初始路由（支持 multi-html 入口）
const path = location.pathname || ''
app.mount('#app')

router.isReady().then(() => {
  if (path.includes('dashboard.html')) router.push('/dashboard')
  else if (path.includes('Laoren.html')) router.push('/laoren')
  else if (path.includes('requests.html')) router.push('/requests')
  else router.push('/')
})

export default app
