package com.soft.ob.health.service;

import com.soft.ob.entity.Meirijiankang;
import java.util.List;
import java.util.Optional;

public interface MeirijiankangService {
    List<Meirijiankang> getAllMeirijiankang();
    Optional<Meirijiankang> getMeirijiankangById(Long id);
    Meirijiankang saveMeirijiankang(Meirijiankang meirijiankang);
    Meirijiankang updateMeirijiankang(Long id, Meirijiankang meirijiankang);
    void deleteMeirijiankang(Long id);
}
