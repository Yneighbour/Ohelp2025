package com.soft.ob.auth.service;

import com.soft.ob.auth.entity.Auth;
import com.soft.ob.auth.mapper.AuthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private AuthMapper authMapper;

    public Auth login(String username, String password) {
        Auth auth = authMapper.selectByUsername(username);
        if (auth != null && auth.getPassword().equals(password)) {
            auth.setToken(UUID.randomUUID().toString());
            auth.setLoginTime(LocalDateTime.now());
            auth.setIsActive(true);
            auth.setUpdatedAt(LocalDateTime.now());
            authMapper.update(auth);
            return auth;
        }
        return null;
    }

    public boolean logout(Long authId) {
        Auth auth = authMapper.selectById(authId);
        if (auth != null) {
            auth.setLogoutTime(LocalDateTime.now());
            auth.setIsActive(false);
            auth.setUpdatedAt(LocalDateTime.now());
            authMapper.update(auth);
            return true;
        }
        return false;
    }

    public Auth register(String username, String password, Long userId) {
        Auth auth = new Auth();
        auth.setUsername(username);
        auth.setPassword(password);
        auth.setToken(UUID.randomUUID().toString());
        auth.setUserId(userId);
        auth.setIsActive(true);
        auth.setCreatedAt(LocalDateTime.now());
        auth.setUpdatedAt(LocalDateTime.now());
        authMapper.insert(auth);
        return auth;
    }

    public Auth validateToken(String token) {
        return authMapper.selectByToken(token);
    }

    public Auth getAuthById(Long id) {
        return authMapper.selectById(id);
    }

    public List<Auth> getAllAuth() {
        return authMapper.selectAll();
    }

    public boolean deleteAuth(Long id) {
        return authMapper.deleteById(id) > 0;
    }
}
