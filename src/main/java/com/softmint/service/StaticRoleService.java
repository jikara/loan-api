package com.softmint.service;

import com.softmint.entity.StaticRole;
import com.softmint.enums.StaticRoleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface StaticRoleService {
    Page<StaticRole> findByFilters(String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    StaticRole findStaticRoleByType(StaticRoleType type);
}
