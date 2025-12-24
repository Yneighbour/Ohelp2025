package com.soft.ob.repository;

import com.soft.ob.entity.Huodongfenlei;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HuodongfenleiRepository extends JpaRepository<Huodongfenlei, Long> {
}