package com.soft.ob.elder.controller;

import com.soft.ob.elder.entity.Elderly;
import com.soft.ob.elder.service.ElderlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/elder/elderly")
@CrossOrigin(origins = "*")
public class ElderlyController {

    @Autowired
    private ElderlyService elderlyService;

    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> createElderly(@RequestBody Elderly elderly) {
        Elderly createdElderly = elderlyService.createElderly(elderly);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 201);
        response.put("message", "Elderly created successfully");
        response.put("data", createdElderly);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getElderlyById(@PathVariable Long id) {
        Elderly elderly = elderlyService.getElderlyById(id);
        Map<String, Object> response = new HashMap<>();

        if (elderly != null) {
            response.put("code", 200);
            response.put("message", "Success");
            response.put("data", elderly);
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Elderly not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getAllElderly() {
        List<Elderly> elderlyList = elderlyService.getAllElderly();
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Success");
        response.put("data", elderlyList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<Map<String, Object>> getElderlyByName(@PathVariable String name) {
        List<Elderly> elderlyList = elderlyService.getElderlyByName(name);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Success");
        response.put("data", elderlyList);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateElderly(@PathVariable Long id, @RequestBody Elderly elderly) {
        elderly.setId(id);
        Elderly updatedElderly = elderlyService.updateElderly(elderly);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Elderly updated successfully");
        response.put("data", updatedElderly);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteElderly(@PathVariable Long id) {
        boolean success = elderlyService.deleteElderly(id);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            response.put("code", 200);
            response.put("message", "Elderly deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Elderly not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<Map<String, Object>> activateElderly(@PathVariable Long id) {
        boolean success = elderlyService.activateElderly(id);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            response.put("code", 200);
            response.put("message", "Elderly activated successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Elderly not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Map<String, Object>> deactivateElderly(@PathVariable Long id) {
        boolean success = elderlyService.deactivateElderly(id);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            response.put("code", 200);
            response.put("message", "Elderly deactivated successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Elderly not found");
            return ResponseEntity.status(404).body(response);
        }
    }
}
