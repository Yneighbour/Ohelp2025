package com.soft.ob.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "huodongfenlei")
@Data
public class Huodongfenlei {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String fenleiName; // 分类名称
    
    @Column
    private String fenleiImg; // 分类图片
    
    @Column
    private Integer shangxia; // 上下架状态 (1:上架, 0:下架)
    
    @Column
    private LocalDateTime createTime;
    
    @Column
    private LocalDateTime updateTime;
}