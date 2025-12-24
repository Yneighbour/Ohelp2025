package com.soft.ob.repository;

import com.soft.ob.entity.Jifenzengjia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JifenzengjiaRepository extends JpaRepository<Jifenzengjia, Long> {
    List<Jifenzengjia> findByLaorenUuid(String laorenUuid);
}