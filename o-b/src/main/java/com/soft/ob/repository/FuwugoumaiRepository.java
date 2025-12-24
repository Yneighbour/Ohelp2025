package com.soft.ob.repository;

import com.soft.ob.entity.Fuwugoumai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FuwugoumaiRepository extends JpaRepository<Fuwugoumai, Long> {
    List<Fuwugoumai> findByLaorenUuid(String laorenUuid);
    List<Fuwugoumai> findByGoumaiYesno(String goumaiYesno);
}