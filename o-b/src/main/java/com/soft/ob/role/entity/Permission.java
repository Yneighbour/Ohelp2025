package com.soft.ob.role.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "permission")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name; // 权限名称，如：用户管理、角色管理

    @Column(nullable = false, unique = true, length = 100)
    private String code; // 权限编码，如：user:manage、role:manage

    @Column(length = 50)
    private String module; // 所属模块，如：系统管理、业务管理

    @Column(length = 500)
    private String description; // 权限描述

    @Column(name = "is_active")
    private Boolean isActive = true; // 是否启用

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
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
