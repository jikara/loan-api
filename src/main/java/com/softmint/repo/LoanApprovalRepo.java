package com.softmint.repo;

import com.softmint.entity.LoanApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "loanApprovals", path = "loan-approval")
public interface LoanApprovalRepo extends JpaRepository<LoanApproval, UUID>, JpaSpecificationExecutor<LoanApproval> {


}
