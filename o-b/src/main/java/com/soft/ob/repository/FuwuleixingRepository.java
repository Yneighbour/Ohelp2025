package com.soft.ob.repository;

import com.soft.ob.entity.Fuwuleixing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuwuleixingRepository extends JpaRepository<Fuwuleixing, Long> {
}