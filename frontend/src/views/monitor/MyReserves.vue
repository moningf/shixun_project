<template>
  <div class="page">
    <el-card>
      <template #header><span>我的订单</span></template>
      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column prop="id" label="订单号" width="80" />
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
        <el-table-column label="操作" fixed="right" width="180">
          <template #default="{ row }">
            <el-button size="small" type="primary" @click="showEditDialog(row)">修改数量</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog title="修改数量" v-model="dialogVisible" width="400px">
      <el-form :model="editForm">
        <el-form-item label="新数量">
          <el-input-number v-model="editForm.rnum" :min="1" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="submitting" @click="handleUpdate">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getMyReserves, updateMyReserveNum, deleteReserve } from '@/api'
import { ElMessage, ElMessageBox } from 'element-plus'

const tableData = ref([])
const loading = ref(false)
const dialogVisible = ref(false)
const submitting = ref(false)
const editForm = reactive({ id: '', rnum: 0 })

async function loadData() {
  loading.value = true
  try {
    const res = await getMyReserves()
    tableData.value = res.data || []
  } finally { loading.value = false }
}

function showEditDialog(row) {
  editForm.id = row.id
  editForm.rnum = row.num
  dialogVisible.value = true
}

async function handleUpdate() {
  submitting.value = true
  try {
    await updateMyReserveNum(editForm.id, editForm.rnum)
    ElMessage.success('修改成功')
    dialogVisible.value = false
    loadData()
  } catch (e) { ElMessage.error('修改失败') }
  finally { submitting.value = false }
}

async function handleDelete(id) {
  try {
    await ElMessageBox.confirm('确定删除该订单吗？', '提示', { type: 'warning' })
    await deleteReserve(id)
    ElMessage.success('删除成功')
    loadData()
  } catch (e) {}
}

onMounted(loadData)
</script>
