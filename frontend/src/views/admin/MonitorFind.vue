<template>
  <div class="page">
    <el-card>
      <template #header><span>查询班长</span></template>
      <el-form :model="searchForm" inline>
        <el-form-item label="学号"><el-input v-model="searchForm.cno" placeholder="学号" clearable /></el-form-item>
        <el-form-item label="年级"><el-input v-model="searchForm.cgrade" placeholder="年级" clearable /></el-form-item>
        <el-form-item label="学院"><el-input v-model="searchForm.cdept" placeholder="学院" clearable /></el-form-item>
        <el-form-item label="专业"><el-input v-model="searchForm.cmajor" placeholder="专业" clearable /></el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="tableData" border stripe>
        <el-table-column prop="cno" label="学号" width="120" />
        <el-table-column prop="cgrade" label="年级" width="100" />
        <el-table-column prop="cdept" label="学院" width="150" />
        <el-table-column prop="cmajor" label="专业" width="150" />
        <el-table-column prop="cnum" label="班级人数" width="100" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { searchMonitors } from '@/api'

const searchForm = ref({ cno: '', cgrade: '', cdept: '', cmajor: '' })
const tableData = ref([])

async function handleSearch() {
  try {
    const res = await searchMonitors(searchForm.value)
    tableData.value = res.data || []
  } catch (e) {}
}

function resetSearch() {
  searchForm.value = { cno: '', cgrade: '', cdept: '', cmajor: '' }
  tableData.value = []
}
</script>
