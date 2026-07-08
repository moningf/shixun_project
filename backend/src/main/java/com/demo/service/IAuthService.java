package com.demo.service;

import com.demo.dto.LoginRequest;
import com.demo.dto.LoginResponse;

public interface IAuthService {
    LoginResponse login(LoginRequest request);
}
