package com.soft.ob.emergency.controller;

import com.soft.ob.emergency.entity.Emergency;
import com.soft.ob.emergency.service.EmergencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/emergency")
@CrossOrigin(origins = "*")
public class EmergencyController {

    @Autowired
    private EmergencyService emergencyService;

    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> createEmergency(@RequestBody Emergency emergency) {
        emergency.setStatus("pending");
        Emergency createdEmergency = emergencyService.createEmergency(emergency);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 201);
        response.put("message", "Emergency request created successfully");
        response.put("data", createdEmergency);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getEmergencyById(@PathVariable Long id) {
        Emergency emergency = emergencyService.getEmergencyById(id);
        Map<String, Object> response = new HashMap<>();

        if (emergency != null) {
            response.put("code", 200);
            response.put("message", "Success");
            response.put("data", emergency);
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Emergency request not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getAllEmergencies() {
        List<Emergency> emergencies = emergencyService.getAllEmergencies();
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Success");
        response.put("data", emergencies);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/elderly/{elderlyId}")
    public ResponseEntity<Map<String, Object>> getEmergenciesByElderlyId(@PathVariable Long elderlyId) {
        List<Emergency> emergencies = emergencyService.getEmergenciesByElderlyId(elderlyId);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Success");
        response.put("data", emergencies);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Map<String, Object>> getEmergenciesByStatus(@PathVariable String status) {
        List<Emergency> emergencies = emergencyService.getEmergenciesByStatus(status);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Success");
        response.put("data", emergencies);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<Map<String, Object>> getEmergenciesByPriority(@PathVariable String priority) {
        List<Emergency> emergencies = emergencyService.getEmergenciesByPriority(priority);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Success");
        response.put("data", emergencies);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateEmergency(@PathVariable Long id, @RequestBody Emergency emergency) {
        emergency.setId(id);
        Emergency updatedEmergency = emergencyService.updateEmergency(emergency);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Emergency request updated successfully");
        response.put("data", updatedEmergency);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteEmergency(@PathVariable Long id) {
        boolean success = emergencyService.deleteEmergency(id);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            response.put("code", 200);
            response.put("message", "Emergency request deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Emergency request not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @PutMapping("/{id}/respond")
    public ResponseEntity<Map<String, Object>> respondToEmergency(@PathVariable Long id, @RequestParam Long responderId) {
        boolean success = emergencyService.respondToEmergency(id, responderId);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            response.put("code", 200);
            response.put("message", "Emergency responded successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Emergency request not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @PutMapping("/{id}/resolve")
    public ResponseEntity<Map<String, Object>> resolveEmergency(@PathVariable Long id) {
        boolean success = emergencyService.resolveEmergency(id);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            response.put("code", 200);
            response.put("message", "Emergency resolved successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Emergency request not found");
            return ResponseEntity.status(404).body(response);
        }
    }
}
