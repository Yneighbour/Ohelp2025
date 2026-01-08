package com.soft.ob.user.controller;

import com.soft.ob.user.entity.User;
import com.soft.ob.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 201);
        response.put("message", "User created successfully");
        response.put("data", createdUser);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        Map<String, Object> response = new HashMap<>();

        if (user != null) {
            response.put("code", 200);
            response.put("message", "Success");
            response.put("data", user);
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "User not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Map<String, Object>> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        Map<String, Object> response = new HashMap<>();

        if (user != null) {
            response.put("code", 200);
            response.put("message", "Success");
            response.put("data", user);
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "User not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity<Map<String, Object>> getUserByPhone(@PathVariable String phone) {
        User user = userService.getUserByPhone(phone);
        Map<String, Object> response = new HashMap<>();

        if (user != null) {
            response.put("code", 200);
            response.put("message", "Success");
            response.put("data", user);
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "User not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Success");
        response.put("data", users);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        User updatedUser = userService.updateUser(user);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "User updated successfully");
        response.put("data", updatedUser);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id) {
        boolean success = userService.deleteUser(id);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            response.put("code", 200);
            response.put("message", "User deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "User not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<Map<String, Object>> activateUser(@PathVariable Long id) {
        boolean success = userService.activateUser(id);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            response.put("code", 200);
            response.put("message", "User activated successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "User not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Map<String, Object>> deactivateUser(@PathVariable Long id) {
        boolean success = userService.deactivateUser(id);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            response.put("code", 200);
            response.put("message", "User deactivated successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "User not found");
            return ResponseEntity.status(404).body(response);
        }
    }
}
