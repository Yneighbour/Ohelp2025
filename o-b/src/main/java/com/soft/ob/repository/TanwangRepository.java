package com.soft.ob.repository;

import com.soft.ob.entity.Tanwang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TanwangRepository extends JpaRepository<Tanwang, Long> {
    List<Tanwang> findByLaorenId(Long laorenId);
    List<Tanwang> findByVisitorNameContaining(String visitorName);
    List<Tanwang> findByStatus(String status);
    List<Tanwang> findByStaffId(Long staffId);
    List<Tanwang> findByVisitTimeBetween(java.time.LocalDateTime startTime, java.time.LocalDateTime endTime);
}