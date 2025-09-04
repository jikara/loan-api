package com.softmint.repo;

import com.softmint.entity.ApprovalPolicy;
import com.softmint.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "approvalPolicies", path = "approval-policy")
public interface ApprovalPolicyRepo extends JpaRepository<ApprovalPolicy, UUID>, JpaSpecificationExecutor<ApprovalPolicy> {

}
