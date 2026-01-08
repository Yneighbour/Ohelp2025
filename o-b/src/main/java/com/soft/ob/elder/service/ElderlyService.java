package com.soft.ob.elder.service;

import com.soft.ob.elder.entity.Elderly;
import com.soft.ob.elder.mapper.ElderlyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ElderlyService {

    @Autowired
    private ElderlyMapper elderlyMapper;

    public Elderly createElderly(Elderly elderly) {
        elderly.setCreatedAt(LocalDateTime.now());
        elderly.setUpdatedAt(LocalDateTime.now());
        elderly.setIsActive(true);
        elderlyMapper.insert(elderly);
        return elderly;
    }

    public Elderly getElderlyById(Long id) {
        return elderlyMapper.selectById(id);
    }

    public List<Elderly> getAllElderly() {
        return elderlyMapper.selectAll();
    }

    public List<Elderly> getElderlyByName(String name) {
        return elderlyMapper.selectByName(name);
    }

    public Elderly updateElderly(Elderly elderly) {
        elderly.setUpdatedAt(LocalDateTime.now());
        elderlyMapper.update(elderly);
        return elderly;
    }

    public boolean deleteElderly(Long id) {
        return elderlyMapper.deleteById(id) > 0;
    }

    public boolean activateElderly(Long id) {
        Elderly elderly = elderlyMapper.selectById(id);
        if (elderly != null) {
            elderly.setIsActive(true);
            elderly.setUpdatedAt(LocalDateTime.now());
            elderlyMapper.update(elderly);
            return true;
        }
        return false;
    }

    public boolean deactivateElderly(Long id) {
        Elderly elderly = elderlyMapper.selectById(id);
        if (elderly != null) {
            elderly.setIsActive(false);
            elderly.setUpdatedAt(LocalDateTime.now());
            elderlyMapper.update(elderly);
            return true;
        }
        return false;
    }
}
