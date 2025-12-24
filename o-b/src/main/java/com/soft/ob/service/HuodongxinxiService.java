package com.soft.ob.service;

import com.soft.ob.entity.Huodongxinxi;
import java.util.List;
import java.util.Optional;

public interface HuodongxinxiService {
    List<Huodongxinxi> getAllHuodongxinxi();
    Optional<Huodongxinxi> getHuodongxinxiById(Long id);
    Huodongxinxi saveHuodongxinxi(Huodongxinxi huodongxinxi);
    Huodongxinxi updateHuodongxinxi(Long id, Huodongxinxi huodongxinxi);
    void deleteHuodongxinxi(Long id);
    List<Huodongxinxi> getByHuodongfenleiId(Long huodongfenleiId);
    List<Huodongxinxi> getByHuodongYesno(String huodongYesno);
}