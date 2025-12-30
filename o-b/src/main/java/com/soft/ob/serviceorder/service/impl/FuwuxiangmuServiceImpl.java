package com.soft.ob.serviceorder.service.impl;

import com.soft.ob.entity.Fuwuxiangmu;
import com.soft.ob.repository.FuwuxiangmuRepository;
import com.soft.ob.serviceorder.service.FuwuxiangmuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FuwuxiangmuServiceImpl implements FuwuxiangmuService {
    
    @Autowired
    private FuwuxiangmuRepository fuwuxiangmuRepository;
    
    @Override
    public List<Fuwuxiangmu> getAllFuwuxiangmu() {
        return fuwuxiangmuRepository.findAll();
    }
    
    @Override
    public Optional<Fuwuxiangmu> getFuwuxiangmuById(Long id) {
        return fuwuxiangmuRepository.findById(id);
    }
    
    @Override
    public Fuwuxiangmu saveFuwuxiangmu(Fuwuxiangmu fuwuxiangmu) {
        fuwuxiangmu.setCreateTime(LocalDateTime.now());
        fuwuxiangmu.setUpdateTime(LocalDateTime.now());
        return fuwuxiangmuRepository.save(fuwuxiangmu);
    }
    
    @Override
    public Fuwuxiangmu updateFuwuxiangmu(Long id, Fuwuxiangmu fuwuxiangmu) {
        Fuwuxiangmu existingFuwuxiangmu = fuwuxiangmuRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Fuwuxiangmu not found with id: " + id));
        
        existingFuwuxiangmu.setFuwuleixingId(fuwuxiangmu.getFuwuleixingId());
        existingFuwuxiangmu.setXiangmuName(fuwuxiangmu.getXiangmuName());
        existingFuwuxiangmu.setXiangmuPhoto(fuwuxiangmu.getXiangmuPhoto());
        existingFuwuxiangmu.setXiangmuNewsPrice(fuwuxiangmu.getXiangmuNewsPrice());
        existingFuwuxiangmu.setXiangmuNumber(fuwuxiangmu.getXiangmuNumber());
        existingFuwuxiangmu.setXiangmuDesc(fuwuxiangmu.getXiangmuDesc());
        existingFuwuxiangmu.setShangxia(fuwuxiangmu.getShangxia());
        existingFuwuxiangmu.setUpdateTime(LocalDateTime.now());
        
        return fuwuxiangmuRepository.save(existingFuwuxiangmu);
    }
    
    @Override
    public void deleteFuwuxiangmu(Long id) {
        Fuwuxiangmu fuwuxiangmu = fuwuxiangmuRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Fuwuxiangmu not found with id: " + id));
        fuwuxiangmuRepository.delete(fuwuxiangmu);
    }
    
    @Override
    public List<Fuwuxiangmu> getByFuwuleixingId(Long fuwuleixingId) {
        return fuwuxiangmuRepository.findByFuwuleixingId(fuwuleixingId);
    }
    
    @Override
    public List<Fuwuxiangmu> getByShangxia(Integer shangxia) {
        return fuwuxiangmuRepository.findByShangxia(shangxia);
    }
}
