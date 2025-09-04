package com.softmint.repo;

import com.softmint.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "loans", path = "loan")
public interface LoanRepo extends JpaRepository<Loan, UUID>, JpaSpecificationExecutor<Loan> {


}
