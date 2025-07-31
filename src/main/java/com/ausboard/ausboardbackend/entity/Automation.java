package com.ausboard.ausboardbackend.entity;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table( name = "automation")
public class Automation {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    @ManyToOne
    @JoinColumn( name = "project_id")
    private Project project;
    private String schedule;
    private String status;
    @Column( name = "last_run")
    private Date lastRun;
    @Column( name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public UUID getId(){
        return id;
    }
    public void setId(UUID id){
        this.id = id;
    }
    public Project getProject(){
        return project;
    }
    public void setProject(Project project){
        this.project = project;
    }
    public String getSchedule(){
        return schedule;
    }
    public void setSchedule(String schedule){
        this.schedule = schedule;
    }
    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public Date getLastRun(){
        return lastRun;
    }
    public void setLastRun(Date lastRun){
        this.lastRun = lastRun;
    }
    public LocalDateTime getCreatedAt(){
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }
}

