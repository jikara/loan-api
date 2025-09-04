package com.softmint.assembler;

import com.softmint.entity.Document;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DocumentModelAssembler implements RepresentationModelAssembler<Document, EntityModel<Document>> {

    private final EntityLinks entityLinks;

    @Override
    @NonNull
    public EntityModel<Document> toModel(@NonNull Document document) {
        return EntityModel.of(document,
                entityLinks.linkToItemResource(Document.class, document.getId()).withSelfRel());
    }


}

