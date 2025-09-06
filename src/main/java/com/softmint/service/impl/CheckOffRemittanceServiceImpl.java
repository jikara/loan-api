package com.softmint.service.impl;

import com.softmint.entity.CheckOffRemittance;
import com.softmint.repo.CheckOffRemittanceRepo;
import com.softmint.service.CheckOffRemittanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CheckOffRemittanceServiceImpl implements CheckOffRemittanceService {
    private final CheckOffRemittanceRepo checkOffRemittanceRepo;

    @Override
    public Page<CheckOffRemittance> findByFilters(Authentication authentication, String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return null;
    }

    @Override
    public Page<CheckOffRemittance> findByFilters(UUID employerId, String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return null;
    }

    @Override
    public CheckOffRemittance create(Authentication authentication, CheckOffRemittance model) {
        return checkOffRemittanceRepo.save(model);
    }

    @Override
    public CheckOffRemittance findById(UUID id) {
        return checkOffRemittanceRepo.findById(id).orElse(null);
    }
}
