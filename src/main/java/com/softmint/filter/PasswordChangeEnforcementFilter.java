package com.softmint.filter;


import com.softmint.component.MessageUtils;
import com.softmint.exception.PasswordChangeRequiredException;
import com.softmint.repo.UserRepo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class PasswordChangeEnforcementFilter extends OncePerRequestFilter {
    private final MessageUtils messageUtils;
    private final UserRepo userRepo;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        // Allow public or profile endpoints to pass through even if password change is required
        if (path.contains("/change-password") || path.contains("/me") || path.contains("/logout")) {
            filterChain.doFilter(request, response);
            return;
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof UUID userId) {
            Boolean mustChange = userRepo.findMustChangePasswordById(userId);
            if (mustChange) {
                throw new PasswordChangeRequiredException(messageUtils.getMessage("auth.error.password_change_required"));
            }
        }
        filterChain.doFilter(request, response);
    }
}

