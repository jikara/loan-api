package com.softmint.assembler;

import com.softmint.entity.Interest;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InterestModelAssembler implements RepresentationModelAssembler<Interest, EntityModel<Interest>> {

    private final EntityLinks entityLinks;

    @Override
    @NonNull
    public EntityModel<Interest> toModel(@NonNull Interest interest) {
        return EntityModel.of(interest,
                entityLinks.linkToItemResource(Interest.class, interest.getId()).withSelfRel());
    }


}

