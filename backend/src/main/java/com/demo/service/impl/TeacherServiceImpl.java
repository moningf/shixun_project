package com.demo.service.impl;

import com.demo.entity.Teacher;
import com.demo.repository.TeacherRepository;
import com.demo.service.ITeacherService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements ITeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher findById(String tno) {
        return teacherRepository.findById(tno).orElse(null);
    }

    @Override
    public Teacher add(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public boolean delete(String tno) {
        if (teacherRepository.existsById(tno)) {
            teacherRepository.deleteById(tno);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Teacher teacher) {
        Optional<Teacher> opt = teacherRepository.findById(teacher.getTno());
        if (opt.isPresent()) {
            Teacher t = opt.get();
            if (teacher.getTname() != null) t.setTname(teacher.getTname());
            if (teacher.getTdept() != null) t.setTdept(teacher.getTdept());
            if (teacher.getTtel() != null) t.setTtel(teacher.getTtel());
            if (teacher.getTemail() != null) t.setTemail(teacher.getTemail());
            if (teacher.getCcno() != null) t.setCcno(teacher.getCcno());
            if (teacher.getPassword() != null) t.setPassword(teacher.getPassword());
            teacherRepository.save(t);
            return true;
        }
        return false;
    }

    @Override
    public boolean modifyPassword(String id, String newPassword) {
        Optional<Teacher> opt = teacherRepository.findById(id);
        if (opt.isPresent()) {
            Teacher t = opt.get();
            t.setPassword(newPassword);
            teacherRepository.save(t);
            return true;
        }
        return false;
    }

    @Override
    public boolean modifyInfo(String id, String tname, String tdept, String ttel, String temail, String ccno) {
        Optional<Teacher> opt = teacherRepository.findById(id);
        if (opt.isPresent()) {
            Teacher t = opt.get();
            if (tname != null) t.setTname(tname);
            if (tdept != null) t.setTdept(tdept);
            if (ttel != null) t.setTtel(ttel);
            if (temail != null) t.setTemail(temail);
            if (ccno != null) t.setCcno(ccno);
            teacherRepository.save(t);
            return true;
        }
        return false;
    }

    @Override
    public List<Teacher> search(Teacher teacher) {
        Specification<Teacher> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (teacher.getTno() != null && !teacher.getTno().trim().isEmpty()) {
                predicates.add(cb.like(root.get("tno"), "%" + teacher.getTno() + "%"));
            }
            if (teacher.getTname() != null && !teacher.getTname().trim().isEmpty()) {
                predicates.add(cb.like(root.get("tname"), "%" + teacher.getTname() + "%"));
            }
            if (teacher.getTdept() != null && !teacher.getTdept().trim().isEmpty()) {
                predicates.add(cb.like(root.get("tdept"), "%" + teacher.getTdept() + "%"));
            }
            if (teacher.getCcno() != null && !teacher.getCcno().trim().isEmpty()) {
                predicates.add(cb.like(root.get("ccno"), "%" + teacher.getCcno() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return teacherRepository.findAll(spec);
    }

    @Override
    public List<Teacher> findByDept(String dept) {
        return teacherRepository.findByTdept(dept);
    }

    @Override
    public List<Teacher> findByName(String name) {
        return teacherRepository.findByTname(name);
    }

    @Override
    public List<Teacher> findByCcno(String ccno) {
        return teacherRepository.findByCcno(ccno);
    }
}
