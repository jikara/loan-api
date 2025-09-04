package com.softmint.service.impl;

import com.softmint.entity.Document;
import com.softmint.repo.DocumentRepo;
import com.softmint.service.DocumentService;
import com.softmint.specification.DocumentSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepo documentRepo;

    @Override
    public Page<Document> findByFilters(String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return documentRepo.findAll(pageable);
    }

    @Override
    public Page<Document> findByFilters(UUID productId, String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        Specification<Document> specification = Specification
                .allOf(DocumentSpecification.hasProductId(productId)
                );
        return documentRepo.findAll(specification, pageable);
    }
}
