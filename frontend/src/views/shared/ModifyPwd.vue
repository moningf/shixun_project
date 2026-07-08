<template>
  <div class="pwd-container">
    <el-card>
      <template #header><h2>修改密码</h2></template>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px" style="max-width: 500px">
        <el-form-item label="新密码" prop="password">
          <el-input v-model="form.password" type="password" show-password placeholder="请输入新密码" />
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="form.confirmPassword" type="password" show-password placeholder="请再次输入新密码" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="loading" @click="handleSave">保存修改</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { reactive, ref, computed } from 'vue'
import { useAuthStore } from '@/stores/auth'
import { modifyAdminPwd, modifyTeacherMyPwd, modifyMonitorMyPwd } from '@/api'
import { ElMessage } from 'element-plus'

const authStore = useAuthStore()
const loading = ref(false)
const formRef = ref()

const form = reactive({
  password: '',
  confirmPassword: ''
})

const validateConfirm = (rule, value, callback) => {
  if (value !== form.password) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

const rules = {
  password: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 3, message: '密码至少3位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认新密码', trigger: 'blur' },
    { validator: validateConfirm, trigger: 'blur' }
  ]
}

async function handleSave() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return

  loading.value = true
  try {
    if (authStore.isAdmin) {
      await modifyAdminPwd(form.password)
    } else if (authStore.isTeacher) {
      await modifyTeacherMyPwd(form.password)
    } else if (authStore.isMonitor) {
      await modifyMonitorMyPwd(form.password)
    }
    ElMessage.success('密码修改成功，请重新登录')
    authStore.logout()
    setTimeout(() => window.location.href = '/login', 1000)
  } catch (e) {
    ElMessage.error('修改失败')
  } finally {
    loading.value = false
  }
}
</script>
