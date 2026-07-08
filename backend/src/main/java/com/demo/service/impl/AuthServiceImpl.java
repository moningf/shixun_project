package com.demo.service.impl;

import com.demo.config.JwtUtil;
import com.demo.dto.LoginRequest;
import com.demo.dto.LoginResponse;
import com.demo.entity.Admin;
import com.demo.entity.Monitor;
import com.demo.entity.Teacher;
import com.demo.repository.AdminRepository;
import com.demo.repository.MonitorRepository;
import com.demo.repository.TeacherRepository;
import com.demo.service.IAuthService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthServiceImpl implements IAuthService {

    private final AdminRepository adminRepository;
    private final TeacherRepository teacherRepository;
    private final MonitorRepository monitorRepository;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(AdminRepository adminRepository,
                           TeacherRepository teacherRepository,
                           MonitorRepository monitorRepository,
                           JwtUtil jwtUtil) {
        this.adminRepository = adminRepository;
        this.teacherRepository = teacherRepository;
        this.monitorRepository = monitorRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        // 1. 尝试管理员
        Optional<Admin> adminOpt = adminRepository.findById(username);
        if (adminOpt.isPresent() && adminOpt.get().getPassword().equals(password)) {
            Admin admin = adminOpt.get();
            String token = jwtUtil.generateToken(username, "manager");
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("mno", admin.getMno());
            userMap.put("mname", admin.getMname());
            userMap.put("mtel", admin.getMtel());
            userMap.put("memail", admin.getMemail());
            return new LoginResponse(true, "登录成功", token, "manager", userMap);
        }

        // 2. 尝试教师
        Optional<Teacher> teacherOpt = teacherRepository.findById(username);
        if (teacherOpt.isPresent() && teacherOpt.get().getPassword().equals(password)) {
            Teacher teacher = teacherOpt.get();
            String token = jwtUtil.generateToken(username, "teacher");
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("tno", teacher.getTno());
            userMap.put("tname", teacher.getTname());
            userMap.put("tdept", teacher.getTdept());
            userMap.put("ttel", teacher.getTtel());
            userMap.put("temail", teacher.getTemail());
            userMap.put("ccno", teacher.getCcno());
            return new LoginResponse(true, "登录成功", token, "teacher", userMap);
        }

        // 3. 尝试班级
        Optional<Monitor> monitorOpt = monitorRepository.findById(username);
        if (monitorOpt.isPresent() && monitorOpt.get().getPassword().equals(password)) {
            Monitor monitor = monitorOpt.get();
            String token = jwtUtil.generateToken(username, "monitor");
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("cno", monitor.getCno());
            userMap.put("cgrade", monitor.getCgrade());
            userMap.put("cdept", monitor.getCdept());
            userMap.put("cmajor", monitor.getCmajor());
            userMap.put("cnum", monitor.getCnum());
            return new LoginResponse(true, "登录成功", token, "monitor", userMap);
        }

        if (adminOpt.isPresent() || teacherOpt.isPresent() || monitorOpt.isPresent()) {
            return new LoginResponse(false, "密码错误");
        }
        return new LoginResponse(false, "用户不存在");
    }
}
