package com.softmint.repo;

import com.softmint.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "permissions", path = "permission")
public interface PermissionRepo extends JpaRepository<Permission, String>, JpaSpecificationExecutor<Permission> {

}
