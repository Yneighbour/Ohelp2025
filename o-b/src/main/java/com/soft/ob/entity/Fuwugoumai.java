package com.soft.ob.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "fuwugoumai")
@Data
public class Fuwugoumai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String goumaiUuid; // 购买唯一标识
    
    @Column
    private String fuwuxiangmuName; // 服务项目名称
    
    @Column
    private String fuwuleixingName; // 服务类型名称
    
    @Column
    private String laorenUuid; // 老人唯一标识
    
    @Column
    private String laorenName; // 老人姓名
    
    @Column
    private BigDecimal goumaiNumber; // 购买数量
    
    @Column
    private BigDecimal goumaiOldNumber; // 原购买数量
    
    @Column
    private BigDecimal goumaiNewNumber; // 新购买数量
    
    @Column
    private BigDecimal goumaiNewPrice; // 购买价格
    
    @Column
    private String goumaiAddress; // 购买地址
    
    @Column
    private String goumaiContent; // 购买备注
    
    @Column
    private String goumaiYesno; // 购买状态 (0:待处理, 1:已确认, 2:已完成)
    
    @Column
    private String goumaiContentReply; // 购买回复
    
    @Column
    private LocalDateTime createTime;
    
    @Column
    private LocalDateTime updateTime;
}