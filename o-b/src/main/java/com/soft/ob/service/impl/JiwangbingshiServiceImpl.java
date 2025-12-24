package com.soft.ob.service.impl;

import com.soft.ob.entity.Jiwangbingshi;
import com.soft.ob.repository.JiwangbingshiRepository;
import com.soft.ob.service.JiwangbingshiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JiwangbingshiServiceImpl implements JiwangbingshiService {
    
    @Autowired
    private JiwangbingshiRepository jiwangbingshiRepository;
    
    @Override
    public List<Jiwangbingshi> getAllJiwangbingshi() {
        return jiwangbingshiRepository.findAll();
    }
    
    @Override
    public Optional<Jiwangbingshi> getJiwangbingshiById(Long id) {
        return jiwangbingshiRepository.findById(id);
    }
    
    @Override
    public Jiwangbingshi saveJiwangbingshi(Jiwangbingshi jiwangbingshi) {
        jiwangbingshi.setCreateTime(LocalDateTime.now());
        jiwangbingshi.setUpdateTime(LocalDateTime.now());
        return jiwangbingshiRepository.save(jiwangbingshi);
    }
    
    @Override
    public Jiwangbingshi updateJiwangbingshi(Long id, Jiwangbingshi jiwangbingshi) {
        Jiwangbingshi existingJiwangbingshi = jiwangbingshiRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Jiwangbingshi not found with id: " + id));
        
        existingJiwangbingshi.setLaorenUuid(jiwangbingshi.getLaorenUuid());
        existingJiwangbingshi.setLaorenName(jiwangbingshi.getLaorenName());
        existingJiwangbingshi.setBingshiName(jiwangbingshi.getBingshiName());
        existingJiwangbingshi.setBingshiContent(jiwangbingshi.getBingshiContent());
        existingJiwangbingshi.setBingshiDelete(jiwangbingshi.getBingshiDelete());
        existingJiwangbingshi.setUpdateTime(LocalDateTime.now());
        
        return jiwangbingshiRepository.save(existingJiwangbingshi);
    }
    
    @Override
    public void deleteJiwangbingshi(Long id) {
        Jiwangbingshi jiwangbingshi = jiwangbingshiRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Jiwangbingshi not found with id: " + id));
        jiwangbingshiRepository.delete(jiwangbingshi);
    }
    
    @Override
    public List<Jiwangbingshi> getByLaorenUuid(String laorenUuid) {
        return jiwangbingshiRepository.findByLaorenUuid(laorenUuid);
    }
}