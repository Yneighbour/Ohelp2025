package com.soft.ob.service;

import com.soft.ob.entity.Jinjiqiuzhu;
import java.util.List;
import java.util.Optional;

public interface JinjiqiuzhuService {
    List<Jinjiqiuzhu> getAllJinjiqiuzhu();
    Optional<Jinjiqiuzhu> getJinjiqiuzhuById(Long id);
    Jinjiqiuzhu saveJinjiqiuzhu(Jinjiqiuzhu jinjiqiuzhu);
    Jinjiqiuzhu updateJinjiqiuzhu(Long id, Jinjiqiuzhu jinjiqiuzhu);
    void deleteJinjiqiuzhu(Long id);
    List<Jinjiqiuzhu> getByLaorenUuid(String laorenUuid);
}