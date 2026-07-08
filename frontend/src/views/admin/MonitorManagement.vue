<template>
  <div class="page">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>班级管理</span>
          <el-button type="primary" @click="showAddDialog">添加班级</el-button>
        </div>
      </template>
      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column prop="cno" label="班级编号" width="120" />
        <el-table-column prop="cgrade" label="年级" width="100" />
        <el-table-column prop="cdept" label="学院" width="150" />
        <el-table-column prop="cmajor" label="专业" width="150" />
        <el-table-column prop="cnum" label="班级人数" width="100" />
        <el-table-column label="操作" fixed="right" width="180">
          <template #default="{ row }">
            <el-button size="small" type="primary" @click="showEditDialog(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row.cno)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog :title="isEdit ? '编辑班级' : '添加班级'" v-model="dialogVisible" width="500px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="班级编号" prop="cno">
          <el-input v-model="form.cno" :disabled="isEdit" />
        </el-form-item>
        <el-form-item label="年级" prop="cgrade"><el-input v-model="form.cgrade" /></el-form-item>
        <el-form-item label="学院" prop="cdept"><el-input v-model="form.cdept" /></el-form-item>
        <el-form-item label="专业" prop="cmajor"><el-input v-model="form.cmajor" /></el-form-item>
        <el-form-item label="班级人数"><el-input v-model="form.cnum" /></el-form-item>
        <el-form-item v-if="!isEdit" label="密码" prop="password">
          <el-input v-model="form.password" type="password" show-password />
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
import { getMonitors, addMonitor, updateMonitor, deleteMonitor } from '@/api'
import { ElMessage, ElMessageBox } from 'element-plus'

const tableData = ref([])
const loading = ref(false)
const dialogVisible = ref(false)
const isEdit = ref(false)
const submitting = ref(false)
const formRef = ref()

const form = reactive({
  cno: '', cgrade: '', cdept: '', cmajor: '', cnum: '', password: ''
})

const rules = {
  cno: [{ required: true, message: '请输入班级编号', trigger: 'blur' }],
  cgrade: [{ required: true, message: '请输入年级', trigger: 'blur' }],
  cdept: [{ required: true, message: '请输入学院', trigger: 'blur' }],
  cmajor: [{ required: true, message: '请输入专业', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
}

async function loadData() {
  loading.value = true
  try {
    const res = await getMonitors()
    tableData.value = res.data || []
  } finally { loading.value = false }
}

function showAddDialog() {
  isEdit.value = false
  Object.keys(form).forEach(k => form[k] = '')
  dialogVisible.value = true
}

function showEditDialog(row) {
  isEdit.value = true
  Object.assign(form, { cno: row.cno, cgrade: row.cgrade, cdept: row.cdept, cmajor: row.cmajor, cnum: row.cnum, password: '' })
  dialogVisible.value = true
}

async function handleSubmit() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  submitting.value = true
  try {
    if (isEdit.value) {
      await updateMonitor(form.cno, { cgrade: form.cgrade, cdept: form.cdept, cmajor: form.cmajor, cnum: form.cnum })
      ElMessage.success('修改成功')
    } else {
      await addMonitor({ ...form })
      ElMessage.success('添加成功')
    }
    dialogVisible.value = false
    loadData()
  } catch (e) {
    ElMessage.error('操作失败')
  } finally { submitting.value = false }
}

async function handleDelete(cno) {
  try {
    await ElMessageBox.confirm('确定删除该班级吗？', '提示', { type: 'warning' })
    await deleteMonitor(cno)
    ElMessage.success('删除成功')
    loadData()
  } catch (e) {}
}

onMounted(loadData)
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
</style>
