package com.soft.ob.repository;

import com.soft.ob.entity.ServiceRequest;
import com.soft.ob.entity.Elderly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
    List<ServiceRequest> findByElderly(Elderly elderly);
    List<ServiceRequest> findByStatus(String status);
    List<ServiceRequest> findByPriority(String priority);
    List<ServiceRequest> findByElderlyId(Long elderlyId);
}