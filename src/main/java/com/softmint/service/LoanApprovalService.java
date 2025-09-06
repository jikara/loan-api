package com.softmint.service;

import com.softmint.entity.LoanApproval;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.UUID;

public interface LoanApprovalService {
    Page<LoanApproval> findByFilters(String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Page<LoanApproval> findByFilters(Authentication authentication, String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    LoanApproval create(Authentication authentication, LoanApproval model);

    LoanApproval findById(Authentication authentication, UUID loanApprovalId);

    Page<LoanApproval> findLoanApprovals(UUID loanId, Pageable pageable);
}
