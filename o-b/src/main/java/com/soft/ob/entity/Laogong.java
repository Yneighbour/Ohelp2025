package com.soft.ob.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "laogong")
@Data
public class Laogong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String laogongUuid; // 老工唯一标识
    
    @Column(nullable = false)
    private String username; // 用户名
    
    @Column(nullable = false)
    private String password; // 密码
    
    @Column
    private String laogongName; // 老工姓名
    
    @Column
    private String laogongPhone; // 老工电话
    
    @Column
    private String laogongIdNumber; // 老工身份证号
    
    @Column
    private String laogongDelete; // 删除状态 (0:正常, 1:已删除)
    
    @Column
    private LocalDateTime createTime;
    
    @Column
    private LocalDateTime updateTime;
}