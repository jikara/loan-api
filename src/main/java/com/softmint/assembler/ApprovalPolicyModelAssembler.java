package com.softmint.assembler;

import com.softmint.entity.ApprovalPolicy;
import com.softmint.entity.ApprovalStep;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ApprovalPolicyModelAssembler implements RepresentationModelAssembler<ApprovalPolicy, EntityModel<ApprovalPolicy>> {

    private final EntityLinks entityLinks;

    @Override
    @NonNull
    public EntityModel<ApprovalPolicy> toModel(@NonNull ApprovalPolicy policy) {
        return EntityModel.of(policy,
                entityLinks.linkToItemResource(ApprovalStep.class, policy.getFirstStep().getId()).withRel("firstStep"),
                entityLinks.linkToItemResource(ApprovalPolicy.class, policy.getId()).withSelfRel());
    }


}

