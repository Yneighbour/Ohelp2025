package com.soft.ob.elder.service;

import com.soft.ob.entity.Qinshu;
import java.util.List;
import java.util.Optional;

public interface QinshuService {
    List<Qinshu> getAllQinshu();
    Optional<Qinshu> getQinshuById(Long id);
    Qinshu saveQinshu(Qinshu qinshu);
    Qinshu updateQinshu(Long id, Qinshu qinshu);
    void deleteQinshu(Long id);
    List<Qinshu> getByLaorenUuid(String laorenUuid);
}
