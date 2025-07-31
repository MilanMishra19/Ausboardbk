package com.ausboard.ausboardbackend.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ausboard.ausboardbackend.dto.TestRunRequest;
import com.ausboard.ausboardbackend.entity.TestRun;
import com.ausboard.ausboardbackend.repository.TestRunRepository;

@Service
public class TestRunService {
    @Autowired
    private TestRunRepository testRunRepository;
    public TestRun createTestRun(TestRun testRun){
        testRun.setId(UUID.randomUUID());
        return testRunRepository.save(testRun);
    }
    public List<TestRun> findTestRunByTestCase(UUID testCaseId){
        return testRunRepository.findByTestCase_Id(testCaseId);
    }
    public List<TestRunRequest> findTestRunByProject(UUID projectId) {
    List<TestRun> runs = testRunRepository.findTestRunByProjectId(projectId);
    return runs.stream().map(tr -> new TestRunRequest(
        tr.getId(),
        tr.getStatus(),
        tr.getResponse(),
        tr.getExecutionTime(),
        tr.getRunAt(),
        tr.getTestCase().getTitle()
    )).collect(Collectors.toList());
}

}