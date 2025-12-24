package com.soft.ob.service.impl;

import com.soft.ob.entity.Elderly;
import com.soft.ob.repository.ElderlyRepository;
import com.soft.ob.service.ElderlyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ElderlyServiceImpl implements ElderlyService {
    
    @Autowired
    private ElderlyRepository elderlyRepository;
    
    @Override
    public List<Elderly> getAllElderly() {
        return elderlyRepository.findAll();
    }
    
    @Override
    public Optional<Elderly> getElderlyById(Long id) {
        return elderlyRepository.findById(id);
    }
    
    @Override
    public Elderly saveElderly(Elderly elderly) {
        return elderlyRepository.save(elderly);
    }
    
    @Override
    public Elderly updateElderly(Long id, Elderly elderlyDetails) {
        Elderly elderly = elderlyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Elderly not found with id: " + id));
        
        elderly.setName(elderlyDetails.getName());
        elderly.setAge(elderlyDetails.getAge());
        elderly.setGender(elderlyDetails.getGender());
        elderly.setPhone(elderlyDetails.getPhone());
        elderly.setAddress(elderlyDetails.getAddress());
        elderly.setBirthDate(elderlyDetails.getBirthDate());
        elderly.setEmergencyContactName(elderlyDetails.getEmergencyContactName());
        elderly.setEmergencyContactPhone(elderlyDetails.getEmergencyContactPhone());
        elderly.setHealthStatus(elderlyDetails.getHealthStatus());
        elderly.setSpecialMedicalNeeds(elderlyDetails.getSpecialMedicalNeeds());
        elderly.setRoomNumber(elderlyDetails.getRoomNumber());
        elderly.setAdmissionDate(elderlyDetails.getAdmissionDate());
        elderly.setNotes(elderlyDetails.getNotes());
        
        return elderlyRepository.save(elderly);
    }
    
    @Override
    public void deleteElderly(Long id) {
        Elderly elderly = elderlyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Elderly not found with id: " + id));
        elderly.setIsActive(false); // Soft delete
        elderlyRepository.save(elderly);
    }
    
    @Override
    public List<Elderly> getActiveElderly() {
        return elderlyRepository.findByIsActiveTrue();
    }
    
    @Override
    public List<Elderly> searchElderlyByName(String name) {
        return elderlyRepository.findByNameContaining(name);
    }
}