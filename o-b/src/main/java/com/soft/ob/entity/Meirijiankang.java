package com.soft.ob.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "meirijiankang")
@Data
public class Meirijiankang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String jiankangName; // 健康资讯名称
    
    @Column
    private String jiankangPhoto; // 健康资讯图片
    
    @Column(length = 1000)
    private String jiankangContent; // 健康资讯详情
    
    @Column
    private String jiankangDelete; // 删除状态 (0:正常, 1:已删除)
    
    @Column
    private LocalDateTime createTime;
    
    @Column
    private LocalDateTime updateTime;
}