<template>
  <div class="profile-container">
    <el-card>
      <template #header><h2>个人信息</h2></template>
      <el-form :model="form" label-width="100px" style="max-width: 500px">
        <template v-if="isAdmin">
          <el-form-item label="管理员编号"><el-input v-model="form.mno" disabled /></el-form-item>
          <el-form-item label="姓名"><el-input v-model="form.mname" /></el-form-item>
          <el-form-item label="电话"><el-input v-model="form.mtel" /></el-form-item>
          <el-form-item label="邮箱"><el-input v-model="form.memail" /></el-form-item>
        </template>
        <template v-if="isTeacher">
          <el-form-item label="教师编号"><el-input v-model="form.tno" disabled /></el-form-item>
          <el-form-item label="姓名"><el-input v-model="form.tname" /></el-form-item>
          <el-form-item label="学院"><el-input v-model="form.tdept" /></el-form-item>
          <el-form-item label="电话"><el-input v-model="form.ttel" /></el-form-item>
          <el-form-item label="邮箱"><el-input v-model="form.temail" /></el-form-item>
          <el-form-item label="课程号"><el-input v-model="form.ccno" /></el-form-item>
        </template>
        <template v-if="isMonitor">
          <el-form-item label="学号"><el-input v-model="form.cno" disabled /></el-form-item>
          <el-form-item label="年级"><el-input v-model="form.cgrade" /></el-form-item>
          <el-form-item label="学院"><el-input v-model="form.cdept" /></el-form-item>
          <el-form-item label="专业"><el-input v-model="form.cmajor" /></el-form-item>
        </template>
        <el-form-item>
          <el-button type="primary" :loading="loading" @click="handleSave">保存修改</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { reactive, computed, ref } from 'vue'
import { useAuthStore } from '@/stores/auth'
import { modifyAdminInfo, modifyTeacherMyInfo, modifyMonitorMyInfo } from '@/api'
import { ElMessage } from 'element-plus'

const authStore = useAuthStore()
const loading = ref(false)

const isAdmin = computed(() => authStore.isAdmin)
const isTeacher = computed(() => authStore.isTeacher)
const isMonitor = computed(() => authStore.isMonitor)

const user = authStore.user || {}

const form = reactive({ ...user })

async function handleSave() {
  loading.value = true
  try {
    if (isAdmin.value) {
      await modifyAdminInfo({ mname: form.mname, mtel: form.mtel, memail: form.memail })
    } else if (isTeacher.value) {
      await modifyTeacherMyInfo({ tname: form.tname, tdept: form.tdept, ttel: form.ttel, temail: form.temail, ccno: form.ccno })
    } else if (isMonitor.value) {
      await modifyMonitorMyInfo({ cgrade: form.cgrade, cdept: form.cdept, cmajor: form.cmajor })
    }
    authStore.updateUser(form)
    ElMessage.success('保存成功')
  } catch (e) {
    ElMessage.error('保存失败')
  } finally {
    loading.value = false
  }
}
</script>
