package com.soft.ob.service.impl;

import com.soft.ob.entity.Qinshu;
import com.soft.ob.repository.QinshuRepository;
import com.soft.ob.service.QinshuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QinshuServiceImpl implements QinshuService {
    
    @Autowired
    private QinshuRepository qinshuRepository;
    
    @Override
    public List<Qinshu> getAllQinshu() {
        return qinshuRepository.findAll();
    }
    
    @Override
    public Optional<Qinshu> getQinshuById(Long id) {
        return qinshuRepository.findById(id);
    }
    
    @Override
    public Qinshu saveQinshu(Qinshu qinshu) {
        qinshu.setCreateTime(LocalDateTime.now());
        qinshu.setUpdateTime(LocalDateTime.now());
        return qinshuRepository.save(qinshu);
    }
    
    @Override
    public Qinshu updateQinshu(Long id, Qinshu qinshu) {
        Qinshu existingQinshu = qinshuRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Qinshu not found with id: " + id));
        
        existingQinshu.setLaorenUuid(qinshu.getLaorenUuid());
        existingQinshu.setLaorenName(qinshu.getLaorenName());
        existingQinshu.setQinshuName(qinshu.getQinshuName());
        existingQinshu.setQinshuPhone(qinshu.getQinshuPhone());
        existingQinshu.setQinshuIdNumber(qinshu.getQinshuIdNumber());
        existingQinshu.setQinshuDelete(qinshu.getQinshuDelete());
        existingQinshu.setUpdateTime(LocalDateTime.now());
        
        return qinshuRepository.save(existingQinshu);
    }
    
    @Override
    public void deleteQinshu(Long id) {
        Qinshu qinshu = qinshuRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Qinshu not found with id: " + id));
        qinshuRepository.delete(qinshu);
    }
    
    @Override
    public List<Qinshu> getByLaorenUuid(String laorenUuid) {
        return qinshuRepository.findByLaorenUuid(laorenUuid);
    }
}