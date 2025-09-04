package com.softmint.repo;

import com.softmint.entity.Loan;
import com.softmint.entity.ProductDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "productDocuments", path = "product-document")
public interface ProductDocumentRepo extends JpaRepository<ProductDocument, UUID>, JpaSpecificationExecutor<ProductDocument> {

}
