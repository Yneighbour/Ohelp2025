package com.soft.ob.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column(unique = true)
    private String email;
    
    @Column
    private String phone;
    
    @Column
    private String realName;
    
    @Column
    private Integer age;
    
    @Column
    private String gender;
    
    @Column
    private String avatar;
    
    @Column
    private String role; // ADMIN, USER, etc.
    
    @Column
    private Boolean isActive = true;
    
    @Column
    private LocalDateTime createTime;
    
    @Column
    private LocalDateTime updateTime;
    
    @Column
    private LocalDateTime lastLoginTime;
}