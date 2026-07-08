package com.demo.service;

import com.demo.entity.Purchase;
import java.util.List;

public interface IPurchaseService {
    List<Purchase> findAll();
    Purchase add(Purchase purchase);
    boolean delete(String bno);
    /** 确认采购入库：更新 book 表库存（或新增教材），再删除采购记录 */
    boolean confirm(String bno);
}
