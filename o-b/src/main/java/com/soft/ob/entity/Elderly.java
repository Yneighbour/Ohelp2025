package com.soft.ob.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "elderly")
@Data
public class Elderly {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private Integer age;
    
    @Column
    private String gender;
    
    @Column
    private String phone;
    
    @Column
    private String address;
    
    @Column
    private LocalDate birthDate;
    
    @Column
    private String emergencyContactName;
    
    @Column
    private String emergencyContactPhone;
    
    @Column
    private String healthStatus;
    
    @Column
    private String specialMedicalNeeds;
    
    @Column
    private String roomNumber;
    
    @Column
    private LocalDate admissionDate;
    
    @Column
    private Boolean isActive = true;
    
    @Column
    private String notes;
}