package com.softmint.repo;

import com.softmint.entity.CheckOffRemittance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "checkOffRemittances", path = "check-off-remittance")
public interface CheckOffRemittanceRepo extends JpaRepository<CheckOffRemittance, UUID>, JpaSpecificationExecutor<CheckOffRemittance> {

}
