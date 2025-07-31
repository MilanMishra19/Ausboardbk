package com.ausboard.ausboardbackend.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table( name = "test_cases")
public class TestCase {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @ManyToOne
    @JoinColumn( name = "project_id")
    @JsonBackReference
    private Project project;
    @OneToMany(mappedBy = "testCase", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<TestRun> testRun;
    private String title;
    private String priority;
    private String status;
    private String endpoints;
    @Column(columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON) 
    private Map<String,String> headers;
    @Column(columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private String variablesUsed;
    @Column( name = "test_type")
    private String testType;
    @Column(columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON) 
    private Map<String,Object> payload;
    @Column(columnDefinition = "json" ,name="fuzz_fields")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> fuzzedPayloads;
    private String method;

    @Column( name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public TestCase() {
    }

    public TestCase(Project project, String title, String priority, String status, String endpoints, String method) {
        this.project = project;
        this.title = title;
        this.priority = priority;
        this.status = status;
        this.endpoints = endpoints;
        this.method = method;
    }

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
    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public String getpriority(){
        return priority;
    }
    public void setPriority(String priority){
        this.priority = priority;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public LocalDateTime getCreatedAt(){
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }
    public String getMethod(){
        return method;
    }
    public void setMethod(String method){
        this.method = method;
    }
    public Map<String,String>getHeaders(){
        return headers;
    }
    public void setHeaders(Map<String,String>headers){
        this.headers = headers;
    }
    public Map<String,Object>getPayload(){
        return payload;
    }
    public void setPayload(Map<String,Object>payload){
        this.payload = payload;
    }
    public String getEndpoints(){
        return endpoints;
    }
    public void setEndpoints(String endpoints){
        this.endpoints = endpoints;
    }
    public String getTestType(){
        return testType;
    }
    public void setTestType(String testType){
        this.testType = testType;
    }
    public String getVariablesUsed(){
        return variablesUsed;
    }
    public void setVariablesUsed(String variablesUsed) {
        this.variablesUsed= variablesUsed;
    }
    public List<String> getFuzzedPayloads(){
        return fuzzedPayloads;
    }
    public void setFuzzedPayloads(List<String> fuzzedPayloads){
        this.fuzzedPayloads = fuzzedPayloads;
    }
    public List<TestRun> getTestRun() {
        return testRun;
    }
    public void setTestRun(List<TestRun> testRun) {
        this.testRun = testRun;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestCase testCase = (TestCase) o;
        return Objects.equals(id, testCase.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); 
    }
}