package com.soft.ob.entity;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Table(name = "staff")
@Data
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, unique = true)
    private String username;
    
    @Column
    private String email;
    
    @Column
    private String phone;
    
    @Column
    private String position;
    
    @Column
    private String department;
    
    @Column
    private String role; // ADMIN, CARE_STAFF, MEDICAL_STAFF, etc.
    
    @Column
    private Boolean isActive = true;
    
    @Column
    private String password; // In real application, this should be encrypted
}