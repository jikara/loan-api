package com.softmint.service.impl;

import com.softmint.entity.StaticRole;
import com.softmint.enums.StaticRoleType;
import com.softmint.repo.StaticRoleRepo;
import com.softmint.service.StaticRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class StaticRoleServiceImpl implements StaticRoleService {
    private final StaticRoleRepo staticRoleRepo;

    @Override
    public Page<StaticRole> findByFilters(String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return staticRoleRepo.findAll(pageable);
    }

    @Override
    public StaticRole findStaticRoleByType(StaticRoleType type) {
        return staticRoleRepo.findStaticRoleByType(type).orElse(null);
    }

}
