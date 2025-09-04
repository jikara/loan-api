package com.softmint.service;

import com.softmint.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;

public interface EmployeeService {
    Page<Employee> findByFilters(Authentication authentication, String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Employee create(Authentication authentication, Employee model);

}
