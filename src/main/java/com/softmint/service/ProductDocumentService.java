package com.softmint.service;

import com.softmint.entity.ProductDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ProductDocumentService {
    Page<ProductDocument> findByFilters(String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Page<ProductDocument> findByFilters(UUID productId, String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
