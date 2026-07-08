package com.demo.service;

import com.demo.entity.Teacher;
import java.util.List;

public interface ITeacherService {
    List<Teacher> findAll();
    Teacher findById(String tno);
    Teacher add(Teacher teacher);
    boolean delete(String tno);
    boolean update(Teacher teacher);
    boolean modifyPassword(String id, String newPassword);
    boolean modifyInfo(String id, String tname, String tdept, String ttel, String temail, String ccno);
    List<Teacher> search(Teacher teacher);
    List<Teacher> findByDept(String dept);
    List<Teacher> findByName(String name);
    List<Teacher> findByCcno(String ccno);
}
