package com.soft.ob.elder.service;

import com.soft.ob.entity.Elderly;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ElderlyService {
    List<Elderly> getAllElderly();
    Optional<Elderly> getElderlyById(Long id);
    Elderly saveElderly(Elderly elderly);
    Elderly updateElderly(Long id, Elderly elderly);
    void deleteElderly(Long id);
    List<Elderly> getActiveElderly();
    List<Elderly> searchElderlyByName(String name);
    Optional<Elderly> findByUsername(String username);
    Optional<Elderly> findByUuid(String uuid);
    Elderly registerElderly(Elderly elderly);
    Elderly loginElderly(String username, String password);
    boolean changeElderlyPassword(Long id, String oldPassword, String newPassword);
    Elderly updateElderlyInfo(Long id, Elderly elderly);
}