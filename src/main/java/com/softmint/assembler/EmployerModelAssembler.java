package com.softmint.assembler;

import com.softmint.entity.Employer;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EmployerModelAssembler implements RepresentationModelAssembler<Employer, EntityModel<Employer>> {

    private final EntityLinks entityLinks;

    @Override
    @NonNull
    public EntityModel<Employer> toModel(@NonNull Employer employer) {
        return EntityModel.of(employer,
                entityLinks.linkToItemResource(Employer.class, employer.getId()).withSelfRel());
    }


}

