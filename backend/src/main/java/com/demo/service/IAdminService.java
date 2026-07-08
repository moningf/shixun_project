package com.demo.service;

import com.demo.entity.Admin;

public interface IAdminService {
    Admin findById(String id);
    boolean modifyPassword(String id, String newPassword);
    boolean modifyInfo(String id, String mname, String mtel, String memail);
}
