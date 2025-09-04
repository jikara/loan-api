package com.softmint.repo;

import com.softmint.entity.ApprovalStep;
import com.softmint.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "approvalSteps", path = "approval-step")
public interface ApprovalStepRepo extends JpaRepository<ApprovalStep, UUID>, JpaSpecificationExecutor<ApprovalStep> {

}
