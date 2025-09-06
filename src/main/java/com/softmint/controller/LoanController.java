package com.softmint.controller;

import com.softmint.assembler.LoanModelAssembler;
import com.softmint.dto.CreateLoanRequest;
import com.softmint.entity.Loan;
import com.softmint.mapper.LoanMapper;
import com.softmint.service.LoanService;
import com.softmint.util.DateTimeUtil;
import jakarta.validation.Valid;
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
@RequestMapping("/api/v1/loan")
public class LoanController {
    private final LoanService loanService;
    private final LoanModelAssembler assembler;
    private final PagedResourcesAssembler<Loan> pagedResourcesAssembler;
    private final LoanMapper loanMapper;


    @GetMapping("{id}")
    public ResponseEntity<EntityModel<Loan>> find(
            Authentication authentication,
            @PathVariable UUID id
    ) {
        Loan loan = loanService.findById(authentication, id);
        return ResponseEntity.ok(assembler.toModel(loan));
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
        Page<Loan> page = loanService.findByFilters(authentication, searchKey, start, end, pageable);
        PagedModel<EntityModel<Loan>> model = pagedResourcesAssembler.toModel(page, assembler);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/approvals/filter")
    public ResponseEntity<?> filterApprovals(
            Authentication authentication,
            @RequestParam(required = false) String searchKey,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            Pageable pageable
    ) {
        LocalDateTime start = DateTimeUtil.parseStartOfDay(startDate);
        LocalDateTime end = DateTimeUtil.parseEndOfDay(endDate);
        Page<Loan> page = loanService.findPendingApprovalsByFilters(authentication, searchKey, start, end, pageable);
        PagedModel<EntityModel<Loan>> model = pagedResourcesAssembler.toModel(page, assembler);
        return ResponseEntity.ok(model);
    }

    @PostMapping("create")
    public ResponseEntity<?> create(
            Authentication authentication,
            @RequestBody @Valid CreateLoanRequest model) {
        Loan mapped = loanMapper.mapFromDto(model);
        Loan loan = loanService.create(authentication, mapped);
        return ResponseEntity.ok(assembler.toModel(loan));
    }
}
