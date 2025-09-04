package com.softmint.repo;

import com.softmint.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "employees", path = "employee")
public interface EmployeeRepo extends JpaRepository<Employee, UUID>, JpaSpecificationExecutor<Employee> {

    Page<Employee> findByEmployerId(UUID id, Pageable pageable);
}
