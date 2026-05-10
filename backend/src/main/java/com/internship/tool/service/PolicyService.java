package com.internship.tool.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.internship.tool.entity.Policy;
import com.internship.tool.exception.ResourceNotFoundException;
import com.internship.tool.repository.PolicyRepository;
import com.internship.tool.exception.BadRequestException;


@Service
public class PolicyService {

    private final PolicyRepository policyRepository;

    public PolicyService(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    // Create Policy
    public Policy createPolicy(Policy policy) {

        validatePolicy(policy);

        return policyRepository.save(policy);
    }

    // Get All Policies
    public List<Policy> getAllPolicies() {
        return policyRepository.findAll();
    }

    // Get Policy By Id
    public Policy getPolicyById(Long id) {

        Optional<Policy> optionalPolicy = policyRepository.findById(id);

        if (optionalPolicy.isPresent()) {
            return optionalPolicy.get();
        }
            throw new ResourceNotFoundException(
        "Policy not found with id: " + id);
        
    }

    // Update Policy
    public Policy updatePolicy(Long id, Policy updatedPolicy) {

        Policy existingPolicy = getPolicyById(id);

        existingPolicy.setPolicyName(updatedPolicy.getPolicyName());
        existingPolicy.setPolicyType(updatedPolicy.getPolicyType());
        existingPolicy.setDescription(updatedPolicy.getDescription());
        existingPolicy.setSeverity(updatedPolicy.getSeverity());
        existingPolicy.setStatus(updatedPolicy.getStatus());
        existingPolicy.setCreatedBy(updatedPolicy.getCreatedBy());

        validatePolicy(existingPolicy);

        return policyRepository.save(existingPolicy);
    }

    // Delete Policy
    public void deletePolicy(Long id) {

        Policy existingPolicy = getPolicyById(id);

        policyRepository.delete(existingPolicy);
    }

    // Search By Status
    public List<Policy> getPoliciesByStatus(String status) {
        return policyRepository.findByStatus(status);
    }

    // Search By Severity
    public List<Policy> getPoliciesBySeverity(String severity) {
        return policyRepository.findBySeverity(severity);
    }

    // Search By Type
    public List<Policy> getPoliciesByType(String type) {
        return policyRepository.findByPolicyType(type);
    }

    // Search By Keyword
    public List<Policy> searchPolicies(String keyword) {
        return policyRepository.findByPolicyNameContainingIgnoreCase(keyword);
    }

    // Validation Method
    private void validatePolicy(Policy policy) {

        if (policy.getPolicyName() == null || policy.getPolicyName().trim().isEmpty()) {
            throw new BadRequestException("Policy name is required");
        }

        if (policy.getPolicyType() == null || policy.getPolicyType().trim().isEmpty()) {
            throw new BadRequestException("Policy type is required");
        }

        if (policy.getSeverity() == null || policy.getSeverity().trim().isEmpty()) {
            throw new BadRequestException("Severity is required");
        }

        if (policy.getStatus() == null || policy.getStatus().trim().isEmpty()) {
            throw new BadRequestException("Status is required");
        }
    }
}