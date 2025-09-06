package com.softmint.entity;

import com.softmint.enums.StaticRoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "employer_roles")
@PrimaryKeyJoinColumn(name = "role_id") // FK to roles.id
public class StaticRole extends Role {
    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    private StaticRoleType type;
}
