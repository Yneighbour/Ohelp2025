package com.soft.ob.service;

import com.soft.ob.entity.Elderly;
import java.util.List;
import java.util.Optional;

public interface ElderlyService {
    List<Elderly> getAllElderly();
    Optional<Elderly> getElderlyById(Long id);
    Elderly saveElderly(Elderly elderly);
    Elderly updateElderly(Long id, Elderly elderly);
    void deleteElderly(Long id);
    List<Elderly> getActiveElderly();
    List<Elderly> searchElderlyByName(String name);
}