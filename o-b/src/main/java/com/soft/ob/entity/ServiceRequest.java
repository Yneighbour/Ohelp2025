package com.soft.ob.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "service_request")
@Data
public class ServiceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(length = 1000)
    private String description;
    
    @Column
    private String type;
    
    @Column
    private String priority; // LOW, MEDIUM, HIGH, URGENT
    
    @ManyToOne
    @JoinColumn(name = "laoren_id")
    private Laoren laoren;
    
    @Column
    private String assignedStaff;
    
    @Column
    private String status; // PENDING, IN_PROGRESS, COMPLETED, CANCELLED
    
    @Column
    private LocalDateTime requestDate;
    
    @Column
    private LocalDateTime scheduledDate;
    
    @Column
    private LocalDateTime completedDate;
    
    @Column
    private String notes;
}