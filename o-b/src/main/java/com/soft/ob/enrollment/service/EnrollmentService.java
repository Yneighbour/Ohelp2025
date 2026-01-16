package com.soft.ob.enrollment.service;

import com.soft.ob.enrollment.entity.Enrollment;
import com.soft.ob.enrollment.mapper.EnrollmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EnrollmentService {
    @Autowired
    private EnrollmentMapper enrollmentMapper;

    public Enrollment getById(Long id) {
        return enrollmentMapper.getById(id);
    }

    public List<Enrollment> listAll() {
        return enrollmentMapper.listAll();
    }

    public List<Enrollment> listByActivity(Long activityId) {
        return enrollmentMapper.listByActivity(activityId);
    }

    public List<Enrollment> listByElderly(Long elderlyId) {
        return enrollmentMapper.listByElderly(elderlyId);
    }

    public List<Enrollment> listByStatus(String status) {
        return enrollmentMapper.listByStatus(status);
    }

    public Enrollment create(Enrollment enrollment) {
        if (enrollment.getEnrollTime() == null) {
            enrollment.setEnrollTime(LocalDateTime.now());
        }
        if (enrollment.getStatus() == null) {
            enrollment.setStatus("pending");
        }
        enrollment.setIsActive(true);
        enrollment.setCreatedAt(LocalDateTime.now());
        enrollment.setUpdatedAt(LocalDateTime.now());
        enrollmentMapper.create(enrollment);
        return enrollment;
    }

    public Enrollment update(Enrollment enrollment) {
        enrollment.setUpdatedAt(LocalDateTime.now());
        enrollmentMapper.update(enrollment);
        return enrollment;
    }

    public void delete(Long id) {
        enrollmentMapper.delete(id);
    }

    public void confirm(Long id) {
        enrollmentMapper.updateStatus(id, "confirmed", null);
    }

    public void checkIn(Long id) {
        enrollmentMapper.updateStatus(id, "attended", LocalDateTime.now());
    }

    public void cancel(Long id) {
        enrollmentMapper.updateStatus(id, "cancelled", null);
    }

    public void markAbsent(Long id) {
        enrollmentMapper.updateStatus(id, "absent", null);
    }
}
