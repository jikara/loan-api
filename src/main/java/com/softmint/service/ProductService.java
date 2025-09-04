package com.softmint.service;

import com.softmint.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ProductService {
    Page<Product> findByFilters(String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Product create(Product product);

    Product findById(UUID id);
}
