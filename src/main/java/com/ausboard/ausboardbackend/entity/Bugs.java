package com.ausboard.ausboardbackend.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table( name = "bugs")
public class Bugs {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    @ManyToOne
    @JoinColumn( name = "test_run_id")
    private TestRun testRun;
    private String severity;
    private String status;
    private String title;
    @ManyToOne
    @JoinColumn( name = "reported_by")
    private Users reportedBy;
    @ManyToOne
    @JoinColumn( name = "assigned_to")
    private Users assignedTo;
    private LocalDateTime createdAt = LocalDateTime.now();
    public UUID getId(){
        return id;
    }
    public void setId(UUID id){
        this.id = id;
    }
    public TestRun getTestRun(){
        return testRun;
    }
    public void setTestRun(TestRun testRun){
        this.testRun = testRun;
    }
    public String getSeverity(){
        return severity;
    }
    public void setSeverity(String severity){
        this.severity = severity;
    }
    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public Users getReportedBy(){
        return reportedBy;
    }
    public void setReportedBy(Users reportedBy){
        this.reportedBy = reportedBy;
    }
    public Users getAssignedTo(){
        return assignedTo;
    }
    public void setAssignedTo(Users assignedTo){
        this.assignedTo = assignedTo;
    }
    public LocalDateTime getCreatedAt(){
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }
}

