package com.softmint.service;

import com.softmint.entity.CheckOffRemittance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.UUID;

public interface CheckOffRemittanceService {
    Page<CheckOffRemittance> findByFilters(Authentication authentication, String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Page<CheckOffRemittance> findByFilters(UUID employerId, String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    CheckOffRemittance create(Authentication authentication, CheckOffRemittance model);

    CheckOffRemittance findById(UUID id);
}
