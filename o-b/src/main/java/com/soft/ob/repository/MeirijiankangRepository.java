package com.soft.ob.repository;

import com.soft.ob.entity.Meirijiankang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeirijiankangRepository extends JpaRepository<Meirijiankang, Long> {
}