package com.soft.ob.elder.service.impl;

import com.soft.ob.entity.Elderly;
import com.soft.ob.repository.ElderlyRepository;
import com.soft.ob.elder.service.ElderlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ElderlyServiceImpl implements ElderlyService {
    
    @Autowired
    private ElderlyRepository elderlyRepository;
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @Override
    public List<Elderly> getAllElderly() {
        return elderlyRepository.findAll();
    }
    
    @Override
    public Optional<Elderly> getElderlyById(Long id) {
        return elderlyRepository.findById(id);
    }
    
    @Override
    public Elderly saveElderly(Elderly elderly) {
        if (elderly.getUuid() == null || elderly.getUuid().isEmpty()) {
            elderly.setUuid(UUID.randomUUID().toString());
        }
        if (elderly.getCreateTime() == null) {
            elderly.setCreateTime(LocalDateTime.now());
        }
        elderly.setUpdateTime(LocalDateTime.now());
        return elderlyRepository.save(elderly);
    }
    
    @Override
    public Elderly updateElderly(Long id, Elderly elderlyDetails) {
        Elderly elderly = elderlyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Elderly not found with id: " + id));
        
        elderly.setName(elderlyDetails.getName());
        elderly.setAge(elderlyDetails.getAge());
        elderly.setGender(elderlyDetails.getGender());
        elderly.setPhone(elderlyDetails.getPhone());
        elderly.setAddress(elderlyDetails.getAddress());
        elderly.setBirthDate(elderlyDetails.getBirthDate());
        elderly.setEmergencyContactName(elderlyDetails.getEmergencyContactName());
        elderly.setEmergencyContactPhone(elderlyDetails.getEmergencyContactPhone());
        elderly.setHealthStatus(elderlyDetails.getHealthStatus());
        elderly.setSpecialMedicalNeeds(elderlyDetails.getSpecialMedicalNeeds());
        elderly.setRoomNumber(elderlyDetails.getRoomNumber());
        elderly.setAdmissionDate(elderlyDetails.getAdmissionDate());
        elderly.setNotes(elderlyDetails.getNotes());
        elderly.setUpdateTime(LocalDateTime.now());
        
        return elderlyRepository.save(elderly);
    }
    
    @Override
    public void deleteElderly(Long id) {
        Elderly elderly = elderlyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Elderly not found with id: " + id));
        elderly.setIsActive(false); // Soft delete
        elderlyRepository.save(elderly);
    }
    
    @Override
    public List<Elderly> getActiveElderly() {
        return elderlyRepository.findByIsActiveTrue();
    }
    
    @Override
    public List<Elderly> searchElderlyByName(String name) {
        return elderlyRepository.findByNameContaining(name);
    }
    
    @Override
    public Optional<Elderly> findByUsername(String username) {
        return elderlyRepository.findByUsername(username);
    }
    
    @Override
    public Optional<Elderly> findByUuid(String uuid) {
        return elderlyRepository.findByUuid(uuid);
    }
    
    @Override
    public Elderly registerElderly(Elderly elderly) {
        if (elderlyRepository.existsByUsername(elderly.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        
        if (elderly.getUuid() == null || elderly.getUuid().isEmpty()) {
            elderly.setUuid(UUID.randomUUID().toString());
        }
        
        elderly.setPassword(passwordEncoder.encode(elderly.getPassword()));
        elderly.setCreateTime(LocalDateTime.now());
        elderly.setUpdateTime(LocalDateTime.now());
        elderly.setIsActive(true);
        
        return elderlyRepository.save(elderly);
    }
    
    @Override
    public Elderly loginElderly(String username, String password) {
        Elderly elderly = elderlyRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Invalid username or password"));
        
        if (!passwordEncoder.matches(password, elderly.getPassword())) {
            // 增加登录尝试次数
            elderly.setLoginAttempts(elderly.getLoginAttempts() + 1);
            if (elderly.getLoginAttempts() >= 5) {
                elderly.setLockedUntil(LocalDateTime.now().plusMinutes(30)); // 锁定30分钟
            }
            elderlyRepository.save(elderly);
            throw new RuntimeException("Invalid username or password");
        }
        
        if (!elderly.getIsActive()) {
            throw new RuntimeException("Account is disabled");
        }
        
        if (elderly.getLockedUntil() != null && elderly.getLockedUntil().isAfter(LocalDateTime.now())) {
            throw new RuntimeException("Account is temporarily locked due to multiple failed login attempts");
        }
        
        // 登录成功，重置尝试次数和更新时间
        elderly.setLoginAttempts(0);
        elderly.setLockedUntil(null);
        elderly.setLastLoginTime(LocalDateTime.now());
        elderly.setUpdateTime(LocalDateTime.now());
        elderlyRepository.save(elderly);
        
        return elderly;
    }
    
    @Override
    public boolean changeElderlyPassword(Long id, String oldPassword, String newPassword) {
        Elderly elderly = elderlyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Elderly not found with id: " + id));
        
        if (!passwordEncoder.matches(oldPassword, elderly.getPassword())) {
            return false;
        }
        
        elderly.setPassword(passwordEncoder.encode(newPassword));
        elderly.setUpdateTime(LocalDateTime.now());
        elderlyRepository.save(elderly);
        
        return true;
    }
    
    @Override
    public Elderly updateElderlyInfo(Long id, Elderly elderly) {
        Elderly existingElderly = elderlyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Elderly not found with id: " + id));
        
        existingElderly.setName(elderly.getName());
        existingElderly.setAge(elderly.getAge());
        existingElderly.setGender(elderly.getGender());
        existingElderly.setPhone(elderly.getPhone());
        existingElderly.setIdNumber(elderly.getIdNumber());
        existingElderly.setAddress(elderly.getAddress());
        existingElderly.setAvatar(elderly.getAvatar());
        existingElderly.setEmergencyContactName(elderly.getEmergencyContactName());
        existingElderly.setEmergencyContactPhone(elderly.getEmergencyContactPhone());
        existingElderly.setHealthStatus(elderly.getHealthStatus());
        existingElderly.setSpecialMedicalNeeds(elderly.getSpecialMedicalNeeds());
        existingElderly.setUpdateTime(LocalDateTime.now());
        
        return elderlyRepository.save(existingElderly);
    }
}