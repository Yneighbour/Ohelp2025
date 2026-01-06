package com.soft.ob.serviceorder.service.impl;

import com.soft.ob.entity.ServiceRequest;
import com.soft.ob.repository.ServiceRequestRepository;
import com.soft.ob.serviceorder.service.ServiceRequestService;
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
    public ServiceRequest updateServiceRequest(Long id, ServiceRequest serviceRequest) {
        ServiceRequest existingServiceRequest = serviceRequestRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ServiceRequest not found with id: " + id));
        
        existingServiceRequest.setTitle(serviceRequest.getTitle());
        existingServiceRequest.setDescription(serviceRequest.getDescription());
        existingServiceRequest.setType(serviceRequest.getType());
        existingServiceRequest.setPriority(serviceRequest.getPriority());
        existingServiceRequest.setElderly(serviceRequest.getElderly());
        existingServiceRequest.setAssignedStaff(serviceRequest.getAssignedStaff());
        existingServiceRequest.setStatus(serviceRequest.getStatus());
        existingServiceRequest.setRequestDate(serviceRequest.getRequestDate());
        existingServiceRequest.setScheduledDate(serviceRequest.getScheduledDate());
        existingServiceRequest.setCompletedDate(serviceRequest.getCompletedDate());
        existingServiceRequest.setNotes(serviceRequest.getNotes());
        
        return serviceRequestRepository.save(existingServiceRequest);
    }
    
    @Override
    public void deleteServiceRequest(Long id) {
        ServiceRequest serviceRequest = serviceRequestRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ServiceRequest not found with id: " + id));
        serviceRequestRepository.delete(serviceRequest);
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