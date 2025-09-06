package com.softmint.controller;

import com.softmint.assembler.EmployerUserModelAssembler;
import com.softmint.entity.EmployerUser;
import com.softmint.service.EmployerUserService;
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
@RequestMapping("/api/v1/employer-user")
public class EmployerUserController {
    private final EmployerUserService employerUserService;
    private final EmployerUserModelAssembler assembler;
    private final PagedResourcesAssembler<EmployerUser> pagedResourcesAssembler;

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
        Page<EmployerUser> page = employerUserService.findEmployerUsersByFilters(employerId, searchKey, start, end, pageable);
        PagedModel<EntityModel<EmployerUser>> model = pagedResourcesAssembler.toModel(page, assembler);
        return ResponseEntity.ok(model);
    }
}
