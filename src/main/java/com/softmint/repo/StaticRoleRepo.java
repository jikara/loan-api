package com.softmint.repo;

import com.softmint.entity.StaticRole;
import com.softmint.enums.StaticRoleType;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "staticRoles", path = "static-role")
public interface StaticRoleRepo extends JpaRepository<StaticRole, UUID>, JpaSpecificationExecutor<StaticRole> {

    @NotNull
    @Override
    Optional<StaticRole> findById(@NotNull UUID uuid);

    Optional<StaticRole> findStaticRoleByType(StaticRoleType type);
}
