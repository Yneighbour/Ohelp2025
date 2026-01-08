package com.soft.ob.user.service;

import com.soft.ob.user.entity.User;
import com.soft.ob.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User createUser(User user) {
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setIsActive(true);
        userMapper.insert(user);
        return user;
    }

    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }

    public User getUserByEmail(String email) {
        return userMapper.selectByEmail(email);
    }

    public User getUserByPhone(String phone) {
        return userMapper.selectByPhone(phone);
    }

    public List<User> getAllUsers() {
        return userMapper.selectAll();
    }

    public User updateUser(User user) {
        user.setUpdatedAt(LocalDateTime.now());
        userMapper.update(user);
        return user;
    }

    public boolean deleteUser(Long id) {
        return userMapper.deleteById(id) > 0;
    }

    public boolean activateUser(Long id) {
        User user = userMapper.selectById(id);
        if (user != null) {
            user.setIsActive(true);
            user.setUpdatedAt(LocalDateTime.now());
            userMapper.update(user);
            return true;
        }
        return false;
    }

    public boolean deactivateUser(Long id) {
        User user = userMapper.selectById(id);
        if (user != null) {
            user.setIsActive(false);
            user.setUpdatedAt(LocalDateTime.now());
            userMapper.update(user);
            return true;
        }
        return false;
    }
}
