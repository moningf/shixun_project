package com.demo.repository;

import com.demo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String>, JpaSpecificationExecutor<Book> {
    List<Book> findByBname(String bname);
    List<Book> findByBauthor(String bauthor);
    List<Book> findByBsource(String bsource);
    List<Book> findByCcno(String ccno);
    List<Book> findByTno(String tno);

    @Modifying
    @Transactional
    @Query("UPDATE Book b SET b.bname = :bname, b.bauthor = :bauthor, b.bsource = :bsource, " +
           "b.bedition = :bedition, b.bprice = :bprice, b.bnum = :bnum, b.ccno = :ccno WHERE b.bno = :bno")
    int updateBook(@Param("bno") String bno, @Param("bname") String bname,
                   @Param("bauthor") String bauthor, @Param("bsource") String bsource,
                   @Param("bedition") String bedition, @Param("bprice") String bprice,
                   @Param("bnum") String bnum, @Param("ccno") String ccno);

    @Modifying
    @Transactional
    @Query("UPDATE Book b SET b.bnum = :bnum WHERE b.bno = :bno")
    int updateStock(@Param("bno") String bno, @Param("bnum") String bnum);
}
