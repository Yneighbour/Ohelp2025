package com.soft.ob.identity.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 家属端登录账号：user 一对一绑定到某个 elderly，并关联一个 relative 记录
 */
@Data
@Entity
@Table(name = "user_family_mapping", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id", unique = true),
    @Index(name = "idx_elderly_id", columnList = "elderly_id"),
    @Index(name = "idx_relative_id", columnList = "relative_id", unique = true)
})
public class UserFamilyMapping {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "elderly_id", nullable = false)
    private Long elderlyId;
    
    @Column(name = "relative_id", nullable = false)
    private Long relativeId;
    
    @Column(name = "is_primary_contact")
    private Boolean isPrimaryContact;
    
    @Column(name = "account_source", length = 50)
    private String accountSource;  // "manual" or "auto"
    
    @Column(name = "created_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
