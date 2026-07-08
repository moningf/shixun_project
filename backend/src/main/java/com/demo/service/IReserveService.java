package com.demo.service;

import com.demo.entity.Reserve;
import java.util.List;

public interface IReserveService {
    List<Reserve> findAll();
    Reserve findById(String id);
    List<Reserve> findByCno(String cno);
    List<Reserve> findByBno(String bno);
    List<Reserve> findByStatus(String status);
    List<Reserve> findUnDeal();
    Reserve add(Reserve reserve);
    boolean delete(String id);
    boolean update(Reserve reserve);
    boolean updateNum(String id, int rnum);
    boolean dealReserve(String id, String place, String qTime);
    List<Reserve> dynamicSearch(Reserve reserve);
}
