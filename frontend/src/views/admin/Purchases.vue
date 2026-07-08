<template>
  <div class="page">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>采购计划</span>
          <el-button type="primary" @click="showAddDialog">添加采购</el-button>
        </div>
      </template>
      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column prop="bno" label="书号" width="130" />
        <el-table-column prop="bname" label="书名" min-width="150" />
        <el-table-column prop="bauthor" label="作者" width="120" />
        <el-table-column prop="bsource" label="出版社" width="150" />
        <el-table-column prop="bedition" label="版次" width="80" />
        <el-table-column prop="bprice" label="单价" width="80" />
        <el-table-column prop="number" label="采购数量" width="100" />
        <el-table-column label="操作" fixed="right" width="220">
          <template #default="{ row }">
            <el-button size="small" type="success" @click="handleConfirm(row)">确认入库</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row.bno)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 添加采购 Dialog -->
    <el-dialog title="添加采购计划" v-model="dialogVisible" width="500px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="书号" prop="bno"><el-input v-model="form.bno" /></el-form-item>
        <el-form-item label="书名" prop="bname"><el-input v-model="form.bname" /></el-form-item>
        <el-form-item label="作者" prop="bauthor"><el-input v-model="form.bauthor" /></el-form-item>
        <el-form-item label="出版社" prop="bsource"><el-input v-model="form.bsource" /></el-form-item>
        <el-form-item label="版次"><el-input v-model="form.bedition" /></el-form-item>
        <el-form-item label="单价"><el-input v-model="form.bprice" /></el-form-item>
        <el-form-item label="采购数量" prop="number">
          <el-input-number v-model="form.number" :min="1" style="width: 100%" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="submitting" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getPurchases, addPurchase, deletePurchase, confirmPurchase } from '@/api'
import { ElMessage, ElMessageBox } from 'element-plus'

const tableData = ref([])
const loading = ref(false)
const dialogVisible = ref(false)
const submitting = ref(false)
const formRef = ref()

const form = reactive({
  bno: '', bname: '', bauthor: '', bsource: '', bedition: '', bprice: '', number: 1
})

const rules = {
  bno: [{ required: true, message: '请输入书号', trigger: 'blur' }],
  bname: [{ required: true, message: '请输入书名', trigger: 'blur' }],
  bauthor: [{ required: true, message: '请输入作者', trigger: 'blur' }],
  bsource: [{ required: true, message: '请输入出版社', trigger: 'blur' }],
  number: [{ required: true, message: '请输入采购数量', trigger: 'blur' }],
}

async function loadData() {
  loading.value = true
  try { const res = await getPurchases(); tableData.value = res.data || [] } finally { loading.value = false }
}

function showAddDialog() {
  Object.keys(form).forEach(k => form[k] = k === 'number' ? 1 : '')
  dialogVisible.value = true
}

async function handleSubmit() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  submitting.value = true
  try {
    await addPurchase({ ...form })
    ElMessage.success('添加成功')
    dialogVisible.value = false
    loadData()
  } catch (e) { ElMessage.error('添加失败') }
  finally { submitting.value = false }
}

/** 确认入库：先存到 book 表再删采购记录 */
async function handleConfirm(row) {
  try {
    await ElMessageBox.confirm(
      `确认将《${row.bname}》入库 ${row.number} 本吗？入库后教材库存会增加。`,
      '确认入库',
      { type: 'success' }
    )
    await confirmPurchase(row.bno)
    ElMessage.success('入库成功，教材库存已更新')
    loadData()
  } catch (e) { /* 取消不做任何事 */ }
}

async function handleDelete(bno) {
  try {
    await ElMessageBox.confirm('确定删除该采购记录吗？', '提示', { type: 'warning' })
    await deletePurchase(bno)
    ElMessage.success('删除成功')
    loadData()
  } catch (e) {}
}

onMounted(loadData)
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
</style>
