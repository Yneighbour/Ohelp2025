package com.soft.ob.service;

import com.soft.ob.entity.ServiceRequest;
import java.util.List;
import java.util.Optional;

public interface ServiceRequestService {
    List<ServiceRequest> getAllServiceRequests();
    Optional<ServiceRequest> getServiceRequestById(Long id);
    ServiceRequest saveServiceRequest(ServiceRequest serviceRequest);
    ServiceRequest updateServiceRequest(Long id, ServiceRequest serviceRequest);
    void deleteServiceRequest(Long id);
    List<ServiceRequest> getServiceRequestsByLaorenId(Long laorenId);
    List<ServiceRequest> getServiceRequestsByStatus(String status);
    List<ServiceRequest> getServiceRequestsByPriority(String priority);
}