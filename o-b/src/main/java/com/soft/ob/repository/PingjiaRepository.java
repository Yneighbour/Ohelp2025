package com.soft.ob.repository;

import com.soft.ob.entity.Pingjia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PingjiaRepository extends JpaRepository<Pingjia, Long> {
    List<Pingjia> findByLaorenId(Long laorenId);
    List<Pingjia> findByStaffId(Long staffId);
    List<Pingjia> findByServiceId(Long serviceId);
    List<Pingjia> findByRating(Integer rating);
    List<Pingjia> findByServiceType(String serviceType);
}