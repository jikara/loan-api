package com.softmint.repo;

import com.softmint.entity.EmployeeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "employeeUsers", path = "employee-user")
public interface EmployeeUserRepo extends JpaRepository<EmployeeUser, UUID>, JpaSpecificationExecutor<EmployeeUser> {

}
