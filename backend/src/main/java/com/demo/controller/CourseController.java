package com.demo.controller;

import com.demo.context.UserContext;
import com.demo.dto.ApiResponse;
import com.demo.entity.Course;
import com.demo.entity.Teacher;
import com.demo.service.ICourseService;
import com.demo.service.ITeacherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final ICourseService courseService;
    private final ITeacherService teacherService;

    public CourseController(ICourseService courseService, ITeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }

    @GetMapping
    public ApiResponse<List<Course>> getAll() {
        return ApiResponse.success(courseService.findAll());
    }

    @GetMapping("/{cno}")
    public ApiResponse<Course> getById(@PathVariable String cno) {
        Course course = courseService.findByCno(cno);
        if (course != null) {
            return ApiResponse.success(course);
        }
        return ApiResponse.error(404, "课程不存在");
    }

    /** 管理员查某教师的课程 */
    @GetMapping("/teacher/{tno}")
    public ApiResponse<Course> getByTeacher(@PathVariable String tno) {
        Teacher teacher = teacherService.findById(tno);
        if (teacher == null || teacher.getCcno() == null) {
            return ApiResponse.error(404, "该教师未关联课程");
        }
        Course course = courseService.findByCno(teacher.getCcno());
        if (course != null) {
            return ApiResponse.success(course);
        }
        return ApiResponse.error(404, "课程不存在");
    }

    // ==================== 教师自助接口 ====================

    /** 教师查自己教的课程 */
    @GetMapping("/my")
    public ApiResponse<Course> getMyCourse() {
        Teacher teacher = teacherService.findById(UserContext.getUserId());
        if (teacher == null || teacher.getCcno() == null) {
            return ApiResponse.error(404, "该教师未关联课程");
        }
        Course course = courseService.findByCno(teacher.getCcno());
        if (course != null) {
            return ApiResponse.success(course);
        }
        return ApiResponse.error(404, "课程不存在");
    }
}
