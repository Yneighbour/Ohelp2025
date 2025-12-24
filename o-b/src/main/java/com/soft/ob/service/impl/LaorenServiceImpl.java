package com.soft.ob.service.impl;

import com.soft.ob.entity.Laoren;
import com.soft.ob.repository.LaorenRepository;
import com.soft.ob.service.LaorenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LaorenServiceImpl implements LaorenService {
    
    @Autowired
    private LaorenRepository laorenRepository;
    
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
        laoren.setCreateTime(LocalDateTime.now());
        laoren.setUpdateTime(LocalDateTime.now());
        return laorenRepository.save(laoren);
    }
    
    @Override
    public Laoren updateLaoren(Long id, Laoren laoren) {
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
        existingLaoren.setAdmissionDate(laoren.getAdmissionDate());
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
            throw new RuntimeException("Username already exists: " + laoren.getUsername());
        }
        
        // Generate unique UUID for the elderly person
        laoren.setLaorenUuid(generateLaorenUuid());
        laoren.setPassword(encryptPassword(laoren.getPassword())); // In real app, use proper encryption
        laoren.setCreateTime(LocalDateTime.now());
        laoren.setUpdateTime(LocalDateTime.now());
        laoren.setIsActive(true);
        return laorenRepository.save(laoren);
    }
    
    @Override
    public Laoren loginLaoren(String username, String password) {
        Optional<Laoren> laorenOpt = laorenRepository.findByUsername(username);
        if (laorenOpt.isPresent()) {
            Laoren laoren = laorenOpt.get();
            if (laoren.getPassword().equals(encryptPassword(password)) && laoren.getIsActive()) {
                laoren.setUpdateTime(LocalDateTime.now());
                return laorenRepository.save(laoren);
            }
        }
        throw new RuntimeException("Invalid username or password");
    }
    
    @Override
    public boolean changeLaorenPassword(Long id, String oldPassword, String newPassword) {
        Laoren laoren = laorenRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Laoren not found with id: " + id));
        
        if (!laoren.getPassword().equals(encryptPassword(oldPassword))) {
            throw new RuntimeException("Old password is incorrect");
        }
        
        laoren.setPassword(encryptPassword(newPassword));
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
        existingLaoren.setUpdateTime(LocalDateTime.now());
        
        return laorenRepository.save(existingLaoren);
    }
    
    // In real application, use proper password encryption
    private String encryptPassword(String password) {
        // This is a simple placeholder - in real app, use BCrypt or similar
        return password;
    }
    
    // Generate a unique identifier for elderly person
    private String generateLaorenUuid() {
        return "LR" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}