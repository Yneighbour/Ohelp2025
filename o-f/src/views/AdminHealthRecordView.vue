<script setup>
import { computed, onMounted, ref } from 'vue';

import AdminLayout from '../components/admin/AdminLayout.vue';
import * as healthApi from '../api/health';
import { adminHealthRecordsData as demoRecords } from '../data/admin';

const loading = ref(false);
const error = ref('');
const keyword = ref('');
const typeFilter = ref('all');
const statusFilter = ref('all');

const rows = ref([]);

// 新增/编辑对话框状态
const dialogVisible = ref(false);
const dialogMode = ref('add'); // 'add' | 'edit'
const dialogForm = ref({
  id: null,
  elderlyId: null,
  recordDate: '',
  bloodPressure: '',
  heartRate: null,
  temperature: null,
  weight: null,
  glucoseLevel: null,
  notes: '',
  doctorId: null,
  isActive: true,
});

function formatDate(value) {
  if (!value) return '';
  const s = String(value);
  return s.length >= 19 ? s.slice(0, 19).replace('T', ' ') : (s.length >= 10 ? s.slice(0, 10) : s);
}

function guessRecordType(r) {
  if (r?.bloodPressure) return '血压';
  if (r?.heartRate != null) return '心率';
  if (r?.glucoseLevel != null) return '血糖';
  if (r?.temperature != null) return '体温';
  return '健康记录';
}

function guessValue(r) {
  if (r?.bloodPressure) return `${r.bloodPressure} mmHg`;
  if (r?.heartRate != null) return `${r.heartRate} bpm`;
  if (r?.glucoseLevel != null) return `${r.glucoseLevel} mmol/L`;
  if (r?.temperature != null) return `${r.temperature} ℃`;
  return '--';
}

function mapStatus(r) {
  // 后端并未提供状态，这里用演示逻辑：只要有值就 normal
  return { status: 'normal', statusText: '正常' };
}

function mapRecord(r) {
  const s = mapStatus(r);
  return {
    id: r?.id ?? '-',
    elderName: r?.elderName || (r?.elderlyId != null ? `老人#${r.elderlyId}` : '-'),
    recordType: guessRecordType(r),
    value: guessValue(r),
    status: s.status,
    statusText: s.statusText,
    recordTime: formatDate(r?.recordDate || r?.createdAt || r?.recordTime),
    operator: r?.operator || (r?.doctorId != null ? `医生#${r.doctorId}` : '--'),
  };
}

const filteredRows = computed(() => {
  const list = Array.isArray(rows.value) ? rows.value : [];
  const key = keyword.value.trim();
  return list.filter((r) => {
    if (typeFilter.value !== 'all' && String(r.recordType) !== typeFilter.value) return false;
    if (statusFilter.value !== 'all' && r.status !== statusFilter.value) return false;
    if (!key) return true;
    return String(r.elderName).includes(key);
  });
});

async function load() {
  loading.value = true;
  error.value = '';
  try {
    const data = await healthApi.listAllHealthRecords();
    if (Array.isArray(data) && data.length) {
      rows.value = data.map(mapRecord);
      return;
    }
    rows.value = demoRecords.slice();
  } catch (e) {
    error.value = '后端接口不可用，已切换为演示数据';
    rows.value = demoRecords.slice();
  } finally {
    loading.value = false;
  }
}

function onSearch() {
  // 搜索功能已通过computed filteredRows实现，这里保留空函数
}

function onAdd() {
  dialogMode.value = 'add';
  dialogForm.value = {
    id: null,
    elderlyId: null,
    recordDate: new Date().toISOString().slice(0, 10),
    bloodPressure: '',
    heartRate: null,
    temperature: null,
    weight: null,
    glucoseLevel: null,
    notes: '',
    doctorId: null,
    isActive: true,
  };
  dialogVisible.value = true;
}

function onView(row) {
  window.alert(`查看${row.elderName}的${row.recordType}记录详情 (ID: ${row.id})\n\n（详情页面待开发）`);
}

function onEdit(row) {
  dialogMode.value = 'edit';
  loading.value = true;
  healthApi.getHealthRecordById(row.id)
    .then(data => {
      dialogForm.value = {
        id: data.id,
        elderlyId: data.elderlyId || null,
        recordDate: data.recordDate || new Date().toISOString().slice(0, 10),
        bloodPressure: data.bloodPressure || '',
        heartRate: data.heartRate || null,
        temperature: data.temperature || null,
        weight: data.weight || null,
        glucoseLevel: data.glucoseLevel || null,
        notes: data.notes || '',
        doctorId: data.doctorId || null,
        isActive: data.isActive !== false,
      };
      dialogVisible.value = true;
    })
    .catch(e => {
      console.error(e);
      window.alert('获取健康记录信息失败');
    })
    .finally(() => {
      loading.value = false;
    });
}

function onDelete(row) {
  const ok = window.confirm('确定要删除该记录吗？\n\n此操作不可恢复！');
  if (!ok) return;
  loading.value = true;
  error.value = '';
  healthApi.deleteHealthRecord(row.id)
    .then(() => {
      window.alert('删除成功');
      return load();
    })
    .catch(e => {
      console.error(e);
      window.alert('删除失败，请稍后重试');
    })
    .finally(() => {
      loading.value = false;
    });
}

function closeDialog() {
  dialogVisible.value = false;
}

async function saveDialog() {
  const form = dialogForm.value;
  if (!form.elderlyId) {
    window.alert('请输入老人ID');
    return;
  }

  loading.value = true;
  error.value = '';
  try {
    if (dialogMode.value === 'add') {
      await healthApi.createHealthRecord(form);
      window.alert('添加成功');
    } else {
      await healthApi.updateHealthRecord(form.id, form);
      window.alert('更新成功');
    }
    dialogVisible.value = false;
    await load();
  } catch (e) {
    console.error(e);
    window.alert(`保存失败: ${e.message || '请稍后重试'}`);
  } finally {
    loading.value = false;
  }
}

onMounted(load);
</script>

<template>
  <AdminLayout active-page="admin-health-record" title="健康记录">
    <div class="admin-toolbar">
      <div class="admin-search-row">
        <input v-model="keyword" type="text" class="admin-search-input" placeholder="搜索老人姓名" />
        <button class="admin-search-btn" type="button" @click="onSearch">搜索</button>
      </div>
      <div class="admin-filter-row">
        <select v-model="typeFilter" class="admin-filter-select">
          <option value="all">全部类型</option>
          <option value="血压">血压</option>
          <option value="心率">心率</option>
          <option value="血糖">血糖</option>
          <option value="体温">体温</option>
        </select>
        <select v-model="statusFilter" class="admin-filter-select">
          <option value="all">全部状态</option>
          <option value="normal">正常</option>
          <option value="warning">偏高/偏低</option>
          <option value="danger">异常</option>
        </select>
        <button class="admin-add-btn" type="button" @click="onAdd">+ 添加记录</button>
      </div>
    </div>

    <div v-if="loading" style="color: var(--text-secondary); padding: 6px 0">正在加载...</div>
    <div v-else-if="error" style="color: var(--warning-color); padding: 6px 0">{{ error }}</div>

    <div class="admin-table-container">
      <table class="admin-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>老人姓名</th>
            <th>记录类型</th>
            <th>测量值</th>
            <th>状态</th>
            <th>记录时间</th>
            <th>操作人</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="r in filteredRows" :key="r.id">
            <td>{{ r.id }}</td>
            <td>{{ r.elderName }}</td>
            <td><span class="category-tag health">{{ r.recordType }}</span></td>
            <td>{{ r.value }}</td>
            <td><span class="status-tag" :class="r.status">{{ r.statusText }}</span></td>
            <td>{{ r.recordTime }}</td>
            <td>{{ r.operator }}</td>
            <td>
              <div class="admin-actions">
                <button class="admin-action-btn view" type="button" @click="onView(r)">详情</button>
                <button class="admin-action-btn edit" type="button" @click="onEdit(r)">编辑</button>
                <button class="admin-action-btn delete" type="button" @click="onDelete(r)">删除</button>
              </div>
            </td>
          </tr>
          <tr v-if="!filteredRows.length">
            <td colspan="8" style="text-align: center; color: var(--text-secondary); padding: 16px 0">暂无数据</td>
          </tr>
        </tbody>
      </table>
    </div>

    <div class="admin-pagination">
      <span>共 {{ filteredRows.length }} 条记录</span>
      <span>第 1/1 页</span>
    </div>

    <!-- 新增/编辑对话框 -->
    <div v-if="dialogVisible" class="admin-dialog-overlay" @click.self="closeDialog">
      <div class="admin-dialog">
        <div class="admin-dialog-header">
          <h3>{{ dialogMode === 'add' ? '添加健康记录' : '编辑健康记录' }}</h3>
          <button class="admin-dialog-close" @click="closeDialog">×</button>
        </div>
        <div class="admin-dialog-body">
          <div class="admin-form-group">
            <label>老人ID <span style="color: red">*</span></label>
            <input v-model.number="dialogForm.elderlyId" type="number" placeholder="请输入老人ID" />
          </div>
          <div class="admin-form-group">
            <label>记录日期 <span style="color: red">*</span></label>
            <input v-model="dialogForm.recordDate" type="date" />
          </div>
          <div class="admin-form-group">
            <label>血压 (mmHg)</label>
            <input v-model="dialogForm.bloodPressure" type="text" placeholder="例如：120/80" />
          </div>
          <div class="admin-form-group">
            <label>心率 (bpm)</label>
            <input v-model.number="dialogForm.heartRate" type="number" placeholder="请输入心率" />
          </div>
          <div class="admin-form-group">
            <label>体温 (℃)</label>
            <input v-model.number="dialogForm.temperature" type="number" step="0.1" placeholder="请输入体温" />
          </div>
          <div class="admin-form-group">
            <label>体重 (kg)</label>
            <input v-model.number="dialogForm.weight" type="number" step="0.1" placeholder="请输入体重" />
          </div>
          <div class="admin-form-group">
            <label>血糖 (mmol/L)</label>
            <input v-model.number="dialogForm.glucoseLevel" type="number" step="0.1" placeholder="请输入血糖值" />
          </div>
          <div class="admin-form-group">
            <label>备注</label>
            <textarea v-model="dialogForm.notes" rows="3" placeholder="请输入备注信息"></textarea>
          </div>
          <div class="admin-form-group">
            <label>医生ID</label>
            <input v-model.number="dialogForm.doctorId" type="number" placeholder="请输入医生ID（可选）" />
          </div>
          <div class="admin-form-group">
            <label>
              <input v-model="dialogForm.isActive" type="checkbox" />
              启用状态
            </label>
          </div>
        </div>
        <div class="admin-dialog-footer">
          <button class="admin-dialog-btn cancel" @click="closeDialog">取消</button>
          <button class="admin-dialog-btn confirm" @click="saveDialog">{{ dialogMode === 'add' ? '确认添加' : '确认修改' }}</button>
        </div>
      </div>
    </div>
  </AdminLayout>
</template>
