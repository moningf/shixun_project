package com.demo.service.impl;

import com.demo.entity.Book;
import com.demo.entity.Reserve;
import com.demo.repository.BookRepository;
import com.demo.repository.ReserveRepository;
import com.demo.service.IReserveService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReserveServiceImpl implements IReserveService {

    private final ReserveRepository reserveRepository;
    private final BookRepository bookRepository;

    public ReserveServiceImpl(ReserveRepository reserveRepository, BookRepository bookRepository) {
        this.reserveRepository = reserveRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Reserve> findAll() {
        return reserveRepository.findAll();
    }

    @Override
    public Reserve findById(String id) {
        return reserveRepository.findById(id).orElse(null);
    }

    @Override
    public List<Reserve> findByCno(String cno) {
        return reserveRepository.findByCno(cno);
    }

    @Override
    public List<Reserve> findByBno(String bno) {
        return reserveRepository.findByBno(bno);
    }

    @Override
    public List<Reserve> findByStatus(String status) {
        return reserveRepository.findByBstatus(status);
    }

    @Override
    public List<Reserve> findUnDeal() {
        return reserveRepository.findByBstatus("未发放");
    }

    @Override
    public Reserve add(Reserve reserve) {
        int maxId = reserveRepository.findMaxId();
        reserve.setId(String.valueOf(maxId + 1));
        if (reserve.getBstatus() == null) {
            reserve.setBstatus("未发放");
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (reserve.getRTime() == null) {
            reserve.setRTime(format.format(new Date()));
        }
        if (reserve.getTotalPrice() == null && reserve.getBno() != null) {
            Optional<Book> bookOpt = bookRepository.findById(reserve.getBno());
            if (bookOpt.isPresent()) {
                double price = Double.parseDouble(bookOpt.get().getBprice());
                reserve.setTotalPrice(price * reserve.getNum());
            }
        }
        return reserveRepository.save(reserve);
    }

    @Override
    public boolean delete(String id) {
        if (reserveRepository.existsById(id)) {
            reserveRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Reserve reserve) {
        if (reserveRepository.existsById(reserve.getId())) {
            reserveRepository.save(reserve);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateNum(String id, int rnum) {
        Optional<Reserve> opt = reserveRepository.findById(id);
        if (opt.isPresent()) {
            Reserve r = opt.get();
            r.setNum(rnum);
            Optional<Book> bookOpt = bookRepository.findById(r.getBno());
            if (bookOpt.isPresent()) {
                double price = Double.parseDouble(bookOpt.get().getBprice());
                r.setTotalPrice(price * rnum);
            }
            reserveRepository.save(r);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean dealReserve(String id, String place, String qTime) {
        Optional<Reserve> opt = reserveRepository.findById(id);
        if (!opt.isPresent()) return false;

        Reserve reserve = opt.get();
        int needNumber = reserve.getNum();
        Optional<Book> bookOpt = bookRepository.findById(reserve.getBno());
        if (!bookOpt.isPresent()) return false;

        Book book = bookOpt.get();
        int bookNumber = Integer.parseInt(book.getBnum());

        if (needNumber > bookNumber) {
            return false;
        }

        int remaining = bookNumber - needNumber;
        book.setBnum(String.valueOf(remaining));
        bookRepository.updateStock(book.getBno(), book.getBnum());

        reserve.setPlace(place);
        reserve.setQTime(qTime);
        reserve.setBstatus("已处理");
        reserveRepository.save(reserve);
        return true;
    }

    @Override
    public List<Reserve> dynamicSearch(Reserve reserve) {
        Specification<Reserve> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (reserve.getId() != null && !reserve.getId().trim().isEmpty()) {
                predicates.add(cb.like(root.get("id"), "%" + reserve.getId() + "%"));
            }
            if (reserve.getCno() != null && !reserve.getCno().trim().isEmpty()) {
                predicates.add(cb.like(root.get("cno"), "%" + reserve.getCno() + "%"));
            }
            if (reserve.getBno() != null && !reserve.getBno().trim().isEmpty()) {
                predicates.add(cb.like(root.get("bno"), "%" + reserve.getBno() + "%"));
            }
            if (reserve.getRTime() != null && !reserve.getRTime().trim().isEmpty()) {
                predicates.add(cb.like(root.get("rTime"), "%" + reserve.getRTime() + "%"));
            }
            if (reserve.getQTime() != null && !reserve.getQTime().trim().isEmpty()) {
                predicates.add(cb.like(root.get("qTime"), "%" + reserve.getQTime() + "%"));
            }
            if (reserve.getPlace() != null && !reserve.getPlace().trim().isEmpty()) {
                predicates.add(cb.like(root.get("place"), "%" + reserve.getPlace() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return reserveRepository.findAll(spec);
    }
}
