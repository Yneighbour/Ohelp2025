package com.soft.ob.controller;

import com.soft.ob.entity.ServiceRequest;
import com.soft.ob.service.ServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/service-requests")
@CrossOrigin(origins = "*")
public class ServiceRequestController {
    
    @Autowired
    private ServiceRequestService serviceRequestService;
    
    @GetMapping
    public ResponseEntity<List<ServiceRequest>> getAllServiceRequests() {
        List<ServiceRequest> serviceRequests = serviceRequestService.getAllServiceRequests();
        return new ResponseEntity<>(serviceRequests, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ServiceRequest> getServiceRequestById(@PathVariable Long id) {
        return serviceRequestService.getServiceRequestById(id)
            .map(serviceRequest -> new ResponseEntity<>(serviceRequest, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping
    public ResponseEntity<ServiceRequest> createServiceRequest(@RequestBody ServiceRequest serviceRequest) {
        ServiceRequest savedServiceRequest = serviceRequestService.saveServiceRequest(serviceRequest);
        return new ResponseEntity<>(savedServiceRequest, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ServiceRequest> updateServiceRequest(@PathVariable Long id, @RequestBody ServiceRequest serviceRequestDetails) {
        try {
            ServiceRequest updatedServiceRequest = serviceRequestService.updateServiceRequest(id, serviceRequestDetails);
            return new ResponseEntity<>(updatedServiceRequest, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceRequest(@PathVariable Long id) {
        serviceRequestService.deleteServiceRequest(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/laoren/{laorenId}")
    public ResponseEntity<List<ServiceRequest>> getServiceRequestsByLaorenId(@PathVariable Long laorenId) {
        List<ServiceRequest> serviceRequests = serviceRequestService.getServiceRequestsByLaorenId(laorenId);
        return new ResponseEntity<>(serviceRequests, HttpStatus.OK);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ServiceRequest>> getServiceRequestsByStatus(@PathVariable String status) {
        List<ServiceRequest> serviceRequests = serviceRequestService.getServiceRequestsByStatus(status);
        return new ResponseEntity<>(serviceRequests, HttpStatus.OK);
    }
    
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<ServiceRequest>> getServiceRequestsByPriority(@PathVariable String priority) {
        List<ServiceRequest> serviceRequests = serviceRequestService.getServiceRequestsByPriority(priority);
        return new ResponseEntity<>(serviceRequests, HttpStatus.OK);
    }
}