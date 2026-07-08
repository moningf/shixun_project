<template>
  <div class="page">
    <el-card>
      <template #header><span>未处理订单</span></template>
      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column prop="id" label="订单号" width="80" />
        <el-table-column prop="cno" label="学号" width="120" />
        <el-table-column prop="bno" label="书号" width="100" />
        <el-table-column label="书名" min-width="150">
          <template #default="{ row }">{{ row.book?.bname || '' }}</template>
        </el-table-column>
        <el-table-column prop="num" label="数量" width="60" />
        <el-table-column prop="totalPrice" label="总价" width="80" />
        <el-table-column prop="rTime" label="预订日期" width="110" />
        <el-table-column prop="bstatus" label="状态" width="100">
          <template #default="{ row }">
            <el-tag type="warning">{{ row.bstatus }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="100">
          <template #default="{ row }">
            <el-button size="small" type="primary" @click="showDealDialog(row)">处理</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog title="处理订单" v-model="dialogVisible" width="500px">
      <el-descriptions :column="2" border style="margin-bottom: 20px">
        <el-descriptions-item label="订单号">{{ currentOrder.id }}</el-descriptions-item>
        <el-descriptions-item label="学号">{{ currentOrder.cno }}</el-descriptions-item>
        <el-descriptions-item label="书名">{{ currentOrder.book?.bname }}</el-descriptions-item>
        <el-descriptions-item label="数量">{{ currentOrder.num }}</el-descriptions-item>
        <el-descriptions-item label="当前库存">{{ currentOrder.book?.bnum }}</el-descriptions-item>
        <el-descriptions-item label="总价">{{ currentOrder.totalPrice }}</el-descriptions-item>
      </el-descriptions>
      <el-form :model="dealForm" label-width="100px">
        <el-form-item label="发放地点">
          <el-input v-model="dealForm.place" placeholder="请输入发放地点" />
        </el-form-item>
        <el-form-item label="发放日期">
          <el-date-picker v-model="dealForm.qTime" type="date" placeholder="选择日期" value-format="YYYY-MM-DD" style="width: 100%" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="submitting" @click="handleDeal">确认处理</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getUnDealReserves, dealReserve } from '@/api'
import { ElMessage } from 'element-plus'

const tableData = ref([])
const loading = ref(false)
const dialogVisible = ref(false)
const submitting = ref(false)
const currentOrder = ref({})
const dealForm = reactive({ place: '', qTime: '' })

async function loadData() {
  loading.value = true
  try { const res = await getUnDealReserves(); tableData.value = res.data || [] } finally { loading.value = false }
}

function showDealDialog(row) {
  currentOrder.value = row
  dealForm.place = ''
  dealForm.qTime = new Date().toISOString().slice(0, 10)
  dialogVisible.value = true
}

async function handleDeal() {
  submitting.value = true
  try {
    await dealReserve(currentOrder.value.id, dealForm)
    ElMessage.success('处理成功')
    dialogVisible.value = false
    loadData()
  } catch (e) {
    ElMessage.error(e.response?.data?.message || '处理失败，可能库存不足')
  } finally { submitting.value = false }
}

onMounted(loadData)
</script>
