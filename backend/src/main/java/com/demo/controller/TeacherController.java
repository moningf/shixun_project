package com.demo.controller;

import com.demo.context.UserContext;
import com.demo.dto.ApiResponse;
import com.demo.entity.Teacher;
import com.demo.service.ITeacherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final ITeacherService teacherService;

    public TeacherController(ITeacherService teacherService) {
        this.teacherService = teacherService;
    }

    // ==================== 管理员接口 ====================

    @GetMapping
    public ApiResponse<List<Teacher>> getAll() {
        List<Teacher> list = teacherService.findAll();
        list.forEach(t -> t.setPassword(null));
        return ApiResponse.success(list);
    }

    @GetMapping("/{id}")
    public ApiResponse<Teacher> getById(@PathVariable String id) {
        Teacher t = teacherService.findById(id);
        if (t != null) {
            t.setPassword(null);
            return ApiResponse.success(t);
        }
        return ApiResponse.error(404, "教师不存在");
    }

    @PostMapping
    public ApiResponse<Teacher> add(@RequestBody Teacher teacher) {
        Teacher result = teacherService.add(teacher);
        result.setPassword(null);
        return ApiResponse.success("添加成功", result);
    }

    @PutMapping("/{id}")
    public ApiResponse<String> updateByAdmin(@PathVariable String id, @RequestBody Teacher teacher) {
        teacher.setTno(id);
        if (teacherService.update(teacher)) {
            return ApiResponse.success("修改成功");
        }
        return ApiResponse.error(400, "修改失败");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable String id) {
        if (teacherService.delete(id)) {
            return ApiResponse.success("删除成功");
        }
        return ApiResponse.error(400, "删除失败");
    }

    @GetMapping("/search")
    public ApiResponse<List<Teacher>> search(
            @RequestParam(required = false) String tno,
            @RequestParam(required = false) String tname,
            @RequestParam(required = false) String tdept,
            @RequestParam(required = false) String ccno) {
        Teacher teacher = new Teacher();
        teacher.setTno(tno);
        teacher.setTname(tname);
        teacher.setTdept(tdept);
        teacher.setCcno(ccno);
        List<Teacher> list = teacherService.search(teacher);
        list.forEach(t -> t.setPassword(null));
        return ApiResponse.success(list);
    }

    // ==================== 教师自助接口（基于 ThreadLocal 用户上下文） ====================

    /** 教师查自己的信息 */
    @GetMapping("/me")
    public ApiResponse<Teacher> getMe() {
        Teacher t = teacherService.findById(UserContext.getUserId());
        if (t != null) {
            t.setPassword(null);
            return ApiResponse.success(t);
        }
        return ApiResponse.error(404, "教师不存在");
    }

    /** 教师改自己的密码 */
    @PutMapping("/me/password")
    public ApiResponse<String> modifyMyPassword(@RequestBody Map<String, String> params) {
        if (teacherService.modifyPassword(UserContext.getUserId(), params.get("password"))) {
            return ApiResponse.success("密码修改成功");
        }
        return ApiResponse.error(400, "密码修改失败");
    }

    /** 教师改自己的信息 */
    @PutMapping("/me/info")
    public ApiResponse<Teacher> modifyMyInfo(@RequestBody Teacher teacher) {
        String userId = UserContext.getUserId();
        if (teacherService.modifyInfo(userId,
                teacher.getTname(), teacher.getTdept(),
                teacher.getTtel(), teacher.getTemail(), teacher.getCcno())) {
            Teacher updated = teacherService.findById(userId);
            updated.setPassword(null);
            return ApiResponse.success("修改成功", updated);
        }
        return ApiResponse.error(400, "修改失败");
    }
}
