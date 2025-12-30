package com.soft.ob.activity.service;

import com.soft.ob.entity.Huodongfenlei;
import java.util.List;
import java.util.Optional;

public interface HuodongfenleiService {
    List<Huodongfenlei> getAllHuodongfenlei();
    Optional<Huodongfenlei> getHuodongfenleiById(Long id);
    Huodongfenlei saveHuodongfenlei(Huodongfenlei huodongfenlei);
    Huodongfenlei updateHuodongfenlei(Long id, Huodongfenlei huodongfenlei);
    void deleteHuodongfenlei(Long id);
}
