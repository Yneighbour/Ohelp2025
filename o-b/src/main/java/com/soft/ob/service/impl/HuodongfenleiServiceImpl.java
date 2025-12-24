package com.soft.ob.service.impl;

import com.soft.ob.entity.Huodongfenlei;
import com.soft.ob.repository.HuodongfenleiRepository;
import com.soft.ob.service.HuodongfenleiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class HuodongfenleiServiceImpl implements HuodongfenleiService {
    
    @Autowired
    private HuodongfenleiRepository huodongfenleiRepository;
    
    @Override
    public List<Huodongfenlei> getAllHuodongfenlei() {
        return huodongfenleiRepository.findAll();
    }
    
    @Override
    public Optional<Huodongfenlei> getHuodongfenleiById(Long id) {
        return huodongfenleiRepository.findById(id);
    }
    
    @Override
    public Huodongfenlei saveHuodongfenlei(Huodongfenlei huodongfenlei) {
        huodongfenlei.setCreateTime(LocalDateTime.now());
        huodongfenlei.setUpdateTime(LocalDateTime.now());
        return huodongfenleiRepository.save(huodongfenlei);
    }
    
    @Override
    public Huodongfenlei updateHuodongfenlei(Long id, Huodongfenlei huodongfenlei) {
        Huodongfenlei existingHuodongfenlei = huodongfenleiRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Huodongfenlei not found with id: " + id));
        
        existingHuodongfenlei.setFenleiName(huodongfenlei.getFenleiName());
        existingHuodongfenlei.setFenleiImg(huodongfenlei.getFenleiImg());
        existingHuodongfenlei.setShangxia(huodongfenlei.getShangxia());
        existingHuodongfenlei.setUpdateTime(LocalDateTime.now());
        
        return huodongfenleiRepository.save(existingHuodongfenlei);
    }
    
    @Override
    public void deleteHuodongfenlei(Long id) {
        Huodongfenlei huodongfenlei = huodongfenleiRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Huodongfenlei not found with id: " + id));
        huodongfenleiRepository.delete(huodongfenlei);
    }
}