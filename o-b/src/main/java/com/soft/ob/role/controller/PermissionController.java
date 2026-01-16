package com.soft.ob.role.controller;

import com.soft.ob.role.entity.Permission;
import com.soft.ob.role.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/permission")
@CrossOrigin(origins = "*")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> createPermission(@RequestBody Permission permission) {
        Permission createdPermission = permissionService.createPermission(permission);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 201);
        response.put("message", "Permission created successfully");
        response.put("data", createdPermission);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getPermissionById(@PathVariable Long id) {
        Permission permission = permissionService.getPermissionById(id);
        Map<String, Object> response = new HashMap<>();

        if (permission != null) {
            response.put("code", 200);
            response.put("message", "Success");
            response.put("data", permission);
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Permission not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getAllPermissions() {
        List<Permission> permissions = permissionService.getAllPermissions();
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "Success");
        response.put("data", permissions);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/module/{module}")
    public ResponseEntity<Map<String, Object>> getPermissionsByModule(@PathVariable String module) {
        List<Permission> permissions = permissionService.getPermissionsByModule(module);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "Success");
        response.put("data", permissions);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updatePermission(@PathVariable Long id, @RequestBody Permission permission) {
        permission.setId(id);
        Permission updatedPermission = permissionService.updatePermission(permission);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "Permission updated successfully");
        response.put("data", updatedPermission);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deletePermission(@PathVariable Long id) {
        boolean success = permissionService.deletePermission(id);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            response.put("code", 200);
            response.put("message", "Permission deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Permission not found");
            return ResponseEntity.status(404).body(response);
        }
    }
}
