package com.soft.ob.worker.service;

import com.soft.ob.entity.Laogong;
import java.util.List;
import java.util.Optional;

public interface LaogongService {
    List<Laogong> getAllLaogong();
    Optional<Laogong> getLaogongById(Long id);
    Laogong saveLaogong(Laogong laogong);
    Laogong updateLaogong(Long id, Laogong laogong);
    void deleteLaogong(Long id);
    Optional<Laogong> findByUsername(String username);
    Optional<Laogong> findByLaogongUuid(String laogongUuid);
    Laogong loginLaogong(String username, String password);
    boolean changeLaogongPassword(Long id, String oldPassword, String newPassword);
}
