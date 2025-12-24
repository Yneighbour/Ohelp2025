package com.soft.ob.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "fuwuleixing")
@Data
public class Fuwuleixing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String leixingName; // 类型名称
    
    @Column
    private String leixingDesc; // 类型描述
    
    @Column
    private String leixingImg; // 类型图片
    
    @Column
    private Integer shangxia; // 上下架状态 (1:上架, 0:下架)
    
    @Column
    private LocalDateTime createTime;
    
    @Column
    private LocalDateTime updateTime;
}