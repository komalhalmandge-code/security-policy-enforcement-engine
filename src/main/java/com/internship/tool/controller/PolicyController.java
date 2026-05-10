package com.internship.tool.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.internship.tool.entity.Policy;
import com.internship.tool.service.PolicyService;

@RestController
@RequestMapping("/api/policies")
@CrossOrigin(origins = "*")
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    // Create Policy
    @PostMapping
    public ResponseEntity<Policy> createPolicy(@RequestBody Policy policy) {

        Policy createdPolicy = policyService.createPolicy(policy);

        return new ResponseEntity<>(createdPolicy, HttpStatus.CREATED);
    }

    // Get All Policies
    @GetMapping
    public ResponseEntity<List<Policy>> getAllPolicies() {

        List<Policy> policies = policyService.getAllPolicies();

        return ResponseEntity.ok(policies);
    }

    // Get Policy By Id
    @GetMapping("/{id}")
    public ResponseEntity<Policy> getPolicyById(@PathVariable Long id) {

        Policy policy = policyService.getPolicyById(id);

        return ResponseEntity.ok(policy);
    }

    // Update Policy
    @PutMapping("/{id}")
    public ResponseEntity<Policy> updatePolicy(
            @PathVariable Long id,
            @RequestBody Policy updatedPolicy) {

        Policy policy = policyService.updatePolicy(id, updatedPolicy);

        return ResponseEntity.ok(policy);
    }

    // Delete Policy
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePolicy(@PathVariable Long id) {

        policyService.deletePolicy(id);

        return ResponseEntity.ok("Policy deleted successfully");
    }

    // Search By Status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Policy>> getPoliciesByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                policyService.getPoliciesByStatus(status));
    }

    // Search By Severity
    @GetMapping("/severity/{severity}")
    public ResponseEntity<List<Policy>> getPoliciesBySeverity(
            @PathVariable String severity) {

        return ResponseEntity.ok(
                policyService.getPoliciesBySeverity(severity));
    }

    // Search By Type
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Policy>> getPoliciesByType(
            @PathVariable String type) {

        return ResponseEntity.ok(
                policyService.getPoliciesByType(type));
    }

    // Search By Keyword
    @GetMapping("/search")
    public ResponseEntity<List<Policy>> searchPolicies(
            @RequestParam String keyword) {

        return ResponseEntity.ok(
                policyService.searchPolicies(keyword));
    }
}