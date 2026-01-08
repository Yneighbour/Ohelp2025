package com.soft.ob.activity.controller;

import com.soft.ob.activity.entity.Activity;
import com.soft.ob.activity.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/activity")
@CrossOrigin(origins = "*")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> createActivity(@RequestBody Activity activity) {
        Activity createdActivity = activityService.createActivity(activity);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 201);
        response.put("message", "Activity created successfully");
        response.put("data", createdActivity);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getActivityById(@PathVariable Long id) {
        Activity activity = activityService.getActivityById(id);
        Map<String, Object> response = new HashMap<>();

        if (activity != null) {
            response.put("code", 200);
            response.put("message", "Success");
            response.put("data", activity);
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Activity not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getAllActivities() {
        List<Activity> activities = activityService.getAllActivities();
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Success");
        response.put("data", activities);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Map<String, Object>> getActivitiesByCategory(@PathVariable String category) {
        List<Activity> activities = activityService.getActivitiesByCategory(category);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Success");
        response.put("data", activities);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Map<String, Object>> getActivitiesByStatus(@PathVariable String status) {
        List<Activity> activities = activityService.getActivitiesByStatus(status);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Success");
        response.put("data", activities);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateActivity(@PathVariable Long id, @RequestBody Activity activity) {
        activity.setId(id);
        Activity updatedActivity = activityService.updateActivity(activity);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Activity updated successfully");
        response.put("data", updatedActivity);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteActivity(@PathVariable Long id) {
        boolean success = activityService.deleteActivity(id);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            response.put("code", 200);
            response.put("message", "Activity deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Activity not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<Map<String, Object>> activateActivity(@PathVariable Long id) {
        boolean success = activityService.activateActivity(id);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            response.put("code", 200);
            response.put("message", "Activity activated successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Activity not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Map<String, Object>> deactivateActivity(@PathVariable Long id) {
        boolean success = activityService.deactivateActivity(id);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            response.put("code", 200);
            response.put("message", "Activity deactivated successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Activity not found");
            return ResponseEntity.status(404).body(response);
        }
    }
}
