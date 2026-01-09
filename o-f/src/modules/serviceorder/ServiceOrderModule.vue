<template>
  <div class="serviceorder-module">
    <div class="card">
      <h2>服务订单模块</h2>
      <p>服务订单管理功能</p>
      <button @click="loadOrders" class="btn btn-primary">加载服务订单</button>
      <div v-if="orders.length > 0">
        <table class="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>老人ID</th>
              <th>服务类型</th>
              <th>价格</th>
              <th>状态</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="order in orders" :key="order.id">
              <td>{{ order.id }}</td>
              <td>{{ order.elderlyId }}</td>
              <td>{{ order.serviceType }}</td>
              <td>{{ order.price }}</td>
              <td>{{ order.status }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import { getAllServiceOrders } from './serviceorder.api.js'

export default {
  name: 'ServiceOrderModule',
  data() {
    return {
      orders: []
    }
  },
  methods: {
    async loadOrders() {
      try {
        const response = await getAllServiceOrders()
        this.orders = response.data
      } catch (error) {
        alert('加载失败: ' + error.message)
      }
    }
  }
}
</script>

<style scoped>
.serviceorder-module {
  padding: 1rem 0;
}
</style>