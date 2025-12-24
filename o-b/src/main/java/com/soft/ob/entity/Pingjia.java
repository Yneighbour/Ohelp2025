package com.soft.ob.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pingjia")
@Data
public class Pingjia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String pingjiaUuid; // 评价唯一标识
    
    @Column(nullable = false)
    private Long serviceId; // 服务ID
    
    @Column
    private String serviceName; // 服务名称
    
    @Column
    private Long laorenId; // 老人ID
    
    @Column
    private String laorenName; // 老人姓名
    
    @Column
    private Long staffId; // 员工ID
    
    @Column
    private String staffName; // 员工姓名
    
    @Column
    private Integer rating; // 评分（1-5星）
    
    @Column(length = 1000)
    private String comment; // 评价内容
    
    @Column
    private String serviceType; // 服务类型
    
    @Column
    private LocalDateTime evaluationTime; // 评价时间
    
    @Column
    private String status; // 评价状态
    
    @Column
    private LocalDateTime createTime;
    
    @Column
    private LocalDateTime updateTime;
}