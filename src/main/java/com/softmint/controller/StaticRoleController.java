package com.softmint.controller;

import com.softmint.assembler.StaticRoleModelAssembler;
import com.softmint.entity.StaticRole;
import com.softmint.service.StaticRoleService;
import com.softmint.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/static-role")
public class StaticRoleController {

    private final StaticRoleService roleService;
    private final StaticRoleModelAssembler assembler;
    private final PagedResourcesAssembler<StaticRole> pagedResourcesAssembler;

    @GetMapping("/filter")
    public ResponseEntity<?> filter(
            @RequestParam(required = false) String searchKey,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            Pageable pageable
    ) {
        LocalDateTime start = DateTimeUtil.parseStartOfDay(startDate);
        LocalDateTime end = DateTimeUtil.parseEndOfDay(endDate);
        Page<StaticRole> page = roleService.findByFilters(searchKey, start, end, pageable);
        PagedModel<EntityModel<StaticRole>> model = pagedResourcesAssembler.toModel(page, assembler);
        return ResponseEntity.ok(model);
    }
}
