<template>
  <div class="page">
    <el-card>
      <template #header><span>我的课程</span></template>
      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column prop="cno" label="课程号" width="120" />
        <el-table-column prop="cname" label="课程名" min-width="200" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getMyCourse } from '@/api'

const tableData = ref([])
const loading = ref(false)

onMounted(async () => {
  loading.value = true
  try {
    const res = await getMyCourse()
    if (res.data) tableData.value = [res.data]
  } finally { loading.value = false }
})
</script>
