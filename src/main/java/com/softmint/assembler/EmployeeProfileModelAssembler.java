package com.softmint.assembler;

import com.softmint.entity.EmployeeProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EmployeeProfileModelAssembler implements RepresentationModelAssembler<EmployeeProfile, EntityModel<EmployeeProfile>> {

    private final EntityLinks entityLinks;

    @Override
    @NonNull
    public EntityModel<EmployeeProfile> toModel(@NonNull EmployeeProfile profile) {
        return EntityModel.of(profile,
                entityLinks.linkToItemResource(EmployeeProfile.class, profile.getId()).withSelfRel());
    }


}

