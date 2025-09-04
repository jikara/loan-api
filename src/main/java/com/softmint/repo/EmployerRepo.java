package com.softmint.repo;

import com.softmint.entity.Employer;
import com.softmint.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "employers", path = "employer")
public interface EmployerRepo extends JpaRepository<Employer, UUID>, JpaSpecificationExecutor<Employer> {

}
