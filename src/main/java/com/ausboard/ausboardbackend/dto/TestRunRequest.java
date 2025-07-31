package com.ausboard.ausboardbackend.dto;

import java.time.LocalDateTime;
import java.util.UUID;


public class TestRunRequest {
    private UUID id;
    private String status;
    private String response;
    private String executionTime;
    private LocalDateTime runAt;
    private String testCaseTitle;

    public TestRunRequest(UUID id, String status, String response, String executionTime, LocalDateTime runAt, String testCaseTitle) {
        this.id = id;
        this.status = status;
        this.response = response;
        this.executionTime = executionTime;
        this.runAt = runAt;
        this.testCaseTitle = testCaseTitle;
    }
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }

    public String getExecutionTime() { return executionTime; }
    public void setExecutionTime(String executionTime) { this.executionTime = executionTime; }

    public LocalDateTime getRunAt() { return runAt; }
    public void setRunAt(LocalDateTime runAt) { this.runAt = runAt; }

    public String getTestCaseTitle() { return testCaseTitle; }
    public void setTestCaseTitle(String testCaseTitle) { this.testCaseTitle = testCaseTitle; }
}
