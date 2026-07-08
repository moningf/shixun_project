<template>
  <div class="page">
    <el-card>
      <template #header><span>教材管理</span></template>
      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column prop="bno" label="书号" width="100" />
        <el-table-column prop="bname" label="书名" min-width="150" />
        <el-table-column prop="bauthor" label="作者" width="120" />
        <el-table-column prop="bsource" label="出版社" width="150" />
        <el-table-column prop="bedition" label="版次" width="80" />
        <el-table-column prop="bprice" label="单价" width="80" />
        <el-table-column prop="bnum" label="库存" width="80" />
        <el-table-column label="操作" fixed="right" width="200">
          <template #default="{ row }">
            <el-button size="small" type="primary" @click="showStockDialog(row)">库存</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row.bno)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog title="修改库存" v-model="dialogVisible" width="400px">
      <el-form :model="stockForm">
        <el-form-item label="书号"><el-input v-model="stockForm.bno" disabled /></el-form-item>
        <el-form-item label="书名"><el-input v-model="stockForm.bname" disabled /></el-form-item>
        <el-form-item label="库存数量">
          <el-input-number v-model="stockForm.bnum" :min="0" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="submitting" @click="handleStockUpdate">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getBooks, updateBookStock, deleteBook } from '@/api'
import { ElMessage, ElMessageBox } from 'element-plus'

const tableData = ref([])
const loading = ref(false)
const dialogVisible = ref(false)
const submitting = ref(false)
const stockForm = reactive({ bno: '', bname: '', bnum: 0 })

async function loadData() {
  loading.value = true
  try { const res = await getBooks(); tableData.value = res.data || [] } finally { loading.value = false }
}

function showStockDialog(row) {
  stockForm.bno = row.bno
  stockForm.bname = row.bname
  stockForm.bnum = parseInt(row.bnum) || 0
  dialogVisible.value = true
}

async function handleStockUpdate() {
  submitting.value = true
  try {
    await updateBookStock(stockForm.bno, String(stockForm.bnum))
    ElMessage.success('库存更新成功')
    dialogVisible.value = false
    loadData()
  } catch (e) { ElMessage.error('更新失败') }
  finally { submitting.value = false }
}

async function handleDelete(bno) {
  try {
    await ElMessageBox.confirm('确定删除该教材吗？', '提示', { type: 'warning' })
    await deleteBook(bno)
    ElMessage.success('删除成功')
    loadData()
  } catch (e) {}
}

onMounted(loadData)
</script>
