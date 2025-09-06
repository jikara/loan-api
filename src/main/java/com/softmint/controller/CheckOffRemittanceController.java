package com.softmint.controller;

import com.softmint.assembler.CheckOffRemittanceModelAssembler;
import com.softmint.entity.CheckOffRemittance;
import com.softmint.service.CheckOffRemittanceService;
import com.softmint.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/check-off-remittance")
public class CheckOffRemittanceController {
    private final CheckOffRemittanceService checkOffRemittanceService;
    private final CheckOffRemittanceModelAssembler assembler;
    private final PagedResourcesAssembler<CheckOffRemittance> pagedResourcesAssembler;

    @GetMapping("{id}")
    public ResponseEntity<?> find(@PathVariable UUID id) {
        CheckOffRemittance checkOffRemittance = checkOffRemittanceService.findById(id);
        return ResponseEntity.ok(assembler.toModel(checkOffRemittance));
    }


    @GetMapping("/filter")
    public ResponseEntity<?> filter(
            Authentication authentication,
            @RequestParam(required = false) String searchKey,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            Pageable pageable
    ) {
        LocalDateTime start = DateTimeUtil.parseStartOfDay(startDate);
        LocalDateTime end = DateTimeUtil.parseEndOfDay(endDate);
        Page<CheckOffRemittance> page = checkOffRemittanceService.findByFilters(authentication, searchKey, start, end, pageable);
        PagedModel<EntityModel<CheckOffRemittance>> model = pagedResourcesAssembler.toModel(page, assembler);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/filter/{employerId}")
    public ResponseEntity<?> filterByEmployerId(
            @PathVariable UUID employerId,
            @RequestParam(required = false) String searchKey,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            Pageable pageable
    ) {
        LocalDateTime start = DateTimeUtil.parseStartOfDay(startDate);
        LocalDateTime end = DateTimeUtil.parseEndOfDay(endDate);
        Page<CheckOffRemittance> page = checkOffRemittanceService.findByFilters(employerId, searchKey, start, end, pageable);
        PagedModel<EntityModel<CheckOffRemittance>> model = pagedResourcesAssembler.toModel(page, assembler);
        return ResponseEntity.ok(model);
    }

    @PostMapping("create")
    public ResponseEntity<?> create(
            Authentication authentication,
            @RequestBody CheckOffRemittance model) {
        CheckOffRemittance checkOffRemittance = checkOffRemittanceService.create(authentication, model);
        return ResponseEntity.ok(assembler.toModel(checkOffRemittance));
    }
}
