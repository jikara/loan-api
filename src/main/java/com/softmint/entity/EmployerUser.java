package com.softmint.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "employer_users")
@PrimaryKeyJoinColumn(name = "user_id")
public class EmployerUser extends User {
    @ManyToOne(optional = false)
    @JoinColumn(name = "employer_id")
    private Employer employer;

    public StaticRole getRole() {
        return (StaticRole) super.getRole();
    }

    public String getEmployerName() {
        return employer.getName();
    }
}
