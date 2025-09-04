package com.softmint.assembler;

import com.softmint.entity.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PermissionModelAssembler implements RepresentationModelAssembler<Permission, EntityModel<Permission>> {

    private final EntityLinks entityLinks;

    @Override
    @NonNull
    public EntityModel<Permission> toModel(@NonNull Permission permission) {
        return EntityModel.of(permission,
                entityLinks.linkToItemResource(Permission.class, permission.getCode()).withSelfRel());
    }


}

