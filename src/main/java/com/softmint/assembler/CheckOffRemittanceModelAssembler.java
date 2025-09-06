package com.softmint.assembler;

import com.softmint.entity.CheckOffRemittance;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CheckOffRemittanceModelAssembler implements RepresentationModelAssembler<CheckOffRemittance, EntityModel<CheckOffRemittance>> {

    private final EntityLinks entityLinks;

    @Override
    @NonNull
    public EntityModel<CheckOffRemittance> toModel(@NonNull CheckOffRemittance checkOffRemittance) {
        return EntityModel.of(checkOffRemittance,
                entityLinks.linkToItemResource(CheckOffRemittance.class, checkOffRemittance.getId()).withSelfRel());
    }


}

