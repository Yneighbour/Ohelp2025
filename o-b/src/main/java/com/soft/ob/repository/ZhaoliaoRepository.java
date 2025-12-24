package com.soft.ob.repository;

import com.soft.ob.entity.Zhaoliao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ZhaoliaoRepository extends JpaRepository<Zhaoliao, Long> {
    List<Zhaoliao> findByLaorenId(Long laorenId);
    List<Zhaoliao> findByStatus(String status);
    List<Zhaoliao> findByType(String type);
    List<Zhaoliao> findByAssignedStaff(String assignedStaff);
}