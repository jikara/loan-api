package com.softmint.service;

import com.softmint.model.AuthUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Getter
@Component
public class JwtService {
    private final SecretKey secretKey;

    public JwtService(@Value("${jwt.secret}") String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(AuthUser authUser) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("permissions", authUser.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList());
        return createToken(claims, authUser.getId().toString());
    }

    public String generateRefreshToken(AuthUser authUser) {
        Map<String, Object> claims = new HashMap<>();
        return createRefreshToken(claims, authUser.getId().toString());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 48)) //15 minutes ***Test Only***
                .and()
                .signWith(getSecretKey())
                .compact();
    }

    private String createRefreshToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 4)) // 30 minutes //Test only
                .and()
                .signWith(getSecretKey())
                .compact();
    }

    public String createPasswordResetToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "reset-password");
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30 minutes
                .and()
                .signWith(getSecretKey())
                .compact();
    }

    public String validatePasswordResetToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        if (!"reset-password".equals(claims.get("type"))) {
            throw new RuntimeException("Invalid token type");
        }
        return claims.getSubject(); // This is the email
    }

    public List<String> extractPermissions(String token) {
        Claims claims = extractAllClaims(token);
        List<?> rawList = claims.get("permissions", List.class);
        List<String> permissions = new ArrayList<>();
        if (rawList != null) {
            for (Object item : rawList) {
                if (item instanceof String) {
                    permissions.add((String) item);
                }
            }
        }
        return permissions;
    }

    public String extractBearerToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // Remove "Bearer " prefix
        }
        return null;
    }
}
