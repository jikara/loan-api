package com.softmint.service;

import com.softmint.entity.EmployeeUser;

import java.util.UUID;

public interface EmployeeUserService {
    EmployeeUser findById(UUID id);
}
