package com.softmint.service.impl;

import com.softmint.entity.EmployeeUser;
import com.softmint.entity.EmployerUser;
import com.softmint.entity.Permission;
import com.softmint.entity.User;
import com.softmint.repo.PermissionRepo;
import com.softmint.service.PermissionService;
import com.softmint.service.UserService;
import com.softmint.specification.PermissionSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepo permissionRepo;
    private final UserService userService;

    @Override
    public Page<Permission> findAssignable(Authentication authentication, Pageable pageable) {
        Specification<Permission> spec;
        UUID authId = (UUID) authentication.getPrincipal();
        User user = userService.getUserById(authId);
        assert user != null;
        if (user instanceof EmployerUser) {
            Set<String> excluded = new HashSet<>();
            spec = Specification.allOf(PermissionSpecification.excludeIds(excluded));
        } else if (user instanceof EmployeeUser) {
            return Page.empty();
        } else {
            spec = Specification.allOf(PermissionSpecification.isAssignable(true));
        }
        return permissionRepo.findAll(spec, pageable);
    }
}
