package com.softmint.service;

import com.softmint.entity.Credential;
import com.softmint.entity.EmployeeUser;
import com.softmint.entity.EmployerUser;
import com.softmint.entity.User;

public interface CredentialService {
    Credential createEmployerUserCredential(EmployerUser user);

    Credential createEmployeeUserCredential(EmployeeUser user);

    Credential createUserCredential(User user);
}
