package com.softmint.service.impl;

import com.softmint.entity.EmployerUser;
import com.softmint.repo.EmployerUserRepo;
import com.softmint.service.EmployerUserService;
import com.softmint.specification.EmployerUserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EmployerUserServiceImpl implements EmployerUserService {

    private final EmployerUserRepo employerUserRepo;

    @Override
    public Page<EmployerUser> findEmployerUsersByFilters(UUID employerId, String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        Specification<EmployerUser> specification = Specification
                .allOf(EmployerUserSpecification.hasEmployer(employerId))
                .and(EmployerUserSpecification.hasSearchKey(searchKey))
                .and(EmployerUserSpecification.createdBetween(startDate, endDate));
        return employerUserRepo.findAll(specification, pageable);
    }
}
