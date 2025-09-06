package com.softmint.assembler;

import com.softmint.controller.EmployeeController;
import com.softmint.controller.EmployerUserController;
import com.softmint.entity.Employer;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@Component
public class EmployerModelAssembler implements RepresentationModelAssembler<Employer, EntityModel<Employer>> {

    private final EntityLinks entityLinks;

    @Override
    @NonNull
    public EntityModel<Employer> toModel(@NonNull Employer employer) {
        UriComponentsBuilder usersBuilder = linkTo(methodOn(EmployerUserController.class)
                .filterByEmployerId(employer.getId(), null, null, null, null))
                .toUriComponentsBuilder();
        UriComponentsBuilder employeesBuilder = linkTo(methodOn(EmployeeController.class)
                .filterByEmployerId(employer.getId(), null, null, null, null))
                .toUriComponentsBuilder();

        return EntityModel.of(employer,
                entityLinks.linkToItemResource(Employer.class, employer.getId()).withSelfRel(),
                Link.of(employeesBuilder.buildAndExpand(employer.getId()).toUriString()).withRel("employees"),
                Link.of(usersBuilder.buildAndExpand(employer.getId()).toUriString()).withRel("employerUsers"));
    }


}

