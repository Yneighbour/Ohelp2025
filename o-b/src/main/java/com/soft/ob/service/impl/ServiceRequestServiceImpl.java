package com.soft.ob.service.impl;

import com.soft.ob.entity.ServiceRequest;
import com.soft.ob.repository.ServiceRequestRepository;
import com.soft.ob.service.ServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceRequestServiceImpl implements ServiceRequestService {
    
    @Autowired
    private ServiceRequestRepository serviceRequestRepository;
    
    @Override
    public List<ServiceRequest> getAllServiceRequests() {
        return serviceRequestRepository.findAll();
    }
    
    @Override
    public Optional<ServiceRequest> getServiceRequestById(Long id) {
        return serviceRequestRepository.findById(id);
    }
    
    @Override
    public ServiceRequest saveServiceRequest(ServiceRequest serviceRequest) {
        return serviceRequestRepository.save(serviceRequest);
    }
    
    @Override
    public ServiceRequest updateServiceRequest(Long id, ServiceRequest serviceRequestDetails) {
        ServiceRequest serviceRequest = serviceRequestRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ServiceRequest not found with id: " + id));
        
        serviceRequest.setTitle(serviceRequestDetails.getTitle());
        serviceRequest.setDescription(serviceRequestDetails.getDescription());
        serviceRequest.setType(serviceRequestDetails.getType());
        serviceRequest.setPriority(serviceRequestDetails.getPriority());
        serviceRequest.setElderly(serviceRequestDetails.getElderly());
        serviceRequest.setAssignedStaff(serviceRequestDetails.getAssignedStaff());
        serviceRequest.setStatus(serviceRequestDetails.getStatus());
        serviceRequest.setScheduledDate(serviceRequestDetails.getScheduledDate());
        serviceRequest.setCompletedDate(serviceRequestDetails.getCompletedDate());
        serviceRequest.setNotes(serviceRequestDetails.getNotes());
        
        return serviceRequestRepository.save(serviceRequest);
    }
    
    @Override
    public void deleteServiceRequest(Long id) {
        serviceRequestRepository.deleteById(id);
    }
    
    @Override
    public List<ServiceRequest> getServiceRequestsByElderlyId(Long elderlyId) {
        return serviceRequestRepository.findByElderlyId(elderlyId);
    }
    
    @Override
    public List<ServiceRequest> getServiceRequestsByStatus(String status) {
        return serviceRequestRepository.findByStatus(status);
    }
    
    @Override
    public List<ServiceRequest> getServiceRequestsByPriority(String priority) {
        return serviceRequestRepository.findByPriority(priority);
    }
}