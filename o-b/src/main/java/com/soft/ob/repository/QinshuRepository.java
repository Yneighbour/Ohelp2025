package com.soft.ob.repository;

import com.soft.ob.entity.Qinshu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QinshuRepository extends JpaRepository<Qinshu, Long> {
    List<Qinshu> findByLaorenUuid(String laorenUuid);
}