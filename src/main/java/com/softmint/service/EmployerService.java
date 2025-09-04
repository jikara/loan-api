package com.softmint.service;

import com.softmint.entity.Employer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface EmployerService {
    Page<Employer> findByFilters(String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Employer create(Employer model);
}
