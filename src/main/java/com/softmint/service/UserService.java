package com.softmint.service;

import com.softmint.dto.TokenResponse;
import com.softmint.entity.Credential;
import com.softmint.entity.Employee;
import com.softmint.entity.User;
import com.softmint.model.AuthUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserService {
    TokenResponse login(String email, String password, String ipAddress);

    AuthUser getAuthUser(String email, String password);

    User getUserById(UUID id);

    User findAccountByEmail(String email);

    void saveToken(User user);

    void resetPassword(String email, String password);

    TokenResponse refresh(String jwt);

    Set<String> getUserPermissions(UUID userId);

    Page<User> findByFilters(String searchKey, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    void changePassword(Credential credential, String password);

    Optional<User> findById(UUID userId);

    User create(Authentication authentication, User model);
}
