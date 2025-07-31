package com.ausboard.ausboardbackend.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table( name = "test_runs")
public class TestRun {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    @ManyToOne
    @JoinColumn( name = "test_case_id")
    @JsonBackReference
    private TestCase testCase;
    @ManyToOne
    @JoinColumn( name = "executed_by")
    private Users executedBy;
    private String status;
    @Column( name = "execution_time")
    private String executionTime;
    @Column( name = "run_at")
    private LocalDateTime runAt = LocalDateTime.now();
    @Column(columnDefinition = "TEXT")
    private String response;
    
    public UUID getId(){
        return id;
    }
    public void setId(UUID id){
        this.id = id;
    }
    public TestCase getTestCase(){
        return testCase;
    }
    public void setTestCase(TestCase testCase){
        this.testCase = testCase;
    }
    public Users getExecutedBy(){
        return executedBy;
    }
    public void setExecutedBy(Users executedBy){
        this.executedBy = executedBy;
    }
    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public String getExecutionTime(){
        return executionTime;
    }
    public void setExecutionTime(String executionTime){
        this.executionTime = executionTime;
    }
    public LocalDateTime getRunAt(){
        return runAt;
    }
    public void setRunAt(LocalDateTime runAt){
        this.runAt = runAt;
    }
    public String getResponse(){
        return response;
    }
    public void setResponse(String response){
        this.response = response;
    }
}
