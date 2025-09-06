package com.softmint.assembler;

import com.softmint.entity.ApprovalPolicy;
import com.softmint.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductModelAssembler implements RepresentationModelAssembler<Product, EntityModel<Product>> {

    private final EntityLinks entityLinks;

    @Override
    @NonNull
    public EntityModel<Product> toModel(@NonNull Product product) {
        EntityModel<Product> model = EntityModel.of(product,
                entityLinks.linkToItemResource(Product.class, product.getId()).withSelfRel());

        if (product.getApprovalPolicy() != null) {
            model.add(
                    entityLinks.linkToItemResource(ApprovalPolicy.class, product.getApprovalPolicy().getId())
                            .withRel("approvalPolicy")
            );
        }

        return model;
    }



}

