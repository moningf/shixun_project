<template>
  <div class="login-page">
    <!-- 左侧装饰区 -->
    <div class="login-left">
      <div class="left-content">
        <div class="brand-icon">📚</div>
        <h1>教材征订与发放管理系统</h1>
        <p class="subtitle">Textbook Ordering & Distribution System</p>
        <div class="feature-list">
          <div class="feature-item">
            <span class="dot"></span>教材管理与查询
          </div>
          <div class="feature-item">
            <span class="dot"></span>在线预订与发放
          </div>
          <div class="feature-item">
            <span class="dot"></span>多种角色协同工作
          </div>
        </div>
      </div>
      <div class="left-bg-shape"></div>
    </div>

    <!-- 右侧登录区 -->
    <div class="login-right">
      <div class="login-card">
        <div class="card-header">
          <h2>欢迎登录</h2>
          <p>请输入您的账号和密码</p>
        </div>

        <el-form ref="formRef" :model="form" :rules="rules" size="large" @keyup.enter="handleLogin">
          <el-form-item prop="username">
            <el-input
              v-model="form.username"
              placeholder="请输入用户名"
              :prefix-icon="User"
              clearable
            />
          </el-form-item>

          <el-form-item prop="password">
            <el-input
              v-model="form.password"
              type="password"
              placeholder="请输入密码"
              :prefix-icon="Lock"
              show-password
            />
          </el-form-item>

          <el-form-item>
            <el-button
              type="primary"
              :loading="loading"
              class="login-btn"
              @click="handleLogin"
            >
              {{ loading ? '验证中...' : '登 录' }}
            </el-button>
          </el-form-item>
        </el-form>

        <div class="card-footer">
          <span>支持管理员 / 教师 / 班级 三种身份登录</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, shallowRef } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { User, Lock } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const router = useRouter()
const authStore = useAuthStore()

const formRef = ref()
const loading = ref(false)

const form = reactive({
  username: '',
  password: ''
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

async function handleLogin() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return

  loading.value = true
  try {
    const result = await authStore.login(form.username, form.password)
    if (result.success) {
      ElMessage.success('登录成功')
      const role = authStore.role
      if (role === 'manager') router.push('/admin/dashboard')
      else if (role === 'teacher') router.push('/teacher/dashboard')
      else if (role === 'monitor') router.push('/monitor/dashboard')
    } else {
      ElMessage.error(result.message || '登录失败')
    }
  } catch (e) {
    ElMessage.error('登录失败，请重试')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-page {
  display: flex;
  min-height: 100vh;
  overflow: hidden;
}

/* ===== 左侧装饰区 ===== */
.login-left {
  flex: 1;
  background: linear-gradient(160deg, #2b3a67 0%, #1a2340 40%, #0f1626 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  overflow: hidden;
}

.left-bg-shape {
  position: absolute;
  top: -50%;
  right: -20%;
  width: 600px;
  height: 600px;
  border-radius: 50%;
  background: radial-gradient(circle, rgba(64, 158, 255, 0.08) 0%, transparent 70%);
  pointer-events: none;
}

.left-content {
  position: relative;
  z-index: 1;
  text-align: center;
  color: #fff;
  padding: 40px;
  max-width: 440px;
}

.brand-icon {
  font-size: 64px;
  margin-bottom: 24px;
  filter: drop-shadow(0 4px 12px rgba(0,0,0,0.3));
}

.left-content h1 {
  font-size: 28px;
  font-weight: 700;
  margin: 0 0 12px 0;
  letter-spacing: 2px;
  color: #fff;
}

.left-content .subtitle {
  font-size: 13px;
  color: rgba(255,255,255,0.45);
  margin: 0 0 48px 0;
  letter-spacing: 1px;
  text-transform: uppercase;
}

.feature-list {
  text-align: left;
  display: inline-flex;
  flex-direction: column;
  gap: 16px;
}

.feature-item {
  font-size: 15px;
  color: rgba(255,255,255,0.7);
  display: flex;
  align-items: center;
  gap: 10px;
}

.feature-item .dot {
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background: #409EFF;
  flex-shrink: 0;
}

/* ===== 右侧登录区 ===== */
.login-right {
  flex: 0 0 520px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f8f9fc;
  padding: 40px;
}

.login-card {
  width: 400px;
  background: #fff;
  border-radius: 16px;
  padding: 48px 40px 36px;
  box-shadow: 0 4px 24px rgba(0, 0, 0, 0.06), 0 1px 4px rgba(0, 0, 0, 0.04);
}

.card-header {
  text-align: center;
  margin-bottom: 36px;
}

.card-header h2 {
  font-size: 24px;
  font-weight: 700;
  color: #1a1a2e;
  margin: 0 0 8px 0;
}

.card-header p {
  font-size: 14px;
  color: #909399;
  margin: 0;
}

.login-btn {
  width: 100%;
  height: 46px;
  font-size: 16px;
  letter-spacing: 4px;
  border-radius: 8px;
}

.card-footer {
  text-align: center;
  margin-top: 20px;
  font-size: 12px;
  color: #c0c4cc;
}

/* ===== 响应式 ===== */
@media (max-width: 768px) {
  .login-left { display: none; }
  .login-right { flex: 1; padding: 20px; }
  .login-card { width: 100%; padding: 32px 24px; }
}
</style>
