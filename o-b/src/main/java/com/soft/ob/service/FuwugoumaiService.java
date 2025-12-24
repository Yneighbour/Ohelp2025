package com.soft.ob.service;

import com.soft.ob.entity.Fuwugoumai;
import java.util.List;
import java.util.Optional;

public interface FuwugoumaiService {
    List<Fuwugoumai> getAllFuwugoumai();
    Optional<Fuwugoumai> getFuwugoumaiById(Long id);
    Fuwugoumai saveFuwugoumai(Fuwugoumai fuwugoumai);
    Fuwugoumai updateFuwugoumai(Long id, Fuwugoumai fuwugoumai);
    void deleteFuwugoumai(Long id);
    List<Fuwugoumai> getByLaorenUuid(String laorenUuid);
    List<Fuwugoumai> getByGoumaiYesno(String goumaiYesno);
}