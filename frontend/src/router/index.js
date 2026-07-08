import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/shared/Login.vue'),
    meta: { title: '登录' }
  },
  // ===== Admin routes =====
  {
    path: '/admin',
    component: () => import('@/views/shared/AppLayout.vue'),
    meta: { role: 'manager' },
    redirect: '/admin/dashboard',
    children: [
      { path: 'dashboard', name: 'AdminDashboard', component: () => import('@/views/admin/Dashboard.vue'), meta: { title: '管理员首页' } },
      { path: 'teachers', name: 'TeacherManagement', component: () => import('@/views/admin/TeacherManagement.vue'), meta: { title: '教师管理' } },
      { path: 'monitors', name: 'MonitorManagement', component: () => import('@/views/admin/MonitorManagement.vue'), meta: { title: '班级管理' } },
      { path: 'monitors/find', name: 'MonitorFind', component: () => import('@/views/admin/MonitorFind.vue'), meta: { title: '查询班级' } },
      { path: 'books', name: 'AdminBookManagement', component: () => import('@/views/admin/BookManagement.vue'), meta: { title: '教材管理' } },
      { path: 'books/find', name: 'AdminBookFind', component: () => import('@/views/admin/BookFind.vue'), meta: { title: '查询教材' } },
      { path: 'reserves', name: 'AdminReserves', component: () => import('@/views/admin/AllReserves.vue'), meta: { title: '全部订单' } },
      { path: 'reserves/undeal', name: 'AdminUnDealReserves', component: () => import('@/views/admin/UnDealReserves.vue'), meta: { title: '未处理订单' } },
      { path: 'purchases', name: 'Purchases', component: () => import('@/views/admin/Purchases.vue'), meta: { title: '采购计划' } },
      { path: 'profile', name: 'AdminProfile', component: () => import('@/views/shared/Profile.vue'), meta: { title: '个人信息' } },
      { path: 'password', name: 'AdminPassword', component: () => import('@/views/shared/ModifyPwd.vue'), meta: { title: '修改密码' } },
    ]
  },
  // ===== Teacher routes =====
  {
    path: '/teacher',
    component: () => import('@/views/shared/AppLayout.vue'),
    meta: { role: 'teacher' },
    redirect: '/teacher/dashboard',
    children: [
      { path: 'dashboard', name: 'TeacherDashboard', component: () => import('@/views/teacher/Dashboard.vue'), meta: { title: '教师首页' } },
      { path: 'books', name: 'TeacherBookManagement', component: () => import('@/views/teacher/BookManagement.vue'), meta: { title: '我的教材' } },
      { path: 'books/all', name: 'TeacherAllBooks', component: () => import('@/views/teacher/AllBooks.vue'), meta: { title: '全部教材' } },
      { path: 'books/find', name: 'TeacherBookFind', component: () => import('@/views/teacher/BookFind.vue'), meta: { title: '查询教材' } },
      { path: 'courses', name: 'TeacherCourses', component: () => import('@/views/teacher/Courses.vue'), meta: { title: '我的课程' } },
      { path: 'profile', name: 'TeacherProfile', component: () => import('@/views/shared/Profile.vue'), meta: { title: '个人信息' } },
      { path: 'password', name: 'TeacherPassword', component: () => import('@/views/shared/ModifyPwd.vue'), meta: { title: '修改密码' } },
    ]
  },
  // ===== Monitor routes =====
  {
    path: '/monitor',
    component: () => import('@/views/shared/AppLayout.vue'),
    meta: { role: 'monitor' },
    redirect: '/monitor/dashboard',
    children: [
      { path: 'dashboard', name: 'MonitorDashboard', component: () => import('@/views/monitor/Dashboard.vue'), meta: { title: '班级首页' } },
      { path: 'books', name: 'MonitorBooks', component: () => import('@/views/monitor/BookReserve.vue'), meta: { title: '教材浏览' } },
      { path: 'books/find', name: 'MonitorBookFind', component: () => import('@/views/monitor/BookFind.vue'), meta: { title: '查询教材' } },
      { path: 'reserves', name: 'MonitorReserves', component: () => import('@/views/monitor/MyReserves.vue'), meta: { title: '我的订单' } },
      { path: 'profile', name: 'MonitorProfile', component: () => import('@/views/shared/Profile.vue'), meta: { title: '个人信息' } },
      { path: 'password', name: 'MonitorPassword', component: () => import('@/views/shared/ModifyPwd.vue'), meta: { title: '修改密码' } },
    ]
  },
  { path: '/', redirect: '/login' },
  { path: '/:pathMatch(.*)*', redirect: '/login' }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// Route guard
router.beforeEach((to, from, next) => {
  document.title = to.meta.title ? `${to.meta.title} - 教材管理系统` : '教材征订与发放管理系统'

  if (to.path === '/login') {
    next()
    return
  }

  const token = localStorage.getItem('token')
  const role = localStorage.getItem('role')

  if (!token) {
    next('/login')
    return
  }

  const requiredRole = to.matched.find(r => r.meta.role)?.meta.role
  if (requiredRole && requiredRole !== role) {
    // Redirect to correct dashboard
    if (role === 'manager') next('/admin/dashboard')
    else if (role === 'teacher') next('/teacher/dashboard')
    else if (role === 'monitor') next('/monitor/dashboard')
    else next('/login')
    return
  }

  next()
})

export default router
