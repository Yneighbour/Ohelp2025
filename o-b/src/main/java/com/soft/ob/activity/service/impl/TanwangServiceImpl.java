package com.soft.ob.activity.service.impl;

import com.soft.ob.entity.Tanwang;
import com.soft.ob.repository.TanwangRepository;
import com.soft.ob.activity.service.TanwangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TanwangServiceImpl implements TanwangService {
    
    @Autowired
    private TanwangRepository tanwangRepository;
    
    @Override
    public List<Tanwang> getAllTanwang() {
        return tanwangRepository.findAll();
    }
    
    @Override
    public Optional<Tanwang> getTanwangById(Long id) {
        return tanwangRepository.findById(id);
    }
    
    @Override
    public Tanwang saveTanwang(Tanwang tanwang) {
        tanwang.setCreateTime(LocalDateTime.now());
        tanwang.setUpdateTime(LocalDateTime.now());
        return tanwangRepository.save(tanwang);
    }
    
    @Override
    public Tanwang updateTanwang(Long id, Tanwang tanwang) {
        Tanwang existingTanwang = tanwangRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tanwang not found with id: " + id));
        
        existingTanwang.setLaorenId(tanwang.getLaorenId());
        existingTanwang.setVisitorName(tanwang.getVisitorName());
        existingTanwang.setVisitorPhone(tanwang.getVisitorPhone());
        existingTanwang.setRelationship(tanwang.getRelationship());
        existingTanwang.setLaorenName(tanwang.getLaorenName());
        existingTanwang.setVisitTime(tanwang.getVisitTime());
        existingTanwang.setLeaveTime(tanwang.getLeaveTime());
        existingTanwang.setVisitReason(tanwang.getVisitReason());
        existingTanwang.setStatus(tanwang.getStatus());
        existingTanwang.setNotes(tanwang.getNotes());
        existingTanwang.setStaffId(tanwang.getStaffId());
        existingTanwang.setStaffName(tanwang.getStaffName());
        existingTanwang.setUpdateTime(LocalDateTime.now());
        
        return tanwangRepository.save(existingTanwang);
    }
    
    @Override
    public void deleteTanwang(Long id) {
        Tanwang tanwang = tanwangRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tanwang not found with id: " + id));
        tanwangRepository.delete(tanwang);
    }
    
    @Override
    public List<Tanwang> getByLaorenId(Long laorenId) {
        return tanwangRepository.findByLaorenId(laorenId);
    }
    
    @Override
    public List<Tanwang> getByVisitorName(String visitorName) {
        return tanwangRepository.findByVisitorNameContaining(visitorName);
    }
    
    @Override
    public List<Tanwang> getByStatus(String status) {
        // Tanwang实体中没有status字段，所以返回所有记录
        return tanwangRepository.findAll();
    }
    
    @Override
    public List<Tanwang> getByStaffId(String staffId) {
        return tanwangRepository.findByStaffId(Long.valueOf(staffId));
    }
    
    @Override
    public List<Tanwang> getByVisitTimeBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return tanwangRepository.findByVisitTimeBetween(startTime, endTime);
    }
}