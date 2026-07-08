package com.demo.controller;

import com.demo.context.UserContext;
import com.demo.dto.ApiResponse;
import com.demo.entity.Monitor;
import com.demo.service.IMonitorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/monitors")
public class MonitorController {

    private final IMonitorService monitorService;

    public MonitorController(IMonitorService monitorService) {
        this.monitorService = monitorService;
    }

    // ==================== 管理员接口 ====================

    @GetMapping
    public ApiResponse<List<Monitor>> getAll() {
        List<Monitor> list = monitorService.findAll();
        list.forEach(m -> m.setPassword(null));
        return ApiResponse.success(list);
    }

    @GetMapping("/{id}")
    public ApiResponse<Monitor> getById(@PathVariable String id) {
        Monitor m = monitorService.findById(id);
        if (m != null) {
            m.setPassword(null);
            return ApiResponse.success(m);
        }
        return ApiResponse.error(404, "班级不存在");
    }

    @PostMapping
    public ApiResponse<Monitor> add(@RequestBody Monitor monitor) {
        Monitor result = monitorService.add(monitor);
        result.setPassword(null);
        return ApiResponse.success("添加成功", result);
    }

    @PutMapping("/{id}")
    public ApiResponse<String> updateByAdmin(@PathVariable String id, @RequestBody Monitor monitor) {
        monitor.setCno(id);
        if (monitorService.update(monitor)) {
            return ApiResponse.success("修改成功");
        }
        return ApiResponse.error(400, "修改失败");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable String id) {
        if (monitorService.delete(id)) {
            return ApiResponse.success("删除成功");
        }
        return ApiResponse.error(400, "删除失败");
    }

    @GetMapping("/search")
    public ApiResponse<List<Monitor>> search(
            @RequestParam(required = false) String cno,
            @RequestParam(required = false) String cgrade,
            @RequestParam(required = false) String cdept,
            @RequestParam(required = false) String cmajor) {
        Monitor monitor = new Monitor();
        monitor.setCno(cno);
        monitor.setCgrade(cgrade);
        monitor.setCdept(cdept);
        monitor.setCmajor(cmajor);
        List<Monitor> list = monitorService.search(monitor);
        list.forEach(m -> m.setPassword(null));
        return ApiResponse.success(list);
    }

    // ==================== 班级自助接口（基于 ThreadLocal 用户上下文） ====================

    /** 班级查自己的信息 */
    @GetMapping("/me")
    public ApiResponse<Monitor> getMe() {
        Monitor m = monitorService.findById(UserContext.getUserId());
        if (m != null) {
            m.setPassword(null);
            return ApiResponse.success(m);
        }
        return ApiResponse.error(404, "班级不存在");
    }

    /** 班级改自己的密码 */
    @PutMapping("/me/password")
    public ApiResponse<String> modifyMyPassword(@RequestBody Map<String, String> params) {
        if (monitorService.modifyPassword(UserContext.getUserId(), params.get("password"))) {
            return ApiResponse.success("密码修改成功");
        }
        return ApiResponse.error(400, "密码修改失败");
    }

    /** 班级改自己的信息 */
    @PutMapping("/me/info")
    public ApiResponse<Monitor> modifyMyInfo(@RequestBody Monitor monitor) {
        String userId = UserContext.getUserId();
        if (monitorService.modifyInfo(userId, monitor.getCgrade(), monitor.getCdept(), monitor.getCmajor())) {
            Monitor updated = monitorService.findById(userId);
            updated.setPassword(null);
            return ApiResponse.success("修改成功", updated);
        }
        return ApiResponse.error(400, "修改失败");
    }
}
