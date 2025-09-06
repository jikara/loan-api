package com.softmint.service.impl;

import com.softmint.entity.Credential;
import com.softmint.entity.Employer;
import com.softmint.entity.EmployerUser;
import com.softmint.entity.Role;
import com.softmint.enums.StaticRoleType;
import com.softmint.repo.EmployerRepo;
import com.softmint.service.CredentialService;
import com.softmint.service.EmployerService;
import com.softmint.service.StaticRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EmployerServiceImpl implements EmployerService {
    private final EmployerRepo employerRepo;
    private final CredentialService credentialService;
    private final StaticRoleService staticRoleService;

    @Override
    public Page<Employer> findByFilters(String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return employerRepo.findAll(pageable);
    }

    @Override
    public Employer create(Employer model) {
        for (EmployerUser user : model.getUsers()) {
            //Set role []
            Role role = staticRoleService.findStaticRoleByType(StaticRoleType.EMPLOYER_ADMIN);
            user.setRole(role);
            //create credential
            Credential credential = credentialService.createEmployerUserCredential(user);
            user.setCredential(credential);
        }
        return employerRepo.save(model);
    }

    @Override
    public Employer findById(UUID id) {
        return employerRepo.findById(id).orElse(null);
    }
}
