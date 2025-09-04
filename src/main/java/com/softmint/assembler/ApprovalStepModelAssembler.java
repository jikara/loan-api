package com.softmint.assembler;

import com.softmint.entity.ApprovalStep;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ApprovalStepModelAssembler implements RepresentationModelAssembler<ApprovalStep, EntityModel<ApprovalStep>> {

    private final EntityLinks entityLinks;

    @Override
    @NonNull
    public EntityModel<ApprovalStep> toModel(@NonNull ApprovalStep step) {
        return EntityModel.of(step,
                entityLinks.linkToItemResource(ApprovalStep.class, step.getId()).withSelfRel());
    }


}

