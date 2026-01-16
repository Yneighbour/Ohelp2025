import { createApp } from 'vue';
import { createPinia } from 'pinia';
import App from './App.vue';
import router from './router';
import { useAuthStore } from './stores/auth';

// 复用原静态演示版样式（逐步迁移为组件样式前先保留）
import '../styles/variables.css';
import '../styles/common.css';
import '../styles/profile-pages.css';
import './styles/main.css';

const app = createApp(App);
const pinia = createPinia();

app.use(pinia);
app.use(router);

// 应用初始化：从 localStorage 恢复身份信息
const authStore = useAuthStore();
authStore.restoreFromLocalStorage();

app.mount('#app');

