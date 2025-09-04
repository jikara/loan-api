package com.softmint.assembler;

import com.softmint.entity.Document;
import com.softmint.entity.ProductDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductDocumentModelAssembler implements RepresentationModelAssembler<ProductDocument, EntityModel<ProductDocument>> {

    private final EntityLinks entityLinks;

    @Override
    @NonNull
    public EntityModel<ProductDocument> toModel(@NonNull ProductDocument productDocument) {
        return EntityModel.of(productDocument,
                entityLinks.linkToItemResource(Document.class, productDocument.getDocument().getId()).withRel("document"),
                entityLinks.linkToItemResource(ProductDocument.class, productDocument.getId()).withSelfRel());
    }


}

