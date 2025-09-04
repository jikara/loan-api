package com.softmint.service.impl;

import com.softmint.entity.Product;
import com.softmint.repo.ProductRepo;
import com.softmint.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;

    @Override
    public Page<Product> findByFilters(String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return productRepo.findAll(pageable);
    }

    @Override
    public Product create(Product product) {
        return productRepo.save(product);
    }

    @Override
    public Product findById(UUID id) {
        return productRepo.findById(id).orElse(null);
    }
}
