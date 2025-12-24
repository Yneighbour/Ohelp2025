package com.soft.ob.service.impl;

import com.soft.ob.entity.Laogong;
import com.soft.ob.repository.LaogongRepository;
import com.soft.ob.service.LaogongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LaogongServiceImpl implements LaogongService {
    
    @Autowired
    private LaogongRepository laogongRepository;
    
    @Override
    public List<Laogong> getAllLaogong() {
        return laogongRepository.findAll();
    }
    
    @Override
    public Optional<Laogong> getLaogongById(Long id) {
        return laogongRepository.findById(id);
    }
    
    @Override
    public Laogong saveLaogong(Laogong laogong) {
        laogong.setCreateTime(LocalDateTime.now());
        laogong.setUpdateTime(LocalDateTime.now());
        return laogongRepository.save(laogong);
    }
    
    @Override
    public Laogong updateLaogong(Long id, Laogong laogong) {
        Laogong existingLaogong = laogongRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Laogong not found with id: " + id));
        
        existingLaogong.setLaogongName(laogong.getLaogongName());
        existingLaogong.setLaogongPhone(laogong.getLaogongPhone());
        existingLaogong.setLaogongIdNumber(laogong.getLaogongIdNumber());
        existingLaogong.setLaogongDelete(laogong.getLaogongDelete());
        existingLaogong.setUpdateTime(LocalDateTime.now());
        
        return laogongRepository.save(existingLaogong);
    }
    
    @Override
    public void deleteLaogong(Long id) {
        Laogong laogong = laogongRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Laogong not found with id: " + id));
        laogongRepository.delete(laogong);
    }
    
    @Override
    public Optional<Laogong> findByUsername(String username) {
        return laogongRepository.findByUsername(username);
    }
    
    @Override
    public Optional<Laogong> findByLaogongUuid(String laogongUuid) {
        return laogongRepository.findByLaogongUuid(laogongUuid);
    }
    
    @Override
    public Laogong loginLaogong(String username, String password) {
        Optional<Laogong> laogongOpt = laogongRepository.findByUsername(username);
        if (laogongOpt.isPresent()) {
            Laogong laogong = laogongOpt.get();
            if (laogong.getPassword().equals(encryptPassword(password))) {
                laogong.setUpdateTime(LocalDateTime.now());
                return laogongRepository.save(laogong);
            }
        }
        throw new RuntimeException("Invalid username or password");
    }
    
    @Override
    public boolean changeLaogongPassword(Long id, String oldPassword, String newPassword) {
        Laogong laogong = laogongRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Laogong not found with id: " + id));
        
        if (!laogong.getPassword().equals(encryptPassword(oldPassword))) {
            throw new RuntimeException("Old password is incorrect");
        }
        
        laogong.setPassword(encryptPassword(newPassword));
        laogong.setUpdateTime(LocalDateTime.now());
        laogongRepository.save(laogong);
        return true;
    }
    
    // In real application, use proper password encryption
    private String encryptPassword(String password) {
        // This is a simple placeholder - in real app, use BCrypt or similar
        return password;
    }
}