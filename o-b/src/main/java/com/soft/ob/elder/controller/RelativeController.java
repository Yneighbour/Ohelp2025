package com.soft.ob.elder.controller;

import com.soft.ob.elder.entity.Relative;
import com.soft.ob.elder.service.RelativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/elder/relative")
@CrossOrigin(origins = "*")
public class RelativeController {

    @Autowired
    private RelativeService relativeService;

    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> createRelative(@RequestBody Relative relative) {
        Relative createdRelative = relativeService.createRelative(relative);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 201);
        response.put("message", "Relative created successfully");
        response.put("data", createdRelative);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getRelativeById(@PathVariable Long id) {
        Relative relative = relativeService.getRelativeById(id);
        Map<String, Object> response = new HashMap<>();

        if (relative != null) {
            response.put("code", 200);
            response.put("message", "Success");
            response.put("data", relative);
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Relative not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @GetMapping("/elderly/{elderlyId}")
    public ResponseEntity<Map<String, Object>> getRelativesByElderlyId(@PathVariable Long elderlyId) {
        List<Relative> relatives = relativeService.getRelativesByElderlyId(elderlyId);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Success");
        response.put("data", relatives);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getAllRelatives() {
        List<Relative> relatives = relativeService.getAllRelatives();
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Success");
        response.put("data", relatives);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateRelative(@PathVariable Long id, @RequestBody Relative relative) {
        relative.setId(id);
        Relative updatedRelative = relativeService.updateRelative(relative);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Relative updated successfully");
        response.put("data", updatedRelative);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteRelative(@PathVariable Long id) {
        boolean success = relativeService.deleteRelative(id);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            response.put("code", 200);
            response.put("message", "Relative deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Relative not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<Map<String, Object>> activateRelative(@PathVariable Long id) {
        boolean success = relativeService.activateRelative(id);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            response.put("code", 200);
            response.put("message", "Relative activated successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Relative not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Map<String, Object>> deactivateRelative(@PathVariable Long id) {
        boolean success = relativeService.deactivateRelative(id);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            response.put("code", 200);
            response.put("message", "Relative deactivated successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Relative not found");
            return ResponseEntity.status(404).body(response);
        }
    }
}
