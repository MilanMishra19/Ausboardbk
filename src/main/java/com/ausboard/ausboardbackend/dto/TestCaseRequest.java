package com.ausboard.ausboardbackend.dto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TestCaseRequest {
    
    private UUID projectId;

    private String title;

    private String method;

    private String endpoints;

    private String priority;
    
    private Map<String, Object> payload;

    private Map<String, String> headers;
    public List<String> fuzzedPayloads;
    private String variablesUsed;
    private String testType;

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(String endpoints) {
        this.endpoints = endpoints;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
    public String getVariablesUsed(){
        return variablesUsed;
    }
    public void setVariablesUsed(String variablesUsed){
        this.variablesUsed = variablesUsed;
    }
    public String getTestType(){
        return testType;
    }
    public void setTestType(String testType){
        this.testType = testType;
    }
    public List<String> getFuzzedPayloads(){
        return fuzzedPayloads;
    }
    public void setFuzzedPayloads(List<String> fuzzedPayloads){
        this.fuzzedPayloads = fuzzedPayloads;
    }
}
