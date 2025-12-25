package com.soft.ob.repository;

import com.soft.ob.entity.ServiceRequest;
import com.soft.ob.entity.Laoren;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
    List<ServiceRequest> findByLaoren(Laoren laoren);
    List<ServiceRequest> findByStatus(String status);
    List<ServiceRequest> findByPriority(String priority);
    List<ServiceRequest> findByLaorenId(Long laorenId);
}