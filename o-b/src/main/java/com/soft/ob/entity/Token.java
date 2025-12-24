package com.soft.ob.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "token")
@Data
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String token; // 令牌值
    
    @Column(nullable = false)
    private String userId; // 用户ID
    
    @Column(nullable = false)
    private String userType; // 用户类型 (USER, LAOREN, LAOGONG, etc.)
    
    @Column
    private LocalDateTime createTime; // 创建时间
    
    @Column
    private LocalDateTime expireTime; // 过期时间
    
    @Column
    private Boolean isActive = true; // 是否激活
    
    @Column
    private LocalDateTime lastAccessTime; // 最后访问时间
}