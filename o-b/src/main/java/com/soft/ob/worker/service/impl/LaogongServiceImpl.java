package com.soft.ob.worker.service.impl;

import com.soft.ob.entity.Laogong;
import com.soft.ob.repository.LaogongRepository;
import com.soft.ob.worker.service.LaogongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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
        return laogongRepository.save(laogong);
    }
    
    @Override
    public Laogong updateLaogong(Long id, Laogong laogong) {
        Laogong existingLaogong = laogongRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Laogong not found with id: " + id));
        
        existingLaogong.setLaogongUuid(laogong.getLaogongUuid());
        existingLaogong.setUsername(laogong.getUsername());
        existingLaogong.setPassword(laogong.getPassword());
        existingLaogong.setLaogongName(laogong.getLaogongName());
        existingLaogong.setLaogongPhone(laogong.getLaogongPhone());
        existingLaogong.setLaogongIdNumber(laogong.getLaogongIdNumber());
        existingLaogong.setLaogongDelete(laogong.getLaogongDelete());
        existingLaogong.setCreateTime(laogong.getCreateTime());
        existingLaogong.setUpdateTime(laogong.getUpdateTime());
        
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
            if (laogong.getPassword().equals(password)) {
                return laogong;
            } else {
                throw new RuntimeException("Invalid password");
            }
        } else {
            throw new RuntimeException("Invalid username");
        }
    }
    
    @Override
    public boolean changeLaogongPassword(Long id, String oldPassword, String newPassword) {
        Optional<Laogong> laogongOpt = laogongRepository.findById(id);
        if (laogongOpt.isPresent()) {
            Laogong laogong = laogongOpt.get();
            if (laogong.getPassword().equals(oldPassword)) {
                laogong.setPassword(newPassword);
                laogongRepository.save(laogong);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}