package com.soft.ob.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "jiwangbingshi")
@Data
public class Jiwangbingshi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String laorenUuid; // 老人唯一标识
    
    @Column
    private String laorenName; // 老人姓名
    
    @Column
    private String bingshiName; // 病史名称
    
    @Column(length = 1000)
    private String bingshiContent; // 病史详情
    
    @Column
    private String bingshiDelete; // 删除状态 (0:正常, 1:已删除)
    
    @Column
    private LocalDateTime createTime;
    
    @Column
    private LocalDateTime updateTime;
}