package com.softmint.repo;

import com.softmint.entity.Interest;
import com.softmint.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "interests", path = "interest")
public interface InterestRepo extends JpaRepository<Interest, UUID>, JpaSpecificationExecutor<Interest> {

}
