package com.softmint.controller;

import com.softmint.assembler.PermissionModelAssembler;
import com.softmint.entity.Permission;
import com.softmint.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/permission")
public class PermissionController {
    private final PermissionService permissionService;
    private final PermissionModelAssembler assembler;
    private final PagedResourcesAssembler<Permission> pagedResourcesAssembler;


    @GetMapping("/assignable")
    public ResponseEntity<?> assignable(
            Authentication authentication,
            Pageable pageable
    ) {
        Page<Permission> page = permissionService.findAssignable(authentication, pageable);
        PagedModel<EntityModel<Permission>> model = pagedResourcesAssembler.toModel(page, assembler);
        return ResponseEntity.ok(model);
    }
}
