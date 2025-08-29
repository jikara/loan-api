package com.softmint.service;

import com.softmint.entity.Credential;
import com.softmint.model.AuthUser;
import com.softmint.repo.CredentialRepo;
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
    private final CredentialRepo credentialRepo;

    @Override
    public AuthUser loadUserByUsername(String email) throws UsernameNotFoundException {
        Credential credential = credentialRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Cannot find user"));
        List<SimpleGrantedAuthority> authorities = credential.getUser()
                .getRole()
                .getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getCode()))
                .toList();
        //String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities
        AuthUser authUser = new AuthUser(email, credential.getPassword(), credential.isActivated(), true, !credential.isExpired(), !credential.isLocked(), authorities);
        authUser.setId(credential.getUser().getId());
        authUser.setDisplayName(credential.getUser().getFullName());
        authUser.setMustChangePassword(credential.isMustChangePassword());
        log.debug("User {} has permissions: {}", email, authorities);
        return authUser;
    }
}
