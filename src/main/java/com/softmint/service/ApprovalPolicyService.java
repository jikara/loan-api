package com.softmint.service;

import com.softmint.entity.ApprovalPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface ApprovalPolicyService {
    Page<ApprovalPolicy> findByFilters(String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
