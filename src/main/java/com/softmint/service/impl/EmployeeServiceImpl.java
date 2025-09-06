package com.softmint.service.impl;

import com.softmint.entity.*;
import com.softmint.enums.StaticRoleType;
import com.softmint.exception.CreateEmployeeException;
import com.softmint.repo.EmployeeRepo;
import com.softmint.service.CredentialService;
import com.softmint.service.EmployeeService;
import com.softmint.service.StaticRoleService;
import com.softmint.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepo employeeRepo;
    private final StaticRoleService staticRoleService;
    private final CredentialService credentialService;
    private final UserService userService;

    @Override
    public Page<Employee> findByFilters(Authentication authentication, String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        // 1️⃣ Get authorities from authentication object
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        // Check if user has "list_employees" permission
        boolean canViewAll = authorities.stream()
                .anyMatch(auth -> auth.getAuthority().equals("list_employees"));
        // 2️⃣ Determine user type
        UUID userId = UUID.fromString(authentication.getPrincipal().toString());
        User user = userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 3️⃣ Fetch filtered employees
        if (user instanceof EmployerUser employerUser) {
            // Employer users: only employees of this employer
            return employeeRepo.findByEmployerId(employerUser.getEmployer().getId(), pageable);
        } else if (user instanceof EmployeeUser employeeUser) {
            // Users with list_employees permission: all employees
            return employeeRepo.findAll(pageable);
        }
        return employeeRepo.findAll(pageable);
    }

    @Override
    public Page<Employee> findByFilters(UUID employerId, String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return employeeRepo.findByEmployerId(employerId, pageable);
    }

    @Override
    public Employee create(Authentication authentication, Employee model) {
        UUID authId = (UUID) authentication.getPrincipal();
        User authUser = userService.getUserById(authId);
        if (!(authUser instanceof EmployerUser employerUser)) {
            throw new CreateEmployeeException("Operation not permitted on current user");
        }
        EmployeeUser user = new EmployeeUser();
        user.setFirstName(model.getProfile().getFirstName());
        user.setLastName(model.getProfile().getLastName());
        user.setPhone(model.getProfile().getPhone());
        user.setEmail(model.getProfile().getEmail());
        model.setUser(user);
        user.setEmployee(model);
        //Set role []
        Role role = staticRoleService.findStaticRoleByType(StaticRoleType.EMPLOYEE);
        user.setRole(role);
        //create credential
        Credential credential = credentialService.createEmployeeUserCredential(user);
        user.setCredential(credential);
        //Set employer
        model.setEmployer(employerUser.getEmployer());
        return employeeRepo.save(model);
    }

    @Override
    public Employee findById(UUID id) {
        return employeeRepo.findById(id).orElse(null);
    }

}
