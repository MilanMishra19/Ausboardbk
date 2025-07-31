package com.ausboard.ausboardbackend.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ausboard.ausboardbackend.entity.Automation;
import com.ausboard.ausboardbackend.entity.Project;
import com.ausboard.ausboardbackend.entity.TestCase;
import com.ausboard.ausboardbackend.entity.TestRun;
import com.ausboard.ausboardbackend.repository.AutomationRepository;
import com.ausboard.ausboardbackend.repository.TestCaseRepository;
import com.ausboard.ausboardbackend.repository.TestRunRepository;

@Service
public class AutomationService {

    @Autowired
    private AutomationRepository automationRepository;

    @Autowired
    private TestRunRepository testRunRepository;

    @Autowired
    private TestCaseRepository testCaseRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Automation createAutomation(Automation automation) {
        automation.setId(UUID.randomUUID());
        return automationRepository.save(automation);
    }
    public TestRun executeTest(String url, TestCase testCase) {
    testCase = testCaseRepository.findById(testCase.getId())
            .orElseThrow(() -> new RuntimeException("Test case not found"));
    boolean isFuzzTest = "fuzz".equalsIgnoreCase(testCase.getTestType());
    boolean isStressTest = "stress".equalsIgnoreCase(testCase.getTestType());
    boolean isLoadTest = "load".equalsIgnoreCase(testCase.getTestType());
    if(isFuzzTest){
        testCase.setPayload(generatedFuzzedPayload(testCase));
        testCase.setHeaders(generatedFuzzedHeaders());
    }
    if(isStressTest){
        return runStressTest(testCase);
    }
    if(isLoadTest){
        return runLoadTest(testCase,50,30);
    }
    Project project = testCase.getProject();
    if (project == null) {
        throw new RuntimeException("Test case is not linked to any Project");
    }

    String fullUrl = project.getUrl() + testCase.getEndpoints();
    HttpHeaders headers = new HttpHeaders();

    // Step 1: Convert headers from stored JSON
    if (testCase.getHeaders() != null && !testCase.getHeaders().isEmpty()) {
        try {
            JSONObject jsonHeaders = new JSONObject(testCase.getHeaders());
            for (String key : jsonHeaders.keySet()) {
                headers.add(key, jsonHeaders.getString(key));
            }
        } catch (JSONException e) {
            throw new RuntimeException("Invalid JSON format in headers");
        }
    }

    // Step 2: Decide content type
    MediaType contentType = headers.getContentType();
    if (contentType == null) {
        contentType = MediaType.APPLICATION_JSON;
        headers.setContentType(contentType);
    }

    // Step 3: Prepare HttpEntity based on content type
    HttpEntity<?> entity;
    if (contentType.equals(MediaType.APPLICATION_FORM_URLENCODED)) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        if (testCase.getPayload() != null && !testCase.getPayload().isEmpty()) {
            try {
                JSONObject jsonPayload = new JSONObject(testCase.getPayload());
                for (String key : jsonPayload.keySet()) {
                    formData.add(key, jsonPayload.getString(key));
                }
            } catch (JSONException e) {
                throw new RuntimeException("Invalid form-data payload format");
            }
        }
        entity = new HttpEntity<>(formData, headers);
    } else {
        entity = (testCase.getPayload() != null && !testCase.getPayload().isEmpty())
                ? new HttpEntity<>(testCase.getPayload(), headers)
                : new HttpEntity<>(headers);
    }

    // Step 4: Execute HTTP call
    long startTime = System.currentTimeMillis();
    ResponseEntity<String> response;
    try {
        response = restTemplate.exchange(
                fullUrl,
                HttpMethod.valueOf(testCase.getMethod().toUpperCase()),
                entity,
                String.class
        );
    } catch (Exception e) {
        long duration = System.currentTimeMillis() - startTime;
        TestRun failedRun = new TestRun();
        failedRun.setTestCase(testCase);
        failedRun.setStatus("FAILED");
        failedRun.setExecutionTime(String.valueOf(duration));
        failedRun.setResponse("Error: " + e.getMessage());
        return testRunRepository.save(failedRun);
    } 
    long duration = System.currentTimeMillis() - startTime;
    // Step 5: Save TestRun
    TestRun testRun = new TestRun();
    testRun.setTestCase(testCase);
    testRun.setStatus(response.getStatusCode().is2xxSuccessful() ? "PASSED" : "FAILED");
    testRun.setExecutionTime(String.valueOf(duration));
    testRun.setResponse(response.getBody());
    return testRunRepository.save(testRun);
}
    private Map<String, Object> generatedFuzzedPayload(TestCase testCase) {
        Map<String,Object> originalPayload = testCase.getPayload();
        Map<String, Object> fuzzedPayload = new java.util.HashMap<>();
        if(originalPayload!=null){
            fuzzedPayload.putAll(originalPayload);
        }
        if(testCase.getFuzzedPayloads()!=null){
            for(String field : testCase.getFuzzedPayloads()){
                fuzzedPayload.put(field,randomString(20));
            }
        }
        return fuzzedPayload;
        
    }

    private Map<String, String> generatedFuzzedHeaders() {
        Map<String, String> fuzzedHeaders = new java.util.HashMap<>();
        fuzzedHeaders.put("X-Fuzz-Header", randomString(30));
        fuzzedHeaders.put("X-Auth", randomString(20));
        return fuzzedHeaders;
    }
    private String randomString(int length){
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*(_";
        StringBuilder sb = new StringBuilder(length);
        for(int i=0;i<length;i++){
            int index = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }
    private TestRun runStressTest(TestCase testCase) {
    int threadCount = 10;
    int requestsPerThread = 20;

    Project project = testCase.getProject();
    if (project == null) {
        throw new RuntimeException("Test case is not linked to any Project");
    }

    String fullUrl = project.getUrl() + testCase.getEndpoints();
    HttpMethod method = HttpMethod.valueOf(testCase.getMethod().toUpperCase());
    HttpHeaders headers = new HttpHeaders();
    if (testCase.getHeaders() != null) {
        testCase.getHeaders().forEach(headers::add);
    }

    HttpEntity<?> entity = (testCase.getPayload() != null && !testCase.getPayload().isEmpty())
            ? new HttpEntity<>(testCase.getPayload(), headers)
            : new HttpEntity<>(headers);

    int[] successCount = {0};
    int[] failureCount = {0};

    long startTime = System.currentTimeMillis();

    Thread[] threads = new Thread[threadCount];

    for (int i = 0; i < threadCount; i++) {
        threads[i] = new Thread(() -> {
            for (int j = 0; j < requestsPerThread; j++) {
                try {
                    ResponseEntity<String> response = restTemplate.exchange(
                            fullUrl, method, entity, String.class);
                    if (response.getStatusCode().is2xxSuccessful()) {
                        synchronized (successCount) {
                            successCount[0]++;
                        }
                    } else {
                        synchronized (failureCount) {
                            failureCount[0]++;
                        }
                    }
                } catch (Exception e) {
                    synchronized (failureCount) {
                        failureCount[0]++;
                    }
                }
            }
        });
        threads[i].start();
    }

    for (Thread thread : threads) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    long duration = System.currentTimeMillis() - startTime;

    String responseSummary = String.format(
            "Stress Test Complete:\nThreads: %d\nRequests/Thread: %d\nTotal: %d\nSuccess: %d\nFailed: %d\nDuration: %d ms",
            threadCount, requestsPerThread, threadCount * requestsPerThread,
            successCount[0], failureCount[0], duration
    );

    TestRun run = new TestRun();
    run.setTestCase(testCase);
    run.setStatus(failureCount[0] == 0 ? "PASSED" : "FAILED");
    run.setExecutionTime(String.valueOf(duration));
    run.setResponse(responseSummary);

    return testRunRepository.save(run);
}
    private TestRun runLoadTest(TestCase testCase, int threadCount, int durationInSeconds){
    Project project = testCase.getProject();
    if (project == null) {
        throw new RuntimeException("TestCase not linked with any project");
    }

    String fullUrl = project.getUrl() + testCase.getEndpoints();
    HttpMethod method = HttpMethod.valueOf(testCase.getMethod().toUpperCase());
    ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

    AtomicInteger passed = new AtomicInteger(0);
    AtomicInteger failed = new AtomicInteger(0);
    AtomicInteger total = new AtomicInteger(0);
    long endTime = System.currentTimeMillis() + durationInSeconds * 1000L;
    List<Long> latencies = Collections.synchronizedList(new ArrayList<>());

    Runnable task = () -> {
        while (System.currentTimeMillis() < endTime) {
            try {
                HttpHeaders headers = new HttpHeaders();
                if (testCase.getHeaders() != null) {
                    testCase.getHeaders().forEach(headers::add);
                }
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<?> entity = (testCase.getPayload() != null && !testCase.getPayload().isEmpty())
                        ? new HttpEntity<>(testCase.getPayload(), headers)
                        : new HttpEntity<>(headers);

                long start = System.nanoTime();
                ResponseEntity<String> response = restTemplate.exchange(fullUrl, method, entity, String.class);
                long end = System.nanoTime();
                latencies.add((end - start) / 1_000_000); 

                if (response.getStatusCode().is2xxSuccessful()) {
                    passed.incrementAndGet();
                } else {
                    failed.incrementAndGet();
                }
            } catch (Exception e) {
                failed.incrementAndGet();
            }
            total.incrementAndGet();
        }
    };

    for (int i = 0; i < threadCount; i++) {
        executorService.submit(task);
    }
    executorService.shutdown();
    try {
        executorService.awaitTermination(durationInSeconds + 5, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
    long totalLatency = latencies.stream().mapToLong(Long::longValue).sum();
    long maxLatency = latencies.stream().mapToLong(Long::longValue).max().orElse(0);
    long minLatency = latencies.stream().mapToLong(Long::longValue).min().orElse(0);
    double avgLatency = latencies.isEmpty() ? 0 : totalLatency / (double) latencies.size();
    double throughput = total.get() / (double) durationInSeconds;

    String responseSummary = String.format(
        "Load Test Results =\nTotal: %d\nPassed: %d\nFailed: %d\nAvg Latency: %.2f ms\nMin: %d ms\nMax: %d ms\nThroughput: %.2f req/s",
        total.get(), passed.get(), failed.get(), avgLatency, minLatency, maxLatency, throughput
    );

    TestRun result = new TestRun();
    result.setTestCase(testCase);
    result.setStatus(failed.get() == 0 ? "PASSED" : "FAILED");
    result.setExecutionTime(durationInSeconds + "s");
    result.setResponse(responseSummary);
    return testRunRepository.save(result);
}

}
