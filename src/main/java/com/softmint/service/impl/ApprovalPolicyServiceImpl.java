package com.softmint.service.impl;

import com.softmint.dto.CreateApprovalPolicyRequest;
import com.softmint.dto.CreateApprovalStepRequest;
import com.softmint.entity.ApprovalPolicy;
import com.softmint.entity.ApprovalStep;
import com.softmint.entity.Role;
import com.softmint.repo.ApprovalPolicyRepo;
import com.softmint.service.ApprovalPolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ApprovalPolicyServiceImpl implements ApprovalPolicyService {
    private final ApprovalPolicyRepo approvalPolicyRepo;

    @Override
    public Page<ApprovalPolicy> findByFilters(String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return approvalPolicyRepo.findAll(pageable);
    }

    @Override
    public ApprovalPolicy create(ApprovalPolicy model) {
        return approvalPolicyRepo.save(model);
    }

    @Override
    @Transactional
    public ApprovalPolicy create(CreateApprovalPolicyRequest dto) {

        // 1. Create ApprovalPolicy entity
        ApprovalPolicy policy = new ApprovalPolicy();
        policy.setName(dto.getName());
        policy.setActive(true);

        List<ApprovalStep> steps = new ArrayList<>();

        // 2. Map DTO steps → entities
        for (int i = 0; i < dto.getSteps().size(); i++) {
            CreateApprovalStepRequest stepDto = dto.getSteps().get(i);

            ApprovalStep step = new ApprovalStep();
            step.setName(stepDto.getName());
            step.setSequence(i + 1);

            // Create Role reference (no DB call)
            Role role = new Role();
            role.setId(stepDto.getAssignedRoleId());
            step.setAssignedRole(role);
            step.setApprovalPolicy(policy);
            steps.add(step);
        }
        // 3. Link steps as a linked list
        for (int i = 0; i < steps.size() - 1; i++) {
            steps.get(i).setNextStep(steps.get(i + 1));
            steps.get(i + 1).setPreviousStep(steps.get(i));
        }
        // 4. Set firstStep in policy
        policy.setFirstStep(steps.isEmpty() ? null : steps.get(0));
        policy.setSteps(steps);
        // 5. Save policy and steps (cascade or manually)
        return approvalPolicyRepo.save(policy);
    }


}
