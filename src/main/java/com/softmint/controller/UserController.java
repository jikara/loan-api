package com.softmint.controller;

import com.softmint.assembler.UserModelAssembler;
import com.softmint.component.MessageUtils;
import com.softmint.dto.*;
import com.softmint.entity.User;
import com.softmint.exception.ChangePasswordException;
import com.softmint.exception.CustomLogonException;
import com.softmint.mapper.UserMapper;
import com.softmint.service.JwtService;
import com.softmint.service.UserService;
import com.softmint.util.DateTimeUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    private final UserModelAssembler assembler;
    private final PagedResourcesAssembler<User> pagedResourcesAssembler;
    private final JwtService jwtService;
    private final MessageUtils messageUtils;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @PostMapping("create")
    public ResponseEntity<?> create(
            Authentication authentication,
            @RequestBody @Valid CreateUserRequest model) {
        User mapped = userMapper.mapFromDto(model);
        User user = userService.create(authentication, mapped);
        return ResponseEntity.ok(assembler.toModel(user));
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filter(
            @RequestParam(required = false) String searchKey,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            Pageable pageable
    ) {
        LocalDateTime start = DateTimeUtil.parseStartOfDay(startDate);
        LocalDateTime end = DateTimeUtil.parseEndOfDay(endDate);
        Page<User> page = userService.findByFilters(searchKey, start, end, pageable);
        PagedModel<EntityModel<User>> model = pagedResourcesAssembler.toModel(page, assembler);
        return ResponseEntity.ok(model);
    }

    @PostMapping("login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        String ipAddress = extractClientIp(request);
        return ResponseEntity.ok(userService.login(loginRequest.email(), loginRequest.password(), ipAddress));
    }

    private String extractClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0]; // In case of multiple IPs
    }

    @PostMapping("change-password")
    public ResponseEntity<?> changePassword(@RequestHeader("Authorization") String authorizationHeader, @RequestBody ChangePasswordRequest changePasswordRequest) {
        String token = jwtService.extractBearerToken(authorizationHeader);
        User user = userService.getUserById(UUID.fromString(jwtService.extractUserId(token)));
        if (user.getCredential() == null) {
            throw new ChangePasswordException("Credentials not found");
        }
        boolean isPasswordMatch = passwordEncoder.matches(changePasswordRequest.currentPassword(), user.getCredential().getPassword());
        if (!isPasswordMatch) {
            throw new ChangePasswordException("Incorrect password");
        }
        userService.changePassword(user.getCredential(), changePasswordRequest.password());
        return ResponseEntity.ok(new SuccessResponse("Password updated successfully"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody String email) {
        // Step 1: Lookup user by email
        User user = userService.findAccountByEmail(email);
        if (user == null) {
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageUtils.getMessage("error_email_not_found"));
            throw new CustomLogonException(messageUtils.getMessage("auth.error.email_not_found"));
        }
        // Step 2: Generate a JWT token for password reset (valid for e.g., 30 minutes)
        String token = jwtService.createPasswordResetToken(user.getEmail());
        user.getCredential().setResetToken(token);
        userService.saveToken(user);
        // Step 4: Send the reset link via email
        //  emailService.sendPasswordResetEmail(user);
        // Step 5: Return a generic success response (don't leak if email exists or not for security in production)
        return ResponseEntity.ok(new SuccessResponse("Password reset instructions sent to your email address"));
    }


    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        String token = resetPasswordRequest.token();
        String password = resetPasswordRequest.password();
        // 1. Validate the reset token and extract the user's email
        String email = jwtService.validatePasswordResetToken(token);
        // 2. Reset the user's password using the provided new password
        userService.resetPassword(email, password);
        // 3. Return a success response
        return ResponseEntity.ok(new SuccessResponse("Password reset successful.Proceed to login"));
    }

    @PostMapping("refresh")
    public ResponseEntity<TokenResponse> refresh(@RequestBody RefreshRequest refreshRequest) {
        String jwt = refreshRequest.refreshToken().substring(7);
        return ResponseEntity.ok(userService.refresh(jwt));
    }

    @PostMapping("logout")
    public void logout() {
    }
}
