package com.softmint.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
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
}
