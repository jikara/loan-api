package com.softmint.assembler;

import com.softmint.entity.Employee;
import com.softmint.entity.Loan;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LoanModelAssembler implements RepresentationModelAssembler<Loan, EntityModel<Loan>> {

    private final EntityLinks entityLinks;

    @Override
    @NonNull
    public EntityModel<Loan> toModel(@NonNull Loan loan) {
        return EntityModel.of(loan,
                entityLinks.linkToItemResource(Employee.class, loan.getBorrower().getId()).withRel("borrower"),
                entityLinks.linkToItemResource(Loan.class, loan.getId()).withSelfRel());
    }


}

