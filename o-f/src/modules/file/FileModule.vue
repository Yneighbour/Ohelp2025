<template>
  <div class="file-module">
    <div class="card">
      <h2>文件管理模块</h2>
      <p>文件上传和管理功能</p>
      <button @click="loadFiles" class="btn btn-primary">加载文件记录</button>
      <div v-if="files.length > 0">
        <table class="table">
          <thead>
            <tr>
              <th>ID</th>
              <th>文件名</th>
              <th>文件类型</th>
              <th>文件大小</th>
              <th>上传者</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="file in files" :key="file.id">
              <td>{{ file.id }}</td>
              <td>{{ file.filename }}</td>
              <td>{{ file.fileType }}</td>
              <td>{{ file.fileSize }}</td>
              <td>{{ file.uploaderId }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import { getAllFileRecords } from './file.api.js'

export default {
  name: 'FileModule',
  data() {
    return {
      files: []
    }
  },
  methods: {
    async loadFiles() {
      try {
        const response = await getAllFileRecords()
        this.files = response.data
      } catch (error) {
        alert('加载失败: ' + error.message)
      }
    }
  }
}
</script>

<style scoped>
.file-module {
  padding: 1rem 0;
}
</style>