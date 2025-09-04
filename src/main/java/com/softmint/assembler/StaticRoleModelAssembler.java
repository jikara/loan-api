package com.softmint.assembler;

import com.softmint.entity.StaticRole;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StaticRoleModelAssembler implements RepresentationModelAssembler<StaticRole, EntityModel<StaticRole>> {

    private final EntityLinks entityLinks;

    @Override
    @NonNull
    public EntityModel<StaticRole> toModel(@NonNull StaticRole staticRole) {
        return EntityModel.of(staticRole,
                entityLinks.linkToItemResource(StaticRole.class, staticRole.getId()).withSelfRel());
    }


}

