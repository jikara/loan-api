package com.softmint.assembler;

import com.softmint.entity.Employer;
import com.softmint.entity.EmployerUser;
import com.softmint.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EmployerUserModelAssembler implements RepresentationModelAssembler<EmployerUser, EntityModel<EmployerUser>> {

    private final EntityLinks entityLinks;

    @Override
    @NonNull
    public EntityModel<EmployerUser> toModel(@NonNull EmployerUser employerUser) {
        return EntityModel.of(employerUser,
                entityLinks.linkToItemResource(Role.class, employerUser.getRole().getId()).withRel("role"),
                entityLinks.linkToItemResource(Employer.class, employerUser.getEmployer().getId()).withRel("employer"),
                entityLinks.linkToItemResource(EmployerUser.class, employerUser.getId()).withSelfRel());
    }


}

