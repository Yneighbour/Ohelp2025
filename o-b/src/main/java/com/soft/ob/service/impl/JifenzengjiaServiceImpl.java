package com.soft.ob.service.impl;

import com.soft.ob.entity.Jifenzengjia;
import com.soft.ob.repository.JifenzengjiaRepository;
import com.soft.ob.service.JifenzengjiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JifenzengjiaServiceImpl implements JifenzengjiaService {
    
    @Autowired
    private JifenzengjiaRepository jifenzengjiaRepository;
    
    @Override
    public List<Jifenzengjia> getAllJifenzengjia() {
        return jifenzengjiaRepository.findAll();
    }
    
    @Override
    public Optional<Jifenzengjia> getJifenzengjiaById(Long id) {
        return jifenzengjiaRepository.findById(id);
    }
    
    @Override
    public Jifenzengjia saveJifenzengjia(Jifenzengjia jifenzengjia) {
        jifenzengjia.setCreateTime(LocalDateTime.now());
        jifenzengjia.setUpdateTime(LocalDateTime.now());
        return jifenzengjiaRepository.save(jifenzengjia);
    }
    
    @Override
    public Jifenzengjia updateJifenzengjia(Long id, Jifenzengjia jifenzengjia) {
        Jifenzengjia existingJifenzengjia = jifenzengjiaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Jifenzengjia not found with id: " + id));
        
        existingJifenzengjia.setLaorenUuid(jifenzengjia.getLaorenUuid());
        existingJifenzengjia.setLaorenName(jifenzengjia.getLaorenName());
        existingJifenzengjia.setJifenName(jifenzengjia.getJifenName());
        existingJifenzengjia.setJifenNumber(jifenzengjia.getJifenNumber());
        existingJifenzengjia.setJifenContent(jifenzengjia.getJifenContent());
        existingJifenzengjia.setJifenDelete(jifenzengjia.getJifenDelete());
        existingJifenzengjia.setUpdateTime(LocalDateTime.now());
        
        return jifenzengjiaRepository.save(existingJifenzengjia);
    }
    
    @Override
    public void deleteJifenzengjia(Long id) {
        Jifenzengjia jifenzengjia = jifenzengjiaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Jifenzengjia not found with id: " + id));
        jifenzengjiaRepository.delete(jifenzengjia);
    }
    
    @Override
    public List<Jifenzengjia> getByLaorenUuid(String laorenUuid) {
        return jifenzengjiaRepository.findByLaorenUuid(laorenUuid);
    }
}