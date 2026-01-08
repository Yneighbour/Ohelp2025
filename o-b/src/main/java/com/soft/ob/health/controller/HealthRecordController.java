package com.soft.ob.health.controller;

import com.soft.ob.health.entity.HealthRecord;
import com.soft.ob.health.service.HealthRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/health")
@CrossOrigin(origins = "*")
public class HealthRecordController {

    @Autowired
    private HealthRecordService healthRecordService;

    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> createHealthRecord(@RequestBody HealthRecord healthRecord) {
        HealthRecord createdRecord = healthRecordService.createHealthRecord(healthRecord);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 201);
        response.put("message", "Health record created successfully");
        response.put("data", createdRecord);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getHealthRecordById(@PathVariable Long id) {
        HealthRecord healthRecord = healthRecordService.getHealthRecordById(id);
        Map<String, Object> response = new HashMap<>();

        if (healthRecord != null) {
            response.put("code", 200);
            response.put("message", "Success");
            response.put("data", healthRecord);
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Health record not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getAllHealthRecords() {
        List<HealthRecord> records = healthRecordService.getAllHealthRecords();
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Success");
        response.put("data", records);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/elderly/{elderlyId}")
    public ResponseEntity<Map<String, Object>> getHealthRecordsByElderlyId(@PathVariable Long elderlyId) {
        List<HealthRecord> records = healthRecordService.getHealthRecordsByElderlyId(elderlyId);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Success");
        response.put("data", records);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/date/{recordDate}")
    public ResponseEntity<Map<String, Object>> getHealthRecordsByRecordDate(@PathVariable LocalDate recordDate) {
        List<HealthRecord> records = healthRecordService.getHealthRecordsByRecordDate(recordDate);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Success");
        response.put("data", records);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<Map<String, Object>> getHealthRecordsByDoctorId(@PathVariable Long doctorId) {
        List<HealthRecord> records = healthRecordService.getHealthRecordsByDoctorId(doctorId);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Success");
        response.put("data", records);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateHealthRecord(@PathVariable Long id, @RequestBody HealthRecord healthRecord) {
        healthRecord.setId(id);
        HealthRecord updatedRecord = healthRecordService.updateHealthRecord(healthRecord);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Health record updated successfully");
        response.put("data", updatedRecord);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteHealthRecord(@PathVariable Long id) {
        boolean success = healthRecordService.deleteHealthRecord(id);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            response.put("code", 200);
            response.put("message", "Health record deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Health record not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<Map<String, Object>> activateHealthRecord(@PathVariable Long id) {
        boolean success = healthRecordService.activateHealthRecord(id);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            response.put("code", 200);
            response.put("message", "Health record activated successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Health record not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Map<String, Object>> deactivateHealthRecord(@PathVariable Long id) {
        boolean success = healthRecordService.deactivateHealthRecord(id);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            response.put("code", 200);
            response.put("message", "Health record deactivated successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Health record not found");
            return ResponseEntity.status(404).body(response);
        }
    }
}
