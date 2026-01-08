package com.soft.ob.health.service;

import com.soft.ob.health.entity.HealthRecord;
import com.soft.ob.health.mapper.HealthRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class HealthRecordService {

    @Autowired
    private HealthRecordMapper healthRecordMapper;

    public HealthRecord createHealthRecord(HealthRecord healthRecord) {
        healthRecord.setCreatedAt(LocalDateTime.now());
        healthRecord.setUpdatedAt(LocalDateTime.now());
        healthRecord.setIsActive(true);
        healthRecordMapper.insert(healthRecord);
        return healthRecord;
    }

    public HealthRecord getHealthRecordById(Long id) {
        return healthRecordMapper.selectById(id);
    }

    public List<HealthRecord> getAllHealthRecords() {
        return healthRecordMapper.selectAll();
    }

    public List<HealthRecord> getHealthRecordsByElderlyId(Long elderlyId) {
        return healthRecordMapper.selectByElderlyId(elderlyId);
    }

    public List<HealthRecord> getHealthRecordsByRecordDate(LocalDate recordDate) {
        return healthRecordMapper.selectByRecordDate(recordDate);
    }

    public List<HealthRecord> getHealthRecordsByDoctorId(Long doctorId) {
        return healthRecordMapper.selectByDoctorId(doctorId);
    }

    public HealthRecord updateHealthRecord(HealthRecord healthRecord) {
        healthRecord.setUpdatedAt(LocalDateTime.now());
        healthRecordMapper.update(healthRecord);
        return healthRecord;
    }

    public boolean deleteHealthRecord(Long id) {
        return healthRecordMapper.deleteById(id) > 0;
    }

    public boolean activateHealthRecord(Long id) {
        HealthRecord healthRecord = healthRecordMapper.selectById(id);
        if (healthRecord != null) {
            healthRecord.setIsActive(true);
            healthRecord.setUpdatedAt(LocalDateTime.now());
            healthRecordMapper.update(healthRecord);
            return true;
        }
        return false;
    }

    public boolean deactivateHealthRecord(Long id) {
        HealthRecord healthRecord = healthRecordMapper.selectById(id);
        if (healthRecord != null) {
            healthRecord.setIsActive(false);
            healthRecord.setUpdatedAt(LocalDateTime.now());
            healthRecordMapper.update(healthRecord);
            return true;
        }
        return false;
    }
}
