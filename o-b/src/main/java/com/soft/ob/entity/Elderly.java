package com.soft.ob.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "elderly")
@Data
public class Elderly {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "uuid", unique = true, nullable = false)
    private String uuid; // 老人唯一标识
    
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    
    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "salt")
    private String salt; // 密码盐值
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "age")
    private Integer age;
    
    @Column(name = "gender")
    private String gender;
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "id_number", unique = true)
    private String idNumber; // 身份证号
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "avatar")
    private String avatar; // 头像
    
    @Column(name = "emergency_contact_name")
    private String emergencyContactName;
    
    @Column(name = "emergency_contact_phone")
    private String emergencyContactPhone;
    
    @Column(name = "health_status")
    private String healthStatus;
    
    @Column(name = "special_medical_needs")
    private String specialMedicalNeeds;
    
    @Column(name = "admission_date")
    private LocalDate admissionDate;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "login_attempts")
    private Integer loginAttempts = 0;
    
    @Column(name = "locked_until")
    private LocalDateTime lockedUntil;
    
    @Column(name = "create_time")
    private LocalDateTime createTime;
    
    @Column(name = "update_time")
    private LocalDateTime updateTime;
    
    @Column(name = "last_login_time")
    private LocalDateTime lastLoginTime;
    
    @Column(nullable = false)
    private String roomNumber;
    
    @Column
    private LocalDate birthDate;
    
    @Column
    private String notes;
}