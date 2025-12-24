package com.soft.ob.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "fuwuxiangmu")
@Data
public class Fuwuxiangmu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String xiangmuName; // 项目名称
    
    @Column
    private String xiangmuPhoto; // 项目图片
    
    @Column
    private BigDecimal xiangmuNewsPrice; // 项目价格
    
    @Column
    private Integer xiangmuNumber; // 项目数量
    
    @Column(length = 1000)
    private String xiangmuDesc; // 项目详情
    
    @Column
    private Long fuwuleixingId; // 服务类型ID
    
    @Column
    private Integer shangxia; // 上下架状态 (1:上架, 0:下架)
    
    @Column
    private LocalDateTime createTime;
    
    @Column
    private LocalDateTime updateTime;
}