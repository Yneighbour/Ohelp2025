package com.soft.ob.service;

import com.soft.ob.entity.Fuwuxiangmu;
import java.util.List;
import java.util.Optional;

public interface FuwuxiangmuService {
    List<Fuwuxiangmu> getAllFuwuxiangmu();
    Optional<Fuwuxiangmu> getFuwuxiangmuById(Long id);
    Fuwuxiangmu saveFuwuxiangmu(Fuwuxiangmu fuwuxiangmu);
    Fuwuxiangmu updateFuwuxiangmu(Long id, Fuwuxiangmu fuwuxiangmu);
    void deleteFuwuxiangmu(Long id);
    List<Fuwuxiangmu> getByFuwuleixingId(Long fuwuleixingId);
    List<Fuwuxiangmu> getByShangxia(Integer shangxia);
}