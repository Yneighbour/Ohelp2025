import { createApp } from 'vue';
import App from './App.vue';
import router from './router';

// 复用原静态演示版样式（逐步迁移为组件样式前先保留）
import '../styles/variables.css';
import '../styles/common.css';
import '../styles/profile-pages.css';
import './styles/main.css';

createApp(App).use(router).mount('#app');
