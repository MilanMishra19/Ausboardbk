package com.ausboard.ausboardbackend.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ausboard.ausboardbackend.dto.TestCaseRequest;
import com.ausboard.ausboardbackend.entity.TestCase;
import com.ausboard.ausboardbackend.entity.TestRun;
import com.ausboard.ausboardbackend.repository.TestCaseRepository;
import com.ausboard.ausboardbackend.service.TestCaseService;

@RestController
@RequestMapping("/api/testcase")
public class TestCaseController {

    private final TestCaseRepository testCaseRepository;
    @Autowired
    private TestCaseService testCaseService;

    TestCaseController(TestCaseRepository testCaseRepository) {
        this.testCaseRepository = testCaseRepository;
    }

    @PostMapping
    public ResponseEntity<TestCase>createNewTestCase(@RequestBody TestCaseRequest request){
        TestCase testCase = new TestCase();
        testCase.setTitle(request.getTitle());
        testCase.setMethod(request.getMethod());
        testCase.setEndpoints(request.getEndpoints());
        testCase.setPriority(request.getPriority());
        testCase.setPayload(request.getPayload());
        testCase.setHeaders(request.getHeaders());
        testCase.setVariablesUsed(request.getVariablesUsed());
        testCase.setTestType(request.getTestType());
        TestCase created = testCaseService.createTestCase(request.getProjectId(), testCase);
        return ResponseEntity.ok(created);
    }
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<TestCase>> getTestCaseByProject(@PathVariable UUID projectId){
        List<TestCase> testCases = testCaseService.findTestCasesByProject(projectId);
        return ResponseEntity.ok(testCases);
    }
    @PostMapping("/run/{testcaseId}")
    public ResponseEntity<TestRun> runSingleTest(@PathVariable UUID testcaseId){
        TestRun testRun = testCaseService.runIndividualTestCase(testcaseId);
        return ResponseEntity.ok(testRun);
    }
    @PostMapping("/run-all/{projectId}")
    public ResponseEntity<List<TestRun>> runAllTests(@PathVariable UUID projectId){
        List<TestRun> testRuns = testCaseService.runAllTestsForProject(projectId);
        return ResponseEntity.ok(testRuns);
    }
    @DeleteMapping("/{testcaseId}")
    public ResponseEntity<String> deleteTestCase(@PathVariable UUID testcaseId){
        if(!testCaseRepository.existsById(testcaseId)){
            return ResponseEntity.notFound().build();
        }
        testCaseRepository.deleteById(testcaseId);
        return ResponseEntity.ok("Testcase along with its testruns deleted successfully");
    }
}
