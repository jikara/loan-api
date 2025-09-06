package com.softmint.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED) // 👈 important
@EntityListeners({AuditingEntityListener.class})
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false, fetch = FetchType.LAZY)
    private Credential credential;
    @ManyToOne(optional = false)
    private Role role;
    @Column(updatable = false)
    private UUID createdBy;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime created;
    private UUID lastModifiedBy;
    @LastModifiedDate
    private LocalDateTime lastModified;
    private Boolean deleted = false;

    public User() {
    }

    public User(UUID id) {
        this.id = id;
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public String getRoleType() {
        if (this instanceof EmployerUser) {
            return "EMPLOYER";
        } else if (this instanceof EmployeeUser) {
            return "EMPLOYEE";
        } else {
            return "LENDER";
        }
    }
}
