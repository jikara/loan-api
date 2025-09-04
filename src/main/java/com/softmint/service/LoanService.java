package com.softmint.service;

import com.softmint.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.UUID;

public interface LoanService {
    Page<Loan> findByFilters(String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Page<Loan> findByFilters(Authentication authentication, String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Loan create(Authentication authentication, Loan model);

    Loan getEmployeeExistingUnsettledLoan(UUID employeeId);

    Loan findById(Authentication authentication, UUID id);
}
