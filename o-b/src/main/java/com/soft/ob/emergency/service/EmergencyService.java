package com.soft.ob.emergency.service;

import com.soft.ob.emergency.entity.Emergency;
import com.soft.ob.emergency.mapper.EmergencyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmergencyService {

    @Autowired
    private EmergencyMapper emergencyMapper;

    public Emergency createEmergency(Emergency emergency) {
        emergency.setCreatedAt(LocalDateTime.now());
        emergency.setUpdatedAt(LocalDateTime.now());
        emergency.setIsActive(true);
        emergencyMapper.insert(emergency);
        return emergency;
    }

    public Emergency getEmergencyById(Long id) {
        return emergencyMapper.selectById(id);
    }

    public List<Emergency> getAllEmergencies() {
        return emergencyMapper.selectAll();
    }

    public List<Emergency> getEmergenciesByElderlyId(Long elderlyId) {
        return emergencyMapper.selectByElderlyId(elderlyId);
    }

    public List<Emergency> getEmergenciesByStatus(String status) {
        return emergencyMapper.selectByStatus(status);
    }

    public List<Emergency> getEmergenciesByPriority(String priority) {
        return emergencyMapper.selectByPriority(priority);
    }

    public Emergency updateEmergency(Emergency emergency) {
        emergency.setUpdatedAt(LocalDateTime.now());
        emergencyMapper.update(emergency);
        return emergency;
    }

    public boolean deleteEmergency(Long id) {
        return emergencyMapper.deleteById(id) > 0;
    }

    public boolean respondToEmergency(Long id, Long responderId) {
        Emergency emergency = emergencyMapper.selectById(id);
        if (emergency != null) {
            emergency.setResponderId(responderId);
            emergency.setResponseTime(LocalDateTime.now());
            emergency.setStatus("responded");
            emergency.setUpdatedAt(LocalDateTime.now());
            emergencyMapper.update(emergency);
            return true;
        }
        return false;
    }

    public boolean resolveEmergency(Long id) {
        Emergency emergency = emergencyMapper.selectById(id);
        if (emergency != null) {
            emergency.setStatus("resolved");
            emergency.setResolvedTime(LocalDateTime.now());
            emergency.setUpdatedAt(LocalDateTime.now());
            emergencyMapper.update(emergency);
            return true;
        }
        return false;
    }
}
