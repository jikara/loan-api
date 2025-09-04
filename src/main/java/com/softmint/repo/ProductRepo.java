package com.softmint.repo;

import com.softmint.entity.Loan;
import com.softmint.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "products", path = "product")
public interface ProductRepo extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {

}
