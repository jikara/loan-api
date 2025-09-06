package com.softmint.service;

import com.softmint.entity.Interest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.UUID;

public interface InterestService {
    Page<Interest> findByFilters(String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Interest getEmployerProductInterest(UUID employerId, UUID productId);

    Interest findNegotiated(Authentication authentication, UUID productId);
}
