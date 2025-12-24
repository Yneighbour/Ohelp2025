package com.soft.ob.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "qinshu")
@Data
public class Qinshu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String laorenUuid; // 老人唯一标识
    
    @Column
    private String laorenName; // 老人姓名
    
    @Column
    private String qinshuName; // 亲人姓名
    
    @Column
    private String qinshuPhone; // 亲人电话
    
    @Column
    private String qinshuIdNumber; // 亲人身份证号
    
    @Column
    private String qinshuDelete; // 删除状态 (0:正常, 1:已删除)
    
    @Column
    private LocalDateTime createTime;
    
    @Column
    private LocalDateTime updateTime;
}