import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { login as loginApi } from '@/api'

export const useAuthStore = defineStore('auth', () => {
  const token = ref(localStorage.getItem('token') || '')
  const user = ref(JSON.parse(localStorage.getItem('user') || 'null'))
  const role = ref(localStorage.getItem('role') || '')

  const isLoggedIn = computed(() => !!token.value)
  const isAdmin = computed(() => role.value === 'manager')
  const isTeacher = computed(() => role.value === 'teacher')
  const isMonitor = computed(() => role.value === 'monitor')

  async function login(username, password) {
    const res = await loginApi({ username, password })
    if (res.code === 200) {
      token.value = res.data.token
      user.value = res.data.user
      role.value = res.data.role
      localStorage.setItem('token', res.data.token)
      localStorage.setItem('user', JSON.stringify(res.data.user))
      localStorage.setItem('role', res.data.role)
      return { success: true }
    }
    return { success: false, message: res.message }
  }

  function logout() {
    token.value = ''
    user.value = null
    role.value = ''
    localStorage.removeItem('token')
    localStorage.removeItem('user')
    localStorage.removeItem('role')
  }

  function updateUser(newUser) {
    user.value = { ...user.value, ...newUser }
    localStorage.setItem('user', JSON.stringify(user.value))
  }

  return { token, user, role, isLoggedIn, isAdmin, isTeacher, isMonitor, login, logout, updateUser }
})
