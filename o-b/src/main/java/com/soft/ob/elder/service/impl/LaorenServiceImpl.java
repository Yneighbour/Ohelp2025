package com.soft.ob.elder.service.impl;

import com.soft.ob.entity.Laoren;
import com.soft.ob.repository.LaorenRepository;
import com.soft.ob.elder.service.LaorenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LaorenServiceImpl implements LaorenService {
    
    @Autowired
    private LaorenRepository laorenRepository;
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @Override
    public List<Laoren> getAllLaoren() {
        return laorenRepository.findAll();
    }
    
    @Override
    public Optional<Laoren> getLaorenById(Long id) {
        return laorenRepository.findById(id);
    }
    
    @Override
    public Laoren saveLaoren(Laoren laoren) {
        if (laoren.getLaorenUuid() == null || laoren.getLaorenUuid().isEmpty()) {
            laoren.setLaorenUuid(UUID.randomUUID().toString());
        }
        laoren.setPassword(passwordEncoder.encode(laoren.getPassword()));
        laoren.setCreateTime(LocalDateTime.now());
        laoren.setUpdateTime(LocalDateTime.now());
        return laorenRepository.save(laoren);
    }
    
    @Override
    public Laoren updateLaoren(Long id, Laoren laoren) {
        Laoren existingLaoren = laorenRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Laoren not found with id: " + id));
        
        existingLaoren.setUsername(laoren.getUsername());
        existingLaoren.setName(laoren.getName());
        existingLaoren.setAge(laoren.getAge());
        existingLaoren.setGender(laoren.getGender());
        existingLaoren.setPhone(laoren.getPhone());
        existingLaoren.setIdNumber(laoren.getIdNumber());
        existingLaoren.setAddress(laoren.getAddress());
        existingLaoren.setAvatar(laoren.getAvatar());
        existingLaoren.setEmergencyContactName(laoren.getEmergencyContactName());
        existingLaoren.setEmergencyContactPhone(laoren.getEmergencyContactPhone());
        existingLaoren.setHealthStatus(laoren.getHealthStatus());
        existingLaoren.setSpecialMedicalNeeds(laoren.getSpecialMedicalNeeds());
        existingLaoren.setIsActive(laoren.getIsActive());
        existingLaoren.setUpdateTime(LocalDateTime.now());
        
        return laorenRepository.save(existingLaoren);
    }
    
    @Override
    public void deleteLaoren(Long id) {
        Laoren laoren = laorenRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Laoren not found with id: " + id));
        laorenRepository.delete(laoren);
    }
    
    @Override
    public Optional<Laoren> findByUsername(String username) {
        return laorenRepository.findByUsername(username);
    }
    
    @Override
    public Optional<Laoren> findByLaorenUuid(String laorenUuid) {
        return laorenRepository.findByLaorenUuid(laorenUuid);
    }
    
    @Override
    public Laoren registerLaoren(Laoren laoren) {
        if (laorenRepository.existsByUsername(laoren.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        
        if (laoren.getLaorenUuid() == null || laoren.getLaorenUuid().isEmpty()) {
            laoren.setLaorenUuid(UUID.randomUUID().toString());
        }
        
        laoren.setPassword(passwordEncoder.encode(laoren.getPassword()));
        laoren.setCreateTime(LocalDateTime.now());
        laoren.setUpdateTime(LocalDateTime.now());
        laoren.setIsActive(true);
        
        return laorenRepository.save(laoren);
    }
    
    @Override
    public Laoren loginLaoren(String username, String password) {
        Laoren laoren = laorenRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Invalid username or password"));
        
        if (!passwordEncoder.matches(password, laoren.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }
        
        if (!laoren.getIsActive()) {
            throw new RuntimeException("Account is disabled");
        }
        
        laoren.setUpdateTime(LocalDateTime.now());
        laorenRepository.save(laoren);
        
        return laoren;
    }
    
    @Override
    public boolean changeLaorenPassword(Long id, String oldPassword, String newPassword) {
        Laoren laoren = laorenRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Laoren not found with id: " + id));
        
        if (!passwordEncoder.matches(oldPassword, laoren.getPassword())) {
            return false;
        }
        
        laoren.setPassword(passwordEncoder.encode(newPassword));
        laoren.setUpdateTime(LocalDateTime.now());
        laorenRepository.save(laoren);
        
        return true;
    }
    
    @Override
    public Laoren updateLaorenInfo(Long id, Laoren laoren) {
        Laoren existingLaoren = laorenRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Laoren not found with id: " + id));
        
        existingLaoren.setName(laoren.getName());
        existingLaoren.setAge(laoren.getAge());
        existingLaoren.setGender(laoren.getGender());
        existingLaoren.setPhone(laoren.getPhone());
        existingLaoren.setIdNumber(laoren.getIdNumber());
        existingLaoren.setAddress(laoren.getAddress());
        existingLaoren.setAvatar(laoren.getAvatar());
        existingLaoren.setEmergencyContactName(laoren.getEmergencyContactName());
        existingLaoren.setEmergencyContactPhone(laoren.getEmergencyContactPhone());
        existingLaoren.setHealthStatus(laoren.getHealthStatus());
        existingLaoren.setSpecialMedicalNeeds(laoren.getSpecialMedicalNeeds());
        existingLaoren.setUpdateTime(LocalDateTime.now());
        
        return laorenRepository.save(existingLaoren);
    }
}