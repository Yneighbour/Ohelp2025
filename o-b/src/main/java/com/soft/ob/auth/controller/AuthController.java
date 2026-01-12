package com.soft.ob.auth.controller;

import com.soft.ob.auth.entity.Auth;
import com.soft.ob.auth.service.AuthService;
import com.soft.ob.user.entity.User;
import com.soft.ob.user.mapper.UserMapper;
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

    @Autowired
    private UserMapper userMapper;

    private String resolveUserRole(Long userId) {
        if (userId == null) return null;
        User user = userMapper.selectById(userId);
        return user != null ? user.getRole() : null;
    }

    private Map<String, Object> buildAuthLoginData(Auth auth) {
        if (auth == null) return null;
        Map<String, Object> data = new HashMap<>();
        data.put("id", auth.getId());
        data.put("username", auth.getUsername());
        data.put("token", auth.getToken());
        data.put("userId", auth.getUserId());
        data.put("role", resolveUserRole(auth.getUserId()));
        return data;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        Auth auth = authService.login(username, password);
        Map<String, Object> response = new HashMap<>();

        if (auth != null) {
            response.put("code", 200);
            response.put("message", "Login successful");
            response.put("data", buildAuthLoginData(auth));
            return ResponseEntity.ok(response);
        } else {
            // 演示模式：不返回 HTTP 401，避免前端拦截器按未授权处理。
            // 失败用 message 提示，data 置空；前端登录页会根据 data 是否完整自行报错。
            response.put("code", 200);
            response.put("message", "用户名或密码错误");
            response.put("data", null);
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(@RequestParam Long authId) {
        boolean success = authService.logout(authId);
        Map<String, Object> response = new HashMap<>();

        // 演示模式：统一 HTTP 200，避免前端把 4xx 当成系统错误。
        response.put("code", 200);
        response.put("message", success ? "Logout successful" : "Logout failed");
        response.put("data", Map.of("success", success));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, Object> request) {
        String username = (String) request.get("username");
        String password = (String) request.get("password");
        Number userIdNumber = (Number) request.get("userId");
        Long userId = userIdNumber != null ? userIdNumber.longValue() : null;

        Map<String, Object> response = new HashMap<>();
        if (username == null || username.isBlank() || password == null || password.isBlank() || userId == null) {
            // 演示模式：参数不完整也不抛 4xx
            response.put("code", 200);
            response.put("message", "Registration failed: missing username/password/userId");
            response.put("data", null);
            return ResponseEntity.ok(response);
        }

        Auth auth = authService.register(username, password, userId);
        response.put("code", 201);
        response.put("message", "Registration successful");
        response.put("data", buildAuthLoginData(auth));
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/validate/{token}")
    public ResponseEntity<Map<String, Object>> validateToken(@PathVariable String token) {
        Auth auth = authService.validateToken(token);
        Map<String, Object> response = new HashMap<>();

        boolean valid = auth != null && Boolean.TRUE.equals(auth.getIsActive());
        Map<String, Object> data = new HashMap<>();
        data.put("valid", valid);
        data.put("userId", auth != null ? auth.getUserId() : null);
        data.put("username", auth != null ? auth.getUsername() : null);
        data.put("role", auth != null ? resolveUserRole(auth.getUserId()) : null);

        // 演示模式：永远 HTTP 200 + code=200，不做“权限裁决/拦截”。
        response.put("code", 200);
        response.put("message", valid ? "Token is valid" : "Token is invalid or expired (demo mode: not blocking)");
        response.put("data", data);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getAuthById(@PathVariable Long id) {
        Auth auth = authService.getAuthById(id);
        Map<String, Object> response = new HashMap<>();

        response.put("code", 200);
        response.put("message", auth != null ? "Success" : "Auth not found");
        response.put("data", auth);
        return ResponseEntity.ok(response);
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

        response.put("code", 200);
        response.put("message", success ? "Delete successful" : "Auth not found");
        response.put("data", Map.of("success", success));
        return ResponseEntity.ok(response);
    }
}
