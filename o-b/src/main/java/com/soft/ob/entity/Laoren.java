package com.soft.ob.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "laoren")
@Data
public class Laoren {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String laorenUuid; // 老人唯一标识
    
    @Column(nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column
    private String name;
    
    @Column
    private Integer age;
    
    @Column
    private String gender;
    
    @Column
    private String phone;
    
    @Column
    private String idNumber; // 身份证号
    
    @Column
    private String address;
    
    @Column
    private String avatar; // 头像
    
    @Column
    private String emergencyContactName;
    
    @Column
    private String emergencyContactPhone;
    
    @Column
    private String healthStatus;
    
    @Column
    private String specialMedicalNeeds;
    
    @Column
    private LocalDate admissionDate;
    
    @Column
    private Boolean isActive = true;
    
    @Column
    private LocalDateTime createTime;
    
    @Column
    private LocalDateTime updateTime;
}