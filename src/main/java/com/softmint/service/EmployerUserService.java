package com.softmint.service;

import com.softmint.entity.EmployerUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface EmployerUserService {
    Page<EmployerUser> findEmployerUsersByFilters(UUID employerId, String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
