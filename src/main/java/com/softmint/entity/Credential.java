package com.softmint.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "credentials")
public class Credential implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true, nullable = false)
    @Basic(optional = false)
    private String email;
    @Basic(optional = false)
    private String password;
    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime updated;
    private LocalDateTime lastLoginAt;
    private String lastLoginIp;
    private String resetToken;
    private boolean locked = false;
    private boolean expired = false;
    private boolean activated = true;
    @OneToOne(mappedBy = "credential")
    private User user;
    private boolean mustChangePassword = true;
    private LocalDateTime lastPasswordChangedAt;
    @Basic(optional = false)
    private int failedLoginAttempts = 0;


}
