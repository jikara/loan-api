package com.softmint.repo;

import com.softmint.entity.EmployeeProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "employeeProfiles", path = "employee-profile")
public interface EmployeeProfileRepo extends JpaRepository<EmployeeProfile, UUID>, JpaSpecificationExecutor<EmployeeProfile> {

}
