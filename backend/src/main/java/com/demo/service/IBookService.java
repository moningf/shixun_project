package com.demo.service;

import com.demo.entity.Book;
import java.util.List;

public interface IBookService {
    List<Book> findAll();
    Book findByBno(String bno);
    List<Book> findByTno(String tno);
    Book add(Book book);
    boolean delete(String bno);
    boolean update(Book book);
    boolean updateStock(String bno, String bnum);
    List<Book> dynamicSearch(Book book);
}
