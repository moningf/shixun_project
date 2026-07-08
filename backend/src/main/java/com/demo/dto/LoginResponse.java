package com.demo.dto;

public class LoginResponse {
    private boolean success;
    private String message;
    private String token;
    private String role;
    private Object user;

    public LoginResponse() {}

    public LoginResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public LoginResponse(boolean success, String message, String token, String role, Object user) {
        this.success = success;
        this.message = message;
        this.token = token;
        this.role = role;
        this.user = user;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public Object getUser() { return user; }
    public void setUser(Object user) { this.user = user; }
}
