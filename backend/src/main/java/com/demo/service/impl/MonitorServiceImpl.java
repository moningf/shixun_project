package com.demo.service.impl;

import com.demo.entity.Monitor;
import com.demo.repository.MonitorRepository;
import com.demo.service.IMonitorService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MonitorServiceImpl implements IMonitorService {

    private final MonitorRepository monitorRepository;

    public MonitorServiceImpl(MonitorRepository monitorRepository) {
        this.monitorRepository = monitorRepository;
    }

    @Override
    public List<Monitor> findAll() {
        return monitorRepository.findAll();
    }

    @Override
    public Monitor findById(String cno) {
        return monitorRepository.findById(cno).orElse(null);
    }

    @Override
    public Monitor add(Monitor monitor) {
        return monitorRepository.save(monitor);
    }

    @Override
    public boolean delete(String cno) {
        if (monitorRepository.existsById(cno)) {
            monitorRepository.deleteById(cno);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Monitor monitor) {
        Optional<Monitor> opt = monitorRepository.findById(monitor.getCno());
        if (opt.isPresent()) {
            Monitor m = opt.get();
            if (monitor.getCgrade() != null) m.setCgrade(monitor.getCgrade());
            if (monitor.getCdept() != null) m.setCdept(monitor.getCdept());
            if (monitor.getCmajor() != null) m.setCmajor(monitor.getCmajor());
            if (monitor.getCnum() != null) m.setCnum(monitor.getCnum());
            if (monitor.getPassword() != null) m.setPassword(monitor.getPassword());
            monitorRepository.save(m);
            return true;
        } else {
            monitorRepository.save(monitor);
            return true;
        }
    }

    @Override
    public boolean modifyPassword(String id, String newPassword) {
        Optional<Monitor> opt = monitorRepository.findById(id);
        if (opt.isPresent()) {
            Monitor m = opt.get();
            m.setPassword(newPassword);
            monitorRepository.save(m);
            return true;
        }
        return false;
    }

    @Override
    public boolean modifyInfo(String id, String cgrade, String cdept, String cmajor) {
        Optional<Monitor> opt = monitorRepository.findById(id);
        if (opt.isPresent()) {
            Monitor m = opt.get();
            if (cgrade != null) m.setCgrade(cgrade);
            if (cdept != null) m.setCdept(cdept);
            if (cmajor != null) m.setCmajor(cmajor);
            monitorRepository.save(m);
            return true;
        }
        return false;
    }

    @Override
    public List<Monitor> search(Monitor monitor) {
        Specification<Monitor> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (monitor.getCno() != null && !monitor.getCno().trim().isEmpty()) {
                predicates.add(cb.like(root.get("cno"), "%" + monitor.getCno() + "%"));
            }
            if (monitor.getCgrade() != null && !monitor.getCgrade().trim().isEmpty()) {
                predicates.add(cb.like(root.get("cgrade"), "%" + monitor.getCgrade() + "%"));
            }
            if (monitor.getCdept() != null && !monitor.getCdept().trim().isEmpty()) {
                predicates.add(cb.like(root.get("cdept"), "%" + monitor.getCdept() + "%"));
            }
            if (monitor.getCmajor() != null && !monitor.getCmajor().trim().isEmpty()) {
                predicates.add(cb.like(root.get("cmajor"), "%" + monitor.getCmajor() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return monitorRepository.findAll(spec);
    }

    @Override
    public List<Monitor> findByDept(String dept) {
        return monitorRepository.findByCdept(dept);
    }

    @Override
    public List<Monitor> findByGrade(String grade) {
        return monitorRepository.findByCgrade(grade);
    }
}
