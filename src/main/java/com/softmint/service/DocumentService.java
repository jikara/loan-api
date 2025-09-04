package com.softmint.service;

import com.softmint.entity.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface DocumentService {
    Page<Document> findByFilters(String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);


    Page<Document> findByFilters(UUID productId, String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
