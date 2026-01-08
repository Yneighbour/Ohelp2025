package com.soft.ob.elder.service;

import com.soft.ob.elder.entity.Relative;
import com.soft.ob.elder.mapper.RelativeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RelativeService {

    @Autowired
    private RelativeMapper relativeMapper;

    public Relative createRelative(Relative relative) {
        relative.setCreatedAt(LocalDateTime.now());
        relative.setUpdatedAt(LocalDateTime.now());
        relative.setIsActive(true);
        relativeMapper.insert(relative);
        return relative;
    }

    public Relative getRelativeById(Long id) {
        return relativeMapper.selectById(id);
    }

    public List<Relative> getRelativesByElderlyId(Long elderlyId) {
        return relativeMapper.selectByElderlyId(elderlyId);
    }

    public List<Relative> getAllRelatives() {
        return relativeMapper.selectAll();
    }

    public Relative updateRelative(Relative relative) {
        relative.setUpdatedAt(LocalDateTime.now());
        relativeMapper.update(relative);
        return relative;
    }

    public boolean deleteRelative(Long id) {
        return relativeMapper.deleteById(id) > 0;
    }

    public boolean activateRelative(Long id) {
        Relative relative = relativeMapper.selectById(id);
        if (relative != null) {
            relative.setIsActive(true);
            relative.setUpdatedAt(LocalDateTime.now());
            relativeMapper.update(relative);
            return true;
        }
        return false;
    }

    public boolean deactivateRelative(Long id) {
        Relative relative = relativeMapper.selectById(id);
        if (relative != null) {
            relative.setIsActive(false);
            relative.setUpdatedAt(LocalDateTime.now());
            relativeMapper.update(relative);
            return true;
        }
        return false;
    }
}
