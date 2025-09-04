package com.softmint.controller;

import com.softmint.assembler.DocumentModelAssembler;
import com.softmint.entity.Document;
import com.softmint.service.DocumentService;
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
@RequestMapping("/api/v1/document")
public class DocumentController {
    private final DocumentService documentService;
    private final DocumentModelAssembler assembler;
    private final PagedResourcesAssembler<Document> pagedResourcesAssembler;

    @GetMapping("/filter")
    public ResponseEntity<?> filter(
            @RequestParam(required = false) String searchKey,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            Pageable pageable
    ) {
        LocalDateTime start = DateTimeUtil.parseStartOfDay(startDate);
        LocalDateTime end = DateTimeUtil.parseEndOfDay(endDate);
        Page<Document> page = documentService.findByFilters(searchKey, start, end, pageable);
        PagedModel<EntityModel<Document>> model = pagedResourcesAssembler.toModel(page, assembler);
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
        Page<Document> page = documentService.findByFilters(productId, searchKey, start, end, pageable);
        PagedModel<EntityModel<Document>> model = pagedResourcesAssembler.toModel(page, assembler);
        return ResponseEntity.ok(model);
    }
}
