<template>
  <div class="page">
    <SiteHeader />
    <main class="container">
      <h1>老人档案</h1>
      <p>示例列表：姓名、年龄、房间号、护理等级</p>
      
      <!-- Elder List -->
      <div class="table-container">
        <table class="elder-table">
          <thead>
            <tr>
              <th>老人ID</th>
              <th>姓名</th>
              <th>年龄</th>
              <th>房间号</th>
              <th>护理等级</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="elder in elders" :key="elder.id">
              <td>{{ elder.id }}</td>
              <td>{{ elder.name }}</td>
              <td>{{ elder.age }}</td>
              <td>{{ elder.roomNumber }}</td>
              <td>{{ elder.careLevel }}</td>
              <td>
                <router-link :to="`/elder/${elder.id}`" class="btn btn-small btn-view">详情</router-link>
                <button class="btn btn-small btn-edit">编辑</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </main>
    <SiteFooter />
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import SiteHeader from '../../components/Header.vue'
import SiteFooter from '../../components/Footer.vue'

export default {
  components: { SiteHeader, SiteFooter },
  setup() {
    const elders = ref([
      { id: 1, name: '张三', age: 85, roomNumber: '302', careLevel: '一级护理' },
      { id: 2, name: '李四', age: 78, roomNumber: '205', careLevel: '二级护理' },
      { id: 3, name: '王五', age: 92, roomNumber: '401', careLevel: '特级护理' }
    ])
    
    return {
      elders
    }
  }
}
</script>

<style scoped>
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.table-container {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.elder-table {
  width: 100%;
  border-collapse: collapse;
}

.elder-table th,
.elder-table td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #ebeef5;
}

.elder-table th {
  background-color: #f5f7fa;
  font-weight: bold;
  color: #303133;
}

.elder-table tr:hover {
  background-color: #f5f7fa;
}

/* Button Styles */
.btn {
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  text-decoration: none;
  display: inline-block;
  text-align: center;
  transition: background-color 0.3s;
  margin-right: 5px;
}

.btn-small {
  padding: 4px 8px;
  font-size: 12px;
}

.btn-view {
  background-color: #409eff;
  color: white;
}

.btn-view:hover {
  background-color: #66b1ff;
}

.btn-edit {
  background-color: #67c23a;
  color: white;
}

.btn-edit:hover {
  background-color: #85ce61;
}
</style>
