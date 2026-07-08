<template>
  <el-container class="layout-container">
    <el-aside :width="isCollapse ? '64px' : '220px'" class="sidebar">
      <div class="logo" @click="goHome">
        <span v-if="!isCollapse">📚 教材管理系统</span>
        <span v-else>📚</span>
      </div>
      <el-menu
        :default-active="activeMenu"
        :collapse="isCollapse"
        :collapse-transition="false"
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409EFF"
        router
      >
        <template v-for="item in menuItems" :key="item.path">
          <el-sub-menu v-if="item.children" :index="item.path">
            <template #title>
              <el-icon><component :is="item.icon" /></el-icon>
              <span>{{ item.title }}</span>
            </template>
            <el-menu-item v-for="child in item.children" :key="child.path" :index="child.path">
              <el-icon><component :is="child.icon" /></el-icon>
              <span>{{ child.title }}</span>
            </el-menu-item>
          </el-sub-menu>
          <el-menu-item v-else :index="item.path">
            <el-icon><component :is="item.icon" /></el-icon>
            <span>{{ item.title }}</span>
          </el-menu-item>
        </template>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header class="header">
        <div class="header-left">
          <el-button :icon="isCollapse ? 'Expand' : 'Fold'" text @click="isCollapse = !isCollapse" />
          <el-breadcrumb separator="/" style="margin-left: 16px">
            <el-breadcrumb-item :to="{ path: basePath + '/dashboard' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item v-if="currentTitle">{{ currentTitle }}</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="header-right">
          <el-dropdown @command="handleCommand">
            <span class="user-info">
              <el-icon><User /></el-icon>
              {{ userName }}
              <el-icon><ArrowDown /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">个人信息</el-dropdown-item>
                <el-dropdown-item command="password">修改密码</el-dropdown-item>
                <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>

      <el-main class="main-content">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, computed, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const route = useRoute()
const authStore = useAuthStore()

const isCollapse = ref(false)

const basePath = computed(() => {
  if (authStore.isAdmin) return '/admin'
  if (authStore.isTeacher) return '/teacher'
  if (authStore.isMonitor) return '/monitor'
  return ''
})

const userName = computed(() => {
  const user = authStore.user
  if (!user) return ''
  return user.mname || user.tname || user.cno || ''
})

const currentTitle = computed(() => route.meta.title || '')

const activeMenu = computed(() => route.path)

// Admin menu
const adminMenu = [
  { path: '/admin/dashboard', title: '工作台', icon: 'HomeFilled' },
  { path: '/admin/teachers', title: '教师管理', icon: 'User' },
  { path: '/admin/monitors', title: '班长管理', icon: 'UserFilled',
    children: [
      { path: '/admin/monitors', title: '班长列表', icon: 'List' },
      { path: '/admin/monitors/find', title: '查询班长', icon: 'Search' },
    ]
  },
  { path: '/admin/books', title: '教材管理', icon: 'Reading',
    children: [
      { path: '/admin/books', title: '教材列表', icon: 'List' },
      { path: '/admin/books/find', title: '查询教材', icon: 'Search' },
    ]
  },
  { path: '/admin/reserves', title: '订单管理', icon: 'Tickets',
    children: [
      { path: '/admin/reserves', title: '全部订单', icon: 'List' },
      { path: '/admin/reserves/undeal', title: '未处理订单', icon: 'Warning' },
    ]
  },
  { path: '/admin/purchases', title: '采购计划', icon: 'ShoppingCart' },
]

// Teacher menu
const teacherMenu = [
  { path: '/teacher/dashboard', title: '工作台', icon: 'HomeFilled' },
  { path: '/teacher/books', title: '教材管理', icon: 'Reading',
    children: [
      { path: '/teacher/books', title: '我的教材', icon: 'List' },
      { path: '/teacher/books/all', title: '全部教材', icon: 'View' },
      { path: '/teacher/books/find', title: '查询教材', icon: 'Search' },
    ]
  },
  { path: '/teacher/courses', title: '我的课程', icon: 'Collection' },
]

// Monitor menu
const monitorMenu = [
  { path: '/monitor/dashboard', title: '工作台', icon: 'HomeFilled' },
  { path: '/monitor/books', title: '教材浏览', icon: 'Reading' },
  { path: '/monitor/books/find', title: '查询教材', icon: 'Search' },
  { path: '/monitor/reserves', title: '我的订单', icon: 'Tickets' },
]

const menuItems = computed(() => {
  if (authStore.isAdmin) return adminMenu
  if (authStore.isTeacher) return teacherMenu
  if (authStore.isMonitor) return monitorMenu
  return []
})

function goHome() {
  router.push(basePath.value + '/dashboard')
}

function handleCommand(command) {
  if (command === 'profile') {
    router.push(basePath.value + '/profile')
  } else if (command === 'password') {
    router.push(basePath.value + '/password')
  } else if (command === 'logout') {
    authStore.logout()
    router.push('/login')
  }
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}
.sidebar {
  background-color: #304156;
  overflow-y: auto;
  overflow-x: hidden;
}
.sidebar .el-menu {
  border-right: none;
}
.logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
  background: rgba(0, 0, 0, 0.15);
  white-space: nowrap;
  overflow: hidden;
}
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: #fff;
  border-bottom: 1px solid #e6e6e6;
  padding: 0 20px;
  height: 60px;
}
.header-left {
  display: flex;
  align-items: center;
}
.header-right {
  display: flex;
  align-items: center;
}
.user-info {
  display: flex;
  align-items: center;
  gap: 6px;
  cursor: pointer;
  color: #333;
}
.main-content {
  background: #f0f2f5;
  padding: 20px;
  overflow-y: auto;
}
</style>
