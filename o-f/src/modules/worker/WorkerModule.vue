<template>
  <div class="worker-module">
    <div class="card">
      <h2>工作人员模块</h2>
      <p>工作人员管理功能</p>
      <button @click="loadWorkers" class="btn btn-primary">加载工作人员</button>
      <div v-if="workers.length > 0">
        <table class="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>姓名</th>
              <th>职位</th>
              <th>部门</th>
              <th>状态</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="worker in workers" :key="worker.id">
              <td>{{ worker.id }}</td>
              <td>{{ worker.name }}</td>
              <td>{{ worker.position }}</td>
              <td>{{ worker.department }}</td>
              <td>{{ worker.isActive ? '激活' : '未激活' }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import { getAllWorkers } from './worker.api.js'

export default {
  name: 'WorkerModule',
  data() {
    return {
      workers: []
    }
  },
  methods: {
    async loadWorkers() {
      try {
        const response = await getAllWorkers()
        this.workers = response.data
      } catch (error) {
        alert('加载失败: ' + error.message)
      }
    }
  }
}
</script>

<style scoped>
.worker-module {
  padding: 1rem 0;
}
</style>