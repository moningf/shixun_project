<template>
  <div class="page">
    <el-card>
      <template #header><span>查询教材</span></template>
      <el-form :model="searchForm" inline>
        <el-form-item label="书号"><el-input v-model="searchForm.bno" clearable /></el-form-item>
        <el-form-item label="书名"><el-input v-model="searchForm.bname" clearable /></el-form-item>
        <el-form-item label="作者"><el-input v-model="searchForm.bauthor" clearable /></el-form-item>
        <el-form-item label="出版社"><el-input v-model="searchForm.bsource" clearable /></el-form-item>
        <el-form-item label="版次"><el-input v-model="searchForm.bedition" clearable /></el-form-item>
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
        <el-table-column prop="bedition" label="版次" width="80" />
        <el-table-column prop="bprice" label="单价" width="80" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { searchBooks } from '@/api'

const searchForm = ref({ bno: '', bname: '', bauthor: '', bsource: '', bedition: '', ccno: '' })
const tableData = ref([])

async function handleSearch() {
  try { const res = await searchBooks(searchForm.value); tableData.value = res.data || [] } catch (e) {}
}
function resetSearch() {
  searchForm.value = { bno: '', bname: '', bauthor: '', bsource: '', bedition: '', ccno: '' }
  tableData.value = []
}
</script>
