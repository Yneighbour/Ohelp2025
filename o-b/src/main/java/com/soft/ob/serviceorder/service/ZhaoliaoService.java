package com.soft.ob.serviceorder.service;

import com.soft.ob.entity.Zhaoliao;
import java.util.List;
import java.util.Optional;

public interface ZhaoliaoService {
    List<Zhaoliao> getAllZhaoliao();
    Optional<Zhaoliao> getZhaoliaoById(Long id);
    Zhaoliao saveZhaoliao(Zhaoliao zhaoliao);
    Zhaoliao updateZhaoliao(Long id, Zhaoliao zhaoliao);
    void deleteZhaoliao(Long id);
    List<Zhaoliao> getByLaorenId(Long laorenId);
    List<Zhaoliao> getByStatus(String status);
    List<Zhaoliao> getByType(String type);
    List<Zhaoliao> getByAssignedStaff(String assignedStaff);
}
