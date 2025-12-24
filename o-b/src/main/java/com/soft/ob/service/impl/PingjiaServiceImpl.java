package com.soft.ob.service.impl;

import com.soft.ob.entity.Pingjia;
import com.soft.ob.repository.PingjiaRepository;
import com.soft.ob.service.PingjiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PingjiaServiceImpl implements PingjiaService {
    
    @Autowired
    private PingjiaRepository pingjiaRepository;
    
    @Override
    public List<Pingjia> getAllPingjia() {
        return pingjiaRepository.findAll();
    }
    
    @Override
    public Optional<Pingjia> getPingjiaById(Long id) {
        return pingjiaRepository.findById(id);
    }
    
    @Override
    public Pingjia savePingjia(Pingjia pingjia) {
        pingjia.setPingjiaUuid(generatePingjiaUuid());
        pingjia.setCreateTime(LocalDateTime.now());
        pingjia.setUpdateTime(LocalDateTime.now());
        return pingjiaRepository.save(pingjia);
    }
    
    @Override
    public Pingjia updatePingjia(Long id, Pingjia pingjia) {
        Pingjia existingPingjia = pingjiaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pingjia not found with id: " + id));
        
        existingPingjia.setServiceId(pingjia.getServiceId());
        existingPingjia.setServiceName(pingjia.getServiceName());
        existingPingjia.setLaorenId(pingjia.getLaorenId());
        existingPingjia.setLaorenName(pingjia.getLaorenName());
        existingPingjia.setStaffId(pingjia.getStaffId());
        existingPingjia.setStaffName(pingjia.getStaffName());
        existingPingjia.setRating(pingjia.getRating());
        existingPingjia.setComment(pingjia.getComment());
        existingPingjia.setServiceType(pingjia.getServiceType());
        existingPingjia.setEvaluationTime(pingjia.getEvaluationTime());
        existingPingjia.setStatus(pingjia.getStatus());
        existingPingjia.setUpdateTime(LocalDateTime.now());
        
        return pingjiaRepository.save(existingPingjia);
    }
    
    @Override
    public void deletePingjia(Long id) {
        Pingjia pingjia = pingjiaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pingjia not found with id: " + id));
        pingjiaRepository.delete(pingjia);
    }
    
    @Override
    public List<Pingjia> getByLaorenId(Long laorenId) {
        return pingjiaRepository.findByLaorenId(laorenId);
    }
    
    @Override
    public List<Pingjia> getByStaffId(Long staffId) {
        return pingjiaRepository.findByStaffId(staffId);
    }
    
    @Override
    public List<Pingjia> getByServiceId(Long serviceId) {
        return pingjiaRepository.findByServiceId(serviceId);
    }
    
    @Override
    public List<Pingjia> getByRating(Integer rating) {
        return pingjiaRepository.findByRating(rating);
    }
    
    @Override
    public List<Pingjia> getByServiceType(String serviceType) {
        return pingjiaRepository.findByServiceType(serviceType);
    }
    
    private String generatePingjiaUuid() {
        return "PJ" + UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
    }
}