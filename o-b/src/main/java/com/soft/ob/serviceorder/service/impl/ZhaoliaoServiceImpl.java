package com.soft.ob.serviceorder.service.impl;

import com.soft.ob.entity.Zhaoliao;
import com.soft.ob.repository.ZhaoliaoRepository;
import com.soft.ob.serviceorder.service.ZhaoliaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ZhaoliaoServiceImpl implements ZhaoliaoService {
    
    @Autowired
    private ZhaoliaoRepository zhaoliaoRepository;
    
    @Override
    public List<Zhaoliao> getAllZhaoliao() {
        return zhaoliaoRepository.findAll();
    }
    
    @Override
    public Optional<Zhaoliao> getZhaoliaoById(Long id) {
        return zhaoliaoRepository.findById(id);
    }
    
    @Override
    public Zhaoliao saveZhaoliao(Zhaoliao zhaoliao) {
        return zhaoliaoRepository.save(zhaoliao);
    }
    
    @Override
    public Zhaoliao updateZhaoliao(Long id, Zhaoliao zhaoliao) {
        Zhaoliao existingZhaoliao = zhaoliaoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Zhaoliao not found with id: " + id));
        
        existingZhaoliao.setZhaoliaoUuid(zhaoliao.getZhaoliaoUuid());
        existingZhaoliao.setTitle(zhaoliao.getTitle());
        existingZhaoliao.setDescription(zhaoliao.getDescription());
        existingZhaoliao.setType(zhaoliao.getType());
        existingZhaoliao.setLaorenId(zhaoliao.getLaorenId());
        existingZhaoliao.setLaorenName(zhaoliao.getLaorenName());
        existingZhaoliao.setAssignedStaff(zhaoliao.getAssignedStaff());
        existingZhaoliao.setStatus(zhaoliao.getStatus());
        existingZhaoliao.setScheduledTime(zhaoliao.getScheduledTime());
        existingZhaoliao.setCompletedTime(zhaoliao.getCompletedTime());
        existingZhaoliao.setNotes(zhaoliao.getNotes());
        existingZhaoliao.setCreateTime(zhaoliao.getCreateTime());
        existingZhaoliao.setUpdateTime(zhaoliao.getUpdateTime());
        
        return zhaoliaoRepository.save(existingZhaoliao);
    }
    
    @Override
    public void deleteZhaoliao(Long id) {
        Zhaoliao zhaoliao = zhaoliaoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Zhaoliao not found with id: " + id));
        zhaoliaoRepository.delete(zhaoliao);
    }
    
    @Override
    public List<Zhaoliao> getByLaorenId(Long laorenId) {
        return zhaoliaoRepository.findByLaorenId(laorenId);
    }
    
    @Override
    public List<Zhaoliao> getByStatus(String status) {
        return zhaoliaoRepository.findByStatus(status);
    }
    
    @Override
    public List<Zhaoliao> getByType(String type) {
        return zhaoliaoRepository.findByType(type);
    }
    
    @Override
    public List<Zhaoliao> getByAssignedStaff(String assignedStaff) {
        return zhaoliaoRepository.findByAssignedStaff(assignedStaff);
    }
}