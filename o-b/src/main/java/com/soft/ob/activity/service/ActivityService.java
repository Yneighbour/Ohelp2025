package com.soft.ob.activity.service;

import com.soft.ob.activity.entity.Activity;
import com.soft.ob.activity.mapper.ActivityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    public Activity createActivity(Activity activity) {
        activity.setCreatedAt(LocalDateTime.now());
        activity.setUpdatedAt(LocalDateTime.now());
        activity.setIsActive(true);
        activityMapper.insert(activity);
        return activity;
    }

    public Activity getActivityById(Long id) {
        return activityMapper.selectById(id);
    }

    public List<Activity> getAllActivities() {
        return activityMapper.selectAll();
    }

    public List<Activity> getActivitiesByCategory(String category) {
        return activityMapper.selectByCategory(category);
    }

    public List<Activity> getActivitiesByStatus(String status) {
        return activityMapper.selectByStatus(status);
    }

    public Activity updateActivity(Activity activity) {
        activity.setUpdatedAt(LocalDateTime.now());
        activityMapper.update(activity);
        return activity;
    }

    public boolean deleteActivity(Long id) {
        return activityMapper.deleteById(id) > 0;
    }

    public boolean activateActivity(Long id) {
        Activity activity = activityMapper.selectById(id);
        if (activity != null) {
            activity.setIsActive(true);
            activity.setUpdatedAt(LocalDateTime.now());
            activityMapper.update(activity);
            return true;
        }
        return false;
    }

    public boolean deactivateActivity(Long id) {
        Activity activity = activityMapper.selectById(id);
        if (activity != null) {
            activity.setIsActive(false);
            activity.setUpdatedAt(LocalDateTime.now());
            activityMapper.update(activity);
            return true;
        }
        return false;
    }
}
