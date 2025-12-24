package com.soft.ob.service;

import com.soft.ob.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User saveUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    User registerUser(User user);
    User loginUser(String username, String password);
    boolean changePassword(Long id, String oldPassword, String newPassword);
    User updateUserInfo(Long id, User user);
}