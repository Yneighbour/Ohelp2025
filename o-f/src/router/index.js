import { createRouter, createWebHistory } from 'vue-router'
import Home from '../pages/Home.vue'
import Dashboard from '../pages/Dashboard.vue'
import LaorenList from '../pages/LaorenList.vue'
import Requests from '../pages/Requests.vue'

const routes = [
  { path: '/', name: 'Home', component: Home },
  { path: '/dashboard', name: 'Dashboard', component: Dashboard },
  { path: '/laoren', name: 'LaorenList', component: LaorenList },
  { path: '/requests', name: 'Requests', component: Requests }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
