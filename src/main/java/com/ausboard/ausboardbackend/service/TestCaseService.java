package com.ausboard.ausboardbackend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ausboard.ausboardbackend.entity.Project;
import com.ausboard.ausboardbackend.entity.TestCase;
import com.ausboard.ausboardbackend.entity.TestRun;
import com.ausboard.ausboardbackend.repository.ProjectRepository;
import com.ausboard.ausboardbackend.repository.TestCaseRepository;
import com.ausboard.ausboardbackend.repository.TestRunRepository;

@Service
public class TestCaseService {
    @Autowired
    private TestCaseRepository testCaseRepository;
    @Autowired
    private TestRunRepository testRunRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private AutomationService automationService;

    public TestCase createTestCase(UUID projectId, TestCase testCase){
        Project project = projectRepository.findById(projectId)
        .orElseThrow(()->new RuntimeException("Project not found"));
        testCase.setCreatedAt(LocalDateTime.now());
        testCase.setProject(project);
        return testCaseRepository.save(testCase);
    }
    public List<TestCase> findTestCasesByProject(UUID projectId){
        return testCaseRepository.findByProject_ProjectId(projectId);
    }
    public TestRun runIndividualTestCase(UUID testCaseId){
        TestCase testCase = testCaseRepository.findById(testCaseId)
        .orElseThrow(()->new RuntimeException("Test case not found"));
        String fullUrl = testCase.getProject().getUrl() + testCase.getEndpoints();
        TestRun testRun = automationService.executeTest(fullUrl,testCase);
        return testRunRepository.save(testRun);
    }
    public List<TestRun>runAllTestsForProject(UUID projectId){
        Project project = projectRepository.findById(projectId)
        .orElseThrow(()->new RuntimeException("Project not found"));
        List<TestCase> testCases = testCaseRepository.findByProject_ProjectId(projectId);
        List<TestRun> runs = new ArrayList<>();
        for(TestCase tc :  testCases){
            String fullUrl = project.getUrl() + tc.getEndpoints();
            TestRun tr = automationService.executeTest(fullUrl,tc);
            runs.add(testRunRepository.save(tr));
        }
        return runs;
    }
}
