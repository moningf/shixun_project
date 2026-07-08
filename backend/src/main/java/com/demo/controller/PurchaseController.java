package com.demo.controller;

import com.demo.dto.ApiResponse;
import com.demo.entity.Purchase;
import com.demo.service.IPurchaseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    private final IPurchaseService purchaseService;

    public PurchaseController(IPurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping
    public ApiResponse<List<Purchase>> getAll() {
        return ApiResponse.success(purchaseService.findAll());
    }

    @PostMapping
    public ApiResponse<Purchase> add(@RequestBody Purchase purchase) {
        return ApiResponse.success("添加成功", purchaseService.add(purchase));
    }

    @DeleteMapping("/{bno}")
    public ApiResponse<String> delete(@PathVariable String bno) {
        if (purchaseService.delete(bno)) {
            return ApiResponse.success("删除成功");
        }
        return ApiResponse.error(400, "删除失败");
    }

    /** 确认采购入库 */
    @PutMapping("/{bno}/confirm")
    public ApiResponse<String> confirm(@PathVariable String bno) {
        if (purchaseService.confirm(bno)) {
            return ApiResponse.success("入库成功");
        }
        return ApiResponse.error(400, "入库失败，采购记录不存在");
    }
}
