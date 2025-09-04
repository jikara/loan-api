package com.softmint.entity;

import com.softmint.enums.StaticRoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "employer_roles")
@PrimaryKeyJoinColumn(name = "role_id") // FK to roles.id
public class StaticRole extends Role {
    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    private StaticRoleType type;
    @OneToMany(mappedBy = "role")
    private List<EmployerUser> users;
}
