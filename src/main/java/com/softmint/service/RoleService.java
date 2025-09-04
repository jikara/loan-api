package com.softmint.service;

import com.softmint.entity.EmployerUser;
import com.softmint.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;

public interface RoleService {
    Page<Role> findByFilters(Authentication authentication, String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Role getEmployerUserRole(EmployerUser user);
}
