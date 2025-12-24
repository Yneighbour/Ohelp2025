package com.soft.ob.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tanwang")
@Data
public class Tanwang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String tanwangUuid; // 探望登记唯一标识
    
    @Column
    private String visitorName; // 访客姓名
    
    @Column
    private String visitorPhone; // 访客电话
    
    @Column
    private String relationship; // 与老人关系
    
    @Column
    private Long laorenId; // 老人ID
    
    @Column
    private String laorenName; // 老人姓名
    
    @Column
    private LocalDateTime visitTime; // 探望时间
    
    @Column
    private LocalDateTime leaveTime; // 离开时间
    
    @Column
    private String visitReason; // 探望事由
    
    @Column
    private String status; // 状态（待确认、已确认、已完成、已取消）
    
    @Column
    private String notes; // 备注
    
    @Column
    private String staffId; // 登记员工ID
    
    @Column
    private String staffName; // 登记员工姓名
    
    @Column
    private LocalDateTime createTime;
    
    @Column
    private LocalDateTime updateTime;
}