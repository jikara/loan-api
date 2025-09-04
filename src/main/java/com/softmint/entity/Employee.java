package com.softmint.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "employees")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    private EmployeeProfile profile;
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "employer_id")
    private Employer employer;
    private BigDecimal salary;
    private String designation;
    private LocalDate dateJoined;
    private LocalDate dateLeft; // nullable if current employer
    @OneToOne(cascade = CascadeType.ALL,mappedBy = "employee")
    private EmployeeUser user;
}
