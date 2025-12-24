package com.soft.ob.service;

import com.soft.ob.entity.Jiwangbingshi;
import java.util.List;
import java.util.Optional;

public interface JiwangbingshiService {
    List<Jiwangbingshi> getAllJiwangbingshi();
    Optional<Jiwangbingshi> getJiwangbingshiById(Long id);
    Jiwangbingshi saveJiwangbingshi(Jiwangbingshi jiwangbingshi);
    Jiwangbingshi updateJiwangbingshi(Long id, Jiwangbingshi jiwangbingshi);
    void deleteJiwangbingshi(Long id);
    List<Jiwangbingshi> getByLaorenUuid(String laorenUuid);
}