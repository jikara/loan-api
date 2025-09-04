package com.softmint.service.impl;

import com.softmint.entity.Credential;
import com.softmint.entity.EmployeeUser;
import com.softmint.entity.EmployerUser;
import com.softmint.entity.User;
import com.softmint.service.CredentialService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CredentialServiceImpl implements CredentialService {
    private final PasswordEncoder passwordEncoder;

    @Override
    public Credential createEmployerUserCredential(EmployerUser user) {
        Credential credential = new Credential();
        credential.setEmail(user.getEmail());
        credential.setPassword(passwordEncoder.encode("12345678"));
        return credential;
    }

    @Override
    public Credential createEmployeeUserCredential(EmployeeUser user) {
        Credential credential = new Credential();
        credential.setEmail(user.getEmail());
        credential.setPassword(passwordEncoder.encode("12345678"));
        return credential;
    }

    @Override
    public Credential createUserCredential(User user) {
        Credential credential = new Credential();
        credential.setEmail(user.getEmail());
        credential.setPassword(passwordEncoder.encode("12345678"));
        return credential;
    }
}
