package com.soft.ob.controller;

import com.soft.ob.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/token")
@CrossOrigin(origins = "*")
public class TokenController {
    
    @Autowired
    private TokenService tokenService;
    
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createToken(@RequestParam String userId, @RequestParam String userType) {
        Map<String, Object> response = new HashMap<>();
        try {
            com.soft.ob.entity.Token token = tokenService.createToken(userId, userType);
            response.put("success", true);
            response.put("token", token.getToken());
            response.put("userId", token.getUserId());
            response.put("userType", token.getUserType());
            response.put("expireTime", token.getExpireTime());
            response.put("message", "Token created successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Failed to create token: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestParam String tokenValue) {
        Map<String, Object> response = new HashMap<>();
        boolean isValid = tokenService.validateToken(tokenValue);
        response.put("valid", isValid);
        if (isValid) {
            response.put("message", "Token is valid");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Token is invalid or expired");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
    
    @PostMapping("/invalidate")
    public ResponseEntity<Map<String, Object>> invalidateToken(@RequestParam String tokenValue) {
        Map<String, Object> response = new HashMap<>();
        try {
            tokenService.invalidateToken(tokenValue);
            response.put("success", true);
            response.put("message", "Token invalidated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Failed to invalidate token: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<Map<String, Object>> refreshToken(@RequestParam String tokenValue) {
        Map<String, Object> response = new HashMap<>();
        try {
            com.soft.ob.entity.Token token = tokenService.refreshToken(tokenValue);
            response.put("success", true);
            response.put("token", token.getToken());
            response.put("expireTime", token.getExpireTime());
            response.put("message", "Token refreshed successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Failed to refresh token: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}