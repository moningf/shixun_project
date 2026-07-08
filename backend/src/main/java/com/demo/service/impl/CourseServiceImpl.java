package com.demo.service.impl;

import com.demo.entity.Course;
import com.demo.repository.CourseRepository;
import com.demo.service.ICourseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements ICourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public Course findByCno(String cno) {
        return courseRepository.findById(cno).orElse(null);
    }
}
