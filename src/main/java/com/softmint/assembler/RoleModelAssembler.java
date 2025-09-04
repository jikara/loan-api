package com.softmint.assembler;

import com.softmint.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RoleModelAssembler implements RepresentationModelAssembler<Role, EntityModel<Role>> {

    private final EntityLinks entityLinks;

    @Override
    @NonNull
    public EntityModel<Role> toModel(@NonNull Role role) {
        return EntityModel.of(role,
                entityLinks.linkToItemResource(Role.class, role.getId()).withSelfRel());
    }


}

