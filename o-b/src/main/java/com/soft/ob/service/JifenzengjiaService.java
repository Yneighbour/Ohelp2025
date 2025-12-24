package com.soft.ob.service;

import com.soft.ob.entity.Jifenzengjia;
import java.util.List;
import java.util.Optional;

public interface JifenzengjiaService {
    List<Jifenzengjia> getAllJifenzengjia();
    Optional<Jifenzengjia> getJifenzengjiaById(Long id);
    Jifenzengjia saveJifenzengjia(Jifenzengjia jifenzengjia);
    Jifenzengjia updateJifenzengjia(Long id, Jifenzengjia jifenzengjia);
    void deleteJifenzengjia(Long id);
    List<Jifenzengjia> getByLaorenUuid(String laorenUuid);
}