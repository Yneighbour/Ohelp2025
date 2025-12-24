package com.soft.ob.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "huodongxinxi")
@Data
public class Huodongxinxi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String huodongName; // 活动名称
    
    @Column
    private String huodongPhoto; // 活动图片
    
    @Column
    private String huodongAddress; // 活动地点
    
    @Column
    private LocalDateTime huodongTime; // 活动时间
    
    @Column
    private BigDecimal huodongMoney; // 活动费用
    
    @Column(length = 1000)
    private String huodongContent; // 活动详情
    
    @Column
    private Long huodongfenleiId; // 活动分类ID
    
    @Column
    private String huodongYesno; // 活动状态 (0:未开始, 1:进行中, 2:已结束)
    
    @Column
    private LocalDateTime createTime;
    
    @Column
    private LocalDateTime updateTime;
}