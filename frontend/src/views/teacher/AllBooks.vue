<template>
  <div class="page">
    <el-card>
      <template #header><span>全部教材</span></template>
      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column prop="bno" label="书号" width="100" />
        <el-table-column prop="bname" label="书名" min-width="150" />
        <el-table-column prop="bauthor" label="作者" width="120" />
        <el-table-column prop="bsource" label="出版社" width="150" />
        <el-table-column prop="bedition" label="版次" width="80" />
        <el-table-column prop="bprice" label="单价" width="80" />
        <el-table-column prop="bnum" label="库存" width="80" />
        <el-table-column label="课程" width="120">
          <template #default="{ row }">{{ row.course?.cname || row.ccno }}</template>
        </el-table-column>
        <el-table-column label="教师" width="120">
          <template #default="{ row }">{{ row.teacher?.tname || row.tno }}</template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getBooks } from '@/api'

const tableData = ref([])
const loading = ref(false)

onMounted(async () => {
  loading.value = true
  try { const res = await getBooks(); tableData.value = res.data || [] } finally { loading.value = false }
})
</script>
