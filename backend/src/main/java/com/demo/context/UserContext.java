package com.demo.context;

/**
 * 用户上下文 —— 基于 ThreadLocal，拦截器在 preHandle 注入，afterCompletion 清除。
 * 整个请求链路中任意位置调用 UserContext.getUserId() / getRole() 即可获取当前用户。
 */
public class UserContext {

    private static final ThreadLocal<String> USER_ID = new ThreadLocal<>();
    private static final ThreadLocal<String> ROLE = new ThreadLocal<>();

    public static void set(String userId, String role) {
        USER_ID.set(userId);
        ROLE.set(role);
    }

    public static String getUserId() {
        return USER_ID.get();
    }

    public static String getRole() {
        return ROLE.get();
    }

    /**
     * 判断当前用户是否为管理员
     */
    public static boolean isManager() {
        return "manager".equals(getRole());
    }

    /**
     * 判断当前用户是否为教师
     */
    public static boolean isTeacher() {
        return "teacher".equals(getRole());
    }

    /**
     * 判断当前用户是否为班级
     */
    public static boolean isMonitor() {
        return "monitor".equals(getRole());
    }

    /**
     * 必须由拦截器的 afterCompletion 调用，防止内存泄漏
     */
    public static void clear() {
        USER_ID.remove();
        ROLE.remove();
    }
}
