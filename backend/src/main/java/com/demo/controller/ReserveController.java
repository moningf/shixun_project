package com.demo.controller;

import com.demo.context.UserContext;
import com.demo.dto.ApiResponse;
import com.demo.entity.Reserve;
import com.demo.service.IReserveService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reserves")
public class ReserveController {

    private final IReserveService reserveService;

    public ReserveController(IReserveService reserveService) {
        this.reserveService = reserveService;
    }

    // ==================== 管理员接口 ====================

    @GetMapping
    public ApiResponse<List<Reserve>> getAll() {
        return ApiResponse.success(reserveService.findAll());
    }

    @GetMapping("/undeal")
    public ApiResponse<List<Reserve>> getUnDeal() {
        return ApiResponse.success(reserveService.findUnDeal());
    }

    @GetMapping("/{id}")
    public ApiResponse<Reserve> getById(@PathVariable String id) {
        Reserve reserve = reserveService.findById(id);
        if (reserve != null) {
            return ApiResponse.success(reserve);
        }
        return ApiResponse.error(404, "订单不存在");
    }

    /** 管理员按学号查订单 */
    @GetMapping("/by-monitor/{cno}")
    public ApiResponse<List<Reserve>> getByCno(@PathVariable String cno) {
        return ApiResponse.success(reserveService.findByCno(cno));
    }

    @PutMapping("/{id}")
    public ApiResponse<String> update(@PathVariable String id, @RequestBody Reserve reserve) {
        reserve.setId(id);
        if (reserveService.update(reserve)) {
            return ApiResponse.success("修改成功");
        }
        return ApiResponse.error(400, "修改失败");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable String id) {
        if (reserveService.delete(id)) {
            return ApiResponse.success("删除成功");
        }
        return ApiResponse.error(400, "删除失败");
    }

    @PutMapping("/{id}/deal")
    public ApiResponse<String> deal(@PathVariable String id, @RequestBody Map<String, String> params) {
        String place = params.get("place");
        String qTime = params.get("qTime");
        if (reserveService.dealReserve(id, place, qTime)) {
            return ApiResponse.success("处理成功");
        }
        return ApiResponse.error(400, "库存不足，处理失败");
    }

    // ==================== 班长自助接口 ====================

    /** 班长查自己的订单 */
    @GetMapping("/my")
    public ApiResponse<List<Reserve>> getMyReserves() {
        return ApiResponse.success(reserveService.findByCno(UserContext.getUserId()));
    }

    /** 班长预订教材，cno 自动从上下文获取 */
    @PostMapping
    public ApiResponse<Reserve> add(@RequestBody Reserve reserve) {
        reserve.setCno(UserContext.getUserId());
        return ApiResponse.success("预订成功", reserveService.add(reserve));
    }

    /** 班长修改自己订单的数量 */
    @PutMapping("/my/{id}/num")
    public ApiResponse<String> updateMyNum(@PathVariable String id, @RequestBody Map<String, Object> params) {
        int rnum = Integer.parseInt(params.get("rnum").toString());
        if (reserveService.updateNum(id, rnum)) {
            return ApiResponse.success("修改成功");
        }
        return ApiResponse.error(400, "修改失败");
    }
}
