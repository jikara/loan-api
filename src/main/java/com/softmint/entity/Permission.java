package com.softmint.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "permissions")
public class Permission {
    @Id
    private String code;
    @Basic(optional = false)
    private String name;
    private String description;
    @Basic(optional = false)
    @Builder.Default
    private boolean assignable = true;
}
