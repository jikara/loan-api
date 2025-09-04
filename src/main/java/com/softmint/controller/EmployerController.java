package com.softmint.controller;

import com.softmint.assembler.EmployerModelAssembler;
import com.softmint.entity.Employer;
import com.softmint.service.EmployerService;
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
@RequestMapping("/api/v1/employer")
public class EmployerController {
    private final EmployerService employerService;
    private final EmployerModelAssembler assembler;
    private final PagedResourcesAssembler<Employer> pagedResourcesAssembler;

    @GetMapping("/filter")
    public ResponseEntity<?> filter(
            @RequestParam(required = false) String searchKey,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            Pageable pageable
    ) {
        LocalDateTime start = DateTimeUtil.parseStartOfDay(startDate);
        LocalDateTime end = DateTimeUtil.parseEndOfDay(endDate);
        Page<Employer> page = employerService.findByFilters(searchKey, start, end, pageable);
        PagedModel<EntityModel<Employer>> model = pagedResourcesAssembler.toModel(page, assembler);
        return ResponseEntity.ok(model);
    }

    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody Employer model) {
        Employer employer = employerService.create(model);
        return ResponseEntity.ok(assembler.toModel(employer));
    }
}
