package com.demo.service.impl;

import com.demo.entity.Book;
import com.demo.entity.Purchase;
import com.demo.repository.BookRepository;
import com.demo.repository.PurchaseRepository;
import com.demo.service.IPurchaseService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseServiceImpl implements IPurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final BookRepository bookRepository;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, BookRepository bookRepository) {
        this.purchaseRepository = purchaseRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Purchase> findAll() {
        return purchaseRepository.findAll();
    }

    @Override
    public Purchase add(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }

    @Override
    public boolean delete(String bno) {
        if (purchaseRepository.existsById(bno)) {
            purchaseRepository.deleteById(bno);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean confirm(String bno) {
        Optional<Purchase> opt = purchaseRepository.findById(bno);
        if (!opt.isPresent()) return false;

        Purchase purchase = opt.get();
        Optional<Book> bookOpt = bookRepository.findById(bno);

        if (bookOpt.isPresent()) {
            // 教材已存在 → 增加库存
            Book book = bookOpt.get();
            int currentStock = Integer.parseInt(book.getBnum() == null ? "0" : book.getBnum());
            int newStock = currentStock + purchase.getNumber();
            book.setBnum(String.valueOf(newStock));
            bookRepository.save(book);
        } else {
            // 新教材 → 从采购计划创建，库存 = 采购数量
            Book book = new Book();
            book.setBno(purchase.getBno());
            book.setBname(purchase.getBname());
            book.setBauthor(purchase.getBauthor());
            book.setBsource(purchase.getBsource());
            book.setBedition(purchase.getBedition());
            book.setBprice(purchase.getBprice());
            book.setBnum(String.valueOf(purchase.getNumber()));
            // 外键设为 null（不可用空字符串！否则 EAGER 关联会查数据库找不存在的记录炸掉）
            book.setCcno(null);
            book.setTno(null);
            bookRepository.save(book);
        }

        purchaseRepository.deleteById(bno);
        return true;
    }
}
