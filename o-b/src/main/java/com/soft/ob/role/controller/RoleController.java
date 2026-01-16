package com.soft.ob.role.controller;

import com.soft.ob.role.entity.Role;
import com.soft.ob.role.entity.Permission;
import com.soft.ob.role.service.RoleService;
import com.soft.ob.role.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")
@CrossOrigin(origins = "*")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> createRole(@RequestBody Role role) {
        Role createdRole = roleService.createRole(role);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 201);
        response.put("message", "Role created successfully");
        response.put("data", createdRole);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getRoleById(@PathVariable Long id) {
        Role role = roleService.getRoleById(id);
        Map<String, Object> response = new HashMap<>();

        if (role != null) {
            response.put("code", 200);
            response.put("message", "Success");
            response.put("data", role);
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Role not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<Map<String, Object>> getRoleByCode(@PathVariable String code) {
        Role role = roleService.getRoleByCode(code);
        Map<String, Object> response = new HashMap<>();

        if (role != null) {
            response.put("code", 200);
            response.put("message", "Success");
            response.put("data", role);
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Role not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "Success");
        response.put("data", roles);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/permissions")
    public ResponseEntity<Map<String, Object>> getRolePermissions(@PathVariable Long id) {
        List<Permission> permissions = rolePermissionService.getPermissionsByRoleId(id);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "Success");
        response.put("data", permissions);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateRole(@PathVariable Long id, @RequestBody Role role) {
        role.setId(id);
        Role updatedRole = roleService.updateRole(role);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "Role updated successfully");
        response.put("data", updatedRole);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/permissions")
    public ResponseEntity<Map<String, Object>> assignPermissionsToRole(
            @PathVariable Long id,
            @RequestBody Map<String, List<Long>> request) {
        List<Long> permissionIds = request.get("permissionIds");
        boolean success = rolePermissionService.assignPermissionsToRole(id, permissionIds);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            response.put("code", 200);
            response.put("message", "Permissions assigned successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 500);
            response.put("message", "Failed to assign permissions");
            return ResponseEntity.status(500).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteRole(@PathVariable Long id) {
        boolean success = roleService.deleteRole(id);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            response.put("code", 200);
            response.put("message", "Role deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Role not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<Map<String, Object>> activateRole(@PathVariable Long id) {
        boolean success = roleService.activateRole(id);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            response.put("code", 200);
            response.put("message", "Role activated successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Role not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Map<String, Object>> deactivateRole(@PathVariable Long id) {
        boolean success = roleService.deactivateRole(id);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            response.put("code", 200);
            response.put("message", "Role deactivated successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Role not found");
            return ResponseEntity.status(404).body(response);
        }
    }
}
