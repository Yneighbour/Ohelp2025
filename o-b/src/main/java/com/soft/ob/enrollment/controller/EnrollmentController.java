package com.soft.ob.enrollment.controller;

import com.soft.ob.enrollment.entity.Enrollment;
import com.soft.ob.enrollment.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/enrollment")
@CrossOrigin(origins = "http://localhost:5173")
public class EnrollmentController {
    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Long id) {
        Enrollment enrollment = enrollmentService.getById(id);
        Map<String, Object> response = new HashMap<>();
        if (enrollment != null) {
            response.put("code", 200);
            response.put("message", "查询成功");
            response.put("data", enrollment);
            return ResponseEntity.ok(response);
        } else {
            response.put("code", 404);
            response.put("message", "报名信息不存在");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listAll() {
        List<Enrollment> enrollments = enrollmentService.listAll();
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "查询成功");
        response.put("data", enrollments);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<Map<String, Object>> listByActivity(@PathVariable Long activityId) {
        List<Enrollment> enrollments = enrollmentService.listByActivity(activityId);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "查询成功");
        response.put("data", enrollments);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/elderly/{elderlyId}")
    public ResponseEntity<Map<String, Object>> listByElderly(@PathVariable Long elderlyId) {
        List<Enrollment> enrollments = enrollmentService.listByElderly(elderlyId);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "查询成功");
        response.put("data", enrollments);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Map<String, Object>> listByStatus(@PathVariable String status) {
        List<Enrollment> enrollments = enrollmentService.listByStatus(status);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "查询成功");
        response.put("data", enrollments);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody Enrollment enrollment) {
        Enrollment created = enrollmentService.create(enrollment);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 201);
        response.put("message", "创建成功");
        response.put("data", created);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody Enrollment enrollment) {
        enrollment.setId(id);
        Enrollment updated = enrollmentService.update(enrollment);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "更新成功");
        response.put("data", updated);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        enrollmentService.delete(id);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "删除成功");
        response.put("data", null);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<Map<String, Object>> confirm(@PathVariable Long id) {
        enrollmentService.confirm(id);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "确认成功");
        response.put("data", null);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/checkin")
    public ResponseEntity<Map<String, Object>> checkIn(@PathVariable Long id) {
        enrollmentService.checkIn(id);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "签到成功");
        response.put("data", null);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Map<String, Object>> cancel(@PathVariable Long id) {
        enrollmentService.cancel(id);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "取消成功");
        response.put("data", null);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/absent")
    public ResponseEntity<Map<String, Object>> markAbsent(@PathVariable Long id) {
        enrollmentService.markAbsent(id);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "标记成功");
        response.put("data", null);
        return ResponseEntity.ok(response);
    }
}
