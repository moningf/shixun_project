package com.demo.service;

import com.demo.entity.Monitor;
import java.util.List;

public interface IMonitorService {
    List<Monitor> findAll();
    Monitor findById(String cno);
    Monitor add(Monitor monitor);
    boolean delete(String cno);
    boolean update(Monitor monitor);
    boolean modifyPassword(String id, String newPassword);
    boolean modifyInfo(String id, String cgrade, String cdept, String cmajor);
    List<Monitor> search(Monitor monitor);
    List<Monitor> findByDept(String dept);
    List<Monitor> findByGrade(String grade);
}
