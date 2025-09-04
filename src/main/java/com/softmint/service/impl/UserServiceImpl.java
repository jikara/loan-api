package com.softmint.service.impl;

import com.softmint.dto.TokenResponse;
import com.softmint.entity.Credential;
import com.softmint.entity.Permission;
import com.softmint.entity.User;
import com.softmint.model.AuthUser;
import com.softmint.repo.CredentialRepo;
import com.softmint.repo.UserRepo;
import com.softmint.service.CredentialService;
import com.softmint.service.JwtService;
import com.softmint.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepo;
    private final CredentialRepo credentialRepo;
    private final CredentialService credentialService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public TokenResponse login(String email, String password, String ipAddress) {
        AuthUser authUser = getAuthUser(email, password); // This might throw BadCredentialsException or UsernameNotFoundException
        // Successful login path
        Credential credential = getUserById(authUser.getId()).getCredential();
        credential.setFailedLoginAttempts(0); // Reset on success
        credential.setLastLoginIp(ipAddress);
        credential.setLastLoginAt(LocalDateTime.now());
        credentialRepo.save(credential);
        return new TokenResponse(
                jwtService.generateToken(authUser),
                jwtService.generateRefreshToken(authUser),
                "bearer"
        );
    }

    @Override
    public final AuthUser getAuthUser(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        return (AuthUser) authentication.getPrincipal();
    }

    @Override
    public User getUserById(UUID id) {
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public User findAccountByEmail(String email) {
        return null;
    }

    @Override
    public void saveToken(User user) {

    }

    @Override
    public void resetPassword(String email, String password) {

    }

    @Override
    public TokenResponse refresh(String jwt) {
        return null;
    }

    @Override
    public Set<String> getUserPermissions(UUID userId) {
        User user = userRepo.findById(userId).orElse(null);
        assert user != null;
        return user.getRole().getPermissions().stream()
                .map(Permission::getCode)  // or getKey(), depending on your field
                .collect(Collectors.toSet());

    }

    @Override
    public Page<User> findByFilters(String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return userRepo.findAll(pageable);
    }

    @Override
    public void changePassword(Credential credential, String password) {
        credential.setPassword(passwordEncoder.encode(password));
        credential.setMustChangePassword(false);
        credential.setLastPasswordChangedAt(LocalDateTime.now());
        credentialRepo.save(credential);
        // Emit log event
//        LogEvent logEvent = LogEvent.builder()
//                .source(this)
//                .eventType("CHANGE_PASSWORD")
//                .message("Credential " + credential.getEmail() + " changed password")
//                .build();
//        eventPublisher.publishLog(logEvent);
    }

    @Override
    public Optional<User> findById(UUID userId) {
        return userRepo.findById(userId);
    }

    @Override
    public User create(Authentication authentication, User model) {
        UUID authId = (UUID) authentication.getPrincipal();
        model.setCreatedBy(authId);
        //create credential
        Credential credential = credentialService.createUserCredential(model);
        model.setCredential(credential);
        return userRepo.save(model);
    }


}
