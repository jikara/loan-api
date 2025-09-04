package com.softmint.service.impl;

import com.softmint.entity.EmployerUser;
import com.softmint.entity.Role;
import com.softmint.repo.RoleRepo;
import com.softmint.service.RoleService;
import com.softmint.specification.RoleSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepo roleRepo;

    @Override
    public Page<Role> findByFilters(Authentication authentication,String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        Specification<Role> spec = Specification
                .allOf(RoleSpecification.isBaseRole())
                .and(RoleSpecification.hasSearchKey(searchKey))
                .and(RoleSpecification.createdBetween(startDate, endDate));
        return roleRepo.findAll(spec, pageable);
    }

    @Override
    public Role getEmployerUserRole(EmployerUser user) {
        return roleRepo.findAll().get(0);
    }

}
