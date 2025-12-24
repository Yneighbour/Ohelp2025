package com.soft.ob.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "jifenzengjia")
@Data
public class Jifenzengjia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String laorenUuid; // 老人唯一标识
    
    @Column
    private String laorenName; // 老人姓名
    
    @Column
    private String jifenName; // 积分名称
    
    @Column
    private BigDecimal jifenNumber; // 积分数量
    
    @Column
    private String jifenContent; // 积分详情
    
    @Column
    private String jifenDelete; // 删除状态 (0:正常, 1:已删除)
    
    @Column
    private LocalDateTime createTime;
    
    @Column
    private LocalDateTime updateTime;
}