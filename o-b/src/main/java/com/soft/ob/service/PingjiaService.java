package com.soft.ob.service;

import com.soft.ob.entity.Pingjia;
import java.util.List;
import java.util.Optional;

public interface PingjiaService {
    List<Pingjia> getAllPingjia();
    Optional<Pingjia> getPingjiaById(Long id);
    Pingjia savePingjia(Pingjia pingjia);
    Pingjia updatePingjia(Long id, Pingjia pingjia);
    void deletePingjia(Long id);
    List<Pingjia> getByLaorenId(Long laorenId);
    List<Pingjia> getByStaffId(Long staffId);
    List<Pingjia> getByServiceId(Long serviceId);
    List<Pingjia> getByRating(Integer rating);
    List<Pingjia> getByServiceType(String serviceType);
}