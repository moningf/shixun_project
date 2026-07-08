<template>
  <div class="page">
    <el-card>
      <template #header><span>教材浏览 & 预订</span></template>
      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column prop="bno" label="书号" width="100" />
        <el-table-column prop="bname" label="书名" min-width="150" />
        <el-table-column prop="bauthor" label="作者" width="120" />
        <el-table-column prop="bsource" label="出版社" width="150" />
        <el-table-column prop="bedition" label="版次" width="80" />
        <el-table-column prop="bprice" label="单价" width="80" />
        <el-table-column prop="bnum" label="库存" width="80" />
        <el-table-column label="教师" width="120">
          <template #default="{ row }">{{ row.teacher?.tname || '' }}</template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="100">
          <template #default="{ row }">
            <el-button size="small" type="primary" :disabled="!row.bnum || row.bnum === '0'" @click="showReserveDialog(row)">
              预订
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog title="预订教材" v-model="dialogVisible" width="400px">
      <el-descriptions :column="1" border style="margin-bottom: 20px">
        <el-descriptions-item label="书号">{{ currentBook.bno }}</el-descriptions-item>
        <el-descriptions-item label="书名">{{ currentBook.bname }}</el-descriptions-item>
        <el-descriptions-item label="单价">{{ currentBook.bprice }}</el-descriptions-item>
        <el-descriptions-item label="库存">{{ currentBook.bnum }}</el-descriptions-item>
      </el-descriptions>
      <el-form :model="reserveForm">
        <el-form-item label="预订数量">
          <el-input-number v-model="reserveForm.num" :min="1" :max="parseInt(currentBook.bnum) || 1" />
        </el-form-item>
      </el-form>
      <div v-if="reserveForm.num && currentBook.bprice" style="text-align: right; margin-top: 10px; font-size: 16px">
        预估总价：<span style="color: #E6A23C; font-weight: bold">¥{{ (reserveForm.num * parseFloat(currentBook.bprice)).toFixed(2) }}</span>
      </div>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="submitting" @click="handleReserve">确认预订</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getBooks, addReserve } from '@/api'
import { ElMessage } from 'element-plus'

const tableData = ref([])
const loading = ref(false)
const dialogVisible = ref(false)
const submitting = ref(false)
const currentBook = ref({})
const reserveForm = reactive({ num: 1 })

async function loadData() {
  loading.value = true
  try { const res = await getBooks(); tableData.value = res.data || [] } finally { loading.value = false }
}

function showReserveDialog(row) {
  currentBook.value = row
  reserveForm.num = 1
  dialogVisible.value = true
}

async function handleReserve() {
  submitting.value = true
  try {
    await addReserve({
      bno: currentBook.value.bno,
      num: reserveForm.num
    })
    ElMessage.success('预订成功')
    dialogVisible.value = false
  } catch (e) { ElMessage.error('预订失败') }
  finally { submitting.value = false }
}

onMounted(loadData)
</script>
