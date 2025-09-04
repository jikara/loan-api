package com.softmint.repo;

import com.softmint.entity.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface CredentialRepo extends JpaRepository<Credential, UUID> , JpaSpecificationExecutor<Credential> {

    Optional<Credential> findByEmail(String email);

    boolean existsByEmailIgnoreCase(String email);
}
