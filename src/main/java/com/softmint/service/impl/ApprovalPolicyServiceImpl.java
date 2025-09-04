package com.softmint.service.impl;

import com.softmint.entity.ApprovalPolicy;
import com.softmint.repo.ApprovalPolicyRepo;
import com.softmint.service.ApprovalPolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ApprovalPolicyServiceImpl implements ApprovalPolicyService {
    private final ApprovalPolicyRepo approvalPolicyRepo;

    @Override
    public Page<ApprovalPolicy> findByFilters(String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return approvalPolicyRepo.findAll(pageable);
    }
}
