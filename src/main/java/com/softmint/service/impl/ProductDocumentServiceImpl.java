package com.softmint.service.impl;

import com.softmint.entity.ProductDocument;
import com.softmint.repo.ProductDocumentRepo;
import com.softmint.service.ProductDocumentService;
import com.softmint.specification.ProductDocumentSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProductDocumentServiceImpl implements ProductDocumentService {
    private final ProductDocumentRepo productDocumentRepo;

    @Override
    public Page<ProductDocument> findByFilters(String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return productDocumentRepo.findAll(pageable);
    }

    @Override
    public Page<ProductDocument> findByFilters(UUID productId, String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        Specification<ProductDocument> spec = Specification
                .allOf(ProductDocumentSpecification.hasProductId(productId))
                .and(ProductDocumentSpecification.createdBetween(startDate, endDate));
        return productDocumentRepo.findAll(spec, pageable);
    }
}
