package com.softmint.service;

import com.softmint.entity.User;
import com.softmint.model.AuthUser;
import com.softmint.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthUserService implements UserDetailsService {
    private final UserRepo userRepo;

    @Override
    public AuthUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findUserByCredentialEmail(email).orElseThrow(() -> new UsernameNotFoundException("Cannot find user"));
        List<SimpleGrantedAuthority> authorities = user
                .getRole()
                .getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getCode()))
                .toList();
        //String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities
        AuthUser authUser = new AuthUser(email, user.getCredential().getPassword(), user.getCredential().isActivated(), true, !user.getCredential().isExpired(), !user.getCredential().isLocked(), authorities);
        authUser.setId(user.getId());
        authUser.setDisplayName(user.getFullName());
        authUser.setMustChangePassword(user.getCredential().isMustChangePassword());
        log.debug("User {} has permissions: {}", email, authorities);
        return authUser;
    }
}
