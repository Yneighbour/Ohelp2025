package com.soft.ob.service;

import com.soft.ob.entity.Tanwang;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TanwangService {
    List<Tanwang> getAllTanwang();
    Optional<Tanwang> getTanwangById(Long id);
    Tanwang saveTanwang(Tanwang tanwang);
    Tanwang updateTanwang(Long id, Tanwang tanwang);
    void deleteTanwang(Long id);
    List<Tanwang> getByLaorenId(Long laorenId);
    List<Tanwang> getByVisitorName(String visitorName);
    List<Tanwang> getByStatus(String status);
    List<Tanwang> getByStaffId(String staffId);
    List<Tanwang> getByVisitTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
}