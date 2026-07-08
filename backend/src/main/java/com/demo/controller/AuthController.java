package com.demo.controller;

import com.demo.context.UserContext;
import com.demo.dto.ApiResponse;
import com.demo.dto.LoginRequest;
import com.demo.dto.LoginResponse;
import com.demo.service.IAuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse result = authService.login(request);
        if (result.isSuccess()) {
            return ApiResponse.success(result.getMessage(), result);
        }
        return ApiResponse.error(401, result.getMessage());
    }
}
