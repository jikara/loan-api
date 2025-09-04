package com.softmint.service.impl;

import com.softmint.entity.Interest;
import com.softmint.repo.InterestRepo;
import com.softmint.service.InterestService;
import com.softmint.specification.InterestSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class InterestServiceImpl implements InterestService {
    private final InterestRepo interestRepo;

    @Override
    public Page<Interest> findByFilters(String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return interestRepo.findAll(pageable);
    }

    @Override
    public Interest getEmployerProductInterest(UUID employerId, UUID productId) {
        Specification<Interest> spec = Specification.allOf(InterestSpecification.hasEmployerAndProduct(employerId, productId));
        return interestRepo.findOne(spec).orElse(null);
    }
}
