package com.softmint.repo;

import com.softmint.entity.EmployerUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "users", path = "employer-user")
public interface EmployerUserRepo extends JpaRepository<EmployerUser, UUID>, JpaSpecificationExecutor<EmployerUser> {

}
