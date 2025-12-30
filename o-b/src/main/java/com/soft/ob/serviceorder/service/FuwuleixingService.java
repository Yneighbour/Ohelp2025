package com.soft.ob.serviceorder.service;

import com.soft.ob.entity.Fuwuleixing;
import java.util.List;
import java.util.Optional;

public interface FuwuleixingService {
    List<Fuwuleixing> getAllFuwuleixing();
    Optional<Fuwuleixing> getFuwuleixingById(Long id);
    Fuwuleixing saveFuwuleixing(Fuwuleixing fuwuleixing);
    Fuwuleixing updateFuwuleixing(Long id, Fuwuleixing fuwuleixing);
    void deleteFuwuleixing(Long id);
}
