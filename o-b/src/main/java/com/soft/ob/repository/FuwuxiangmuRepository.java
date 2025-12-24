package com.soft.ob.repository;

import com.soft.ob.entity.Fuwuxiangmu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FuwuxiangmuRepository extends JpaRepository<Fuwuxiangmu, Long> {
    List<Fuwuxiangmu> findByFuwuleixingId(Long fuwuleixingId);
    List<Fuwuxiangmu> findByShangxia(Integer shangxia);
}