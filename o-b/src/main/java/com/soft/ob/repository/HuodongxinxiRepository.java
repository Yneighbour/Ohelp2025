package com.soft.ob.repository;

import com.soft.ob.entity.Huodongxinxi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HuodongxinxiRepository extends JpaRepository<Huodongxinxi, Long> {
    List<Huodongxinxi> findByHuodongfenleiId(Long huodongfenleiId);
    List<Huodongxinxi> findByHuodongYesno(String huodongYesno);
}