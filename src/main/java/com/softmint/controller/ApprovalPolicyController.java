package com.softmint.controller;

import com.softmint.assembler.ApprovalPolicyModelAssembler;
import com.softmint.dto.CreateApprovalPolicyRequest;
import com.softmint.entity.ApprovalPolicy;
import com.softmint.service.ApprovalPolicyService;
import com.softmint.util.DateTimeUtil;
import jakarta.validation.Valid;
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
@RequestMapping("/api/v1/approval-policy")
public class ApprovalPolicyController {
    private final ApprovalPolicyService approvalPolicyService;
    private final ApprovalPolicyModelAssembler assembler;
    private final PagedResourcesAssembler<ApprovalPolicy> pagedResourcesAssembler;

    @GetMapping("/filter")
    public ResponseEntity<?> filter(
            @RequestParam(required = false) String searchKey,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            Pageable pageable
    ) {
        LocalDateTime start = DateTimeUtil.parseStartOfDay(startDate);
        LocalDateTime end = DateTimeUtil.parseEndOfDay(endDate);
        Page<ApprovalPolicy> page = approvalPolicyService.findByFilters(searchKey, start, end, pageable);
        PagedModel<EntityModel<ApprovalPolicy>> model = pagedResourcesAssembler.toModel(page, assembler);
        return ResponseEntity.ok(model);
    }

    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody @Valid CreateApprovalPolicyRequest model) {
        ApprovalPolicy approvalPolicy = approvalPolicyService.create(model);
        return ResponseEntity.ok(assembler.toModel(approvalPolicy));
    }
}
