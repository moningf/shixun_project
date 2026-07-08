<template>
  <div class="page">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>教师管理</span>
          <el-button type="primary" @click="showAddDialog">添加教师</el-button>
        </div>
      </template>

      <!-- 搜索栏 -->
      <el-form :model="searchForm" inline class="search-bar">
        <el-form-item label="教师编号"><el-input v-model="searchForm.tno" placeholder="教师编号" clearable /></el-form-item>
        <el-form-item label="姓名"><el-input v-model="searchForm.tname" placeholder="姓名" clearable /></el-form-item>
        <el-form-item label="学院"><el-input v-model="searchForm.tdept" placeholder="学院" clearable /></el-form-item>
        <el-form-item label="课程号"><el-input v-model="searchForm.ccno" placeholder="课程号" clearable /></el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column prop="tno" label="教师编号" width="100" />
        <el-table-column prop="tname" label="姓名" width="100" />
        <el-table-column prop="tdept" label="学院" width="150" />
        <el-table-column prop="ttel" label="电话" width="130" />
        <el-table-column prop="temail" label="邮箱" width="180" />
        <el-table-column prop="ccno" label="课程号" width="100" />
        <el-table-column label="操作" fixed="right" width="180">
          <template #default="{ row }">
            <el-button size="small" type="primary" @click="showEditDialog(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row.tno)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- Add/Edit Dialog -->
    <el-dialog :title="isEdit ? '编辑教师' : '添加教师'" v-model="dialogVisible" width="500px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="教师编号" prop="tno">
          <el-input v-model="form.tno" :disabled="isEdit" />
        </el-form-item>
        <el-form-item label="姓名" prop="tname"><el-input v-model="form.tname" /></el-form-item>
        <el-form-item label="学院" prop="tdept"><el-input v-model="form.tdept" /></el-form-item>
        <el-form-item label="电话" prop="ttel"><el-input v-model="form.ttel" /></el-form-item>
        <el-form-item label="邮箱" prop="temail"><el-input v-model="form.temail" /></el-form-item>
        <el-form-item label="课程号" prop="ccno"><el-input v-model="form.ccno" /></el-form-item>
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
import { getTeachers, searchTeachers, addTeacher, updateTeacher, deleteTeacher } from '@/api'
import { ElMessage, ElMessageBox } from 'element-plus'

const tableData = ref([])
const loading = ref(false)
const searchForm = ref({ tno: '', tname: '', tdept: '', ccno: '' })
const isSearching = ref(false)
const dialogVisible = ref(false)
const isEdit = ref(false)
const submitting = ref(false)
const formRef = ref()

const form = reactive({
  tno: '', tname: '', tdept: '', ttel: '', temail: '', ccno: '', password: ''
})

const rules = {
  tno: [{ required: true, message: '请输入教师编号', trigger: 'blur' }],
  tname: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  tdept: [{ required: true, message: '请输入学院', trigger: 'blur' }],
  ttel: [{ required: true, message: '请输入电话', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
}

async function loadData() {
  loading.value = true
  try {
    const res = await getTeachers()
    tableData.value = res.data || []
    isSearching.value = false
  } finally {
    loading.value = false
  }
}

async function handleSearch() {
  const params = searchForm.value
  if (!params.tno && !params.tname && !params.tdept && !params.ccno) {
    loadData()
    return
  }
  loading.value = true
  try {
    const res = await searchTeachers(params)
    tableData.value = res.data || []
    isSearching.value = true
  } finally { loading.value = false }
}

function resetSearch() {
  searchForm.value = { tno: '', tname: '', tdept: '', ccno: '' }
  loadData()
}

function showAddDialog() {
  isEdit.value = false
  Object.keys(form).forEach(k => form[k] = '')
  dialogVisible.value = true
}

function showEditDialog(row) {
  isEdit.value = true
  form.tno = row.tno
  form.tname = row.tname
  form.tdept = row.tdept
  form.ttel = row.ttel
  form.temail = row.temail
  form.ccno = row.ccno
  form.password = ''
  dialogVisible.value = true
}

async function handleSubmit() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  submitting.value = true
  try {
    if (isEdit.value) {
      await updateTeacher(form.tno, {
        tname: form.tname, tdept: form.tdept, ttel: form.ttel, temail: form.temail, ccno: form.ccno
      })
      ElMessage.success('修改成功')
    } else {
      await addTeacher({ ...form })
      ElMessage.success('添加成功')
    }
    dialogVisible.value = false
    refreshData()
  } catch (e) {
    ElMessage.error('操作失败')
  } finally {
    submitting.value = false
  }
}

function refreshData() {
  if (isSearching.value) handleSearch()
  else loadData()
}

async function handleDelete(tno) {
  try {
    await ElMessageBox.confirm('确定删除该教师吗？', '提示', { type: 'warning' })
    await deleteTeacher(tno)
    ElMessage.success('删除成功')
    refreshData()
  } catch (e) {}
}

onMounted(loadData)
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
.search-bar { margin-bottom: 16px; padding: 12px 16px 0; background: #fafafa; border-radius: 6px; }
</style>
