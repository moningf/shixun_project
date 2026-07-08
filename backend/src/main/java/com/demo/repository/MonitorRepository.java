package com.demo.repository;

import com.demo.entity.Monitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MonitorRepository extends JpaRepository<Monitor, String>, JpaSpecificationExecutor<Monitor> {
    Optional<Monitor> findByCnoAndPassword(String cno, String password);
    List<Monitor> findByCdept(String cdept);
    List<Monitor> findByCgrade(String cgrade);
    List<Monitor> findByCmajor(String cmajor);
    List<Monitor> findByCnum(String cnum);
    List<Monitor> findByCgradeAndCdept(String cgrade, String cdept);
    List<Monitor> findByCgradeAndCdeptAndCmajor(String cgrade, String cdept, String cmajor);
}
