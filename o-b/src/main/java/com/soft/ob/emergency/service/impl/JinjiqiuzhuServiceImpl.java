package com.soft.ob.emergency.service.impl;

import com.soft.ob.entity.Jinjiqiuzhu;
import com.soft.ob.repository.JinjiqiuzhuRepository;
import com.soft.ob.emergency.service.JinjiqiuzhuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class JinjiqiuzhuServiceImpl implements JinjiqiuzhuService {
    
    @Autowired
    private JinjiqiuzhuRepository jinjiqiuzhuRepository;
    
    @Override
    public List<Jinjiqiuzhu> getAllJinjiqiuzhu() {
        return jinjiqiuzhuRepository.findAll();
    }
    
    @Override
    public Optional<Jinjiqiuzhu> getJinjiqiuzhuById(Long id) {
        return jinjiqiuzhuRepository.findById(id);
    }
    
    @Override
    public Jinjiqiuzhu saveJinjiqiuzhu(Jinjiqiuzhu jinjiqiuzhu) {
        return jinjiqiuzhuRepository.save(jinjiqiuzhu);
    }
    
    @Override
    public Jinjiqiuzhu updateJinjiqiuzhu(Long id, Jinjiqiuzhu jinjiqiuzhu) {
        Jinjiqiuzhu existingJinjiqiuzhu = jinjiqiuzhuRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Jinjiqiuzhu not found with id: " + id));
        
        existingJinjiqiuzhu.setLaorenUuid(jinjiqiuzhu.getLaorenUuid());
        existingJinjiqiuzhu.setLaorenName(jinjiqiuzhu.getLaorenName());
        existingJinjiqiuzhu.setJinjiqiuzhuText(jinjiqiuzhu.getJinjiqiuzhuText());
        existingJinjiqiuzhu.setJinjiqiuzhuDelete(jinjiqiuzhu.getJinjiqiuzhuDelete());
        existingJinjiqiuzhu.setCreateTime(jinjiqiuzhu.getCreateTime());
        existingJinjiqiuzhu.setUpdateTime(jinjiqiuzhu.getUpdateTime());
        
        return jinjiqiuzhuRepository.save(existingJinjiqiuzhu);
    }
    
    @Override
    public void deleteJinjiqiuzhu(Long id) {
        Jinjiqiuzhu jinjiqiuzhu = jinjiqiuzhuRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Jinjiqiuzhu not found with id: " + id));
        jinjiqiuzhuRepository.delete(jinjiqiuzhu);
    }
    
    @Override
    public List<Jinjiqiuzhu> getByLaorenUuid(String laorenUuid) {
        return jinjiqiuzhuRepository.findByLaorenUuid(laorenUuid);
    }
}