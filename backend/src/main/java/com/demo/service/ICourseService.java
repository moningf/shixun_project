package com.demo.service;

import com.demo.entity.Course;
import java.util.List;

public interface ICourseService {
    List<Course> findAll();
    Course findByCno(String cno);
}
