package com.softmint.service.impl;

import com.softmint.entity.EmployeeUser;
import com.softmint.repo.EmployeeUserRepo;
import com.softmint.service.EmployeeUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EmployeeUserServiceImpl implements EmployeeUserService {


    private final EmployeeUserRepo employeeUserRepo;

    @Override
    public EmployeeUser findById(UUID id) {
        return employeeUserRepo.findById(id).orElse(null);
    }
}
