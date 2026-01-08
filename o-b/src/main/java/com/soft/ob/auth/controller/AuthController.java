package com.soft.ob.auth.controller;

import com.soft.ob.auth.entity.Auth;
import com.soft.ob.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        Auth auth = authService.login(username, password);
        Map<String, Object> response = new HashMap<>();

        if (auth != null) {
            response.put("code", 200);
            response.put("message", "Login successful");
            response.put("data", auth);
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 401);
            response.put("message", "Invalid credentials");
            return ResponseEntity.status(401).body(response);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(@RequestParam Long authId) {
        boolean success = authService.logout(authId);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            response.put("code", 200);
            response.put("message", "Logout successful");
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 400);
            response.put("message", "Logout failed");
            return ResponseEntity.status(400).body(response);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, Object> request) {
        String username = (String) request.get("username");
        String password = (String) request.get("password");
        Long userId = ((Number) request.get("userId")).longValue();

        Auth auth = authService.register(username, password, userId);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 201);
        response.put("message", "Registration successful");
        response.put("data", auth);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/validate/{token}")
    public ResponseEntity<Map<String, Object>> validateToken(@PathVariable String token) {
        Auth auth = authService.validateToken(token);
        Map<String, Object> response = new HashMap<>();

        if (auth != null && auth.getIsActive()) {
            response.put("code", 200);
            response.put("message", "Token is valid");
            response.put("data", auth);
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 401);
            response.put("message", "Token is invalid or expired");
            return ResponseEntity.status(401).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getAuthById(@PathVariable Long id) {
        Auth auth = authService.getAuthById(id);
        Map<String, Object> response = new HashMap<>();

        if (auth != null) {
            response.put("code", 200);
            response.put("message", "Success");
            response.put("data", auth);
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Auth not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getAllAuth() {
        List<Auth> authList = authService.getAllAuth();
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", "Success");
        response.put("data", authList);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteAuth(@PathVariable Long id) {
        boolean success = authService.deleteAuth(id);
        Map<String, Object> response = new HashMap<>();

        if (success) {
            response.put("code", 200);
            response.put("message", "Delete successful");
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "Auth not found");
            return ResponseEntity.status(404).body(response);
        }
    }
}
