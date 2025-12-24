package com.soft.ob.service.impl;

import com.soft.ob.entity.Huodongxinxi;
import com.soft.ob.repository.HuodongxinxiRepository;
import com.soft.ob.service.HuodongxinxiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class HuodongxinxiServiceImpl implements HuodongxinxiService {
    
    @Autowired
    private HuodongxinxiRepository huodongxinxiRepository;
    
    @Override
    public List<Huodongxinxi> getAllHuodongxinxi() {
        return huodongxinxiRepository.findAll();
    }
    
    @Override
    public Optional<Huodongxinxi> getHuodongxinxiById(Long id) {
        return huodongxinxiRepository.findById(id);
    }
    
    @Override
    public Huodongxinxi saveHuodongxinxi(Huodongxinxi huodongxinxi) {
        huodongxinxi.setCreateTime(LocalDateTime.now());
        huodongxinxi.setUpdateTime(LocalDateTime.now());
        return huodongxinxiRepository.save(huodongxinxi);
    }
    
    @Override
    public Huodongxinxi updateHuodongxinxi(Long id, Huodongxinxi huodongxinxi) {
        Huodongxinxi existingHuodongxinxi = huodongxinxiRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Huodongxinxi not found with id: " + id));
        
        existingHuodongxinxi.setHuodongName(huodongxinxi.getHuodongName());
        existingHuodongxinxi.setHuodongPhoto(huodongxinxi.getHuodongPhoto());
        existingHuodongxinxi.setHuodongAddress(huodongxinxi.getHuodongAddress());
        existingHuodongxinxi.setHuodongTime(huodongxinxi.getHuodongTime());
        existingHuodongxinxi.setHuodongMoney(huodongxinxi.getHuodongMoney());
        existingHuodongxinxi.setHuodongContent(huodongxinxi.getHuodongContent());
        existingHuodongxinxi.setHuodongfenleiId(huodongxinxi.getHuodongfenleiId());
        existingHuodongxinxi.setHuodongYesno(huodongxinxi.getHuodongYesno());
        existingHuodongxinxi.setUpdateTime(LocalDateTime.now());
        
        return huodongxinxiRepository.save(existingHuodongxinxi);
    }
    
    @Override
    public void deleteHuodongxinxi(Long id) {
        Huodongxinxi huodongxinxi = huodongxinxiRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Huodongxinxi not found with id: " + id));
        huodongxinxiRepository.delete(huodongxinxi);
    }
    
    @Override
    public List<Huodongxinxi> getByHuodongfenleiId(Long huodongfenleiId) {
        return huodongxinxiRepository.findByHuodongfenleiId(huodongfenleiId);
    }
    
    @Override
    public List<Huodongxinxi> getByHuodongYesno(String huodongYesno) {
        return huodongxinxiRepository.findByHuodongYesno(huodongYesno);
    }
}