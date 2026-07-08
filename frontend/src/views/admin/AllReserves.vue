<template>
  <div class="page">
    <el-card>
      <template #header><span>全部订单</span></template>
      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column prop="id" label="订单号" width="80" />
        <el-table-column prop="cno" label="班级编号" width="120" />
        <el-table-column prop="bno" label="书号" width="100" />
        <el-table-column label="书名" min-width="150">
          <template #default="{ row }">{{ row.book?.bname || '' }}</template>
        </el-table-column>
        <el-table-column prop="num" label="数量" width="60" />
        <el-table-column prop="totalPrice" label="总价" width="80" />
        <el-table-column prop="rTime" label="预订日期" width="110" />
        <el-table-column prop="qTime" label="发放日期" width="110" />
        <el-table-column prop="bstatus" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.bstatus === '已处理' ? 'success' : 'warning'">{{ row.bstatus }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="place" label="发放地点" width="150" />
        <el-table-column label="操作" fixed="right" width="120" v-if="false">
          <template #default="{ row }">
            <el-button v-if="row.bstatus !== '已处理'" size="small" type="primary" @click="$emit('deal', row)">处理</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getReserves } from '@/api'

const tableData = ref([])
const loading = ref(false)

onMounted(async () => {
  loading.value = true
  try { const res = await getReserves(); tableData.value = res.data || [] } finally { loading.value = false }
})
</script>
