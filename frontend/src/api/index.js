import request from '@/utils/request'

// ========== Auth ==========
export const login = (data) => request.post('/auth/login', data)

// ========== Admin（自助接口，id 从 UserContext 自动获取） ==========
export const getAdminMe = () => request.get('/admin/me')
export const modifyAdminPwd = (password) => request.put('/admin/password', { password })
export const modifyAdminInfo = (data) => request.put('/admin/info', data)

// ========== Teacher（管理员） ==========
export const getTeachers = (params) => request.get('/teachers', { params })
export const getTeacher = (id) => request.get(`/teachers/${id}`)
export const addTeacher = (data) => request.post('/teachers', data)
export const updateTeacher = (id, data) => request.put(`/teachers/${id}`, data)
export const deleteTeacher = (id) => request.delete(`/teachers/${id}`)
export const searchTeachers = (params) => request.get('/teachers/search', { params })

// ========== Teacher（自助接口） ==========
export const getTeacherMe = () => request.get('/teachers/me')
export const modifyTeacherMyPwd = (password) => request.put('/teachers/me/password', { password })
export const modifyTeacherMyInfo = (data) => request.put('/teachers/me/info', data)

// ========== Monitor（管理员） ==========
export const getMonitors = () => request.get('/monitors')
export const getMonitor = (id) => request.get(`/monitors/${id}`)
export const addMonitor = (data) => request.post('/monitors', data)
export const updateMonitor = (id, data) => request.put(`/monitors/${id}`, data)
export const deleteMonitor = (id) => request.delete(`/monitors/${id}`)
export const searchMonitors = (params) => request.get('/monitors/search', { params })

// ========== Monitor（自助接口） ==========
export const getMonitorMe = () => request.get('/monitors/me')
export const modifyMonitorMyPwd = (password) => request.put('/monitors/me/password', { password })
export const modifyMonitorMyInfo = (data) => request.put('/monitors/me/info', data)

// ========== Book ==========
export const getBooks = () => request.get('/books')
export const getBook = (bno) => request.get(`/books/${bno}`)
export const getBooksByTeacher = (tno) => request.get(`/books/by-teacher/${tno}`)
export const getMyBooks = () => request.get('/books/my')
export const addBook = (data) => request.post('/books', data)
export const updateBook = (bno, data) => request.put(`/books/${bno}`, data)
export const updateBookStock = (bno, bnum) => request.put(`/books/${bno}/stock`, { bnum })
export const deleteBook = (bno) => request.delete(`/books/${bno}`)
export const searchBooks = (params) => request.get('/books/search', { params })

// ========== Course ==========
export const getCourses = () => request.get('/courses')
export const getCourse = (cno) => request.get(`/courses/${cno}`)
export const getCourseByTeacher = (tno) => request.get(`/courses/teacher/${tno}`)
export const getMyCourse = () => request.get('/courses/my')

// ========== Reserve ==========
export const getReserves = () => request.get('/reserves')
export const getUnDealReserves = () => request.get('/reserves/undeal')
export const getReserve = (id) => request.get(`/reserves/${id}`)
export const getReservesByMonitor = (cno) => request.get(`/reserves/by-monitor/${cno}`)
export const getMyReserves = () => request.get('/reserves/my')
export const addReserve = (data) => request.post('/reserves', data)
export const updateReserve = (id, data) => request.put(`/reserves/${id}`, data)
export const updateMyReserveNum = (id, rnum) => request.put(`/reserves/my/${id}/num`, { rnum })
export const deleteReserve = (id) => request.delete(`/reserves/${id}`)
export const dealReserve = (id, data) => request.put(`/reserves/${id}/deal`, data)

// ========== Purchase ==========
export const getPurchases = () => request.get('/purchases')
export const addPurchase = (data) => request.post('/purchases', data)
export const deletePurchase = (bno) => request.delete(`/purchases/${bno}`)
export const confirmPurchase = (bno) => request.put(`/purchases/${bno}/confirm`)
