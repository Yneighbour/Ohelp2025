package com.soft.ob.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "zhaoliao")
@Data
public class Zhaoliao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String zhaoliaoUuid; // 照料计划唯一标识
    
    @Column(nullable = false)
    private String title; // 计划标题
    
    @Column
    private String description; // 计划描述
    
    @Column
    private String type; // 照料类型（如：日常护理、送餐服务、洗浴服务等）
    
    @Column
    private Long laorenId; // 关联老人ID
    
    @Column
    private String laorenName; // 老人姓名
    
    @Column
    private String assignedStaff; // 分配的工作人员
    
    @Column
    private String status; // 计划状态（待执行、执行中、已完成）
    
    @Column
    private LocalDateTime scheduledTime; // 计划执行时间
    
    @Column
    private LocalDateTime completedTime; // 完成时间
    
    @Column
    private String notes; // 备注
    
    @Column
    private LocalDateTime createTime;
    
    @Column
    private LocalDateTime updateTime;
}