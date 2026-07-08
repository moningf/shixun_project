package com.demo.repository;

import com.demo.entity.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReserveRepository extends JpaRepository<Reserve, String>, JpaSpecificationExecutor<Reserve> {
    List<Reserve> findByCno(String cno);
    List<Reserve> findByBno(String bno);
    List<Reserve> findByBstatus(String bstatus);

    @Query("SELECT COALESCE(MAX(r.id), 0) FROM Reserve r")
    int findMaxId();
}
