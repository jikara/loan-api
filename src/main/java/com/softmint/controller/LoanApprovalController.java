package com.softmint.controller;

import com.softmint.assembler.LoanApprovalModelAssembler;
import com.softmint.entity.LoanApproval;
import com.softmint.service.LoanApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/loan-approval")
public class LoanApprovalController {
    private final LoanApprovalService loanApprovalService;
    private final LoanApprovalModelAssembler assembler;
    private final PagedResourcesAssembler<LoanApproval> pagedResourcesAssembler;

    @PostMapping("create")
    public ResponseEntity<?> create(
            Authentication authentication,
            @RequestBody LoanApproval model) {
        LoanApproval loanApproval = loanApprovalService.create(authentication, model);
        return ResponseEntity.ok(assembler.toModel(loanApproval));
    }

    @GetMapping("/filter/{loanId}")
    public ResponseEntity<?> getLoanApprovals(@PathVariable UUID loanId, Pageable pageable
    ) {
        Page<LoanApproval> page = loanApprovalService.findLoanApprovals(loanId, pageable);
        PagedModel<EntityModel<LoanApproval>> model = pagedResourcesAssembler.toModel(page, assembler);
        return ResponseEntity.ok(model);
    }
}
