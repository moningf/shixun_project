package com.demo.repository;

import com.demo.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, String>, JpaSpecificationExecutor<Teacher> {
    Optional<Teacher> findByTnoAndPassword(String tno, String password);
    List<Teacher> findByTdept(String tdept);
    List<Teacher> findByTname(String tname);
    List<Teacher> findByCcno(String ccno);
    List<Teacher> findByTdeptAndCcno(String tdept, String ccno);
    List<Teacher> findByTdeptAndTname(String tdept, String tname);
}
