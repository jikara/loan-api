package com.softmint.controller;

import com.softmint.assembler.ProductDocumentModelAssembler;
import com.softmint.entity.ProductDocument;
import com.softmint.service.ProductDocumentService;
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
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/product-document")
public class ProductDocumentController {
    private final ProductDocumentService productDocumentService;
    private final ProductDocumentModelAssembler assembler;
    private final PagedResourcesAssembler<ProductDocument> pagedResourcesAssembler;

    @GetMapping("/filter")
    public ResponseEntity<?> filter(
            @RequestParam(required = false) String searchKey,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            Pageable pageable
    ) {
        LocalDateTime start = DateTimeUtil.parseStartOfDay(startDate);
        LocalDateTime end = DateTimeUtil.parseEndOfDay(endDate);
        Page<ProductDocument> page = productDocumentService.findByFilters(searchKey, start, end, pageable);
        PagedModel<EntityModel<ProductDocument>> model = pagedResourcesAssembler.toModel(page, assembler);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/filter/by-product/{productId}")
    public ResponseEntity<?> filterByProduct(
            @PathVariable UUID productId,
            @RequestParam(required = false) String searchKey,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            Pageable pageable
    ) {
        LocalDateTime start = DateTimeUtil.parseStartOfDay(startDate);
        LocalDateTime end = DateTimeUtil.parseEndOfDay(endDate);
        Page<ProductDocument> page = productDocumentService.findByFilters(productId, searchKey, start, end, pageable);
        PagedModel<EntityModel<ProductDocument>> model = pagedResourcesAssembler.toModel(page, assembler);
        return ResponseEntity.ok(model);
    }
}
