package com.soft.ob.service.impl;

import com.soft.ob.entity.Meirijiankang;
import com.soft.ob.repository.MeirijiankangRepository;
import com.soft.ob.service.MeirijiankangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MeirijiankangServiceImpl implements MeirijiankangService {
    
    @Autowired
    private MeirijiankangRepository meirijiankangRepository;
    
    @Override
    public List<Meirijiankang> getAllMeirijiankang() {
        return meirijiankangRepository.findAll();
    }
    
    @Override
    public Optional<Meirijiankang> getMeirijiankangById(Long id) {
        return meirijiankangRepository.findById(id);
    }
    
    @Override
    public Meirijiankang saveMeirijiankang(Meirijiankang meirijiankang) {
        meirijiankang.setCreateTime(LocalDateTime.now());
        meirijiankang.setUpdateTime(LocalDateTime.now());
        return meirijiankangRepository.save(meirijiankang);
    }
    
    @Override
    public Meirijiankang updateMeirijiankang(Long id, Meirijiankang meirijiankang) {
        Meirijiankang existingMeirijiankang = meirijiankangRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Meirijiankang not found with id: " + id));
        
        existingMeirijiankang.setJiankangName(meirijiankang.getJiankangName());
        existingMeirijiankang.setJiankangPhoto(meirijiankang.getJiankangPhoto());
        existingMeirijiankang.setJiankangContent(meirijiankang.getJiankangContent());
        existingMeirijiankang.setJiankangDelete(meirijiankang.getJiankangDelete());
        existingMeirijiankang.setUpdateTime(LocalDateTime.now());
        
        return meirijiankangRepository.save(existingMeirijiankang);
    }
    
    @Override
    public void deleteMeirijiankang(Long id) {
        Meirijiankang meirijiankang = meirijiankangRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Meirijiankang not found with id: " + id));
        meirijiankangRepository.delete(meirijiankang);
    }
}