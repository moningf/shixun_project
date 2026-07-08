package com.demo.service.impl;

import com.demo.entity.Book;
import com.demo.repository.BookRepository;
import com.demo.service.IBookService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements IBookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findByBno(String bno) {
        return bookRepository.findById(bno).orElse(null);
    }

    @Override
    public List<Book> findByTno(String tno) {
        return bookRepository.findByTno(tno);
    }

    @Override
    public Book add(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public boolean delete(String bno) {
        if (bookRepository.existsById(bno)) {
            bookRepository.deleteById(bno);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Book book) {
        return bookRepository.updateBook(
                book.getBno(), book.getBname(), book.getBauthor(),
                book.getBsource(), book.getBedition(), book.getBprice(),
                book.getBnum(), book.getCcno()) > 0;
    }

    @Override
    public boolean updateStock(String bno, String bnum) {
        return bookRepository.updateStock(bno, bnum) > 0;
    }

    @Override
    public List<Book> dynamicSearch(Book book) {
        Specification<Book> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (book.getBno() != null && !book.getBno().trim().isEmpty()) {
                predicates.add(cb.like(root.get("bno"), "%" + book.getBno() + "%"));
            }
            if (book.getBname() != null && !book.getBname().trim().isEmpty()) {
                predicates.add(cb.like(root.get("bname"), "%" + book.getBname() + "%"));
            }
            if (book.getBauthor() != null && !book.getBauthor().trim().isEmpty()) {
                predicates.add(cb.like(root.get("bauthor"), "%" + book.getBauthor() + "%"));
            }
            if (book.getBsource() != null && !book.getBsource().trim().isEmpty()) {
                predicates.add(cb.like(root.get("bsource"), "%" + book.getBsource() + "%"));
            }
            if (book.getBedition() != null && !book.getBedition().trim().isEmpty()) {
                predicates.add(cb.like(root.get("bedition"), "%" + book.getBedition() + "%"));
            }
            if (book.getCcno() != null && !book.getCcno().trim().isEmpty()) {
                predicates.add(cb.like(root.get("ccno"), "%" + book.getCcno() + "%"));
            }
            if (book.getTno() != null && !book.getTno().trim().isEmpty()) {
                predicates.add(cb.like(root.get("tno"), "%" + book.getTno() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return bookRepository.findAll(spec);
    }
}
