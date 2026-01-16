package com.soft.ob.identity.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 老人端登录账号：一对一映射 user 与 elderly
 */
@Data
@Entity
@Table(name = "user_elder_mapping", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id", unique = true),
    @Index(name = "idx_elderly_id", columnList = "elderly_id", unique = true),
    @Index(name = "idx_created_at", columnList = "created_at")
})
public class UserElderMapping {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "elderly_id", nullable = false)
    private Long elderlyId;
    
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
