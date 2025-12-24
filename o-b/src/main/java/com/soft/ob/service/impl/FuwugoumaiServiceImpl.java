package com.soft.ob.service.impl;

import com.soft.ob.entity.Fuwugoumai;
import com.soft.ob.repository.FuwugoumaiRepository;
import com.soft.ob.service.FuwugoumaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FuwugoumaiServiceImpl implements FuwugoumaiService {
    
    @Autowired
    private FuwugoumaiRepository fuwugoumaiRepository;
    
    @Override
    public List<Fuwugoumai> getAllFuwugoumai() {
        return fuwugoumaiRepository.findAll();
    }
    
    @Override
    public Optional<Fuwugoumai> getFuwugoumaiById(Long id) {
        return fuwugoumaiRepository.findById(id);
    }
    
    @Override
    public Fuwugoumai saveFuwugoumai(Fuwugoumai fuwugoumai) {
        fuwugoumai.setCreateTime(LocalDateTime.now());
        fuwugoumai.setUpdateTime(LocalDateTime.now());
        return fuwugoumaiRepository.save(fuwugoumai);
    }
    
    @Override
    public Fuwugoumai updateFuwugoumai(Long id, Fuwugoumai fuwugoumai) {
        Fuwugoumai existingFuwugoumai = fuwugoumaiRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Fuwugoumai not found with id: " + id));
        
        existingFuwugoumai.setGoumaiUuid(fuwugoumai.getGoumaiUuid());
        existingFuwugoumai.setFuwuxiangmuName(fuwugoumai.getFuwuxiangmuName());
        existingFuwugoumai.setFuwuleixingName(fuwugoumai.getFuwuleixingName());
        existingFuwugoumai.setLaorenUuid(fuwugoumai.getLaorenUuid());
        existingFuwugoumai.setLaorenName(fuwugoumai.getLaorenName());
        existingFuwugoumai.setGoumaiNumber(fuwugoumai.getGoumaiNumber());
        existingFuwugoumai.setGoumaiOldNumber(fuwugoumai.getGoumaiOldNumber());
        existingFuwugoumai.setGoumaiNewNumber(fuwugoumai.getGoumaiNewNumber());
        existingFuwugoumai.setGoumaiNewPrice(fuwugoumai.getGoumaiNewPrice());
        existingFuwugoumai.setGoumaiAddress(fuwugoumai.getGoumaiAddress());
        existingFuwugoumai.setGoumaiContent(fuwugoumai.getGoumaiContent());
        existingFuwugoumai.setGoumaiYesno(fuwugoumai.getGoumaiYesno());
        existingFuwugoumai.setGoumaiContentReply(fuwugoumai.getGoumaiContentReply());
        existingFuwugoumai.setUpdateTime(LocalDateTime.now());
        
        return fuwugoumaiRepository.save(existingFuwugoumai);
    }
    
    @Override
    public void deleteFuwugoumai(Long id) {
        Fuwugoumai fuwugoumai = fuwugoumaiRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Fuwugoumai not found with id: " + id));
        fuwugoumaiRepository.delete(fuwugoumai);
    }
    
    @Override
    public List<Fuwugoumai> getByLaorenUuid(String laorenUuid) {
        return fuwugoumaiRepository.findByLaorenUuid(laorenUuid);
    }
    
    @Override
    public List<Fuwugoumai> getByGoumaiYesno(String goumaiYesno) {
        return fuwugoumaiRepository.findByGoumaiYesno(goumaiYesno);
    }
}