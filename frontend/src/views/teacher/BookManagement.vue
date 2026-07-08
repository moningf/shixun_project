<template>
  <div class="page">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>我的教材</span>
          <el-button type="primary" @click="showAddDialog">添加教材</el-button>
        </div>
      </template>
      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column prop="bno" label="书号" width="100" />
        <el-table-column prop="bname" label="书名" min-width="150" />
        <el-table-column prop="bauthor" label="作者" width="120" />
        <el-table-column prop="bsource" label="出版社" width="150" />
        <el-table-column prop="bedition" label="版次" width="80" />
        <el-table-column prop="bprice" label="单价" width="80" />
        <el-table-column label="课程" width="120">
          <template #default="{ row }">{{ row.course?.cname || row.ccno }}</template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="180">
          <template #default="{ row }">
            <el-button size="small" type="primary" @click="showEditDialog(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row.bno)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- Add/Edit Dialog -->
    <el-dialog :title="isEdit ? '编辑教材' : '添加教材'" v-model="dialogVisible" width="500px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="书号" prop="bno">
          <el-input v-model="form.bno" :disabled="isEdit" />
        </el-form-item>
        <el-form-item label="书名" prop="bname"><el-input v-model="form.bname" /></el-form-item>
        <el-form-item label="作者" prop="bauthor"><el-input v-model="form.bauthor" /></el-form-item>
        <el-form-item label="出版社" prop="bsource"><el-input v-model="form.bsource" /></el-form-item>
        <el-form-item label="版次"><el-input v-model="form.bedition" /></el-form-item>
        <el-form-item label="单价"><el-input v-model="form.bprice" /></el-form-item>
        <el-form-item label="课程号" prop="ccno">
          <el-select v-model="form.ccno" placeholder="选择课程" style="width: 100%">
            <el-option v-for="c in courses" :key="c.cno" :label="c.cname" :value="c.cno" />
          </el-select>
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
import { getMyBooks, addBook, updateBook, deleteBook, getCourses } from '@/api'
import { ElMessage, ElMessageBox } from 'element-plus'
const tableData = ref([])
const courses = ref([])
const loading = ref(false)
const dialogVisible = ref(false)
const isEdit = ref(false)
const submitting = ref(false)
const formRef = ref()

const form = reactive({
  bno: '', bname: '', bauthor: '', bsource: '', bedition: '', bprice: '', ccno: ''
})

const rules = {
  bno: [{ required: true, message: '请输入书号', trigger: 'blur' }],
  bname: [{ required: true, message: '请输入书名', trigger: 'blur' }],
  bauthor: [{ required: true, message: '请输入作者', trigger: 'blur' }],
  bsource: [{ required: true, message: '请输入出版社', trigger: 'blur' }],
  ccno: [{ required: true, message: '请选择课程', trigger: 'change' }],
}

async function loadData() {
  loading.value = true
  try {
    const res = await getMyBooks()
    tableData.value = res.data || []
  } finally { loading.value = false }
}

async function loadCourses() {
  try {
    const res = await getCourses()
    courses.value = res.data || []
  } catch (e) {}
}

function showAddDialog() {
  isEdit.value = false
  Object.keys(form).forEach(k => form[k] = '')
  dialogVisible.value = true
}

function showEditDialog(row) {
  isEdit.value = true
  form.bno = row.bno
  form.bname = row.bname
  form.bauthor = row.bauthor
  form.bsource = row.bsource
  form.bedition = row.bedition
  form.bprice = row.bprice
  form.ccno = row.ccno
  dialogVisible.value = true
}

async function handleSubmit() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  submitting.value = true
  try {
    if (isEdit.value) {
      await updateBook(form.bno, { bname: form.bname, bauthor: form.bauthor, bsource: form.bsource, bedition: form.bedition, bprice: form.bprice, ccno: form.ccno })
      ElMessage.success('修改成功')
    } else {
      await addBook({ ...form })
      ElMessage.success('添加成功')
    }
    dialogVisible.value = false
    loadData()
  } catch (e) { ElMessage.error('操作失败') }
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

onMounted(() => { loadData(); loadCourses() })
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
</style>
