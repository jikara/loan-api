package com.softmint.controller;

import com.softmint.assembler.InterestModelAssembler;
import com.softmint.entity.Interest;
import com.softmint.service.InterestService;
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
@RequestMapping("/api/v1/interest")
public class InterestController {
    private final InterestService interestService;
    private final InterestModelAssembler assembler;
    private final PagedResourcesAssembler<Interest> pagedResourcesAssembler;

    @GetMapping("/filter")
    public ResponseEntity<?> filter(
            @RequestParam(required = false) String searchKey,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            Pageable pageable
    ) {
        LocalDateTime start = DateTimeUtil.parseStartOfDay(startDate);
        LocalDateTime end = DateTimeUtil.parseEndOfDay(endDate);
        Page<Interest> page = interestService.findByFilters(searchKey, start, end, pageable);
        PagedModel<EntityModel<Interest>> model = pagedResourcesAssembler.toModel(page, assembler);
        return ResponseEntity.ok(model);
    }
}
