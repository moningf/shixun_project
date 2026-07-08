package com.demo.service.impl;

import com.demo.entity.Admin;
import com.demo.repository.AdminRepository;
import com.demo.service.IAdminService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements IAdminService {

    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin findById(String id) {
        return adminRepository.findById(id).orElse(null);
    }

    @Override
    public boolean modifyPassword(String id, String newPassword) {
        Optional<Admin> opt = adminRepository.findById(id);
        if (opt.isPresent()) {
            Admin admin = opt.get();
            admin.setPassword(newPassword);
            adminRepository.save(admin);
            return true;
        }
        return false;
    }

    @Override
    public boolean modifyInfo(String id, String mname, String mtel, String memail) {
        Optional<Admin> opt = adminRepository.findById(id);
        if (opt.isPresent()) {
            Admin admin = opt.get();
            if (mname != null) admin.setMname(mname);
            if (mtel != null) admin.setMtel(mtel);
            if (memail != null) admin.setMemail(memail);
            adminRepository.save(admin);
            return true;
        }
        return false;
    }
}
