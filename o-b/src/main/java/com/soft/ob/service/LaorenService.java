package com.soft.ob.service;

import com.soft.ob.entity.Laoren;
import java.util.List;
import java.util.Optional;

public interface LaorenService {
    List<Laoren> getAllLaoren();
    Optional<Laoren> getLaorenById(Long id);
    Laoren saveLaoren(Laoren laoren);
    Laoren updateLaoren(Long id, Laoren laoren);
    void deleteLaoren(Long id);
    Optional<Laoren> findByUsername(String username);
    Optional<Laoren> findByLaorenUuid(String laorenUuid);
    Laoren registerLaoren(Laoren laoren);
    Laoren loginLaoren(String username, String password);
    boolean changeLaorenPassword(Long id, String oldPassword, String newPassword);
    Laoren updateLaorenInfo(Long id, Laoren laoren);
}