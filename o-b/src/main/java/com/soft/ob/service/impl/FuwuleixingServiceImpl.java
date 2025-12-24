package com.soft.ob.service.impl;

import com.soft.ob.entity.Fuwuleixing;
import com.soft.ob.repository.FuwuleixingRepository;
import com.soft.ob.service.FuwuleixingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FuwuleixingServiceImpl implements FuwuleixingService {
    
    @Autowired
    private FuwuleixingRepository fuwuleixingRepository;
    
    @Override
    public List<Fuwuleixing> getAllFuwuleixing() {
        return fuwuleixingRepository.findAll();
    }
    
    @Override
    public Optional<Fuwuleixing> getFuwuleixingById(Long id) {
        return fuwuleixingRepository.findById(id);
    }
    
    @Override
    public Fuwuleixing saveFuwuleixing(Fuwuleixing fuwuleixing) {
        fuwuleixing.setCreateTime(LocalDateTime.now());
        fuwuleixing.setUpdateTime(LocalDateTime.now());
        return fuwuleixingRepository.save(fuwuleixing);
    }
    
    @Override
    public Fuwuleixing updateFuwuleixing(Long id, Fuwuleixing fuwuleixing) {
        Fuwuleixing existingFuwuleixing = fuwuleixingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Fuwuleixing not found with id: " + id));
        
        existingFuwuleixing.setLeixingName(fuwuleixing.getLeixingName());
        existingFuwuleixing.setLeixingDesc(fuwuleixing.getLeixingDesc());
        existingFuwuleixing.setLeixingImg(fuwuleixing.getLeixingImg());
        existingFuwuleixing.setShangxia(fuwuleixing.getShangxia());
        existingFuwuleixing.setUpdateTime(LocalDateTime.now());
        
        return fuwuleixingRepository.save(existingFuwuleixing);
    }
    
    @Override
    public void deleteFuwuleixing(Long id) {
        Fuwuleixing fuwuleixing = fuwuleixingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Fuwuleixing not found with id: " + id));
        fuwuleixingRepository.delete(fuwuleixing);
    }
}