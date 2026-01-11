import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { MessagePlugin } from './shared/message'
import { DirectivesPlugin } from './shared/directives'
import './styles/index.css'

const app = createApp(App)
app.use(router)
app.use(MessagePlugin)
app.use(DirectivesPlugin)
app.mount('#app')