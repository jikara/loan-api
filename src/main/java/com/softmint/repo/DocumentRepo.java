package com.softmint.repo;

import com.softmint.entity.Document;
import com.softmint.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "documents", path = "document")
public interface DocumentRepo extends JpaRepository<Document, UUID>, JpaSpecificationExecutor<Document> {

}
