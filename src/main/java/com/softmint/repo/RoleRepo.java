package com.softmint.repo;

import com.softmint.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "roles", path = "role")
public interface RoleRepo extends JpaRepository<Role, UUID>, JpaSpecificationExecutor<Role> {
    @RestResource(path = "check-name", rel = "check-name")
    boolean existsByName(@Param("name") String name);
}
