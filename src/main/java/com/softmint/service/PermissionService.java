package com.softmint.service;

import com.softmint.entity.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface PermissionService {
    Page<Permission> findAssignable(Authentication authentication, Pageable pageable);
}
