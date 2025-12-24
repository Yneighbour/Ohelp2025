package com.soft.ob.repository;

import com.soft.ob.entity.Jiwangbingshi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JiwangbingshiRepository extends JpaRepository<Jiwangbingshi, Long> {
    List<Jiwangbingshi> findByLaorenUuid(String laorenUuid);
}