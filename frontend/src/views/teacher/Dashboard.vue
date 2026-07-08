<template>
  <div class="dashboard">
    <h2>欢迎回来，{{ userName }}</h2>
    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="8">
        <el-card shadow="hover" class="stat-card" @click="$router.push('/teacher/books')">
          <el-icon :size="40" color="#409EFF"><Reading /></el-icon>
          <div class="stat-num">{{ stats.bookCount }}</div>
          <div class="stat-label">我的教材</div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover" class="stat-card" @click="$router.push('/teacher/courses')">
          <el-icon :size="40" color="#67C23A"><Collection /></el-icon>
          <div class="stat-num">{{ stats.courseCount }}</div>
          <div class="stat-label">课程数</div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover" class="stat-card" @click="$router.push('/teacher/books/all')">
          <el-icon :size="40" color="#E6A23C"><View /></el-icon>
          <div class="stat-num">{{ stats.allBookCount }}</div>
          <div class="stat-label">全部教材</div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { reactive, computed, onMounted } from 'vue'
import { useAuthStore } from '@/stores/auth'
import { getMyBooks, getCourses, getBooks } from '@/api'

const authStore = useAuthStore()
const userName = computed(() => authStore.user?.tname || '教师')

const stats = reactive({ bookCount: 0, courseCount: 0, allBookCount: 0 })

onMounted(async () => {
  try { const res = await getMyBooks(); stats.bookCount = res.data?.length || 0 } catch (e) {}
  try { const res = await getCourses(); stats.courseCount = res.data?.length || 0 } catch (e) {}
  try { const res = await getBooks(); stats.allBookCount = res.data?.length || 0 } catch (e) {}
})
</script>

<style scoped>
.dashboard h2 { margin: 0 0 20px 0; }
.stat-card { text-align: center; cursor: pointer; padding: 20px; }
.stat-num { font-size: 36px; font-weight: bold; margin: 10px 0; color: #333; }
.stat-label { font-size: 14px; color: #999; }
</style>
