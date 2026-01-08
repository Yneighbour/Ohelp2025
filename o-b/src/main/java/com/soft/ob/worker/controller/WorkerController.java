package com.soft.ob.worker.controller;

import com.soft.ob.worker.entity.Worker;
import com.soft.ob.worker.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/worker")
@CrossOrigin(origins = "*")
public class WorkerController {

    @Autowired
    private WorkerService workerService;

    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> createWorker(@RequestBody Worker worker) {
        Worker createdWorker = workerService.createWorker(worker);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 201);
        response.put("message", "Worker created successfully");
        response.put("data", createdWorker);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getWorkerById(@PathVariable Long id) {
        Worker worker = workerService.getWorkerById(id);
        Map<String, Object> response = new HashMap<>();

        if (worker != null) {
            response.put("code", 200);
            response.put("message", "Success");
            response.put("data", worker);
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Worker not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Map<String, Object>> getWorkerByEmail(@PathVariable String email) {
        Worker worker = workerService.getWorkerByEmail(email);
        Map<String, Object> response = new HashMap<>();

        if (worker != null) {
            response.put("code", 200);
            response.put("message", "Success");
            response.put("data", worker);
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Worker not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity<Map<String, Object>> getWorkerByPhone(@PathVariable String phone) {
        Worker worker = workerService.getWorkerByPhone(phone);
        Map<String, Object> response = new HashMap<>();

        if (worker != null) {
            response.put("code", 200);
            response.put("message", "Success");
            response.put("data", worker);
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Worker not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getAllWorkers() {
        List<Worker> workers = workerService.getAllWorkers();
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Success");
        response.put("data", workers);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<Map<String, Object>> getWorkersByDepartment(@PathVariable String department) {
        List<Worker> workers = workerService.getWorkersByDepartment(department);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Success");
        response.put("data", workers);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/position/{position}")
    public ResponseEntity<Map<String, Object>> getWorkersByPosition(@PathVariable String position) {
        List<Worker> workers = workerService.getWorkersByPosition(position);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Success");
        response.put("data", workers);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/available")
    public ResponseEntity<Map<String, Object>> getAvailableWorkers() {
        List<Worker> workers = workerService.getAvailableWorkers();
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Success");
        response.put("data", workers);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateWorker(@PathVariable Long id, @RequestBody Worker worker) {
        worker.setId(id);
        Worker updatedWorker = workerService.updateWorker(worker);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Worker updated successfully");
        response.put("data", updatedWorker);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteWorker(@PathVariable Long id) {
        boolean success = workerService.deleteWorker(id);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            response.put("code", 200);
            response.put("message", "Worker deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Worker not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<Map<String, Object>> activateWorker(@PathVariable Long id) {
        boolean success = workerService.activateWorker(id);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            response.put("code", 200);
            response.put("message", "Worker activated successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Worker not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Map<String, Object>> deactivateWorker(@PathVariable Long id) {
        boolean success = workerService.deactivateWorker(id);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            response.put("code", 200);
            response.put("message", "Worker deactivated successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Worker not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @PutMapping("/{id}/availability")
    public ResponseEntity<Map<String, Object>> setWorkerAvailability(@PathVariable Long id, @RequestParam Boolean available) {
        boolean success = workerService.setWorkerAvailability(id, available);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            response.put("code", 200);
            response.put("message", "Worker availability updated successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Worker not found");
            return ResponseEntity.status(404).body(response);
        }
    }
}
