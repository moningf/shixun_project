<template>
  <div class="dashboard">
    <h2>欢迎回来，{{ userName }}</h2>
    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="8">
        <el-card shadow="hover" class="stat-card" @click="$router.push('/admin/teachers')">
          <el-icon :size="40" color="#409EFF"><User /></el-icon>
          <div class="stat-num">{{ stats.teacherCount }}</div>
          <div class="stat-label">教师总数</div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover" class="stat-card" @click="$router.push('/admin/monitors')">
          <el-icon :size="40" color="#67C23A"><UserFilled /></el-icon>
          <div class="stat-num">{{ stats.monitorCount }}</div>
          <div class="stat-label">班长总数</div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover" class="stat-card" @click="$router.push('/admin/reserves/undeal')">
          <el-icon :size="40" color="#E6A23C"><Warning /></el-icon>
          <div class="stat-num">{{ stats.undealCount }}</div>
          <div class="stat-label">待处理订单</div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { reactive, computed, onMounted } from 'vue'
import { useAuthStore } from '@/stores/auth'
import { getTeachers, getMonitors, getUnDealReserves } from '@/api'

const authStore = useAuthStore()
const userName = computed(() => authStore.user?.mname || '管理员')

const stats = reactive({
  teacherCount: 0,
  monitorCount: 0,
  undealCount: 0
})

onMounted(async () => {
  try {
    const teachers = await getTeachers()
    stats.teacherCount = teachers.data?.length || 0
  } catch (e) {}
  try {
    const monitors = await getMonitors()
    stats.monitorCount = monitors.data?.length || 0
  } catch (e) {}
  try {
    const undeal = await getUnDealReserves()
    stats.undealCount = undeal.data?.length || 0
  } catch (e) {}
})
</script>

<style scoped>
.dashboard h2 { margin: 0 0 20px 0; }
.stat-card { text-align: center; cursor: pointer; padding: 20px; }
.stat-num { font-size: 36px; font-weight: bold; margin: 10px 0; color: #333; }
.stat-label { font-size: 14px; color: #999; }
</style>
