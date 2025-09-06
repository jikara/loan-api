package com.softmint.assembler;

import com.softmint.entity.Loan;
import com.softmint.entity.LoanApproval;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LoanApprovalModelAssembler implements RepresentationModelAssembler<LoanApproval, EntityModel<LoanApproval>> {

    private final EntityLinks entityLinks;

    @Override
    @NonNull
    public EntityModel<LoanApproval> toModel(@NonNull LoanApproval loanApproval) {
        return EntityModel.of(loanApproval,
                entityLinks.linkToItemResource(Loan.class, loanApproval.getLoan().getId()).withRel("loan"),
                entityLinks.linkToItemResource(LoanApproval.class, loanApproval.getId()).withSelfRel());
    }


}

