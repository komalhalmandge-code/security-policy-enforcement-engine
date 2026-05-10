package com.internship.tool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.internship.tool.entity.Policy;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {

    List<Policy> findByStatus(String status);

    List<Policy> findBySeverity(String severity);

    List<Policy> findByPolicyType(String policyType);

    List<Policy> findByPolicyNameContainingIgnoreCase(String keyword);
}