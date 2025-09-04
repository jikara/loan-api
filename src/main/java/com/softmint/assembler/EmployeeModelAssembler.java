package com.softmint.assembler;

import com.softmint.entity.Employee;
import com.softmint.entity.EmployeeProfile;
import com.softmint.entity.Employer;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EmployeeModelAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>> {

    private final EntityLinks entityLinks;

    @Override
    @NonNull
    public EntityModel<Employee> toModel(@NonNull Employee employee) {
        return EntityModel.of(employee,
                entityLinks.linkToItemResource(Employer.class, employee.getEmployer().getId()).withRel("employer"),
                entityLinks.linkToItemResource(EmployeeProfile.class, employee.getProfile().getId()).withRel("profile"),
                entityLinks.linkToItemResource(Employee.class, employee.getId()).withSelfRel());
    }

}

