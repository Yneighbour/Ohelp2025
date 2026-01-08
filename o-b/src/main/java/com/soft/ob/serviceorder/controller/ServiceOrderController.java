package com.soft.ob.serviceorder.controller;

import com.soft.ob.serviceorder.entity.ServiceOrder;
import com.soft.ob.serviceorder.service.ServiceOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/serviceorder")
@CrossOrigin(origins = "*")
public class ServiceOrderController {

    @Autowired
    private ServiceOrderService serviceOrderService;

    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> createServiceOrder(@RequestBody ServiceOrder serviceOrder) {
        serviceOrder.setStatus("pending");
        ServiceOrder createdOrder = serviceOrderService.createServiceOrder(serviceOrder);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 201);
        response.put("message", "Service order created successfully");
        response.put("data", createdOrder);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getServiceOrderById(@PathVariable Long id) {
        ServiceOrder serviceOrder = serviceOrderService.getServiceOrderById(id);
        Map<String, Object> response = new HashMap<>();

        if (serviceOrder != null) {
            response.put("code", 200);
            response.put("message", "Success");
            response.put("data", serviceOrder);
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Service order not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getAllServiceOrders() {
        List<ServiceOrder> orders = serviceOrderService.getAllServiceOrders();
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Success");
        response.put("data", orders);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/elderly/{elderlyId}")
    public ResponseEntity<Map<String, Object>> getServiceOrdersByElderlyId(@PathVariable Long elderlyId) {
        List<ServiceOrder> orders = serviceOrderService.getServiceOrdersByElderlyId(elderlyId);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Success");
        response.put("data", orders);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/service-type/{serviceType}")
    public ResponseEntity<Map<String, Object>> getServiceOrdersByServiceType(@PathVariable String serviceType) {
        List<ServiceOrder> orders = serviceOrderService.getServiceOrdersByServiceType(serviceType);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Success");
        response.put("data", orders);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/provider/{serviceProviderId}")
    public ResponseEntity<Map<String, Object>> getServiceOrdersByServiceProviderId(@PathVariable Long serviceProviderId) {
        List<ServiceOrder> orders = serviceOrderService.getServiceOrdersByServiceProviderId(serviceProviderId);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Success");
        response.put("data", orders);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Map<String, Object>> getServiceOrdersByStatus(@PathVariable String status) {
        List<ServiceOrder> orders = serviceOrderService.getServiceOrdersByStatus(status);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Success");
        response.put("data", orders);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateServiceOrder(@PathVariable Long id, @RequestBody ServiceOrder serviceOrder) {
        serviceOrder.setId(id);
        ServiceOrder updatedOrder = serviceOrderService.updateServiceOrder(serviceOrder);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Service order updated successfully");
        response.put("data", updatedOrder);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteServiceOrder(@PathVariable Long id) {
        boolean success = serviceOrderService.deleteServiceOrder(id);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            response.put("code", 200);
            response.put("message", "Service order deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Service order not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Map<String, Object>> cancelServiceOrder(@PathVariable Long id) {
        boolean success = serviceOrderService.cancelServiceOrder(id);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            response.put("code", 200);
            response.put("message", "Service order cancelled successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Service order not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Map<String, Object>> completeServiceOrder(@PathVariable Long id) {
        boolean success = serviceOrderService.completeServiceOrder(id);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            response.put("code", 200);
            response.put("message", "Service order completed successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Service order not found");
            return ResponseEntity.status(404).body(response);
        }
    }
}
