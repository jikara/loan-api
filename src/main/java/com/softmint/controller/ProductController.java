package com.softmint.controller;

import com.softmint.assembler.ProductModelAssembler;
import com.softmint.entity.Product;
import com.softmint.service.ProductService;
import com.softmint.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;
    private final ProductModelAssembler assembler;
    private final PagedResourcesAssembler<Product> pagedResourcesAssembler;

    @GetMapping("/filter")
    public ResponseEntity<?> filter(
            @RequestParam(required = false) String searchKey,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            Pageable pageable
    ) {
        LocalDateTime start = DateTimeUtil.parseStartOfDay(startDate);
        LocalDateTime end = DateTimeUtil.parseEndOfDay(endDate);
        Page<Product> page = productService.findByFilters(searchKey, start, end, pageable);
        PagedModel<EntityModel<Product>> model = pagedResourcesAssembler.toModel(page, assembler);
        return ResponseEntity.ok(model);
    }

    @PostMapping("create")
    public ResponseEntity<?> login(@RequestBody Product product) {
        Product managed = productService.create(product);
        return ResponseEntity.ok(assembler.toModel(managed));
    }
}
