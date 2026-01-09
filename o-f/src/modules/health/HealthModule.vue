<template>
  <div class="health-module">
    <div class="card">
      <h2>健康管理模块</h2>
      <p>健康记录管理功能</p>
      <button @click="loadRecords" class="btn btn-primary">加载健康记录</button>
      <div v-if="records.length > 0">
        <table class="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>老人ID</th>
              <th>记录日期</th>
              <th>血压</th>
              <th>心率</th>
              <th>体温</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="record in records" :key="record.id">
              <td>{{ record.id }}</td>
              <td>{{ record.elderlyId }}</td>
              <td>{{ record.recordDate }}</td>
              <td>{{ record.bloodPressure }}</td>
              <td>{{ record.heartRate }}</td>
              <td>{{ record.temperature }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import { getAllHealthRecords } from './health.api.js'

export default {
  name: 'HealthModule',
  data() {
    return {
      records: []
    }
  },
  methods: {
    async loadRecords() {
      try {
        const response = await getAllHealthRecords()
        this.records = response.data
      } catch (error) {
        alert('加载失败: ' + error.message)
      }
    }
  }
}
</script>

<style scoped>
.health-module {
  padding: 1rem 0;
}
</style>