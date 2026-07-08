<template>
  <div class="page">
    <el-card>
      <template #header><span>查询教材</span></template>
      <el-form :model="searchForm" inline>
        <el-form-item label="书号"><el-input v-model="searchForm.bno" clearable /></el-form-item>
        <el-form-item label="书名"><el-input v-model="searchForm.bname" clearable /></el-form-item>
        <el-form-item label="教师编号"><el-input v-model="searchForm.tno" clearable /></el-form-item>
        <el-form-item label="课程号"><el-input v-model="searchForm.ccno" clearable /></el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="tableData" border stripe>
        <el-table-column prop="bno" label="书号" width="100" />
        <el-table-column prop="bname" label="书名" min-width="150" />
        <el-table-column prop="bauthor" label="作者" width="120" />
        <el-table-column prop="bsource" label="出版社" width="150" />
        <el-table-column prop="bprice" label="单价" width="80" />
        <el-table-column prop="bnum" label="库存" width="80" />
        <el-table-column label="教师" width="120">
          <template #default="{ row }">{{ row.teacher?.tname || '' }}</template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { searchBooks } from '@/api'

const searchForm = ref({ bno: '', bname: '', tno: '', ccno: '' })
const tableData = ref([])

async function handleSearch() {
  try { const res = await searchBooks(searchForm.value); tableData.value = res.data || [] } catch (e) {}
}
function resetSearch() {
  searchForm.value = { bno: '', bname: '', tno: '', ccno: '' }
  tableData.value = []
}
</script>
