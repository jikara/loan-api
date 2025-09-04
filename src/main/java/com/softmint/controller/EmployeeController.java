package com.softmint.controller;

import com.softmint.assembler.EmployeeModelAssembler;
import com.softmint.entity.Employee;
import com.softmint.service.EmployeeService;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeModelAssembler assembler;
    private final PagedResourcesAssembler<Employee> pagedResourcesAssembler;

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
        Page<Employee> page = employeeService.findByFilters(authentication, searchKey, start, end, pageable);
        PagedModel<EntityModel<Employee>> model = pagedResourcesAssembler.toModel(page, assembler);
        return ResponseEntity.ok(model);
    }

    @PostMapping("create")
    public ResponseEntity<?> create(
            Authentication authentication,
            @RequestBody Employee model) {
        Employee employee = employeeService.create(authentication, model);
        return ResponseEntity.ok(assembler.toModel(employee));
    }
}
