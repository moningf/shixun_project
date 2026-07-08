package com.demo.controller;

import com.demo.context.UserContext;
import com.demo.dto.ApiResponse;
import com.demo.entity.Admin;
import com.demo.service.IAdminService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final IAdminService adminService;

    public AdminController(IAdminService adminService) {
        this.adminService = adminService;
    }

    /** 获取当前登录管理员的信息 */
    @GetMapping("/me")
    public ApiResponse<Admin> getMe() {
        Admin admin = adminService.findById(UserContext.getUserId());
        if (admin != null) {
            admin.setPassword(null);
            return ApiResponse.success(admin);
        }
        return ApiResponse.error(404, "管理员不存在");
    }

    /** 修改当前登录管理员的密码 */
    @PutMapping("/password")
    public ApiResponse<String> modifyPassword(@RequestBody java.util.Map<String, String> params) {
        if (adminService.modifyPassword(UserContext.getUserId(), params.get("password"))) {
            return ApiResponse.success("密码修改成功");
        }
        return ApiResponse.error(400, "密码修改失败");
    }

    /** 修改当前登录管理员的信息 */
    @PutMapping("/info")
    public ApiResponse<Admin> modifyInfo(@RequestBody Admin admin) {
        String userId = UserContext.getUserId();
        if (adminService.modifyInfo(userId, admin.getMname(), admin.getMtel(), admin.getMemail())) {
            Admin updated = adminService.findById(userId);
            updated.setPassword(null);
            return ApiResponse.success("信息修改成功", updated);
        }
        return ApiResponse.error(400, "信息修改失败");
    }
}
