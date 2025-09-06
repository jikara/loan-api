package com.softmint.service;

import com.softmint.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.UUID;

public interface EmployeeService {
    Page<Employee> findByFilters(Authentication authentication, String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);


    Page<Employee> findByFilters(UUID employerId, String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Employee create(Authentication authentication, Employee model);

    Employee findById(UUID id);
}
