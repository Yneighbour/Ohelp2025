package com.soft.ob.repository;

import com.soft.ob.entity.Jinjiqiuzhu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JinjiqiuzhuRepository extends JpaRepository<Jinjiqiuzhu, Long> {
    List<Jinjiqiuzhu> findByLaorenUuid(String laorenUuid);
}